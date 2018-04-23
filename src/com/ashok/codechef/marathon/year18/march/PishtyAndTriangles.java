/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.march;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem Name: Pishty and Triangles
 * Link: https://www.codechef.com/MARCH18A/problems/PSHTRG
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PishtyAndTriangles {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int LIMIT = 100000, MAX_ARRAY_LENGTH = 46;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] ar = in.readIntArray(n);
        QueryProcessor rq = new RangeQueryUpdate(ar);
        StringBuilder sb = new StringBuilder(q << 2);
        while (q > 0) {
            q--;
            if (in.readInt() == 1) {
                rq.update(in.readInt() - 1, in.readInt());
            } else {
                sb.append(rq.query(in.readInt() - 1, in.readInt() - 1)).append('\n');
            }
        }

        out.print(sb);
    }

    private static int search(int[] ar, int val) {
        if (ar.length <= 10)
            return iterativeSearch(ar, val);

        return ar.length <= 10 ? iterativeSearch(ar, val) : Arrays.binarySearch(ar, val);
    }

    private static int iterativeSearch(int[] ar, int val) {
        int index = 0;
        for (int e : ar) {
            if (e == val)
                return index;

            index++;
        }

        throw new RuntimeException("Something is wrong with code.");
    }

    interface QueryProcessor {
        void update(int index, int value);

        long query(int start, int end);
    }

    private static class BruteForceQueryProcessor implements QueryProcessor {
        final int[] ar;
        final Heap heap;

        BruteForceQueryProcessor(int[] ar) {
            this.ar = ar;
            heap = new Heap(Math.min(MAX_ARRAY_LENGTH, ar.length));
        }

        @Override
        public void update(int index, int value) {
            ar[index] = value;
        }

        @Override
        public long query(int start, int end) {
            if (end - start < 2) return 0;
            heap.reset();

            for (int i = start; i <= end; i++)
                heap.offer(ar[i]);

            return getMaxPerimeter();
        }

        private long getMaxPerimeter() {
            long a = heap.poll(), b = heap.poll(), c;
            long val = 0;
            while (!heap.isEmpty()) {
                c = heap.poll();
                if (c < a + b) val = a + b + c;

                a = b;
                b = c;
            }

            return val;
        }
    }

    /**
     * This class is for frequent updates of elements. It updates the element and
     * Structure in order of log(n). If updates are for in range then it's
     * better to use {@link RangeQueryLazy} Lazy propagation.
     */
    private static class RangeQueryUpdate implements QueryProcessor {
        private Node root;
        private final int[] ar;
        private final Heap heap;

        public RangeQueryUpdate(int[] ar) {
            this.ar = ar;
            heap = new Heap(Math.min(MAX_ARRAY_LENGTH, ar.length));
            construct();
        }

        public long query(int L, int R) {
            if (R - L < 2) return 0;
            heap.reset();
            query(root, L, R);
            return getMaxPerimeter();
        }

        private long getMaxPerimeter() {
            long a = heap.poll(), b = heap.poll(), c;
            long val = 0;
            while (!heap.isEmpty()) {
                c = heap.poll();
                if (c < a + b) val = a + b + c;

                a = b;
                b = c;
            }

            return val;
        }

        private void query(Node node, int L, int R) {
            if (node.l == L && node.r == R) {
                for (int e : node.customArray.array)
                    heap.offer(e);

                return;
            }
            int mid = (node.l + node.r) >>> 1;
            if (L > mid)
                query(node.right, L, R);
            else if (R <= mid)
                query(node.left, L, R);
            else {
                query(node.left, L, mid);
                query(node.right, mid + 1, R);
            }
        }

        public void update(int i, int data) {
            if (ar[i] == data)
                return;

            ar[i] = data;
            update(root, i);
        }

        private boolean update(Node root, int i) {
            if (root.l == root.r) {
                root.resetValue();
                return true;
            }

            boolean change = false;
            int mid = (root.l + root.r) >>> 1;
            change = i > mid ? update(root.right, i) : update(root.left, i);
            return change && root.resetValue();
        }

        private void construct() {
            root = new Node(0, ar.length - 1);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(0, mid);
            root.right = construct(mid + 1, ar.length - 1);
            root.resetValue();
        }

        private Node construct(int l, int r) {
            if (l == r)
                return new Node(l, l);

            Node node = new Node(l, r);
            int mid = (l + r) >>> 1;
            node.left = construct(l, mid);
            node.right = construct(mid + 1, r);
            node.resetValue();
            return node;
        }

        final class Node {
            Node left, right;
            int l, r;
            CustomArray customArray;

            void initializeArray(int len) {
                customArray = new CustomArray(new int[Math.min(len, r + 1 - l)]);
                if (customArray.length == 1)
                    customArray.array[0] = ar[l];
            }

            void initializeArray(int[] ar) {
                customArray = new CustomArray(ar);
            }

            Node(int m, int n) {
                l = m;
                r = n;
                initializeArray(MAX_ARRAY_LENGTH);
            }

            boolean resetValue() {
                if (l == r) {
                    customArray.array[0] = ar[l];
                    return true;
                } else
                    return customArray.merge(left.customArray, right.customArray);
            }
        }
    }

    private static final Comparator<CustomArray> CUSTOM_ARRAY_COMPARATOR = (a, b) -> a.length - b.length;

    final static class CustomArray implements Comparable<CustomArray> {
        final int[] array;
        final int length;
        private final static int[] BAKUP_AR = new int[MAX_ARRAY_LENGTH];

        CustomArray(int[] ar) {
            array = ar;
            length = ar.length;
        }

        @Override
        public int compareTo(CustomArray customArray) {
            return length - customArray.length;
        }

        @Override
        public int hashCode() {
            return length;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof CustomArray))
                return false;

            CustomArray customArray = (CustomArray) o;
            return equals(customArray);
        }

        public boolean equals(CustomArray customArray) {
            return Arrays.equals(array, customArray.array);
        }

        boolean replace(int oldValue, int newValue) {
            if (oldValue == newValue) return false;
            int index = search(array, oldValue);

            if (oldValue < newValue) {
                for (int i = index + 1; array[i] < newValue && i < length; i++, index++) {
                    array[index] = array[i];
                }
            } else {
                for (int i = index - 1; array[i] > newValue && i >= 0; i--, index--) {
                    array[index] = array[i];
                }
            }

            array[index] = newValue;
            return true;
        }

        boolean merge(CustomArray a, CustomArray b) {
            backup();
            int ai = 0, bi = 0, index = 0;
            boolean change = false;
            while (ai < a.length && bi < b.length && index < length) {
                array[index++] = a.array[ai] < b.array[bi] ? b.array[bi++] : a.array[ai++];
            }

            while (index < length && ai < a.length) {
                array[index++] = a.array[ai++];
            }

            while (index < length && bi < b.length) {
                array[index++] = b.array[bi++];
            }

            return checkDiff();
        }

        void backup() {
            for (int i = 0; i < length; i++)
                BAKUP_AR[i] = array[i];
        }

        boolean checkDiff() {
            for (int i = 0; i < length; i++)
                if (array[i] != BAKUP_AR[i])
                    return true;

            return false;
        }
    }

    /**
     * This class is to support heap (and also priority queues) related functions.
     *
     * @author Ashok Rajpurohit (ashok1113@gmail.com)
     */
    final static class Heap {
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

        public boolean isEmpty() {
            return count == 0;
        }

        public int size() {
            return count;
        }

        /**
         * If the heap is already full then this will update the already existing
         * top element if necessary.
         *
         * @param e
         * @return
         */
        public boolean offer(int e) {
            if (e == 0)
                return false;

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
            if (compare(e, heap[0]) <= 0)
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
                if (compare(e, val) <= 0)
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
                if (compare(val, e) <= 0)
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

            return compare(heap[c1], heap[c2]) <= 0 ? c1 : c2;
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        private int compare(int a, int b) {
            return a - b;
        }

        public void reset() {
            count = 0;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}