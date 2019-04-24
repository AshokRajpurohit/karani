/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.aqr;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AQR {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        firstProblem();
        secondProblem();
    }

    private static void firstProblem() throws IOException {
        int n = in.readInt();
        String[] ar = in.readStringArray(n), br = in.readStringArray(n); // br should be a different permutation of ar
        List<String> a = Arrays.stream(ar).collect(Collectors.toList());
        List<String> b = Arrays.stream(br).collect(Collectors.toList());
        out.println(totalCandies(a, b));
        out.flush();
    }

    private static void secondProblem() throws IOException {
        int n = in.readInt(), b = in.readInt();
        int[] xar = in.readIntArray(n);
        int[][] yars = in.readIntTable(n, b);
        Level[] levels = IntStream.range(0, n)
                .mapToObj(i -> new Level(xar[i], yars[i]))
                .toArray(t -> new Level[t]);

        out.println(calculateMinTime(levels));
        out.flush();
    }

    static void minTimeToExitMaze(List<Integer> levels, List<List<Integer>> openings) {
        int[] levelAr = levels.stream().mapToInt(i -> i).toArray();
        int[][] levelValues = openings.stream()
                .map(list -> list.stream().mapToInt(i -> i).toArray())
                .toArray(t -> new int[t][]);

        Level[] lar = IntStream.range(0, levelAr.length)
                .mapToObj(i -> new Level(levelAr[i], levelValues[i]))
                .toArray(t -> new Level[t]);

        System.out.println(calculateMinTime(lar));
    }

    private static double calculateMinTime(Level[] levels) {
        Level base = levels[0];
        int size = base.values.length;
        double[] baseAr = new double[size];
        for (int i = 1; i < levels.length; i++) {
            Level top = levels[i];
            double[] temp = new double[size];
            long diffX = 1L * (top.x - base.x) * (top.x - base.x);
            Arrays.fill(temp, Long.MAX_VALUE);
            int index = 0;
            for (int value : top.values) {
                int bi = 0;
                for (int v : base.values) {
                    double distance = diffX + 1L * (value - v) * (value - v);
                    temp[index] = Math.min(temp[index], baseAr[bi] + Math.sqrt(distance));
                    bi++;
                }

                index++;
            }
            base = top;
            baseAr = temp;
        }

        return Arrays.stream(baseAr).min().orElse(0);
    }

    final static class Level {
        final int x;
        final int[] values;

        Level(int x, int[] values) {
            this.x = x;
            this.values = values;
        }
    }

    static long totalCandies(List<String> initial, List<String> reorder) {
        String[] original = initial.stream().toArray(t -> new String[t]);
        Map<String, Integer> nameIdMapping = IntStream.range(0, original.length)
                .mapToObj(i -> new Integer(i))
                .collect(Collectors.toMap(t -> original[t], t -> t));

        int[] finalOrdering = reorder.stream().mapToInt(s -> nameIdMapping.get(s)).toArray();
        return calculateSwaps(finalOrdering);
    }

    private static long calculateSwaps(int[] values) {
        long value = 0;
        BSTbyArray tree = new BSTbyArray(values.length);
        for (int v : values) {
            value += tree.countLargerElements(v);
            tree.add(v);
        }

        return value;
    }

    private static class BSTbyArray {
        private int[] node, left, right, count, parent;
        private int size = 0;

        public BSTbyArray() {
            this(16);
        }

        public BSTbyArray(int size) {
            node = new int[size];
            left = new int[size];
            right = new int[size];
            count = new int[size];
            parent = new int[size];
            Arrays.fill(parent, -1);
            Arrays.fill(left, -1);
            Arrays.fill(right, -1);
        }

        public BSTbyArray(int[] ar) {
            this(ar.length);
            add(ar);
        }

        public void add(int[] ar) {
            ensureCapacity(size + ar.length);
            for (int e : ar)
                add(e);
        }

        public void add(int n) {
            ensureCapacity(size + 1);
            node[size] = n;
            count[size] = 1;
            if (size == 0) {
                size++;
                return;
            }

            int temp = 0;
            while (true) {
                if (n > node[temp]) {
                    if (right[temp] == -1) {
                        right[temp] = size;
                        parent[size] = temp;
                        size++;
                        break;
                    }
                    temp = right[temp];
                } else {
                    if (left[temp] == -1) {
                        left[temp] = size;
                        parent[size] = temp;
                        size++;
                        break;
                    }
                    temp = left[temp];
                }
            }

            updateCount(size - 1);
        }

        private void updateCount(int index) {
            count[index] = 1;
            int p = parent[index];
            while (p != -1) {
                count[p]++;
                p = parent[p];
            }
        }

        int countLargerElements(int value) {
            return size - countSmallerElements(value);
        }

        int countSmallerElements(int value) {
            if (size == 0) return 0;
            int counts = 0;
            int index = 0;
            while (index != -1) {
                if (value > node[index]) {
                    if (left[index] != -1) {
                        counts += count[left[index]];
                    }

                    counts++;
                    index = right[index];
                } else
                    index = left[index];
            }

            return counts;
        }

        public boolean find(int n) {
            int temp = 0;
            while (true) {
                if (n == node[temp])
                    return true;
                if (n > node[temp]) {
                    if (right[temp] == -1)
                        return false;
                    temp = right[temp];
                } else {
                    if (left[temp] == -1)
                        return false;
                    temp = left[temp];
                }
            }
        }

        public int[] sort() {
            int[] ar = new int[size];
            sort(ar);
            return ar;
        }

        private void sort(int[] ar) {
            int index = 0;
            if (left[0] != 0)
                index = sort(ar, left[0], 0);
            ar[index] = node[0];
            if (right[0] != 0)
                sort(ar, right[0], index + 1);
        }

        private int sort(int[] ar, int node_index, int index) {
            if (left[node_index] != 0)
                index = sort(ar, left[node_index], index);
            ar[index] = node[node_index];
            index++;
            if (right[node_index] != 0)
                return sort(ar, right[node_index], index);

            return index;
        }

        private void ensureCapacity(int n) {
            if (node.length >= n)
                return;
            int[] temp = new int[node.length << 1];
            for (int i = 0; i < size; i++)
                temp[i] = node[i];

            node = temp;

            temp = new int[node.length];
            for (int i = 0; i < size; i++)
                temp[i] = left[i];

            left = temp;
            temp = new int[node.length];
            for (int i = 0; i < size; i++)
                temp[i] = right[i];

            right = temp;
        }
    }

}
