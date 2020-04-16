/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.april20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.ToLongFunction;
import java.util.stream.IntStream;

/**
 * Problem Name: Perfect Power Divisors
 * Link: https://www.codechef.com/APRIL20A/problems/PPDIV
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PerfectPowerDivisors {
    private static final long MOD = 1000000007, LIMIT = 1000000000000000000L, MID_LIMIT = 1000000000000L;
    private static final long[] CUBES;
    private static final long THREE_INVERSE = pow(3, (int) MOD - 2);
    private static int[] PRIMES = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59};
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    static {
        long time = System.currentTimeMillis();
        int n = 1000001;
        CUBES = new long[n];
        CUBES[1] = 1;
        for (int i = 2; i < n; i++) {
            CUBES[i] = 1L * i * i * i;
        }

        System.out.println(System.currentTimeMillis() - time);
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        Processor processor = Processors.getProcessor(2);
        long[] results = new long[t];
        long[] inputs = in.readLongArray(t);
        IntStream.range(0, t)
                .parallel()
                .forEach(i -> {
                    long result = processor.process(inputs[i]);
                    results[i] = result;
                });

        for (long result : results) out.println(result);
    }

    private static int getPower(long n, int base) {
        if (base == 2) return getTwoPoower(n);
        long limit = n / base;
        long expo = base;
        int power = 1;
        while (expo <= limit) {
            power++;
            expo *= base;
        }

        return power;
    }

    private static int getTwoPoower(long n) {
        return Long.numberOfTrailingZeros(Long.highestOneBit(n));
    }

    private static boolean checkSquare(long n) {
        long sqrt = (long) Math.sqrt(n);
        return n == sqrt * sqrt;
    }

    private static long forSquares(long n) {
        long res = 0;
        // let's go in reverse direction.
        int r = getSqrt(n), q = 1; // initial quotient is always one.
        int squareLimit = 1000000;

        while (r > squareLimit) {
            q++;
            int prev = getSqrt(n / q);
            res += (q - 1) * squareSum(prev + 1, r);
            res %= MOD;
            r = prev;
        }

        while (r > 1) {
            long square = 1L * r * r;
            long contri = getContribution(n, square);
            res += contri;
            r--;
        }

        return res % MOD;
    }

    private static int getSqrt(long n) {
        int sqrt = (int) Math.sqrt(n);
        long square = 1L * sqrt * sqrt;
        if (square > n) return sqrt - 1;
        return sqrt;
    }

    private static long forCubes(long n) {
        long res = 0;
        for (int i = 2; i < CUBES.length && CUBES[i] <= n; i++) {
            long contri = getContribution(n, CUBES[i]);
            res += contri;
        }

        return res % MOD;
    }

    private static int getPowerRoot(long n, int power) {
        return (int) (0.001 + Math.pow(n, 1.0 / power));
    }

    private static long getContribution(long n, long perfectPower) {
        return (n - (n % perfectPower)) % MOD;
    }

    private static long squareSum(int start, int end) {
        long res = squareSum(end) - squareSum((start - 1));
        return res < 0 ? res + MOD : res;
    }

    private static long squareSum(long n) {
        if (n < 2) return n;
        if (n >= MOD) n %= MOD;
        long res = n * (n + 1);
        res = res >>> 1;
        if (res >= MOD) res %= MOD;
        res = (res * (1 + (n << 1)) % MOD) * THREE_INVERSE;
        return res < MOD ? res : res % MOD;
    }

    private static long[] removeDuplicates(long[] ar) {
        Arrays.sort(ar);
        long prev = ar[0];
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] == prev) ar[i] = -1;
            else prev = ar[i];
        }

        return Arrays.stream(ar).filter(v -> v != -1).toArray();
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @return
     */
    private static long pow(long a, int b) {
        int r = Integer.highestOneBit(b);
        long res = a;

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
     * Recursive aproach to calculate a raised to power b.
     *
     * @param a
     * @param b
     * @return
     */
    private static long simplePow(long a, int b) {
        if (a == 2) return a << b;
        return simplePow0(a, b);
    }

    private static long simplePow0(long a, int b) {
        if (b == 1) return a;
        long res = simplePow0(a, b >>> 1);
        res = res * res;
        return isEven(b) ? res : res * a;
    }

    private static boolean isEven(long n) {
        return (n & 1) == 0;
    }

    private interface Processor {
        long process(long n);
    }

    final static class Processors {
        public static Processor getProcessor(int version) {
            switch (version) {
                case 1:
                    return new ProcessorV1();
                default:
                    return new ProcessorV2();
            }
        }
    }

    private static class ProcessorV1 implements Processor {
        static final long[] PERFECT_POWERS;
        static final int[] POWER_FACTOR;

        static {
            long time = System.currentTimeMillis();
            int n = 1000000;
            List<Long> perfectPowers = new LinkedList<>();
            long limit = 1L * n * n;
            for (int i = 2; i <= n; i++) {
                for (long j = 1L * i * i; j <= limit; j *= i) perfectPowers.add(j);
            }

            long[] ar = perfectPowers.stream().mapToLong(v -> v).toArray();
            PERFECT_POWERS = removeDuplicates(ar);

            int bits = 65;
            POWER_FACTOR = new int[bits];
            Arrays.fill(POWER_FACTOR, Integer.MIN_VALUE);
            List<Integer>[] divisors = IntStream.range(0, bits).mapToObj(i -> new LinkedList<Integer>()).toArray(t -> new List[t]);
            for (int i = 2; i < bits; i++) {
                for (int j = i + i; j < bits; j += i) {
                    divisors[j].add(i);
                }

                for (int j = i * i; j < bits; j *= i) POWER_FACTOR[j] = 0;
            }

            for (int i = 0; i < bits; i++) {
                if (POWER_FACTOR[i] == 0) continue;
                List<Integer> divisorList = divisors[i];
                int powerFactor = divisorList.stream().mapToInt(v -> POWER_FACTOR[v]).sum();
                POWER_FACTOR[i] = 1 - powerFactor;
            }

            System.out.println("processor 1: " + (System.currentTimeMillis() - time));
        }

        private static long higherPowers(long n) {
            long res = 0;
            for (int i = 5; ; i++) {
                int factor = POWER_FACTOR[i];
                if (factor == 0) continue;
                long contri = forHigherPower(n, i);
                if (contri == 0) break;
                if (factor == -1) contri = MOD - contri;
                res += contri;
            }

            return res >= MOD ? res % MOD : res;
        }

        private static long forHigherPower(long n, int power) {
            if ((n >>> power) == 0) return 0;
            long res = 0;
            int limit = getPowerRoot(n, power);
            for (int base = 2; base <= limit; base++) {
                long basePower = simplePow(base, power);
                res += getContribution(n, basePower);
            }

            return res >= MOD ? res % MOD : res;
        }

        private static long midProcess(long n) {
            long res = n % MOD;
            for (long pp : PERFECT_POWERS) {
                if (pp > n) break;
                res += getContribution(n, pp);
            }

            res = res % MOD;
            if (res < 0) res += MOD;
            return res;
        }

        public long process(long n) {
            if (n <= MID_LIMIT) return midProcess(n);
            long res = n % MOD;
            long bySquares = forSquares(n);
            long byCubes = forCubes(n);
            long byHigherPowers = higherPowers(n);
            res += bySquares + byCubes + byHigherPowers;
            res = res % MOD;
            return res < 0 ? res + MOD : res;
        }
    }

    private static class ProcessorV2 implements Processor {
        private static final long[] PERFECT_POWERS;

        static {
            long time = System.currentTimeMillis();
            int n = 1000000;
            List<Long> perfectPowers = new LinkedList<>();
            for (int i = 2; i <= n; i++) {
                if (checkSquare(i)) continue; // let's take square free numbers only.
                long j = 1L * i * i * i; // starting with power 3.
                long square = 1L * i * i;
                long limit = LIMIT / square;
                while (true) {
                    perfectPowers.add(j);
                    if (j > limit) break;
                    j *= square; // incrementing the power by two to keep it odd and square free.
                }
            }

            long[] powers = perfectPowers.stream()
//                    .filter(v -> !checkSquare(v)) // squares not needed, as already covered separately.
                    .mapToLong(v -> v)
                    .toArray();

            PERFECT_POWERS = removeDuplicates(powers);
//            System.out.println("processor 2: " + (System.currentTimeMillis() - time));
        }

        private static long higherPowers(long n) {
            long res = n % MOD;
            for (long pp : PERFECT_POWERS) {
                if (pp > n) break;
                res += getContribution(n, pp);
                res %= MOD;
            }

            res = res % MOD;
            if (res < 0) res += MOD;
            return res;
        }

        public long process(final long n) {
            ToLongFunction<Long> squareFunction = t -> forSquares(t);
            ToLongFunction<Long> higherPowerFunction = t -> higherPowers(t);

            ToLongFunction[] functions = new ToLongFunction[]{squareFunction, higherPowerFunction};
            long res = 0;
            res += Arrays.stream(functions)
                    .parallel()
                    .mapToLong(function -> function.applyAsLong(n))
                    .sum();

            return res % MOD;
        }
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