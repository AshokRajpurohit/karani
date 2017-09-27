package com.ashok.lang.math;

import java.math.BigInteger;

/**
 * Alternative to {@link Fraction} where numerator and denominator are exceptionally large
 * as in the case of evaluation of {@code ContinuedFraction}.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class BigFraction implements Comparable<BigFraction> {
    public final BigInteger numerator, denominator;

    public BigFraction(long a, long b) {
        this(String.valueOf(a), String.valueOf(b));
    }

    public BigFraction(String a, String b) {
        this(new BigInteger(a), new BigInteger(b));
    }

    public BigFraction(BigInteger a, BigInteger b) {
        numerator = a;
        denominator = b;
    }

    public BigFraction(Fraction fraction) {
        this(fraction.numerator, fraction.denominator);
    }

    public BigFraction toInverse() {
        return new BigFraction(denominator, numerator);
    }

    public String toString() {
        return numerator + " / " + denominator;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BigFraction))
            return false;

        BigFraction bf = (BigFraction) o;
        return bf.numerator.equals(numerator) && denominator.equals(bf.denominator);
    }

    public BigFraction add(BigFraction fraction) {
        return add(this, fraction);
    }

    public static BigFraction add(BigFraction a, BigFraction b) {
        BigInteger n = a.numerator.multiply(b.denominator).add(a.denominator.multiply(b.numerator));
        BigInteger d = a.denominator.multiply(b.denominator);
        return new BigFraction(n, d);
    }

    public BigFraction subtract(BigFraction fraction) {
        return subtract(this, fraction);
    }

    public static BigFraction subtract(BigFraction a, BigFraction b) {
        BigInteger n = a.numerator.multiply(b.denominator).subtract(a.denominator.multiply(b.numerator));
        BigInteger d = a.denominator.multiply(b.denominator);
        return new BigFraction(n, d);
    }

    public int compareNumerator(BigFraction fraction) {
        numerator.add(denominator);
        return numerator.compareTo(fraction.numerator);
    }

    public int compareDenominator(BigFraction fraction) {
        return denominator.compareTo(fraction.denominator);
    }

    @Override
    public int compareTo(BigFraction fraction) {
        return (numerator.multiply(fraction.denominator))
                .compareTo(denominator.multiply(fraction.numerator));
    }
}
