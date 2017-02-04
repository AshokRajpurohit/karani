/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.aman;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: GE Healthcare DGS
 * Link: Private Link
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class GEHealthCare {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        GEHealthCare a = new GEHealthCare();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    private static String rearrangeWord(String word) {
        String noAnswer = "no answer";
        char[] ar = word.toCharArray();

        if (impossible(ar))
            return noAnswer;

        int maxIndex = ar.length - 1;
        int index;

        for (index = ar.length - 2; index >= 0; index--) {
            if (ar[index] < ar[maxIndex])
                break;
            else
                maxIndex = index;
        }

        int minLargerIndex = maxIndex;
        for (int i = index + 1; i < ar.length; i++) {
            if (ar[i] > ar[index] && ar[i] < ar[minLargerIndex])
                minLargerIndex = i;
        }

        swap(ar, index, minLargerIndex);
        Arrays.sort(ar, index + 1, ar.length);

        return String.valueOf(ar);
    }

    private static void swap(char[] ar, int i, int j) {
        char ch = ar[i];
        ar[i] = ar[j];
        ar[j] = ch;
    }

    private static boolean impossible(char[] ar) {
        for (int i = 1; i < ar.length; i++)
            if (ar[i] > ar[i - 1])
                return false;

        return true;
    }

    class Adder implements Arithmetic {
        public int add(int a, int b) {
            return a + b;
        }
    }

    interface Arithmetic {
        public int add(int a, int b);
    }
}
