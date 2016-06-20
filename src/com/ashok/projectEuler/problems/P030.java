package com.ashok.projecteuler.problems;


public class P030 {
    private static int[] powFive = new int[10];

    static {
        for (int i = 1; i < 10; i++)
            powFive[i] = i * i * i * i * i;
    }

    private P030() {
        super();
    }

    public static int sumFivePow() {
        int res = 0;
        for (int i = 2; i < 1000000; i++)
            if (isPowFiveSum(i)) {
                res += i;
                System.out.println("le:\t" + i);
            }

        return res;
    }

    private static boolean isPowFiveSum(int n) {
        int res = 0, value = n;
        while (n > 0) {
            res += powFive[n % 10];
            n /= 10;
        }

        return res == value;
    }
}
