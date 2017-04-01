/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.aman;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: School Marking System
 * Link: Check mail for IBM Test from Aman
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class HackerRankIBM {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        HackerRankIBM a = new HackerRankIBM();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int total = in.readInt(), rank = in.readInt();
            out.println(marksForRank(in.readIntArray(total), rank));
            out.flush();
        }
    }

    private static int marksForRank(int[] scores, int rank) {
        if (rank > scores.length || rank == 0)
            return 0;

        int[] copy = scores.clone();
        Arrays.sort(copy);
        reverse(copy);
        int score = copy[0];

        if (rank == 1)
            return score;

        for (int i = 1; i < copy.length && rank > 1; i++) {
            if (copy[i] != score) {
                rank--;
                score = copy[i];
            }
        }

        return rank == 1 ? score : 0;
    }

    private static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int t = ar[i];
            ar[i] = ar[j];
            ar[j] = t;
        }
    }
}
