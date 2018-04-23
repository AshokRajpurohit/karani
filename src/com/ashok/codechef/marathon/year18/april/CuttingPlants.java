/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.april;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem Name: Cutting Plants
 * Link: https://www.codechef.com/APRIL18A/problems/CUTPLANT
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CuttingPlants {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] initialHeights = in.readIntArray(n), finalHeights = in.readIntArray(n);
            out.println(process(initialHeights, finalHeights));
        }
    }

    private static int process(int[] initialHeights, int[] finalHeights) {
        if (checkImpossible(initialHeights, finalHeights))
            return -1;

        if (Arrays.equals(initialHeights, finalHeights)) {
            return 0;
        }

        Garden garden = new Garden(initialHeights, finalHeights);
        return garden.process();
    }

    private static int[] nextSmaller(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;
            while (j < ar.length && ar[j] >= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    /**
     * Returns array of integers having index of next equal element to it.
     * If the element doesn't have any such element then the index for it would
     * be the array size.
     * <p>
     * for array
     * {1, 2, 3, 2, 1}
     * next equal elements indices should be
     * {4, 3, 5, 5, 5}
     * <p>
     * The parameter array doesn't need to be sorted.
     *
     * @param ar
     * @return
     */
    private static int[] nextEqual(int[] ar) {
        int len = ar.length;
        int[] res = new int[len];
        Pair[] pairs = new Pair[len];

        for (int i = 0; i < len; i++)
            pairs[i] = new Pair(i, ar[i]);

        Arrays.sort(pairs, PAIR_COMPARATOR);
        res[pairs[len - 1].index] = ar.length;
        for (int i = len - 2; i >= 0; i--) {
            if (pairs[i].value == pairs[i + 1].value)
                res[pairs[i].index] = pairs[i + 1].index;
            else
                res[pairs[i].index] = len;
        }

        return res;
    }

    /**
     * Returns array of next higher element index.
     * At index i the array contains the index of next higher element.
     * If there is no higher element right to it, than the value is array size.
     *
     * @param ar
     * @return
     */
    private static int[] nextHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;
        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;
            while (j < ar.length && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    private static boolean checkImpossible(int[] source, int[] target) {
        int n = source.length;
        for (int i = 0; i < n; i++)
            if (source[i] < target[i])
                return true;

        return false;
    }

    final static class Garden {
        final int trees;
        final int[] initialHeights, finalHeights;
        final int[] finallyNextEqual, finallyNextLarger;
        final SparseTable table;

        Garden(int[] initialHeights, int[] finalHeights) {
            trees = initialHeights.length;
            this.initialHeights = initialHeights;
            this.finalHeights = finalHeights;
            finallyNextEqual = nextEqual(finalHeights);
            finallyNextLarger = nextHigher(finalHeights);
            table = new SparseTable(initialHeights);
        }

        private int process() {
            boolean[] map = new boolean[trees];
            int count = 0;
            for (int i = 0; i < trees; i++) {
                if (map[i] || initialHeights[i] == finalHeights[i]) continue;
                count++;
                map[i] = true;
                int j = i, k = j, limit = finallyNextLarger[j];
                while (k < limit) {
                    if (table.query(j, k) < finalHeights[i]) // not possible for kth tree.
                        break;

                    map[k] = true;
                    k = finallyNextEqual[k];
                }
            }

            return count;
        }
    }

    private static final Comparator<Pair> PAIR_COMPARATOR = (a, b) -> a.value == b.value ? a.index - b.index : a.value - b.value;

    final static class Pair {
        int index;
        int value;

        Pair(int i, int v) {
            index = i;
            value = v;
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

    /**
     * Implementation of Range Query Data Structure using an array of arrays.
     * Preprocessing complexity is order of n * long(n) and quering complexity is
     * order of 1 (constant time). This is useful when the range of the query can
     * be anything from 1 to n. If the min range width is fixed,
     * {@link RangeQueryBlock} is the better option for range query.
     * For two dimensional arrays use {@link Quadtree} or {@link SparseTable2D}
     *
     * @author Ashok Rajpurohit ashok1113@gmail.com
     */
    final static class SparseTable {
        private int[][] mar;

        public SparseTable(int[] ar) {
            format(ar);
        }

        public int query(int L, int R) {
            int half = Integer.highestOneBit(R + 1 - L);
            return operation(mar[half][L], mar[half][R + 1 - half]);
        }

        private void format(int[] ar) {
            mar = new int[ar.length + 1][];
            mar[1] = ar;
            int bit = 2;
            while (bit < mar.length) {
                int half = bit >>> 1;
                mar[bit] = new int[ar.length - bit + 1];
                for (int i = 0; i <= ar.length - bit; i++) {
                    mar[bit][i] = operation(mar[half][i], mar[half][i + half]);
                }
                bit <<= 1;
            }
        }

        /**
         * Single operation for query types.
         *
         * @param a
         * @param b
         * @return
         */
        public int operation(int a, int b) {
            return Math.min(a, b);
        }
    }
}