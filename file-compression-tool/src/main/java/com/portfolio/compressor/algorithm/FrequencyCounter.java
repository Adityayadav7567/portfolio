package com.portfolio.compressor.algorithm;

import java.io.IOException;
import java.io.InputStream;

/** Streams bytes and counts frequencies in O(n) time with constant 256-entry memory. */
public final class FrequencyCounter {
    private FrequencyCounter() {}
    public static long[] count(InputStream in) throws IOException {
        long[] f = new long[256]; byte[] buf = new byte[8192]; int n;
        while ((n = in.read(buf)) != -1) for (int i = 0; i < n; i++) f[buf[i] & 0xFF]++;
        return f;
    }
}
