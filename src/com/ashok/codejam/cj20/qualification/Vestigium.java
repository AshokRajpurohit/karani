/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.cj20.qualification;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.IntStream;

/**
 * Problem Name: Vestigium
 * Link: Code Jam 2020 Qualifying Round
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class Vestigium {
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
        int n = in.readInt();
        int[][] matrix = in.readIntTable(n, n);
        int trace = getTrace(matrix);
        int dupEntryRowCount = getDuplicateEntryRows(matrix);
        int dupEntryColCount = getDuplicateEntryCols(matrix);
        return new StringJoiner(" ")
                .add(String.valueOf(trace))
                .add(String.valueOf(dupEntryRowCount))
                .add(String.valueOf(dupEntryColCount))
                .toString();
    }

    private static int getDuplicateEntryCols(int[][] matrix) {
        int n = matrix.length;
        return (int) IntStream.range(0, n)
                .filter(c -> hasDuplicates(
                        IntStream.range(0, n).map(r -> matrix[r][c]).toArray()))
                .count();
    }

    private static int getDuplicateEntryRows(int[][] matrix) {
        return (int) Arrays.stream(matrix)
                .filter(row -> hasDuplicates(row))
                .count();
    }

    private static boolean hasDuplicates(int[] row) {
        int n = row.length;
        boolean[] map = new boolean[n + 1];
        Arrays.stream(row).forEach(v -> map[v] = true);
        return IntStream.range(1, n + 1).filter(i -> !map[i]).count() != 0;
    }

    private static int getTrace(int[][] matrix) {
        return IntStream.range(0, matrix.length)
                .map(i -> matrix[i][i])
                .sum();
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
