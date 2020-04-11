/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.cj20.r1A;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Problem Name: Square Dance
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SquareDance {
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
            print(i, process());
        }
    }

    private static String process() throws IOException {
        int r = in.readInt(), c = in.readInt();
        int[][] dancers = in.readIntTable(r, c);
        return "" + process(dancers);
    }

    private static long process(int[][] dancers) {
        long score = score(dancers);
        long totalScore = score;
        while (true) {
            performRound(dancers);
            long roundScore = score(dancers);
            if (roundScore == score) break;
            score = roundScore;
            totalScore += score;
        }

        return totalScore;
    }

    private static void performRound(int[][] dancers) {
        int r = dancers.length, c = dancers[0].length;
        long[][] avgs = new long[r][c];
        IntStream.range(0, r).forEach(i -> {
            long sum = 0, val = dancers[i][0];
            avgs[i][0] += val;
            for (int j = 1; j < c; j++) {
                if (val == 0) val = dancers[i][j];
                avgs[i][j] += val;
                if (dancers[i][j] != 0) val = dancers[i][j];
            }

            val = dancers[i][c - 1];
            avgs[i][c - 1] += val;
            for (int j = c - 2; j >= 0; j--) {
                if (val == 0) val = dancers[i][j];
                avgs[i][j] += val;
                if (dancers[i][j] != 0) val = dancers[i][j];
            }
        });

        IntStream.range(0, c).forEach(j -> {
            long sum = 0, val = dancers[0][j];
            avgs[0][j] += val;
            for (int i = 1; i < r; i++) {
                if (val == 0) val = dancers[i][j];
                avgs[i][j] += val;
                if (dancers[i][j] != 0) val = dancers[i][j];
            }

            val = dancers[r - 1][j];
            avgs[r - 1][j] += val;
            for (int i = r - 2; i >= 0; i--) {
                if (val == 0) val = dancers[i][j];
                avgs[i][j] += val;
                if (dancers[i][j] != 0) val = dancers[i][j];
            }
        });

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if ((dancers[i][j] << 2) < avgs[i][j]) dancers[i][j] = 0;
            }
        }
    }

    private static long score(int[][] dancers) {
        return Arrays.stream(dancers).flatMap(row -> Arrays.stream(row).mapToObj(v -> v)).mapToLong(v -> v).sum();
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

        public int[][] readIntTable(int m, int n) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = readIntArray(n);

            return res;
        }
    }
}
