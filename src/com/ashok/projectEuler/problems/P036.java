package com.ashok.projectEuler.problems;


public class P036 {
    public static long solve(int n) {
        long res = 0;

        for (int i = 1; i <= n; i += 2) {
            if (check(i))
                res += i;
        }

        return res;
    }

    private static boolean check(int n) {
        if (decimal(n))
            return binary(n);

        return false;
    }

    private static boolean decimal(int n) {
        int rev = 0, f = n;
        while (n > 0) {
            rev = (rev << 3) + (rev << 1) + n % 10;
            n /= 10;
        }

        return rev == f;
    }

    private static boolean binary(int n) {
        int rev = 0, f = n;
        while (n > 0) {
            rev = (rev << 1) + (n & 1);
            n = n >>> 1;
        }

        return rev == f;
    }

    public static boolean palindromInNumberSystem(int number, int base) {
        int rev = 0, n = number;
        while (n > 0) {
            rev = rev * base + n % base;
            n /= base;
        }

        return rev == number;
    }
}
