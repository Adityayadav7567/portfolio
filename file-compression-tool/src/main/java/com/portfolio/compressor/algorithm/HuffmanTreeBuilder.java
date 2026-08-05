package com.portfolio.compressor.algorithm;

/** Builds an optimal prefix-code tree by repeatedly merging the two least frequent nodes. */
public final class HuffmanTreeBuilder {
    public HuffmanNode build(long[] frequencies) {
        MinHeap<HuffmanNode> heap = new MinHeap<>();
        for (int i = 0; i < frequencies.length; i++) if (frequencies[i] > 0) heap.add(HuffmanNode.leaf(i, frequencies[i]));
        if (heap.isEmpty()) return null;
        if (heap.size() == 1) return HuffmanNode.parent(heap.poll(), HuffmanNode.leaf(0, 0));
        while (heap.size() > 1) heap.add(HuffmanNode.parent(heap.poll(), heap.poll()));
        return heap.poll();
    }
}
