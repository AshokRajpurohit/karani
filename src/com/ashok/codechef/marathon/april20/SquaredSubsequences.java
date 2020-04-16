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
import java.util.function.Predicate;

/**
 * Problem Name: Squared Subsequences
 * Link: https://www.codechef.com/APRIL20A/problems/SQRDSUB
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class SquaredSubsequences {
    private static final Predicate<Integer> SINGLE_TWO_PREDICATE = v -> (v & 3) == 2; // zero is not included.
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
            out.println(process(ar));
        }
    }

    private static long process(int[] ar) {
        int n = ar.length;
        for (int i = 0; i < n; i++) ar[i] = Math.abs(ar[i]);
        int[] prevEvenIndices = prevEvenIndices(ar);
        long[] res = new long[n];
        for (int i = 0; i < n; i++) {
            long count = 0;
            int value = ar[i];
            if (divisibileBy4(value)) {
                count = i + 1;
            } else if (isOdd(value)) {
                int prevTwo = prevEvenIndices[i];
                count = i - prevTwo;
                if (prevTwo != -1) count += res[prevTwo];
            } else {
                int prevTwo = prevEvenIndices[i];
                count = prevTwo + 1;
            }

            res[i] = count;
        }

        return Arrays.stream(res).sum();
    }

    private static long bruteForce(int[] ar) {
        int res = 0, n = ar.length;
        for (int i = 0; i < n; i++) ar[i] = Math.abs(ar[i]);
        for (int i = 0; i < n; i++) {
            int multiple = 1;
            for (int j = i; j >= 0; j--) {
                multiple = multiple * ar[j];
                if (divisibileBy4(multiple)) {
                    res += j + 1;
                    break;
                }
                if (isGoodNum(multiple)) res++;
                if (isOdd(multiple)) multiple = 1;
            }
        }

        return res;
    }

    private static boolean isOdd(long n) {
        return (n & 1) == 1;
    }

    private static boolean divisibileBy4(long n) {
        return (n & 3) == 0; // zero is included.
    }

    private static int[] prevEvenIndices(int[] ar) {
        int value = -1, n = ar.length;
        int[] res = new int[n];
        res[0] = -1;
        for (int i = 0; i < n; i++) {
            res[i] = value;
            if (!isOdd(ar[i])) value = i;
        }

        return res;
    }

    private static boolean isGoodNum(long n) {
        return divisibileBy4(n) || isOdd(n);
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}