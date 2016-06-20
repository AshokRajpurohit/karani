package com.ashok.projecteuler.problems;


public class P053 {
    public static int solve(int n, int limit) {
        int res = 0;

        for (int i = 2; i <= n; i++)
            res += process(i, limit);

        return res;
    }

    public static int process(int n, int limit) {
        long value = 1;
        int count = 2, numerator = n, denomiantor = 1;

        while (value <= limit && numerator >= denomiantor) {
            value *= numerator;
            value /= denomiantor;

            if (value <= limit) {
                if (numerator > denomiantor)
                    count += 2;
                else
                    count++;
            }

            numerator--;
            denomiantor++;
        }

        int res = n + 1 - count;

        if (res < 0)
            return 0;

        return res;
    }
}
