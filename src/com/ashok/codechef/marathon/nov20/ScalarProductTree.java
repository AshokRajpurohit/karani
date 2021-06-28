/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.nov20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: Scalar Product Tree
 * Link: https://www.codechef.com/NOV20A/problems/SCALSUM
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ScalarProductTree {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final long MOD = 1L << 32;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] weights = in.readIntArray(n);
        Edge[] edges = readEdges(n - 1);

        Node[] nodes = buildTree(weights, edges);
        Query[] queries = readQueries(q);
        process(nodes, queries);
        Arrays.sort(queries, Comparator.comparingInt(a -> a.id));
        StringBuilder sb = new StringBuilder(q << 3);
        for (Query query : queries) {
            sb.append(query.result).append('\n');
        }

        out.print(sb);
    }

    private static void process(Node[] nodes, Query[] queries) {
        Comparator<Query> comparator = Comparator
                .comparingInt((Query a) -> nodes[a.u].level)
                .thenComparingInt(a -> a.u)
                .thenComparingInt(a -> a.v);
        Arrays.sort(queries, comparator);
        QueryMap map = new QueryMap(nodes.length);
        for (Query q : queries) {
            q.result = calculate(nodes[q.u], nodes[q.v], map);
            map.add(q);
        }
    }

    private static long calculate(Node u, Node v, QueryMap map) {
        long result = 0;
        while (u != v) {
            result += u.weight * v.weight % MOD;
            u = u.parent;
            v = v.parent;
            Query query = new Query(u.id, v.id);
            if (map.contains(query)) {
                return (result + map.get(query).result) % MOD;
            }
        }

        return (result + u.squareValue) % MOD;
    }

    private static Node[] buildTree(int[] weights, Edge[] edges) {
        int n = weights.length;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i, weights[i]);
        }

        List<Integer>[] connections = getConnections(edges);
        Node root = nodes[0];
        root.level = 0;
        int level = 1;
        Queue<Node> queue = new LinkedList<>();
        root.squareValue = root.weight * root.weight % MOD;
        queue.add(root);
        queue.add(INVALID);
        // BFS
        while (queue.size() > 1) {
            Node node = queue.remove();
            if (node == INVALID) {
                level++;
                queue.add(node);
                continue;
            }

            for (int id : connections[node.id]) {
                Node next = nodes[id];
                if (next.level != -1) continue;
                next.level = level;
                next.parent = node;
                next.squareValue = node.squareValue + next.weight * next.weight;
                next.squareValue %= MOD;
                queue.add(next);
            }
        }

        return nodes;
    }

    private static Edge[] readEdges(int n) throws IOException {
        Edge[] edges = new Edge[n];
        for (int i = 0; i < n; i++) {
            edges[i] = new Edge(in.readInt() - 1, in.readInt() - 1);
        }

        return edges;
    }

    private static Query[] readQueries(int n) throws IOException {
        Query[] queries = new Query[n];
        for (int i = 0; i < n; i++) {
            queries[i] = new Query(in.readInt() - 1, in.readInt() - 1);
        }

        return queries;
    }

    private static LinkedList<Integer>[] getConnections(Edge[] edges) {
        int n = edges.length + 1;
        LinkedList<Integer>[] connections = new LinkedList[n];
        for (int i = 0; i < n; i++) connections[i] = new LinkedList<>();

        for (Edge e : edges) {
            connections[e.u].add(e.v);
            connections[e.v].add(e.u);
        }

        return connections;
    }

    private static final Node INVALID = new Node(-1, -1);

    final static class Node {
        final int id;
        final long weight;
        int level = -1;
        long squareValue;
        Node parent = INVALID;

        Node(final int id, final int weight) {
            this.id = id;
            this.weight = weight;
        }
    }

    final static class Query implements Comparable<Query> {
        private static int id_gen = 0;
        final int id = id_gen++;
        final int u, v;

        long result = 0;

        Query(final int u, final int v) {
            this.u = Math.min(u, v);
            this.v = Math.max(u, v);
        }

        public int hashCode() {
            return u * 1003 + v;
        }

        public boolean equals(Object o) {
            Query query = (Query) o;
            return u == query.u && v == query.v;
        }

        @Override
        public int compareTo(Query q) {
            return u == q.u ? v - q.v : u - q.u;
        }
    }

    final static class QueryMap {
        private final int len;
        private final ArrayList<Query>[] maps;

        QueryMap(final int len) {
            this.len = len;
            maps = new ArrayList[len];
            for (int i = 0; i < len; i++) maps[i] = new ArrayList<>();
            Query[] queries = new Query[10];
        }

        public boolean contains(Query query) {
            return Collections.binarySearch(maps[query.u], query) >= 0;
        }

        public Query get(Query query) {
            int index = Collections.binarySearch(maps[query.u], query);
            return maps[query.u].get(index);
        }

        public void add(Query query) {
            maps[query.u].add(query);
        }
    }

    final static class Edge {
        final int u, v;

        Edge(final int u, final int v) {
            this.u = u;
            this.v = v;
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