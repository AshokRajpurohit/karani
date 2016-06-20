package com.ashok.lang.dsa;

public class QueueArray {

    private int size = 16, start = 0, end = 0, count = 0;
    private int[] ar;

    public QueueArray() {
        ar = new int[size];
    }

    public QueueArray(int size) {
        if (size > 16)
            this.size = size;
        ar = new int[size];
    }

    public void add(int n) {
        ensureCapacity();
        end++;

        if (end == size)
            end = 0;

        ar[end] = n;
        count++;
    }

    private void ensureCapacity() {
        if (count < size)
            return;

        int[] temp = new int[size << 1];
        for (int i = 0; i < ar.length; i++)
            temp[i] = ar[i];

        if (end < start) {
            for (int i = 0; i <= end; i++) {
                temp[size + i] = ar[i];
            }
            end += size;
        }
        size = size << 1;
    }

    public void remove() {
        if (count > 0) {
            count--;
            start++;
        }
    }

    public int capacity() {
        return size;
    }

}
