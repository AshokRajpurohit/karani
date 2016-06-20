package com.ashok.projectEuler.problems;


public class P021 {
    private static int[] ar;

    private P021() {
        super();
    }

    public static long solve(int n) {
        if (n > 10000000)
            return -1;

        int m = n;
        n = (n << 2);
        ar = new int[n];
        for (int i = 1; i < n; i++)
            ar[i] = 1;

        for (int i = 2; i < n; i++)
            for (int j = (i << 1); j < n; j += i)
                ar[j] += i;

        long sum = 0;
        for (int i = 2; i < m; i++)
            if (i == ar[ar[i]] && i != ar[i])
                sum += i;

        System.out.println(ar[220] + "\t" + ar[284]);

        return sum;
    }
}
