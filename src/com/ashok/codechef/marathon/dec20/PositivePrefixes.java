/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.dec20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.stream.IntStream;

/**
 * Problem Name: Positive Prefixes
 * Link: https://www.codechef.com/DEC20A/problems/POSPREFS
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PositivePrefixes {
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
            out.println(process(in.readInt(), in.readInt()));
        }
    }

    private static String process(int n, int k) {
        int diff = n - k;
        int[] ar;
        if (k > diff) {
            ar = permutation(n, diff);
        } else {
            ar = permutation(n, k);
            flip(ar);
        }

        return toString(ar);
    }

    private static int[] permutation(int n, int k) {
        int[] ar = getArray(n);
        int k2 = k << 1;
        for (int i = 0; i < k2; i += 2) {
            ar[i] = -ar[i];
        }

        return ar;
    }

    private static int[] getArray(int n) {
        return IntStream.range(1, n + 1).toArray();
    }

    private static void flip(int[] ar) {
        for (int i = 0; i < ar.length; i++) ar[i] = -ar[i];
    }

    private static String toString(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length);
        for (int e : ar) sb.append(e).append(' ');
        return sb.toString();
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