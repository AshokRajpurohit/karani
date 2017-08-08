package com.ashok.lang.utils;

import java.util.Arrays;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Utils {
    /**
     * Returns array of next higher element index.
     * At index i the array contains the index of next higher element.
     * If there is no higher element right to it, than the value is array size.
     *
     * @param ar
     * @return
     */
    public static int[] nextHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    public static int[] nextSmaller(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] >= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    public static int[] previousHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[0] = -1;

        for (int i = 1; i < ar.length; i++) {
            int j = i - 1;

            while (j >= 0 && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    public static int[] previousSmaller(int[] ar) {
        int[] res = new int[ar.length];
        res[0] = -1;

        for (int i = 1; i < ar.length; i++) {
            int j = i - 1;

            while (j >= 0 && ar[j] >= ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    public static int[] nextNonSmaller(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] < ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    public static int[] nextNonHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = ar.length - 2; i >= 0; i--) {
            int j = i + 1;

            while (j < ar.length && ar[j] > ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    public static int[] previousNonHigher(int[] ar) {
        int[] res = new int[ar.length];
        res[0] = -1;

        for (int i = 1; i < ar.length; i++) {
            int j = i - 1;

            while (j >= 0 && ar[j] > ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    public static int[] previousNonSmaller(int[] ar) {
        int[] res = new int[ar.length];
        res[0] = -1;

        for (int i = 1; i < ar.length; i++) {
            int j = i - 1;

            while (j >= 0 && ar[j] < ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    /**
     * Returns array of integers having index of next equal element to it.
     * If the element doesn't have any such element then the index for it would
     * be the array size.
     * <p>
     * for array
     * {1, 2, 3, 2, 1}
     * next equal elements indices should be
     * {4, 3, 5, 5, 5}
     * <p>
     * The parameter array doesn't need to be sorted.
     *
     * @param ar
     * @return
     */
    public static int[] nextEqual(int[] ar) {
        int[] res = getIndexArray(ar.length);
        Pair[] pairs = new Pair[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = 0; i < ar.length; i++)
            pairs[i] = new Pair(i, ar[i]);

        Arrays.sort(pairs);

        for (int i = ar.length - 2; i >= 0; i--) {
            if (pairs[i].value == pairs[i + 1].value)
                res[pairs[i].index] = pairs[i + 1].index;
            else
                res[pairs[i].index] = ar.length;
        }

        return res;
    }

    /**
     * Returns array of integer having index of previous equal element to it.
     * If the element doesn't have any such element then the index for it
     * would be -1 to indicate non-existance of any previous duplicate.
     * <p>
     * for array
     * {1, 2, 3, 2, 1}
     * previous equal elements indices should be
     * {-1, -1, -1, 1, 0}
     * <p>
     * The parameter array doesn't need to be sorted.
     *
     * @param ar
     * @return
     */
    public static int[] previousEqual(int[] ar) {
        int[] res = getIndexArray(ar.length);
        Pair[] pairs = new Pair[ar.length];
        res[0] = -1;

        for (int i = 0; i < ar.length; i++)
            pairs[i] = new Pair(i, ar[i]);

        Arrays.sort(pairs);

        for (int i = 1; i < ar.length; i++) {
            if (pairs[i].value == pairs[i - 1].value)
                res[pairs[i].index] = pairs[i - 1].index;
            else
                res[pairs[i].index] = -1;
        }

        return res;
    }

    public static int[] getIndexArray(int size) {
        int[] ar = new int[size];

        for (int i = 1; i < size; i++)
            ar[i] = i;

        return ar;
    }

    public static boolean isAllUnique(Comparable[] ar) {
        Comparable[] aux = ar.clone();
        Arrays.sort(aux);

        for (int i = 1; i < aux.length; i++)
            if (aux[i].compareTo(aux[i - 1]) == 0)
                return false;

        return true;
    }

    public static boolean allEquals(Comparable[] ar) {
        for (int i = 1; i < ar.length; i++)
            if (ar[i].compareTo(ar[i - 1]) != 0)
                return false;

        return true;
    }

    public static long[] getSumArray(int[] ar) {
        int len = ar.length;
        long[] sum = new long[len];
        sum[0] = ar[0];

        for (int i = 1; i < len; i++)
            sum[i] = sum[i - 1] + ar[i];

        return sum;
    }

    final static class Pair implements Comparable<Pair> {
        int index;
        Comparable value;

        Pair(int i, Comparable v) {
            index = i;
            value = v;
        }


        @Override
        public int compareTo(Pair pair) {
            int compare = value.compareTo(pair.value);

            return compare == 0 ? index - pair.index : compare;
        }
    }
}
