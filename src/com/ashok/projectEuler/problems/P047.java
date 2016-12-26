package com.ashok.projecteuler.problems;

import com.ashok.lang.math.Prime;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P047 {
    private static int[] primes = Prime.gen_prime(1000);
    private static int limit = 1000000;
    private static boolean[] check = new boolean[limit];

    public static void solve() {
        for (int i = 0; i < primes.length; i++) {
            int n = primes[i];
            for (int j = i + 1; j < primes.length && n < limit; j++) {
                n *= primes[j];
                for (int k = j + 1; k < primes.length && n < limit; k++) {
                    n *= primes[k];
                    for (int l = k + 1; l < primes.length && n < limit; l++) {
                        process(n * primes[l], i, j, k, l);
                    }

                    n /= primes[k];
                }

                n /= primes[j];
            }
        }

        int count = 0, num = 0;
        for (int i = 0; i < limit; i++) {
            if (check[i])
                count++;
            else
                count = 0;

            if (count == 4) {
                num = i - 3;
                break;
            }
        }

        System.out.println(num);
    }

    private static void process(int n, int i, int j, int k, int l) {
        if (n >= limit)
            return;

        while (n < limit) {
            check[n] = true;
            process(n, j, k, l);

            n *= primes[i];
        }
    }

    private static void process(int n, int i, int j, int k) {
        if (n >= limit)
            return;

        while (n < limit) {
            check[n] = true;
            process(n, j, k);

            n *= primes[i];
        }
    }

    private static void process(int n, int i, int j) {
        if (n >= limit)
            return;

        while (n < limit) {
            check[n] = true;
            process(n, j);

            n *= primes[i];
        }
    }

    private static void process(int n, int i) {
        if (n >= limit)
            return;

        while (n < limit) {
            check[n] = true;
            n *= primes[i];
        }
    }
}
