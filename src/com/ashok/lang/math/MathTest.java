/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.math;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        GuassianInteger ca = GuassianInteger.of(8, 38), cb = GuassianInteger.of(9, 59);
        System.out.println(ModularArithmatic.gcd(ca.norm, cb.norm));
        List<GuassianInteger> gcds = Arrays.asList(GuassianInteger.of(1, 1)
                , GuassianInteger.of(2, 3), GuassianInteger.of(1, 5));

        gcds = gcds.stream().flatMap(c -> Stream.of(c, c.conjugate)).collect(Collectors.toList());

//        gcds.forEach(c -> {
//            out.println();
//            out.println(c, c.norm);
//            boolean divisibility = ca.checkDivisability(c) && cb.checkDivisability(c);
//            out.println(divisibility);
//            if (!divisibility) return;
//            out.println(ca.divide(c), cb.divide(c));
//            out.println(Arrays.asList(ca.multiply(c), ca.multiply(c.conjugate)).toString());
//            out.println(Arrays.asList(cb.multiply(c), cb.multiply(c.conjugate)).toString());
//        });
        out.flush();

        while (true) {
            GuassianInteger alpha = GuassianInteger.of(in.readInt(), in.readInt());
            GuassianInteger beta = GuassianInteger.of(in.readInt(), in.readInt());
            out.println(GuassianInteger.gcd(alpha, beta));
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
