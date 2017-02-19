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
 * Problem Name: Most Frequent Element
 * Link: https://www.codechef.com/FEB17/problems/MFREQ
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class MostFrequentElement {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        Query query = new Query(in.readIntArray(n));

        StringBuilder sb = new StringBuilder(q << 3);
        while (q > 0) {
            q--;

            sb.append(query.findNumber(in.readInt() - 1, in.readInt() - 1, in.readInt())).append('\n');
        }

        out.print(sb);
    }

    final static class Query {
        int[] ar, leftCountArray, rightCountArray;

        Query(int[] ar) {
            this.ar = ar;
            populate();
        }

        public int findNumber(int left, int right, int count) {
            int mid = (left + right) >>> 1;

            int leftCount = Math.min(getLeftCountValue(mid), mid - left);
            int rightCount = Math.min(getRightCountValue(mid), right - mid);

            if (leftCount + rightCount + 1 >= count)
                return ar[mid];

            return -1;
        }

        private int getLeftCountValue(int index) {
            if (index <= 0)
                return 0;

            return leftCountArray[index];
        }

        private int getRightCountValue(int index) {
            if (index >= ar.length - 1)
                return 0;

            return rightCountArray[index];
        }

        private void populate() {
            leftCountArray = new int[ar.length];
            rightCountArray = new int[ar.length];

            updateLeftCount();
            updateRightCount();
        }

        private void updateLeftCount() {
            for (int i = 1; i < ar.length; i++)
                if (ar[i] == ar[i - 1])
                    leftCountArray[i] = leftCountArray[i - 1] + 1;
        }

        private void updateRightCount() {
            for (int i = ar.length - 2; i >= 0; i--)
                if (ar[i] == ar[i + 1])
                    rightCountArray[i] = rightCountArray[i + 1] + 1;
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
