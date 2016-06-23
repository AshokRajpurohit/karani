package com.ashok.lang.math;

/**
 * The {@code Fraction} class is to represent numbers in fraction format.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Fraction implements Comparable<Fraction> {
    public final int numerator, denominator;
    public final double value;

    public Fraction(int n, int d) {
        numerator = n;
        denominator = d;
        value = 1.0 * n / d;
    }

    public int compareTo(Fraction fraction) {
        return Double.compare(this.value, fraction.value);
    }

    public String toString() {
        return numerator + "/" + denominator;
    }

    public Fraction toReducedFraction() {
        int g = ModularArithmatic.gcd(numerator, denominator);

        return new Fraction(numerator / g, denominator / g);
    }
}
