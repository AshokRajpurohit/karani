/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring.uipath;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class KindergartenCandy {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        KindergartenCandy a = new KindergartenCandy();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(distributeCandy(in.readIntArray(in.readInt())));
            out.flush();
        }
    }

    private static int distributeCandy(int[] score) {
        int n = score.length;
        int[] left = new int[n], right = new int[n];
        left[0] = 1;
        right[n - 1] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = score[i] > score[i - 1] ? left[i - 1] + 1 : 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            right[i] = score[i] > score[i + 1] ? right[i + 1] + 1 : 1;
        }

        int result = 0;
        for (int i = 0; i < n; i++)
            result += Math.max(left[i], right[i]);

        return result;
    }
}
