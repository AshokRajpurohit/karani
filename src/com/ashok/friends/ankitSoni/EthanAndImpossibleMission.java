/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankitSoni;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name:
 * Link:
 *
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class EthanAndImpossibleMission {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static long[] left, right;
    private static int mod;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        mod = in.readInt();

        int[] ar = in.readIntArray(n);
        populate(ar);

        StringBuilder sb = new StringBuilder(q << 3);
        while (q > 0) {
            q--;

            sb.append(query(in.readInt() - 1)).append('\n');
        }

        out.print(sb);
    }

    private static long query(int index) {
        return queryLeft(index - 1) * queryRight(index + 1) % mod;
    }

    private static void populate(int[] ar) {
        left = new long[ar.length];
        right = new long[ar.length];

        left[0] = ar[0] % mod;
        for (int i = 1; i < ar.length; i++)
            left[i] = left[i - 1] * ar[i] % mod;

        right[ar.length - 1] = ar[ar.length - 1] % mod;
        for (int i = ar.length - 2; i >= 0; i--)
            right[i] = right[i + 1] * ar[i] % mod;
    }

    private static long queryLeft(int index) {
        if (index < 0)
            return 1;

        return left[index];
    }

    private static long queryRight(int index) {
        if (index >= right.length)
            return 1;

        return right[index];
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
