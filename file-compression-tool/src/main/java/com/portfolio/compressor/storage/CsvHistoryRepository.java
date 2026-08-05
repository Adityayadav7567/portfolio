package com.portfolio.compressor.storage;

import com.portfolio.compressor.model.*;
import java.io.*;import java.nio.file.*;import java.time.LocalDateTime;import java.util.*;

/** Lightweight durable history store; can be swapped with SQLite without changing services. */
public final class CsvHistoryRepository implements HistoryRepository {
    private final Path file;
    public CsvHistoryRepository(Path file) { this.file = file; }
    public void save(CompressionResult r) throws IOException { Files.createDirectories(file.getParent()); try (BufferedWriter w = Files.newBufferedWriter(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) { w.write(String.join(",", esc(r.input().getFileName().toString()), LocalDateTime.now().toString(), String.valueOf(r.originalSize()), String.valueOf(r.compressedSize()), String.valueOf(r.compressionRatio()))); w.newLine(); } }
    public List<HistoryEntry> findAll() throws IOException { if (!Files.exists(file)) return List.of(); List<HistoryEntry> list = new ArrayList<>(); for (String line : Files.readAllLines(file)) { String[] p = line.split(",", -1); if (p.length >= 5) list.add(new HistoryEntry(p[0], LocalDateTime.parse(p[1]), Long.parseLong(p[2]), Long.parseLong(p[3]), Double.parseDouble(p[4]))); } return list; }
    private static String esc(String s) { return s.replace(",", "_"); }
}
