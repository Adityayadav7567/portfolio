package com.portfolio.compressor.services;

import com.portfolio.compressor.algorithm.*;
import com.portfolio.compressor.exceptions.CompressionException;
import com.portfolio.compressor.model.CompressionResult;
import com.portfolio.compressor.storage.HistoryRepository;
import com.portfolio.compressor.utils.BitReader;
import com.portfolio.compressor.utils.BitWriter;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/** Coordinates streaming Huffman compression/decompression and the custom .huff container format. */
public final class HuffmanCodecService {
    private static final int MAGIC = 0x48465546; // H F U F
    private static final int VERSION = 1;
    private final HistoryRepository historyRepository;
    public HuffmanCodecService(HistoryRepository historyRepository) { this.historyRepository = historyRepository; }

    public CompressionResult compress(Path input, Path output, ProgressListener progress) throws CompressionException {
        validateReadable(input); Instant start = Instant.now();
        try {
            long originalSize = Files.size(input); long[] freq; byte[] sha = sha256(input);
            try (InputStream in = Files.newInputStream(input)) { freq = FrequencyCounter.count(in); }
            HuffmanNode root = new HuffmanTreeBuilder().build(freq); Map<Integer, String> table = new EncodingTableGenerator().generate(root);
            Files.createDirectories(output.toAbsolutePath().getParent());
            try (DataOutputStream header = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(output)))) {
                header.writeInt(MAGIC); header.writeInt(VERSION); header.writeUTF(input.getFileName().toString()); header.writeLong(originalSize); header.write(sha); for (long f : freq) header.writeLong(f);
                try (BitWriter bits = new BitWriter(header); InputStream in = Files.newInputStream(input)) {
                    byte[] buf = new byte[8192]; int n; long done = 0;
                    while ((n = in.read(buf)) != -1) { for (int i = 0; i < n; i++) bits.writeBits(table.get(buf[i] & 0xFF)); done += n; progress.onProgress(originalSize == 0 ? 1 : (double) done / originalSize); }
                }
            }
            CompressionResult result = new CompressionResult(input, output, originalSize, Files.size(output), Duration.between(start, Instant.now()));
            historyRepository.save(result); return result;
        } catch (IOException e) { throw new CompressionException("Unable to compress " + input, e); }
    }

    public Path decompress(Path compressed, Path outputDirectory, ProgressListener progress) throws CompressionException {
        validateReadable(compressed);
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(Files.newInputStream(compressed)))) {
            if (in.readInt() != MAGIC || in.readInt() != VERSION) throw new CompressionException("Unsupported or corrupted .huff file");
            String name = in.readUTF(); long originalSize = in.readLong(); byte[] expectedSha = in.readNBytes(32); long[] freq = new long[256]; for (int i = 0; i < 256; i++) freq[i] = in.readLong();
            HuffmanNode root = new HuffmanTreeBuilder().build(freq); Files.createDirectories(outputDirectory); Path out = unique(outputDirectory.resolve(name));
            try (OutputStream file = Files.newOutputStream(out); BitReader bits = new BitReader(in)) {
                for (long written = 0; written < originalSize; written++) { HuffmanNode node = root; while (!node.isLeaf()) { int bit = bits.readBit(); if (bit < 0) throw new CompressionException("Unexpected end of compressed data"); node = bit == 0 ? node.left() : node.right(); } file.write(node.value()); progress.onProgress((double) (written + 1) / Math.max(1, originalSize)); }
            }
            if (!MessageDigest.isEqual(expectedSha, sha256(out))) throw new CompressionException("Integrity check failed: SHA-256 mismatch");
            return out;
        } catch (IOException e) { throw new CompressionException("Unable to decompress " + compressed, e); }
    }
    private static void validateReadable(Path p) throws CompressionException { if (!Files.isRegularFile(p) || !Files.isReadable(p)) throw new CompressionException("Missing or unreadable file: " + p); }
    private static byte[] sha256(Path p) throws IOException { try { MessageDigest d = MessageDigest.getInstance("SHA-256"); try (InputStream in = Files.newInputStream(p)) { byte[] b = new byte[8192]; int n; while ((n = in.read(b)) != -1) d.update(b,0,n); } return d.digest(); } catch (Exception e) { throw new IOException(e); } }
    private static Path unique(Path p) { int i = 1; Path candidate = p; while (Files.exists(candidate)) candidate = p.resolveSibling(p.getFileName() + "." + i++); return candidate; }
}
