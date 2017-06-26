/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.indiahacks;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Hackers with Bits
 * Link: https://www.hackerearth.com/challenge/competitive/programming-indiahacks-2017/algorithm/hack-the-string-9dce7834/
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class HackersWithBits {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        out.println(process(in.readIntArray(n)));
    }

    private static int process(int[] ar) {
        int n = ar.length, oneCount = getCount(ar, 1);
        if (oneCount <= 2 || oneCount >= n - 1)
            return oneCount;

        int[] prev = getPreviousCounts(ar), next = getNextCounts(ar);
        int count = 0;
        for (int i = 0; i < n && count < oneCount; i++)
            if (ar[i] == 0)
                count = Math.max(count, prev[i] + next[i] + 1);

        return Math.min(count, oneCount);
    }

    private static int getCount(int[] ar, int value) {
        int count = 0;
        for (int e : ar)
            if (value == e)
                count++;

        return count;
    }

    private static int[] getPreviousCounts(int[] booleanValueArray) {
        int[] res = new int[booleanValueArray.length];
        for (int i = 1; i < booleanValueArray.length; i++)
            res[i] = booleanValueArray[i - 1] == 1 ? res[i - 1] + 1 : 0;

        return res;
    }

    private static int[] getNextCounts(int[] booleanValueArray) {
        int[] res = new int[booleanValueArray.length];
        for (int i = booleanValueArray.length - 2; i >= 0; i--)
            res[i] = booleanValueArray[i + 1] == 1 ? res[i + 1] + 1 : 0;

        return res;
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