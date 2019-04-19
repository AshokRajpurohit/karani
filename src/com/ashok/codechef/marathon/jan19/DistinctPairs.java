/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.jan19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Distinct Pairs
 * Link: https://www.codechef.com/JAN19A/problems/DPAIRS
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class DistinctPairs {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        int[] ar = in.readIntArray(n), br = in.readIntArray(m);
        StringBuilder sb = new StringBuilder((n + m) << 2);
        int maxa = getMaxElementIndex(ar), minb = getMinElementIndex(br);
        for (int i = 0; i < n; i++) sb.append(i).append(' ').append(minb).append('\n');
        for (int i = 0; i < minb; i++) sb.append(maxa).append(' ').append(i).append('\n');
        for (int i = minb + 1; i < m; i++) sb.append(maxa).append(' ').append(i).append('\n');
        out.print(sb);
    }

    private static int getMaxElementIndex(int[] ar) {
        int index = 0;
        for (int i = 1; i < ar.length; i++) if (ar[i] > ar[index]) index = i;
        return index;
    }

    private static int getMinElementIndex(int[] ar) {
        int index = 0;
        for (int i = 1; i < ar.length; i++) if (ar[i] < ar[index]) index = i;
        return index;
    }

    private static void validateForDistinct(int[] ar) {
        Arrays.sort(ar);
        for (int i = 1; i < ar.length; i++) if (ar[i] == ar[i - 1]) throw new RuntimeException("Duplicate elements");
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