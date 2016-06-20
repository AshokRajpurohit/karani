package com.ashok.projecteuler.problems;


public class P033 {
    public static long solve() {
        int num, den;
        int fnum = 1, fden = 1;

        for (int i = 11; i < 100; i++) {
            if (i % 10 == 0)
                continue;

            for (int j = i + 1; j < 100; j++) {
                if (j % 10 == 0)
                    continue;

                int commonDigit = commonDigit(i, j);

                if (commonDigit != 0) {
                    int g = gcd(i, j);
                    int tn = i / g, td = j / g;

                    int rcdn = removeDigit(i, commonDigit), rcdd =
                            removeDigit(j, commonDigit);
                    g = gcd(rcdn, rcdd);
                    rcdn /= g;
                    rcdd /= g;

                    if (tn == rcdn && td == rcdd) {
                        fnum *= rcdn;
                        fden *= rcdd;
                    }
                }
            }
        }

        return fden / gcd(fnum, fden);
    }

    private static int removeDigit(int a, int d) {
        int p = a / 10, q = a % 10;

        if (p == d)
            return q;

        return p;
    }

    private static int commonDigit(int a, int b) {
        int p = a / 10, q = a % 10;
        int x = b / 10, y = b % 10;

        if (p == x || p == y)
            return p;

        if (q == x || q == y)
            return q;

        return 0;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
    }
}
