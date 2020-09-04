package com.ashok.lang.math;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@code ContinuedFraction} is to implmement Continued Fraction for mathematics.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public interface ContinuedFraction {
    static BigFraction evaluate(List<Fraction> list) {
        LinkedList<Fraction> copy = new LinkedList<>(list);
        BigFraction value = new BigFraction(copy.removeLast());
        int size = copy.size();

        for (int i = 0; i < size; i++) {
            value = value.toInverse().add(new BigFraction(copy.removeLast()));
        }

        return value;
    }

    ContinuedFraction nextFraction();

    Fraction toFraction();
}
