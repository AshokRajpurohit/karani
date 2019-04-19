/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.mathematics;

import java.io.*;

/**
 * Problem Name: Die Hard 3
 * Link: https://www.hackerrank.com/challenges/die-hard-3/problem
 * <p>
 * You are given two jugs of capacity <i><b>a</b></i> and <i><b>b</b></i> litre, and infinte supply
 * of water. Tell whether it is possible to measure <i><b>c</b></i> litre using two jugs.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class DieHard3 {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.readInt(), in.readInt(), in.readInt()) ? "YES" : "NO");
        }

        out.flush();
    }

    private static boolean process(int a, int b, int c) {
        if (a < b)
            return process(b, a, c);

        if (c % gcd(a, b) != 0)
            return false;

        int min = Math.min(a % b, b - (a % b));
        if (c < min || c > a)
            return false;

        if (c == a || c % b == 0)
            return true;

        if (c % b == a % b)
            return true;

        if (c % b == b - (a % b))
            return true;

        int del = a % b;


        return true;
    }

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader(InputStream input) {
            in = input;
        }

        public InputReader() {
            in = System.in;
        }

        public InputReader(String file) throws FileNotFoundException {
            in = new FileInputStream(file);
        }

        public InputReader(File file) throws FileNotFoundException {
            in = new FileInputStream(file);
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
