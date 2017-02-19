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
 * Problem Name: Chef and His Apartment Dues
 * Link: https://www.codechef.com/FEB17/problems/CHEFAPAR
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndHisApartmentDues {
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
            int[] rentInfo = in.readIntArray(n);
            out.println(calculateAmmount(rentInfo));
        }
    }

    private static int calculateAmmount(int[] ar) {
        int firstUnpaidMonth = firstUnpaidMonth(ar);

        if (firstUnpaidMonth == ar.length)
            return 0;

        int unpaidMonths = ar.length - firstUnpaidMonth - count(ar, 1, firstUnpaidMonth + 1);
        return unpaidMonths * 1000 + (ar.length - firstUnpaidMonth) * 100;
    }

    private static int firstUnpaidMonth(int[] ar) {
        for (int i = 0; i < ar.length; i++)
            if (ar[i] == 0)
                return i;

        return ar.length;
    }

    private static int count(int[] ar, int val, int start) {
        int count = 0;

        for (int i = start; i < ar.length; i++)
            if (ar[i] == val)
                ++count;

        return count;
    }

    private static int count(int[] ar, int val) {
        int count = 0;

        for (int e : ar)
            if (e == val)
                ++count;

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
