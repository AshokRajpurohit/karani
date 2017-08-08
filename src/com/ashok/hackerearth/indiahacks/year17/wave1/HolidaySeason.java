/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.indiahacks.year17.wave1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Holiday Season
 * Link: https://www.hackerearth.com/challenge/competitive/indiahacks-2017-programming-wave-10-eliminator-1/algorithm/holiday-season-ab957deb/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class HolidaySeason {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        out.println(process(in.read(n).toCharArray()));
    }

    private static long process(char[] ar) {
        int[][] charIndexMap = getMap(ar);
        int[][] charCountSumMap = getSumMap(ar);
        long res = 0;

        for (int a = 'a'; a <= 'z'; a++) {
            int alen = charIndexMap[a].length;
            if (alen < 2)
                continue;

            for (int b = 'a'; b <= 'z'; b++) {
                int blen = charIndexMap[b].length;
                if (blen < 2)
                    continue;

                res += calculate(charIndexMap[a], charCountSumMap[b]);
            }
        }

        return res;
    }

    private static long calculate(int[] ar, int[] sumAr) {
        long res = 0;
        int len = ar.length, slen = sumAr.length, last = slen - 1;
        for (int i = 0; i < len; i++) { // ar[i] and ar[j] are a and c, let's find count of b and d.
            for (int j = i + 1; j < len; j++) {
                int b = getSum(sumAr, ar[j] - 1) - getSum(sumAr, ar[i]);
                int d = getSum(sumAr, last) - getSum(sumAr, ar[j]);
                res += 1L * b * d;
            }
        }

        return res;
    }

    private static int getSum(int[] sum, int from, int to) {
        return getSum(sum, to) - getSum(sum, from - 1);
    }

    private static int getSum(int[] sum, int index) {
        if (index < 0)
            return 0;

        return sum[index];
    }

    private static int[][] getMap(char[] ar) {
        LinkedList<Integer>[] map = new LinkedList[256];
        for (int i = 'a'; i <= 'z'; i++)
            map[i] = new LinkedList<>();

        int len = ar.length;
        for (int i = 0; i < len; i++)
            map[ar[i]].addLast(i);

        int[][] charIndexMap = new int[256][];
        for (int i = 'a'; i <= 'z'; i++)
            charIndexMap[i] = toArray(map[i]);

        return charIndexMap;
    }

    private static int[][] getSumMap(char[] ar) {
        int len = ar.length;
        int[][] sumMap = new int[256][];
        for (int i = 'a'; i <= 'z'; i++)
            sumMap[i] = new int[len];

        for (int i = 0; i < len; i++)
            sumMap[ar[i]][i] = 1;

        for (int i = 'a'; i <= 'z'; i++)
            populateSum(sumMap[i]);

        return sumMap;
    }

    private static void populateSum(int[] ar) {
        int len = ar.length;
        for (int i = 1; i < len; i++)
            ar[i] += ar[i - 1];
    }

    private static int[] toArray(LinkedList<Integer> list) {
        int[] ar = new int[list.size()];
        int index = 0;
        for (int e : list)
            ar[index++] = e;

        return ar;
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
    }
}