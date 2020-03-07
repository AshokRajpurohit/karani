/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.groupon;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem Name: Groupon SDE Hiring Test
 * Link: Email
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SDEFeb2020 {
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
        while (true) {
            int[] ar = in.readIntArray(in.readInt());
            List<Integer> list = Arrays.stream(ar).mapToObj(n -> n).collect(Collectors.toList());
            int[] br = in.readIntArray(in.readInt());
            List<Integer> list1 = Arrays.stream(br).mapToObj(n -> n).collect(Collectors.toList());
            out.println(MinimumMoves.minimumMoves(list, list1));
            out.flush();
        }
    }

    final static class MinimumMoves {
        public static int minimumMoves(List<Integer> arr1, List<Integer> arr2) {
            Iterator<Integer> iter1 = arr1.iterator(), iter2 = arr2.iterator();
            int moves = 0;
            while (iter1.hasNext()) {
                moves += moves(iter1.next(), iter2.next());
            }

            return moves;
        }

        private static int moves(int a, int b) {
            int moves = 0;
            while (a > 9) {
                int da = a % 10, db = b % 10;
                moves += Math.abs(da - db);
                a /= 10;
                b /= 10;
            }

            return moves + Math.abs(a - b);
        }
    }

    final static class MathHomework {
        public static int minNum(int threshold, List<Integer> points) {
            // Write your code here
            int[] scores = points.stream().mapToInt(n -> n).toArray();
            int min = Arrays.stream(scores).min().getAsInt(), max = Arrays.stream(scores).max().getAsInt();
            int minProblems = scores.length;
            if (max - min < threshold) return minProblems;

            for (int i = 0; i < scores.length; i++) {
                for (int j = i + 1; j < scores.length; j++) {
                    if (Math.abs(scores[i] - scores[j]) >= threshold) {
                        minProblems = Math.min(minProblems, calculate(scores, i, j));
                    }
                }
            }

            return minProblems;
        }

        private static int calculate(int[] ar, int i, int j) {
            int count = 1 + ((i + 1) >>> 1);
            return count + ((j - i + 1) >>> 1);
        }
    }
}
