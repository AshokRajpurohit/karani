/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.dec;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Chef and Hamming Distance of arrays
 * Link: https://www.codechef.com/DEC17/problems/CHEFHAM
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndHammingDistance {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(1000);
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int[] res = process(ar);
            sb.append(hammingDistance(ar, res)).append('\n');
            append(sb, res);
        }

        out.print(sb);
    }

    private static int[] process(int[] ar) {
        int len = ar.length;
        if (len == 1) return ar;

        Pair[] pairs = toPairs(ar);
        shiftValues(pairs, duplicate(pairs) ? 2 : 1);
        return toArray(pairs);
    }

    private static boolean duplicate(Pair[] pairs) {
        int len = pairs.length;
        if (len < 4) return false;

        for (int i = 1; i < len; i++)
            if (pairs[i].value == pairs[i - 1].value)
                return true;

        return false;
    }

    private static int max(int[] ar) {
        int max = 0;
        for (int e : ar)
            max = Math.max(max, e);

        return max;
    }

    private static void shiftValues(Pair[] pairs, int shifts) {
        if (shifts == 0)
            return;

        int value = pairs[0].value, temp = 0, len = pairs.length;
        for (int i = 1; i < len; i++) {
            temp = pairs[i].value;
            pairs[i].value = value;
            value = temp;
        }

        pairs[0].value = value;
        shiftValues(pairs, shifts - 1);
    }

    private static void append(StringBuilder sb, int[] ar) {
        for (int e : ar)
            sb.append(e).append(' ');

        sb.append('\n');
    }

    private static Pair[] toPairs(int[] ar) {
        int len = ar.length, max = max(ar), index = 0;
        int[] first = new int[max + 1], second = new int[max + 1];
        Arrays.fill(first, -1);
        Arrays.fill(second, -1);

        for (int e : ar) {
            if (first[e] == -1) first[e] = index++;
            else second[e] = index++;
        }

        Pair[] pairs = new Pair[len];
        index = 0;
        for (int i = 1; i <= max; i++) {
            if (first[i] != -1) pairs[index++] = new Pair(first[i], i);
            if (second[i] != -1) pairs[index++] = new Pair(second[i], i);
        }

        return pairs;
    }

    private static int[] toArray(Pair[] pairs) {
        int len = pairs.length;
        int[] res = new int[len];
        for (Pair pair : pairs)
            res[pair.index] = pair.value;

        return res;
    }

    private static int hammingDistance(int[] source, int[] target) {
        int count = 0, len = source.length;
        if (len >= 4) return len;

        for (int i = 0; i < len; i++)
            if (source[i] != target[i]) count++;

        return count;
    }

    final static class Pair implements Comparable<Pair> {
        final int index;
        int value;

        Pair(int i, int v) {
            index = i;
            value = v;
        }

        @Override
        public int compareTo(Pair pair) {
            return value - pair.value;
        }

        public String toString() {
            return index + " -> " + value;
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