package com.ashok.projecteuler.problems;

import com.ashok.lang.math.Fraction;
import com.ashok.lang.math.ModularArithmatic;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P073 {
    public static int solve(int n, int n1, int d1, int n2, int d2) {
        Fraction min = new Fraction(n1, d1), max = new Fraction(n2, d2);
        System.out.println("min fraction is: " + min + " and max is: " + max);

        if (min.compareTo(max) > 0) {
            Fraction temp = min;
            min = max;
            max = temp;
        }
        int count = 0;

        for (int i = n; i > 1; i--) {
            int j = i * n2 / d2;
            Fraction temp = new Fraction(j, i);

            while (max.compareTo(temp) <= 0) {
                j--;
                temp = new Fraction(j, i);
            }

            while (min.compareTo(temp) < 0) {
                if (ModularArithmatic.gcd(j, i) == 1)
                    count++;

                j--;
                temp = new Fraction(j, i);
            }
        }

        return count;
    }
}
