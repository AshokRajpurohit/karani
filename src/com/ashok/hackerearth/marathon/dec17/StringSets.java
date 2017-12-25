/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.dec17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: String Sets
 * Link: https://www.hackerearth.com/challenge/competitive/december-circuits-17/algorithm/string-sets-1-d9d9e893/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class StringSets {
    private static final int MOD = 1000000007;
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] map;
    private static long[] factorials;

    static {
        map = new int[256];
        for (int i = 'a'; i <= 'z'; i++)
            map[i] = i - 'a' + 1;

        for (int i = 'A'; i <= 'Z'; i++)
            map[i] = i - 'A' + 27;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt(), k = in.readInt();
        char[] ar = in.read(n).toCharArray();
        int sum = calculateSum(ar) + n; // all the characters are going to have atleast 1 as weight.
        int val = k - sum, max = n * 25;
        long res = 0;
        val %= m;
        if (val < 0) val += m;
        if (val <= max) populate(n * 26);

        while (val <= max) {
            res += calculate(val, n);
            val += m;
        }

        res %= MOD;
        if (res < 0) res += MOD;

        out.println(res);
    }

    private static long calculate(int val, int n) {
        long res = 0;
        int r = 0, s = val, count = 0;
        boolean positive = true;
        while (r <= n && s >= 0) {
            long v = ncr(n, r);
            if (!positive) v = -v;

            v = v * ncr(n + s - 1, s) % MOD;
            res += v;

            r++;
            s -= 26;
            positive = !positive;
            count++;
        }

        return res;
    }

    private static int calculateSum(char[] ar) {
        int sum = 0;
        for (char ch : ar)
            sum += map[ch];

        return sum;
    }

    private static long ncr(int n, int r) {
        if (r == 0 || n == r)
            return 1;

        return factorials[n] * inverse(factorials[r] * factorials[n - r] % MOD) % MOD;
    }

    private static void populate(int n) {
        factorials = new long[n + 1];
        factorials[0] = factorials[1] = 1;
        for (int i = 2; i <= n; i++)
            factorials[i] = factorials[i - 1] * i % MOD;
    }

    private static long inverse(long n) {
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
        return res;
    }

    final static class InputReader {
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