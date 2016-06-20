package com.ashok.projecteuler.problems;


public class P040 {
    public static char solve(int n) {
        StringBuilder sb = new StringBuilder(n);

        for (int i = 1; sb.length() < n; i++)
            sb.append(i);

        return sb.charAt(n - 1);
    }
}
