/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.march;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Easy Problem
 * Link: https://www.codechef.com/MARCH18A/problems/XXOR
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndEasyProblem {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] ar = in.readIntArray(n);
        QueryProcessor processor = new QueryProcessor(ar);
        StringBuilder sb = new StringBuilder(q << 2);
        while (q > 0) {
            q--;
            sb.append(processor.query(in.readInt() - 1, in.readInt() - 1)).append('\n');
        }

        out.print(sb);
    }

    private static void toSumArray(int[] ar) {
        int len = ar.length;
        for (int i = 1; i < len; i++)
            ar[i] += ar[i - 1];
    }

    private final static class QueryProcessor {
        final static int BITS = 31, MAX_NUM = (1 << BITS) - 1;
        final int[][] bitCountSumMap = new int[BITS][];

        private QueryProcessor(int[] ar) {
            initialize(ar.length);
            populate(ar);
        }

        private void initialize(int n) {
            for (int i = 0; i < BITS; i++)
                bitCountSumMap[i] = new int[n];
        }

        private void populate(int[] ar) {
            int xor = 1, len = ar.length;
            for (int i = 0; i < BITS; i++) {
                int[] map = bitCountSumMap[i];
                int counts = 0;
                for (int j = 0; j < len; j++) {
                    counts += (xor & ar[j]) == 0 ? 0 : 1;
                    map[j] = counts;
                }

                xor = xor << 1;
            }
        }

        public int query(int from, int to) {
            int val = 0, bit = 1, len = to + 1 - from;
            for (int[] map : bitCountSumMap) {
                int count = query(map, from, to);
                val ^= count >= len - count ? 0 : bit;
                bit <<= 1;
            }

            return val;
        }

        private static int query(int[] map, int from, int to) {
            return query(map, to) - query(map, from - 1);
        }

        private static int query(int[] map, int index) {
            return index < 0 ? 0 : map[index];
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