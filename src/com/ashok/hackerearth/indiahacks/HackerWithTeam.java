/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.indiahacks;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Hacker with Team
 * Link: https://www.hackerearth.com/challenge/competitive/programming-indiahacks-2017/algorithm/hacker-with-hack-function-03dd9bc0/
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class HackerWithTeam {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int READ = 1, UPDATE = 2;
    private int[] numbers;
    private RangeQueryLazy rql;

    public static void main(String[] args) throws IOException {
        HackerWithTeam object = new HackerWithTeam();
        object.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        numbers = in.readIntArray(n);
        populate();
        StringBuilder sb = new StringBuilder(q << 2);
        while (q > 0) {
            q--;
            if (in.readInt() == READ) {
                sb.append(query(in.readInt() - 1, in.readInt() - 1, in.readInt())).append('\n');
            } else {
                update(in.readInt() - 1, in.readInt());
            }
        }

        out.print(sb);
    }

    private void populate() {
        long[] sums = getSumArray(numbers);
        rql = new RangeQueryLazy(sums);
    }

    /**
     * Queries relevent data from {@code rql} for given range and length.
     * <i>rql</i> returns sum of all elements from start to any given index passed.
     * For any <i>index</i> with length {@code len},
     * the answer is sum of all elements from start to <i>index</i> minus
     * sum of all elements from start to <i>index - length - 1</i> as the answer is
     * sum of <i>len</i> elements including itself at <i>index</i>
     *
     * @param from start index, inclusive
     * @param to   end index, inclusive
     * @param len  length of adjacent team members to be included.
     * @return sum of contribution.
     */
    private long query(int from, int to, int len) {
        return rql.query(from, to) - rql.query(from - 1 - len, to - 1 - len);
    }

    private void update(int index, int newValue) {
        int diff = newValue - numbers[index];
        if (diff == 0)
            return;

        numbers[index] = newValue;
        rql.update(index, numbers.length - 1, diff);
    }

    /**
     * Returns sum array where at any given index, stores sum of all elements before it and itself.
     * So res[i] = Sum(ar[k]) for each k, 0 <= k <= i.
     *
     * @param ar
     * @return
     */
    private static long[] getSumArray(int[] ar) {
        int len = ar.length;
        long[] res = new long[len];
        res[0] = ar[0];
        for (int i = 1; i < len; i++)
            res[i] = res[i - 1] + ar[i];

        return res;
    }

    /**
     * Implementation of Range Query Lazy Updation.
     * This kind of Range query is useful when the update is for an index range.
     * This is to find the minimum(here sum) element in the range and the update function is
     * incrementing all the elements by parameter in the range.
     * If the upate is always for single element then use {@link RangeQueryUpdate}.
     *
     * @author Ashok Rajpurohit ashok1113@gmail.com
     */
    final static class RangeQueryLazy {
        private Node root;

        public RangeQueryLazy(long[] ar) {
            construct(ar);
        }

        public void update(int l, int r, int data) {
            update(root, l, r, data);
        }

        public long query(int L, int R) {
            if (R < 0)
                return 0;

            return query(root, Math.max(L, 0), R);
        }

        /**
         * Updates the node and child nodes if necessary.
         *
         * @param root
         * @param L    start index
         * @param R    end index
         * @param data to be added to each element from index L to index R.
         */
        private static void update(Node root, int L, int R, long data) {
            if (data == 0)
                return;

            if (root.l == L && root.r == R) {
                root.udata += data;
                root.data += data * (R + 1 - L); // add data to all elements.
                return;
            }
            int mid = (root.l + root.r) >>> 1;
            update(root.right, mid + 1, root.r, root.udata);
            update(root.left, root.l, mid, root.udata);
            root.udata = 0;

            if (L > mid) {
                update(root.right, L, R, data);
                root.data = operation(root.left.data, root.right.data);
                return;
            }

            if (R <= mid) {
                update(root.left, L, R, data);
                root.data = operation(root.left.data, root.right.data);
                return;
            }

            update(root.left, L, mid, data);
            update(root.right, mid + 1, R, data);
            root.data = operation(root.left.data, root.right.data);
        }

        private static long query(Node root, int L, int R) {
            if (root.l == L && root.r == R)
                return root.data;

            int mid = (root.l + root.r) >>> 1;
            update(root.right, mid + 1, root.r, root.udata);
            update(root.left, root.l, mid, root.udata);
            root.udata = 0;

            if (L > mid)
                return query(root.right, L, R);

            if (R <= mid)
                return query(root.left, L, R);

            return operation(query(root.left, L, mid),
                    query(root.right, mid + 1, R));

        }

        private void construct(long[] ar) {
            root = new Node(0, ar.length - 1, 0);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
            root.data = operation(root.left.data, root.right.data);
        }

        private Node construct(long[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l, ar[l]);

            Node temp = new Node(l, r, 0);
            int mid = (l + r) >>> 1;
            temp.left = construct(ar, l, mid);
            temp.right = construct(ar, mid + 1, r);
            temp.data = operation(temp.left.data, temp.right.data);
            return temp;
        }

        private static long operation(long a, long b) {
            return a + b;
        }

        private final static class Node {
            Node left, right;
            int l, r;
            long data, udata;
            //        boolean update = false;

            Node(int i, int j, long d) {
                l = i;
                r = j;
                data = d;
            }
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