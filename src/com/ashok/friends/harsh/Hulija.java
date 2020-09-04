/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.harsh;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Hulija {
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
            int n = in.readInt(), m = in.readInt();
            int[][] ar = in.readIntTable(n, m);
            out.println(inverseSpiral(ar));
            out.flush();
        }
    }

    private static List<Integer> inverseSpiral(int[][] matrix) {
        return inverse(spiralTraversal(matrix));
    }

    private static List<Integer> spiralTraversal(int[][] matrix) {
        int r1 = 0, c1 = 0, r2 = matrix.length - 1, c2 = matrix[0].length - 1;
        List<Integer> list = new LinkedList<>();
        while (r1 <= r2 && c1 <= c2) {
            list.addAll(forwardRow(r1, c1, c2, matrix));
            list.addAll(forwardColumn(c2, r1 + 1, r2, matrix));
            list.addAll(backwardRow(r2, c2 - 1, c1, matrix));
            list.addAll(backwardColumn(c1, r2 - 1, r1 + 1, matrix));
            r1++;
            c1++;
            r2--;
            c2--;
        }

        return list;
    }

    private static List<Integer> forwardRow(int r, int c1, int c2, int[][] matrix) {
        if (c1 > c2) return new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        for (int c = c1; c <= c2; c++) list.add(matrix[r][c]);
        return list;
    }

    private static List<Integer> forwardColumn(int c, int r1, int r2, int[][] matrix) {
        List<Integer> list = new LinkedList<>();
        if (r1 > r2) return list;
        for (int r = r1; r <= r2; r++) list.add(matrix[r][c]);
        return list;
    }

    private static List<Integer> backwardRow(int r, int c1, int c2, int[][] matrix) {
        List<Integer> list = forwardRow(r, c2, c1, matrix);
        return inverse(list);
    }

    private static List<Integer> backwardColumn(int c, int r1, int r2, int[][] matrix) {
        List<Integer> list = forwardColumn(c, r2, r1, matrix);
        return inverse(list);
    }

    private static List<Integer> inverse(List<Integer> list) {
        if (list.isEmpty()) return list;
        LinkedList<Integer> ll = new LinkedList<>();
        list.forEach(e -> ll.addFirst(e));
        return ll;
    }
}
