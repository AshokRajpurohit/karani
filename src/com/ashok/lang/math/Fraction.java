package com.ashok.lang.math;

/**
 * The {@code Fraction} class is to represent numbers in fraction format.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Fraction implements Comparable<Fraction> {
    public final long numerator, denominator;
    public final double value;

    public Fraction(long n, long d) {
        numerator = n;
        denominator = d;
        value = 1.0 * n / d;
    }

    public int compareTo(Fraction fraction) {
        return Double.compare(value, fraction.value);
    }

    public String toString() {
        return numerator + "/" + denominator;
    }

    public Fraction toReducedFraction() {
        long g = ModularArithmatic.gcd(numerator, denominator);
        return g == 1 ? this : new Fraction(numerator / g, denominator / g);
    }

    public Fraction add(Fraction fraction) {
        return add(toReducedFraction(), fraction.toReducedFraction());
    }

    private static Fraction add(Fraction a, Fraction b) {
        long n = a.numerator * b.denominator + a.denominator * b.numerator;
        long d = a.denominator * b.denominator;
        long g = ModularArithmatic.gcd(n, d);
        return new Fraction(n / g, d / g);
    }
}
