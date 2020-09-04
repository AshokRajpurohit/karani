package com.ashok.lang.math;

/**
 * This class actually implements Guassian Integers rather than Complex Numbers
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class GuassianInteger {
    final long r; // real part
    final long i; // imaginary part
    final long norm;
    final GuassianInteger conjugate;
    public static final GuassianInteger IOTA = Unit.IOTA.c;
    public static final GuassianInteger ONE = Unit.ONE.c;

    public static final GuassianInteger ZERO = new GuassianInteger(0, 0);

    public static GuassianInteger of(final long r, final long i) {
        if (Math.abs(r) + Math.abs(i) > 1) return new GuassianInteger(r, i);
        if (r == 0 && i == 0) return ZERO;
        if (r == 0) return i == 1 ? Unit.IOTA.c : Unit.MINUS_IOTA.c;
        return r == 1 ? Unit.ONE.c : Unit.MINUS_ONE.c;
    }

    private GuassianInteger(final long r, final long i) {
        this.r = r;
        this.i = i;
        norm = r * r + i * i;
        conjugate = i == 0 ? this : new GuassianInteger(this);
    }

    private GuassianInteger(final GuassianInteger conjugate) {
        r = conjugate.r;
        i = -conjugate.i;
        this.conjugate = conjugate;
        norm = conjugate.norm;
    }

    public long getRealPart() {
        return r;
    }

    public long getImaginaryPart() {
        return i;
    }

    public long getNorm() {
        return norm;
    }

    public GuassianInteger add(GuassianInteger c) {
        return of(r + c.r, i + c.i);
    }

    public boolean checkDivisability(GuassianInteger c) {
        if (norm % c.norm != 0) return false;
        long rel = r * c.r - i * c.i;
        if (rel % c.norm != 0) return false;
        long imag = r * c.i + c.r * i;
        return imag % c.norm == 0;
    }

    public GuassianInteger subtract(GuassianInteger c) {
        return of(r - c.r, i - c.i);
    }

    public GuassianInteger divide(GuassianInteger c) {
        long rel = r * c.r + i * c.i;
        long imag = -r * c.i + c.r * i;
        return of(rel / c.norm, imag / c.norm);
    }

    public boolean isUnit() {
        for (Unit u : Unit.values()) {
            if (this == u.c) return true;
        }

        return false;
    }

    /**
     * Not reliable for calculating the actual gcd, the function can go in infinite loop, one such example:
     * -9+19i and -19+4i
     * @param a
     * @param b
     * @return
     */
    public static GuassianInteger gcd(GuassianInteger a, GuassianInteger b) {
        long gcdNorm = ModularArithmatic.gcd(a.norm, b.norm);
        if (gcdNorm == 1) return Unit.ONE.c;
        while (b != ZERO) {
            GuassianInteger temp = a.mod(b);
            a = b;
            b = temp;
        }

        return a;
    }

    public GuassianInteger mod(GuassianInteger c) {
        if (norm < c.norm) return this;
        GuassianInteger d = divide(c);
        return subtract(c.multiply(d));
    }

    public GuassianInteger multiply(GuassianInteger c) {
        long rel = r * c.r - i * c.i;
        long imag = r * c.i + c.r * i;
        return of(rel, imag);
    }

    /**
     * Checks if this number can be obtained multiplying unit numbers.
     *
     * @param c Complex number to check if the number unit multiple of this.
     * @return
     */
    public boolean checkSimilarity(GuassianInteger c) {
        if (norm != c.norm) return false;
        for (Unit u : Unit.values()) {
            GuassianInteger uc = u.c;
            long rel = uc.r * c.r - uc.i * c.i;
            long imag = uc.r * c.i + c.r * uc.i;
            if (rel == r && imag == i) return true;
        }

        return false;
    }

    @Override
    public String toString() {
        if (i == 0) return "" + r;
        if (r == 0) return i == 1 || i == -1 ? "i" : i + "i";
        if (Math.abs(i) == 1) return r + (i > 0 ? "+i" : "-i");
        return r + (i > 0 ? "+" : "") + i + "i";
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) return false;
        if (this == o) return true;
        if (!(o instanceof GuassianInteger)) return false;
        GuassianInteger c = (GuassianInteger) o;
        return r == c.r && i == c.i;
    }

    private enum Unit {
        ONE(1, 0),
        IOTA(0, 1),
        MINUS_ONE(-1, 0),
        MINUS_IOTA(0, -1);
        final GuassianInteger c;

        Unit(final long r, final long i) {
            c = new GuassianInteger(r, i);
        }
    }
}
