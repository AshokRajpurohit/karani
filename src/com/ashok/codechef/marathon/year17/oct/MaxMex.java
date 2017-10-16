/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.oct;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Max Mex
 * Link: https://www.codechef.com/OCT17/problems/MEX
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class MaxMex {
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
            int n = in.readInt(), k = in.readInt();
            out.println(process(in.readIntArray(n), k));
        }
    }

    private static int process(int[] multiset, int k) {
        int[] set = toSet(multiset);
        int res = k; // when 0 to k - 1 elements are missing from set.
        for (int i = 0; i < set.length; i++) {
            int missing = set[i] - i;
            if (missing > k) // never going to catch the missing element.
                return res;

            res = Math.max(res, set[i] - missing + k + 1);
        }

        return res;
    }

    private static int[] toSet(int[] ar) {
        boolean[] map = toMap(ar);
        int[] set = new int[count(map, true)];
        int index = 0;

        for (int i = 0; i < map.length; i++)
            if (map[i])
                set[index++] = i;

        return set;
    }

    private static boolean[] toMap(int[] ar) {
        int max = getMax(ar);
        boolean[] map = new boolean[max + 1];
        for (int e : ar)
            map[e] = true;

        return map;
    }

    private static int count(boolean[] ar, boolean value) {
        int count = 0;
        for (boolean b : ar)
            if (b == value)
                count++;

        return count;
    }

    private static int getMax(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(e, max);

        return max;
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