/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.june;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem Name: Pairwise union of sets
 * Link: https://www.codechef.com/JUNE17/problems/UNIONSET
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PairwiseUnionOfSets {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final ArrayLengthComparator ARRAY_LENGTH_COMPARATOR = new ArrayLengthComparator();
    private static final int MOD = 3126269;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;

            int n = in.readInt(), k = in.readInt();

            int[][] sets = new int[n][];
            for (int i = 0; i < n; i++) {
                int len = in.readInt();
                sets[i] = in.readIntArray(len);
                Arrays.sort(sets[i]);
            }

            out.println(process(sets, k));
        }
    }

    private static int process(int[][] sets, int setSize) {
        Arrays.sort(sets, ARRAY_LENGTH_COMPARATOR);
        CustomArray[] customArrays = toCustomArrays(sets);

        int begin = 0, end = sets.length - 1;
        int count = 0;

        while (end >= 0 && customArrays[end].length == setSize) {
            count += end; // number of other elements, it can be grouped with.
            end--;
        }

        if (end < 0)
            return count;

        while (begin < sets.length && customArrays[begin].length + customArrays[end].length < setSize) // this set is never going to
            begin++; // form a pair for complete set.

        int cur = 0;
        while (begin < end) {
            if (begin > 0 && customArrays[begin].equals(customArrays[begin - 1])) { // let's not re-calculate
                count += cur; // when we already know the outcome.
                begin++;
                continue;
            }

            cur = 0;
            CustomArray customArray = customArrays[begin];
            for (int i = end; i > begin; i--)
                if (completeSet(customArray.array, customArrays[i].array, setSize))
                    cur++;

            begin++;
            count += cur;
        }

        return count;
    }

    private static boolean completeSet(int[] a, int[] b, int size) {
        if (a.length + b.length < size)
            return false;

        int value = 1, ai = 0, bi = 0;
        while (ai < a.length && bi < b.length) {
            if (a[ai] != value && b[bi] != value)
                return false;

            if (a[ai] == value)
                ai++;

            if (b[bi] == value)
                bi++;

            value++;
        }

        while (ai < a.length) {
            if (a[ai++] != value++)
                return false;
        }

        while (bi < b.length) {
            if (b[bi++] != value++)
                return false;
        }

        return value == size + 1;
    }

    private static CustomArray[] toCustomArrays(int[][] intArrays) {
        int length = intArrays.length;
        CustomArray[] ar = new CustomArray[length];

        for (int i = 0; i < length; i++)
            ar[i] = new CustomArray(intArrays[i]);

        return ar;
    }

    private static int getHashValue(int[] ar) {
        long value = 0;
        int multiplier = 1;
        for (int e : ar) {
            value += multiplier * e;
            multiplier++;
        }

        return (int) (value % MOD);
    }

    final static class CustomArray implements Comparable<CustomArray> {
        final int[] array;
        final int hashValue, length;

        CustomArray(int[] ar) {
            array = ar;
            length = ar.length;
            hashValue = getHashValue(ar);
        }

        @Override
        public int compareTo(CustomArray customArray) {
            if (length != customArray.length)
                return length - customArray.length;

            return hashValue - customArray.hashValue;
        }

        @Override
        public int hashCode() {
            return hashValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof CustomArray))
                return false;

            CustomArray customArray = (CustomArray) o;
            return equals(customArray);
        }

        public boolean equals(CustomArray customArray) {
            if (length != customArray.length || hashValue != customArray.hashValue)
                return false;

            return Arrays.equals(array, customArray.array);
        }
    }

    final static class ArrayLengthComparator implements Comparator<int[]> {

        @Override
        public int compare(int[] o1, int[] o2) {
            return o1.length - o2.length;
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