/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Yoda
 * Link: https://www.codechef.com/FEB17/problems/CHEFYODA
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndYoda {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final double epsilon = 0.000001;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;

            out.println(process(in.readInt(), in.readInt(), in.readInt(), in.readInt()));
        }
    }

    private static double process(int n, int m, int p, int k) {
        if (p == 0) // Chef is always going to win atleast zero games.
            return 1.0;

        boolean firstWayWin = isEven(n * m), secondWayWin = isDiagonalWayBlocksWin(n, m);
        if (firstWayWin && secondWayWin)
            return 1.0;

        if (!firstWayWin && !secondWayWin)
            return 0.0;

        if (p * 2 == k + 1)
            return 0.5;

        double ncr = 1.0, error = epsilon / k;
        double probability = 1.0 / Math.pow(2, k), quanta = 1.0 / (1 << 20);
        int powers = k;

        for (int i = 1; i <= k - p; i++) {
            ncr = ncr * (k + 1 - i) / i;

            while (ncr > error && powers > 0) {
                if (powers >= 20) {
                    powers -= 20;
                    ncr *= quanta;
                } else {
                    ncr /= (1 << powers);
                    powers = 0;
                }
            }

            if (powers == 0)
                probability += ncr;
        }

        return probability;
    }

    private static boolean isDiagonalWayBlocksWin(int n, int m) {
        return isEven(n) && isEven(m);
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    private static boolean isOdd(int n) {
        return !isEven(n);
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
