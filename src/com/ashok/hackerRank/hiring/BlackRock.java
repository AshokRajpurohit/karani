/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BlackRock {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        out.println(findMax(n, in.readLine()));
    }

    /**
     * This was the most interesting problem. I wrote array-iterator for general purpose only
     * because of this problem, Thanks to you.
     */

    /**
     * Why the same set of questions for San Francisco and New York location?
     * Any way I am going to copy paste my old solution which I wrote for SF.
     *
     * @param n
     * @param tree
     * @return
     */
    static long findMax(int n, String tree) {
        if (n == 0)
            return 0;

        Node root = buildTree(tree);
        process(root);

        return Math.max(root.maxValue, root.maxChildValue);
    }

    private static void process(Node node) {
        if (node == INVALID)
            return;

        process(node.left);
        process(node.right);

        node.maxChildValue = getMaxValue(node.left) + getMaxValue(node.right);
        node.maxValue = node.value + getMaxChildValues(node.left) + getMaxChildValues(node.right);
    }

    private static long getMaxValue(Node node) {
        if (node == INVALID) return 0;
        return Math.max(node.maxValue, node.maxChildValue);
    }

    private static long getMaxChildValues(Node node) {
        if (node == INVALID) return 0;
        return getMaxValue(node.left) + getMaxValue(node.right);
    }

    final static int ESCAPE_CHAR = '#';

    final static Node INVALID = new Node(-1);

    final static class Node {
        Node left = INVALID, right = INVALID;
        final long value;
        long maxValue = 0, maxChildValue = 0;

        Node(long v) {
            value = v;
        }

        public String toString() {
            return "" + value;
        }

    }

    private static Node getNode(String value) {
        if (value.charAt(0) == ESCAPE_CHAR)
            return INVALID;

        return new Node(Long.valueOf(value));
    }

    private static Node buildTree(String tree) {
        String[] values = tree.split(" ");
        ArrayIterator<String> iterator = new ArrayIterator<>(values);
        LinkedList<Node> queue = new LinkedList<>();
        Node root = new Node(Long.valueOf(iterator.next()));
        queue.addLast(root);
        queue.addLast(INVALID);

        while (iterator.hasNext()) {
            Node node = queue.removeFirst();
            while (node != INVALID && iterator.hasNext()) {
                String leftVal = iterator.next();
                String rightVal = iterator.hasNext() ? iterator.next() : "#";
                node.left = getNode(leftVal);
                node.right = getNode(rightVal);

                if (node.left != INVALID)
                    queue.addLast(node.left);

                if (node.right != INVALID)
                    queue.addLast(node.right);

                node = queue.removeFirst();
            }

            queue.addLast(INVALID);
        }

        return root;
    }

    /**
     * @author Ashok Rajpurohit (ashok1113@gmail.com).
     */
    final static class ArrayIterator<T> implements Iterator<T> {
        private T[] ar;
        private int index = 0;
        final int length;

        ArrayIterator(T[] ar) {
            this.ar = ar;
            length = ar.length;
        }

        @Override
        public boolean hasNext() {
            return index < length;
        }

        @Override
        public T next() {
            return ar[index++];
        }
    }

    /**
     * Why the same set of questions for San Francisco and New York location?
     * Any way I am going to copy paste my old solution which I wrote for SF.
     *
     * @param quotes
     * @return
     */
    static int[] arbitrage(String[] quotes) {
        int len = quotes.length;
        int[] res = new int[len];
        int index = 0, dollors = 100000;

        for (String quote : quotes) {
            String[] conversions = quote.split(" ");
            Double usdToEuro = Double.valueOf(conversions[0]),
                    euroToGBP = Double.valueOf(conversions[1]),
                    gbpToUsd = Double.valueOf(conversions[2]);

            double value = dollors / usdToEuro;
            value /= euroToGBP;
            value /= gbpToUsd;

            int diff = (int) (value - dollors);
            res[index++] = Math.max(0, diff);
        }

        return res;
    }

    /**
     * Why the same set of questions for San Francisco and New York location?
     * Any way I am going to copy paste my old solution which I wrote for SF.
     *
     * @param votes
     * @return
     */
    static String electionWinner(String[] votes) {
        Map<String, Pair> map = new HashMap<>();
        for (String vote : votes) {
            addToMap(map, vote);
        }

        Pair winner = Collections.max(map.values());
        return winner.name;
    }

    private static void addToMap(Map<String, Pair> map, String key) {
        if (map.containsKey(key)) {
            map.get(key).counter.increment();
        } else {
            map.put(key, new Pair(key));
        }
    }

    final static class Counter implements Comparable<Counter> {
        int count = 1;

        void increment() {
            count++;
        }

        @Override
        public int compareTo(Counter counter) {
            return count - counter.count;
        }
    }

    final static class Pair implements Comparable<Pair> {
        String name;
        Counter counter;

        Pair(String name, Counter counter) {
            this.name = name;
            this.counter = counter;
        }

        Pair(String name) {
            this.name = name;
            counter = new Counter();
        }

        @Override
        public int compareTo(Pair pair) {
            int val = counter.compareTo(pair.counter);
            return val != 0 ? val : name.compareTo(pair.name);
        }

        public String toString() {
            return name + " " + counter.count;
        }
    }
}