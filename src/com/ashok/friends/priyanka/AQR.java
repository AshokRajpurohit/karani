/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.priyanka;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringJoiner;
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
        while (true) {
            out.println(in.readLine());
            out.flush();
        }
    }

    final static class MatrixSummation {
        int[][] matrixSummation(int[][] after) {
            int rows = after.length, cols = after[0].length;
            if (rows == 1 || cols == 1) return after;

            int[][] before = new int[rows][cols];
            before[0][0] = after[0][0];
            for (int i = 1; i < rows; i++) {
                before[i][0] = after[i][0] - after[i - 1][0]; // first column
            }
            for (int j = 1; j < cols; j++) {
                before[0][j] = after[0][j] - after[0][j - 1]; // first row
            }

            IntStream.range(1, rows).forEach(row -> {
                IntStream.range(1, cols).forEach(col -> {
                    before[row][col] = after[row][col] + after[row - 1][col - 1] - after[row - 1][col] - after[row][col - 1];
                });
            });

            return before;
        }
    }

    final static class ZombieClusters {
        int zombieClusters(int[][] zombies) {
            int n = zombies.length;
            if (n == 1) return 1;
            int[] clusters = new int[n];
            IntStream.range(0, n).forEach(i -> clusters[i] = i);
            IntStream.range(0, n).forEach(r -> {
                IntStream.range(0, n).forEach(c -> {
                    if (r != c && zombies[r][c] == 1) {
                        replace(clusters, clusters[r], clusters[c]);
                    }
                });
            });

            return (int) Arrays.stream(clusters).distinct().count();
        }

        private void replace(int[] ar, int oldValue, int newValue) {
            for (int i = 0; i < ar.length; i++) {
                if (ar[i] == oldValue) ar[i] = newValue;
            }
        }
    }

    final static class RearrangeSentences {
        String rearrangeTheSentence(String sentence) {
            String[] words = sentence.toLowerCase().split("[ .]");
            Arrays.sort(words, Comparator.comparing(String::length));
            char[] chars = words[0].toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            words[0] = String.valueOf(chars);
            StringJoiner stringJoiner = new StringJoiner(" ");
            Arrays.stream(words).forEach(word -> stringJoiner.add(word));
            String value = stringJoiner.toString();
            return value + ".";
        }
    }
}
