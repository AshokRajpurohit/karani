/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.math;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * This class is for the book
 * 'A Friendly Introduction to Number Theory by Joseph H. Silverman'.
 * All the programs are to solve excersise problems.
 * Any generic program will be moved out of this class eventually.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MathTest {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        MathTest a = new MathTest();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            for (int i = 1; i <= n; i++)
                for (int j = i; j <= n; j++) {
                    long v = process(i, j);
                    if (v != -1 && primitive(i, j, v))
                        out.println(i + " : " + j + " => " + v);
                }

            out.flush();
        }
    }

    private static long process(int a, int b) {
        long res = 1L * a * a * a + 1L * b * b * b;

        if (Numbers.isSquare(res))
            return (long) Math.sqrt(res);

        return -1;
    }

    private static boolean primitive(long a, long b, long c) {
        if (a == b && a != 2)
            return false;

        long gcd = ModularArithmatic.gcd(a, b);

        return gcd == 1 || !Numbers.isSquare(gcd);
    }
}
