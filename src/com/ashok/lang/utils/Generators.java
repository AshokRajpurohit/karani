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

    public static String generateRandomExpression(int length, char[] operators) {
        StringBuilder sb = new StringBuilder(length * 10);
        Random random = new Random();
        int n = operators.length;

        sb.append(Math.abs(random.nextLong()));
        for (int i = 0; i < length; i++) {
            sb.append(operators[random.nextInt(n)]);
            sb.append(Math.abs(random.nextLong()));
        }

        return sb.toString();
    }

    public static String generateRandomExpression(int length, char[]
            operators, long mod) {
        StringBuilder sb = new StringBuilder(length * 10);
        Random random = new Random();
        int n = operators.length;

        sb.append(Math.abs(random.nextLong()) % mod);
        for (int i = 0; i < length; i++) {
            char operator = operators[random.nextInt(n)];
            sb.append(operator);

            long num = Math.abs(random.nextLong() % mod);
            if (operator == '/') {
                while (num == 0)
                    num = Math.abs(random.nextLong() % mod);
            }

            sb.append(num);
        }

        return sb.toString();
    }
}
