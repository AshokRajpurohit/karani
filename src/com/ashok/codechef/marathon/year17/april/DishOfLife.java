/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.april;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Dish of Life
 * Link: https://www.codechef.com/APRIL17/problems/DISHLIFE
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class DishOfLife {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String SAD = "sad", ALL = "all", SOME = "some";

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;
            int islands = in.readInt(), ingredients = in.readInt();
            int[][] islandIngredients = new int[islands][];

            for (int i = 0; i < islands; i++) {
                int ingredientsInIsland = in.readInt();
                int[] ar = in.readIntArray(ingredientsInIsland);
                islandIngredients[i] = ar;
                normalize(ar);
            }

            out.println(process(islandIngredients, islands, ingredients));
        }
    }

    /**
     * Converts 1 based indices into 0 based indices.
     *
     * @param ar array containing 1 based indices.
     */
    private static void normalize(int[] ar) {
        for (int i = 0; i < ar.length; i++)
            ar[i]--;
    }

    private static String process(int[][] map, int n, int k) {
        BucketChecker bc = new BucketChecker(k);
        for (int[] ar : map)
            bc.addElements(ar);

        if (!bc.containsAllElements()) // even collecting from all elements, Chef could not collect
            return SAD;                 // all ingredients. So sad na!

        for (int[] ar : map) {
            bc.removeElements(ar); // let's not collect ingredients from this island.

            if (bc.containsAllElements()) // oh Chef has all the ingredients, he can skip some.
                return SOME;

            bc.addElements(ar);
        }
        return ALL;
    }

    final static class BucketChecker {
        private final int[] counts;
        final int capacity;
        private int elementCount = 0;

        BucketChecker(int capacity) {
            this.capacity = capacity;
            counts = new int[capacity];
        }

        void addElements(int[] ar) {
            for (int e : ar)
                addElement(e);
        }

        void removeElements(int[] ar) {
            for (int e : ar)
                removeElement(e);
        }

        private void addElement(int e) {
            counts[e]++;
            if (counts[e] == 1)
                elementCount++;
        }

        private void removeElement(int e) {
            counts[e]--;
            if (counts[e] == 0)
                elementCount--;
        }

        boolean containsAllElements() {
            return elementCount == capacity;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
