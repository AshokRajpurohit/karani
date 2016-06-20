package com.ashok.projecteuler.problems;


public class P038 {
    final static int MAX = 987654321, MIN = 123456789;
    private static boolean[] digits = new boolean[10];

    public static long solve() {
        long res = MIN;

        for (int i = 2; i < 100000; i++) {
            long num = i;

            for (int j = 2; j < 9 && num <= MAX; j++) {
                num = concatenate(num, i * j);
                if (pandigital(num))
                    res = Math.max(res, num);
            }
        }

        return res;
    }

    private static long concatenate(long a, long b) {
        long res = a, rev = 0;
        while (b > 0) {
            rev = rev * 10 + b % 10;
            b /= 10;
        }

        while (rev > 0) {
            res = res * 10 + rev % 10;
            rev /= 10;
        }

        return res;
    }

    private static boolean pandigital(long n) {
        boolean res = true;

        if (n < MIN || n > MAX)
            return false;

        while (n > 0) {
            int v = (int) (n % 10);
            if (v == 0 || digits[v])
                break;

            digits[v] = true;
            n /= 10;
        }

        for (int i = 1; i < 10 && res; i++) {
            if (!digits[i])
                res = false;
        }

        for (int i = 0; i < 10; i++)
            digits[i] = false;

        return res;
    }
}
