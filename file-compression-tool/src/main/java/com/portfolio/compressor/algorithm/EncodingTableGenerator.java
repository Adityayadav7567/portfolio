package com.portfolio.compressor.algorithm;

import java.util.HashMap;
import java.util.Map;

/** Recursively traverses the Huffman tree to generate byte-to-bit-string encodings. */
public final class EncodingTableGenerator {
    public Map<Integer, String> generate(HuffmanNode root) { Map<Integer, String> table = new HashMap<>(); walk(root, "", table); return table; }
    private void walk(HuffmanNode node, String path, Map<Integer, String> table) {
        if (node == null) return;
        if (node.isLeaf()) { if (node.frequency() > 0) table.put(node.value(), path.isEmpty() ? "0" : path); return; }
        walk(node.left(), path + '0', table); walk(node.right(), path + '1', table);
    }
}
