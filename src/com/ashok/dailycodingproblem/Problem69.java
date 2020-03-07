/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.dailycodingproblem;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: Largest Multiplication of any three integers from given array.
 * Link:
 * Time: 5 Minutes
 * Level: Easy
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Problem69 {
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
            out.println(largestMultiplication(in.readIntArray(in.readInt())));
            out.flush();
        }
    }

    private static long largestMultiplication(int[] ar) {
        long result = 1;
        if (ar.length == 3) {
            return result * ar[0] * ar[1] * ar[2];
        }

        Arrays.sort(ar);
        int len = ar.length;
        return Math.max(1L * ar[0] * ar[1] * ar[len - 1], 1L * ar[len - 1] * ar[len - 2] * ar[len - 3]);
    }
}
