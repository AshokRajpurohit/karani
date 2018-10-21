/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.sept;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Table Game
 * Link: https://www.codechef.com/SEPT18A/problems/TABGAME
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class TableGame {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        test();
//        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            String col = in.read(), row = in.read();
            TableMatrix matrix = new TableMatrix(row, col);
            int q = in.readInt();
            char[] ar = new char[q];
            for (int i = 0; i < q; i++)
                ar[i] = matrix.query(in.readInt(), in.readInt());

            out.println(new String(ar));
        }
    }

    /**
     * Compare the code with brute-force version.
     *
     * @throws IOException
     */
    private static void play() throws IOException {
        Output output = new Output();
        while (true) {
            output.println("Enter values");
            output.flush();
            int n = in.readInt(), m = in.readInt();
            RandomStrings strings = new RandomStrings();
            String row = strings.nextBinaryString(n), col = strings.nextBinaryString(m);
            TableMatrix matrix = new TableMatrix(row, col);
            BruteForceTableMatrix brute = new BruteForceTableMatrix(row, col);
            int q = in.readInt();
            int[] rows = Generators.generateRandomIntegerArray(q, 1, n),
                    cols = Generators.generateRandomIntegerArray(q, 1, m);

            for (int i = 0; i < q; i++) {
                char actual = 'a', expected = 'e';
                try {
                    actual = matrix.query(rows[i], cols[i]);
                    expected = brute.query(rows[i], cols[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (actual != expected) {
                        output.println(col);
                        output.println(row);
                        output.println(rows[i] + " " + cols[i]);
                        output.println("Expected output: " + expected + ", Actual: " + actual);
                        output.print(brute.matrix);
                        output.flush();
                        break;
                    }
                }

                if (actual != expected) {
                    output.println(col);
                    output.println(row);
                    output.println(rows[i] + " " + cols[i]);
                    output.println("Expected output: " + expected + ", Actual: " + actual);
                    output.print(brute.matrix);
                    output.flush();
                    actual = matrix.query(rows[i], cols[i]);
                    expected = brute.query(rows[i], cols[i]);

                    break;
                }
            }
        }
    }

    /**
     * Test to check for any exception thrown by code.
     *
     * @throws IOException
     */
    private static void test() throws IOException {
        Output output = new Output();
        while (true) {
            output.println("Enter values for n and m");
            output.flush();
            int n = in.readInt(), m = in.readInt();
            RandomStrings randomStrings = new RandomStrings();
            while (true) {
                int rowSize = randomStrings.nextInt(n) + 1, columnSize = randomStrings.nextInt(m) + 1;
                String row = randomStrings.nextBinaryString(rowSize), col = randomStrings.nextBinaryString(columnSize);
                int q = randomStrings.nextInt(rowSize * columnSize) + 1;
                int[] rows = Generators.generateRandomIntegerArray(q, 1, rowSize),
                        cols = Generators.generateRandomIntegerArray(q, 1, columnSize);

                TableMatrix matrix = new TableMatrix(row, col);
                for (int i = 0; i < q; i++)
                    matrix.query(rows[i], cols[i]);
            }
        }
    }

    private static class TableMatrix {
        static final char ZERO = '0', ONE = '1';
        static final char[] BITS = new char[]{ZERO, ONE};
        protected final int[] row, col;
        protected final int[] frow, fcol;
        protected final int[] srow, scol;
        final char ORIGIN;
        final int rlen, clen;

        TableMatrix(String r, String c) {
            row = toArray(r);
            col = toArray(c);
            ORIGIN = BITS[1 ^ (row[0] & col[0])];
            rlen = row.length;
            clen = col.length;
            frow = new int[rlen + 1];
            fcol = new int[clen + 1];
            srow = new int[rlen + 1];
            scol = new int[clen + 1];
            populate();
        }

        private void populate() {
            fcol[1] = frow[1] = toInt(ORIGIN);

            for (int i = 2; i <= rlen; i++) frow[i] = 1 ^ (row[i - 1] & frow[i - 1]);
            for (int i = 2; i <= clen; i++) fcol[i] = 1 ^ (col[i - 1] & fcol[i - 1]);

            if (rlen == 1 || clen == 1) return;

            scol[2] = srow[2] = 1 ^ (frow[2] & fcol[2]);

            for (int i = 3; i <= rlen; i++) srow[i] = 1 ^ (frow[i] & srow[i - 1]);
            for (int i = 3; i <= clen; i++) scol[i] = 1 ^ (fcol[i] & scol[i - 1]);
        }

        char query(int r, int c) {
            if (r == 1) return BITS[fcol[c]];
            if (c == 1) return BITS[frow[r]];
            if (r < c) return BITS[scol[c + 2 - r]];
            return BITS[srow[r + 2 - c]];
        }

        private static int[] toArray(String s) {
            int[] ar = new int[s.length()];
            int index = 0;
            for (char ch : s.toCharArray())
                ar[index++] = toInt(ch);

            return ar;
        }

        private static int toInt(char ch) {
            return ch == ZERO ? 0 : 1;
        }
    }

    final static class BruteForceTableMatrix extends TableMatrix {
        private final int[][] matrix;

        BruteForceTableMatrix(String r, String c) {
            super(r, c);
            matrix = new int[rlen + 1][clen + 1];
            prepare();
        }

        private void prepare() {
            for (int i = 1; i <= rlen; i++) matrix[i][0] = row[i - 1];
            for (int i = 1; i <= clen; i++) matrix[0][i] = col[i - 1];

            for (int i = 1; i <= rlen; i++) {
                for (int j = 1; j <= clen; j++)
                    matrix[i][j] = 1 - (matrix[i - 1][j] & matrix[i][j - 1]);
            }
        }

        @Override
        char query(int r, int c) {
            return BITS[matrix[r][c]];
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