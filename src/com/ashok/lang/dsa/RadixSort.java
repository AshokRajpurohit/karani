package com.ashok.lang.dsa;

import com.ashok.lang.math.Numbers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class RadixSort {
    private static int bits = 63, bitCount = 6;

    private RadixSort() {
        super();
    }

    public static void sort(int[] ar) {
        int pos_count = 0;
        for (int i = 0; i < ar.length; i++)
            if (ar[i] >= 0)
                pos_count++;

        int[] pos = new int[pos_count];
        int[] neg = new int[ar.length - pos_count];

        for (int i = 0, j = 0; i < ar.length && j < pos_count; i++)
            if (ar[i] >= 0) {
                pos[j] = ar[i];
                j++;
            }

        for (int i = 0, j = 0; i < ar.length && j < neg.length; i++)
            if (ar[i] < 0) {
                neg[j] = -ar[i];
                j++;
            }

        sortPos(pos);
        sortPos(neg);

        for (int i = neg.length - 1; i >= 0; i--)
            ar[neg.length - 1 - i] = -neg[i];

        for (int i = 0; i < pos.length; i++)
            ar[i + neg.length] = pos[i];
    }

    private static void sortPos(int[] ar) {
        int max = 0;
        for (int i = 0; i < ar.length; i++)
            if (ar[i] > max)
                max = ar[i];

        int xor = bits;
        int shift = 0;
        while (max > 0) {
            int[] temp = new int[bits + 1];
            for (int i = 0; i < ar.length; i++)
                temp[(ar[i] & xor) >>> shift]++;

            DArray[] list = new DArray[bits + 1];
            for (int i = 0; i < list.length; i++)
                list[i] = new DArray(temp[i]);

            for (int i = 0; i < ar.length; i++)
                list[(ar[i] & xor) >>> shift].add(ar[i]);

            for (int i = 0, j = 0; i < ar.length && j <= bits; j++) {
                for (int k = 0; k < list[j].size; k++, i++)
                    ar[i] = list[j].list[k];
            }

            max = max >>> bitCount;
            shift += bitCount;
            xor = xor << bitCount;
        }
    }

    /**
     * Sorts numbers in string format.
     *
     * @param ar
     */
    public static void sortNumberStrings(String[] ar) {
        int maxLength = 0;
        for (String e : ar)
            maxLength = Math.max(maxLength, e.length());

        for (int i = 0; i < ar.length; i++)
            ar[i] = Numbers.normalizeNumberString(ar[i]);

        Arrays.sort(ar, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        for (int i = 0; i < ar.length; ) {
            int j = i + 1;

            while (j < ar.length && ar[j].length() == ar[i].length())
                j++;

            sortNumberStrings(ar, i, j - 1);
            i = j;
        }
    }

    private static void sortNumberStrings(String[] ar, int start, int end) {
        int index = ar[start].length() - 1;

        while (index >= 0) {
            sortNumberStrings(ar, start, end, index);
            index--;
        }
    }

    private static void sortNumberStrings(String[] ar, int start, int end, int index) {
        if (start == end)
            return;

        LinkedList<String>[] map = new LinkedList[256];
        for (int i = '0'; i <= '9'; i++)
            map[i] = new LinkedList<>();

        for (int i = start; i <= end; i++)
            map[ar[i].charAt(index)].addLast(ar[i]);

        int arrIndex = start;
        for (int i = '0'; i <= '9'; i++) {
            LinkedList<String> list = map[i];
            for (String str : list)
                ar[arrIndex++] = str;
        }
    }
}
