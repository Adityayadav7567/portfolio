package com.portfolio.compressor.model;

import java.nio.file.Path;
import java.time.Duration;

public record CompressionResult(Path input, Path output, long originalSize, long compressedSize, Duration timeTaken) {
    public double compressionRatio() { return originalSize == 0 ? 0 : (double) compressedSize / originalSize; }
    public double spaceSavedPercent() { return originalSize == 0 ? 0 : (1.0 - compressionRatio()) * 100.0; }
}
