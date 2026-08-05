package com.portfolio.compressor.algorithm;

import java.util.ArrayList;
import java.util.List;

/** Array-backed binary min heap implemented from scratch for placement-ready DSA clarity. */
public final class MinHeap<T extends Comparable<T>> {
    private final List<T> heap = new ArrayList<>();
    public int size() { return heap.size(); }
    public boolean isEmpty() { return heap.isEmpty(); }
    public void add(T value) { heap.add(value); siftUp(heap.size() - 1); }
    public T poll() {
        if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
        T min = heap.getFirst(); T last = heap.removeLast();
        if (!heap.isEmpty()) { heap.set(0, last); siftDown(0); }
        return min;
    }
    private void siftUp(int i) { while (i > 0) { int p = (i - 1) / 2; if (heap.get(p).compareTo(heap.get(i)) <= 0) break; swap(p, i); i = p; } }
    private void siftDown(int i) { while (true) { int l = i * 2 + 1, r = l + 1, s = i; if (l < heap.size() && heap.get(l).compareTo(heap.get(s)) < 0) s = l; if (r < heap.size() && heap.get(r).compareTo(heap.get(s)) < 0) s = r; if (s == i) return; swap(i, s); i = s; } }
    private void swap(int a, int b) { T t = heap.get(a); heap.set(a, heap.get(b)); heap.set(b, t); }
}
