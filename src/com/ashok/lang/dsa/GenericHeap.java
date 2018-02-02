package com.ashok.lang.dsa;

import java.util.Comparator;

/**
 * This Heap (aka Priority Queue) implementation is for fix size.
 * It is assumed that in most situation the max size is already known and
 * we have to know the highest priority element or highest k priority elements
 * or kth priority element. In all these case the k (max size) is fixed.
 *
 * @param <E> type of parameter Heap holds.
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class GenericHeap<E> {
    private final Comparator<? super E> comparator;
    public final int capacity;
    private int count = 0;
    private final E[] heap;

    public GenericHeap(Comparator<? super E> comparator, int capacity) {
        this.comparator = comparator;
        this.capacity = capacity;
        heap = (E[]) new Object[capacity];
    }

    public E poll() {
        E res = heap[0];
        count--;
        heap[0] = heap[count];
        reformatDown(0);

        return res;
    }

    public E peek() {
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
    public boolean offer(E e) {
        if (count == capacity)
            return update(e);
        else
            add(e);

        return true;
    }

    public void addAll(E[] ar) {
        for (E e : ar)
            offer(e);
    }

    private void add(E e) {
        heap[count] = e;
        reformatUp(count);
        count++;
    }

    private boolean update(E e) {
        if (comparator.compare(e, heap[0]) <= 0)
            return false;

        heap[0] = e;
        reformatDown(0);
        return true;
    }

    private void reformatUp(int index) {
        E val = heap[index];
        while (index != 0) {
            int parent = (index - 1) >>> 1;
            E e = heap[parent];
            if (comparator.compare(e, val) <= 0)
                break;

            set(index, e);
            index = parent;
        }

        set(index, val);
    }

    private void reformatDown(int index) {
        E val = heap[index];
        while (((index << 1) + 1) < count) {
            int c = getSmallerChild(index);
            E e = heap[c];
            if (comparator.compare(val, e) <= 0)
                break;

            set(index, e);
            index = c;
        }

        set(index, val);
    }

    private void set(int index, E e) {
        heap[index] = e;
    }

    private int getParent(int index) {
        return (index - 1) >>> 1;
    }

    private int getSmallerChild(int index) {
        int c1 = (index << 1) + 1, c2 = c1 + 1;

        if (c2 == count)
            return c1;

        return comparator.compare(heap[c1], heap[c2]) <= 0 ? c1 : c2;
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
