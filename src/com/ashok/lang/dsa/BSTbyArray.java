package com.ashok.lang.dsa;

public class BSTbyArray {
    private int[] node, left, right;
    private int size = 0;

    public BSTbyArray() {
        node = new int[16];
        left = new int[16];
        right = new int[16];
    }

    public BSTbyArray(int size) {
        node = new int[size];
        left = new int[size];
        right = new int[size];
    }

    public BSTbyArray(int[] ar) {
        this(ar.length);
        add(ar);
    }

    public void add(int[] ar) {
        ensureCapacity(size + ar.length);
        for (int e : ar)
            add(e);
    }

    public void add(int n) {
        ensureCapacity(size + 1);
        node[size] = n;

        int temp = 0;
        while (true) {
            if (n > node[temp]) {
                if (right[temp] == 0) {
                    right[temp] = size;
                    size++;
                    return;
                }
                temp = right[temp];
            } else {
                if (left[temp] == 0) {
                    left[temp] = size;
                    size++;
                    return;
                }
                temp = left[temp];
            }
        }
    }

    public boolean find(int n) {
        int temp = 0;
        while (true) {
            if (n == node[temp])
                return true;
            if (n > node[temp]) {
                if (right[temp] == 0)
                    return false;
                temp = right[temp];
            } else {
                if (left[temp] == 0)
                    return false;
                temp = left[temp];
            }
        }
    }

    public String print() {
        StringBuilder sb = new StringBuilder(size << 3);
        print(sb, 0);
        return sb.toString();
    }

    public String toString() {
        return print();
    }

    private void print(StringBuilder sb, int n) {
        if (left[n] != 0)
            print(sb, left[n]);

        sb.append(", ").append(node[n]);

        if (right[n] != 0)
            print(sb, right[n]);
    }

    public int[] sort() {
        int[] ar = new int[size];
        sort(ar);
        return ar;
    }

    private void sort(int[] ar) {
        int index = 0;
        if (left[0] != 0)
            index = sort(ar, left[0], 0);
        ar[index] = node[0];
        if (right[0] != 0)
            sort(ar, right[0], index + 1);
    }

    private int sort(int[] ar, int node_index, int index) {
        if (left[node_index] != 0)
            index = sort(ar, left[node_index], index);
        ar[index] = node[node_index];
        index++;
        if (right[node_index] != 0)
            return sort(ar, right[node_index], index);

        return index;
    }

    private void ensureCapacity(int n) {
        if (node.length >= n)
            return;
        int[] temp = new int[node.length << 1];
        for (int i = 0; i < size; i++)
            temp[i] = node[i];

        node = temp;

        temp = new int[node.length];
        for (int i = 0; i < size; i++)
            temp[i] = left[i];

        left = temp;
        temp = new int[node.length];
        for (int i = 0; i < size; i++)
            temp[i] = right[i];

        right = temp;
    }
}
