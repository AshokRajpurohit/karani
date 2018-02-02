/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan18;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Road
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-18/algorithm/road-1-63e2e618/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Road {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int LIMIT = 50;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), k = in.readInt();
        out.println(process(in.readIntArray(n), k));
    }

    private static int process(int[] ar, int k) {
        int ref = ar[0], min = Math.min(0, ref - k), max = ref + k;
        ar = normalize(ar, min, max);
        int len = ar.length;
        Bucket bucket = new Bucket(ar, max(ar) + 1);
        Bucket res = process(bucket, ar, k);
        return max(res.map[0]);
    }

    private static Bucket process(Bucket bucket, int[] ar, int k) {
        final int invalid = k + 1, len = ar.length;
        int limit = max(ar);
        Bucket res = new Bucket(len, limit + 1);
        int[][] map = res.map;
        for (int index = len - 1; index >= 0; index--) {
            int value = ar[index];
            int min = Math.max(0, value - k), max = Math.min(limit, value + k);
            for (int j = min; j <= max; j++) {
                int nextElementIndex = bucket.nextElementIndex(index, j);
                if (nextElementIndex == -1) {
                    map[index][0] = Math.max(map[index][0], 1);
                    continue;
                }
                int nextValue = ar[nextElementIndex];
                int cost = Math.abs(value - nextValue);
                for (int e : map[nextElementIndex]) {
                    map[index][cost] = Math.max(map[index][cost], e + 1);
                    if (++cost > k) break;
                }
            }
        }

        return res;
    }

    private static int max(int[] ar) {
        int max = ar[0];

        for (int e : ar)
            max = Math.max(e, max);

        return max;
    }

    private static int[] normalize(int[] ar, int min, int max) {
        int count = count(ar, min - 1, max + 1);
        if (count == ar.length) return ar;

        int[] res = new int[count];
        int index = 0;
        for (int e : ar)
            if (e >= min && e <= max) res[index++] = e;

        return res;
    }

    /**
     * Counts number of elements between {@code min} and {@code max} both exclusive.
     *
     * @param ar
     * @param min
     * @param max
     * @return number of elements in range.
     */
    private static int count(int[] ar, int min, int max) {
        int count = 0;
        for (int e : ar)
            if (e > min && e < max) count++;

        return count;
    }

    final static class Bucket {
        final int size;
        private int[][] map; // contains the list of next item's index for a given value.

        Bucket(int len, int size) {
            this.size = size;
            map = new int[len][size];
        }

        Bucket(int[] ar, int size) {
            this.size = size;
            populate(ar);
        }

        private void set(int index1, int index2, int value) {
            map[index1][index2] = value;
        }

        private void populate(int[] ar) {
            int len = ar.length;
            map = new int[len][size];
            Arrays.fill(map[len - 1], -1);
            int v = ar[len - 1];
            for (int index = len - 2; index >= 0; index--) {
                System.arraycopy(map[index + 1], 0, map[index], 0, size);
                map[index][v] = index + 1;
                v = ar[index];
            }
        }

        private int nextElementIndex(int index, int value) {
            return map[index][value];
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