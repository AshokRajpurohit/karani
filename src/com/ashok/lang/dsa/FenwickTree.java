package com.ashok.lang.dsa;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class FenwickTree {
    private final long[] array;
    private int size = 0;
    private final int capacity;

    public FenwickTree(int capacity) {
        this.capacity = capacity;
        array = new long[capacity];
    }

    public FenwickTree(int[] ar) {
        this(ar.length);

        for (int value : ar)
            add(value);
    }

    public void add(long value) {
        array[size++] = value;

        if (size == capacity)
            populate();
    }

    /**
     * Populates the tree for the very first and only time.
     * Every update operation is called once. This method runs in order of
     * N (array length).
     */
    private void populate() {
        for (int i = 0; i < capacity; i++)
            updateTree(i);
    }

    public void update(int index, final long value) {
        updateTree(index, value);
        array[index] += value;
    }

    public void replace(int index, long value) {
        long oldValue = query(index, index);
        update(index, value - oldValue);
    }

    /**
     * Returns the sum of elements from start to end index, both inclusive.
     *
     * @param start
     * @param end
     * @return
     */
    public long query(int start, int end) {
        long result = 0;
        while (end >= start) {
            result += array[end];
            end = end & (end + 1);
            end--;
        }

        start--;
        while (start > end) {
            result -= array[start];
            start = start & (start + 1);
            start--;
        }

        return result;
    }

    public long query(int index) {
        int sum = 0;
        while(index >= 0) {
            sum += array[index];
            index = index & (index + 1);
            index--;
        }

        return sum;
    }

    /**
     * This method finds the parent and update the same with this node value.
     *
     * @param index
     */
    private void updateTree(int index) {
        int nextIndex = index | (index + 1);

        if (nextIndex >= capacity)
            return;

        array[nextIndex] += array[index];
    }

    /**
     * This is an addition operation for the specified node.
     * The update value is added to the same node and recursively
     * the parent node is also updated.
     *
     * @param index current node index to be updated.
     * @param value value to be updated.
     */
    private void updateTree(int index, long value) {
        int nextIndex = index | (index + 1);

        if (nextIndex >= capacity)
            return;

        array[nextIndex] += value;
        updateTree(nextIndex, value);
    }

    private long operation(long a, long b) {
        return a + b;
    }

    private long inverseOperation(long a, long b) {
        return a - b;
    }
}
