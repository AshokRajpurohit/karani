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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Min Difference Queries
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-18/algorithm/min-difference-queries-f5b9c199/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MinDifferenceQueries {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] ar = in.readIntArray(n);
        QueryProcessor query = getQuery(ar);
        StringBuilder sb = new StringBuilder(q << 2);
        int ans = 0;
        for (int i = 0; i < q; i++) {
            int from = (in.readInt() + ans) % n;
            int to = (in.readInt() + ans) % n;
            ans = query.query(from, to);
            sb.append(ans).append('\n');
        }

        out.println(sb);
    }

    private static QueryProcessor getQuery(int[] ar) {
        int[] uniqueuElements = getUniqueElements(ar);
        if (uniqueuElements.length == 1) return new AllSameElementsQuery();

        return uniqueuElements.length == ar.length ? new UniqueElementQuery() : new BruteForceQuery(ar);
    }

    private static boolean checkIfUniqueElements(int[] ar) {
        return getUniqueElements(ar).length == ar.length;
    }

    private static int[] getUniqueElements(int[] ar) {
        int[] a = ar.clone();
        Arrays.sort(a);
        int count = 1, ref = a[0];
        for (int e : a)
            if (e != ref) {
                ref = e;
                count++;
            }

        int[] res = new int[count];
        int index = 1;
        ref = res[0] = a[0];
        for (int e : a) {
            if (e != ref) {
                ref = e;
                res[index++] = ref;
            }
        }

        return res;
    }

    private static int calculateMinDiff(List<Integer> c) {
        int[] ar = toArray(c);
        Arrays.sort(ar);
        int min = ar[1] - ar[0];
        for (int i = 2; i < ar.length; i++)
            min = Math.min(min, ar[i] - ar[i - 1]);

        return min;
    }

    private static int[] toArray(Collection<Integer> c) {
        int[] ar = new int[c.size()];
        int index = 0;
        for (int e : c)
            ar[index++] = e;

        return ar;
    }

    /**
     * Returns array of integers having index of next equal element to it.
     * If the element doesn't have any such element then the index for it would
     * be the array size.
     * <p>
     * for array
     * {1, 2, 3, 2, 1}
     * next equal elements indices should be
     * {4, 3, 5, 5, 5}
     * <p>
     * The parameter array doesn't need to be sorted.
     *
     * @param ar
     * @return
     */
    private static int[] nextEqual(int[] ar) {
        int len = ar.length;
        int[] res = new int[len];
        Pair[] pairs = new Pair[len];
        res[ar.length - 1] = len;

        for (int i = 0; i < len; i++)
            pairs[i] = new Pair(i, ar[i]);

        Arrays.sort(pairs);

        for (int i = len - 2; i >= 0; i--) {
            if (pairs[i].value == pairs[i + 1].value)
                res[pairs[i].index] = pairs[i + 1].index;
            else
                res[pairs[i].index] = len;
        }

        return res;
    }

    private static int[] nextUnequal(int[] ar) {
        int len = ar.length;
        int[] res = new int[len];
        res[len - 1] = len;
        for (int i = len - 2; i >= 0; i--) {
            res[i] = ar[i] != ar[i + 1] ? i + 1 : res[i + 1];
        }

        return res;
    }

    final static class Pair implements Comparable<Pair> {
        int index, value;

        Pair(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Pair pair) {
            return value == pair.value ? index - pair.index : value - pair.value;
        }
    }

    private interface QueryProcessor {
        int query(int from, int to);
    }

    final static class AllSameElementsQuery implements QueryProcessor {
        public int query(int from, int to) {
            return -1;
        }
    }

    final static class UniqueElementQuery implements QueryProcessor {
        public int query(int from, int to) {
            return from == to ? -1 : 0;
        }
    }

    final static class RangeQuery implements QueryProcessor {
        private int[] ar, uniqueElements, nextEqual, nextUnequal;
        private int[][] elementIndexMap;
        private final List<Integer> buffer = new LinkedList<>(), counts = new LinkedList<>();
        private boolean[] map, countMap;

        private RangeQuery(int[] ar) {
            this.ar = ar;
            uniqueElements = getUniqueElements(ar);
            nextEqual = nextEqual(ar);
            nextUnequal = nextUnequal(ar);
            populate();
        }

        private void populate() {
            int uniqueCount = uniqueElements.length;
            elementIndexMap = new int[uniqueCount][];
            List<Integer>[] indexMap = new LinkedList[uniqueCount];
            for (int i = 0; i < uniqueCount; i++)
                indexMap[i] = new LinkedList();

            int index = 0;
            for (int e : ar) {
                int i = Arrays.binarySearch(uniqueElements, e);
                indexMap[i].add(index++);
            }

            for (int i = 0; i < uniqueCount; i++)
                elementIndexMap[i] = toArray(indexMap[i]);

            map = new boolean[uniqueCount];
            countMap = new boolean[ar.length];
        }

        public int query(int from, int to) {
            if (nextUnequal[from] > to)
                return -1;

            int index = from;
            int res;
            try {
                while (index <= to) {
                    int val = ar[index];
                    int uniqueIndex = Arrays.binarySearch(uniqueElements, val);
                    if (map[uniqueIndex])
                        continue;

                    int count = getElementCount(uniqueIndex, from, to);
                    if (countMap[count])
                        return 0;

                    countMap[count] = true;
                    counts.add(count);
                    map[uniqueIndex] = true;
                    buffer.add(uniqueIndex);
                    index = nextUnequal[index];
                }

                res = calculateMinDiff(counts);
                return res;
            } finally {
                clearBuffer();
            }
        }

        private int getElementCount(int index, int from, int to) {
            int[] indexArray = elementIndexMap[index];
            int first = Arrays.binarySearch(indexArray, from), last = Arrays.binarySearch(indexArray, to);

            if (first < 0) {
                first = -first - 1;
            }

            if (last < 0) {
                last = -last - 2;
            }

            return last + 1 - first;
        }

        private void clearBuffer() {
            for (int e : buffer)
                map[e] = false;

            for (int e : counts)
                countMap[e] = false;

            buffer.clear();
            counts.clear();
        }
    }

    final static class BruteForceQuery implements QueryProcessor {
        private final int[] ar;

        BruteForceQuery(int[] ar) {
            this.ar = ar;
        }

        @Override
        public int query(int from, int to) {
            if (from == to)
                return -1;

            int len = to + 1 - from, index = 0;
            int[] uar = new int[len];

            for (int i = from; i <= to; i++)
                uar[index++] = ar[i];

            Arrays.sort(uar);
            int count = 1;
            for (int i = 1; i < len; i++)
                if (uar[i] != uar[i - 1])
                    count++;

            if (count == 1)
                return -1;

            int[] uniqar = new int[count];
            index = 0;
            int c = 1;
            for (int i = 1; i < len; i++) {
                if (uar[i] != uar[i - 1]) {
                    uniqar[index++] = c;
                    c = 1;
                } else {
                    c++;
                }
            }

            uniqar[index] = c;
            Arrays.sort(uniqar);
            int minDiff = uniqar[1] - uniqar[0];
            for (int i = 2; i < count; i++)
                minDiff = Math.min(minDiff, uniqar[i] - uniqar[i - 1]);

            return minDiff;
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