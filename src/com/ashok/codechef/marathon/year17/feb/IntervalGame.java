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
 * Problem Name: Interval Game
 * Link: https://www.codechef.com/FEB17/problems/INTERVAL
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class IntervalGame {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static long[] sums;
    private static int[] segmentLengths;
    private static RangeQueryBlock queryBlock;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;

            int n = in.readInt(), m = in.readInt();
            int[] ar = in.readIntArray(n), br = in.readIntArray(m);

            sb.append(useBlockQuery(ar, br)).append('\n');
        }

        out.print(sb);
    }

    private static long useBlockQuery(int[] ar, int[] br) {
        populateSums(ar);
        segmentLengths = br;
        updateQueryBlock(br.length - 1);

        calculateUsingBlockQuery(br.length - 2);
        return queryBlock.query(0);
    }

    private static void updateQueryBlock(int segmentIndex) {
        int segmentLength = segmentLengths[segmentIndex], size = sums.length - segmentLength + 1;
        long[] ar = new long[size];

        for (int i = 0; i < ar.length; i++)
            ar[i] = getSum(i, i + segmentLength - 1);

        queryBlock = new RangeQueryBlock(ar, getBlockLength(segmentIndex));
    }

    private static int getBlockLength(int segmentIndex) {
        if (segmentIndex == 0)
            return sums.length + 1 - segmentLengths[segmentIndex];

        return segmentLengths[segmentIndex - 1] - segmentLengths[segmentIndex] - 1;
    }

    private static void calculateUsingBlockQuery(int segmentIndex) {
        if (segmentIndex < 0)
            return;

        int segmLen = segmentLengths[segmentIndex], length = sums.length + 1 - segmLen;
        long[] ar = new long[length];

        for (int start = 0, end = segmLen - 1; end < sums.length; start++, end++)
            ar[start] = getSum(start, end) - queryBlock.query(start + 1);

        queryBlock = new RangeQueryBlock(ar, getBlockLength(segmentIndex));
        calculateUsingBlockQuery(segmentIndex - 1);
    }

    private static void populateSums(int[] ar) {
        sums = new long[ar.length];
        sums[0] = ar[0];

        for (int i = 1; i < ar.length; i++)
            sums[i] = sums[i - 1] + ar[i];
    }

    private static long getSum(int start, int end) {
        return getSum(end) - getSum(start - 1);
    }

    private static long getSum(int index) {
        if (index < 0)
            return 0;

        return sums[index];
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

    /**
     * This class is to query min or max in a given range when the range is
     * always more than block size.
     * if the range size is always equal to block size then querying can be done
     * in constant time. for small block size it's better to use
     * {@link SparseTable}.
     *
     * @author Ashok Rajpurohit ashok1113@gmail.com
     */
    final static class RangeQueryBlock {
        private long[] qar, rar; // qar is for left to right and rar is for right to left.
        private int block;

        public RangeQueryBlock(long[] ar, int k) {
            block = k;
            qar = new long[ar.length];
            rar = new long[ar.length];

            process(ar);
        }

        private long query(int index) {
            return Math.max(qar[index + block - 1], rar[index]);
        }

        private void process(long[] ar) {
            qar[0] = ar[0];
            for (int i = 1; i < ar.length; i++) {
                if (i % block == 0)
                    qar[i] = ar[i];
                else
                    qar[i] = Math.max(ar[i], qar[i - 1]);
            }

            rar[ar.length - 1] = ar[ar.length - 1];
            for (int i = ar.length - 2; i >= 0; i--) {
                if (i % block == block - 1)
                    rar[i] = ar[i];
                else
                    rar[i] = Math.max(ar[i], rar[i + 1]);
            }
        }
    }
}
