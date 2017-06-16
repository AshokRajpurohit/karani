/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankitSoni;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Problem Name:
 * Link:
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MagnitudeSoftware {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(in.readInt());
            out.flush();
        }
    }

    final static class Problem3 {
        static Map<String, Node> nodeMap = new HashMap<>();

        private static void solve() throws IOException {
            int n = in.readInt();

            for (int i = 0; i < n; i++) {
                Node source = getNode(in.read()), target = getNode(in.read());
                source.add(target);
            }

            int q = in.readInt();
            Map<Node, LinkedList<Node>> sourceTargetMap = new HashMap<>(),
                    targetSourceMap = new HashMap<>();

            while (q > 0) {
                Node source = nodeMap.get(in.read()), target = nodeMap.get(in.read());
                out.println(calculate(source, target));
                put(sourceTargetMap, source, target);
                put(targetSourceMap, target, source);
            }
        }

        private static int calculate(Node source, Node target) {
            if (source == target)
                return 0;

            int length = 0;
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(source);

            while (queue.size() > 0) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Node node = queue.removeFirst();

                    if (node == target)
                        return length;

                    queue.addAll(node.destinations);
                }

                length++;
            }

            return -1;
        }

        private static void put(Map<Node, LinkedList<Node>> map, Node key, Node value) {
            if (!map.containsKey(key))
                map.put(key, new LinkedList<Node>());

            map.get(key).add(value);
        }

        private static Node getNode(String name) {
            if (nodeMap.containsKey(name))
                return nodeMap.get(name);

            Node node = new Node(name);
            nodeMap.put(name, node);

            return node;
        }

        final static class Node {
            final String name;
            LinkedList<Node> destinations = new LinkedList<>(),
                    sources = new LinkedList<>();

            Node(String name) {
                this.name = name;
            }

            void add(Node node) {
                destinations.add(node);
                node.sources.add(this);
            }

            public int hashCode() {
                return name.hashCode();
            }
        }
    }

    final static class Problem2 {

        private static void solve() throws IOException {
            while (true) {
                out.println(getArrayIndex(in.readInt(), in.readInt(), in.readInt()));
                out.flush();
            }
        }

        private static long getArrayIndex(int n, int row, int col) {
            if (row >= 2 * n - 1 || (row >= n && col >= n - (row + 1 - n)) || (row < n && col > row))
                return -1;

            if (row <= n)
                return getArrayIndex(row, col);

            long index = getArrayIndex(row, col) - (sumN(row - n) << 1);
            return index;
        }

        private static long sumN(int start, int end) {
            int n = end + 1 - start;
            return 1L * n * start + sumN(n - 1);
        }

        private static long validateIndex(long index, long size) {
            return index < size ? index : -1;
        }

        private static long getArrayIndex(int row, int col) {
            return sumN(row) + col;
        }

        private static long sumN(int n) {
            long val = 1L * n * (n + 1);
            return val >>> 1;
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