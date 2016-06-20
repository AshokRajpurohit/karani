package com.ashok.lang.utils;

import java.util.Random;

public class Generators {
    public static int[] gen_rand(int size, int mod) {
        Random random = new Random();
        int[] ar = new int[size];
        for (int i = 0; i < size; i++)
            ar[i] = random.nextInt(mod);

        return ar;
    }

    public static int[] gen_rand(int size, int start, int end) {
        int mod = end + 1 - start;
        int[] res = gen_rand(size, mod);

        for (int i = 0; i < size; i++)
            res[i] += start;

        return res;
    }
}
