/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.may20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Binary Land
 * Link: https://www.codechef.com/MAY20A/problems/BINLAND
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BinaryLand {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int MOD = 1000000007;
    private static final char ONE = '1';
    private static final String ADD = "add", REMOVE = "remove", PATH = "path";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        BinaryGrid grid = new BinaryGrid(n);
        StringBuilder sb = new StringBuilder(q);
        while (q > 0) {
            q--;
            String queryType = in.read();
            if (queryType.equals(ADD)) {
                grid.add(in.read());
            } else if (queryType.equals(REMOVE)) {
                grid.remove();
            } else {
                int c = in.readInt() - 1, d = in.readInt() - 1;
                long res = grid.calculate(c, d);
                sb.append(res).append('\n');
            }
        }

        out.print(sb);
    }

    final static class BinaryGrid {
        private final int n;
        private final LinkedList<boolean[]> rows = new LinkedList<>();
        private int zeroRow = -1, oneRow = -1;
        private boolean refresh = false;
        private final long[][] firstLastMap;

        BinaryGrid(final int n) {
            this.n = n;
            firstLastMap = new long[n][n];
            reset();
        }

        private void add(String row) {
            boolean[] newRow = new boolean[n];
            for (int i = 0; i < n; i++) newRow[i] = row.charAt(i) == ONE;
            boolean[] lastRow = rows.isEmpty() ? null : rows.getLast();
            rows.add(newRow);
            if (allFalse(newRow)) zeroRow = rows.size();
            else if (allTrue(newRow)) oneRow = rows.size();

            if (rows.size() > 1 && !refresh) {
                updateMap(lastRow, newRow);
            }
        }

        private void updateMap(boolean[] lastRow, boolean[] newRow) {
            if (refresh) {
                return;
            }
            for (int i = 0; i < n; i++) {
                if (firstLastMap[i][0] == -1) continue;
                firstLastMap[i] = calculate(lastRow, newRow, firstLastMap[i]);
            }
        }

        private void remove() {
            rows.removeFirst();
            reset();
            zeroRow--;
            oneRow--;
            refresh = true;
        }

        private void reset() {
            if (refresh) return;
            for (long[] row : firstLastMap) Arrays.fill(row, -1);
        }

        private void refresh() {
            refresh = false;
            if (rows.size() == 1) {
                reset();
                return;
            }

            for (int i = 0; i < n; i++) calculate(i);
        }

        private void calculate(int c) {
            refresh = false;
            boolean[] prevRow = rows.getFirst();
            long[] ar = new long[n];
            ar[c] = 1;
            int index = -1;
            for (boolean[] row : rows) {
                index++;
                if (index == 0) continue;
                ar = calculate(prevRow, row, ar);
                prevRow = row;
            }

            firstLastMap[c] = ar;
        }

        private long[] calculate(boolean[] prevRow, boolean[] row, long[] ar) {
            long[] br = new long[n];
            for (int i = 0; i < n; i++) {
                if (prevRow[i] == row[i]) br[i] = ar[i];
                if (i > 0 && prevRow[i - 1] == row[i]) br[i] += ar[i - 1];
                if (i < n - 1 && prevRow[i + 1] == row[i]) br[i] += ar[i + 1];
                br[i] %= MOD;
            }

            return br;
        }

        public long calculate(int c, int d) {
            if (rows.getFirst()[c] != rows.getLast()[d]) return 0;
            boolean[] prevRow = rows.getFirst();
            if (prevRow[c] && zeroRow > 0) return 0;
            if (!prevRow[c] && oneRow > 0) return 0;
            if (rows.size() == 1) return c == d ? 1 : 0;
            if (firstLastMap[c][d] == -1) calculate(c);
            return firstLastMap[c][d];
        }

        private boolean allFalse(boolean[] ar) {
            for (boolean b : ar) if (b) return false;
            return true;
        }

        private boolean allTrue(boolean[] ar) {
            for (boolean b : ar) if (!b) return false;
            return true;
        }
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
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
            for (; offset < bufferSize; ++offset) {
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
    }
}