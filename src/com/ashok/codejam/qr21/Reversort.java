/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.qr21;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Reversort {
    private static final PrintWriter out = new PrintWriter(System.out);
    private static final InputReader in = new InputReader();
    private static final String CASE = "Case #";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        for (int i = 1; i <= t; i++) {
            print(i, problem2());
        }
    }

    private static String problem2() throws IOException {
        int n = in.readInt(), c = in.readInt();
        int max = n * (n + 1) / 2 - 1;
        if (max < c || c < n - 1) return "IMPOSSIBLE";
        int[] ar = IntStream.range(1, n + 1).toArray();
        int[] counts = new int[n];
        Arrays.fill(counts, 1);
        c -= n - 1;
        for (int i = 0, j = n; i < n - 1 && c > 0; i++, j--) {
            int diff = j - counts[i];
            diff = Math.min(diff, c);
            counts[i] += diff;
            c -= diff;
        }

        for (int i = n - 2; i >= 0; i--) {
            int j = i + counts[i] - 1;
            if (j == i) continue;
            for (int k = i; k < j; k++, j--) {
                int temp = ar[k];
                ar[k] = ar[j];
                ar[j] = temp;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(ar[0]);
        for (int i = 1; i < n; i++) sb.append(' ').append(ar[i]);
        return sb.toString();
    }

    private static String process() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        int res = 0;
        for (int i = 0; i < n - 1; i++) {
            int j = i;
            while (ar[j] != i + 1) j++;
            res += j + 1 - i;
            for (int k = i; k < j; k++, j--) {
                int temp = ar[k];
                ar[k] = ar[j];
                ar[j] = temp;
            }
        }

        return res + "";
    }

    private static void print(int testNo, String result) {
        out.println(String.join(" ", CASE + testNo + ":", result));
    }

    private static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public boolean hasNext() throws IOException {
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            return bufferSize != -1;
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
