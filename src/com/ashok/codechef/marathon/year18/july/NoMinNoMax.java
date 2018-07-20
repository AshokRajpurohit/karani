/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: No Minimum No Maximum
 * Link: https://www.codechef.com/JULY18A/problems/NMNMX
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class NoMinNoMax {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int MOD = 1000000007, totient = MOD - 1, totient2 = 500000002;
    private static final int limit = 5000;
    private static final long[] factorials = new long[limit + 1],
            twoPowersInFactorial = new long[limit + 1],
            twoPowers = new long[limit + 1],
            twoPowersInFactor = new long[limit + 1];

    static {
        factorials[0] = factorials[1] = 1;
        for (int i = 2; i <= limit; i++)
            factorials[i] = factorials[i - 1] * removeTwoFactors(i) % totient;

        twoPowersInFactorial[2] = 1;
        for (int i = 4, j = 2; i <= limit; i += 2, j++)
            twoPowersInFactorial[i] = twoPowersInFactorial[j] + 1;

        for (int i = 3; i <= limit; i++)
            twoPowersInFactorial[i] += twoPowersInFactorial[i - 1];

        twoPowers[0] = 1;
        for (int i = 1; i <= limit; i++)
            twoPowers[i] = (twoPowers[i - 1] << 1) % totient;

        for (int i = 2, j = 1; i <= limit; i += 2, j++)
            twoPowersInFactor[i] = twoPowersInFactor[j] + 1;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
//        play();
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt(), k = in.readInt();
            int[] ar = in.readIntArray(n);
            out.println(process(ar, k));
        }
    }

    private static void play() throws IOException {
        while (true) {
            int n = in.readInt(), k = in.readInt();
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = i + 1;

            long p = process(ar, k), b = bruteForce2(ar, k);
            if (p != b)
                System.err.println("process: " + p + ", brute: " + b);
            else {
                out.println("process: " + p + ", brute: " + b);
                out.flush();
            }
        }
    }

    private static long process(int[] ar, int k) {
        Arrays.sort(ar);
        if (k == 3)
            return singleElementProcess(ar);

        int n = ar.length;
        if (k == n) return multiply(ar, 1, n - 2, MOD);

        long pow = ncr(n - 1, k - 1);
        long res = 1;
        long[] powers = new long[n];
        Arrays.fill(powers, pow);

        for (int i = 0, j = n - 1; i < n; i++, j--) {
            powers[i] += totient - ncr(n - 1 - i, k - 1);
            powers[i] += totient - ncr(i, k - 1);
        }

        for (int i = 0; i < n; i++)
            res = res * pow(ar[i], powers[i], MOD) % MOD;

        return res;
    }

    private static void validate(int[] ar) {
        for (int i = 1; i < ar.length; i++)
            if (ar[i] == ar[i - 1])
                throw new RuntimeException("Error, numbers are not disctinct");
    }

    private static long bruteForce(int[] ar, int k) {
        Arrays.sort(ar);
        if (k == 3)
            return singleElementProcess(ar);

        long res = 1;
        for (int i = 1; i <= ar.length; i++)
            res = res * bruteForce(ar, i, 1L, k - 1) % MOD;

        return res;
    }

    private static long bruteForce2(int[] ar, int k) {
        Arrays.sort(ar);
        if (k == 3) return singleElementProcess(ar);

        int n = ar.length;
        if (k == n) return multiply(ar, 1, n - 2, MOD);

        long[] ncrs = combinations(n, k - 1);
        long res = 1, pow = ncrs[n - 1], offset = totient << 1;
        for (int i = 0; i < n; i++)
            res = res * pow(ar[i], offset + pow - ncrs[n - 1 - i] - ncrs[i], MOD) % MOD;

        return res < 0 ? res + MOD : res;
    }

    private static long bruteForce(int[] ar, int index, long multiplicationSoFar, int k) {
        if (ar.length - k < index)
            return 1;

        if (k == 1)
            return pow(multiplicationSoFar, ar.length - index, MOD);

        return bruteForce(ar, index + 1, multiplicationSoFar, k) * bruteForce(ar, index + 1, multiplicationSoFar * ar[index] % MOD, k - 1) % MOD;
    }

    private static long singleElementProcess(int[] ar) {
        int n = ar.length;
        long res = 1;
        for (int i = 1; i < n - 1; i++) {
            res = res * pow(ar[i], i * (n - i - 1), MOD) % MOD;
        }

        return res;
    }

    private static long singleElementExcludeProcess(int[] ar) {
        int n = ar.length;
        return multiply(ar, 1, n - 2, MOD) * multiply(ar, 2, n - 3, MOD) % MOD;
    }

    private static long sigmaN(long n) {
        return (n * (n + 1)) >>> 1;
    }

    private static long removeTwoFactors(long n) {
        while ((n & 1) == 0)
            n >>>= 1;

        return n;
    }

    private static long[] getMultiplicationArray(int[] ar) {
        int n = ar.length;
        long[] res = new long[n];
        res[0] = ar[0];
        for (int i = 1; i < n; i++)
            res[i] = res[i - 1] * ar[i] % MOD;

        return res;
    }

    private static long ncr(int n, int r) {
        if (r < 0 || n < r)
            return 0;

        if (r == 0 || n == r)
            return 1;

        long powerOfTwo = twoPowersInFactorial[n] - twoPowersInFactorial[r] - twoPowersInFactorial[n - r];
        return (factorials[n] * pow(factorials[r] * factorials[n - r], totient2 - 1, totient) % totient) * pow(2, powerOfTwo, totient) % totient;
    }

    private static long inverse(long a) {
        return pow(a, totient2 - 1, totient);
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
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

        if (res < 0) res += mod;
        return res;
    }

    private static long[] combinations(int n, int r) {
        long[] res = new long[n + 1];
        int[] twoExponents = new int[n + 1];
        res[r] = 1;
        for (int i = r + 1; i <= n; i++) {
            res[i] = (res[i - 1] * removeTwoFactors(i) % totient) * pow(removeTwoFactors(i - r), totient2 - 1, totient) % totient;
            twoExponents[i] = (int) (twoExponents[i - 1] + twoPowersInFactor[i] - twoPowersInFactor[i - r]);
        }

        for (int i = r + 1; i <= n; i++)
            res[i] = res[i] * twoPowers[twoExponents[i]] % totient;

        return res;
    }

    private static long multiply(int[] ar) {
        long res = 1;
        for (int e : ar) res = res * e % MOD;
        return res;
    }

    private static long multiply(int[] ar, int start, int end, long mod) {
        long res = 1;
        for (int i = start; i <= end; i++)
            res = res * ar[i] % mod;

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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}