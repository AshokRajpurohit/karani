/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Primitive Queries
 * Link: https://www.codechef.com/FEB17/problems/DISTNUM3
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PrimitiveQueries {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final Node PARAM_ROOT = new Node(-1, -1); // not reachable node through physical world. Can be reached only when you get Moksha.
    private static Node[] nodes;
    private static Edge[] edges;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] ar = in.readIntArray(n);
        edges = new Edge[n - 1];

        for (int i = 0; i < edges.length; i++)
            edges[i] = new Edge(in.readInt() - 1, in.readInt() - 1);

        populateNodes(ar);

        StringBuilder sb = new StringBuilder(q << 2);
        int searchQuery = 1;

        while (q > 0) {
            q--;

            if (in.readInt() == searchQuery)
                sb.append(findDistinctNumbers(in.readInt() - 1, in.readInt() - 1)).append('\n');
            else
                update(in.readInt() - 1, in.readInt());
        }

        out.print(sb);
    }

    private static void update(int nodeIndex, int value) {
        nodes[nodeIndex].value = value;
    }

    private static int findDistinctNumbers(int u, int v) {
        if (u == v)
            return 1;

        LinkedList<Integer> numbers = getNumbers(u, v);
        return countUniqueNumbers(numbers);
    }

    private static int countUniqueNumbers(LinkedList<Integer> list) {
        if (list.size() == 1)
            return 1;

        if (list.size() == 2)
            return list.removeFirst() == list.removeLast() ? 1 : 2;

        int[] ar = new int[list.size()];
        int index = 0;

        for (int e : list)
            ar[index++] = e;

        Arrays.sort(ar);
        int count = 1;

        for (int i = 1; i < ar.length; i++)
            if (ar[i] != ar[i - 1])
                count++;

        return count;
    }

    private static int bruteForceCount(LinkedList<Integer> list) {
        int count = 1;

        while (list.size() > 1) {
            int num = list.removeLast();

            if (list.contains(num))
                continue;

            count++;
        }

        return count;
    }

    private static LinkedList<Integer> getNumbers(int nodeIndex1, int nodeIndex2) {
        Node node1 = nodes[nodeIndex1], node2 = nodes[nodeIndex2];

        if (node1.level < node2.level)
            return getNumbers(node1, node2);

        return getNumbers(node2, node1);
    }

    private static LinkedList<Integer> getNumbers(Node higher, Node lower) {
        LinkedList<Integer> list = new LinkedList<>();

        while (lower.level != higher.level) {
            list.addLast(lower.value);
            lower = lower.parent; // climb up baby.
        }

        while (higher != lower) { // keep going untill you find your common ancestor.
            list.add(lower.value);

            if (higher.value != lower.value) // let's discard duplicates at this stage.
                list.addLast(higher.value);

            lower = lower.parent;
            higher = higher.parent;
        }

        list.addLast(higher.value); // we should not miss the common ancestor.
        return list;
    }

    /**
     * Creates nodes and sets their parant nodes.
     *
     * @param ar
     */
    private static void populateNodes(int[] ar) {
        nodes = new Node[ar.length];
        for (int i = 0; i < ar.length; i++)
            nodes[i] = new Node(i, ar[i]);

        LinkedList<Integer>[] matrix = getAdjacentList();
        nodes[0].addParent(PARAM_ROOT);

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(0);
        boolean[] check = new boolean[nodes.length];

        // BFS Traversal.
        while (!queue.isEmpty()) {
            int counter = queue.size();

            while (counter > 0) {
                counter--;
                int nodeIndex = queue.removeFirst();

                check[nodeIndex] = true;
                for (int nextNodeIndex : matrix[nodeIndex]) {
                    if (!check[nextNodeIndex]) {
                        queue.addLast(nextNodeIndex);
                        nodes[nextNodeIndex].addParent(nodes[nodeIndex]);
                    }
                }
            }
        }
    }

    private static LinkedList<Integer>[] getAdjacentList() {
        LinkedList<Integer>[] matrix = new LinkedList[nodes.length];
        for (int i = 0; i < matrix.length; i++)
            matrix[i] = new LinkedList<>();

        for (Edge edge : edges) {
            matrix[edge.from].addLast(edge.to);
            matrix[edge.to].addLast(edge.from);
        }

        return matrix;
    }

    final static class Node {
        int id, value, level = -1;
        Node parent;

        Node(int id, int value) {
            this.id = id;
            this.value = value;
        }

        void addParent(Node parentNode) {
            parent = parentNode;
            level = parent.level + 1;
        }

        public String toString() {
            return id + ", " + value + ", " + level + ", " + parent.id;
        }
    }

    final static class Edge {
        int from, to;

        Edge(int f, int t) {
            from = f;
            to = t;
            return;
        }

        public boolean equals(Edge edge) {
            return (from == edge.from && to == edge.to) ||
                    (to == edge.from && from == edge.to);
        }

        public String toString() {
            return from + ", " + to;
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
