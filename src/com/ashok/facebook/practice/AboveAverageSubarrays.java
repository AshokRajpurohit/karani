/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.facebook.practice;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AboveAverageSubarrays {
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
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        out.println(process(ar));
    }

    private static Fraction[] process(int[] ar) {
        List<Fraction> res = new ArrayList();
        int n = ar.length, sum = Arrays.stream(ar).sum();
        int[] sumAr = new int[n];
        sumAr[0] = ar[0];
        for (int i = 1; i < n; i++) sumAr[i] = sumAr[i - 1] + ar[i];

        Fraction total = new Fraction(sum, n);
        for (int i = 0; i < ar.length; i++) {
            int count = 0;
            for (int j = i; j < ar.length; j++) {
                int partialSum = sumAr[j] - (i == 0 ? 0 : sumAr[i - 1]);
                Fraction fraction = new Fraction(partialSum, ++count);
                int diff = fraction.compareTo(total);
                if (diff > 0) res.add(new Fraction(i + 1, j + 1));
                else if (diff == 0 && i == 0 && j == n - 1) res.add(new Fraction(i + 1, j + 1));
            }
        }

        return res.stream().toArray(t -> new Fraction[t]);
    }

    final static class Fraction implements Comparable<Fraction> {
        final int num, den;

        Fraction(final int num, final int den) {
            this.num = num;
            this.den = den;
        }

        @Override
        public int compareTo(Fraction f) {
            long x = 1L * num * f.den, y = 1L * den * f.num;
            return Long.compare(x, y);
        }

        public String toString() {
            return num + ", " + den;
        }
    }
}
