/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankitSoni;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Problem Name: Ankit Soni, Zapr Hiring Challenge
 * Link: Only Ankit has the link.`
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ZaprHiringChallenge {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ZaprHiringChallenge a = new ZaprHiringChallenge();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(Pascal.xyz(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    final static class Pascal {
        final static int mod = 1000000007;
        private static long[] factorials = new long[50];

        static {
            factorials[0] = 1;

            for (int i = 1; i < factorials.length; i++)
                factorials[i] = factorials[i - 1] * i % mod;
        }

        static int xyz(int a, int b) {
            if (a < 25 && b < 25)
                return 0;

            int a1 = Math.min(24, a), b1 = Math.min(24, b);
            long till24 = ncr(a1 + b1, 24);

            if (a1 < 24 || b1 < 24)
                return (int) till24;

            return (int) (till24 * pow(2, Math.min(a, b) - 24));
        }

        private static long ncr(int n, int r) {
            return (factorials[n] * pow(factorials[r], mod - 2) % mod) * pow(factorials[n - r], mod - 2) % mod;
        }

        private static long pow(long a, long b) {
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
    }

    final static class TwoPlayerCompetetion {
        final static String bruce = "Bruce\n", clark = "Clark\n";

        private static void whoWonTheGame(String[] tests) {
            StringBuilder sb = new StringBuilder(tests.length * 5);

            for (String num : tests)
                sb.append(whoWonTheGame(num));

            System.out.println(sb);
        }

        private static String whoWonTheGame(String num) {
            BigInteger bi = new BigInteger(num);
            char[] bits = bi.toString(2).toCharArray();
            boolean res = true;
            long n = 0;

            long half = Long.parseLong(String.valueOf(Arrays.copyOfRange(bits, 1, bits.length)), 2);
            if (half == 0)
                n = Long.parseLong(String.valueOf(Arrays.copyOfRange(bits, 0, bits.length - 1)), 2);
            else
                n = half;


            while (n > 1) {
                long highestBit = Long.highestOneBit(n);

                if (highestBit == n)
                    n >>>= 1;
                else
                    n -= highestBit;

                res = !res;
            }

            return res ? bruce : clark;
        }

    }
}
