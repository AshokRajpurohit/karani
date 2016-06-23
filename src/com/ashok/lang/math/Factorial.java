package com.ashok.lang.math;

import java.math.BigInteger;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class Factorial {

    private static long[] fact;

    static {
        fact = new long[21];
        fact[0] = 1;
        for (int i = 1; i < 21; i++)
            fact[i] = fact[i - 1] * i;
    }

    private Factorial() {
        super();
    }

    public static long fact(int i) {
        if (i > 20)
            return 0;
        return fact[i];
    }

    /**
     *  need to work more.
     *  not giving correct results
     *  it's incorrect, forget about this.
     * @param n
     * @return
     */

    private static BigInteger facto(int n) {
        double nbyE = n / Math.E;
        StringBuilder sbnum = new StringBuilder(String.valueOf(nbyE));
        StringBuilder sbden = new StringBuilder((int)n * 9);
        StringBuilder sbzero = new StringBuilder(18);
        sbden.append('1');
        int i = 0;
        while (sbnum.charAt(i) != '.')
            i++;
        sbnum.deleteCharAt(i);
        while (i < sbnum.length()) {
            sbzero.append('0');
            i++;
        }

        for (i = 1; i < n; i++)
            sbden.append(sbzero);

        BigInteger res = new BigInteger(sbnum.toString()).pow(n);
        res = res.divide(new BigInteger(sbden.toString()));
        double twoPIn = 2 * n * Math.PI;
        long deno = 1;
        while ((long)twoPIn != twoPIn) {
            deno *= 10;
            twoPIn *= 10;
        }

        res = res.multiply(new BigInteger(String.valueOf((long)twoPIn)));
        return res.divide(new BigInteger(String.valueOf(deno)));
    }

    /**
     * There is no guarantee that this function will work as the power of
     * double will be double and so it won't result into an integer,
     * obviously the answer is long or int.
     * @param n
     * @param mod
     * @return
     */

    public static long fact(long n, long mod) {
        if (n <= 20)
            return fact[(int)n] % mod;

        if (n >= mod)
            return 0;

        return (long)(Power.pow(n / Math.E, n, mod) *
                      (Math.sqrt(2 * Math.PI * n) % mod) % mod);
    }

    /**
     * This function calculates no of digits in factorial of n using
     * Stirling�s Formula. For more information follow the link.
     *  http://en.wikipedia.org/wiki/Stirling%27s_approximation
     * @param n
     * @return the number of digits in fact(n)
     */
    public static long DigFact(long n) {
        return (long)Math.ceil(n * (Math.log10(n / Math.E)) +
                               Math.log10(2 * Math.PI * n) / 2);
    }

    /**
     * returns number of zero digits trailing in factorial(n)
     * @param n
     * @return
     */

    public static long zeros(long n) {
        long count = 0;
        while (n > 4) {
            n /= 5;
            count += n;
        }
        return count;
    }
}
