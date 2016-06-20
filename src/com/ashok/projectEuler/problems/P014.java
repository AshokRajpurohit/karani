package com.ashok.projecteuler.problems;


public class P014 {
    private static int[] seq = new int[1000000];

    static {
        seq[1] = 1;
    }

    private P014() {
        super();
    }

    public static long solve() {
        for (int i = 2; i < 1000000; i++)
            process(i);

        int max = 1;
        for (int i = 1; i < 1000000; i++)
            if (seq[max] < seq[i])
                max = i;

        System.out.println(seq[max] + "\t" + max);
        return max;
    }

    private static int process(long n) {
        if (n < 1000000 && seq[(int) n] > 0)
            return seq[(int) n];

        int len = 0;

        if ((n & 1) == 0)
            len = process(n / 2) + 1;
        else
            len = process(n * 3 + 1) + 1;

        if (n < 1000000)
            seq[(int) n] = len;

        return len;
    }
}
