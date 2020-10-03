/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.sept2020;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chefina and Swap
 * Link: https://www.codechef.com/SEPT20A/problems/CHFNSWAP
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefinaAndSwap {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final double sqrt2 = Math.sqrt(2);

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            int n = in.readInt();
            sb.append(process(n)).append('\n');
        }

        out.print(sb);
    }

    private static long process(int n) {
        if (n == 3) return 2;
        int nMod4 = n & 3;
        if (nMod4 == 1 || nMod4 == 2) return 0;
        int m = 2 + (int) (n / sqrt2);
        m = Math.min(n - 1, m);
        long totalSwaps = 0;
        while (m > 0) {
            long swaps = calculateSwaps(m, n);
            if (swaps == -1) break;
            totalSwaps += swaps;
            m--;
        }

        return totalSwaps;
    }

    private static long calculateSwaps(int m, int n) {
        long first = sumOfNums(m), second = sumOfNums(n) - first;
        long diff = second - first, d = diff >>> 1;
        if (diff == 0) {
            long mc2 = sumOfNums(m - 1), nmc2 = sumOfNums(n - m - 1);
            return mc2 + nmc2;
        }
        if (diff < 0) return 0;
        if (d > n - 1) return -1;
        long start = Math.max(1, m + 1 - d), end = Math.min(m, n - d);
        return end + 1 - start;
    }

    private static long sumOfNums(long n) {
        return (n * (n + 1)) >>> 1;
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
    }
}