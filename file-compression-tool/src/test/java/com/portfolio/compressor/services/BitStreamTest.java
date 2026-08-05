package com.portfolio.compressor.services;

import com.portfolio.compressor.utils.*;import org.junit.jupiter.api.Test;import java.io.*;import static org.junit.jupiter.api.Assertions.*;

class BitStreamTest {
    @Test void writesAndReadsBitsAcrossByteBoundaries() throws Exception { ByteArrayOutputStream out = new ByteArrayOutputStream(); try (BitWriter w = new BitWriter(out)) { w.writeBits("1011001110"); } try (BitReader r = new BitReader(new ByteArrayInputStream(out.toByteArray()))) { for (char c : "1011001110".toCharArray()) assertEquals(c == '1' ? 1 : 0, r.readBit()); } }
}
