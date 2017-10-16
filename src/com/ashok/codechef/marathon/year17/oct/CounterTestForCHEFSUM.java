/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.oct;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Counter Test For CHEFSUM
 * Link: https://www.codechef.com/OCT17/problems/CHEFCOUN
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CounterTestForCHEFSUM {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final long LIMIT = (1L << 32) - 1;
//    private static final long LIMIT = (1L << 4) - 1; // for testing the solution.

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder();
        while (t > 0) {
            t--;

            append(sb, process(in.readInt()));
        }

        out.print(sb);
    }

    private static long[] process(int n) {
        long[] ar = new long[n];
        // pref sum is ar[i] + sum or (n + 1) * value which has to be smaller than LIMIT for smallest value.
        long val = LIMIT / (n + 1);
        Arrays.fill(ar, val);
        long remaining = LIMIT % (n + 1);
        increment(ar, remaining);
        return ar;
    }

    /**
     * One of these element give the minimum {@code CHEFSUM} for incorrect code.
     *
     * @param ar
     * @param value
     */
    private static void increment(long[] ar, long value) {
        if (value == 0) {
            ar[ar.length - 1]++;
            ar[0]--;
            return;
        }

        int index = ar.length - 1;
        while (value > 0) {
            ar[index--]++;
            value--;
        }

        if (index < 0) {
            ar[ar.length - 1]++;
            ar[0]--;
        }
    }

    private static void append(StringBuilder sb, long[] ar) {
        for (long e : ar)
            sb.append(e).append(' ');

        sb.append('\n');
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