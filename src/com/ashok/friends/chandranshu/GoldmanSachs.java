/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.chandranshu;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class GoldmanSachs {
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
            int[][] matrix = in.readIntTable(in.readInt(), in.readInt());
            List<List<Integer>> list = Arrays.stream(matrix).map(a -> Arrays.stream(a).mapToObj(b -> new Integer(b)).collect(Collectors.toList())).collect(Collectors.toList());
            out.println(countSpecialElements(list));
            out.flush();
        }
    }

    static int countConnections(List<List<Integer>> matrix) {
        int m = matrix.size(), n = matrix.get(0).size();
        int[][] grid = matrix.stream()
                .map(r -> r.stream().mapToInt(v -> v).toArray())
                .toArray(t -> new int[t][]);

        return IntStream.range(0, m).map(r -> {
            return IntStream.range(0, n).map(c -> {
                if (grid[r][c] == 0) return 0;
                int count = 0;
                int rs = Math.max(0, r - 1), re = Math.min(r + 1, m - 1);
                int cs = Math.max(0, c - 1), ce = Math.min(c + 1, n - 1);
                for (int x = rs; x <= re; x++) for (int y = cs; y <= ce; y++) count += grid[x][y];
                return count - 1;
            }).sum();
        }).sum() >>> 1;
    }

    static int countSpecialElements(List<List<Integer>> matrix) {
        int m = matrix.size(), n = matrix.get(0).size();
        int[][] grid = matrix.stream()
                .map(r -> r.stream().mapToInt(v -> v).toArray())
                .toArray(t -> new int[t][]);

        Point[][] points = IntStream.range(0, m).mapToObj(ri ->
                IntStream.range(0, n).mapToObj(ci -> new Point(ri, ci, grid[ri][ci])).toArray(t -> new Point[t])
        ).toArray(t -> new Point[t][]);

        boolean[][] check = new boolean[m][n];
        BooleanHolder invalidData = new BooleanHolder();
        Comparator<Point> pointComparator = (a, b) -> a.value - b.value;
        // let's check rows first:
        IntStream.range(0, m).forEach(ri -> {
            Point min = Arrays.stream(points[ri]).min(pointComparator).get();
            Point max = Arrays.stream(points[ri]).max(pointComparator).get();
            if (Arrays.stream(points[ri]).filter(a -> a.value == min.value).count() > 1) invalidData.value = true;
            if (Arrays.stream(points[ri]).filter(a -> a.value == max.value).count() > 1) invalidData.value = true;
            check[min.x][min.y] = true;
            check[max.x][max.y] = true;
        });

        // let's check columns now.
        IntStream.range(0, n).forEach(ci -> {
            Point min = IntStream.range(0, m).mapToObj(ri -> points[ri][ci]).min(pointComparator).get();
            Point max = IntStream.range(0, m).mapToObj(ri -> points[ri][ci]).max(pointComparator).get();
            if (IntStream.range(0, m).mapToObj(ri -> points[ri][ci]).filter(a -> a.value == min.value).count() > 1)
                invalidData.value = true;
            if (IntStream.range(0, m).mapToObj(ri -> points[ri][ci]).filter(a -> a.value == max.value).count() > 1)
                invalidData.value = true;

            check[min.x][min.y] = true;
            check[max.x][max.y] = true;
        });

        if (invalidData.value)
            return -1;

        return count(check, true);
    }

    private static int count(boolean[][] ar, boolean value) {
        return (int) Arrays.stream(ar).mapToLong(br -> IntStream.range(0, br.length).filter(i -> br[i]).count()).sum();
    }

    final static class Point {
        final int x, y, value;

        Point(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    final static class BooleanHolder {
        boolean value = false;
    }
}
