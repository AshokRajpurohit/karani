/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.codejam17.qualification;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: Problem B. Tidy Numbers
 * Link: https://code.google.com/codejam/contest/3264486/dashboard#s=p1
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TidyNumbers {
    private static Output out;// = new Output();
    private static InputReader in;// = new InputReader();
    private static final String CASE = "Case #";
    private static final String path
            = "C:\\Projects\\others\\karani\\src\\com\\ashok\\codejam\\codejam17\\qualification\\TidyNumbersLarge";
    private static final int ZERO = '0';

    public static void main(String[] args) throws IOException {
//        in = new InputReader();
//        out = new Output();
        in = new InputReader(path + ".in");
        out = new Output(path + ".out");
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        for (int i = 1; i <= t; i++) {
            sb.append(CASE).append(i).append(": ");
            long n = in.readLong();
            sb.append(process(n)).append('\n');
        }

        out.print(sb);
    }

    private static long process(long n) {
        char[] number = Long.toString(n).toCharArray();
        if (tidy(number))
            return n;

        int min = number[number.length - 1];
        for (int i = number.length - 2; i >= 0; i--) {
            if (min < number[i]) {
                number[i]--;
                Arrays.fill(number, i + 1, number.length, '9');
            }

            min = number[i];
        }

        return Long.valueOf(String.valueOf(number));
    }

    private static boolean tidy(char[] digits) {
        for (int i = 1; i < digits.length; i++)
            if (digits[i] < digits[i - 1])
                return false;

        return true;
    }
}
