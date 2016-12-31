package com.ashok.lang.math;

/**
 * The {@code ContinuedFraction} is to implmement Continued Fraction for mathematics.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public interface ContinuedFraction {

    /**
     * Returns nth integer value in fraction.
     *
     * @param n
     * @return
     */
    int getCoefficient(int n);
}
