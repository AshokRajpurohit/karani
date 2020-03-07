/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.dailycodingproblem;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Nth Perfect Number, Perfect number is the number whose digit sum is 10.
 * Link:
 * Time: 6 Minutes to Code.
 * Level: Easy
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Problem70 {
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
            out.println(getPerfectNumber(in.readInt()));
            out.flush();
        }
    }

    private static int getPerfectNumber(int n) {
        if (n == 1) return 19;
        int num = 28;
        while (n > 2) {
            num++;
            if (checkSum(num, 10)) n--;
        }

        return num;
    }

    private static boolean checkSum(int n, int s) {
        return digitSum(n) == s;
    }

    private static int digitSum(int n) {
        int sum = 0;
        while (n > 9) {
            sum += n % 10;
            n /= 10;
        }

        return sum + n;
    }
}
