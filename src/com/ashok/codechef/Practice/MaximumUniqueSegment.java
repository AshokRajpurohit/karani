/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.Practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Maximum Unique Segment
 * Link: https://www.codechef.com/LTIME49/problems/MAXSEGM
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class MaximumUniqueSegment {
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
            out.println(process(in.readIntArray(n), in.readIntArray(n)));
        }
    }

    private static long process(int[] costs, int[] weights) {
        int[] prev = previousEqual(costs);
        long[] sum = getSumArray(weights);
        int left = 0, right = 0, len = costs.length;
        long max = sum[0];
        while (right < len) {
            left = Math.max(left, prev[right] + 1);
            max = Math.max(max, sum[right] - sum[left] + weights[left]);
            right++;
        }

        return max;
    }

    private static int[] previousEqual(int[] ar) {
        int len = ar.length;
        int[] map = new int[len], res = new int[len];
        Arrays.fill(map, -1);

        for (int i = 0; i < len; i++) {
            res[i] = map[ar[i]];
            map[ar[i]] = i;
        }

        return res;
    }

    private static long[] getSumArray(int[] ar) {
        int len = ar.length;
        long[] sum = new long[len];
        sum[0] = ar[0];

        for (int i = 1; i < len; i++)
            sum[i] = sum[i - 1] + ar[i];

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