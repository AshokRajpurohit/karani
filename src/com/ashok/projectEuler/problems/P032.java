package com.ashok.projectEuler.problems;


public class P032 {
    private static boolean[] digits = new boolean[10];

    public static long solve() {
        long res = 0;
        boolean[] check = new boolean[1000000];

        for (int i = 2; i < 1000; i++) {
            if (i % 10 == 0)
                continue;

            for (int j = i; j < 100000; j++) {
                if (j % 10 == 0)
                    continue;

                long v = 1L * i * j;
                if (v > check.length - 1)
                    break;

                if (check(i, j) && !check[i * j]) {
                    check[i * j] = true;
                    res += i * j;
                }
            }
        }

        return res;
    }

    private static boolean check(int a, int b) {
        if (!fill(a)) {
            reset();
            return false;
        }

        if (!fill(b)) {
            reset();
            return false;
        }

        if (!fill(a * b)) {
            reset();
            return false;
        }

        for (int i = 1; i < 10; i++)
            if (!digits[i])
                return false;

        reset();
        return true;
    }

    private static boolean fill(int a) {
        while (a > 0) {
            int v = a % 10;
            if (v == 0 || digits[v])
                return false;

            digits[v] = true;
            a /= 10;
        }

        return true;
    }

    private static void reset() {
        for (int i = 0; i < 10; i++)
            digits[i] = false;
    }
}
