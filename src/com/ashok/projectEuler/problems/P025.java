package com.ashok.projectEuler.problems;


import java.math.BigInteger;

public class P025 {
    private P025() {
        super();
    }

    public static int indexFibonacci(int digits) {
        if (digits == 1)
            return 1;

        if (digits < 19) {
            long num = (long) Math.pow(10, digits - 1);
            long a = 1, b = 1, c = 0;
            int i = 2;
            while (a < num) {
                i++;
                b = a + b;
                c = b;
                b = a;
                a = c;
            }
            return i;
        }

        BigInteger a = BigInteger.ONE, b = BigInteger.ONE;
        int index = 2;
        while (a.toString().length() < digits) {
            index++;
            b = a.add(b);
            BigInteger c = b;
            b = a;
            a = c;
        }

        return index;
    }
}
