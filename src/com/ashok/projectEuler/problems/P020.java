package com.ashok.projectEuler.problems;


import java.math.BigInteger;

public class P020 {
    private P020() {
        super();
    }

    public static int solve(int n) {
        long res = 1;
        for (int i = 2; i < 21; i++)
            res = res * i;

        BigInteger bi = new BigInteger(String.valueOf(res));
        for (int i = 21; i <= n; i++)
            bi = bi.multiply(new BigInteger(String.valueOf(i)));

        String s = bi.toString();
        int sum = 0;
        for (int i = 0; i < s.length(); i++)
            sum += s.charAt(i) - '0';

        System.out.println(sum);
        return sum;
    }
}
