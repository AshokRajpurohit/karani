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

    public static int[] generateRandomIntegerArray(int size, int start, int end) {
        int mod = end + 1 - start;
        int[] res = generateRandomIntegerArray(size, mod);

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
}
