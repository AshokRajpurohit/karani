/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.feb21;

// import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

/**
 * Problem Name: XOR Sums
 * Link: https://www.codechef.com/FEB21B/problems/SUMXOR2
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class XORSums {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int[] BITS;
    private static final long[] factorials;
    private static final long[] inverFacts;
    private static final int MOD = 998244353;

    static {
        BITS = new int[30];
        BITS[0] = 1;
        for (int i = 1; i < 30; i++) BITS[i] = BITS[i - 1] << 1;

        factorials = new long[200001];
        factorials[0] = factorials[1] = 1;
        for (int i = 2; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i % MOD;
        }

        inverFacts = new long[200001];
        inverFacts[0] = inverFacts[1] = 1;
        for (int i = 2; i < factorials.length; i++) {
            inverFacts[i] = inverseModulo(factorials[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        // test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        int q = in.readInt();
        int[] qs = in.readIntArray(q);

        StringBuilder sb = new StringBuilder(q << 3);
        long[] res = process(ar, qs);
        for (long e : res) sb.append(e).append('\n');
        out.println(sb);
    }

    private static long[] process(int[] ar, int[] qs) {
        int n = ar.length;
        long[] res = new long[qs.length];
        int[] bitCounts = new int[30];
        for (int e : ar) {
            int r = 1, i = 0;
            while (r <= e) {
                if ((r & e) != 0) bitCounts[i]++;
                r <<= 1;
                i++;
            }
        }

        long[][] mem = new long[n + 1][];

        IntBinaryOperator calc = (bit, m) -> {
            int k = bitCounts[bit], v = BITS[bit];
            int r = 1;
            long sum = 0;
            long[] map = mem[n - k];
            if (map == null) {
                map = new long[1 + n];
                map[0] = map[n - k] = 1;
                map[1] = map[n - k - 1] = n - k;
                for (int i = 2; i < map.length; i++) map[i] = ncr(n - k, i);
                for (int i = 1; i < map.length; i++) {
                    map[i] += map[i - 1];
                    if (map[i] >= MOD) map[i] -= MOD;
                }

                mem[n - k] = map;
            }

            while (r <= k && r <= m) {
                sum += v * (ncr(k, r) * map[m - r] % MOD) % MOD;
                r += 2;
            }

            sum %= MOD;
            return (int) sum;
        };
        int ri = 0;
        for (int m : qs) {
            long val = IntStream.range(0, 30)
                    .filter(i -> bitCounts[i] != 0)
                    // .parallel()
                    .mapToLong(i -> calc.applyAsInt(i, m))
                    .sum();

            res[ri++] = val % MOD;
        }

        return res;
    }

    private static long ncr(int n, int r) {
        if (r > n) return 0;
        if (r == 0 || n == r) return 1;
        if (r == 1 || r == n - 1) return n;
        return (factorials[n] * inverFacts[r] % MOD) * inverFacts[n - r] % MOD;
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @return
     */
    public static long pow(long a, long b) {
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

    /**
     * This function returns inverse modulo of a modulo mod.
     * it's based on Fermat's Little Theorom.
     * This function is now obsolete as We have better function implemented
     * in {@link ModularArithmatic#inverseModulo}.
     *
     * @param a
     * @return
     */
    public static long inverseModulo(long a) {
        return pow(a, MOD - 2);
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
    }
}