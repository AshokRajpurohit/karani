/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.Contest.oct18;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Mojtaba and Construction camp I
 * Link: https://www.hackerearth.com/problem/algorithm/persig-0e434759/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MojtabaAndConstructionCamp {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int LIMIT = 5000000;
    private static long[] factorials = new long[LIMIT + 1];

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void initialize(int m) {
        factorials[1] = factorials[0] = 1;
        for (int i = 2; i <= LIMIT; i++)
            factorials[i] = factorials[i - 1] * i % m;

    }

    private static void solve() throws IOException {
        int t = in.readInt(), m = in.readInt();
        initialize(m);
        StringBuilder sb = new StringBuilder(t << 2);
        while (t > 0) {
            t--;
            sb.append(process(in.readInt(), m)).append(' ');
        }

        out.print(sb);
    }

    private static long process(int n, int m) {
        if (n < 3 || m == 1)
            return 0;

        long c = factorials[n - 1];
        c = c * (c - 1) / 2;
        long res = (1L * n * n % m) * (c % m) % m;
        return res < 0 ? res + m : res;
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
