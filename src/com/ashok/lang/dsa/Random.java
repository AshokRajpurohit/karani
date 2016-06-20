package com.ashok.lang.dsa;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Random {
    private long x = 100003, y = 1003, m; //0x5DEECE66DL
    long a, b;

    public Random() {
        m = (1L << 32) - 1;
        a = System.nanoTime() & m;
        b = System.nanoTime() & m;
    }

    public Random(int mod) {
        m = mod;
        a = System.nanoTime() & m;
        b = System.nanoTime() & m;
    }

    public int nextInt() {
        while (true) {
            long temp = a;
            a = (a * x + b * y) & m;
            b = temp;
            if (a != b)
                return (int)a;
        }
    }

    public long nextLong() {
        long a = nextInt();
        return (a << 32) | nextInt();
    }

    public char nextChar() {
        return (char)(nextInt() & 255);
    }
}
