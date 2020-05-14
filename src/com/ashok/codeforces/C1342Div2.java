/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codeforces;

import java.io.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Problem Name:
 * Link:
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class C1342Div2 {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        ProblemA.solve();
//        ProblemB.solve();
        ProblemC.solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
        }
    }

    final static class ProblemC {

        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                process();
            }
        }

        private static void test() throws IOException {
            while (true) {
                int a = in.readInt(), b = in.readInt(), x = in.readInt(), y = in.readInt();
            }
        }

        private static void process() throws IOException {
            int a = in.readInt(), b = in.readInt(), q = in.readInt();
            int hcm = a * b / gcd(a, b);
            StringBuilder sb = new StringBuilder();
            boolean[] diffs = new boolean[hcm];
            IntStream.range(1, hcm).forEach(v -> diffs[v] = (v % a) % b != (v % b) % a);
            int[] counts = new int[hcm];
            IntStream.range(1, hcm).forEach(v -> counts[v] = counts[v - 1] + (diffs[v] ? 1 : 0));
            int loopDiffs = counts[hcm - 1];
            while (q > 0) {
                q--;
                long left = in.readLong(), right = in.readLong();
                if (a == b) {
                    sb.append(0).append(' ');
                    continue;
                }

                if (left == right) {
                    sb.append(diffs[(int) (left % hcm)] ? 1 : 0).append(' ');
                    continue;
                }
                int excessL = (int) (left % hcm), excessR = (int) (right % hcm);
                int loopsInRange = (int) (right / hcm - left / hcm);
                long diffCount = loopsInRange * loopDiffs;
                diffCount += counts[excessR] - loopDiffs; // include rightmost and exclude the loop we counted.
                diffCount += loopDiffs - (excessL == 0 ? loopDiffs : counts[excessL - 1])   ;
//                if (diffCount != bruteForce(a, b, left, right)) throw new RuntimeException("lo karlo baat");
                sb.append(diffCount).append(' ');
//                long commonCount = (right / hcm - (left - 1) / hcm) * loopDiffs;
//                commonCount += counts[hcm - 1] - counts[Math.max(0, excessL - 1)];
//                commonCount += counts[excessR] - loopDiffs;
//                sb.append(commonCount).append(' ');
            }

            out.println(sb);
        }

        private static long bruteForce(int a, int b, long x, long y) {
            return LongStream.range(x, y + 1).filter(v -> (v % a) % b != (v % b) % a).count();
        }

        /**
         * Euclid's Greatest Common Divisor algorithm implementation.
         * For more information refer Wikipedia and Alan Baker's Number Theory.
         *
         * @param a
         * @param b
         * @return Greatest Commond Divisor of a and b
         */
        private static int gcd(int a, int b) {
            if (a == 0)
                return b;
            return gcd(b % a, a);
        }

    }

    final static class ProblemB {
        public static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                out.println(process(in.read()));
            }
        }

        private static String process(String binStr) {
            if (binStr.length() == 1) return binStr + binStr;
            if (allCharSame(binStr)) return binStr;
            int xor = '0' ^ '1';
            StringBuilder sb = new StringBuilder();
            char[] chars = binStr.toCharArray();
            sb.append(chars[0]);
            char ref = chars[0];
            for (int i = 1; i < chars.length; i++) {
                char ch = chars[i];
                if (ch == ref) sb.append((char) (xor ^ ref));
                sb.append(ch);
                ref = ch;
            }

            return sb.toString();
        }

        private static boolean allCharSame(String str) {
            char ref = str.charAt(0);
            for (char ch : str.toCharArray()) if (ch != ref) return false;
            return true;
        }
    }

    final static class ProblemA {
        public static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int x = in.readInt(), y = in.readInt();
                int a = in.readInt(), b = in.readInt();
                out.println(process(x, y, a, b));
            }
        }

        private static long process(long x, long y, long a, long b) {
            if (b > 2 * a) {
                return a * (Math.abs(x) + Math.abs(y));
            }

            long c1 = a * (Math.abs(x) + Math.abs(y));
            if (x * y < 0) return c1;
            x = Math.abs(x);
            y = Math.abs(y);
            long c2 = b * Math.min(x, y) + a * Math.abs(x - y);
            return Math.min(c1, c2);
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader(InputStream input) {
            in = input;
        }

        public InputReader() {
            in = System.in;
        }

        public InputReader(String file) throws FileNotFoundException {
            in = new FileInputStream(file);
        }

        public InputReader(File file) throws FileNotFoundException {
            in = new FileInputStream(file);
        }

        public void close() throws IOException {
            in.close();
        }

        public String next() throws IOException {
            return read();
        }

        public boolean hasNext() throws IOException {
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            return bufferSize != -1;
        }

        public boolean isNewLine() {
            return buffer[offset] == '\n' || buffer[offset] == '\r';
        }

        public char nextChar() throws IOException {
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            return (char) buffer[offset++];
        }

        public char nextValidChar() throws IOException {
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

            return (char) buffer[offset++];

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

        public int[] readIntArray(int n, int d) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt() + d;

            return ar;
        }

        public int[][] readIntTable(int m, int n) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = readIntArray(n);

            return res;
        }

        public int[][] readIntTable(int m, int n, int d) throws IOException {
            int[][] res = new int[m][];
            for (int i = 0; i < m; i++)
                res[i] = readIntArray(n, d);

            return res;
        }

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }

        public long[] readLongArray(int n, long d) throws IOException {
            long[] ar = new long[n];
            for (int i = 0; i < n; i++)
                ar[i] = readLong() + d;

            return ar;
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

        public boolean isSpaceCharacter(char ch) {
            return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
        }

        public String readLine() throws IOException {
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
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
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

        public String readLines(int lines) throws IOException {
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

            for (; offset < bufferSize && lines > 0; ++offset) {
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
                    lines--;

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

        public String[] readLineArray(int size) throws IOException {
            String[] lines = new String[size];
            for (int i = 0; i < size; i++)
                lines[i] = readLine();

            return lines;
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

        public String[] readStringArray(int size) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = read();

            return res;
        }

        public String[] readStringArray(int size, int len) throws IOException {
            String[] res = new String[size];
            for (int i = 0; i < size; i++)
                res[i] = read(len);

            return res;
        }

        public double readDouble() throws IOException {
            return Double.parseDouble(read());
        }

        public double[] readDoubleArray(int n) throws IOException {
            double[] ar = new double[n];
            for (int i = 0; i < n; i++)
                ar[i] = readDouble();

            return ar;
        }
    }
}