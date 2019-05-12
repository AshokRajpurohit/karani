/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.tesco;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 * abc ->
 * aab -> aab, aba, baa
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Tesco {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static long[] factorials;
    private static int[] charIndices = new int[256];

    static {
        iniializeFactorials(30);
        Arrays.fill(charIndices, -1);
    }

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void iniializeFactorials(int n) {
        factorials = new long[n + 1];
        factorials[1] = factorials[0] = 1;
        for (int i = 2; i <= n; i++)
            factorials[i] = factorials[i - 1] * i;
    }

    private static void solve() throws IOException {
        testPrograme();
        out.flush();
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    private static void testPrograme() throws IOException {
        test("aba");
        test("abc");
        test("a");
        test("aaaaaa");
        test("");
    }

    private static void test(String s) {
        long permutationCount = calculatePpermutations(s.toCharArray());
        Collection<String> permutations = getPermutations(s.toCharArray());
        if (permutations.size() != permutationCount)
            throw new RuntimeException("permutation count is not correct, expected: " + permutationCount + ", found: " + permutations.size() + "\n" + permutations);

        out.println(permutations.toString());
    }

    private static Collection<String> getPermutations(char[] ar) {
        Arrays.sort(ar);
        Collection<String> permutations = new LinkedList<>();
        permute(ar, 0, permutations);
        return permutations;
    }

    private static void permute(char[] chars, int index, Collection<String> permutations) {
        if (index >= chars.length - 1) {
            permutations.add(new String(chars));
            return;
        }

        List<Integer> swapCharIndices = getNextDifferentChars(chars, index);
        for (Integer next : swapCharIndices) {
            swap(chars, index, next);
            permute(chars, index + 1, permutations);
            swap(chars, index, next);
        }
    }

    private static void swap(char[] ar, int a, int b) {
        char temp = ar[a];
        ar[a] = ar[b];
        ar[b] = temp;
    }

    private static List<Integer> getNextDifferentChars(char[] ar, int index) {
//        charIndices[ar[index]] = index;
        List<Integer> nextCharIndices = new LinkedList<>();
        while (index < ar.length) {
            if (charIndices[ar[index]] == -1) {
                charIndices[ar[index]] = index;
                nextCharIndices.add(index);
            }

            index++;
        }

        unmark(nextCharIndices, ar);
        return nextCharIndices;
    }

    private static void unmark(List<Integer> indices, char[] ar) {
        indices.forEach(i -> charIndices[ar[i]] = -1);
    }

    private static long calculatePpermutations(char[] ar) {
        int[] counts = new int[256];
        for (char ch : ar) counts[ch]++;

        long denominator = 1;
        for (int i = 'a'; i <= 'z'; i++) denominator *= factorial(counts[i]);
        return factorial(ar.length) / denominator;
    }

    private static long factorial(int n) {
        return factorials[n];
    }
}
