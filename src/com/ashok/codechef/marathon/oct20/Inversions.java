/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.oct20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Inversions
 * Link: https://www.codechef.com/OCT20A/problems/INVSMOD2
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class Inversions {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int LIMIT = 2001;
    private static long[][] map = new long[LIMIT][LIMIT];

    static {
        for (long[] row : map) Arrays.fill(row, -1);
        for (int i = 0; i < LIMIT; i++) map[i][0] = 1;
        for (int i = 1; i < LIMIT; i++) map[1][i] = 0;
        for (int i = 2; i < 100; i++) {
            for (int j = 1; j < 100; j++) {
                map[i][j] = process1(i, j);
            }
        }

        StringBuilder sb = new StringBuilder();
        out.println(sb);
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
            long n = in.readLong(), k = in.readLong();
            out.println(process(n, k) & 1);
        }
    }

    private static long process(long n, long k) {
        if (k == 0) return 1;
        if (k < 0) return 0;
        if (n < 2) return 0;
        if (k == 1) return (n - 1) & 1;
        int ni = (int) n, ki = (int) k;
        if (n >= LIMIT) return process1(n, k);
        if (map[ni][ki] != -1) return map[ni][ki];
        long res = process1(ni, ki);
        map[ni][ki] = res & 1;
        return map[ni][ki];
    }

    private static long process1(long n, long k) {
        if (k < 0 || n < 0) return 0;
        if (k == 0) return 1;
        if (n < 2) return 0;
        if (k == 1) return (n - 1);
        int ni = (int) n, ki = (int) k;
        if (n < LIMIT && k < LIMIT && map[ni][ki] != -1) return map[ni][ki];
        long res = process1(n - 1, k) + process1(n, k - 1) - process1(n - 1, k - n);
        if (n >= LIMIT || k >= LIMIT) return res & 1;
        map[ni][ki] = res;
        return map[ni][ki];
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }
    }
}