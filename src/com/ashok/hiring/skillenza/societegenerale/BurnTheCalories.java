/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.skillenza.societegenerale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Burn the calories
 * Link: https://skillenza.com/challenge/societegenerale-code-a-thon-javafullstack-mar/checkpoint/submit/1242
 * <p>
 * Zack is suffering from obesity and his doctor has advised him to go for running every day. However, he only has n days on which he can go for running.
 * The number of calories burned on the kth day is given by 2 * i + d, where i is the miles he has already covered on the previous (k-1)th
 * days and d is the number of miles he ran on the kth day.
 * <p>
 * You are given a collection of not necessarily distinct non-negative integers d1, d2, …, dn.
 * On any given day where he goes for a run, he runs exactly di kilometers where di belongs to this collection of numbers.
 * In other words, a number from this collection represents the allowed number of kilometers he can run on any given day.
 * <p>
 * At the end of n days, he has used up every number from this collection. That is, all allowed daily values have been used.
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BurnTheCalories {
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
            out.println(calculate(in.readIntArray(in.readInt())));
        }
    }

    private static long calculate(int[] ar) {
        Arrays.sort(ar);
        reverse(ar);
        long value = 0, milesSoFar = 0;
        for (int e : ar) {
            value += (milesSoFar << 1) + e;
            milesSoFar += e;
        }

        return value;
    }

    private static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
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