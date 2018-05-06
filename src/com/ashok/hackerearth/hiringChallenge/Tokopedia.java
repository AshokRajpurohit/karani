/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name:
 * Link: Find Your Purpose @ Tokopedia
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Tokopedia {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        PlayingSchedule.solve();
//        DiskGame.solve();
        in.close();
        out.close();
    }

    public static long pow(long a, long b, long mod) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }

        if (res < 0) res += mod;
        return res;
    }

    final static class DiskGame {
        final static String RK = "RK", NAKUL = "Nakul";

        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                out.println(process(in.readLong()) ? RK : NAKUL);
            }
        }

        private static boolean process(long n) {
            return process(n, highestPower(n));
        }

        private static boolean process(long n, long power) {
            if (n == 0)
                return false;

            long moves = n / power;
            return (moves & 1) == 1 ? !process(n % power, power >>> 2) : process(n % power, power >>> 2);
        }

        private static long highestPower(long n) {
            if (n < 4)
                return 1;

            long power = 1;
            while (n > 1) {
                n >>>= 2;
                power <<= 2;
            }

            return power >>> 2;
        }
    }

    final static class VisitingFriends {
        private static void solve() throws IOException {
            int t = in.readInt();
            StringBuilder sb = new StringBuilder(t << 2);
            while (t > 0) {
                t--;
                int n = in.readInt(), m = in.readInt();
            }
        }
    }

    final static class Friends {
        final int a, b;

        Friends(int x, int y) {
            a = x;
            b = y;
        }
    }

    final static class PlayingSchedule {
        final static int MOD = 1000000007;

        private static void solve() throws IOException {
            int n = in.readInt();
            long t = in.readLong();
            int a = in.readInt(), b = in.readInt();
            out.println(process(n, t));
        }

        private static long process(int n, long t) {
            if (t == 3)
                return n == 2 ? 0 : 1;

            if (t == 4)
                return 2 * (n - 1) * (n - 2) + 2;

            long res = 1;
            res = (n - 1) * (n - 1);
            return 2 * res * pow(n - 2, t - 4, MOD) % MOD;
        }

        private static long ncr(long n, long r) {
            long num = 1;
            for (int i = 0; i < r; i++) {
                num = num * n % MOD;
                n--;
            }

            long deno = 1;
            for (int i = 2; i <= r; i++)
                deno = deno * i % MOD;

            return num * pow(deno, MOD - 2, MOD) % MOD;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
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
    }
}