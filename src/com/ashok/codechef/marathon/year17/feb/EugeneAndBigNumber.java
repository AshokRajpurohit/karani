/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Problem Name: Eugene and big number
 * Link: https://www.codechef.com/FEB17/problems/KBIGNUMB
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class EugeneAndBigNumber {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        validator();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 10);

        while (t > 0) {
            t--;

            sb.append(process(in.readInt(), in.readLong(), in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static void validator() throws IOException {
        Random random = new Random();
        while (true) {
            int a = in.readInt();
            long n = in.readLong();
            int mod = in.readInt();

            long actual = calculate(a, n, mod), expected = process(a, n, mod);
            out.println("actual: " + actual + ", expected: " + expected);
            out.flush();
            if (actual != expected) {
                process(a, n, mod);
            }
        }
    }

    /**
     * Repetetion of number a, n times is
     * aaaa... n times = a + a * d + a * d * d + ... n times
     * i.e. a (1 + d + d * d + ... n terms)
     *
     * @param a   Number to be repeted.
     * @param n   Repetetion count
     * @param mod Modulo to be taken for final result.
     * @return no need to explain.
     */
    private static long process(int a, long n, int mod) {
        int d = getDecimal(a);
        d %= mod;// as explained, it won't change result.
        a %= mod;

        if (a == 0)
            return 0;

        long D = d - 1;
        if (D < 0)
            D += mod;

        long N = a * (pow(d, n, mod) + mod - 1) % mod;
        long g = gcd(D, mod);
        if (g == 1)
            return N * inverseModulo(D, mod) % mod;

        N /= g;
        D /= g;
        mod /= g;

        return mod + (N * inverseModulo(D, mod) % mod);
    }

    private static long calculate(int a, long n, int mod) {
        int d = getDecimal(a);
        d %= mod;// as explained, it won't change result.
        a %= mod;

        if (a == 0)
            return 0;

//        return a * binaryWay(d, n, mod) % mod;
        return a * squareMethod(d, n, mod) % mod;
    }

    private static long squareMethod(long d, long n, long mod) {
        if (n < 20)
            return iterativeWay(d, n, mod);

        long sq = (long) Math.sqrt(n);

        long res = squareMethod(d, sq, mod) * squareMethod(pow(d, sq, mod), sq, mod) % mod;
        long rem = n - sq * sq;

        res += squareMethod(d, rem, mod) * pow(d, sq * sq, mod) % mod;


        return res % mod;
    }

    private static long binaryWay(long d, long n, long mod) {
        if (n == 1)
            return 1;

        if (n == 2)
            return (d + 1) % mod;

        long m = (n >>> 1);
        long res = binaryWay(d, m, mod) * (1 + pow(d, m, mod)) % mod;

        if ((n & 1) == 1)
            res += pow(d, n - 1, mod);

        return res % mod;

    }

    private static long iterativeWay(long d, long n, long mod) {
        long r = 1, res = 0;

        while (n > 0) {
            res += r;
            r = r * d % mod;
            n--;
        }

        return res % mod;
    }

    /**
     * Any integer N can be written as x * pow(10, d) where x < 1.
     * This function returns that d (second param in pow function).
     *
     * @param a
     * @return
     */
    private static int getDecimal(int a) {
        int d = 10;

        while (d <= a)
            d = (d << 3) + (d << 1);

        return d;
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @param mod
     * @return
     */
    private static long pow(long a, long b, long mod) {
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
        return res;
    }

    /**
     * This function returns the modular inverse of number a with modulo mod.
     * It uses basically Extended Euclid Algorithm. This method doesn't return
     * inverse Modulo, it actually returns the number to which if multiplied
     * and taken modulo mod equals to GCD of a and mod. So if a and mod are
     * coprime then it is inverse modulo other wise this method throws Exception.
     *
     * @param a
     * @param mod
     * @return
     */
    public static long inverseModulo(long a, long mod) {
        if (a % mod == mod - 1)
            return mod - 1;

        if (a == 1)
            return 1;

        if (gcd(a, mod) > 1)
            throw new RuntimeException("Something is wrong");

        return (mod + extendedEuclid(a, mod)[1]) % mod;
    }

    public static long[] extendedEuclid(long a, long b) {
        long[] result = new long[3];
        xEuclid(a, b, result);
        return result;
    }

    /**
     * Extended Euclid's algorithm. This method is exact implementation of
     * the algorithm explained in CLRS.
     * For further read refer Alan Baker's A Comprehensive Course in Number
     * Theory, Chapter 1
     *
     * @param a
     * @param b
     * @param res
     */
    private static void xEuclid(long a, long b, long[] res) {
        if (b == 0) {
            res[0] = a;
            res[1] = 1;
            res[2] = 0;
            return;
        }

        xEuclid(b, a % b, res);
        long x = res[1], y = res[2];
        res[1] = y;
        res[2] = x - y * (a / b);
    }

    private static long gcd(long a, long b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
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
    }
}
