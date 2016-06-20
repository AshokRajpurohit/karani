package com.ashok.projecteuler.problems;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P066 {
    public static void solve() {
        int max = 0, maxD = 0;

        for (int d = 2; d <= 1000; d++) {
            if (isSquare(d))
                continue;

            for (int x = (int) Math.sqrt(d + 1); x <= 1000000; x++) {
                long square = 1L * x * x;
                if ((square - 1) % d != 0)
                    continue;

                long y = (square - 1) / d;
                if (y != 0 && isSquare(y)) {
                    if (max < x) {
                        max = x;
                        maxD = d;
                    }
                    break;
                }
            }
        }

        System.out.println(max + "\t" + maxD);
    }

    private static boolean isSquare(long n) {
        long r = (long) Math.sqrt(n);

        return r * r == n;
    }
}
