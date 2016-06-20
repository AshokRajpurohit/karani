package com.ashok.projectEuler.problems;


/**
 * Incomplete
 */
public class P063 {
    public static int solve(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++)
            res += count(i);

        return res;
    }

    private static int count(int n) {
        if (n == 1)
            return 9;

        if (n == 2)
            return 6;

        int count = 0;
        for (int i = 5; i < 10; i++)
            if (digits(power(i, n)) == n)
                count++;

        return count;
    }

    private static long power(int a, int b) {
        long res = a;

        for (int i = 2; i <= b; i++)
            res *= a;

        return res;
    }

    private static int digits(long n) {
        int count = 0;

        while (n > 0) {
            n /= 10;
            count++;
        }

        return count;
    }
}
