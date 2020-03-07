/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.dailycodingproblem;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Count attacking bishop pairs
 * Level: Medium
 * Time: 15 minutes
 *
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Problem68 {
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
        while (true) {
            int n = in.readInt();
            List<Integer> a = new LinkedList<>(), b = new LinkedList<>();
            while (n > 0) {
                n--;
                a.add(in.readInt());
                b.add(in.readInt());
            }

            out.println(attackingBishops(a, b));
            out.flush();
        }
    }

    private static long attackingBishops(List<Integer> rowIndices, List<Integer> columnIndices) {
        int maxRow = rowIndices.stream().max(Integer::compareTo).get();
        int maxColumn = columnIndices.stream().max(Integer::compareTo).get();
        int minRow = rowIndices.stream().min(Integer::compareTo).get();
        int minCol = columnIndices.stream().min(Integer::compareTo).get();

        int[] sumMap = new int[maxColumn + maxRow + 1];
        int minDiff = minRow - maxColumn, maxDiff = maxRow - minCol;
        int[] diffMap = new int[maxDiff + 1 - minDiff + 1];

        List<Point> points = new LinkedList<>();
        Iterator<Integer> rowIter = rowIndices.iterator(), colIter = columnIndices.iterator();
        while (rowIter.hasNext()) {
            points.add(new Point(rowIter.next(), colIter.next()));
        }

        Point[] pointsArray = points.stream().toArray(s -> new Point[s]);
        for (Point a: points) {
            int diff = a.row - a.col, sum = a.row + a.col;
            diffMap[diff - minDiff]++;
            sumMap[sum]++;
        }

        long result = 0;
        for (int e : diffMap) {
            result += 1L * e * (e - 1) / 2;
        }

        for (int e : sumMap) {
            result += 1L * e * (e - 1) / 2;
        }

        return result;
    }

    final static class Point {
        final int row, col;

        Point(final int row, final int col) {
            this.row = row;
            this.col = col;
        }
    }
}
