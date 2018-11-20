/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.simility;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Generators;

import java.io.IOException;

/**
 * Problem Name: Shuffle array in zig-zag order
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Simility {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
//        int[] ar = in.readIntArray(in.readInt());
//        QuickSort.kthMinElement(ar, ar.length >> 1);
        int n = in.readInt();
        while (true) {
            int[] ar = Generators.generateRandomIntegerArray(n, 1000);
            partitionAtMid(ar);
            int[] copy = prepareWaveForm(ar);
            try {
                validateWaveForm(copy);
            } catch (Exception e) {
//                e.printStackTrace();
                out.print(ar);
                out.print(copy);
                out.flush();
                in.read();
            }
        }
    }

    private static void validateWaveForm(int[] ar) {
        for (int i = 1; i < ar.length; i += 2)
            if (ar[i] <= ar[i - 1]) throw new RuntimeException("something is wrong with this array");

        for (int i = 2; i < ar.length; i += 2)
            if (ar[i] >= ar[i - 1]) throw new RuntimeException("something is wrong with the array");
    }

    private static int[] prepareWaveForm(int[] ar) {
        int start = 0, end = ar.length - 1, index = 0;
        int[] copy = new int[ar.length];
        while (start < end) {
            copy[index++] = ar[start++];
            copy[index++] = ar[end--];
        }

        if (start == end) copy[index++] = ar[start];
        return copy;
    }

    private static void partitionAtMid(int[] ar) {
        int start = 0, end = ar.length - 1, mid = (start + end) >>> 1;
        int pivote = -1;
        while (pivote != mid) {
            pivote = pivoteM3(ar, start, end);
            if (pivote > mid)
                end = pivote - 1;
            else if (pivote < mid)
                start = pivote + 1;
            else
                return;
        }
    }

    private static int pivoteM3(int[] ar, int start, int end) {
        if (start == end)
            return start;

        int l = start;
        int n = end;
        int m = (l + n) >>> 1;
        int len = end + 1 - start;
        m = medianOfThree(ar, l, m, n); // Mid-size, med of 3

        //        int mid = medianOfThree(ar, start, end);
        int pivot = ar[m];
        swap(ar, end, m);
        int i = start;
        for (int j = start; j < end; j++) {
            if (ar[j] <= pivot) {
                swap(ar, i, j);
                i++;
            }
        }
        ArrayUtils.swap(ar, i, end);
        return i;
    }

    public static void swap(int[] ar, int i, int j) {
        int temp = ar[i];
        ar[i] = ar[j];
        ar[j] = temp;
    }

    private static int medianOfThree(int[] ar, int a, int b, int c) {
        return (ar[a] < ar[b] ? (ar[b] < ar[c] ? b : ar[a] < ar[c] ? c : a) :
                (ar[b] > ar[c] ? b : ar[a] > ar[c] ? c : a));
    }
}
