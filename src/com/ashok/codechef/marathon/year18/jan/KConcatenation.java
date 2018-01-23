/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.jan;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: K-Concatenation
 * Link: https://www.codechef.com/JAN18/problems/KCON
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class KConcatenation {
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
            int[] ar = in.readIntArray(n);
            out.println(process(ar, k));
        }
    }

    private static long process(int[] ar, int k) {
        if (k == 1) return maxSumSubArray(ar);

        long singleMax = maxSumSubArray(ar);
        long res = maxSumSubArray(concatenate(ar, ar));
        k = singleMax == res ? k - 1 : k - 2;
        long sum = sum(ar);
        return sum > 0 ? res + sum * k : res;
    }

    private static int[] concatenate(int[] ar, int[] br) {
        int[] res = new int[ar.length + br.length];
        System.arraycopy(ar, 0, res, 0, ar.length);
        System.arraycopy(br, 0, res, ar.length, br.length);
        return res;
    }

    private static long maxSumSubArray(int[] ar) {
        if (ar.length == 1)
            return ar[0];

        long maxSoFar = ar[0], maxHere = ar[0];
        for (int i = 1; i < ar.length; i++) {
            if (maxHere < 0)
                maxHere = ar[i];
            else
                maxHere += ar[i];

            maxSoFar = Math.max(maxSoFar, maxHere);
        }

        return maxSoFar;
    }

    private static long sum(int[] ar) {
        long sum = 0;
        for (int e : ar)
            sum += e;

        return sum;
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