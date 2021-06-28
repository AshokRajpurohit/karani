/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.feb21;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: Prime Game
 * Link: https://www.codechef.com/FEB21B/problems/PRIGAME
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PrimeGame {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int limit = 1000000;
    private static final String CHEF = "Chef", DIVYAM = "Divyam";
    private static final boolean[] primes;
    private static final int[] countPrimes;
    private static final int[] counts;

    static {
        primes = new boolean[limit + 1];
        countPrimes = new int[limit + 1];

        counts = new int[limit + 1];
        Arrays.fill(primes, true);
        primes[0] = false;
        counts[1] = 0;
        for (int i = 2; i <= 1000; i++) {
            if (counts[i] != 0) continue;
            for (int j = i; j <= limit; j += i) {
                counts[j]++;
                primes[j] = false;
            }
            primes[i] = true;
        }

        for (int i = 2; i <= limit; i++) {
            countPrimes[i] = countPrimes[i - 1] + (primes[i] ? 1 : 0);
        }

        for (int i = 1001; i <= limit; i++) if (counts[i] == 0) counts[i] = 1;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder();
        while (t > 0) {
            t--;
            int x = in.readInt(), y = in.readInt();
            sb.append(process(x, y)).append('\n');
        }

        out.println(sb);
    }

    private static String process(int x, int y) {
        if (x == 1) return CHEF;
        return y >= countPrimes[x] ? CHEF : DIVYAM;
    }

    private static int[] map;

    private static String bruteForce(int x, int y) {
        if (x == 1) return CHEF;
        int fact = 1;
        while (x > 0) fact = fact * x--;
        map = new int[fact + 1];
        return bf(fact, y) ? CHEF : DIVYAM;
    }

    private static boolean bf(int x, int y) {
        if (counts[x] <= y || x == 1) return true;
        if (x == 0) return false;
        if (map[x] != 0) return map[x] == 1;
        map[x] = 1;
        for (int i = x; i > 0; i--) {
            if (counts[i] > y) continue;
            if (!bf(x - i, y)) {
                return true;
            }
        }

        map[x] = 2;

        return false;
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
    }
}