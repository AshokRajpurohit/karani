/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.dec;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Total Diamonds
 * Link: https://www.codechef.com/DEC17/problems/VK18
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class TotalDiamonds {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] map, basic = new int[]{0, -1, 2, -3, 4, -5, 6, -7, 8, -9};
    private static long[] sumMap;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        int[] pairs = in.readIntArray(t);
        long[] res = process(pairs);
        StringBuilder sb = new StringBuilder(t << 2);

        for (long value : res)
            sb.append(value).append('\n');

        out.print(sb);
    }

    private static long[] process(int[] pairs) {
        int len = pairs.length;
        long max = max(pairs);
        populate((int) max);
        long[] res = new long[len];
        for (int i = 0; i < len; i++) {
            int grid = pairs[i];
            res[i] = sumMap[grid];
        }

        return res;
    }

    private static int max(int[] ar) {
        int maxValue = ar[0];
        for (int e : ar)
            maxValue = Math.max(maxValue, e);

        return maxValue;
    }

    private static void populate(final int value) {
        int limit = Math.max(value * 2, 9);
        map = new int[limit + 1];
        System.arraycopy(basic, 0, map, 0, 10);
        for (int i = 10; i <= limit; i++)
            map[i] = map[i / 10] + map[i % 10];

        for (int i = 1; i <= limit; i++)
            map[i] = Math.abs(map[i]);

        for (int i = 1; i <= limit; i++)
            map[i] = map[i - 1] + map[i];

        sumMap = new long[value + 1];
        for (int i = 1; i <= value; i++) {
            int v = query(i + 1, i << 1);
            sumMap[i] = sumMap[i - 1] + (v << 1) - query(i << 1, i << 1);
        }
    }

    private static int query(int start, int end) {
        return map[end] - map[start - 1];
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
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