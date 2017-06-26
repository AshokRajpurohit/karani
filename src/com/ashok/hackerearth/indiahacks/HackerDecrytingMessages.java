/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.indiahacks;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * Problem Name: Hacker Decryting Messages
 * Link: https://www.hackerearth.com/challenge/competitive/programming-indiahacks-2017/algorithm/hacker-with-prime-bebe28ac/
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class HackerDecrytingMessages {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private int[] numbers, primes, twoPrimeMultiples;
    private int limit = 1000000; // default value.
    private boolean[] convertibleNumbers;
    private static final String YES = "YES", NO = "NO";

    public static void main(String[] args) throws IOException {
        HackerDecrytingMessages object = new HackerDecrytingMessages();
        object.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        numbers = in.readIntArray(n);
        int[] queries = in.readIntArray(q);
        limit = Math.max(getMax(numbers), getMax(queries));
        process();

        StringBuilder sb = new StringBuilder(q << 2);
        for (int e : queries)
            sb.append(convertibleNumbers[e] ? YES : NO).append('\n');

        out.print(sb);
    }

    private void process() {
        numbers = normalize(numbers);
        populate();
    }

    private static int[] normalize(int[] ar) {
        boolean[] map = new boolean[getMax(ar) + 1];
        for (int e : ar)
            map[e] = true;

        map[0] = false;
        map[1] = false;
        return getIndexArray(map, true);
    }

    private void populate() {
        primes = generatePrimes(limit + 1);
        twoPrimeMultiples = generateTwoPrimesMultiples(primes, limit + 1);
        convertibleNumbers = generateConvertibleNumbersMap(twoPrimeMultiples, numbers, limit + 1);
    }

    private static int[] generatePrimes(int n) {
        boolean[] ar = new boolean[n];
        Arrays.fill(ar, 2, n, true); // asume all are primes.

        int root = (int) Math.sqrt(n);
        for (int i = 2; i <= root; i++) {
            if (ar[i]) {
                for (int j = i << 1; j < n; j += i) {
                    ar[j] = false; // j is divisible by i, so not a prime.
                }
            }
        }

        return getIndexArray(ar, true);
    }

    private static int[] generateTwoPrimesMultiples(int[] primes, int n) {
        boolean[] ar = new boolean[n];
        int sqrt = (int) Math.sqrt(n);

        for (int i = 0; primes[i] <= sqrt; i++) {
            for (int j = i; primes[i] * primes[j] < n; j++) {
                ar[primes[i] * primes[j]] = true;
            }
        }

        return getIndexArray(ar, true);
    }

    private static boolean[] generateConvertibleNumbersMap(int[] primeMultiples, int[] numbers, int n) {
        boolean[] ar = new boolean[n];
        for (int e : primeMultiples) {
            ar[e] = true;
            for (int f : numbers) {
                long num = 1L * e * f;
                if (num >= n || num < 0)
                    break;

                while (num < n && num >= 0) {
                    ar[(int) num] = true;
                    num *= f;
                }
            }
        }

        return ar;
    }

    private static int[] getIndexArray(boolean[] ar, boolean value) {
        int length = getCount(ar, true);
        int[] res = new int[length];
        for (int i = 2, index = 0; index < length; i++)
            if (ar[i])
                res[index++] = i;

        return res;
    }

    private static int getCount(boolean[] ar, boolean value) {
        int count = 0;
        for (boolean e : ar)
            if (e == value)
                count++;

        return count;
    }

    private static int getMax(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(e, max);

        return max;
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