/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.april20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Problem Name: Strange Number
 * Link: <a href="https://www.codechef.com/APRIL20A/problems/STRNO#">codechef</a>
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class StrangeNumber {
    private static final int LIMIT = 1000000000;
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] primes = generatePrimes((int) Math.sqrt(LIMIT) + 1);

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        test();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            sb.append(process(in.readInt(), in.readInt()))
                    .append('\n');
        }

        out.println(sb);
    }

    private static void test() throws IOException {
        Random random = new Random();
        int count = in.readInt();
        while (count > 0) {
            int n = 1 + random.nextInt(LIMIT + 1);
            List<Factor> factors = factors(n);
            int factorMulti = factors.stream()
                    .mapToInt(factor -> factor.value)
                    .reduce((a, b) -> a * b)
                    .orElse(1);

            if (factorMulti != n) {
                out.println("factors are not correct for " + n);
                out.flush();
                break;
            }
        }
    }

    private static int process(int divisorCount, int primeFactorCount) {
        if (divisorCount == 1) return 0;
        if (primeFactorCount == 1) return 1;
//        if (primeFactorCount >= 32) return 0;
//        if ((divisorCount >>> primeFactorCount) == 1) return 1;
//        if ((divisorCount >>> primeFactorCount) == 0) return 0;
        List<Factor> factors = factors(divisorCount);
        int sum = factors.stream().mapToInt(pf -> pf.power).sum();
        // A = f1(p1).f2(p2)...fk(pk)     fi(pi) is same as fi raised to power pi
        // X = (p1+1).(p2+1)..(pk+1) X >= 2^k
        // X = g1(c1).g2(c2)..gm(cm)  c1, c2, ..., cm can be partitioned into k groups
        // Signam(ci) >= k
        return sum >= primeFactorCount ? 1 : 0;
    }

    private static List<Factor> factors(int n) {
        List<Factor> primeFactors = new LinkedList<>();
        for (int prime : primes) {
            if (prime > n) break;
            if (n % prime != 0) continue;
            int power = powers(prime, n);
            n = removeFactor(n, prime);
            primeFactors.add(new Factor(prime, power));
        }

        if (n != 1) {
            primeFactors.add(new Factor(n, 1));
        }

        return primeFactors;
    }

    private static int powers(int factor, int number) {
        int count = 0;
        while (number >= factor && number % factor == 0) {
            count++;
            number /= factor;
        }

        return count;
    }

    private static int removeFactor(int n, int factor) {
        while (n % factor == 0) {
            n /= factor;
        }

        return n;
    }

    /**
     * This function generates prime numbers upto given integer n and
     * returns the array of primes upto n (inclusive).
     *
     * @param n prime numbers upto integer n
     * @return
     */
    private static int[] generatePrimes(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];
        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }
        return ret;
    }

    final static class Factor {
        static final Factor DEFAULT = new Factor(1, 0);
        final int factor, power;
        final int value;

        Factor(int factor, int power) {
            this.factor = factor;
            this.power = power;
            int value = 1;
            while (power > 0) {
                power--;
                value = value * factor;
            }

            this.value = value;
        }

        public String toString() {
            return factor + "^" + power;
        }
    }

    final static class InputReader {
        private final InputStream in;
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
    }
}