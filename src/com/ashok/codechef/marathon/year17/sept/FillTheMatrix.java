/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Fill The Matrix
 * Link: https://www.codechef.com/SEPT17/problems/FILLMTR
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class FillTheMatrix {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String YES = "yes", NO = "no";
    private static final long LIMIT = 1000000; // 10^6, although in problem statement it is 10^5.

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        while (t > 0) {
            t--;
            int n = in.readInt(), q = in.readInt();
            Matrix matrix = new Matrix(n, q);
            Edge[] edges = new Edge[q];

            for (int i = 0; i < q; i++)
                edges[i] = matrix.getEdge(in.readInt() - 1, in.readInt() - 1, in.readInt());

            sb.append(matrix.validateEntries(edges) ? YES : NO).append('\n');
        }

        out.print(sb);
    }

    static boolean isValidPathSum(int pathSum) {
        return (pathSum & 1) == 0;
    }

    static Edge[] toArray(List<Edge> list) {
        Edge[] ar = new Edge[list.size()];
        int index = 0;
        for (Edge edge : list)
            ar[index++] = edge;

        return ar;
    }

    static boolean validateDummyEdges(Edge[] edges) {
        for (Edge edge : edges)
            if (edge.isDummy() && edge.weight != 0)
                return false;

        return true;
    }

    static Edge[] removeDummyEdges(Edge[] edges) {
        LinkedList<Edge> list = new LinkedList<>();
        for (Edge edge : edges)
            if (!edge.isDummy())
                list.addLast(edge);

        return list.size() == edges.length ? edges : toArray(list);
    }

    /**
     * Removes duplicate and dummy edges.
     *
     * @param edges
     * @return
     */
    static Edge[] normalize(Edge[] edges) {
        List<Edge> edgeList = new LinkedList<>();
        edgeList.add(edges[0]);
        int len = edges.length;
        for (int i = 1; i < len; i++) {
            if (edges[i].equals(edges[i - 1]))
                continue;

            edgeList.add(edges[i]);
        }

        formConnectins(edgeList);
        addKeys(edgeList);

        return edgeList.size() == edges.length ? edges : toArray(edgeList);
    }

    static void addKeys(List<Edge> edges) {
        int index = 0;
        for (Edge edge : edges)
            edge.hash = index++;
    }

    static void formConnectins(List<Edge> edges) {
        for (Edge edge : edges)
            edge.formConnections();
    }

    final static class Matrix {
        final int size;
        final Node[] nodes;

        Matrix(int n, int q) {
            size = n;
            MatrixBaseArray mba = new MatrixBaseArray(size);
            nodes = mba.nodes;
        }

        public Edge getEdge(int r, int c, int v) {
            Node rn = nodes[r], cn = nodes[c];
            return r < c ? new Edge(rn, cn, v) : new Edge(cn, rn, v);
        }

        boolean validateEntries(Edge[] edges) {
            if (!validateDummyEdges(edges))
                return false;

            edges = removeDummyEdges(edges);
            Arrays.sort(edges);

            if (checkForConflicts(edges))
                return false;

            edges = normalize(edges);
            return validateConnections(edges);
        }

        boolean validateConnections(Edge[] edges) {
            boolean[] statusMap = new boolean[size],
                    edgeMap = new boolean[edges.length];
            LinkedList<Node> queue = new LinkedList<>();
            int[] rootDistance = new int[size]; // mapping for each node and it's distance from selected root.

            for (int i = 0; i < size; i++) {
                if (statusMap[i])
                    continue;

                statusMap[i] = true;
                queue.addLast(nodes[i]); // root node for the tree.
                while (!queue.isEmpty()) {
                    Node top = queue.removeFirst();
                    for (Edge edge : top.connections) {
                        if (edgeMap[edge.hash])
                            continue;

                        Node next = edge.otherNode(top);
                        edgeMap[edge.hash] = true;
                        int distance = rootDistance[top.index] + edge.weight;
                        if (statusMap[next.index]) {
                            if (isValidPathSum(distance + rootDistance[next.index]))
                                continue;

                            return false;
                        }

                        rootDistance[next.index] = distance;
                        statusMap[next.index] = true;
                        queue.addLast(next);
                    }
                }
            }

            return true;
        }

        static boolean checkForConflicts(Edge[] edges) {
            for (int i = 1; i < edges.length; i++) {
                if (edges[i].conflicts(edges[i - 1]))
                    return true;
            }

            return false;
        }
    }

    final static class MatrixBaseArray {
        final int size;
        private Node[] nodes;

        MatrixBaseArray(int size) {
            this.size = size;
            nodes = new Node[size];

            for (int i = 0; i < size; i++)
                nodes[i] = new Node(i);
        }
    }

    final static class Node implements Comparable<Node> {
        final int index;
        LinkedList<Edge> connections = new LinkedList<>();

        Node(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(Node node) {
            return index - node.index;
        }

        public int hashCode() {
            return index;
        }

        public String toString() {
            return "node: " + index + " connected to: " + connections + " nodes";
        }
    }

    final static class Edge implements Comparable<Edge> {
        final Node a, b;
        int hash, weight;

        Edge(Node a, Node b, int value) {
            this.a = a;
            this.b = b;
            weight = value;
        }

        static Edge newInstance(Node a, Node b, int value) {
            return a.index < b.index ? new Edge(a, b, value) : new Edge(b, a, value);
        }

        void formConnections() {
            a.connections.addLast(this);
            b.connections.addLast(this);
        }

        @Override
        public int compareTo(Edge edge) {
            return a == edge.a ? b.index - edge.b.index : a.index - edge.a.index;
        }

        public boolean equals(Object object) {
            if (this == object)
                return true;

//            if (!(object instanceof Edge)) // not necessary here.
//                return false;

            Edge edge = (Edge) object;
            return a == edge.a && b == edge.b && weight == edge.weight;
        }

        public int hashCode() {
            return hash;
        }

        public boolean isValid() {
            return a != b || weight == 0;
        }

        public boolean isDummy() {
            return a == b;
        }

        public String toString() {
            return a + " <-> " + b;
        }

        public Node otherNode(Node node) {
            return a == node ? b : a;
        }

        public boolean sameEdge(Edge edge) {
            return a == edge.a && b == edge.b;
        }

        boolean conflicts(Edge edge) {
            return sameEdge(edge) && weight != edge.weight;
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String readLine() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}