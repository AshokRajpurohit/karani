package com.ashok.projecteuler.problems;

import com.ashok.lang.math.Numbers;

import java.math.BigInteger;

/**
 * Problem: Powerful digit sum
 * Link: https://projecteuler.net/problem=56
 *
 * Description:
 * A googol (10^100) is a massive number: one followed by one-hundred zeros; 100^100 is almost unimaginably large:
 * one followed by two-hundred zeros. Despite their size, the sum of the digits in each number is only 1.
 *
 * Considering natural numbers of the form, a^b, where a, b < 100, what is the maximum digital sum?
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P056 {
    private static BigInteger[] bigIntegers = new BigInteger[100];
    public static int solve() {
        int max = 99;

        for (int a = 2; a < 100; a++)
            for (int b = 2; b < 100; b++)
                max = Math.max(max, getDigitSums(a, b));

        return max;
    }

    private static int getDigitSums(int a, int b) {
        BigInteger bigA = getBigInteger(a);
        return digitCounts(bigA.pow(b));
    }

    private static int digitCounts(BigInteger bi) {
        return Numbers.digitSum(bi.toString());
    }

    private static BigInteger getBigInteger(int n) {
        if (bigIntegers[n] == null)
            bigIntegers[n] = new BigInteger(String.valueOf(n));

        return bigIntegers[n];
    }
}
