/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.june;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Chef and the Feast
 * Link: https://www.codechef.com/JUNE17/problems/NEO01
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndFeast {
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
            int[] dishes = in.readIntArray(n);
            out.println(process(dishes));
        }
    }

    private static long process(int[] dishes) {
        if (min(dishes) >= 0) // oh, all are positive elements, let's group them together.
            return sum(dishes) * dishes.length;

        if (max(dishes) <= 0) // no point in grouping negative elements.
            return sum(dishes);

        Arrays.sort(dishes);
        reverse(dishes);
        long[] forward = getSumArray(dishes);
        long sum = sum(dishes), max = sum;

        for (int i = 0; i < dishes.length; i++)
            max = Math.max(max, forward[i] * i + sum);

        return max;
    }

    private static int min(int[] ar) {
        int min = Integer.MAX_VALUE;

        for (int e : ar)
            min = Math.min(min, e);

        return min;
    }

    private static int max(int[] ar) {
        int max = Integer.MIN_VALUE;

        for (int e : ar)
            max = Math.max(max, e);

        return max;
    }

    private static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    private static long sum(int[] ar) {
        return sum(ar, 0, ar.length - 1);
    }

    private static long sum(int[] ar, int start, int end) {
        if (start > end)
            return 0;

        long res = 0;

        for (int i = start; i <= end; i++)
            res += ar[i];

        return res;
    }

    private static long[] getSumArray(int[] ar) {
        long[] res = new long[ar.length];
        res[0] = ar[0];

        for (int i = 1; i < ar.length; i++)
            res[i] = res[i - 1] + ar[i];

        return res;
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