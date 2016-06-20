package com.ashok.projectEuler.problems;


public class P045 {
    public static long solve() {
        long pent = 1, hex = 1;

        for (int i = 166, j = 144; i < 1000000000; i++) {
            pent = 1L * i * (3 * i - 1);
            pent = pent >>> 1;

            if (hex < pent) {
                j++;
                hex = 1L * j * (2 * j - 1);
            }

            if (hex == pent)
                return hex;
        }

        return -1;
    }
}
