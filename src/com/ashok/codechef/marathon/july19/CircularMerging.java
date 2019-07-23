/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.july19;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Circular Merging
 * Link: https://www.codechef.com/JULY19A/problems/CIRMERGE#
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CircularMerging {
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
            int[] nums = in.readIntArray(n);
            out.println(calculateMinimumPenalty(nums));
        }
    }

    private static long calculateMinimumPenalty(int[] nums) {
        int len = nums.length;
        if (len == 2) return nums[0] + nums[1];
        return new PenaltyGame(nums).calculate();
    }

    private final static class PenaltyGame {
        final int size;
        private final long[][] matrix;
        private final int[] scores;
        private final CircularSumArray sumArray;

        public PenaltyGame(int[] scores) {
            this.size = scores.length;
            this.scores = scores;
            sumArray = new CircularSumArray(scores);
            matrix = new long[size][size];
            for (long[] rows : matrix)
                Arrays.fill(rows, -1);

            for (int i = 0; i < size; i++)
                matrix[i][i] = 0;
        }

        public long calculate() {
            process();
            long result = matrix[0][size - 1];
            for (int i = 1; i < size; i++)
                result = Math.min(result, matrix[i][i - 1]);

            return result;
        }

        private void process() {
            if (matrix[0][size - 1] != -1)
                return; // already processed.

            process(0, size - 1);
            for (int i = 1; i < size; i++)
                process(i, i - 1);
        }

        private long process(int from, int to) {
            if (matrix[from][to] != -1) {
                return matrix[from][to];
            }

            if (to < from) {
                return process0(from, to);
            }

            long result = Long.MAX_VALUE;
            for (int i = from + 1; i <= to; i++) {
                result = Math.min(result, process(from, i - 1) + process(i, to));
            }

            matrix[from][to] = result + sumArray.getSum(from, to);
            return matrix[from][to];
        }

        /**
         * process for the subarray when the last element is part of subarray, i.e. to < from in circular array.
         *
         * @param from
         * @param to
         */
        private long process0(int from, int to) {
            long result = Long.MAX_VALUE;
            for (int i = from + 1; i < size; i++) {
                result = Math.min(result, process(from, i - 1) + process(i, to));
            }

            result = Math.min(result, process(from, size - 1) + process(0, to));
            for (int i = 1; i <= to; i++) {
                result = Math.min(result, process(from, i - 1) + process(i, to));
            }

            matrix[from][to] = result + sumArray.getSum(from, to);
            return matrix[from][to];
        }
    }

    final static class CircularSumArray {
        final int size;
        private final int[] ar;
        private final long[] sumArray;

        CircularSumArray(final int[] ar) {
            size = ar.length;
            this.ar = ar;
            sumArray = new long[size];
            initialize();
        }

        public long getSum(int from, int to) {
            return from <= to ? getSum0(from, to) : getSum0(from, size - 1) + getSum0(0, to);
        }

        private long getSum0(int from, int to) {
            return getValueAtIndex(to) - getValueAtIndex(from - 1);
        }

        private long getValueAtIndex(int index) {
            return index < 0 ? 0 : sumArray[index];
        }

        private void initialize() {
            long sum = 0;
            int index = 0;
            for (int e : ar) {
                sum += e;
                sumArray[index++] = sum;
            }
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