package com.portfolio.compressor.services;

import com.portfolio.compressor.storage.CsvHistoryRepository;import org.junit.jupiter.api.Test;import org.junit.jupiter.api.io.TempDir;import java.nio.file.*;import static org.junit.jupiter.api.Assertions.*;

class HuffmanCodecServiceTest {
    @TempDir Path temp;
    @Test void compressesAndDecompressesFilePerfectly() throws Exception { Path input=temp.resolve("sample.txt"); Files.writeString(input, "hello huffman huffman huffman"); HuffmanCodecService s = new HuffmanCodecService(new CsvHistoryRepository(temp.resolve("history/history.csv"))); var result=s.compress(input,temp.resolve("sample.txt.huff"),p->{}); assertTrue(Files.size(result.output())>0); Path restored=s.decompress(result.output(),temp.resolve("out"),p->{}); assertEquals(Files.readString(input), Files.readString(restored)); }
    @Test void supportsEmptyFile() throws Exception { Path input=temp.resolve("empty.bin"); Files.write(input, new byte[0]); HuffmanCodecService s = new HuffmanCodecService(new CsvHistoryRepository(temp.resolve("history/history.csv"))); var result=s.compress(input,temp.resolve("empty.bin.huff"),p->{}); Path restored=s.decompress(result.output(),temp.resolve("out"),p->{}); assertArrayEquals(Files.readAllBytes(input), Files.readAllBytes(restored)); }
}
