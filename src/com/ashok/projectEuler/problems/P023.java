package com.ashok.projecteuler.problems;


public class P023 {
    private P023() {
        super();
    }

    public static int solve(int n) {
        int[] divSum = new int[n + 1];
        boolean[] abundant = new boolean[n + 1];

        for (int i = 1; i <= n; i++)
            divSum[i] = 1;

        for (int i = 2; i <= n; i++)
            for (int j = (i << 1); j <= n; j += i)
                divSum[j] += i;

        for (int i = 3; i <= n; i++)
            if (divSum[i] > i)
                abundant[i] = true;

        boolean[] ar = new boolean[n + 1];
        for (int i = 1; i <= (n >> 1); i++)
            if (abundant[i])
                for (int j = i; j <= n - i; j++)
                    if (abundant[j])
                        ar[i + j] = true;

        int res = 0;
        for (int i = 1; i <= n; i++)
            if (!ar[i])
                res += i;

        return res;
    }
}
