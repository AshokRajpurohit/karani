package com.ashok.lang.dsa;

public class CountSort {
    private CountSort() {
        super();
    }

    public static void sort(int[] ar) {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] > max)
                max = ar[i];

            if (ar[i] < min)
                min = ar[i];
        }

        int range = max - min + 1;
        if (range > ar.length) {
            QuickSort.sort(ar);
            return;
        }

        for (int i = 0; i < ar.length; i++)
            ar[i] -= min;

        int[] count = new int[range];
        for (int i = 0; i < ar.length; i++)
            count[ar[i]]++;

        for (int i = 1; i < range; i++)
            count[i] += count[i - 1];

        int[] res = new int[ar.length];

        for (int i = ar.length - 1; i >= 0; i--) {
            res[count[ar[i]] - 1] = ar[i] + min;
            count[ar[i]]--;
        }

        for (int i = 0; i < ar.length; i++)
            ar[i] = res[i];
    }

    public static void sort(char[] ar) {
        int[] count = new int[256];
        for (int i = 0; i < ar.length; i++)
            count[ar[i]]++;

        for (int i = 1; i < 256; i++)
            count[i] += count[i - 1];

        char[] res = new char[ar.length];

        for (int i = ar.length - 1; i >= 0; i--) {
            res[count[ar[i]] - 1] = ar[i];
            count[ar[i]]--;
        }

        for (int i = 0; i < ar.length; i++)
            ar[i] = res[i];
    }

    public static char[] sort(String s) {
        char[] ar = s.toCharArray();
        sort(ar);
        return ar;
    }
}
