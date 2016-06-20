package com.ashok.projectEuler.problems;


public class P046 {
    public static int solve() {
        int n = 1000000;
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }

        int[] primes = ret;
        System.out.println(primes[0]);

        int res = 15;

        while (res < n) {
            res += 2;
            if (!ar[res])
                continue;

            boolean yes = true;

            for (int i = 0; i < primes.length && primes[i] < res && yes; i++)
                yes &= !isSquare((res - primes[i]));

            if (yes)
                return res;
        }

        return -1;
    }

    private static boolean isSquare(int n) {
        if ((n & 1) == 1)
            return false;

        n = n >>> 1;

        int r = (int) Math.sqrt(n);

        return r * r == n;
    }

    private static int[] gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }
        return ret;
    }
}
