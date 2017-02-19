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
 * Problem Name: Chef and his Best Friend
 * Link: https://www.codechef.com/FEB17/problems/CHEFBEST
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndBestFriend {
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
            out.println(calculate(in.readIntArray(n)));
        }
    }

    private static int calculate(int[] ar) {
        if (ar.length == 1)
            return 0;

        int firstZero = firstOccurance(ar, 0), lastOne = lastOccurance(ar, 1);
        if (lastOne < firstZero)
            return 0;

        return count(ar, 1, firstZero, lastOne) + getDelay(ar, firstZero, lastOne);
    }

    private static int[] normalize(int[] ar, int firstZero, int lastOne) {
        int[] copy = new int[lastOne + 1 - firstZero];
        int index = 0;

        for (int i = firstZero; i <= lastOne; i++)
            copy[index++] = ar[i];

        return copy;
    }

    private static int getDelay(int[] ar) {
        return getDelay(ar, 0, ar.length - 1);
    }

    private static int getDelay(int[] ar, int start, int end) {
        int delay = 0;

        for (int i = end; i >= start; i--)
            if (ar[i] == 0)
                delay++;
            else
                delay = delay == 0 ? delay : delay - 1;

        return delay - 1;
    }

    private static void swap(int[] ar, int i, int j) {
        int t = ar[i];
        ar[i] = ar[j];
        ar[j] = t;
    }

    private static int firstOccurance(int[] ar, int val) {
        for (int i = 0; i < ar.length; i++)
            if (ar[i] == val)
                return i;

        return ar.length;
    }

    private static int lastOccurance(int[] ar, int val) {
        for (int i = ar.length - 1; i >= 0; i--)
            if (ar[i] == val)
                return i;

        return 0;
    }

    private static int count(int[] ar, int val) {
        return count(ar, val, 0, ar.length - 1);
    }

    private static int count(int[] ar, int val, int start, int end) {
        int count = 0;

        for (int i = start; i <= end; i++)
            if (ar[i] == val)
                count++;

        return count;
    }

    private static int bruteForce(int[] ar) {
        int count = 0;
        boolean change = true;

        while (change) {
            change = false;

            for (int i = 1; i < ar.length; i++) {
                if (ar[i] == 1 && ar[i - 1] == 0) {
                    change = true;
                    swap(ar, i, i - 1);
                    i++;
                }
            }

            if (change)
                count++;
        }

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
