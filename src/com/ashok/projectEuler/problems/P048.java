package com.ashok.projectEuler.problems;


import java.math.BigInteger;

public class P048 {
    public static long solve(int n, int d) {
        if (d > 18)
            throw new RuntimeException("Bhad mein ja");

        long mod = 1;
        while (d > 0) {
            d--;
            mod = (mod << 3) + (mod << 1);
        }

        System.out.println(mod);

        long res = 1;
        for (int i = 2; i <= n; i++)
            res += pow(i, i, mod);

        return res % mod;
    }

    public static String process(int n, int d) {
        long mod = 1;
        while (d > 0) {
            d--;
            mod = (mod << 3) + (mod << 1);
        }

        BigInteger bmod = new BigInteger(String.valueOf(mod));
        BigInteger res = new BigInteger("1");

        for (int i = 2; i <= n; i++) {
            BigInteger bi = new BigInteger(String.valueOf(i));
            res = res.add(bi.pow(i).mod(bmod));
        }

        return res.mod(bmod).toString();
    }

    private static long pow(long a, long b, long mod) {
        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return res;
    }
}
