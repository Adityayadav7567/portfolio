package com.portfolio.compressor.services;

import com.portfolio.compressor.exceptions.CompressionException;import com.portfolio.compressor.model.CompressionResult;
import java.io.IOException;import java.nio.file.*;import java.util.*;import java.util.concurrent.*;import java.util.stream.Stream;

/** Multi-threaded batch and folder compression facade. */
public final class BatchCompressionService {
    private final HuffmanCodecService codec;
    public BatchCompressionService(HuffmanCodecService codec) { this.codec = codec; }
    public List<CompressionResult> compressFiles(List<Path> files, Path outDir) throws InterruptedException, ExecutionException {
        try (ExecutorService pool = Executors.newFixedThreadPool(Math.max(2, Runtime.getRuntime().availableProcessors() - 1))) {
            List<Future<CompressionResult>> futures = new ArrayList<>();
            for (Path file : files) futures.add(pool.submit(() -> codec.compress(file, outDir.resolve(file.getFileName() + ".huff"), p -> {})));
            List<CompressionResult> results = new ArrayList<>(); for (Future<CompressionResult> f : futures) results.add(f.get()); return results;
        }
    }
    public List<CompressionResult> compressFolder(Path folder, Path outDir) throws IOException, InterruptedException, ExecutionException { try (Stream<Path> s = Files.walk(folder)) { return compressFiles(s.filter(Files::isRegularFile).toList(), outDir); } }
}
