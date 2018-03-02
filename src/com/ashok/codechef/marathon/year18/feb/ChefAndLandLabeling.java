/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.feb;

import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: Chef And Land Labeling
 * Link: https://www.codechef.com/FEB18/problems/CLANDLBL
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndLandLabeling {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int INVALID_LABEL = -1, limit = 1000;
    private static int[][] commonDivisorCount, sortedCommonDivisorCounts; // normalized natural indexing
    private static int[] primes, divisors;
    private static final Bucket<Edge> bucket = new Bucket(limit, new Edge[limit][]);

    private static void populate(int n) {
        primes = generatePrimes(n);
        divisors = new int[n + 1];
        for (int i = 1; i <= n; i++)
            for (int j = i; j <= n; j += i)
                divisors[j]++;

        commonDivisorCount = new int[n][n];
        for (int i = 1; i <= n; i++)
            for (int j = i + 1; j <= n; j++) {
                int gcd = gcd(i, j);
                commonDivisorCount[i - 1][j - 1] = divisors[gcd];
                commonDivisorCount[j - 1][i - 1] = divisors[gcd];
            }

        sortedCommonDivisorCounts = new int[n][];
        for (int i = 0; i < n; i++)
            sortedCommonDivisorCounts[i] = commonDivisorCount[i].clone();

        for (int[] ar : sortedCommonDivisorCounts)
            Arrays.sort(ar);
    }

    public static void main(String[] args) throws IOException {
        play();
        solve();
        in.close();
        out.close();
    }

    private static void play() throws IOException {
        while (true) {
            int n = in.readInt();
            long time = System.currentTimeMillis();
            populate(n);
            int[] ar = Utils.getIndexArray(n);
            for (int i = 0; i < n; i++)
                ar[i]++;

            Graph graph = new Graph(n);
            ArrayUtils.randomizeArray(ar);
            for (int i = 0; i < n; i++) {
                int[] labels = new int[n];
                for (int j = 0; j < n; j++) {
                    if (i == j)
                        continue;

                    int g = gcd(ar[i], ar[j]);
                    labels[j] = divisors[g];
                }

                graph.updateEdgeLabels(i, labels);
            }

            process(graph);
            out.println(System.currentTimeMillis() - time);
//            out.print(toString(graph.nodes));
            out.flush();
        }
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            populate(n);
            Graph graph = new Graph(n);
            for (int i = 0; i < n; i++)
                graph.updateEdgeLabels(i, in.readIntArray(n));

            process(graph);
            out.print(toString(graph.nodes));
        }
    }

    private static void process(Graph graph) {
        bucket.clear();
        graph.process();
        // assign 1 and prime labels first. it's easy to identify them, the edges
        // connecting them has only two type of labels, i.e. 1 ans 0
    }

    /**
     * Euclid's Greatest Common Divisor algorithm implementation.
     * For more information refer Wikipedia and Alan Baker's Number Theory.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    /**
     * This function generates prime numbers upto given integer n and
     * returns the array of primes upto n (inclusive).
     *
     * @param n prime numbers upto integer n
     * @return
     */
    private static int[] generatePrimes(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        ar[1] = true; // let's include 1 also for this proble to reduce the extra
        // efforts to handle edge cases.
        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];
        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }

        return ret;
    }

    private static int getHashValue(int[] ar) {
        long value = 0;
        int multiplier = 1;
        for (int e : ar) {
            value += multiplier * e;
            multiplier++;
        }

        return (int) (value % limit);
    }

    private static String toString(Node[] nodes) {
        StringBuilder sb = new StringBuilder(nodes.length << 2);
        for (Node node : nodes) {
            sb.append(node.label).append('\n');
        }

        return sb.toString();
    }

    private static Integer[] toArray(Collection<Integer> c) {
        Integer[] ar = new Integer[c.size()];
        int index = 0;
        for (int e : c)
            ar[index++] = e;

        return ar;
    }

    /**
     * It's a dirty way of coding, not best practices to be followed.
     *
     * @param ar
     * @param edges
     * @return
     */
    private static int compare(int[] ar, Edge[] edges) {
        int n = ar.length;
        for (int i = 0; i < n; i++)
            if (ar[i] != edges[i].label)
                return ar[i] - edges[i].label;

        return 0;
    }

    private static int compareArrays(Object ar, Object br) {
        return compare((int[]) ar, (Edge[]) br);
    }

    private static List<Integer> findAll(Edge[] edges) {
        List<Integer> list = new LinkedList<>();
        int index = 1;
        for (int[] labels : sortedCommonDivisorCounts) {
            if (compare(labels, edges) == 0)
                list.add(index);

            index++;
        }

        return list;
    }

    final static class Graph {
        final int size;
        final Node[] nodes;
        private boolean labelled = false;
        private boolean[] availableLabels;
        private Bucket<Integer> nodeLabelsBucket;

        Graph(int size) {
            this.size = size;
            nodes = new Node[size];
            availableLabels = new boolean[size + 1];
            Arrays.fill(availableLabels, true);
            availableLabels[0] = false;
            nodeLabelsBucket = new Bucket<>(size, new Integer[size][]);
            initialize();
        }

        private void initialize() {
            for (int i = 0; i < size; i++)
                nodes[i] = new Node(i, size);

            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    Edge edge = new Edge(nodes[i], nodes[j]);
                }
            }
        }

        private void updateEdgeLabels(int nodeId, int[] edgeLabels) {
            nodes[nodeId].updateEdgeLabels(edgeLabels);
        }

        private void process() {
            int primeIndex = primes.length;
            for (Node node : nodes)
                if (node.isPrimeLabel()) { // prime numbers greater than n / 2
                    node.label = primeIndex == primes.length ? 1 : primes[primeIndex];
                    primeIndex--;
                }

            for (Node node : nodes) {
                if (!node.labelled()) {
                    process(node.id);
                    break;
//                    List<Integer> possibleLabels = findAll(bucket.putAndGet(node.id, node.edges));
                }
            }

//            Arrays.binarySearch(sortedCommonDivisorCounts, nodes[0].edges, ChefAndLandLabeling::compareArrays);
        }

        private boolean process(int nodeIndex) {
            if (nodeIndex == size)
                return true;

            Node node = nodes[nodeIndex];
            if (node.labelled()) {
                return process(nodeIndex + 1);
            }

            Integer[] possibleLabels = possibleLabels(node);
            for (int label : possibleLabels) {
                if (!availableLabels[label])
                    continue;

                node.label = label;
                availableLabels[label] = false;
                boolean res = validate(node) && process(nodeIndex + 1);
                availableLabels[label] = true;

                if (res)
                    return true;
            }

            node.label = INVALID_LABEL;
            return false;
        }

        private Integer[] possibleLabels(Node node) {
            if (nodeLabelsBucket.contains(node.id))
                return nodeLabelsBucket.get(node.id);

            List<Integer> possibleLabels = findAll(bucket.putAndGet(node.id, node.edges));
            return nodeLabelsBucket.putAndGet(node.id, toArray(possibleLabels));
        }

        private boolean validate(Node node) {
            for (Edge edge : node.edges) {
                if (edge == SELF_EDGE)
                    continue;

                Node other = edge.otherNode(node);
                if (other.labelled() && divisors[gcd(node.label, other.label)] != edge.label)
                    return false;
            }

            return true;
        }
    }

    private static final Node INVALID_NODE = new Node(-1, 0);

    final static class Node {
        final int id;
        final Edge[] edges;
        private int label = INVALID_LABEL;

        Node(int id, int size) {
            this.id = id;
            edges = new Edge[size];
            if (id == -1)
                return;

            edges[id] = SELF_EDGE;
        }

        private void updateEdgeLabels(int[] edgeLabels) {
            for (int i = 0; i < edgeLabels.length; i++)
                edges[i].label = edgeLabels[i];
        }

        public boolean labelled() {
            return label != INVALID_LABEL;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return "[" + id + ", " + label + "]";
        }

        private boolean isPrimeLabel() {
            int count0 = 0, count1 = 0;
            for (Edge edge : edges) {
                if (edge.label == 0)
                    count0++;
                else if (edge.label == 1)
                    count1++;
                else return false;
            }

            return count0 == 1 && count1 == edges.length - 1;
        }
    }

    private static final Edge SELF_EDGE = new Edge(INVALID_NODE, INVALID_NODE);
    private static int edgeSequence = 1;

    final static class Edge implements Comparable<Edge> {
        final int id = edgeSequence++;
        final Node node1, node2;
        private int label = INVALID_LABEL;

        Edge(Node node1, Node node2) {
            this.node1 = node1;
            this.node2 = node2;

            if (node1 == INVALID_NODE)
                return;

            node1.edges[node2.id] = this;
            node2.edges[node1.id] = this;
        }

        private Node otherNode(Node node) {
            return node == node1 ? node2 : node1;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return node1 + " <--> " + node2;
        }

        @Override
        public int compareTo(Edge edge) {
            return label - edge.label;
        }
    }

    final static class Bucket<T> {
        private final int capacity;
        private final T[][] map;

        Bucket(int capacity, T[][] ar) {
            this.capacity = capacity;
            map = ar;
        }

        void putIfAbsent(int key, T[] value) {
            if (contains(key))
                return;

            T[] copy = value.clone();
            Arrays.sort(copy);
            map[key] = copy;
        }

        T[] get(int key) {
            return map[key];
        }

        T[] putAndGet(int key, T[] value) {
            putIfAbsent(key, value);
            return map[key];
        }

        boolean contains(int key) {
            return map[key] != null;
        }

        void clear() {
            for (int i = 0; i < capacity; i++)
                map[i] = null;
        }
    }

    private static final Comparator<Edge[]> EDGES_COMPARATOR = new Comparator<Edge[]>() {
        @Override
        public int compare(Edge[] o1, Edge[] o2) {
            int n = o1.length;
            for (int i = 0; i < n; i++)
                if (o1[i].label != o2[i].label)
                    return o1[i].label - o2[i].label;
            return 0;
        }
    };

    private static final Comparator<int[]> ARRAY_COMPARATOR = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            int n = o1.length;
            for (int i = 0; i < n; i++)
                if (o1[i] != o2[i])
                    return o1[i] - o2[i];

            return 0;
        }
    };

    final static class CustomArray implements Comparable<CustomArray> {
        final int[] array;
        final int hashValue, length;

        CustomArray(int[] ar) {
            array = ar;
            length = ar.length;
            hashValue = getHashValue(ar);
        }

        @Override
        public int compareTo(CustomArray customArray) {
            if (length != customArray.length)
                return length - customArray.length;

            return hashValue - customArray.hashValue;
        }

        @Override
        public int hashCode() {
            return hashValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof CustomArray))
                return false;

            CustomArray customArray = (CustomArray) o;
            return equals(customArray);
        }

        public boolean equals(CustomArray customArray) {
            if (length != customArray.length || hashValue != customArray.hashValue)
                return false;

            return Arrays.equals(array, customArray.array);
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