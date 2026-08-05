package com.portfolio.compressor.algorithm;

/** Binary tree node used by Huffman's greedy algorithm. */
public final class HuffmanNode implements Comparable<HuffmanNode> {
    private final int value;
    private final long frequency;
    private final HuffmanNode left;
    private final HuffmanNode right;

    public HuffmanNode(int value, long frequency, HuffmanNode left, HuffmanNode right) {
        this.value = value; this.frequency = frequency; this.left = left; this.right = right;
    }
    public static HuffmanNode leaf(int value, long frequency) { return new HuffmanNode(value, frequency, null, null); }
    public static HuffmanNode parent(HuffmanNode a, HuffmanNode b) { return new HuffmanNode(-1, a.frequency + b.frequency, a, b); }
    public boolean isLeaf() { return left == null && right == null; }
    public int value() { return value; }
    public long frequency() { return frequency; }
    public HuffmanNode left() { return left; }
    public HuffmanNode right() { return right; }
    @Override public int compareTo(HuffmanNode o) { return Long.compare(this.frequency, o.frequency); }
}
