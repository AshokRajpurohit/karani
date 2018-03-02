/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.harsh.niyo;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Words And Trees
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class WordsAndTrees {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), q = in.readInt();
        int[] chars = in.readIntArray(n);
        Tree tree = new Tree(chars);
        for (int i = 1; i < n; i++)
            tree.addEdge(in.readInt() - 1, in.readInt() - 1);

        tree.restructure();
        StringBuilder sb = new StringBuilder(q << 2);
        while (q > 0) {
            q--;
            sb.append(tree.query(in.readInt() - 1, in.read().toCharArray())).append('\n');
        }

        out.print(sb);
    }

    private static void merge(int[] a, int[] b) {
        for (int i = 0; i < 26; i++)
            a[i] += b[i];
    }

    private static int countAbsent(int[] ar, int[] map) {
        int count = 0;
        for (int i = 0; i < 26; i++)
            if (map[i] < ar[i])
                count += ar[i] - map[i];

        return count;
    }

    private static int[] getMap(char[] ar) {
        int[] map = new int[26];
        for (char ch : ar)
            map[ch - 'a']++;

        return map;
    }

    final static class Tree {
        final Node[] nodes;
        final int size;

        Tree(int[] ar) {
            size = ar.length;
            nodes = new Node[size];
            for (int i = 0; i < size; i++)
                nodes[i] = new Node(i, ar[i]);
        }

        private void addEdge(int n1, int n2) {
            Node node1 = nodes[n1], node2 = nodes[n2];
            node1.nodes.add(node2);
            node2.nodes.add(node1);
        }

        private int query(int nodeId, char[] ar) {
            return countAbsent(getMap(ar), nodes[nodeId].counts);
        }

        private int query(int nodeId, int[] ar) {
            return countAbsent(ar, nodes[nodeId].counts);
        }

        void restructure() {
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(nodes[0]);
            queue.add(INVALID_NODE);
            boolean[] map = new boolean[size];
            map[0] = true;

            while (queue.size() > 1) {
                Node node = queue.removeFirst();
                if (node == INVALID_NODE) {
                    queue.addLast(INVALID_NODE);
                    continue;
                }

                List<Node> childNodes = new LinkedList<>();
                for (Node n : node.nodes) {
                    if (map[n.id])
                        continue;

                    map[n.id] = true; // processed.
                    n.parent = node;
                    childNodes.add(n);
                    queue.addLast(n);
                }

                node.nodes = childNodes;
            }

            nodes[0].process();
        }
    }

    private static final Node INVALID_NODE = new Node(-1, 49);

    final static class Node {
        final int id;
        Node parent;
        List<Node> nodes = new LinkedList<>();
        int[] counts = new int[26];

        Node(int id, int ch) {
            this.id = id;
            counts[ch - 49] = 1;
        }

        public int hashCode() {
            return id;
        }

        private void process() {
            for (Node child : nodes)
                child.process();

            for (Node child : nodes)
                merge(counts, child.counts);
        }

        public String toString() {
            return "" + id;
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
    }
}