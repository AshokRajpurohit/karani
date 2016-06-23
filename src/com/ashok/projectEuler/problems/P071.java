package com.ashok.projecteuler.problems;

import com.ashok.lang.math.Fraction;
import com.ashok.lang.math.ModularArithmatic;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P071 {
    public static int solve(int n) {
        Fraction maxFraction = new Fraction(1, 3), three7 = new Fraction(3, 7);

        for (int i = n; i >= 2; i--) {
            int j = i * 3 / 7;
            Fraction temp = new Fraction(j, i);
            while (j > 0 && three7.compareTo(temp) <= 0) {
                j--;
                temp = new Fraction(j, i);
            }

            while (j > 1 && ModularArithmatic.gcd(j, i) != 1)
                j--;

            temp = new Fraction(j, i);
            if (maxFraction.compareTo(temp) < 0)
                maxFraction = temp;
        }

        System.out.println(maxFraction);
        return maxFraction.numerator;
    }
}
