/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Gerrymander
 * Link: https://www.codechef.com/FEB17/problems/GERMANDE
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class Gerrymander {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String ZERO = "0\n", ONE = "1\n";
    private static int[] countArray;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 1);

        while (t > 0) {
            t--;

            int p = in.readInt(), q = in.readInt();
            sb.append(process(p, q, in.readIntArray(p * q)));
        }

        out.print(sb);
    }

    private static String process(int p, int q, int[] ar) {
        if (p == 1 || q == 1) {
            int oneCount = count(ar, 1);

            return oneCount * 2 > ar.length ? ONE : ZERO;
        }

        int minimumCount = minimumCount(p, q);
        if (count(ar, 1) < minimumCount)
            return ZERO;

        populateCounts(ar, q);
        int minStates = (p + 1) >>> 1, minCities = (q + 1) >>> 1;

        for (int i = 0; i < q; i++) {
            int stateWinCount = 0;
            for (int state = 0, j = i; state < p; state++, j += q) {
                if (queryCount(j, j + q - 1) >= minCities) {
                    stateWinCount++;

                    if (stateWinCount >= minStates)
                        return ONE;
                }
            }
        }

        return ZERO;
    }

    private static int queryCount(int start, int end) {
        return queryCount(end) - queryCount(start - 1);
    }

    private static int queryCount(int index) {
        if (index < 0)
            return 0;

        return countArray[index];
    }

    private static int[] getExtendedArray(int[] ar, int blockSize) {
        int[] res = new int[ar.length + blockSize - 1];

        for (int i = 0; i < ar.length; i++)
            res[i] = ar[i];

        for (int i = 0, j = ar.length; j < res.length; i++, j++)
            res[j] = ar[i];

        return res;
    }

    private static void populateCounts(int[] ar, int blockSize) {
        countArray = getExtendedArray(ar, blockSize);

        for (int i = 1; i < countArray.length; i++)
            countArray[i] += countArray[i - 1];
    }

    /**
     * Minimum votes needed to be president.
     * Atleast half of the states should have governer and for a state to have
     * governer, half of the cities should have representative.
     *
     * @param states
     * @param cities
     * @return
     */
    private static int minimumCount(int states, int cities) {
        return (states + 1) * (cities + 1) / 4; // same like half of the states, each with half of the cities.
    }

    private static int count(int[] ar, int val) {
        int count = 0;

        for (int e : ar)
            if (e == val)
                count++;

        return count;
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
