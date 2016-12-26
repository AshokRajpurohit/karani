package com.ashok.projecteuler.problems;

import com.ashok.lang.math.Numbers;

import java.util.Arrays;

/**
 * Problem: Permuted multiples
 * Link: https://projecteuler.net/problem=52
 * <p>
 * Description:
 * It can be seen that the number, 125874, and its double, 251748, contain exactly
 * the same digits, but in a different order.
 * <p>
 * Find the smallest positive integer, x, such that 2x, 3x, 4x, 5x, and 6x, contain the same digits.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P052 {
    private static int[] digits = new int[10], auxilliary = new int[10];

    public static int solve() {
        int x = 5000;

        while (true) {
            if (isMagicNumber(x))
                return x;

            x++;
        }
    }

    private static boolean isMagicNumber(int x) {
        if (earlyDiscard(x))
            return false;

        populateDigitMap(x * 2);

        for (int y = 3 * x; y <= 6 * x; y += x)
            if (!validate(y))
                return false;

        return true;
    }

    private static boolean earlyDiscard(int n) {
        return Numbers.digitCounts(n << 1) != Numbers.digitCounts(n * 6);
    }

    private static boolean validate(int n) {
        populateAuxilliaryMap(n);
        return compare();
    }

    private static boolean compare() {
        for (int i = 0; i < 10; i++)
            if (digits[i] != auxilliary[i])
                return false;

        return true;
    }

    private static void populateAuxilliaryMap(int n) {
        populateMap(auxilliary, n);
    }

    private static void populateDigitMap(int n) {
        populateMap(digits, n);
    }

    private static void populateMap(int[] map, int n) {
        Arrays.fill(map, 0);

        while (n > 0) {
            map[n % 10]++;
            n /= 10;
        }
    }
}
