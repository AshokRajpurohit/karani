package com.ashok.projecteuler.problems;


public class P037 {
    private static boolean[] primes;

    static {
        primes = new boolean[1000000];
        for (int i = 2; i < 1000000; i++)
            primes[i] = true;

        for (int i = 2; i < 1000; i++)
            for (int j = i << 1; j < 1000000; j += i)
                if (primes[j])
                    primes[j] = false;
    }

    public static int solve() {
        int res = 0, count = 0;
        for (int i = 11; i < primes.length; i++) {
            if (!primes[i])
                continue;

            if (truncable(i)) {
                res += i;
                count++;
            }
        }

        return res;
    }

    private static boolean truncable(int n) {
        int high = 10;
        while (high <= n)
            high = (high << 3) + (high << 1);

        int f = n;
        while (f > 0) {
            if (!primes[f])
                return false;

            f /= 10;
        }

        while (n > 0) {
            if (!primes[n])
                return false;

            n %= high;
            high /= 10;
        }

        return true;
    }
}
