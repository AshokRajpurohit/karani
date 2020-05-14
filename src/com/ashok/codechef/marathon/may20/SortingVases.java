/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.may20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: Sorting Vases
 * Link: https://www.codechef.com/MAY20A/problems/SORTVS
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class SortingVases {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        MovesCalculator calculator;
        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();
            int[] temp = in.readIntArray(n);
            int[] permutation = new int[n + 1];
            System.arraycopy(temp, 0, permutation, 1, n);
            boolean[][] robot = new boolean[n + 1][n + 1];
            for (int i = 0; i < m; i++) {
                int u = in.readInt(), v = in.readInt();
                robot[u][v] = true;
                robot[v][u] = true;
            }

            calculator = new SplitCycleTimeCalculator(permutation, robot);
            int ans = calculator.calculateTime();
            out.println(ans);
        }
    }

    /**
     * if (i,j) and (j,k) can be swapped by robot than (i,k) also can be swapped by robot.
     * This method marks (i,k) also as valid swap pair for robot.
     *
     * @param robot
     */
    private static void normalize(boolean[][] robot) {
        int n = robot.length;
        for (int i = 1; i < n; i++) {
            robot[i][i] = false;
        }
        for (int i = 1; i < n; i++) {
            normalize(robot, i);
        }
    }

    private static void normalize(boolean[][] robot, int i) {
        if (robot[i][i]) return;
        int n = robot.length;
        boolean[] check = new boolean[n];
        Queue<Integer> nodes = new LinkedList<>();
        nodes.add(i);
        List<Integer> group = new LinkedList<>();
        check[i] = true;
        while (!nodes.isEmpty()) {
            int next = nodes.remove();
            group.add(next);
            for (int j = 1; j < n; j++) {
                if (next == j || check[j]) continue;
                if (!robot[next][j]) continue;
                nodes.add(j);
                check[j] = true;
            }
        }

        for (int node : group) {
            for (int e : group) {
                robot[node][e] = true;
                robot[e][node] = true;
            }
        }
    }

    private static int directSwaps(int[] permutation, boolean[][] robot) {
        int count = 0;
        for (int i = 1; i < permutation.length; i++) {
            if (permutation[i] == i) continue;
            int j = permutation[i];
            if (permutation[j] != i) continue;
            swap(permutation, i, j);
            count += robot[i][j] ? 0 : 1;
        }

        return count;
    }

    private static void swap(int[] ar, int a, int b) {
        int temp = ar[a];
        ar[a] = ar[b];
        ar[b] = temp;
    }

    interface MovesCalculator {
        int calculateTime();
    }

    final static class SplitCycleTimeCalculator implements MovesCalculator {
        private final int[] permutation;
        private final boolean[][] robot;
        private int count = -1;

        SplitCycleTimeCalculator(final int[] permutation, final boolean[][] robot) {
            this.permutation = permutation;
            this.robot = robot;
            normalize(robot);
        }

        @Override
        public int calculateTime() {
            if (count != -1) return count;
            swapFast();
            int time = directSwaps(permutation, robot);
            for (int i = 1; i < permutation.length; i++) {
                if (permutation[i] == i) continue;
                enlarge(i); // let's merge all the smaller cycles using fast swaps and then decide how to break into smaller cycles.
                time += calculate(i);
                format(getCycle(i));
            }

            count = time;
            return count;
        }

        private void enlarge(int node) {
            boolean[] check = new boolean[permutation.length];
            int[] cycle = getCycle(node);
            for (int e : cycle) check[e] = true;
            for (int e : cycle) {
                for (int i = 1; i < permutation.length; i++) {
                    if (check[i] || permutation[i] == i) continue;
                    if (robot[e][i]) {
                        swap(permutation, e, i); // creating bigger cycle by merging two smaller cycles.
                        enlarge(node); // let's merge all the smaller cycles using fast swaps.
                        return;
                    }
                }
            }
        }

        private void format(int[] indices) {
            for (int e : indices) permutation[e] = e;
        }

        private void swapFast() {
            for (int i = 1; i < permutation.length; ) {
                int j = permutation[i];
                if (j == i) {
                    i++;
                    continue;
                }
                if (robot[i][j]) swap(permutation, i, j);
                else i++;
            }
        }

        private int calculate(int start) {
            int[] cycle = getCycle(start);
            if (cycle.length < 4) {
                return cycle.length - 1;
            }

            List<Swap> swaps = getJumpingSwaps(cycle);
            if (swaps.isEmpty()) {
                return cycle.length - 1;
            }

            int min = cycle.length - 1;
            for (Swap swap : swaps) {
                // any swaps which does not include adjacent nodes in cycle, splits the cycle in two smaller
                // cycles. Any cycle of size n requres n-1 swaps and if we split a cycle of m+n size into two smaller
                // m and n sized cycles, the number of swaps are (m-1) + (n-1) + split_swap. If we use a robot swap
                // to split the cycles then the total time is reduced at least by one human swap.
                swap(permutation, swap.i, swap.j); // swap splits the cycle into two smaller cycles.
                int first = calculate(swap.i), second = calculate(swap.j); // calculate for each smaller cycle.
                min = Math.min(min, first + second);
                swap(permutation, swap.i, swap.j); // revert the split.
            }

            return min;
        }

        /**
         * Returns list of fast swaps (robot swaps) which does not include the adjacent elements in the cycle.
         *
         * @param cycle
         * @return
         */
        private List<Swap> getJumpingSwaps(int[] cycle) {
            List<Swap> jumpingSwaps = new ArrayList<>();
            for (int i = 0; i < cycle.length; i++) {
                for (int j = i + 2; j < cycle.length; j++) {
                    int a = cycle[i], b = cycle[j];
                    if (robot[a][b]) jumpingSwaps.add(new Swap(a, b));
                }
            }

            return jumpingSwaps;
        }

        private int[] getCycle(int start) {
            List<Integer> cycle = new ArrayList<>();
            cycle.add(start);
            int next = permutation[start];
            while (next != start) {
                cycle.add(next);
                next = permutation[next];
            }

            return cycle.stream().mapToInt(v -> v).toArray();
        }
    }

    final static class NormalMovesCalculator implements MovesCalculator {
        private final int[] permutation;
        private final boolean[][] robot;

        NormalMovesCalculator(final int[] permutation, final boolean[][] robot) {
            this.permutation = permutation;
            this.robot = robot;
            normalize(robot);
        }

        @Override
        public int calculateTime() {
            int count = directSwaps(permutation, robot);
            count += applyRobotMoves();
            count += applyNormalMoves();
            return count;
        }

        private int applyNormalMoves() {
            int n = permutation.length;
            int count = 0;
            for (int i = 1; i < n; i++) {
                if (permutation[i] == i) continue;
                int j = permutation[i];
                count += applyMoves(i);
                if (robot[i][j]) {
                }
            }

            return count;
        }

        private int applyRobotMoves() {
            int n = permutation.length;
            int count = 0;
            for (int i = 1; i < n; i++) {
                if (permutation[i] == i) continue;
                int j = permutation[i];
                if (robot[i][j]) {
                    count += applyMoves(i);
                }
            }

            return count;
        }

        private int applyMoves(int start) {
            Deque<Integer> path = new LinkedList<>();
            path.push(start);
            int next = permutation[start];
            while (next != start) {
                path.add(next);
                next = permutation[next];
            }

            int prev = path.pop();
            int count = 0;
            while (!path.isEmpty()) {
                next = path.pop();
                swap(permutation, prev, next);
                if (!robot[prev][next]) count++;
            }

            return count;
        }
    }

    private static class Swap extends Pair {
        Swap(final int i, final int j) {
            super(i, j);
        }
    }

    private static class Pair {
        final int i, j;

        private Pair(final int i, final int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "(" + i + "," + j + ")";
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

        public int[] readIntArray(int n, int d) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt() + d;

            return ar;
        }
    }
}