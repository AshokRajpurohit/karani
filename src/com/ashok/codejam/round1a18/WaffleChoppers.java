/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.round1a18;

import com.ashok.lang.dsa.Random;
import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Waffle Choppers
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class WaffleChoppers {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final char CHOCOLATE_CHAR = '@';

    public static void main(String[] args) throws IOException {
//        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        for (int i = 1; i <= t; i++) {
            int r = in.readInt(), c = in.readInt(), h = in.readInt(), v = in.readInt();
            String[] grid = in.readStringArray(r, c);
            boolean result = process(grid, h, v);
            out.println("Case #" + i + ": " + (result ? "POSSIBLE" : "IMPOSSIBLE"));
            out.flush();
        }
    }

    private static void play() throws IOException {
        char[] ops = new char[] {'.', '@'};
        RandomStrings random = new RandomStrings();
        while (true) {
            int limit = in.readInt();
            while (true) {
                int r = random.nextInt(limit) + 2, c = random.nextInt(limit) + 2, h = random.nextInt(limit) + 1, v = random.nextInt(limit) + 1;
                String[] grid = new String[r];
                for (int i = 0; i < r; i++)
                    grid[i] = Generators.generateRandomExpression(c, ops);

                process(grid, h, v);
                out.println("Success");
                out.flush();
            }
        }
    }

    private static boolean process(String[] grid, int h, int v) {
        char[][] cells = toCells(grid);
        int[] rowSums = getRowSums(cells), colSums = getColumnSums(cells);
        int sum = sum(rowSums);
        if (sum == 0)
            return true;

        if (sum % ((h + 1) * (v + 1)) != 0)
            return false;

        if (sum == rowSums.length * colSums.length)
            return true;

        int[] horizontalCuts = getCuts(rowSums, sum, h), verticalCuts = getCuts(colSums, sum, v);
        if (count(horizontalCuts, -1) != 0 || count(verticalCuts, -1) != 0)
            return false;

        return validate(cells, horizontalCuts, verticalCuts, sum / ((h + 1) * (v + 1)));
    }

    private static boolean validate(char[][] cells, int[] hCuts, int[] vCuts, int value) {
        int hstart = 0;
        for (int cut : hCuts) {
            int vstart = 0;
            for (int vc : vCuts) {
                if (count(cells, hstart, cut, vstart, vc) != value)
                    return false;

                vstart = vc + 1;
            }

            hstart = cut + 1;
        }

        return true;
    }

    private static int count(char[][] cells, int rs, int re, int cs, int ce) {
        int count = 0;
        for (int r = rs; r <= re; r++) {
            for (int c = cs; c <= ce; c++) {
                if (cells[r][c] == CHOCOLATE_CHAR)
                    count++;
            }
        }

        return count;
    }

    private static int[] getCuts(int[] ar, int sum, int cuts) {
        int cutValue = sum / (cuts + 1);
        int start = 0, end = 0, chips = ar[0], len = ar.length, index = 0;
        int[] cutAr = new int[cuts + 1];
        Arrays.fill(cutAr, -1);
        if (sum % (cuts + 1) != 0)
            return cutAr;
        while (start < len) {
            chips = 0;
            end = start;
            while (end < len && chips < cutValue) {
                chips += ar[end];
                end++;
            }

            end--;
            if (chips != cutValue)
                return cutAr;

            cutAr[index++] = end;
            start = end + 1;
        }

        return cutAr;
    }

    private static int sum(int[] ar) {
        int sum = 0;
        for (int e : ar)
            sum += e;

        return sum;
    }

    private static int[] getColumnSums(char[][] cells) {
        int r = cells.length, c = cells[0].length;
        int[] columnSums = new int[c];
        int index = 0;
        for (char[] row : cells) {
            index = 0;
            for (char ch : row) {
                if (ch == CHOCOLATE_CHAR)
                    columnSums[index]++;

                index++;
            }
        }

        return columnSums;
    }

    private static int[] getRowSums(char[][] cells) {
        int n = cells.length;
        int[] rowSums = new int[n];
        int index = 0;
        for (char[] row : cells) {
            rowSums[index++] = getCount(row, CHOCOLATE_CHAR);
        }

        return rowSums;
    }

    private static int getCount(char[] ar, char ch) {
        int count = 0;
        for (char c : ar) {
            if (c == ch)
                count++;
        }

        return count;
    }

    private static int count(int[] ar, int v) {
        int count = 0;
        for (int e : ar)
            if (v == e) count++;

        return count;
    }

    private static char[][] toCells(String[] grid) {
        int n = grid.length;
        char[][] cells = new char[n][];
        for (int i = 0; i < n; i++)
            cells[i] = grid[i].toCharArray();

        return cells;
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String[] readStringArray(int size, int len) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = read(len);

            return res;
        }
    }
}