package com.ashok.projecteuler.problems;


public class P001 {
    public static long solve(int n, int a, int b) {
        return get(n, a) + get(n, b) - get(n, lcm(a, b));
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
    }

    private static int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }

    private static long get(int n, int a) {
        if (a > n)
            return 0;

        long num = n / a, d = a;

        return num * ((a << 1) + (num - 1) * d) / 2;
    }
}
