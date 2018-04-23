package com.ashok.lang.math;

/**
 * @author Ashok Rajpurohit
 *         Binary GCD Implementation.
 *         Euclid's method is faster than Binary GCD when one of the parameter is
 *         in Integer range. If both the parameters are in Long range, in that case
 *         Binary GCD is faster.
 */

public class BinaryGCD {
    private BinaryGCD() {
        super();
    }

    public static int gcd(int a, int b) {
        return euclid(a, b);
    }

    public static int gcd(int a, long b) {
        return euclid(a, (int) (b % a));
    }

    public static int gcd(long a, int b) {
        return euclid((int) (a % b), b);
    }

    public static long gcd(long a, long b) {
        if (a < Integer.MAX_VALUE || b < Integer.MAX_VALUE)
            return euclid((int) a, (int) b);
        return bgcd(a, b);
    }

    public static int[] extendedEuclid(int a, int b) {
        int[] result = new int[3];
        xEuclid(a, b, result);
        return result;
    }

    public static long[] extendedEuclid(long a, long b) {
        long[] result = new long[3];
        xEuclid(a, b, result);
        return result;
    }

    public static long inverseModulo(long a, long mod) {
        if (a % mod == mod - 1)
            return a % mod;

        if (euclid(a, mod) > 1)
            return 0;

        return (mod + extendedEuclid(a, mod)[1]) % mod;
    }

    /**
     * Extended Euclid's algorithm. This method is exact implementation of
     * the algorithm explained in CLRS.
     * For further read refer Alan Baker's A Comprehensive Course in Number
     * Theory, Chapter 1
     *
     * @param a
     * @param b
     * @param res
     */
    private static void xEuclid(long a, long b, long[] res) {
        if (b == 0) {
            res[0] = a;
            res[1] = 1;
            res[2] = 0;
            return;
        }

        xEuclid(b, a % b, res);
        long x = res[1], y = res[2];
        res[1] = y;
        res[2] = x - y * (a / b);
    }

    private static void xEuclid(int a, int b, int[] res) {
        if (b == 0) {
            res[0] = a;
            res[1] = 1;
            res[2] = 0;
            return;
        }

        xEuclid(b, a % b, res);
        int x = res[1], y = res[2];
        res[1] = y;
        res[2] = x - y * (a / b);
    }

    private static long bgcd(long a, long b) {
        if (a == 0)
            return b;

        if (b == 0)
            return a;

        if (a == 1 || b == 1)
            return 1;

        int res = 0;
        int x = Long.numberOfTrailingZeros(a);
        int y = Long.numberOfTrailingZeros(b);
        res = x > y ? y : x;
        a = a >>> x;
        b = b >>> y;

        while (a != b && a != 1) {
            if (a > b) {
                a -= b;
                a = a >>> Long.numberOfTrailingZeros(a);
            } else {
                b -= a;
                b = b >>> Long.numberOfTrailingZeros(b);
            }
        }
        return a << res;
    }

    /**
     * Euclid's Greatest Common Divisor algorithm implementation.
     * For more information refer Wikipedia and Alan Baker's Number Theory.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static int euclid(int a, int b) {
        if (a == 0)
            return b;
        return euclid(b % a, a);
    }

    /**
     * This method is now used only for inverse modulo calculation.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static long euclid(long a, long b) {
        if (a == 0)
            return b;
        return euclid(b % a, a);
    }
}
