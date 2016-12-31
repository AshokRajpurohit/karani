package com.ashok.lang.math;

import com.ashok.lang.utils.ArrayUtils;

/**
 * The {@code Numbers} class implements methods to format digits.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Numbers {
    private static int[] powerTensInteger = new int[10];
    private static long[] powerTensLong = new long[19];
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    final static int[] charToDigits = new int[256];

    static {
        powerTensInteger[0] = 1;
        powerTensLong[0] = 1;

        for (int i = 1; i < 10; i++)
            powerTensInteger[i] = (powerTensInteger[i - 1] << 3) + (powerTensInteger[i - 1] << 1);

        for (int i = 1; i < 19; i++)
            powerTensLong[i] = (powerTensLong[i - 1] << 3) + (powerTensLong[i - 1] << 1);

        for (int i = '1'; i <= '9'; i++)
            charToDigits[i] = i - '0';
    }

    public static char[] toCharArray(long n) {
        int size = 1;
        long copy = n;

        while (n > 9) {
            copy /= 10;
            size++;
        }

        char[] ar = new char[size];
        int index = 0;

        while (n > 0) {
            ar[index++] = digits[(int) (n % 10)];
            n /= 10;
        }

        ArrayUtils.reverse(ar);
        return ar;
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

    public static int digitSum(String numString) {
        int sum = 0;

        for (int i = 0; i < numString.length(); i++)
            sum += charToDigits[numString.charAt(i)];

        return sum;
    }

    /**
     * index is from right to left. the rightmost digit is at index 0.
     *
     * @param num   number to be formatted.
     * @param digit new digit for the position.
     * @param index position from right.
     * @return number with replaced digit.
     */
    public static long replaceDigit(long num, int digit, int index) {
        long ten = powerTensLong[index], nextTen = ten * 10;
        return nextTen * (num / nextTen) + num % ten + digit * ten;
    }

    /**
     * index is from right to left. the rightmost digit is at index 0.
     *
     * @param num   number to be formatted.
     * @param digit new digit for the position.
     * @param index position from right.
     * @return number with replaced digit.
     */
    public static int replaceDigit(int num, int digit, int index) {
        int ten = powerTensInteger[index], nextTen = ten * 10;
        return nextTen * (num / nextTen) + num % ten + digit * ten;
    }
}
