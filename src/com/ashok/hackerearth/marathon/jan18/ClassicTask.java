/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan18;

import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: Classic Task
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-18/algorithm/classic-task-39656dbf/
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ClassicTask {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        play();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        EdgeSet[] edgeSets = new EdgeSet[m];
        for (int i = 0; i < m; i++)
            edgeSets[i] = new EdgeSet(in.readInt() - 1, in.readInt() - 1);

        out.println(process(edgeSets, n));
    }

    private static void play() throws IOException {
        while (true) {
            int n = in.readInt(), m = in.readInt();
            while (true) {
                reset();
                EdgeSet[] edgeSets = new EdgeSet[m];
                int[] ar = Generators.generateRandomIntegerArray(m, n);
                int[] br = Generators.generateRandomIntegerArray(m, n);

                for (int i = 0; i < m; i++)
                    edgeSets[i] = new EdgeSet(Math.min(ar[i], br[i]), Math.max(ar[i], br[i]));

                out.println(process(edgeSets, n));
                out.flush();
            }
        }
    }

    private static void reset() {
        GraphNode.nodeSequence = 0;
        GraphNode.list.clear();

        ConnectedSet.connectedSetSequence = 0;
        ConnectedSet.sets.clear();
    }

    private static int process(EdgeSet[] edgeSets, int nodeCount) {
        GraphNode[] nodes = getNodes(nodeCount);
        RangeQueryLazy rql = new RangeQueryLazy(nodeCount);
        for (EdgeSet edges : edgeSets)
            rql.update(edges);

        /*boolean[] groupedNode = new boolean[nodeCount];
        List<Pair> mergers = new LinkedList<>();

        for (int i = 0; i < nodeCount; i++) {
            if (!groupedNode[i]) {
                groupedNode[i] = true;
                List<EdgeSet> edges = rql.query(i);
                GraphNode node = nodes[i];
                ConnectedSet set = node.set;
                for (EdgeSet edgeSet : edges) {
                    GraphNode next = edgeSet.connectedNode(i);
                    if (groupedNode[next.id]) {
                        mergers.add(new Pair(node.set.id, next.set.id));
                        continue;
                    }

                    set.addNode(next);
                    groupedNode[next.id] = true;
                }
            }
        }

        processSetMerges(mergers);*/
        process(nodes, rql);
        Set<ConnectedSet> connectedSets = new HashSet<>();
        for (GraphNode node : nodes)
            connectedSets.add(node.set);

        return connectedSets.size();
    }

    private static int bruteForce(EdgeSet[] edgeSets, int nodeCount) {
        return 0;
    }

    private static void process(GraphNode[] nodes, RangeQueryLazy rql) {
        List<ConnectedSet> connectedSets = getConnectedSets(nodes);
        Collections.sort(connectedSets);
        GenericHeap heap = new GenericHeap(new Comparator<ConnectedSet>() {
            @Override
            public int compare(ConnectedSet o1, ConnectedSet o2) {
                return o1.compareTo(o2);
            }
        }, connectedSets.size(), referenceProvider);

        for (ConnectedSet connectedSet : connectedSets)
            heap.add(connectedSet);

        boolean cont = true;
        while (cont && !heap.isEmpty()) {
            cont = false;
            ConnectedSet connectedSet = heap.poll();
            for (GraphNode node : connectedSet.nodes) {
                for (EdgeSet edgeSet : rql.query(node.id)) {
                    ConnectedSet next = edgeSet.connectedNode(node.id).set;
                    if (connectedSet == next)
                        continue;

                    cont = true;
                    next.merge(connectedSet);
                    heap.restructure(next);
                    break;
                }

                if (cont)
                    break;
            }
        }
    }

    private static List<ConnectedSet> getConnectedSets(GraphNode[] nodes) {
        List<ConnectedSet> connectedSets = new LinkedList<>();
        for (GraphNode node : nodes)
            connectedSets.add(node.set);

        return connectedSets;
    }

    private static void processSetMerges(List<Pair> mergers) {
        int[] mergeMap = new int[ConnectedSet.sets.size()];
        Arrays.fill(mergeMap, -1);

        for (Pair merger : mergers) {
            ConnectedSet a = ConnectedSet.getById(merger.a), b = ConnectedSet.getById(merger.b);
            while (a.size == 0) {
                int aid = mergeMap[a.id];
                a = ConnectedSet.getById(aid);
            }

            while (b.size == 0) {
                int bid = mergeMap[b.id];
                b = ConnectedSet.getById(bid);
            }

            if (a.size > b.size) {
                a.merge(b);
                mergeMap[b.id] = a.id;
            } else {
                b.merge(a);
                mergeMap[a.id] = b.id;
            }
        }
    }

    private static GraphNode[] getNodes(int size) {
        GraphNode[] nodes = new GraphNode[size];
        for (int i = 0; i < size; i++)
            nodes[i] = new GraphNode();

        Comparator<GraphNode> comparator = new Comparator<GraphNode>() {
            @Override
            public int compare(GraphNode o1, GraphNode o2) {
                return o1.id - o2.id;
            }
        };

        Arrays.sort(nodes, comparator);
        Collections.sort(GraphNode.list, comparator);

        Comparator<ConnectedSet> setComparator = new Comparator<ConnectedSet>() {
            @Override
            public int compare(ConnectedSet o1, ConnectedSet o2) {
                return o1.id - o2.id;
            }
        };

        Collections.sort(ConnectedSet.sets, setComparator);
        return nodes;
    }

    final static class Pair {
        final int a, b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    final static class EdgeSet implements Comparable<EdgeSet> {
        final int start, end, weight;

        EdgeSet(int start, int end) {
            this.start = start;
            this.end = end;
            weight = start + end;
        }

        private boolean containsNode(int node) {
            return start <= node && node <= end;
        }

        private GraphNode connectedNode(int node) {
            return GraphNode.getById(containsNode(node) ? weight - node : -1);
        }

        @Override
        public int compareTo(EdgeSet edgeSet) {
            return start == edgeSet.start ? end - edgeSet.end : start - edgeSet.start;
        }

        public String toString() {
            return start + ", " + end;
        }
    }

    final static class ConnectedSet implements Comparable<ConnectedSet> {
        private static int connectedSetSequence = 0;
        private static ArrayList<ConnectedSet> sets = new ArrayList<>(1000);

        final int id = connectedSetSequence++;
        private int size = 0, ref = 0;
        Set<GraphNode> nodes = new TreeSet<>();

        private ConnectedSet() {
            sets.add(this);
        }

        private static ConnectedSet getById(int id) {
            return sets.get(id);
        }

        private void addNode(GraphNode node) {
            if (nodes.add(node))
                size++;

            node.set = this;
        }

        private void merge(ConnectedSet connectedSet) {
            for (GraphNode node : connectedSet.nodes)
                addNode(node);

            connectedSet.clear();
        }

        private void clear() {
            size = 0;
            nodes.clear();
        }

        private void remove(GraphNode node) {
            if (nodes.remove(node))
                size--;
        }

        public int hashCode() {
            return id;
        }

        @Override
        public int compareTo(ConnectedSet connectedSet) {
            return size - connectedSet.size;
        }

        public String toString() {
            return id + ", " + size;
        }
    }

    private static final GraphNode INVALID_GRAPH_NODE = new GraphNode();

    final static class GraphNode implements Comparable<GraphNode> {
        private static int nodeSequence = -1;
        private static ArrayList<GraphNode> list = new ArrayList<>(1000);

        final int id = nodeSequence++;
        private ConnectedSet set = new ConnectedSet();
        boolean mark;

        private GraphNode() {
            set.addNode(this);
            list.add(this);
            if (id == -1) list.remove(0);
        }

        private void detach() {
            set.remove(this);
        }

        public int hashCode() {
            return id;
        }

        @Override
        public int compareTo(GraphNode node) {
            return set.compareTo(node.set);
        }

        public static GraphNode getById(int id) {
            return id < 0 || id >= list.size() ? INVALID_GRAPH_NODE : list.get(id);
        }

        public String toString() {
            return id + ", set: " + set;
        }
    }

    /**
     * Implementation of Range Query Lazy Updation.
     * This kind of Range query is useful when the update is for an index range.
     * This is to find the minimum element in the range and the update function is
     * incrementing all the elements by parameter in the range.
     * If the upate is always for single element then use {@link RangeQueryUpdate}.
     *
     * @author Ashok Rajpurohit ashok1113@gmail.com
     */
    final static class RangeQueryLazy {
        private Node root;

        public RangeQueryLazy(int size) {
            construct(size);
        }

        public void update(EdgeSet change) {
            update(root, change);
        }

        public List<EdgeSet> query(int index) {
            LinkedList<EdgeSet> edgeSets = new LinkedList<>();
            query(root, index, edgeSets);
            return edgeSets;
        }

        /**
         * Updates the node and child nodes if necessary.
         *
         * @param root
         * @param change List of edges represented in short form.
         */
        private static void update(Node root, EdgeSet change) {
            if (root.l >= change.start && root.r <= change.end) {
                root.changes.add(change);
                return;
            }

            int mid = (root.l + root.r) >>> 1;
            if (change.start > mid) {
                update(root.right, change);
            } else if (change.end <= mid) {
                update(root.left, change);
            } else {
                update(root.left, change);
                update(root.right, change);
            }
        }

        private static void query(Node root, int index, List<EdgeSet> list) {
            if (root.l == root.r) {
                list.addAll(root.changes);
                return;
            }

            int mid = (root.l + root.r) >>> 1;
            list.addAll(root.changes);

            if (index > mid)
                query(root.right, index, list);
            else if (index <= mid)
                query(root.left, index, list);
        }

        private void construct(int size) {
            root = new Node(0, size - 1);
            int mid = (size - 1) >>> 1;
            root.left = construct(0, mid);
            root.right = construct(mid + 1, size - 1);
        }

        private Node construct(int l, int r) {
            if (l == r)
                return new Node(l, l);

            Node temp = new Node(l, r);
            int mid = (l + r) >>> 1;
            temp.left = construct(l, mid);
            temp.right = construct(mid + 1, r);
            return temp;
        }

        private final static class Node {
            Node left, right;
            int l, r;
            final List<EdgeSet> changes = new LinkedList<>();

            Node(int i, int j) {
                l = i;
                r = j;
            }
        }
    }

    /**
     * This Heap (aka Priority Queue) implementation is for fix size.
     * It is assumed that in most situation the max size is already known and
     * we have to know the highest priority element or highest k priority elements
     * or kth priority element. In all these case the k (max size) is fixed.
     *
     * @author Ashok Rajpurohit (ashok1113@gmail.com).
     */
    final static class GenericHeap {
        private final Comparator<? super ConnectedSet> comparator;
        public final int capacity;
        private int count = 0;
        private final ConnectedSet[] heap;
        private final ReferenceProviderSetter referenceProvider;

        public GenericHeap(Comparator<ConnectedSet> comparator, int capacity, ReferenceProviderSetter referenceProvider) {
            this.comparator = comparator;
            this.capacity = capacity;
            heap = new ConnectedSet[capacity];
            this.referenceProvider = referenceProvider;
        }

        public ConnectedSet poll() {
            ConnectedSet res = heap[0];
            count--;
            heap[0] = heap[count];
            reformatDown(0);

            return res;
        }

        public boolean isEmpty() {
            return count <= 0;
        }

        public ConnectedSet peek() {
            return heap[0];
        }

        public boolean isFull() {
            return count == capacity;
        }

        /**
         * If the heap is already full then this will update the already existing
         * top element if necessary.
         *
         * @param e
         * @return
         */
        public boolean offer(ConnectedSet e) {
            if (count == capacity)
                return update(e);
            else
                add(e);

            return true;
        }

        public void addAll(ConnectedSet[] ar) {
            for (ConnectedSet e : ar)
                add(e);
        }

        private void add(ConnectedSet e) {
            set(count, e);
            reformatUp(count);
            count++;
        }

        private boolean update(ConnectedSet e) {
            if (comparator.compare(e, heap[0]) <= 0)
                return false;

            set(0, e);
            reformatDown(0);
            return true;
        }

        private void reformatUp(int index) {
            ConnectedSet val = heap[index];
            while (index != 0) {
                int parent = (index - 1) >>> 1;
                ConnectedSet p = heap[parent];
                if (comparator.compare(p, val) <= 0)
                    break;

                set(index, p);
                index = parent;
            }

            set(index, val);
        }

        private void set(int index, ConnectedSet connectedSet) {
            heap[index] = connectedSet;
            referenceProvider.setValue(connectedSet, index);
        }

        private void reformatDown(int index) {
            ConnectedSet val = heap[index];
            while (((index << 1) + 1) < count) {
                int c = getSmallerChild(index);
                ConnectedSet e = heap[c];
                if (comparator.compare(val, e) <= 0)
                    break;

                set(index, e);
                index = c;
            }

            set(index, val);
        }

        private int getParent(int index) {
            return (index - 1) >>> 1;
        }

        private int getSmallerChild(int index) {
            int c1 = (index << 1) + 1, c2 = c1 + 1;

            if (c2 == count)
                return c1;

            return comparator.compare(heap[c1], heap[c2]) <= 0 ? c1 : c2;
        }

        public void restructure(ConnectedSet e) {
            int index = referenceProvider.getValue(e);
            reformatDown(index);
            reformatUp(index);
        }
    }

    private static ReferenceProviderSetter referenceProvider = new ReferenceProviderSetter() {
        @Override
        public int getValue(ConnectedSet connectedSet) {
            return connectedSet.ref;
        }

        @Override
        public int setValue(ConnectedSet connectedSet, int newValue) {
            return connectedSet.ref = newValue;
        }
    };

    interface ReferenceProviderSetter {
        int getValue(ConnectedSet connectedSet);

        int setValue(ConnectedSet connectedSet, int newValue);
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