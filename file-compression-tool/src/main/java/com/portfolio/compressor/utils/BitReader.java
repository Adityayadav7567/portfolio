package com.portfolio.compressor.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/** Reads single bits from a byte stream without loading the compressed file into memory. */
public final class BitReader implements Closeable {
    private final InputStream in; private int current = -1; private int remaining;
    public BitReader(InputStream in) { this.in = in; }
    public int readBit() throws IOException { if (remaining == 0) { current = in.read(); if (current == -1) return -1; remaining = 8; } remaining--; return (current >>> remaining) & 1; }
    @Override public void close() throws IOException { in.close(); }
}
