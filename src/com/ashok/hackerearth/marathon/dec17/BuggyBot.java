/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.dec17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Buggy Bot
 * Link: https://www.hackerearth.com/challenge/competitive/december-circuits-17/algorithm/buggy-bot-d8f6eb53/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BuggyBot {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int START_NODE = 1;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), k = in.readInt(), m = in.readInt();
        Graph graph = new Graph(n);

        for (int i = 0; i < m; i++)
            graph.addEdge(in.readInt(), in.readInt());

        PositionMap nonBuggyMap = new PositionMap(graph);
        BuggyMap buggyMap = new BuggyMap(graph);

        nonBuggyMap.addNode(START_NODE);
        buggyMap.addNextNodes(START_NODE);

        for (int i = 0; i < k; i++) {
            int from = in.readInt(), to = in.readInt();
            boolean moved = nonBuggyMap.move(from, to);
            buggyMap.move(from, to);
            if (moved)
                buggyMap.addNextNodes(to); // bot can move to neighbouring nodes to this.
        }

        StringBuilder sb = new StringBuilder();
        sb.append(getCount(buggyMap, nonBuggyMap)).append('\n');

        for (int i = 1; i <= n; i++)
            if (buggyMap.nodeMap[i] || nonBuggyMap.nodeMap[i])
                sb.append(i).append(' ');

        out.println(sb);
    }

    private static int getCount(PositionMap a, PositionMap b) {
        int len = a.graph.nodeCount, count = 0;
        for (int i = 1; i <= len; i++)
            if (a.nodeMap[i] || b.nodeMap[i])
                count++;

        return count;
    }

    private static class PositionMap {
        final Graph graph;
        final boolean[] nodeMap;

        PositionMap(Graph graph) {
            this.graph = graph;
            nodeMap = new boolean[graph.nodeCount + 1];
        }

        void addNode(int node) {
            nodeMap[node] = true;
        }

        int count() {
            int count = 0;
            for (boolean b : nodeMap)
                if (b)
                    count++;

            return count;
        }

        /**
         * Move bot staying at {@code from} to new position {@code to}
         *
         * @param from bot's position.
         * @param to   bot's new position.
         */
        boolean move(int from, int to) {
            if (!nodeMap[from]) // ignore if bot is not present at this location.
                return false;

            nodeMap[from] = false;
            nodeMap[to] = true;
            return true;
        }
    }

    /**
     * This class holds the list of all the nodes, where bot reached
     * because of bug.
     */
    private final static class BuggyMap extends PositionMap {
        final boolean[] parentMap;

        BuggyMap(Graph graph) {
            super(graph);
            parentMap = new boolean[graph.nodeCount + 1];
        }

        /**
         * Add all the neighbouring nodes to {@code node} to this map.
         * Bot can travel to any node next to {@code node}.
         *
         * @param node
         */
        void addNextNodes(int node) {
//            if (parentMap[node])
//                return;

            parentMap[node] = true;
            LinkedList<Integer> nextNodes = graph.getNextNodes(node);
            for (int next : nextNodes)
                nodeMap[next] = true;
        }
    }

    final static class Graph {
        final int nodeCount;
        final LinkedList<Integer>[] edges, parent;

        Graph(int n) {
            nodeCount = n;
            edges = new LinkedList[n + 1];
            parent = new LinkedList[n + 1];

            for (int i = 1; i <= n; i++)
                edges[i] = new LinkedList<>();

            for (int i = 1; i <= n; i++)
                parent[i] = new LinkedList<>();
        }

        private void addEdge(int from, int to) {
            edges[from].addLast(to);
            parent[to].addLast(from);
        }

        private LinkedList<Integer> getNextNodes(int node) {
            return edges[node];
        }

        private LinkedList<Integer> getParents(int node) {
            return parent[node];
        }

        public Graph clone() {
            Graph graph = new Graph(nodeCount);

            for (int i = 1; i <= nodeCount; i++) {
                graph.edges[i].addAll(edges[i]);
                graph.parent[i].addAll(parent[i]);
            }

            return graph;
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
    }
}