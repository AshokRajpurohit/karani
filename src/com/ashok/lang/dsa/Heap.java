package com.ashok.lang.dsa;

/**
 * This class is to support heap (and also priority queues) related functions,
 * i.e. heapsort, finding k maximum or minimum elements.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Heap {
    private Heap() {
        super();
    }

    private int[] heap;
    private boolean min;
    int k = 0;

    public static int[] sort(int[] ar) {
        return sort(ar, true);
    }

    /**
     * This function sorts the given array using HeapSort algorithm.
     * for more details please refer "Introduction to Algorithms by CLRS".
     *
     * @param ar the array to be sorted.
     * @param min whether the min element should be first or last.
     * @return sorted array
     */
    public static int[] sort(int[] ar, boolean min) {
        Heap h = new Heap();
        h.heap = new int[ar.length];
        h.min = min;

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
        Heap h = new Heap();
        h.heap = new int[k];
        h.min = true;
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
        Heap h = new Heap();
        h.heap = new int[k];
        h.min = false;
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

    private void add(int n) {
        heap[k] = n;
        reformatUp(k);
        k++;
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
