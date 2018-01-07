/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.Practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Maximum Palindromes
 * Link: https://www.hackerrank.com/contests/hourrank-25/challenges/maximum-palindromes/problem
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MaximumPalindromes {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[][] charCountIndexMap;
    private static final int MOD = 1000000007;
    private static final int[] charCountMap = new int[256], lowerCaseChars = new int[26];
    private static long[] factorials;
    private static char[] chars;

    static {
        for (int i = 0; i < 26; i++)
            lowerCaseChars[i] = i + 'a';
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        String s = in.read();
        process(s);
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 3);
        while (q > 0) {
            q--;
            sb.append(query(in.readInt() - 1, in.readInt() - 1)).append('\n');
        }

        out.print(sb);
    }

    private static void process(String s) {
        chars = s.toCharArray();
        int len = s.length();
        charCountIndexMap = new int[256][];
        for (int ch : lowerCaseChars)
            charCountIndexMap[ch] = new int[len];

        int index = 0;
        for (int e : chars)
            charCountIndexMap[e][index++] = 1;

        for (int[] e : charCountIndexMap)
            prefixSum(e);

        initializeFactorials(len);
    }

    private static long query(int start, int end) {
        populateCharCountMap(start, end);
        int odds = 0, length = 0;
        long numerator = 1, denominator = 1;
        for (int ch : lowerCaseChars) {
            int count = charCountMap[ch];
            if (count == 0) continue;

            odds += (count & 1);
            count >>>= 1;

            if (count == 0) continue;
            length += count;
            denominator = denominator * factorials[count] % MOD;
        }

        clearBuffer();
        numerator = factorials[length] * Math.max(odds, 1) % MOD;
        return numerator * inverseModulo(denominator) % MOD;
    }

    private static void populateCharCountMap(int start, int end) {
        for (int ch : lowerCaseChars)
            charCountMap[ch] = query(charCountIndexMap[ch], start, end);
    }

    private static int query(int[] ar, int start, int end) {
        return query(ar, end) - query(ar, start - 1);
    }

    private static int query(int[] ar, int index) {
        return index < 0 ? 0 : ar[index];
    }

    private static void initializeFactorials(int len) {
        factorials = new long[len + 1];
        factorials[0] = 1;
        for (int i = 1; i <= len; i++)
            factorials[i] = factorials[i - 1] * i % MOD;
    }

    private static void clearBuffer() {
        for (int ch : lowerCaseChars)
            charCountMap[ch] = 0;
    }

    private static void prefixSum(int[] ar) {
        if (ar == null)
            return;

        int len = ar.length;
        for (int i = 1; i < len; i++)
            ar[i] += ar[i - 1];
    }

    private static long inverseModulo(long n) {
        return pow(n, MOD - 2);
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @return
     */
    private static long pow(long a, long b) {
        if (b == 0)
            return 1;

        a = a % MOD;
        if (a < 0)
            a += MOD;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % MOD;
            if ((b & r) != 0) {
                res = (res * a) % MOD;
            }
        }

        if (res < 0) res += MOD;
        return res;
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