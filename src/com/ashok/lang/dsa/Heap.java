package com.ashok.lang.dsa;

/**
 * This class is to support heap (and also priority queues) related functions.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Heap {
    private int[] heap;
    public final int capacity;
    private int count = 0;

    public Heap(int capacity) {
        this.capacity = capacity;
        heap = new int[capacity];
    }

    public int poll() {
        int res = heap[0];
        count--;
        heap[0] = heap[count];
        reformatDown(0);

        return res;
    }

    public int peek() {
        return heap[0];
    }

    public boolean isFull() {
        return count == capacity;
    }

    /**
     * If the heap is already full then this will update the already existing
     * top element if necessary.
     *
     * @param e
     * @return
     */
    public boolean offer(int e) {
        if (count == capacity)
            return update(e);
        else
            add(e);

        return true;
    }

    private void add(int e) {
        heap[count] = e;
        reformatUp(count);
        count++;
    }

    private boolean update(int e) {
        if (e - heap[0] <= 0)
            return false;

        heap[0] = e;
        reformatDown(0);
        return true;
    }

    private void reformatUp(int index) {
        int val = heap[index];
        while (index != 0) {
            int parent = (index - 1) >>> 1;
            int e = heap[parent];
            if (e - val <= 0)
                break;

            heap[index] = e;
            index = parent;
        }

        heap[index] = val;
    }

    private void reformatDown(int index) {
        int val = heap[index];
        while (((index << 1) + 1) < count) {
            int c = getSmallerChild(index);
            int e = heap[c];
            if (val - e <= 0)
                break;

            heap[index] = e;
            index = c;
        }

        heap[index] = val;
    }

    private int getParent(int index) {
        return (index - 1) >>> 1;
    }

    private int getSmallerChild(int index) {
        int c1 = (index << 1) + 1, c2 = c1 + 1;

        if (c2 == count)
            return c1;

        return heap[c1] - heap[c2] <= 0 ? c1 : c2;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

}
