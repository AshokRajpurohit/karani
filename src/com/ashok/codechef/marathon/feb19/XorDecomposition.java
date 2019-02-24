/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.feb19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * Problem Name: Xor Decomposition
 * Link: https://www.codechef.com/FEB19A/problems/XDCOMP
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class XorDecomposition {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static long MOD = 1000000007;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException, ExecutionException, InterruptedException {
        int n = in.readInt(), x = in.readInt();
        Tree tree = new Tree(in.readIntArray(n), in.readEdges(n - 1));
        out.println(process(tree, x));
    }

    private static long process(Tree tree, int x) {
        if (tree.size == 1) return tree.root.value == x ? 1 : 0;
        if (tree.root.xorSum != x && tree.root.xorSum != 0) return 0;
        if (x == 0) return pow(2, Arrays.stream(tree.nodes).filter(n -> n.xorSum == 0).count() - 1);
        if (Arrays.stream(tree.nodes).filter(n -> n.xorSum == x).count() == 0) return 0;
        Triplet res = process(tree.root, x);
        return res.value == x ? res.evenCount : res.oddCount;
    }

    private static Triplet process(Node node, int x) {
        if (node.nodes.isEmpty()) {
            return new Triplet(node, node.value == x ? 0 : 1, node.value == x ? 1 : 0, node.value);
        }

        List<Triplet> triplets = node.nodes.stream().map(n -> process(n, x)).collect(Collectors.toList());
        int val = node.value;
        long even = 1, odd = 0;
        for (Triplet triplet : triplets) {
            if (triplet.node.xorSum != 0 && triplet.node.xorSum != x) {
                val = val ^ triplet.value;
                long te = (even * triplet.evenCount + odd * triplet.oddCount) % MOD;
                long to = (even * triplet.oddCount + odd * triplet.evenCount) % MOD;
                even = te;
                odd = to;
            }
        }

        for (Triplet triplet : triplets) {
            if (triplet.oddCount == 0) continue;
            if (triplet.node.xorSum == 0 || triplet.node.xorSum == x) {
                long te = even * (triplet.evenCount + triplet.oddCount) % MOD + odd * triplet.oddCount;
                long to = odd * (triplet.evenCount + triplet.oddCount) % MOD + even * triplet.oddCount;
                even = te % MOD;
                odd = to % MOD;
            }
        }

        return val == x ? new Triplet(node, odd, even, 0) : new Triplet(node, even, odd, val);
    }

    private static final ForkJoinPool pool = new ForkJoinPool();

    private static long process1(Tree tree, int x) throws ExecutionException, InterruptedException {
        if (tree.size < 100) {
            return process(tree, x);
        }

        if (tree.size == 1) return tree.root.value == x ? 1 : 0;
        if (tree.root.xorSum != x && tree.root.xorSum != 0) return 0;
        if (x == 0) return pow(2, Arrays.stream(tree.nodes).filter(n -> n.xorSum == 0).count() - 1);
        if (Arrays.stream(tree.nodes).filter(n -> n.xorSum == x).count() == 0) return 0;
        Triplet res = pool.submit(new ParallelProcessTask(tree.root, x)).get();
        return res.value == x ? res.evenCount : res.oddCount;
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @return
     */
    public static long pow(long a, long b) {
        if (b == 0)
            return 1;

        a = a % MOD;
        if (a < 0)
            a += MOD;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % MOD;
            if ((b & r) != 0) {
                res = (res * a) % MOD;
            }
        }

        if (res < 0) res += MOD;
        return res;
    }

    final static class ParallelProcessTask extends RecursiveTask<Triplet> {
        Node node;
        int x;

        ParallelProcessTask(Node node, int x) {
            this.node = node;
            this.x = x;
        }

        @Override
        protected Triplet compute() {
            if (node.nodes.isEmpty()) {
                return new Triplet(node, node.value == x ? 0 : 1, node.value == x ? 1 : 0, node.value);
            }

            List<Triplet> triplets;
            if (node.count > 100) {
                triplets = node.nodes.stream().map(n -> new ParallelProcessTask(n, x)).map(p -> p.join()).collect(Collectors.toList());
            } else
                triplets = node.nodes.stream().map(n -> process(n, x)).collect(Collectors.toList());

            int val = node.value;
            long even = 1, odd = 0;
            for (Triplet triplet : triplets) {
                if (triplet.node.xorSum != 0 && triplet.node.xorSum != x) {
                    val = val ^ triplet.value;
                    long te = (even * triplet.evenCount + odd * triplet.oddCount) % MOD;
                    long to = (even * triplet.oddCount + odd * triplet.evenCount) % MOD;
                    even = te;
                    odd = to;
                }
            }

            for (Triplet triplet : triplets) {
                if (triplet.oddCount == 0) continue;
                if (triplet.node.xorSum == 0 || triplet.node.xorSum == x) {
                    long te = even * (triplet.evenCount + triplet.oddCount) % MOD + odd * triplet.oddCount;
                    long to = odd * (triplet.evenCount + triplet.oddCount) % MOD + even * triplet.oddCount;
                    even = te % MOD;
                    odd = to % MOD;
                }
            }

            return val == x ? new Triplet(node, odd, even, 0) : new Triplet(node, even, odd, val);
        }
    }

    private static final Node INVALID_NODE = new Node(-1, -1);

    final static class Tree {
        final int size;
        final Node[] nodes;
        Node root = INVALID_NODE;
        Deque<Node> deque;

        Tree(int[] values, Edge[] edges) {
            size = values.length;
            nodes = new Node[size];
            for (int i = 0; i < size; i++) nodes[i] = new Node(i, values[i]);
            initialize(edges);
        }

        private void initialize(Edge[] edges) {
            for (Edge e : edges) {
                nodes[e.u].nodes.add(nodes[e.v]);
                nodes[e.v].nodes.add(nodes[e.u]);
            }

            populateParents();
            populateDeque();
            updateCounts();
            getRoot().updateXorSum();
        }

        private void populateParents() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(getRoot());
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                node.nodes.stream().forEach(ch -> ch.setParent(node));
                queue.addAll(node.nodes);
            }
        }

        private void populateDeque() {
            Deque<Node> stack = new LinkedList<>(), queue = new LinkedList<>();
            stack.addFirst(root);
            queue.add(root);
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                stack.addAll(node.nodes);
                queue.addAll(node.nodes);
            }

            deque = stack;
        }

        private void updateCounts() {
            Iterator<Node> iterator = deque.descendingIterator();
            while (iterator.hasNext()) {
                Node node = iterator.next();
                node.nodes.forEach(n -> node.count += n.count);
            }
        }

        private Node getRoot() {
            if (root == INVALID_NODE) root = Arrays.stream(nodes).max((a, b) -> a.nodes.size() - b.nodes.size()).get();
            return root;
        }
    }

    final static class Node {
        final int id, value;
        int xorSum = 0, count = 1; // tree size
        List<Node> nodes = new LinkedList<>();
        Node parent = INVALID_NODE;

        Node(int id, int value) {
            this.id = id;
            this.value = value;
        }

        void setParent(Node parent) {
            if (nodes.remove(parent))
                this.parent = parent;
        }

        int updateXorSum() {
            xorSum = value;
            nodes.forEach(node -> xorSum = xorSum ^ node.updateXorSum());
            return xorSum;
        }

        public String toString() {
            return id + ", " + value + ", " + xorSum + ") " + nodes;
        }
    }

    final static class Edge {
        final int u, v;

        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        int getOtherValue(int val) {
            if (val != u && val != v) throw new RuntimeException("invalid value: " + val);
            return val == u ? v : u;
        }
    }

    final static class Pair {
        final long a, b;

        Pair(long a, long b) {
            this.a = a;
            this.b = b;
        }
    }

    final static class Triplet {
        final long evenCount, oddCount;
        final int value;
        final Node node;

        Triplet(Node node, long e, long o, int v) {
            evenCount = e;
            oddCount = o;
            value = v;
            this.node = node;
        }

        public String toString() {
            return "(" + evenCount + " " + oddCount + " " + value + ")" + node;
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

        public Edge readEdge() throws IOException {
            return new Edge(readInt() - 1, readInt() - 1);
        }

        public Edge[] readEdges(int n) throws IOException {
            Edge[] edges = new Edge[n];
            for (int i = 0; i < n; i++) edges[i] = readEdge();
            return edges;
        }
    }
}