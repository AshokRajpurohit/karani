/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.mathematics;

import com.ashok.lang.inputs.InputReader;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Problem Name: Special Multiple
 * Link: https://www.hackerrank.com/challenges/special-multiple/problem
 * <p>
 * You are given an integer N. Can you find the least positive integer X
 * made up of only 9's and 0's, such that, X is a multiple of N?
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SpecialMultiple {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            long time = System.currentTimeMillis();
            for (int i = 1; i <= n; i++) {
                out.println(i + ": " + getSpecialMultiple(i));
                out.flush();
            }

            out.println(System.currentTimeMillis() - time);
            out.flush();
        }
    }

    private static long getSpecialMultiple(int n) {
        long res = 9, r = 1;
        while (res % n != 0) {
            res = toSpecialNumber(++r);
        }

        return res;
    }

    private static long toDecimal(long n) {
        long r = 1, num = 0;
        while (n > 0) {
            long t1 = n / 10;
            long digit = n - ((t1 << 3) + (t1 << 1));
            n = t1;
            if (digit == 9) num += r;
            r <<= 1;
        }

        return num;
    }

    private static long toSpecialNumber(long n) {
        long r = 9, num = 0;
        while (n > 0) {
            if ((n & 1) == 1) num += r;
            r = (r << 3) + (r << 1);
            n >>>= 1;
        }

        return num;
    }
}
