package com.ashok.projecteuler.problems;

import com.ashok.lang.math.ModularArithmatic;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P072 {
    public static long solve(int n) {
        int[] phi = ModularArithmatic.totientList(n);
        long sum = 0;

        for (int e : phi)
            sum += e;

        return sum;
    }
}
