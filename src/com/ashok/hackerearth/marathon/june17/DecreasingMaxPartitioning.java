/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.june17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Decreasing Max Partitioning
 * Contest: June Circuits 2017
 * Link: https://www.hackerearth.com/challenge/competitive/june-circuits-17/algorithm/decreasing-max-partitioning/
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class DecreasingMaxPartitioning {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int MOD = 1000000007;

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
            out.println(process(in.readIntArray(n)));
        }
    }

    /**
     * The maximum partition can be formed using the maximum numbers till the points from end.
     * For the array, [6, 4, 1, 3, 2] the maxArray (for maximum number of partitions) is
     * [6, 4, 3, 3, 2] or we can say there can be at max 4 partitions. The third element in array ar[2] i.e. 1
     * can be part of partion4 or partition 2.
     * <p>
     * For all the numbers which are smaller than maximum number and lies on left side of it are going to be
     * part of partition with max element in it.
     * <p>
     * Let's say we know the result for n element array, i.e. Fn. Now we add r smaller elements and then one
     * element larger than max element. Now there are two possibilities,
     * <p>
     * 1. Keep the original partitions as it is and add one more partition. r elements can be arranged between new
     * partition and left-most partition in (r + 1) ways.
     * 2. Merge the left most partition of all combinations into the newly added partition. There is only one way
     * to do it.
     * <p>
     * So number of partitions when new elements are added to left of the array is:
     * (r + 1) * Fn + Fn = (r + 2) * Fn.
     * <p>
     * For single element Fn is 1.
     * <p>
     * Now we can follow the same procedure recursively starting from the right-most element.
     *
     * @param ar
     * @return
     */
    private static long process(int[] ar) {
        int len = ar.length, index = len - 1;
        long res = 1;

        for (int i = len - 2; i >= 0; i--) {
            if (ar[i] < ar[index])
                continue;

            res = res * (index + 1 - i) % MOD;
            index = i;
        }

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