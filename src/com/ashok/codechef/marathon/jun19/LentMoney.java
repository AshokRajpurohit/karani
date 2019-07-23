/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.jun19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Problem Name: Lent Money
 * Link: https://www.codechef.com/JUNE19A/problems/LENTMO
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class LentMoney {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int k = in.readInt(), x = in.readInt();
            out.println(calculate(ar, k, x));
        }
    }

    private static long calculate(int[] ar, int k, int x) {
        int len = ar.length;
        if (x == 0) return sum(ar);
        if (k == len) return Math.max(sum(ar), xorSum(ar, x));
        if (k == 1) return Arrays.stream(ar).mapToLong(i -> Math.max(i, i ^ x)).sum();
        NumberXorPair[] numberXorPairs = Arrays.stream(ar).mapToObj(n -> new NumberXorPair(n, n ^ x)).toArray(t -> new NumberXorPair[t]);
        return calculate(numberXorPairs, k);
    }

    private static long calculate(NumberXorPair[] numberXorPairs, int k) {
        long res = 0;
        for (NumberXorPair numberXorPair : numberXorPairs){
            res += Math.max(numberXorPair.number, numberXorPair.xor);
        }

        int positiveCount = (int) Arrays.stream(numberXorPairs).filter(n -> n.diff > 0).count();
        if (isEven(k)) {
            if (!isEven(positiveCount)) {
                int minPosDiff = Arrays.stream(numberXorPairs).filter(n -> n.diff >= 0).mapToInt(n -> n.diff).min().orElse(0);
                int maxNegDiff = Arrays.stream(numberXorPairs).filter(n -> n.diff < 0).mapToInt(n -> n.diff).max().orElse(0);
                res -= minPosDiff;
                if (maxNegDiff != 0) res += Math.max(0, minPosDiff + maxNegDiff);
            }
        }

        return res;
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    private static long calculateAllPositive(NumberXorPair[] numberXorPairs, int k) {
        int len = numberXorPairs.length;
        int left = len % k;
        left = Math.min(left, k - left);
        return IntStream.range(0, len - left).mapToLong(i -> numberXorPairs[i].xor).sum();
    }

    private static long sum(int[] ar) {
        long sum = 0;
        for (int e : ar) sum += e;
        return sum;
    }

    private static long xorSum(int[] ar, int xorValue) {
        long result = 0;
        for (int e : ar) result += e ^ xorValue;
        return result;
    }

    final static class NumberXorPair {
        final int number, xor, diff;

        NumberXorPair(int number, int xor) {
            this.number = number;
            this.xor = xor;
            this.diff = xor - number;
        }

        public String toString() {
            return "[" + number + " " + xor + " " + diff + "]";
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
    }
}