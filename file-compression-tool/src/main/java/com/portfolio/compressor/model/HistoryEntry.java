package com.portfolio.compressor.model;

import java.time.LocalDateTime;
public record HistoryEntry(String fileName, LocalDateTime date, long originalSize, long compressedSize, double compressionRatio) {}
