/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.april19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * Problem Name: Subtree Removal
 * Link: https://www.codechef.com/APRIL19A/problems/SUBREM
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class SubtreeRemoval {
    private static PrintWriter out = new PrintWriter(System.out);
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
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt(), cost = in.readInt();
            int[] ar = in.readIntArray(n);
            List<Pair> pairs = new LinkedList<>();
            for (int i = 0; i < n - 1; i++)
                pairs.add(new Pair(in.readInt() - 1, in.readInt() - 1));

            Tree tree = new Tree(ar, pairs);
            out.println(tree.calculateMaxProfit(cost));
        }
    }

    final static class Tree {
        public final int size;
        private TreeNode[] nodes;

        Tree(int[] values, List<Pair> connections) {
            size = values.length;
            nodes = new TreeNode[size];
            IntStream.range(0, size).forEach(i -> nodes[i] = new TreeNode(values[i]));
            setConnections(connections);
            initialize();
            populateValues();
        }

        public long calculateMaxProfit(final int operationCost) {
            TreeNode root = nodes[0];
            return calculateMaxProfit(root, operationCost);
        }

        private static long calculateMaxProfit(TreeNode node, final int cost) {
            long profit = node.treeNodes.stream().mapToLong(n -> calculateMaxProfit(n, cost)).sum();
            return Math.max(profit + node.value, -cost);
        }

        private void setConnections(List<Pair> connections) {
            connections.forEach(pair -> {
                nodes[pair.a].addChild(nodes[pair.b]);
                nodes[pair.b].addChild(nodes[pair.a]);
            });
        }

        private void initialize() {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(nodes[0]);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                node.treeNodes.forEach(n -> {
                    n.treeNodes.remove(node);
                    queue.add(n);
                });
            }
        }

        private void populateValues() {
            TreeNode node = nodes[0];
            node.refreshValues();
        }
    }

    final static class TreeNode {
        final List<TreeNode> treeNodes = new LinkedList<>();
        final int value;
        private long initialSum = 0, sum = 0;

        TreeNode(int value) {
            this.value = value;
            initialSum = value;
            sum = value;
        }

        void addChild(TreeNode node) {
            treeNodes.add(node);
        }

        void refreshValues() {
            treeNodes.forEach(node -> {
                node.refreshValues();
                initialSum += node.initialSum;
                sum += node.sum;
            });
        }
    }

    final static class Pair {
        final int a, b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
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
