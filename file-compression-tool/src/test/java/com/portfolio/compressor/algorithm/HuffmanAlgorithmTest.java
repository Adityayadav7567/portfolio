package com.portfolio.compressor.algorithm;

import org.junit.jupiter.api.Test;import java.util.Map;import static org.junit.jupiter.api.Assertions.*;

class HuffmanAlgorithmTest {
    @Test void buildsTreeWithCombinedFrequency() { long[] f = new long[256]; f['a']=5; f['b']=2; f['c']=1; HuffmanNode root = new HuffmanTreeBuilder().build(f); assertEquals(8, root.frequency()); }
    @Test void generatesPrefixFreeEncodingTable() { long[] f = new long[256]; f['a']=5; f['b']=2; f['c']=1; Map<Integer,String> t = new EncodingTableGenerator().generate(new HuffmanTreeBuilder().build(f)); assertEquals(3, t.size()); assertTrue(t.values().stream().noneMatch(String::isBlank)); for (String a: t.values()) for (String b: t.values()) if (!a.equals(b)) assertFalse(b.startsWith(a)); }
    @Test void minHeapPollsInSortedOrder() { MinHeap<Integer> h = new MinHeap<>(); h.add(5); h.add(1); h.add(3); assertEquals(1,h.poll()); assertEquals(3,h.poll()); assertEquals(5,h.poll()); }
}
