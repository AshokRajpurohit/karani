/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.thoughtspot;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ThoughtSpot {
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
        System.out.println(ops("10101", 1));
        System.out.println(ops("101010011", 1));
        System.out.println(ops("1111", 1));
        System.out.println(ops("0000", 1));
        System.out.println(ops("1111100000", 1));
        System.out.println(ops("000001111", 1));
        System.out.println(ops("11110000111", 1));
        System.out.println(ops("1010101010101010101", 1));

        System.out.println("---------");

        System.out.println(cost("10101", 1));
        System.out.println(cost("101010011", 1));
        System.out.println(cost("1111", 1));
        System.out.println(cost("0000", 1));
        System.out.println(cost("1111100000", 1));
        System.out.println(cost("000001111", 1));
        System.out.println(cost("11110000111", 1));
        System.out.println(cost("1010101010101010101", 1));

        System.out.println("---------");

        System.out.println(ops("10101", 2));
        System.out.println(ops("101010011", 2));
        System.out.println(ops("1111", 2));
        System.out.println(ops("0000", 2));
        System.out.println(ops("1111100000", 2));
        System.out.println(ops("11001100110011001100", 2));

        while (true) {
//            out.println(ops(in.read(), in.readInt()));
            out.println(cost(in.read(), in.readInt()));
            out.flush();
        }
    }

    public static int[] countFlipsToConvert(String s, int k) {
        if (s.isEmpty()) return new int[3];

        int n = s.length();
        int[] count0s = new int[n], count1s = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                int v = i + k >= n ? 0 : count0s[i+k];
                count0s[i] = v+1;
                if (i + k < n) count1s[i] = count1s[i+k];
            } else {
                int v = i + k >= n ? 0 : count1s[i+k];
                count1s[i] = v+1;
                if (i+k < n) count0s[i] = count0s[i+k];
            }
        }

        boolean[] flips = new boolean[n];
        int flipsToZero = 0; // count of flips from 1 to 0
        int flipsToOne = 0; // count of flips from 0 to 1

        int ops = 0;
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            boolean flip = flips[i];

            if ((!flip && ch == '0')) continue;
            if (flip && ch == '1') {
                if (i+k < n) flips[i+k] = flip;
                continue;
            }

            ops++; // ops = 1
            if (i+k < n) flips[i+k] = !flip;

            int count0 = count0s[i];
            int count1 = count1s[i];

            if (flip) { // swapping
                int temp = count0;
                count0 = count1;
                count1 = temp;
            }

            flipsToZero += count1;
            flipsToOne += count0;
        }

        return new int[] {flipsToOne, flipsToZero, ops};
    }

    private static int cost(String s, int k) {
        int[] res = countFlipsToConvert(s, k);
        return res[0];
    }

    private static int ops(String s, int k) {
        return countFlipsToConvert(s, k)[2];
    }
}
