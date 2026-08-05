package com.portfolio.compressor.controller;

import com.portfolio.compressor.model.CompressionResult;import com.portfolio.compressor.services.HuffmanCodecService;
import javafx.concurrent.Task;import java.nio.file.Path;

public final class CompressionController {
    private final HuffmanCodecService codec;
    public CompressionController(HuffmanCodecService codec) { this.codec = codec; }
    public Task<CompressionResult> compressTask(Path in, Path out) { return new Task<>() { protected CompressionResult call() throws Exception { return codec.compress(in, out, this::updateProgress); } }; }
    public Task<Path> decompressTask(Path in, Path dir) { return new Task<>() { protected Path call() throws Exception { return codec.decompress(in, dir, this::updateProgress); } }; }
}
