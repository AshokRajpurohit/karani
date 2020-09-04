package com.ashok.lang.math;

import java.util.LinkedList;
import java.util.List;

/**
 * it is like
 * d + a / (ﾚb + c )
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class SquareRootContinuedFraction implements ContinuedFraction {
    private final static ContinuedFraction INVALID_FRACTION = new SquareRootContinuedFraction(-1, -1, -1);
    final long a, b, c, d;
    final int hash;

    SquareRootContinuedFraction(long a, long b, long c) {
        this(a, b, c, 0);
    }

    SquareRootContinuedFraction(long a, long b, long c, long d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        hash = Long.hashCode(1L * Long.hashCode(a * b) * Long.hashCode(c * d));
    }

    public SquareRootContinuedFraction(long n) {
        a = 1;
        b = n;
        c = -(int) Math.sqrt(n);
        d = -c;
        hash = hashCode();
    }

    public int hashCode() {
        return hash;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SquareRootContinuedFraction))
            return false;

        SquareRootContinuedFraction cf = (SquareRootContinuedFraction) o;
        return a == cf.a && b == cf.b && c == cf.c;
    }

    public SquareRootContinuedFraction nextFraction() {
        double numerator = Math.sqrt(b) - c;
        long denominator = b - c * c;
        denominator /= a; // a divides this new denominator perfectly. do some paperwork and prove it.
        long newC = -c - denominator * (int) (numerator / denominator);
        long newD = denominator * (long) (numerator / denominator);

        return new SquareRootContinuedFraction(denominator, b, newC, newD / denominator);
    }

    public Fraction toFraction() {
        return new Fraction(d, 1);
    }

    public String toString() {
        return d + " +  " + a + " / (ﾚ" + b + " " + c + ")";
    }
}
