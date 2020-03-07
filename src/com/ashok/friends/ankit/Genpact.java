/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankit;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.ModularArithmatic;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Genpact {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private int[] credits;
    private int minScore, maxScore;

    public static void main(String[] args) throws IOException {
        Genpact a = new Genpact();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        int n = in.readInt();
        credits = in.readIntArray(n);
        normalize(credits);
        int totalCredits = sum(credits);
        int maxScore = totalCredits * 10, minScore = totalCredits * 5;
        boolean[] map = new boolean[maxScore + 1];
        map[maxScore] = true;
        map[minScore] = true;
        for (int i = maxScore - 1; i > minScore; i++) {
            populate(i, map, credits);
        }
    }

    private static void normalize(int[] ar) {
        int gcd = 0;
        for (int e : ar) {
            gcd = ModularArithmatic.gcd(gcd, e);
            if (gcd == 1)
                return;
        }

        int len = ar.length;
        for (int i = 0; i < len; i++)
            ar[i] /= gcd;

        Arrays.sort(ar);
    }

    private static void populate(int value, boolean[] map, int[] credits) {

    }

    private static int sum(int[] ar) {
        int sum = 0;
        for (int e : ar)
            sum += e;

        return sum;
    }
}
