/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.sorocco;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Problem Name:
 * Link:
 * <p>
 * a to z as 1 to 26
 * aaa is now 111
 * ak ka aaa
 * 501-Error
 * <p>
 * validation: there should not be any zero. should be a number string
 * ith char: 1. use ith char and take all the possible ways for remaining string
 * 2. combine ith and (i + 1)st char and take all the possible ways for remaining
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SoroccoF2F {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static boolean[] validCharIndices = new boolean[100];
    private static final char ZERO = '0';

    static {
        for (int i = 'a', j = 1; i <= 'z'; i++, j++)
            validCharIndices[j] = true;
    }

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
            out.println(calculateMappingWays(in.read()));
            out.flush();
        }
    }

    private static BigInteger calculateMappingWays(String numString) {
        if (numString.charAt(0) == ZERO)
            return BigInteger.ZERO;

        if (numString.length() == 1) return BigInteger.ONE;
        char[] ar = numString.toCharArray();
        BigInteger sum = BigInteger.ONE, previousSum = BigInteger.ONE;
        for (int i = 1; i < ar.length; i++) {
            BigInteger result;
            if (ar[i] == ZERO) {
                if (!validate(ar[i - 1], ar[i]))
                    return BigInteger.ZERO;

                result = previousSum;
            } else {
                if (validate(ar[i - 1], ar[i])) {
                    result = sum.add(previousSum);
                } else {
                    result = sum;
                }
            }

            previousSum = sum;
            sum = result;
        }

        return sum;
    }

    private static boolean validate(char digit1, char digit2) {
        int num = (digit1 - '0') * 10 + (digit1 - ZERO);
        return validCharIndices[num];
    }

    private static boolean validate(String numString) {
        for (char ch : numString.toCharArray()) {
            if (ch == '0')
                return false;
        }

        return true;
    }
}
