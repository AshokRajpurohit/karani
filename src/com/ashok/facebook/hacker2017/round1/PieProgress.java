/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.facebook.hacker2017.round1;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Problem Name: Pie Progress
 * Link: https://www.facebook.com/hackercup/problem/1800890323482794/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class PieProgress {
    private static Output out;
    private static InputReader in;
    private static final String CASE = "Case #";
    private static final int MIN_WEIGHT = 50;

    public static void main(String[] args) throws IOException {
        String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\facebook\\hacker2017\\round1\\";
        in = new InputReader(path + "PieProgress.in");
        out = new Output(path + "PieProgress.out");
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            int n = in.readInt(), m = in.readInt();
            int[][] pies = in.readIntTable(n, m);

            append(sb, i, process(pies));
        }

        out.print(sb);
    }

    private static int process(int[][] pies) {
        normalizePrices(pies);
        int n = pies.length;
        PriorityQueue<Integer> queue = new PriorityQueue<>(n);
        int totalCost = 0;

        for (int[] piePrices : pies) {
            for (int price : piePrices)
                queue.add(price);

            totalCost += queue.remove();
        }

        return totalCost;
    }

    private static void normalizePrices(int[][] ar) {
        for (int[] e : ar)
            normalizePrices(e);
    }

    /**
     * Actual price for an element at index (1 based) is price + index^2 - (index - 1)^2.
     * Simplifying it for zero based index, actual prices is : prices + 2 * index + 1
     *
     * @param ar
     */
    private static void normalizePrices(int[] ar) {
        Arrays.sort(ar);
        for (int i = 0, j = 1; i < ar.length; i++, j += 2)
            ar[i] += j;
    }

    private static void append(StringBuilder sb, int test, int trips) {
        sb.append(CASE).append(test).append(": ").append(trips).append('\n');
    }
}
