package com.ashok.projecteuler.problems;


public class P034 {
    private static int[] digitFactorial = new int[10];

    static {
        digitFactorial[0] = 1;
        for (int i = 1; i < 10; i++)
            digitFactorial[i] = i * digitFactorial[i - 1];
    }

    private P034() {
        super();
    }

    public static long solve() {
        long res = 0;
        for (int i = 3; i < 10000000; i++)
            if (isFactSum(i))
                res += i;

        return res;
    }

    private static boolean isFactSum(int n) {
        int value = n, res = 0;

        while (n > 0) {
            res += digitFactorial[n % 10];
            n /= 10;
        }

        return res == value;
    }
}
