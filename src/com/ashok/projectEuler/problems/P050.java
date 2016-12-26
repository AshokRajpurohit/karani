package com.ashok.projecteuler.problems;

import com.ashok.lang.math.Prime;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P050 {
    private static int[] primes = Prime.gen_prime(1000000);
    private static long[] sum = new long[primes.length];
    private static boolean[] checkPrime = new boolean[1000000];

    static {
        sum[0] = primes[0];

        for (int i = 1; i < primes.length; i++)
            sum[i] = sum[i - 1] + primes[i];

        for (int e : primes)
            checkPrime[e] = true;
    }

    public static void solve() {
        int len = 21;
        long prime = 953;
        for (int i = 0; i < primes.length - len; i++) {
            int j = i + len - 1;

            while (j < primes.length && sum(i, j) < 1000000) {
                if (checkPrime[(int) sum(i, j)] && len < j + 1 - i) {
                    len = j + 1 - i;
                    prime = sum(i, j);
                }

                j++;
            }
        }

        System.out.println(len + "\t" + prime);
    }

    private static long sum(int start, int end) {
        if (start == 0)
            return sum[end];

        return sum[end] - sum[start - 1];
    }
}
