/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.priyanka;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class OnlineTest {
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
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            List<Integer> arr = Arrays.stream(ar).mapToObj(e -> e).collect(Collectors.toList());
            List<Integer> brr = null;
            out.println(CountSteps.howManySteps(arr));
            out.flush();
        }
    }

    public static long howManySteps1(List<Integer> arr) {
        int[] ar = arr.stream().mapToInt(e -> e).toArray();
        long swaps = 0;
        for (int i = 1; i < ar.length; i++) {
            for (int j = 0; j < i; j++) {
                if (ar[i] < ar[j]) swaps++;
            }
        }

        return swaps;
    }

    public static long howManySwaps(List<Integer> arr) {
        RankTree rankTree = new RankTree();
        long swaps = 0;
        for (int num : arr) {
            swaps += rankTree.size - rankTree.getIndex(num);
            rankTree.add(num);
        }

        return swaps;
    }

    final static class SolvingQueries {
        public static List<Integer> solvingQueries(List<Integer> nums, List<Integer> prefixLenghts) {
            int size = nums.size(), maxQuery = prefixLenghts.stream().max(Integer::compareTo).get();
            Set<Integer> prefLens = new HashSet<>(prefixLenghts);
            int[] prefLenAr = prefLens.stream().mapToInt(e -> e).sorted().toArray();
            int[] numArray = nums.stream().mapToInt(e -> e).toArray();
            int[] results = new int[nums.size()];
            int[] cycleLengths = new int[nums.size() + 1];
            cycleLengths[1] = size;
            cycleLengths[size] = size;

            for (int i = 2; i <= maxQuery; i++) {
                int len = i;
                boolean cont = true;
                outer:
                while (cont) {
                    for (int j = 0; j < i; j++, len++) {
                        if (numArray[len] != numArray[j]) break outer;
                    }
                }

                cycleLengths[i] = len;
            }

            prefLens.stream().forEach(e -> {
                List<Integer> list = new LinkedList<>();
                for (int i = 1; i <= e; i++) {
                    if (cycleLengths[i] >= e) {
                        list.add(i);
                    }
                }

                results[e] = list.get(((1 + list.size()) / 2) - 1);
            });

            return prefixLenghts.stream().map(e -> results[e]).collect(Collectors.toList());
        }
    }

    final static class CountSteps {
        public static long howManySteps(List<Integer> arr) {
            RankTree rankTree = new RankTree();
            long swaps = 0;
            for (int num : arr) {
                swaps += rankTree.size - rankTree.getIndex(num);
                rankTree.add(num);
            }

            return swaps;
        }
    }

    /**
     * count in Node is the number of smaller elements than this current element.
     */
    final static class Node {
        Node left, right;
        int data, count = 0;

        Node(int i) {
            data = i;
        }
    }

    /**
     * Rank Tree class is to support index related queries in an array.
     * for each element we want to know how many elements are smaller than this
     * or greater than this. I am using AVL tree to height balance it and for
     * optimum performance.
     *
     * @author Ashok Rajpurohit (ashok1113@gmail.com)
     */

    final static class RankTree {
        Node root;
        int size = 0;

        private static Node add(Node root, int n) {
            if (root == null) {
                return new Node(n);
            }
            if (root.data < n) {
                root.right = add(root.right, n);
                return root;
            }

            root.left = add(root.left, n);
            root.count++;
            return root;
        }

        public void add(int n) {
            size++;
            if (root == null) {
                root = new Node(n);
                return;
            }
            root = add(root, n);
        }

        /**
         * This function returns how many elements smaller than or equal to
         * the parameter n are present(inserted previously) in the data structure.
         * If you want to know how many elements greater than n are present,
         * use size - getIndex(n)
         *
         * @param n
         * @return
         */
        public int getIndex(int n) {
            Node temp = root;
            int res = 0;
            while (temp != null) {
                if (n == temp.data)
                    return res + temp.count;
                if (n > temp.data) {
                    res += temp.count + 1;
                    temp = temp.right;
                } else {
                    temp = temp.left;
                }
            }

            return res;
        }

        public void add(int[] ar) {
            for (int e : ar)
                add(e);
        }
    }

}
