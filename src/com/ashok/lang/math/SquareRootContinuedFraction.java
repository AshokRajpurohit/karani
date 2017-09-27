package com.ashok.lang.math;

/**
 * it is like
 * d + a / (ﾚb + c )
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class SquareRootContinuedFraction implements ContinuedFraction {
    private final static ContinuedFraction INVALID_FRACTION = new SquareRootContinuedFraction(-1, -1, -1);
    final int a, b, c, d;
    final int hash;

    SquareRootContinuedFraction(int a, int b, int c) {
        this(a, b, c, 0);
    }

    SquareRootContinuedFraction(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        hash = Long.hashCode(1L * Long.hashCode(a * b) * Long.hashCode(c * d));
    }

    public SquareRootContinuedFraction(int n) {
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
        int denominator = b - c * c;
        denominator /= a; // a divides this new denominator perfectly. do some paperwork and prove it.
        int newC = -c - denominator * (int) (numerator / denominator);
        int newD = denominator * (int) (numerator / denominator);

        return new SquareRootContinuedFraction(denominator, b, newC, newD / denominator);
    }

    public Fraction toFraction() {
        return new Fraction(d, 1);
    }

    public String toString() {
        return d + " +  " + a + " / (ﾚ" + b + " " + c + ")";
    }
}
