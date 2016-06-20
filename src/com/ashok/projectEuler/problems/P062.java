package com.ashok.projectEuler.problems;


public class P062 {
    private static long[] cubes = new long[100000];
    private static boolean[] mark = new boolean[cubes.length];
    private static int[] map = new int[10], temp = new int[10];

    static {
        for (int i = 1; i < cubes.length; i++)
            cubes[i] = 1L * i * i * i;
    }

    public static long solve(int iter) {
        for (int i = 1; i < cubes.length; i++)
            if (check(i, iter - 1))
                return cubes[i];

        return 0;
    }

    private static boolean check(int n, int iter) {
        long c = cubes[n];
        mark[n] = true;

        populate(c);
        int digits = digits(c);
        boolean res = false;

        for (int j = n + 1; j < cubes.length && digits(cubes[j]) == digits;
             j++) {
            if (!mark[j]) {
                if (match(j)) {
                    mark[j] = true;
                    res |= check(digits, j, iter - 1);
                }
            }
        }

        return res;
    }

    private static boolean check(int digits, int n, int iter) {
        if (iter == 0)
            return true;

        if (n == cubes.length)
            return false;

        /* if (iter == 1) {
            if (digits(n) != digits)
                return false;

            if (match(n)) {
                mark[n] = true;
                return true;
            }

            return false;
        } */

        boolean res = false;

        for (int i = n + 1; i < cubes.length && digits(cubes[i]) == digits;
             i++) {

            if (mark[i])
                continue;

            if (match(i)) {
                mark[i] = true;

                res |= check(digits, i, iter - 1);
            }
        }

        return res;
    }

    private static boolean match(int n) {
        long c = cubes[n];

        while (c > 0) {
            int d = (int) (c % 10);
            c /= 10;

            temp[d]++;
        }

        boolean res = match();
        clean(temp);

        return res;
    }

    private static void clean(int[] ar) {
        for (int i = 0; i < ar.length; i++)
            ar[i] = 0;
    }

    private static boolean match() {
        for (int i = 0; i < 10; i++)
            if (map[i] != temp[i])
                return false;

        return true;
    }

    private static int digits(long n) {
        int count = 0;

        while (n > 0) {
            count++;
            n /= 10;
        }

        return count;
    }

    private static void populate(long cube) {
        clean(map);

        while (cube > 0) {
            int d = (int) (cube % 10);
            cube /= 10;

            map[d]++;
        }
    }
}
