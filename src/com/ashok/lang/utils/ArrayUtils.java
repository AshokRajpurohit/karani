package com.ashok.lang.utils;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ArrayUtils {
    public static int[] toArray(Iterable<Integer> list) {
        int size = 0;
        for (Integer e : list)
            size++;

        int[] ar = new int[size];
        int index = 0;

        for (Integer e : list)
            ar[index++] = e;

        return ar;
    }

    public static void reverse(Object[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            Object temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    public static void reverse(char[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            char temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    public static void reverse(int[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            int temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    public static void reverse(long[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            long temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    public static void reverse(double[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            double temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }

    public static void reverse(short[] ar) {
        for (int i = 0, j = ar.length - 1; i < j; i++, j--) {
            short temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }
    }
}
