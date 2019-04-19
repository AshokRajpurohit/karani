/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.vinash;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Hiring {
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
            out.println(countPalindromes(in.read()));
            out.flush();
        }
    }

    private static int maxDifference(int[] ar) {
        int min = ar[0], diff = 0;
        for (int e : ar) {
            min = Math.min(min, e);
            diff = Math.max(diff, e - min);
        }

        return diff == 0 ? -1 : diff;
    }

    public static int countPalindromes(String s) {
        int count = 0;
        char[] ar = s.toCharArray();
        for (int i = 0; i < s.length(); i++)
            count += countPalindromes(ar, i, i) + countPalindromes(ar, i, i + 1);

        return count;
    }

    private static int countPalindromes(char[] ar, int left, int right) {
        int count = 0;
        while (left >= 0 && right < ar.length && ar[left] == ar[right]) {
            count++;
            left--;
            right++;
        }

        return count;
    }
}
