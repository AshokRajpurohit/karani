package com.ashok.topcoder.srm;


public class Fibonacci {
    private Fibonacci() {
        super();
    }

    private static final long[] fib;

    static {
        fib = new long[92];
        fib[0] = 1;
        fib[1] = 1;
        long a = 1, b = 1, temp;
        int count = 2;
        while (count < 92) {
            b = a + b;
            temp = b;
            b = a;
            a = temp;
            fib[count] = a;
            count++;
        }
        System.out.println(fib[91] + "\t le ashok");
    }

    public static long getfib(int n) {
        if (n >= 92)
            return -1;
        return fib[n];
    }

    public static long[] getfibs() {
        return fib.clone();
    }

    public static long getfib(int n, long mod) {
        if (n < 92)
            return fib[n] % mod;
        return -1;
    }
}
