/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codeforces;

import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Problem Name: CodeForces 637 Div 2
 * Link:
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Contest637Div2 {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ProblemD.solve();
        in.close();
        out.close();
    }

    /**
     * Incomplete solution.
     */
    final static class ProblemD {
        private static final int[] digits;
        private static final int[] bits;

        static {
            digits = Stream.of("1110111", "0010010", "1011101", "1011011", "0111010",
                    "1101011", "1101111", "1010010", "1111111", "1111011")
                    .mapToInt(s -> binaryToInt(s.toCharArray()))
                    .toArray();

            bits = IntStream.range(0, 256)
                    .map(i -> Integer.bitCount(i))
                    .toArray();
        }

        private static void solve() throws IOException {
            int n = in.readInt(), k = in.readInt();
            String[] digits = in.readStringArray(n);
            out.println(process(digits, k));
        }

        private static String process(String[] binStrs, int missingSticks) {
            int[] ar = Arrays.stream(binStrs).mapToInt(bs -> binaryToInt(bs.toCharArray())).toArray();
            int n = ar.length;
            if (n == 1) {
                int num = addSticks(ar[0], missingSticks);
                return "" + getNumber(num);
            }
            int minMissing = Arrays.stream(ar).map(v -> minimumMissing(v)).sum();
            if (minMissing > missingSticks) return "-1";
            int maxMissing = Arrays.stream(ar).map(v -> maxMissing(v)).sum();
            if (maxMissing < missingSticks) return "-1";

            int[] nums;
            if (minMissing == missingSticks) {
                nums = Arrays.stream(ar).map(v -> numWithMinSticks(v)).map(v -> getNumber(v)).toArray();
                return printable(nums);
            }

            if (maxMissing == missingSticks) {
                nums = Arrays.stream(ar).map(v -> numWithMaxSticks(v)).map(v -> getNumber(v)).toArray();
                return printable(nums);
            }

            int extraSticks = maxMissing - minMissing;
            int[] sticksSum = Arrays.stream(ar).map(v -> maxMissing(v)).toArray();
            for (int i = n - 2; i >= 0; i--) sticksSum[i] += sticksSum[i + 1];
            nums = ar.clone();
            // let's spray extra sticks if we can.
            int index = 0;
           /* while (index < n && extraSticks > 0) {
                int sticksDiff =-1;
                extraSticks = 0;
            }*/
            return "-1";
        }

        private static String printable(int[] ar) {
            StringBuilder sb = new StringBuilder(ar.length);
            for (int e : ar) sb.append(e);
            return sb.toString();
        }

        private static int stickDiff(int a, int b) {
            return bits[a ^ b];
        }

        private static int getNumber(int binNum) {
            for (int i = 0; i < digits.length; i++) {
                if (digits[i] == binNum) return i;
            }

            return -1;
        }

        /**
         * returns the largest digit which can be obtained if {@code sticks} are added to this display.
         * In case these number of digits can't be added, than it will return -1.
         *
         * @param n
         * @param sticks
         * @return
         */
        private static int addSticks(int n, int sticks) {
            if (sticks == 0) return n;
            int num = -1;
            for (int digit : digits) {
                if ((digit & n) != n) continue;
                int x = digit ^ n;
                if (bits[x] != sticks) continue;
                num = digit;
            }

            return num;
        }

        private static int minimumMissing(int n) {
            int min = 7;
            for (int digit : digits) {
                if ((digit & n) == n) {
                    int x = digit ^ n;
                    min = Math.min(min, bits[x]);
                }
            }

            return min;
        }

        private static int maxMissing(int n) {
            int max = 0;
            for (int digit : digits) {
                if ((digit & n) == n) {
                    int x = digit ^ n;
                    max = Math.max(max, bits[x]);
                }
            }

            return max;
        }

        private static int numWithMaxSticks(int n) {
            int num = n;
            int max = 0;
            for (int digit : digits) {
                if ((digit & n) == n) {
                    int x = digit ^ n;
                    int bitDiff = bits[x];
                    if (max < bitDiff) num = digit;
                    else if (max == bitDiff) num = Math.max(num, digit);
                    max = Math.min(max, bitDiff);
                }
            }

            return num;
        }

        private static int numWithMinSticks(int n) {
            int num = n;
            int min = 7;
            for (int digit : digits) {
                if ((digit & n) == n) {
                    int x = digit ^ n;
                    int bitDiff = bits[x];
                    if (min > bitDiff) num = digit;
                    else if (min == bitDiff) num = Math.max(num, digit);
                    min = Math.min(min, bitDiff);
                }
            }

            return num;
        }

        private static int binaryToInt(char[] chars) {
            int num = 0;
            for (char ch : chars) {
                num = (num << 1) | (ch - '0');
            }

            return num;
        }
    }

    final static class ProblemC {
        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt();
                int[] ar = in.readIntArray(n);
                process(ar);
            }
        }

        private static void process(int[] ar) {
            int n = ar.length;
            if (n < 3) {
                out.println("Yes");
                return;
            }
            for (int i = 0; i < ar.length; i++) ar[i]--;
            int[] indices = new int[n];
            for (int i = 0; i < ar.length; i++) indices[ar[i]] = i;

            int num = 0, left = 0, right = n - 1;
            boolean possible = true;
            outer:
            while (left < right) {
                int index = indices[num];
                num += right - index + 1;
                if (index < right) {
                    for (int i = index + 1; i <= right; i++) {
                        if (ar[i] != ar[i - 1] + 1) {
                            possible = false;
                            break outer;
                        }
                    }
                }
                right = index - 1;
            }
            out.println(possible ? "Yes" : "No");
        }
    }

    final static class ProblemB {
        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt(), k = in.readInt();
                int[] heights = in.readIntArray(n);
                process(heights, k);
            }
        }

        private static void process(int[] ar, int k) {
            int n = ar.length;
            boolean[] peaks = new boolean[n];
            for (int i = 1; i < n - 1; i++) peaks[i] = ar[i] > ar[i - 1] && ar[i] > ar[i + 1];
            int count = (int) IntStream.range(1, n).filter(i -> peaks[i]).count();
            if (count == 0) {
                out.println(1 + " " + 1);
                return;
            }

            int[] counts = new int[n];
            for (int i = 1; i < n - 1; i++) {
                if (peaks[i]) counts[i] = counts[i - 1] + 1;
                else counts[i] = counts[i - 1];
            }

            int maxPeaks = counts[k - 2], left = 0;
            for (int i = 1, j = k; j < n; i++, j++) {
                int pk = counts[j - 1] - counts[i];
                if (pk > maxPeaks) {
                    left = i;
                    maxPeaks = pk;
                }
            }

            out.println((maxPeaks + 1) + " " + (left + 1));
        }
    }

    final static class ProblemA {
        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt(), a = in.readInt(), b = in.readInt(), c = in.readInt(), d = in.readInt();
                int lowest = n * (a - b), max = n * (a + b);
                boolean yes = lowest <= c + d && max >= c - d;
                out.println(yes ? "Yes" : "No");
            }
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