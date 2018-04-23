/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.tridip.migo;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Grid {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static final char GRASS = 'Y';
    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        Grid a = new Grid();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            out.println(process(in.readStringArray(n)));
            out.flush();
        }
    }

    private static long process(String[] gridRows) {
        Group group = new Group(gridRows);
        group.process();
        int fields = group.getGroupCount();
        return twoPower(fields - 1);
    }

    private static long twoPower(int n) {
        if (n == 0)
            return 1;

        int half = n >>> 1;
        long halfPower = twoPower(half);
        long power = halfPower * halfPower % MOD;
        return isEven(n) ? power : (power << 1) % MOD;
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    private static char[][] toGrid(String[] gridRows) {
        int n = gridRows.length;
        char[][] grid = new char[n][];
        for (int i = 0; i < n; i++)
            grid[i] = gridRows[i].toCharArray();

        return grid;
    }

    final static class Group {
        final char[][] grid;
        final int[][] cellGroups;
        private int groupId = 1;
        final int rows, cols;
        boolean processed = false;

        Group(String[] gridRows) {
            grid = toGrid(gridRows);
            rows = grid.length;
            cols = grid[0].length;
            cellGroups = new int[rows][cols];
        }

        private void process() {
            if (processed)
                return;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (!marked(i, j) && isGrass(i, j)) {
                        markGroup(i, j);
                        groupId++;
                    }
                }
            }
        }

        private int getGroupCount() {
            return groupId - 1;
        }

        private void markGroup(int r, int c) {
            if (!isValid(r, c) || marked(r, c) || !isGrass(r, c))
                return;

            cellGroups[r][c] = groupId;
            markGroup(r + 1, c);
            markGroup(r - 1, c);
            markGroup(r, c + 1);
            markGroup(r, c - 1);
        }

        private boolean isValid(int r, int c) {
            return r >= 0 && r < rows && c >= 0 && c < cols;
        }

        private boolean marked(int r, int c) {
            return cellGroups[r][c] != 0;
        }

        private boolean isGrass(int r, int c) {
            return grid[r][c] == GRASS;
        }
    }
}
