package com.portfolio.compressor.storage;

import com.portfolio.compressor.model.CompressionResult;
import com.portfolio.compressor.model.HistoryEntry;
import java.io.IOException;
import java.util.List;

public interface HistoryRepository { void save(CompressionResult result) throws IOException; List<HistoryEntry> findAll() throws IOException; }
