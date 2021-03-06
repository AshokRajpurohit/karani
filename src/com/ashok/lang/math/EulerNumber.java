package com.ashok.lang.math;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public enum EulerNumber implements ContinuedFraction {
    e();

    public int getCoefficient(int n) {
        if (n == 0)
            return 2;

        if (n % 3 != 2)
            return 1;

        return ((n + 1) / 3) << 1;
    }

    public ContinuedFraction nextFraction() {
        return this;
    }

    public Fraction toFraction() {
        return new Fraction(1, 1);
    }
}