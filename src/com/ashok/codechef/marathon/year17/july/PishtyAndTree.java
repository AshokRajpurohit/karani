/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Pishty and tree
 * Link: https://www.codechef.com/JULY17/problems/PSHTTR
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PishtyAndTree {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;

            int n = in.readInt();
            Tree tree = new Tree(n);

            while (n > 1) {
                n--;
                tree.addEdge(in.readInt() - 1, in.readInt() - 1, in.readInt());
            }

            int q = in.readInt();
            Query[] queries = new Query[q];
            for (int i = 0; i < q; i++)
                queries[i] = new Query(i, in.readInt() - 1, in.readInt() - 1, in.readInt());

            tree.processQueries(queries);
            StringBuilder sb = new StringBuilder(q << 3);
            for (Query query : queries)
                sb.append(query.value).append('\n');

            out.print(sb);
        }
    }

    private static Query[] deepClone(Query[] queries) {
        Cloneable[] res = queries.clone();
        for (int i = 0; i < queries.length; i++)
            res[i] = queries[i].clone();

        return queries;
    }

    final static class Tree {
        final int size;
        final NodeCollection nodeCollection;
        final Edge[] edges;
        private int edgeCount = 0;
        boolean normalized;
        Node root;
        private TreeSearch treeSearch;

        Tree(int size) {
            this.size = size;
            nodeCollection = new NodeCollection(size);
            edges = new Edge[size - 1];
        }

        void addEdge(int s, int d, int c) {
            Node source = nodeCollection.getNode(s), target = nodeCollection.getNode(d);
            edges[edgeCount++] = new Edge(source, target, c);
        }

        /**
         * Set the node with maximum edges as root node and arrange all edges emanating from root node.
         */
        void normalize() {
            if (normalized)
                return;

            root = nodeCollection.nodeWithMaxEdges();
            LinkedList<Node> queue = new LinkedList<>();
            queue.addLast(root);
            queue.addLast(INVALID_NODE);
            boolean[] map = new boolean[size]; // map to check whether node is processed for tree normalization.
            map[root.id] = true;
            int level = 0;

            while (queue.size() > 1) {
                Node top = queue.removeFirst();
                level++;
                while (top != INVALID_NODE) {
                    LinkedList<Edge> edgeList = new LinkedList<>();
                    for (Edge edge : top.destinations) {
                        Node next = edge.otherNode(top);
                        if (map[next.id])
                            continue;

                        next.level = level;
                        edge.normalize(top);
                        queue.addLast(next);
                        edgeList.addLast(edge);
                        map[next.id] = true;
                    }

                    top.destinations = edgeList;
                    top = queue.removeFirst();
                }

                queue.addLast(INVALID_NODE);
            }

            treeSearch = new TreeSearch(this, level);
            normalized = true;
        }

        void processQueries(Query[] queries) {
            normalize();
            for (Query query : queries) {
                updateSingleQuery(query.q1);
                updateSingleQuery(query.q2);
            }

//            treeSearch.processQueries(queries);
            treeSearch.processQueriesBruteForce(queries);
        }

        void updateSingleQuery(SingleQuery singleQuery) {
            singleQuery.node = nodeCollection.getNode(singleQuery.nodeId);
        }
    }

    final static class TreeSearch {
        final int size;
        boolean[] leafNodeMap;
        Node[] nodeAssociativityMap;
        LinkedList<Node>[] nodeListMap;

        TreeSearch(Tree tree, int levels) {
            size = tree.size;
            leafNodeMap = new boolean[size];
            for (Node node : tree.nodeCollection.nodes)
                leafNodeMap[node.id] = node.destinations.isEmpty();

            nodeListMap = new LinkedList[size];
            nodeAssociativityMap = new Node[size];
            populate(tree.root);
        }

        Node populate(Node node) {
            Node next = node;
            for (Edge edge : node.destinations)
                next = populate(edge.destination);

            nodeAssociativityMap[node.id] = next;
            return next;
        }

        void processQueries(Query[] queries) {
            LinkedList<SingleQuery>[] singleQueryListMap = new LinkedList[size];
            for (int i = 0; i < size; i++)
                singleQueryListMap[i] = new LinkedList<>();

            for (SingleQuery query : toSingleQueries(queries))
                singleQueryListMap[nodeAssociativityMap[query.nodeId].id].add(query);

            processSingleQueries(singleQueryListMap);
            for (Query query : queries)
                query.combineResult();
        }

        LinkedList<SingleQuery> toSingleQueries(Query[] queries) {
            LinkedList<SingleQuery> singleQuerieList = new LinkedList<>();
            for (Query query : queries) {
                singleQuerieList.addLast(query.q1);
                singleQuerieList.addLast(query.q2);
            }

            return singleQuerieList;
        }

        void processSingleQueries(LinkedList<SingleQuery>[] singleQueryListMap) {
            for (LinkedList<SingleQuery> singleQueries : singleQueryListMap)
                processQueries(singleQueries);
        }

        void processQueries(LinkedList<SingleQuery> singleQueries) {
            if (singleQueries.isEmpty())
                return;

            QueryDataWrapper[] queryDataWrappers = getDataWrappers(singleQueries);
            Arrays.sort(queryDataWrappers);
            Node leaf = nodeAssociativityMap[singleQueries.getFirst().nodeId];
            FenwickTree fenwickTree = new FenwickTree(leaf.level + 1);

            for (QueryDataWrapper queryData : queryDataWrappers) {
                if (queryData.isQuery()) {
                    SingleQuery query = queryData.query;
                    query.value = fenwickTree.query(0, query.node.level);
                } else {
                    fenwickTree.update(queryData.node.level, queryData.data);
                }
            }
        }

        QueryDataWrapper[] getDataWrappers(LinkedList<SingleQuery> singleQueries) {
            Node node = singleQueries.getFirst().node;
            QueryDataWrapper[] queryDataWrappers = new QueryDataWrapper[singleQueries.size() + node.level];
            int index = 0;
            for (SingleQuery query : singleQueries)
                queryDataWrappers[index++] = new QueryDataWrapper(query);

            while (node.parent != ANANTA) {
                queryDataWrappers[index++] = new QueryDataWrapper(node);
                node = node.parent;
            }

            return queryDataWrappers;
        }

        void validateQueries(Query[] queries) {
            Query[] copy = deepClone(queries);
            processQueries(queries);
            processQueriesBruteForce(copy);
            boolean res = true;
            int len = queries.length;
            for (int i = 0; i < len && res; i++)
                res = queries[i].value == copy[i].value;

            if (!res)
                throw new RuntimeException("outcome doesn't match");
        }

        void processQueriesBruteForce(Query[] queries) {
            for (Query query : queries)
                query.value = getQueryValueBruteForce(query);
        }

        int getQueryValueBruteForce(Query query) {
            int res = 0, threshold = query.value;
            int m1 = query.q1.magicNumber, m2 = query.q2.magicNumber;
            Node node1 = query.q1.node, node2 = query.q2.node;

            while (node1.level > node2.level) {
                if (node1.parentEdge.magicNumber <= threshold)
                    res ^= node1.parentEdge.magicNumber;

                node1 = node1.parent;
            }

            while (node1.level < node2.level) {
                if (node2.parentEdge.magicNumber <= threshold)
                    res ^= node2.parentEdge.magicNumber;

                node2 = node2.parent;
            }

            while (node1 != node2) {
                if (node1.parentEdge.magicNumber <= threshold)
                    res ^= node1.parentEdge.magicNumber;

                if (node2.parentEdge.magicNumber <= threshold)
                    res ^= node2.parentEdge.magicNumber;

                node1 = node1.parent;
                node2 = node2.parent;
            }

            return res;
        }

        int getQueryValueBruteForce(SingleQuery query) {
            int res = 0;
            Node node = query.node;
            while (node.parent != ANANTA) {
                if (node.parentEdge.magicNumber <= query.magicNumber)
                    res ^= node.parentEdge.magicNumber;
                node = node.parent;
            }

            return res;
        }

    }

    final static class Edge {
        Node source, destination;
        final int magicNumber;

        Edge(Node s, Node d, int c) {
            source = s;
            destination = d;
            magicNumber = c;

            source.addDestination(this);
            destination.addDestination(this);
        }

        Node otherNode(Node source) {
            return this.source == source ? destination : this.source;
        }

        void normalize(Node source) {
            if (this.source != source) {
                source = destination;
                destination = this.source;
                this.source = source;
            }

            destination.parent = source;
            destination.parentEdge = this;
        }
    }

    final static class NodeCollection {
        Node[] nodes;

        NodeCollection(int capacity) {
            nodes = new Node[capacity];
            for (int i = 0; i < capacity; i++)
                nodes[i] = new Node(i);
        }

        Node getNode(int index) {
            return nodes[index];
        }

        Node nodeWithMaxEdges() {
            Node ref = nodes[0];
            for (Node node : nodes)
                ref = ref.edgeCount() > node.edgeCount() ? ref : node;

            return ref;
        }
    }

    private static final Node INVALID_NODE = new Node(-1), ANANTA = new Node(100000000);

    final static class Node {
        final int id;
        LinkedList<Edge> destinations = new LinkedList<>();
        Node parent = ANANTA; // for root node, there is no parent, i.e. beginning. itself is beginning
        Edge parentEdge;
        int level = 0; // 0 is for root.


        Node(int index) {
            id = index;
        }

        void addDestination(Edge node) {
            destinations.addLast(node);
        }

        public boolean equals(Node node) {
            return id == node.id;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Node)
                return equals((Node) object);

            return false;
        }

        int edgeCount() {
            return destinations.size();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[id: ").append(id).append(" and destinations: ");
            for (Edge edge : destinations)
                sb.append(edge.otherNode(this).id).append(", ");

            sb.append("]");
            return sb.toString();
        }
    }

    final static class Query implements Cloneable {
        final int index;
        int value = 0;
        final SingleQuery q1, q2;

        Query(int id, int u, int v, int k) {
            index = id;
            q1 = new SingleQuery(u, k);
            q2 = new SingleQuery(v, k);
        }

        void combineResult() {
            value = q1.value ^ q2.value;
        }

        @Override
        public Query clone() {
            return new Query(index, q1.nodeId, q2.nodeId, q1.magicNumber);
        }
    }

    private final static SingleQuery INVALID_SINGLE_QUERY = new SingleQuery(-1, -1);

    final static class SingleQuery {
        final int nodeId, magicNumber;
        private Node node;
        int value = 0;

        SingleQuery(int nodeId, int magicNumber) {
            this.nodeId = nodeId;
            this.magicNumber = magicNumber;
        }

        @Override
        public String toString() {
            return "[node: " + nodeId + ", magicNumber: " + magicNumber + ", value: " + value + "]";
        }
    }

    final static class FenwickTree {
        private final int[] array;
        private int size = 0;
        private final int capacity;

        public FenwickTree(int capacity) {
            this.capacity = capacity;
            array = new int[capacity];
        }

        public FenwickTree(int[] ar) {
            this(ar.length);

            for (int value : ar)
                add(value);
        }

        public void add(int value) {
            array[size++] = value;

            if (size == capacity)
                populate();
        }

        /**
         * Populates the tree for the very first and only time.
         * Every update operation is called once. This method runs in order of
         * N (array length).
         */
        private void populate() {
            for (int i = 0; i < capacity; i++)
                updateTree(i);
        }

        public void update(int index, final int value) {
            updateTree(index, value);
            array[index] ^= value;
        }

        public void replace(int index, int value) {
            int oldValue = query(index, index);
            if (value == oldValue)
                return;

            update(index, value ^ oldValue);
        }

        public void remove(int index) {
            replace(index, 0);
        }

        /**
         * Returns the sum of elements from start to end index, both inclusive.
         *
         * @param start
         * @param end
         * @return
         */
        public int query(int start, int end) {
            int result = 0;
            while (end >= start) {
                result ^= array[end];
                end = end & (end + 1);
                end--;
            }

            start--;
            while (start > end) {
                result ^= array[start];
                start = start & (start + 1);
                start--;
            }

            return result;
        }

        public int query(int index) {
            return query(index, index);
        }

        public int size() {
            return size;
        }

        /**
         * This method finds the parent and update the same with this node value.
         *
         * @param index
         */
        private void updateTree(int index) {
            int nextIndex = index | (index + 1);

            if (nextIndex >= capacity)
                return;

            array[nextIndex] += array[index];
        }

        /**
         * This is an addition operation for the specified node.
         * The update value is added to the same node and recursively
         * the parent node is also updated.
         *
         * @param index current node index to be updated.
         * @param value value to be updated.
         */
        private void updateTree(int index, long value) {
            int nextIndex = index | (index + 1);

            if (nextIndex >= capacity)
                return;

            array[nextIndex] += value;
            updateTree(nextIndex, value);
        }

        public void clear() {
            size = 0;
            Arrays.fill(array, 0);
        }

        private long operation(long a, long b) {
            return a + b;
        }

        private long inverseOperation(long a, long b) {
            return a - b;
        }
    }

    final static class QueryDataWrapper implements Comparable<QueryDataWrapper> {
        final SingleQuery query;
        final int data;
        final Node node;

        QueryDataWrapper(SingleQuery query) {
            this.query = query;
            data = query.magicNumber;
            node = null;
        }

        QueryDataWrapper(Node node) {
            this.data = node.parentEdge.magicNumber;
            query = null;
            this.node = node;
        }

        boolean isQuery() {
            return query != null;
        }

        @Override
        public int compareTo(QueryDataWrapper o) {
            if (data == o.data)
                return query == null ? -1 : 1;

            return data - o.data;
        }

        @Override
        public String toString() {
            return "[node: " + node.id + ", data: " + data + ", query: " + query + "]";
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