/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.april19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name: Playing with Numbers
 * Link: https://www.codechef.com/APRIL19A/problems/SJ1#
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PlayingWithNumbers {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        TreeSet<Integer> set = new TreeSet<>();
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            List<Pair> pairs = new LinkedList<>();
            for (int i = 0; i < n - 1; i++)
                pairs.add(new Pair(in.readInt() - 1, in.readInt() - 1));

            long[] values = in.readLongArray(n);
            long[] modulos = in.readLongArray(n);
            Tree tree = new Tree(values, pairs);
            StringBuilder sb = new StringBuilder();
            long[] ar = tree.calculate(modulos);
            for (long v : ar) {
                sb.append(v).append(' ');
            }
            out.println(sb);
        }
    }

    private static long gcd(long a, long b) {
        return a == 0 ? b : a == 1 ? 1 : gcd(b % a, a);
    }

    final static class Tree {
        public final int size;
        private TreeNode[] nodes;

        Tree(long[] values, List<Pair> connections) {
            TreeNode.sequence = 0;
            size = values.length;
            nodes = new TreeNode[size];
            IntStream.range(0, size).forEach(i -> nodes[i] = new TreeNode(values[i]));
            setConnections(connections);
            initialize();
        }

        private List<TreeNode> getLeafNodes() {
            return Arrays.stream(nodes)
                    .filter(node -> node.treeNodes.isEmpty())
                    .collect(Collectors.toList());
        }

        private long[] calculate(long[] modulos) {
            List<TreeNode> leaftNodes = getLeafNodes();
            return leaftNodes.stream().mapToLong(node -> calculate(node.gcd, modulos[node.id])).toArray();
        }

        private static long calculate(long v, long mod) {
            return mod - gcd(v, mod);
        }

        private void setConnections(List<Pair> connections) {
            connections.forEach(pair -> {
                nodes[pair.a].addChild(nodes[pair.b]);
                nodes[pair.b].addChild(nodes[pair.a]);
            });
        }

        private void initialize() {
            Queue<TreeNode> queue = new LinkedList<>();
            TreeNode root = nodes[0];
            root.gcd = root.value;
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                node.treeNodes.forEach(n -> {
                    n.treeNodes.remove(node);
                    n.gcd = gcd(node.gcd, n.value);
                });
                queue.addAll(node.treeNodes);
            }
        }
    }

    final static class TreeNode {
        private static int sequence = 0;
        final int id = sequence++;
        final List<TreeNode> treeNodes = new LinkedList<>();
        final long value;
        long gcd = 0;

        TreeNode(long value) {
            this.value = value;
        }

        void addChild(TreeNode node) {
            treeNodes.add(node);
        }

        void refreshValue(final long gcd) {
            this.gcd = gcd(gcd, value);
            treeNodes.forEach(node -> node.refreshValue(this.gcd));
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }
}