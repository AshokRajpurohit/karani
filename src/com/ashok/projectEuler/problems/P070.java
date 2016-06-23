package com.ashok.projecteuler.problems;

import com.ashok.lang.math.ModularArithmatic;
import com.ashok.lang.math.Numbers;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P070 {
    public static int solve(int n) {
        int[] phi = ModularArithmatic.totientList(n);

        double min = 100.0, ratio = 0.0;
        int nmin = 0;
        int[] buf = new int[10];

        for (int i = 2; i <= n; i++) {
            if (!Numbers.isSamePermutation(i, phi[i], buf))
                continue;

            ratio = 1.0 * i / phi[i];
            if (ratio < min) {
                min = ratio;
                nmin = i;
            }
        }

        return nmin;
    }
}
