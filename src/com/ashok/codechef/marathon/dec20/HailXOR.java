/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.dec20;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.function.ToIntBiFunction;
import java.util.stream.IntStream;

/**
 * Problem Name: Hail XOR
 * Link: https://www.codechef.com/DEC20A/problems/HXOR
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class HailXOR {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        test();
        solve();
        in.close();
        out.close();
    }

    private static void test() throws IOException {
        while (true) {
            int n = in.readInt(), x = in.readInt(), size = in.readInt();
            while (true) {
                int[] ar = Generators.generateRandomIntegerArray(n, 1, size);
                int[] br = ar.clone(), cr = ar.clone();
                long time = System.currentTimeMillis();
                naive(br, x);
                naive(cr, x);
                boolean match = Arrays.equals(br, cr);
                if (!match) {
                    br = ar.clone();
                    cr = ar.clone();
                    naive(br, x);
                    naive(cr, x);
                }
                out.println("results are matching: " + match);
                out.println("success and time taken is: " + (System.currentTimeMillis() - time) + " ms");
                out.flush();
            }
        }
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
//            t--;
            int n = in.readInt(), x = in.readInt();
            int[] ar = in.readIntArray(n);
            naive(ar, x);
            out.println(toString(ar));
            out.flush();
        }
    }

    private static void naive(int[] ar, int x) {
        int[] orig = ar.clone();
        final int n = ar.length, last = n - 1;
        ToIntBiFunction<Integer, Integer> nextMatch = nextMatch(ar, last);

        for (int i = 0; i < last && x > 0; i++) {
            int e = ar[i], p = Integer.highestOneBit(e);
            while (p > 0 && x > 0) {
                if ((e & p) == p) {
                    int next = nextMatch.applyAsInt(i + 1, p);
                    ar[next] ^= p;
                    ar[i] ^= p;
                    x--;
                }
                p >>>= 1;
            }
        }

        if (n > 2 || (x & 1) == 0) return;

        ar[n - 2] ^= 1;
        ar[n - 1] ^= 1;
    }

    private static ToIntBiFunction<Integer, Integer> nextMatch(int[] ar, int last) {
        ToIntBiFunction<Integer, Integer> nextMatch = (index, r) -> {
            while (index < ar.length) {
                if ((ar[index] & r) == r) {
                    return index;
                }

                index++;
            }

            return last;
        };
        return nextMatch;
    }

    private static boolean isEven(int r) {
        return (r & 1) == 0;
    }

    private static String toString(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length);
        for (int e : ar) sb.append(e).append(' ');
        return sb.toString();
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