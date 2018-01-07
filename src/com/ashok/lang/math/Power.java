package com.ashok.lang.math;

import java.math.BigInteger;

/**
 * This class is to calculate big powers with least number of
 * multiplication.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class Power {
    private static long MOD_LIMIT = (long) Math.sqrt(Long.MAX_VALUE);

    private Power() {
        super();
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @param mod
     * @return
     */
    public static long pow(long a, long b, long mod) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }

        if (res < 0) res += mod;
        return res;
    }

    public static String pow(String a, long b, String mod) {
        return pow(new BigInteger(a), b, new BigInteger(mod)).toString();
    }

    public static BigInteger pow(BigInteger a, long b, BigInteger mod) {
        if (b == 0)
            return BigInteger.ONE;

        a = a.mod(mod);
        if (a.compareTo(BigInteger.ZERO) < 0)
            a = a.add(mod);

        if (a.equals(BigInteger.ONE) || a.equals(BigInteger.ZERO) || b == 1)
            return a;

        long r = Long.highestOneBit(b);
        BigInteger res = a;

        while (r > 1) {
            r = r >> 1;
            res = res.multiply(res).mod(mod);
            if ((b & r) != 0) {
                res = res.multiply(a).mod(mod);
            }
        }

        if (res.compareTo(BigInteger.ZERO) < 0) res = res.add(mod);
        return res;
    }

    /**
     * calculates a^(b^c) modulo mod
     *
     * @param a
     * @param b
     * @param c
     * @param mod
     * @return
     */
    public static long pow(long a, long b, long c, long mod) {
        b = pow(b, c, mod - 1);
        return pow(a, b, mod);
    }

    public static long powfact(long a, long b, long mod) {
        if (b >= mod - 1)
            return 1;

        return 4;
    }

    public static double pow(double a, long b, long mod) {
        if (b == 0)
            return 1;

        if (a == 1 || a == 0 || b == 1)
            return a;

        boolean sign = true;
        if ((b & 1) == 1 && a < 0)
            sign = false;

        if (a < 0)
            a = -a;

        a = a % mod;

        long r = Long.highestOneBit(b);
        double res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return sign ? res : -res;
    }

    public static double pow(double a, long b, double mod) {
        if (b == 0)
            return 1;

        if (a == 1 || a == 0 || b == 1)
            return a;

        boolean sign = true;
        if ((b & 1) == 1 && a < 0)
            sign = false;

        if (a < 0)
            a = -a;

        a = a % mod;

        long r = Long.highestOneBit(b);
        double res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return sign ? res : -res;
    }

    /**
     * Calculates a raised to power b in least number of multiplication.
     *
     * @param a
     * @param b
     * @return
     */
    public static long pow(long a, long b) {

        if (a == 1 || a == 0 || b == 1)
            return a;

        if (b == 0)
            return 1;

        boolean sign = true;
        if ((b & 1) == 1 && a < 0)
            sign = false;

        if (a < 0)
            a = -a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = res * res;
            if ((b & r) != 0) {
                res = res * a;
            }
        }
        return sign ? res : -res;
    }

    /**
     * This function returns inverse modulo of a modulo mod.
     * it's based on Fermat's Little Theorom.
     * This function is now obsolete as We have better function implemented
     * in {@link ModularArithmatic#inverseModulo}.
     *
     * @param a
     * @param mod should be a prime number.
     * @return
     */
    public static long inverseModulo(long a, long mod) {
        return pow(a, mod - 2, mod);
    }
}
