/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */

package com.ashok.codechef.marathon.year17.april;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * Problem Name: Chef and Divisor Tree
 * Link: https://www.codechef.com/APRIL17/problems/CHEFDIV
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndDivisorTree {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] primes = gen_prime(1000000);

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        long a = in.readLong(), b = in.readLong();
        LinkedList<Integer>[] factorListMap = getFactorListMap(a, b);
        long res = 0;

        for (LinkedList<Integer> list : factorListMap) {
            if (list == null || list.isEmpty())
                continue;

            res += calculateScore(toArray(list));
        }

        out.println(res);
    }

    private static LinkedList<Integer>[] getFactorListMap(long start, long end) {
        long offset = start;
        int length = (int) (end - start + 1);
        LinkedList<Integer>[] factorListMap = new LinkedList[length];
        long[] valueAr = new long[length];

        for (int i = 0; i < length; i++)
            valueAr[i] = offset + i;

        int upperLimit = (int) Math.sqrt(end) + 1;
        for (int e : primes) {
            if (e > upperLimit)
                break;

            long num = start + e - 1 - (start - 1) % e; // start + e - 1 is equivalent to start - 1, modulo e
            int index = (int) (num - offset);

            while (index < length) {
                int count = 1;
                while (valueAr[index] % e == 0) {
                    count++;
                    valueAr[index] /= e;
                }

                if (count != 1) {
                    ensureInitialized(factorListMap, index);
                    factorListMap[index].addLast(count);
                }

                index += e;
            }
        }

        for (int i = 0; i < valueAr.length; i++) {
            if (valueAr[i] != 1) {
                ensureInitialized(factorListMap, i);
                factorListMap[i].addLast(2);
            }
        }

        return factorListMap;
    }

    private static void ensureInitialized(LinkedList<Integer>[] listAr, int index) {
        if (listAr[index] == null)
            listAr[index] = new LinkedList<>();
    }

    private static long calculateScore(int[] factorCounts) {
        if (factorCounts.length == 1) {
            int value = factorCounts[0];

            return (value * (value + 1)) / 2 - 1;
        }
        Arrays.sort(factorCounts);
        reverse(factorCounts);

        long res = 0, factor = multiply(factorCounts);
        int ref = 0, count = factorCounts.length;
        res = factor;

        while (factor > 1) {
            ref = factorCounts[0];

            for (int i = 0; i < factorCounts.length; i++) {
                if (factorCounts[i] < ref)
                    break;

                factor = factor * (ref - 1) / ref;
                if (factor <= 1)
                    return res;

                res += factor;
                factorCounts[i]--;
            }
        }

        return res;
    }

    private static int[] toArray(LinkedList<Integer> list) {
        int[] ar = new int[list.size()];
        int index = 0;

        for (int e : list)
            ar[index++] = e;

        return ar;
    }

    private static long multiply(int[] ar) {
        long res = 1;
        for (int e : ar)
            res *= e;

        return res;
    }

    private static long pow(int num, int pow) {
        long res = num;

        while (pow > 1) {
            res *= num;
            pow--;
        }

        return res;
    }

    private static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    /**
     * This function generates prime numbers upto given integer n and
     * returns the array of primes upto n (inclusive).
     * {@link https://github.com/AshokRajpurohit/karani/blob/master/src/com/ashok/lang/math/Prime.java}
     *
     * @param n prime numbers upto integer n
     * @return
     */
    public static int[] gen_prime(int n) {
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
