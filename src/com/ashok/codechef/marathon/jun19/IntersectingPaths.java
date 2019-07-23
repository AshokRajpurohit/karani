/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.jun19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * Problem Name: Intersecting Paths
 * Link: https://www.codechef.com/JUNE19A/problems/INTRPATH
 * Incomplete/Incorrect Solution
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class IntersectingPaths {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException, ExecutionException, InterruptedException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt(), q = in.readInt();
            Edge[] edges = new Edge[n];
            for (int i = 0; i < n; i++) edges[i] = new Edge(in.readInt(), in.readInt());
            Future<Graph> future = ForkJoinPool.commonPool().submit(() -> new Graph(edges));
            Query[] queries = new Query[q];
            long[] result = new long[q];
            for (int i = 0; i < q; i++) queries[i] = new Query(i, in.readInt() - 1, in.readInt() - 1);
            Graph graph = future.get();
            Arrays.stream(queries).forEach(query -> result[query.id] = graph.query(query.a, query.b));
            out.println(toString(result));
        }
    }

    private static String toString(long[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (long e : ar) sb.append(e).append(' ');
        return sb.toString();
    }

    final static class Graph {
        final Edge[] edges;
        private Node root;
        final Node[] nodes;
        final int size;

        Graph(Edge[] edges) {
            this.edges = edges;
            size = edges.length + 1;
            nodes = IntStream.range(0, size).parallel().mapToObj(i -> new Node(i)).toArray(t -> new Node[t]);
            Arrays.stream(edges).forEach(edge -> {
                Node n1 = nodes[edge.a], n2 = nodes[edge.b];
                n1.addChild(n2);
                n2.addChild(n1);
                preprocess();
            });
        }

        long query(int u, int v) {
            Node a = nodes[u], b = nodes[v];
            return a.level <= b.level ? query(a, b) : query(b, a);
        }

        private static long query(Node higher, Node lower) {
            Node parent = findParent(higher, lower);
            return -1;
        }

        private void preprocess() {
            root = findRoot();
            formTree();
        }

        private void formTree() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node node = queue.remove();
                node.updateChilds();
                queue.addAll(node.childNodes);
            }
        }

        private static Node findParent(Node u, Node v) {
            return INVALID_NODE;
        }

        private Node findRoot() {
            return Arrays.stream(nodes).max((a, b) -> a.childNodes.size() - b.childNodes.size()).get();
        }
    }

    private static final Node INVALID_NODE = new Node(-1);

    final static class Node {
        final int id;
        Node parent = INVALID_NODE;
        List<Node> childNodes = new ArrayList<>();
        int totalChildCounts = 0, perfectIntersectionCounts = 0, level = 0;

        Node(int id) {
            this.id = id;
        }

        synchronized void addChild(Node node) {
            childNodes.add(node);
        }

        synchronized void setParent(Node parent) {
            this.parent = parent;
            level = parent.level + 1;
            childNodes.remove(parent);
        }

        public void updateChilds() {
            childNodes.forEach(child -> child.setParent(this));
        }

        public String toString() {
            if (this == INVALID_NODE) return "INVALID_NODE";
            return "id: " + id + ", child-count: " + childNodes.size();
        }
    }

    final static class Query {
        final int id, a, b;

        Query(int id, int a, int b) {
            this.id = id;
            this.a = a;
            this.b = b;
        }
    }

    final static class Edge {
        final int a, b;

        Edge(int a, int b) {
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