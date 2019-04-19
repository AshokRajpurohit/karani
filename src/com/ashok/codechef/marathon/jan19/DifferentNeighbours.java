/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.jan19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Different Neighbours
 * Link: https://www.codechef.com/JAN19A/problems/DIFNEIGH
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class DifferentNeighbours {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int[][] BASE_GRID = new int[][]
            {
                    {1, 1, 3, 3},
                    {2, 2, 4, 4},
                    {3, 3, 1, 1},
                    {4, 4, 2, 2}
            };

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

    private static String process(int n, int m) {
        StringBuilder sb = new StringBuilder();
        if (n == 1) {
            if (m == 1) {
                sb.append("1\n1");
                return sb.toString();
            }
            sb.append(Math.min(m - 1, 2)).append('\n');
            singleRow(sb, m);
            sb.deleteCharAt(sb.length() - 1);
        } else if (m == 1) {
            sb.append(Math.min(n - 1, 2)).append('\n');
            singleColumn(sb, n);
            sb.deleteCharAt(sb.length() - 1);
        } else if (n == 2) {
            sb.append(Math.min(m, 3)).append('\n');
            doubleRow(sb, m);
        } else if (m == 2) {
            sb.append(Math.min(n, 3)).append('\n');
            doubleColumn(sb, n);
            sb.deleteCharAt(sb.length() - 1);
        } else prepareGrid(sb, n, m);

        return sb.toString();
    }

    private static void prepareGrid(StringBuilder sb, int rows, int columns) {
        sb.append(4).append('\n');
        int rowLoop = rows >>> 2;
        String row1 = toString(getColumns(BASE_GRID[0], columns));
        String row2 = toString(getColumns(BASE_GRID[1], columns));
        String row3 = toString(getColumns(BASE_GRID[2], columns));
        String row4 = toString(getColumns(BASE_GRID[3], columns));
        while (rowLoop > 0) {
            rowLoop--;
            sb.append(row1).append('\n').append(row2).append('\n').append(row3).append('\n').append(row4).append('\n');
        }

        int remainder = rows & 3;
        if (remainder > 0) sb.append(row1).append('\n');
        if (remainder > 1) sb.append(row2).append('\n');
        if (remainder > 2) sb.append(row3).append('\n');

        sb.deleteCharAt(sb.length() - 1);
    }

    private static String toString(int[] ar) {
        StringBuilder sb = new StringBuilder();
        for (int e : ar) sb.append(e).append(' ');
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    private static int[] getColumns(int[] base, int count) {
        int loopCount = count >>> 2, remainder = count & 3;
        int[] ar = new int[count];
        for (int i = 0, index = 0; i < loopCount; i++, index += 4) System.arraycopy(base, 0, ar, index, 4);
        System.arraycopy(base, 0, ar, count - remainder, remainder);
        return ar;
    }

    private static int max(int[][] ar) {
        int max = 1;
        for (int[] e : ar) max = Math.max(max, max(e));
        return max;
    }

    private static int max(int[] ar) {
        int max = 1;
        for (int e : ar) max = Math.max(max, e);
        return max;
    }

    private static void singleRow(StringBuilder sb, int n) {
        int count = n >>> 2;
        while (count > 0) {
            count--;
            sb.append("1 1 2 2 ");
        }

        count = n & 3;
        if (count == 1) sb.append("1 ");
        else if (count == 2) sb.append("1 1 ");
        else if (count == 3) sb.append("1 1 2 ");
    }

    private static void singleColumn(StringBuilder sb, int n) {
        int count = n >>> 2;
        while (count > 0) {
            count--;
            sb.append("1\n1\n2\n2\n");
        }

        count = n & 3;
        if (count == 0) return;
        if (count == 1) sb.append("1\n");
        else if (count == 2) sb.append("1\n1\n");
        else if (count == 3) sb.append("1\n1\n2\n");
    }

    private static void doubleRow(StringBuilder sb, int n) {
        char[] base = "1 2 3 ".toCharArray();
        String row = getString(base, n);
        sb.append(row).append('\n').append(row).toString();
    }

    private static String getString(char[] ar, int n) {
        StringBuilder sb = new StringBuilder(n + 1);
        int len = ar.length >>> 1, loopCount = n / len;
        while (loopCount > 0) {
            loopCount--;
            sb.append(ar);
        }

        int remainder = n % len;
        char[] remar = new char[remainder << 1];
        System.arraycopy(ar, 0, remar, 0, remainder << 1);
        return sb.append(remar).deleteCharAt(sb.length() - 1).toString();
    }

    private static void doubleColumn(StringBuilder sb, int n) {
        int count = n / 3;
        while (count > 0) {
            count--;
            sb.append("1 1\n2 2\n3 3\n");
        }

        count = n % 3;
        if (count == 0) return;
        if (count == 1) sb.append("1 1\n");
        else if (count == 2) sb.append("1 1\n2 2\n");
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