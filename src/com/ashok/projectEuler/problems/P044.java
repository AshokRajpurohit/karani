package com.ashok.projecteuler.problems;


public class P044 {

    private static int[] pentagonNumbers;

    static {
        int[] ar = new int[10000];
        ar[0] = 1;
        int tn = 1;

        for (int i = 1; i < ar.length; i++) {
            tn += 3;
            ar[i] = ar[i - 1] + tn;
        }

        pentagonNumbers = ar;
    }

    public static String solve() {
        Pair min = new Pair(pentagonNumbers[0], pentagonNumbers[9999]);

        for (int i = 0; i < pentagonNumbers.length; i++) {
            for (int j = i + 1; j < pentagonNumbers.length; j++) {
                int diff = pentagonNumbers[j] - pentagonNumbers[i];
                if (!isPentagonNumber(diff))
                    continue;

                int sum = pentagonNumbers[i] + pentagonNumbers[j];
                if (!isPentagonNumber(sum))
                    continue;

                if (diff < min.b - min.a) {
                    min.a = pentagonNumbers[i];
                    min.b = pentagonNumbers[j];
                }
            }
        }

        return min.toString();
    }

    public static int pentagonNumbers(int i) {
        return pentagonNumbers[i];
    }

    public static boolean isPentagonNumber(int n) {
        n = (n << 1);
        int m = (int) Math.sqrt(n / 3);

        if (m * (3 * m - 1) == n || (m + 1) * (3 * m + 2) == n)
            return true;

        return false;
    }

    final static class Pair {
        int a, b;

        Pair(int x, int y) {
            a = x;
            b = y;
        }

        public String toString() {
            return a + ", " + b;
        }
    }
}
