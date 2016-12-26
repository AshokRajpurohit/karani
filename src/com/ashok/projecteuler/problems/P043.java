package com.ashok.projecteuler.problems;

import com.ashok.lang.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Problem: Sub-string divisibility
 * Link: https://projecteuler.net/problem=43
 * <p>
 * Description:
 * The number, 1406357289, is a 0 to 9 pandigital number because it is made up of each of the digits 0 to 9 in
 * some order, but it also has a rather interesting sub-string divisibility property.
 * <p>
 * Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we note the following:
 * <p>
 * d2d3d4=406 is divisible by 2
 * d3d4d5=063 is divisible by 3
 * d4d5d6=635 is divisible by 5
 * d5d6d7=357 is divisible by 7
 * d6d7d8=572 is divisible by 11
 * d7d8d9=728 is divisible by 13
 * d8d9d10=289 is divisible by 17
 * <p>
 * Find the sum of all 0 to 9 pandigital numbers with this property.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P043 {
    LinkedList<Rule> rules = new LinkedList<>();

    private P043() {
        // do nothing.
    }

    private void add(Rule rule) {
        rules.add(rule);
    }

    private void normalizeRules() {
        Collections.sort(rules);
        Rule current = rules.getFirst();

        for (Rule rule : rules) {
            if (current != rule)
                current.nextRule = rule;

            current = rule;
        }
    }

    public static long solve() {
        P043 p = new P043();
        populateRules(p);
        p.normalizeRules();
        boolean[] digitCheckMap = new boolean[10];
        int[] digits = new int[10];

        return solve(p.rules.getFirst(), digitCheckMap, digits);
    }

    private static long solve(Rule rule, boolean[] check, int[] digits) {
        long res = 0;
        int num = 17;
        while (num < 1000) {
            if (!isNumberValid(check, num)) {
                num += rule.denominator;
                continue;
            }

            addDigitsToMap(check, num);
            rule.fillDigitArray(digits, num);
            res += process(rule.nextRule, check, digits, num / 10);
            clearDigitsFromMap(check, num);
            num += rule.denominator;
        }

        return res;
    }

    private static long process(Rule rule, boolean[] check, int[] digits, int reducedNum) {
        if (rule == null)
            return digitsToNumber(digits, check);

        int d = rule.denominator;
        int num = reducedNum;
        long res = 0;

        while (num < 1000) {
            if (num % d != 0 || !isNumberValid(check, num)) {
                num += 100;
                continue;
            }

            addDigitsToMap(check, num);
            rule.fillDigitArray(digits, num);
            res += process(rule.nextRule, check, digits, num / 10);
            clearDigitsFromMap(check, num);
            num += 100;
        }

        return res;
    }

    private static boolean isNumberValid(boolean[] check, final int n) {
        return !check[n / 100];
        /*
        int num = n;
        int count = 3;
        while (count > 0) {
            if (check[num % 10])
                return false;

            num /= 10;
            count--;
        }

        // let's check for duplicate digits in n, like 224, 636 etc.
        num = n;
        boolean res = true;
        count = 3;
        while (count > 0) {
            if (check[num % 10])
                res = false;

            check[num % 10] = true;
            num /= 10;
            count--;
        }

        clearDigitsFromMap(check, n);
        return res;*/
    }

    private static void addDigitsToMap(boolean[] digitMap, int num) {
        int count = 3;
        while (count > 0) {
            count--;
            digitMap[num % 10] = true;
            num /= 10;
        }
    }

    private static void clearDigitsFromMap(boolean[] digitMap, int num) {
        int count = 3;
        while (count > 0) {
            count--;
            digitMap[num % 10] = false;
            num /= 10;
        }
    }

    private static long digitsToNumber(int[] digits, boolean[] check) {
        addMissingDigit(digits, check);
        if (!areDigitsUnique(digits))
            return 0;

        long num = 0;

        for (int e : digits)
            num = (num << 3) + (num << 1) + e;

        check[digits[0]] = false; // clearing the 1st digit.
        return num;
    }

    private static void addMissingDigit(int[] digits, boolean[] check) {
        for (int i = 0; i < 10; i++)
            if (!check[i])
                digits[0] = i;
    }

    private static boolean areDigitsUnique(int[] digits) {
        boolean[] map = new boolean[10];

        for (int e : digits)
            map[e] = true;

        int count = digits.length;
        for (boolean b : map)
            if (b)
                count--;

        return count == 0;
    }

    private static void populateRules(P043 p) {
        p.add(new Rule(new int[]{1, 2, 3}, 2));
        p.add(new Rule(new int[]{2, 3, 4}, 3));
        p.add(new Rule(new int[]{3, 4, 5}, 5));
        p.add(new Rule(new int[]{4, 5, 6}, 7));
        p.add(new Rule(new int[]{5, 6, 7}, 11));
        p.add(new Rule(new int[]{6, 7, 8}, 13));
        p.add(new Rule(new int[]{7, 8, 9}, 17));
    }

    /**
     * This Rule class can be modified to include generic rules.
     */
    final static class Rule implements Comparable<Rule> {
        private final int[] digitIndices;
        private final int[] normalizedIndices; // sorted version of {@code digitIndices}
        private final int denominator;
        private Rule nextRule = null;

        Rule(int[] ruleArray, int d) {
            digitIndices = ruleArray.clone();
            denominator = d;
            normalizedIndices = ruleArray.clone();
            Arrays.sort(normalizedIndices);
            ArrayUtils.reverse(digitIndices);
        }

        public void fillDigitArray(int[] digits, int num) {
            for (int index : digitIndices) {
                digits[index] = num % 10;
                num /= 10;
            }
        }

        @Override
        public int compareTo(Rule rule) {
            return rule.denominator - denominator; // higher the divisor, lessar the numbers to be processed.
        }
    }
}
