/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Sereja and Inversions
 * Link: https://www.codechef.com/FEB17/problems/SEAINVS
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class SerejaAndInversions {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int mod = 1000000007, limit = 100001;
    private static long[] factorials = new long[limit];
    private static int[] ar;

    static {
        factorials[0] = 1;

        for (int i = 1; i < factorials.length; i++)
            factorials[i] = i * factorials[i - 1] % mod;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;

            int n = in.readInt(), m = in.readInt();
            StringBuilder sb = new StringBuilder(m << 3);
            ar = in.readIntArray(n);
            Inversions inversions = new Inversions(ar);

            while (m > 0) {
                m--;

                sb.append(inversions.query(in.readInt() - 1, in.readInt() - 1, in.readInt() - 1, in.readInt() - 1));
                sb.append('\n');
            }

            out.print(sb);
        }
    }

    final static class Inversions {
        int[] permutation;
        SparseTable minSparseTable, maxSparseTable;

        Inversions(int[] ar) {
            permutation = ar;
            minSparseTable = new SparseTable(permutation, MIN_OPERATOR);
            maxSparseTable = new SparseTable(permutation, MAX_OPERATOR);
        }

        long query(int s1, int e1, int s2, int e2) {
            if (s1 == e1)
                return permutation[s1] < permutation[s2] ? 1 : 0;

            int min1 = minSparseTable.query(s1, e1), min2 = minSparseTable.query(s2, e2);
            if (min1 > min2)
                return 0;

            int max1 = maxSparseTable.query(s1, e1), max2 = maxSparseTable.query(s2, e2);
            if (max1 > max2)
                return 0;

            if (max1 < min2)
                return factorials[e1 + 1 - s1];

            long res = 1;
            int[] first = Arrays.copyOfRange(permutation, s1, e1 + 1),
                    second = Arrays.copyOfRange(permutation, s2, e2 + 1);

            Arrays.sort(first);
            Arrays.sort(second);

            int fi = 0, si = 0;
            while (si < first.length) {
                while (fi < first.length && first[fi] < second[si])
                    fi++;

                if (fi <= si)
                    return 0;

                res = res * (fi - si) % mod;
                si++;
            }

            return res;
        }
    }

    final static class SparseTable {
        private int[][] mar;
        private Operator operator;

        public SparseTable(int[] ar, Operator operator) {
            this.operator = operator;
            format(ar);
        }

        public int query(int L, int R) {
            int half = Integer.highestOneBit(R + 1 - L);
            return operator.operation(mar[half][L], mar[half][R + 1 - half]);
        }

        private void format(int[] ar) {
            mar = new int[ar.length + 1][];
            mar[1] = ar;
            int bit = 2;

            while (bit < mar.length) {
                int half = bit >>> 1;
                mar[bit] = new int[ar.length - bit + 1];

                for (int i = 0; i <= ar.length - bit; i++) {
                    mar[bit][i] = operator.operation(mar[half][i], mar[half][i + half]);
                }
                bit <<= 1;
            }
        }
    }

    private static final Operator MIN_OPERATOR = new Operator() {
        @Override
        public int operation(int a, int b) {
            return a > b ? b : a;
        }
    };

    private static final Operator MAX_OPERATOR = new Operator() {
        @Override
        public int operation(int a, int b) {
            return a > b ? a : b;
        }
    };

    interface Operator {
        int operation(int a, int b);
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
