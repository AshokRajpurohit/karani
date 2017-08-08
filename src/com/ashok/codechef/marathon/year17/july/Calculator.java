/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Calculator
 * Link: https://www.codechef.com/JULY17/problems/CALC
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class Calculator {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static char GREATER = '>', SMALLER = '<';

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 10);
        while (t > 0) {
            t--;
            sb.append(process(in.readInt(), in.readInt())).append('\n');
        }

        out.print(sb);
    }

    /**
     * Computes the maximum number which can be obtained on calculator using at max {@code N} amount
     * of energy.
     * {@code a} and {@code b} are the count of first and second button pressings.
     * So the following equation should hold true.
     * a * 1 + b * B <= N
     * <p>
     * Number on the screen S = a * b;
     * from the previous equation, we know that
     * a = N - bB (bB is equaivalent to b * B).
     * <p>
     * S = b (N - bB).
     * We know from calculus, for max value of S, b = N / (2B).
     *
     * @param N
     * @param B
     * @return
     */
    private static long process(int N, int B) {
        int b = (N >>> 1) / B;
        return Math.max(getCalculatorNumber(b, N, B), getCalculatorNumber(b + 1, N, B));
    }

    private static long getCalculatorNumber(long b, int N, int B) {
        return b * (N - b * B);
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