package com.ashok.lang.dsa;

public class DArray {
    int[] list;
    int size = 0;

    public DArray() {
        list = new int[16];
    }

    public DArray(int capacity) {
        list = new int[capacity];
    }

    public DArray(int[] ar) {
        list = new int[ar.length];
        for (int i = 0; i < ar.length; i++)
            list[i] = ar[i];
    }

    public void add(int n) {
        ensureCapacity();
        list[size] = n;
        size++;
    }

    private void ensureCapacity() {
        if (size < list.length)
            return;

        int[] temp = new int[list.length << 1];
        for (int i = 0; i < list.length; i++)
            temp[i] = list[i];

        list = temp;
    }

    public int[] toArray() {
        int[] ar = new int[size];
        for (int i = 0; i < size; i++)
            ar[i] = list[i];

        return ar;
    }
}
