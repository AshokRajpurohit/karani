package com.ashok.lang.utils;

import java.util.Random;

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

    public static int count(int[] ar, int value) {
        int count = 0;

        for (int e : ar)
            if (e == value)
                count++;

        return count;
    }

    public static int count(long[] ar, long value) {
        int count = 0;

        for (long e : ar)
            if (e == value)
                count++;

        return count;
    }

    public static int count(boolean[] ar, boolean value) {
        int count = 0;

        for (boolean e : ar)
            if (e == value)
                count++;

        return count;
    }

    public static int count(char[] ar, char value) {
        int count = 0;

        for (char e : ar)
            if (e == value)
                count++;

        return count;
    }

    public static int count(double[] ar, double value) {
        int count = 0;

        for (double e : ar)
            if (e == value)
                count++;

        return count;
    }

    public static<T> int count(Iterable<T> list, T value) {
        int count = 0;

        for (T t : list)
            if (value.equals(t))
                count++;

        return count;
    }

    public static int max(int[] ar) {
        int max = ar[0];

        for (int e : ar)
            max = Math.max(e, max);

        return max;
    }

    public static long max(long[] ar) {
        long max = ar[0];

        for (long e : ar)
            max = Math.max(e, max);

        return max;
    }

    public static double max(double[] ar) {
        double max = ar[0];

        for (double e : ar)
            max = Math.max(e, max);

        return max;
    }

    public static Comparable max(Comparable[] ar) {
        Comparable max = ar[0];

        for (Comparable t : ar)
            if (max.compareTo(t) > 0)
                max = t;

        return max;
    }

    public static Comparable max(Iterable<Comparable> list) {
        Comparable max = null;

        for (Comparable e : list) {
            if (max == null || max.compareTo(e) > 0)
                max = e;
        }

        return max;
    }

    public static void randomizeArray(int[] ar) {
        Random random = new Random();
        for (int i = 0; i < ar.length; i++) {
            int j = random.nextInt(ar.length);
            while (j == i)
                j = random.nextInt(ar.length);

            swap(ar, i, j);
        }
    }

    public static void randomizeArray(Object[] objects) {
        Random random = new Random();
        for (int i = 0; i < objects.length; i++) {
            int j = random.nextInt(objects.length);
            while (j == i)
                j = random.nextInt(objects.length);

            swap(objects, i, j);
        }
    }

    public static void swap(int[] ar, int i, int j) {
        int temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
    }

    public static void swap(Object[] objects, int i, int j) {
        Object temp = objects[i];
        objects[i] = objects[j];
        objects[j] = temp;
    }
}
