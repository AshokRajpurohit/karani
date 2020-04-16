/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.april20;

import com.ashok.lang.utils.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Problem Name: Factor Tree
 * Link: https://www.codechef.com/APRIL20A/problems/FCTRE
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class FactorTree {
    private static final Factor[][] FACTORS_FOR_NUM;
    private static final int MOD = 1000000007;
    private static final Factor ONE = new Factor(1, 0);
    private static final Factor[] ONE_FACTORS = new Factor[]{ONE};
    private static final int[] BIT_LENGTHS;
    private static final Comparator<Factor> FACTOR_COMPARATOR = (a, b) -> a.value - b.value;
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    static {
        int n = 1000000;
        BIT_LENGTHS = new int[n + 1];
        for (int i = 2, j = 1; i <= n; i = i << 1, j++) {
            BIT_LENGTHS[i] = j;
        }

        for (int i = 2; i <= n; i++) {
            BIT_LENGTHS[i] = Math.max(BIT_LENGTHS[i], BIT_LENGTHS[i - 1]);
        }

        LinkedList<Integer>[] factors = new LinkedList[n + 1];
        for (int i = 0; i <= n; i++) factors[i] = new LinkedList<>();

        int[] nums = IntStream.range(0, n + 2).toArray();
        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                int v = nums[j];
                if (v < i) continue;
                while (v % i == 0) {
                    v /= i;
                    factors[j].add(i);
                }
                nums[j] = v;
            }
        }

        FACTORS_FOR_NUM = new Factor[n + 1][];
        for (int i = 2; i <= n; i++) FACTORS_FOR_NUM[i] = getFactors(factors[i]);
    }

    public static void main(String[] args) throws IOException {
        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            process();
        }
    }

    private static void process() throws IOException {
        int n = in.readInt();
        Node[] nodes = new Node[n];
        Edge[] edges = readEdges(n - 1);
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i, in.readInt());
        }

        Tree tree = new Tree(nodes, edges, () -> new FactorCollection());
        int q = in.readInt();
        Query[] queries = readQueries(q);
        Arrays.stream(queries)
                .parallel()
                .forEach(query -> {
                    FactorCollection factors = tree.getFactors(query.u, query.v);
                    query.result = getDivisorCounts(factors);
                });

        StringBuilder sb = new StringBuilder(q << 3);
        for (Query query : queries) {
            sb.append(query.result).append('\n');
        }

        out.print(sb);
    }

    private static Query[] readQueries(int count) throws IOException {
        Query[] queries = new Query[count];
        for (int i = 0; i < count; i++) {
            queries[i] = new Query(i, in.readInt() - 1, in.readInt() - 1);
        }

        return queries;
    }

    private static void test() throws IOException {
        while (true) {
            try {
                out.println("enter values for n and q");
                out.flush();
                int n = in.readInt();
                int limit = 1000000;
                Random random = new Random();
//                int[] vals = Generators.generateRandomIntegerArray(n, 1, limit);
                int[] vals = IntStream.range(1, n + 1).toArray();
                ArrayUtils.randomizeArray(vals);
                Node[] nodes = IntStream.range(0, n).mapToObj(i -> new Node(i, vals[i])).toArray(t -> new Node[t]);
//                Edge[] edges = generateRandomEdges(n);
                Edge[] edges = skewTree(n);
                long time = System.currentTimeMillis();
                Tree tree = null, setTree = null;
                try {
                    tree = new Tree(nodes, edges, () -> new FactorTreeSet());
//                    setTree = new Tree(nodes, edges, () -> new FactorTreeSet());
                } catch (Throwable e) {
                    e.printStackTrace();
                    tree = new Tree(nodes, edges, () -> new FactorTreeSet());
                }
                out.println("tree building time: " + (System.currentTimeMillis() - time));
                out.flush();
                int q = in.readInt();
                time = System.currentTimeMillis();
                while (q > 0) {
                    q--;
                    int u = random.nextInt(n), v = random.nextInt(n);
                    try {
                        tree.getFactors(u, v);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        tree.getFactors(u, v);
                    }
                }
                out.println("tree query time: " + (System.currentTimeMillis() - time));
                out.flush();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private static Edge[] generateRandomEdges(int n) {
        Random random = new Random();
        int[] ar = IntStream.range(0, n).toArray();
        ArrayUtils.randomizeArray(ar);
        Edge[] edges = IntStream.range(1, n).mapToObj(i -> new Edge(i, random.nextInt(i))).toArray(t -> new Edge[t]);
        return edges;
    }

    private static Edge[] skewTree(int n) {
        return IntStream.range(1, n).mapToObj(i -> new Edge(i - 1, i)).toArray(t -> new Edge[t]);
    }

    private static Edge[] readEdges(int count) throws IOException {
        Edge[] edges = new Edge[count];
        for (int i = 0; i < count; i++) {
            edges[i] = new Edge(in.readInt() - 1, in.readInt() - 1);
        }
        return edges;
    }

    private static Factor[] mergeFactors(Factor[] factors) {
        Arrays.sort(factors, (a, b) -> a.value - b.value);
        Deque<Factor> fs = new LinkedList<>(); // factor-stack
        fs.push(factors[0]);
        for (int i = 1; i < factors.length; i++) {
            Factor factor = factors[i];
            Factor prev = fs.peek();
            if (prev.value == factor.value) {
                fs.pop();
                fs.push(prev.merge(factor));
            } else {
                fs.push(factor);
            }
        }

        return fs.stream().toArray(t -> new Factor[t]);
    }

    private static Factor[] getFactors(List<Integer> factors) {
        int prime = factors.get(0), count = 0;
        List<Factor> factorList = new LinkedList<>();
        for (int e : factors) {
            if (e == prime) count++;
            else {
                Factor factor = new Factor(prime, count);
                factorList.add(factor);
                count = 1;
                prime = e;
            }
        }

        Factor last = new Factor(prime, count);
        factorList.add(last);
        return factorList.stream().toArray(t -> new Factor[t]);
    }

    private static <T> T[] singletonArray(T a) {
        T[] array = (T[]) Array.newInstance(a.getClass(), 1);
        return array;
    }

    private static Factor[] getFactors(int v) {
        if (v == 1) return ONE_FACTORS;
        return FACTORS_FOR_NUM[v];
    }

    private static long getDivisorCounts(Iterable<Factor> factors) {
        long res = 1;
        for (Factor factor : factors) {
            res *= factor.count + 1;
            if (res >= MOD) res %= MOD;
        }

        return res;
    }

    private static long getDivisorCounts(Factor[] factors) {
        long res = 1;
        for (Factor factor : factors) {
            res *= factor.count + 1;
            if (res >= MOD) res %= MOD;
        }

        return res;
    }

    private static int getRootCandidate(Edge[] edges) {
        int n = edges.length + 1;
        int[] counts = new int[n];
        for (Edge edge : edges) {
            counts[edge.u]++;
            counts[edge.v]++;
        }

        return IntStream.range(0, n).mapToObj(v -> v).max((a, b) -> counts[a] - counts[b]).orElse(0);
    }

    private static int getTreeCenter(Edge[] edges) {
        int n = edges.length + 1;
        int[] counts = new int[n];
        List<Integer>[] destinations = IntStream.range(0, n).mapToObj(i -> new LinkedList<Integer>()).toArray(t -> new List[t]);
        for (Edge edge : edges) {
            counts[edge.u]++;
            counts[edge.v]++;
            destinations[edge.u].add(edge.v);
            destinations[edge.v].add(edge.u);
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        int[] leafs = IntStream.range(0, n).filter(i -> counts[i] == 1).toArray();
        for (int e : leafs) {
            visited[e] = true;
            queue.add(e);
        }

        int center = 0;
        while (!queue.isEmpty()) {
            center = queue.remove();
            for (int next : destinations[center]) {
                if (visited[next]) continue;
                visited[next] = true;
                queue.add(next);
            }
        }

        return center;
    }

    @FunctionalInterface
    interface FactorCollectionProvider {
        FactorCollection newInstance();
    }

    final static class Tree {
        final Node root;
        final Node[] nodes;
        final int size;
        final FactorTree.FactorCollectionProvider fcp;

        Tree(Node[] nodes, Edge[] edges, final FactorCollectionProvider fcp) {
            this.fcp = fcp;
            int rootId = getTreeCenter(edges);
            size = nodes.length;
            this.nodes = nodes;
            root = nodes[rootId];
            formTree(edges);
        }

        private void formTree(Edge[] edges) {
            for (Node node: nodes) node.cumulativeFactors = fcp.newInstance();
            updateConnections(edges);
            updateDirectParents();
            updateHeights();
            Runnable updateCumulativeFactors = () -> updateCumulativeFactors();
            Runnable updateParents = () -> updateParents();
            updateCumulativeFactors.run();
            updateParents.run();

            Stream.of(updateCumulativeFactors, updateParents).parallel().forEach(Runnable::run);
        }

        private void updateParents() {
            int maxHeight = Arrays.stream(nodes).mapToInt(node -> node.height).max().getAsInt();
            int bitLen = BIT_LENGTHS[maxHeight];
            for (Node node : nodes) {
                node.parents = new Node[bitLen + 1];
                node.parents[0] = node.parent;
            }

            Queue<Node> queue = new LinkedList<>();
            root.childNodes.forEach(cns -> queue.addAll(cns.childNodes));
            while (!queue.isEmpty()) {
                Node node = queue.remove();
                int level = node.height;
                for (int i = 2, j = 1; i <= level; i <<= 1, j++) {
                    node.parents[j] = node.parents[j - 1].parents[j - 1];
                }

                queue.addAll(node.childNodes);
            }
        }

        /**
         * Finds the parent at height different equal to {@code level}.
         *
         * @param node,  the reference node.
         * @param level, the height of parent compared to node.
         * @return Parent node at given height.
         */
        private Node getParent(Node node, int level) {
            if (level == 0) return node;
            if (level == 1) return node.parent;
            int bitLen = BIT_LENGTHS[level];
            int r = 1 << bitLen;
            while (level > 1) {
                level = level ^ r; // let's remove the MSB
                r >>>= 1;
                node = node.parents[bitLen];
                bitLen--;
                while (level > 1 && (level & r) == 0) {
                    r >>>= 1;
                    bitLen--;
                }
            }

            return level == 1 ? node.parent : node;
        }

        private void updateCumulativeFactors() {
            for (Node node: nodes) {
                for (Factor factor : node.factors) {
                    node.cumulativeFactors.add(factor);
                }
            }

            Queue<Node> queue = new LinkedList<>();
            queue.addAll(root.childNodes);
            while (!queue.isEmpty()) {
                List<Node> list = Collections.synchronizedList(new LinkedList<>());
                queue.stream()
                        .parallel()
                        .forEach(node -> {
                            FactorCollection factors = node.cumulativeFactors;
                            factors.addAll(node.parent.cumulativeFactors);
                            list.addAll(node.childNodes);
                        });

                queue.clear();
                queue.addAll(list);
            }
            /*while (!queue.isEmpty()) {
                Node node = queue.remove();
                FactorCollection factors = node.cumulativeFactors;
                node.childNodes.forEach(child -> {
                    FactorCollection childFactors = child.cumulativeFactors;
                    childFactors.addAll(factors);
                });

                queue.addAll(node.childNodes);
            }*/
        }

        private void updateConnections(Edge[] edges) {
            for (Edge edge : edges) {
                Node u = nodes[edge.u];
                Node v = nodes[edge.v];
                u.addChild(v);
                v.addChild(u);
            }
        }

        private void updateDirectParents() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node node = queue.remove();
                List<Node> childs = node.childNodes;
                childs.stream().forEach(child -> {
                    child.childNodes.remove(node);
                    child.parent = node;
                });
                queue.addAll(node.childNodes);
            }
        }

        private void updateHeights() {
            root.height = 0;
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node node = queue.remove();
                int height = node.height + 1;
                node.childNodes.forEach(child -> child.height = height);
                queue.addAll(node.childNodes);
            }
        }

        private FactorCollection getFactors(int u, int v) {
            FactorCollection factors = new FactorTreeSet();
            if (u == v) {
                for (Factor factor : nodes[u].factors) factors.add(factor);
                return factors;
            }
            Node n1 = nodes[u], n2 = nodes[v];
            Node parent = getParent(n1, n2);
            factors.addAll(n1.cumulativeFactors);
            factors.addAll(n2.cumulativeFactors);
            factors.reduceAll(parent.cumulativeFactors);
            factors.reduceAll(parent.cumulativeFactors);
            for (Factor factor : parent.factors) {
                factors.add(factor);
            }

            return (factors);
        }

        private Node getParent(Node a, Node b) {
            if (a.height > b.height) return getParent(b, a);
            int heightDiff = b.height - a.height;
            b = getParent(b, heightDiff);
            while (a != b) {
                if (a.parent == b.parent) return a.parent;
                for (int i = 1; i < a.parents.length; i++) {
                    if (a.parents[i] == b.parents[i] || a.parents[i] == null || b.parents[i] == null) {
                        a = a.parents[i - 1];
                        b = b.parents[i - 1];
                        break;
                    }
                }
                a = a.parent;
                b = b.parent;
            }

            return a;
        }

        private List<Node> getPath(Node u, Node v) {
            List<Node> nodes = new ArrayList<>();
            int minDepth = Math.min(u.height, v.height);
            while (u.height > minDepth) {
                nodes.add(u);
                u = u.parent;
            }

            while (v.height > minDepth) {
                nodes.add(v);
                v = v.parent;
            }

            while (u != v) {
                nodes.add(u);
                nodes.add(v);
                u = u.parent;
                v = v.parent;
            }

            nodes.add(u);

            return nodes;
        }
    }

    final static class Node {
        final int id, value;
        final Factor[] factors;
        private final List<Node> childNodes = new LinkedList<>();
        private FactorCollection cumulativeFactors; // all the factors from root down to this node.
        private Node parent;
        private Node[] parents; // bit map using bit length, 1,2,3 means 2nd, 4th, 8th level above.
        private int height = 0;

        Node(final int id, final int value) {
            this.id = id;
            this.value = value;
            factors = getFactors(value);
        }

        void addChild(Node node) {
            childNodes.add(node);
        }

        public String toString() {
            return id + "=" + value + " => " + Arrays.stream(factors).collect(Collectors.toList());
        }
    }

    final static class Query {
        final int id, u, v;
        long result = 0;

        Query(final int id, final int u, final int v) {
            this.id = id;
            this.u = u;
            this.v = v;
        }
    }

    final static class Edge {
        final int u, v;

        Edge(final int u, final int v) {
            this.u = u;
            this.v = v;
        }

        public String toString() {
            return u + " <-> " + v;
        }
    }

    final static class Factor {
        final int value, count;

        Factor(final int value, final int count) {
            this.value = value;
            this.count = count;
        }

        Factor merge(Factor factor) {
            if (value != factor.value) throw new RuntimeException("can not be merged");
            return new Factor(value, count + factor.count);
        }

        Factor reduce(Factor factor) {
            if (value != factor.value || count < factor.count) {
                throw new RuntimeException("not possible to reduce factor");
            }

            return new Factor(value, count - factor.count);
        }

        public int hashCode() {
            return value;
        }

        /**
         * Not considering the count for equality for HashSet and merging two Factors.
         *
         * @param o
         * @return
         */
        public boolean equals(Object o) {
            return value == ((Factor) o).value;
        }

        public String toString() {
            return value + "^" + count;
        }
    }

    static class FactorCollection implements Iterable<Factor> {
        private Map<Factor, Factor> map = new HashMap<>();

        public Factor add(Factor factor) {
            if (!map.containsKey(factor)) {
                map.put(factor, factor);
                return factor;
            }

            Factor prev = map.get(factor);
            Factor newFact = prev.merge(factor);
            map.put(newFact, newFact);
            return newFact;
        }

        public Factor reduce(Factor factor) {
            Factor prev = map.get(factor);
            Factor newFactor = prev.reduce(factor);
            map.put(newFactor, newFactor);
            return newFactor;
        }

        public void reduceAll(FactorCollection factors) {
            factors.forEach(factor -> reduce(factor));
        }

        public void addAll(FactorCollection factors) {
            factors.forEach(factor -> add(factor));
        }

        public Iterator<Factor> iterator() {
            return map.values().iterator();
        }
    }

    static class FactorTreeSet extends FactorCollection {

        TreeMap<Factor, Factor> map = new TreeMap(FACTOR_COMPARATOR);

        public Factor add(Factor factor) {
            if (!map.containsKey(factor)) {
                map.put(factor, factor);
                return factor;
            }

            Factor prev = map.get(factor);
            Factor newFact = prev.merge(factor);
            map.put(newFact, newFact);
            return newFact;
        }

        public Factor reduce(Factor factor) {
            Factor prev = map.get(factor);
            Factor newFactor = prev.reduce(factor);
            map.put(newFactor, newFactor);
            return newFactor;
        }

        public void reduceAll(FactorCollection factors) {
            factors.forEach(factor -> reduce(factor));
        }

        public void addAll(FactorCollection factors) {
            factors.forEach(factor -> add(factor));
        }

        public Iterator<Factor> iterator() {
            return map.values().iterator();
        }
    }

    final static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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