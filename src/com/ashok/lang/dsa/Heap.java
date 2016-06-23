package com.ashok.lang.dsa;

import java.util.Arrays;

/**
 * This class is to support heap (and also priority queues) related functions,
 * i.e. heapsort, finding k maximum or minimum elements.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Heap {
    private int[] heap;
    public final boolean min;
    private int k = 0;
    final static int DEFAULT_SIZE = 16;

    public Heap() {
        min = true;
        heap = new int[DEFAULT_SIZE];
    }

    public Heap(boolean min, int size) {
        this.min = min;
        heap = new int[size];
    }

    public static int[] sort(int[] ar) {
        return sort(ar, true);
    }

    /**
     * This function sorts the given array using HeapSort algorithm.
     * for more details please refer "Introduction to Algorithms by CLRS".
     *
     * @param ar  the array to be sorted.
     * @param min whether the min element should be first or last.
     * @return sorted array
     */
    public static int[] sort(int[] ar, boolean min) {
        Heap h = new Heap(min, ar.length);
        for (int i = 0; i < ar.length; i++)
            h.add(ar[i]);

        h.sort();
        return h.heap;
    }

    public static int kthMax(int[] ar, int k) {
        if (k > ar.length >> 1)
            return minElements(ar, ar.length - k + 1)[0];

        return maxElements(ar, k)[0];
    }

    public static int kthMin(int[] ar, int k) {
        if (k > ar.length >> 1)
            return maxElements(ar, ar.length - k + 1)[0];

        return minElements(ar, k)[0];
    }

    public static int[] maxElements(int[] ar, int k) {
        Heap h = new Heap(true, k);
        for (int i = 0; i < k; i++)
            h.add(ar[i]);

        for (int i = k; i < ar.length; i++) {
            if (ar[i] > h.heap[0]) {
                h.heap[0] = ar[i];
                h.reformatDown(0);
            }
        }

        return h.heap;
    }

    public static int[] minElements(int[] ar, int k) {
        Heap h = new Heap(false, k);
        for (int i = 0; i < k; i++)
            h.add(ar[i]);

        for (int i = k; i < ar.length; i++) {
            if (ar[i] < h.heap[0]) {
                h.heap[0] = ar[i];
                h.reformatDown(0);
            }
        }

        return h.heap;
    }

    private void sort() {
        while (k > 0) {
            int temp = heap[0];
            k--;
            heap[0] = heap[k];
            heap[k] = temp;
            reformatDown(0);
        }

        // let's reverse the array.
        for (int i = 0, j = heap.length - 1; i < j; i++, j--) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

    public void add(int n) {
        if (k == heap.length)
            expandCapacity();

        heap[k] = n;
        reformatUp(k);
        k++;
    }

    public int[] getHeapArray() {
        return Arrays.copyOf(heap, heap.length);
    }

    public void addAll(int[] ar) {
        for (int e : ar)
            add(e);
    }

    public int removeFirst() {
        int value = heap[0];
        k--;
        heap[0] = heap[k];
        reformatDown(0);

        return value;
    }

    public void replaceFirst(int n) {
        heap[0] = n;
        reformatDown(0);
    }

    public int removeLast() {
        k--;
        return heap[k];
    }

    private void expandCapacity() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    private void reformatUp(int index) {
        if (index == 0)
            return;

        int parent = (index - 1) >>> 1;

        if (min) {
            if (heap[index] < heap[parent]) {
                swap(index, parent);
                reformatUp(parent);
            }
            return;
        }

        if (heap[index] > heap[parent]) {
            swap(index, parent);
            reformatUp(parent);
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void reformatDown(int index) {
        if ((index << 1) + 1 >= k)
            return;

        int child = (index << 1) + 1;
        int child2 = child + 1;
        child2 = child2 == k ? child : child2;

        if (min) {
            child = heap[child] > heap[child2] ? child2 : child;
            if (heap[index] > heap[child]) {
                swap(index, child);
                reformatDown(child);
            }
            return;
        }

        child = heap[child] > heap[child2] ? child : child2;
        if (heap[index] < heap[child]) {
            swap(index, child);
            reformatDown(child);
        }
    }
}
