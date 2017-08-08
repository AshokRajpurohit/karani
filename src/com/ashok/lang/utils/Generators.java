package com.ashok.lang.utils;

import com.ashok.lang.dsa.RandomStrings;

import java.util.Random;

/**
 * The {@code Generators} class is to generate random test data for testing.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Generators {
    public static int[] generateRandomIntegerArray(int size, int mod) {
        Random random = new Random();
        int[] ar = new int[size];
        for (int i = 0; i < size; i++)
            ar[i] = random.nextInt(mod);

        return ar;
    }

    public static int[] generateRandomIntegerArray(int size) {
        Random random = new Random();
        int[] ar = new int[size];
        for (int i = 0; i < size; i++)
            ar[i] = random.nextInt();

        return ar;
    }

    public static int[] generateRandomIntegerArray(int size, int start, int end) {
        if (end < start)
            return generateRandomIntegerArray(size, end, start);

        int mod = end + 1 - start;
        int[] res = generateRandomIntegerArray(size, mod);

        for (int i = 0; i < size; i++)
            res[i] += start;

        return res;
    }

    public static long[] generateRandomLongArray(int size) {
        Random random = new Random();
        long[] ar = new long[size];

        for (int i = 0; i < size; i++)
            ar[i] = random.nextLong();

        return ar;
    }

    public static long[] generateRandomLongArray(int size, long mod) {
        long[] ar = generateRandomLongArray(size);

        for (int i = 0; i < size; i++) {
            ar[i] %= mod;

            if (ar[i] < 0)
                ar[i] += mod;
        }

        return ar;
    }

    public static long[] generateRandomLongArray(int size, long start, long end) {
        if (end < start)
            return generateRandomLongArray(size, end, start);

        long mod = end + 1 - start;
        long[] res = generateRandomLongArray(size, mod);

        for (int i = 0; i < size; i++)
            res[i] += start;

        return res;
    }

    public static String[] generateRandomStringArray(int size, int minLength, int maxLength) {
        String[] res = new String[size];
        int[] lenArray = generateRandomIntegerArray(size, minLength, maxLength);
        RandomStrings randomStrings = new RandomStrings();

        for (int i = 0; i < size; i++)
            res[i] = randomStrings.nextStringAaBb(lenArray[i]);

        return res;
    }

    /**
     * Generates random string consisting of characters from {@code operators}.
     *
     * @param length    expression length
     * @param operators character array
     * @return random string using characters from {@code operators} only.
     */
    public static String generateRandomExpression(int length, char[] operators) {
        StringBuilder sb = new StringBuilder(length * 10);
        Random random = new Random();
        int n = operators.length;
        char[] expression = new char[length];

        for (int i = 0; i < length; i++)
            expression[i] = operators[random.nextInt(n)];

        return String.valueOf(expression);
    }
}
