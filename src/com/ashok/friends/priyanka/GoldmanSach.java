/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.priyanka;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.List;

/**
 * Problem Name: Goldman Sach for Priyanka
 * Link: Email
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class GoldmanSach {
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
            out.println(in.read());
            out.flush();
        }
    }

    public static String collapseString(String inputString) {
        inputString = inputString.trim();
        inputString.replaceAll("[^a-zA-Z0-9]", "");
        if (inputString.isEmpty()) return inputString;

        int[] charCounts = new int[256];
        for (char ch : inputString.toCharArray()) {
            charCounts[ch]++;
        }

        StringBuilder sb = new StringBuilder();
        for (char ch : inputString.toCharArray()) {
            if (charCounts[ch] == 0) continue;
            sb.append(charCounts[ch]).append(ch);
            charCounts[ch] = 0;
        }

        return sb.toString();
    }

    static int countConnections(List<List<Integer>> matrix) {
        int m = matrix.size(), n = matrix.get(0).size();
        Boolean[][] grid = matrix.stream()
                .map(row -> {
                    return row.stream()
                            .map(v -> v == 1)
                            .toArray(size -> new Boolean[size]);
                }).toArray(size -> new Boolean[size][]);

        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!grid[i][j]) continue;
                if (i > 0 && j < n - 1 && grid[i - 1][j + 1]) count++; // right-up
                if (j < n - 1 && grid[i][j + 1]) count++; // right
                if (i < m - 1 && j < n - 1 && grid[i + 1][j + 1]) count++; // right-down
                if (i < m - 1 && grid[i + 1][j]) count++; // down
            }
        }

        return count;
    }
}
