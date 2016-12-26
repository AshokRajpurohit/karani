package com.ashok.projecteuler.problems;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem: Odd period square roots
 * Link: https://projecteuler.net/problem=64
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P064 {

    public static int solve(int size) {
        boolean[] irrationalMap = irrationals(size + 1);
        int count = 0;

        for (int i = 2; i <= size; i++) {
            if (!irrationalMap[i])
                continue;

            if (calculateFractionPeriod(i) % 2 == 1)
                count++;
        }

        return count;
    }

    private static int calculateFractionPeriod(int nonSquareNumber) {
        LinkedList<ContinuedFraction> list = new LinkedList<>();

        ContinuedFraction fraction = new ContinuedFraction(nonSquareNumber);
        list.add(fraction);

        fraction = fraction.nextFraction();
        while (!list.contains(fraction)) {
            list.add(fraction);
            fraction = fraction.nextFraction();
        }

        return list.size();
    }

    private static boolean[] irrationals(int size) {
        boolean[] map = new boolean[size];
        Arrays.fill(map, true);

        int sqrt = (int) Math.sqrt(size - 1);

        for (int i = 0; i <= sqrt; i++)
            map[i * i] = false;

        return map;
    }

    /**
     * This fraction is in like
     * a / ( √b + c )
     */
    final static class ContinuedFraction {
        private final static ContinuedFraction INVALID_FRACTION = new ContinuedFraction(-1, -1, -1);
        final int a, b, c;

        ContinuedFraction(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        ContinuedFraction(int n) {
            a = 1;
            b = n;
            c = -(int) Math.sqrt(n);
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof ContinuedFraction))
                return false;

            ContinuedFraction cf = (ContinuedFraction) o;
            return a == cf.a && b == cf.b && c == cf.c;
        }

        public ContinuedFraction nextFraction() {
            double numerator = Math.sqrt(b) - c;
            int denominator = b - c * c;
            denominator /= a; // a divides this new denominator perfectly. do some paperwork and prove it.
            int newC = -c - denominator * (int) (numerator / denominator);

            return new ContinuedFraction(denominator, b, newC);
        }

        public String toString() {
            return a + " / (√" + b + " " + c + ")";
        }
    }
}
