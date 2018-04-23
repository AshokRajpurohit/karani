/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.nirosha.playgames;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class PlayGames24x7 {
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
            int i = 010;
            int j = 07;
            out.println(i);
            out.println(j);
            in.readInt();
            out.println(DivideCode.divide(4, 0));
            out.flush();
        }
    }

    final static class FizzBuzz {
        private static final String FIZZ_BUZZ = "FizzBuzz", FIZZ = "Fizz", BUZZ = "Buzz";

        static void fizzBuzz(int n) {
            StringBuilder sb = new StringBuilder(n);
            String[] nums = new String[n + 1];
            for (int i = 1; i <= n; i++)
                nums[i] = Integer.toString(i);

            for (int i = 3; i <= n; i += 3)
                nums[i] = FIZZ;

            for (int i = 5; i <= n; i += 5)
                nums[i] = BUZZ;

            for (int i = 15; i <= n; i += 15)
                nums[i] = FIZZ_BUZZ;

            for (int i = 1; i <= n; i++)
                sb.append(nums[i]).append('\n');

            System.out.println(sb);
        }
    }

    final static class CountDuplicates {
        static int countDuplicates(int[] numbers) {
            Arrays.sort(numbers);
            boolean prevEqual = false, curEqual = false;
            int count = 0, n = numbers.length;
            for (int i = 1; i < n; i++) {
                curEqual = numbers[i] == numbers[i - 1];
                if (curEqual && !prevEqual)
                    count++;

                prevEqual = curEqual;
            }

            return count;
        }
    }

    final static class MergeSort {
        static int[] mergeArrays(int[] a, int[] b) {
            int ai = 0, bi = 0, ri = 0;
            int alen = a.length, blen = b.length, len = alen + blen;
            int[] result = new int[len];
            while (ai < alen && bi < blen) {
                if (a[ai] < b[bi])
                    result[ri++] = a[ai++];
                else
                    result[ri++] = b[bi++];
            }

            while (ai < alen) {
                result[ri++] = a[ai++];
            }

            while (bi < blen) {
                result[ri++] = b[bi++];
            }

            return result;
        }
    }

    final static class MaximumDifferenceInArray {
        static int maxDifference(int[] ar) {
            int[] maxArray = getMaxArrayFromRight(ar);
            int maxDiff = -1, len = ar.length;
            for (int i = 0; i < len - 2; i++)
                maxDiff = Math.max(maxDiff, maxArray[i + 1] - ar[i]);

            return maxDiff == 0 ? -1 : maxDiff;
        }

        private static int[] getMaxArrayFromRight(int[] ar) {
            int len = ar.length;
            int[] maxArray = new int[len];
            maxArray[len - 1] = ar[len - 1];
            int max = ar[len - 1];
            for (int i = len - 2; i >= 0; i--) {
                max = Math.max(max, ar[i]);
                maxArray[i] = max;
            }

            return maxArray;
        }
    }

    final static class DivideCode {
        private static int divide(int a, int b) {
            int c = -1;

            try {
                c = a / b;
            } catch (Exception e) {
                System.err.println("Exception ");
            } finally {
                System.err.println("Finally ");
            }

            return c;
        }
    }

    static abstract class Animal {
        protected boolean isMammel;
        protected boolean isCarnivorous;

        Animal(boolean isMammel, boolean isCarnivorous) {
            this.isMammel = isMammel;
            this.isCarnivorous = isCarnivorous;
        }

        abstract public String getGreeting();
    }

    final static class Dog extends Animal {
        Dog() {
            super(true, true);
        }

        @Override
        public String getGreeting() {
            return "ruff";
        }
    }

    final static class Cow extends Animal {

        Cow() {
            super(true, false);
        }

        @Override
        public String getGreeting() {
            return "moo";
        }
    }

    final static class Duck extends Animal {
        Duck() {
            super(false, false);
        }

        @Override
        public String getGreeting() {
            return "quack";
        }
    }

}
