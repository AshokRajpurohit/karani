package com.ashok.lang.math;

/**
 * The {@code Numbers} class implements methods to format digits.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Numbers {
    private static int[] powerTensInteger = new int[10];
    private static long[] powerTensLong = new long[19];
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    static {
        powerTensInteger[0] = 1;
        powerTensLong[0] = 1;

        for (int i = 1; i < 10; i++)
            powerTensInteger[i] = (powerTensInteger[i - 1] << 3) + (powerTensInteger[i - 1] << 1);

        for (int i = 1; i < 19; i++)
            powerTensLong[i] = (powerTensLong[i - 1] << 3) + (powerTensLong[i - 1] << 1);
    }

    public static boolean isSamePermutation(long a, long b, int[] digits) {
        for (int i = 0; i < 10; i++)
            digits[i] = 0;

        while (a > 0) {
            digits[(int) (a % 10)]++;
            a /= 10;
        }

        while (b > 0) {
            digits[(int) (b % 10)]--;
            b /= 10;
        }

        for (int e : digits)
            if (e != 0)
                return false;

        return true;
    }

    public static boolean isSamePermutation(long a, long b) {
        int[] digits = new int[10];

        while (a > 0) {
            digits[(int) (a % 10)]++;
            a /= 10;
        }

        while (b > 0) {
            digits[(int) (b % 10)]--;
            b /= 10;
        }

        for (int e : digits)
            if (e != 0)
                return false;

        return true;
    }

    public static long firstDigit(long n) {
        if (n == 0)
            return 0;

        return n / powerTensLong[digitCounts(n) - 1];
    }

    public static int firstDigit(int n) {
        if (n == 0)
            return 0;

        return n / powerTensInteger[digitCounts(n) - 1];
    }

    public static int digitCounts(long n) {
        for (int i = 0; i < powerTensLong.length; i++)
            if (powerTensLong[i] > n)
                return i;

        return powerTensLong.length;
    }

    public static int digitCounts(int n) {
        for (int i = 0; i < powerTensInteger.length; i++)
            if (powerTensInteger[i] > n)
                return i;

        return powerTensInteger.length;
    }
}
