package com.portfolio.compressor.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/** Packs individual bits into bytes for compact Huffman output. */
public final class BitWriter implements Closeable {
    private final OutputStream out; private int current; private int count;
    public BitWriter(OutputStream out) { this.out = out; }
    public void writeBit(int bit) throws IOException { current = (current << 1) | (bit & 1); if (++count == 8) flushByte(); }
    public void writeBits(String bits) throws IOException { for (int i = 0; i < bits.length(); i++) writeBit(bits.charAt(i) == '1' ? 1 : 0); }
    private void flushByte() throws IOException { out.write(current); current = 0; count = 0; }
    public void finish() throws IOException { if (count > 0) { current <<= (8 - count); flushByte(); } out.flush(); }
    @Override public void close() throws IOException { finish(); out.close(); }
}
