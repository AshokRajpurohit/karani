/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.harsh;

import com.ashok.lang.inputs.InputReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Soroco Hiring Challenge
 * Link: https://www.hackerearth.com/challenge/hiring/soroco-hiring-challenge/
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Soroco {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        // choose one of the following method.

        OptimizationGame.solve();
        SumOfSumOfDigits.solve();
        CrossTheStreet.solve();
    }

    private static int getMin(int[][] ar) {
        int min = ar[0][0];

        for (int[] er : ar)
            for (int e : er)
                min = Math.min(min, e);

        return min;
    }

    private static int getMax(int[][] ar) {
        int max = ar[0][0];

        for (int[] er : ar)
            for (int e : er)
                max = Math.max(max, e);

        return max;
    }

    final static class CrossTheStreet {
        public static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt();
            int[][] ar = in.readIntTable(n, m);

            City baseCity = new City(ar);
            int min = getMin(ar), max = getMax(ar);

            int minCost = 10000000;
            for (int height = min; height <= max && minCost > 0; height++) {
                City costCity = baseCity.getCostCity(height);
                minCost = Math.min(minCost, costCity.getMinimumPathCost());
            }

            out.println(minCost);
        }
    }

    final static class City {
        int n, m;
        int[][] grid, costMap;
        boolean[][] visiterMap;
        Point[][] points;
        final static Point INVALID = new Point(-1, -1);

        City(int[][] ar) {
            n = ar.length;
            m = ar[0].length;
            grid = new int[n][m];

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    grid[i][j] = ar[i][j];
        }

        private City getCostCity(int height) {
            City city = new City(grid);
            city.setCost(height);

            return city;
        }

        private void setCost(int height) {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    grid[i][j] = Math.abs(grid[i][j] - height);
        }

        private int getMinimumPathCost() {
            costMap = new int[n][m];
            visiterMap = new boolean[n][m];
            points = new Point[n][m];

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    points[i][j] = new Point(i, j);

            for (int[] ar : costMap)
                Arrays.fill(ar, 10000000);

            costMap[0][0] = grid[0][0];
            LinkedList<Point> queue = new LinkedList<>();
            queue.add(points[0][0]);

            while (queue.size() > 0) {
                int count = queue.size();

                while (count > 0) {
                    count--;
                    Point point = queue.removeFirst();

                    int row = point.row, col = point.col;
                    for (Point next : nextPoints(point)) {
                        if (next != INVALID && costMap[next.row][next.col] > grid[next.row][next.col] + costMap[row][col]) {
                            costMap[next.row][next.col] = Math.min(costMap[next.row][next.col], grid[next.row][next.col] + costMap[row][col]);

                            if (!visiterMap[next.row][next.col])
                                queue.add(next);

                            visiterMap[next.row][next.col] = true; // present in queue for sure.
                        }
                    }
                }

                for (Point point : queue)
                    visiterMap[point.row][point.col] = false; // processing is done.
            }

            return costMap[n - 1][m - 1];
        }

        private Point[] nextPoints(Point point) {
            Point[] points = new Point[4];

            int row = point.row, col = point.col;
            points[0] = getPoint(row + 1, col);
            points[1] = getPoint(row - 1, col);
            points[2] = getPoint(row, col - 1);
            points[3] = getPoint(row, col + 1);

            return points;
        }

        private Point getPoint(int row, int col) {
            if (valid(row, col))
                return points[row][col];

            return INVALID;
        }

        private int getMinimumCost(int row, int col) {
            if (!valid(row, col)) // if the cell is invalid.
                return 10000000;

            if (visiterMap[row][col])
                return costMap[row][col];

            visiterMap[row][col] = true; // mark the cell visited.
            int cost = grid[row][col] + Math.min(getMinimumCost(row + 1, col),
                    Math.min(getMinimumCost(row - 1, col),
                            Math.min(getMinimumCost(row, col - 1), getMinimumCost(row, col + 1))));

            costMap[row][col] = cost;
            return cost;
        }

        private boolean valid(int row, int col) {
            return row >= 0 && row < n && col >= 0 && col < m;
        }
    }

    final static class Point {
        final int row, col;

        Point(int r, int c) {
            row = r;
            col = c;
        }

        public String toString() {
            return row + ", " + col;
        }
    }

    final static class SumOfSumOfDigits {
        private static int[] digitSumMap = new int[18 * 9]; // it's obvious

        static {
            for (int i = 1; i < 10; i++)
                digitSumMap[i] = i;

            for (int i = 10; i < digitSumMap.length; i++)
                digitSumMap[i] = digitSumMap[getDigitSum(i)];
        }

        public static void solve() throws IOException {
            int n = in.readInt(), q = in.readInt();
            long[] ar = in.readLongArray(n);
            int[] digitSums = getDigitSumsArray(ar);
            StringBuilder sb = new StringBuilder(q << 3);

            while (q > 0) {
                q--;

                int type = in.readInt(), k = in.readInt();

                if (type == 1) { // maximum query
                    sb.append(digitSums[n - 1] - queryArray(digitSums, n - k - 1)).append('\n');
                } else {
                    sb.append(digitSums[k - 1]).append('\n');
                }
            }

            out.print(sb);
        }

        private static int queryArray(int[] sumArray, int index) {
            if (index < 0)
                return 0;

            return sumArray[index];
        }

        private static int[] getDigitSumsArray(long[] ar) {
            int[] digitSums = new int[ar.length];

            for (int i = 0; i < ar.length; i++)
                digitSums[i] = digitSumMap[getDigitSum(ar[i])];

            Arrays.sort(digitSums);
            for (int i = 1; i < digitSums.length; i++)
                digitSums[i] += digitSums[i - 1];

            return digitSums;
        }

        private static int getDigitSum(long n) {
            int digitSum = 0;

            while (n > 0) {
                digitSum += n % 10;
                n /= 10;
            }

            return digitSum;
        }
    }

    final static class OptimizationGame {
        public static void solve() throws IOException {
            int t = in.readInt();

            while (t > 0) {
                t--;

                int n = in.readInt();
                out.println(process(in.readLongArray(n)));
            }
        }

        private static long process(long[] ar) {
            long[] extendAr = new long[ar.length * 2];
            int index = 0;
            for (long e : ar)
                extendAr[index++] = e;

            for (int i = 0; i < extendAr.length; i++) {
                long n = extendAr[i];
                if (n == 0)
                    continue;

                int offset = 0;
                extendAr[i] = 0;

                while (n > 0) {
                    while (isEven(n)) {
                        offset++;
                        n >>>= 1;
                    }

                    extendAr[i + offset]++;
                    n--;
                }
            }

            long res = 0;

            for (long e : extendAr)
                res += e;

            return res;
        }

        private static boolean isEven(long n) {
            return (n & 1) == 0;
        }
    }
}
