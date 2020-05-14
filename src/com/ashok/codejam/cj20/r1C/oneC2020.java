/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.cj20.r1C;

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
public class oneC2020 {
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
        return OverRandomized.process();
    }

    final static class OverRandomized {
        private static String process() throws IOException {
            int u = in.readInt();
            int size = 10000;
            Query[] queries = new Query[size];
            char[] digits = new char[10];
            for (int i = 0; i < size; i++)
                queries[i] = new Query(in.readInt(), in.read());

            int count = 1;
            for (int i = 0; i < 10; i++) {
                char dc = simpleFindN(queries, i, digits);
                if (dc == 0) break;
                digits[i] = dc;
                count++;
            }
            while (count < 10) {
                for (int i = 1; i < 10; i++) {
                    if (digits[i] != 0) continue;
                    char dc = findN(queries, i, digits);
                    if (dc == 0) continue;
                    digits[i] = dc;
                    count++;
                }
            }

            return new String(digits);
        }

        private static char find0(Query[] queries) {
            for (Query query: queries) {
                if (query.m == 1 && query.isValid()) return query.r.charAt(0);
            }

            int[] chars = Arrays.stream(queries).filter(q -> q.isValid()).mapToInt(q -> q.r.charAt(0)).toArray();
            int[] counts = new int[256];
            for (int ch: chars) counts[ch]++;
            return (char)maxIndex(counts);
        }

        private static char simpleFindN(Query[] queries, int digit, char[] ref) {
            if (digit == 0) return find0(queries);
            int[] solutions = Arrays.stream(queries)
                    .filter(q -> q.isValid())
                    .mapToInt(q -> q.r.charAt(0))
                    .filter(ch -> !contains(ref, (char)ch))
                    .toArray();

            if (solutions.length == 1) return (char)solutions[0];
            return (char)0;
        }

        private static char findN(Query[] queries, int digit, char[] ref) {
            int[] chars = Arrays.stream(queries)
                    .filter(q -> q.r.length() > digit)
                    .filter(q -> q.isValid())
                    .filter(q -> !contains(ref, q.r.charAt(digit)))
                    .mapToInt(q -> q.r.charAt(digit))
                    .toArray();
            int[] counts = new int[256];
            for (int ch: chars) counts[ch]++;
            int maxCount = Arrays.stream(counts).max().getAsInt();
            int[] sols = IntStream.range(0, 256).filter(ch -> counts[ch] == maxCount).toArray();
            if (sols.length > 1) return (char)0;
            return (char)sols[0];
        }

        private static boolean contains(char[] ar, char ch) {
            for (char c: ar) if (ch == c) return true;
            return false;
        }

        private static int maxIndex(int[] ar) {
            int max = ar[0], index = 0;
            for (int i = 1; i < ar.length; i++) {
                if (ar[i] > max) index = i;
                max = Math.max(max, ar[i]);
            }
            return index;
        }
    }



    final static class Query {
        final int m;
        final String r;

        Query(final int m, final String r) {
            this.m = m;
            this.r = r;
        }

        boolean isValid() {
            return !r.equals("-1");
        }

        public String toString() {
            return m + " " + r;
        }
    }

    final static class PeppurrCat {
        private static String process() throws IOException {
            int x = in.readInt(), y = in.readInt();
            String m = in.read();
            Point[] catPath = new Point[m.length() + 1];
            catPath[0] = new Point(x, y);
            for (int i = 0; i < m.length(); i++) {
                char ch = m.charAt(i);
                if (ch == 'E') x++;
                else if (ch == 'W') x--;
                else if (ch == 'N') y++;
                else y--;
                catPath[i + 1] = new Point(x, y);
            }

            Point self = new Point(0, 0);
            for (int i = 0; i < catPath.length; i++) {
                if (self.distance(catPath[i]) <= i) return "" + i;
            }

            return "IMPOSSIBLE";
        }

    }

    final static class Point {
        final int x, y;

        Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        int distance(Point point) {
            return Math.abs(x - point.x) + Math.abs(y - point.y);
        }

        @Override
        public String toString() {
            return x + " " + y;
        }
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
