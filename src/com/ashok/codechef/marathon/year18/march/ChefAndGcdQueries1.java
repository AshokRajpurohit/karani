/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.march;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Problem Name: Chef and Gcd Queries
 * Link: https://www.codechef.com/MARCH18A/problems/GCDCNT
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndGcdQueries1 {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int LIMIT = 100000;
    private static final int UPDATE_QUERY = 1, READ_QUERY = 2;
    private static int[] PRIMES, LARGEST_PRIME_FACTOR;
    private static final int[][] PRIME_FACTORS = new int[LIMIT + 1][];
    private static volatile Semaphore processingLock = new Semaphore(0), preprocessingLock = new Semaphore(0);
    private static boolean[] PRIME_MAP;
    private static final Thread preprocessing = new Thread(() -> populate());

    private static void populate() {
        List<Integer>[] map = new List[LIMIT + 1];
        PRIMES = generatePrimes(LIMIT);
        PRIME_MAP = new boolean[LIMIT + 1];
        LARGEST_PRIME_FACTOR = new int[LIMIT + 1];
        for (int i = 0; i <= LIMIT; i++)
            map[i] = new LinkedList<>();

        for (int e : PRIMES) {
            for (int f = e; f <= LIMIT; f += e) {
                LARGEST_PRIME_FACTOR[f] = e;
                map[f].add(e);
            }
        }

        for (int i = 1; i <= LIMIT; i++) {
            PRIME_FACTORS[i] = toArray(map[i]);
        }

        for (int e : PRIMES) PRIME_MAP[e] = true;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        populate();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        QueryProcessor processor = getQueryProcessor(ar);
        int q = in.readInt();
        Query[] queries = new Query[q];
        for (int i = 0; i < q; i++) {
            Query query = new Query(in.readInt());
            if (query.queryType == QueryType.READ)
                query.setParams(in.readInt() - 1, in.readInt() - 1, in.readInt());
            else
                query.setParams(in.readInt() - 1, in.readInt());

            queries[i] = query;
        }

        StringBuilder sb = new StringBuilder(q << 2);
        for (Query query : queries) {
            if (query.queryType == QueryType.READ)
                sb.append(processor.query(query.a, query.b, query.c)).append('\n');
            else
                processor.update(query.a, query.b);
        }

        out.print(sb);
    }

    private static void passThrough(Semaphore lock) {
        try {
            lock.acquire();
            lock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        ar[0] = true;
        ar[1] = true;

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = count(ar, false);
        int[] ret = new int[count];
        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }
        return ret;
    }

    private static int count(boolean[] ar, boolean value) {
        int count = 0;
        for (boolean b : ar) if (b == value) count++;
        return count;
    }

    private static int[] toArray(Collection<Integer> c) {
        int[] ar = new int[c.size()];
        int index = 0;
        for (int e : c) ar[index++] = e;
        return ar;
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

    private static int count(boolean[] ar, int from, int to, boolean value) {
        int count = 0;
        while (from <= to) {
            if (ar[from] == value) count++;
            from++;
        }

        return count;
    }

    private static QueryProcessor getQueryProcessor(int[] ar) {
        QueryProcessor queryProcessor = new RedBlackTreeQueryProcessor(ar);
        queryProcessor.preprocess();
        return queryProcessor;
    }

    final static class Query {
        private final QueryType queryType;
        private int a, b, c;

        Query(int type) {
            queryType = type == READ_QUERY ? QueryType.READ : QueryType.UPDATE;
        }

        void setParams(int a, int b) {
            this.a = a;
            this.b = b;
        }

        void setParams(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    enum QueryType {
        READ, UPDATE
    }

    interface QueryProcessor {
        void update(int index, int value);

        int query(int left, int right, int num);

        void preprocess();

        default void startProcessing() {
            passThrough(preprocessingLock);
            preprocess();
            processingLock.release();
        }

        default void waitForProcessing() {
            passThrough(processingLock);
        }
    }

    private static final class OptimizedQueryProcessor implements QueryProcessor {
        private final int[] ar;
        private final RangeQueryUpdate[] rangeQueryUpdates = new RangeQueryUpdate[LIMIT + 1];

        OptimizedQueryProcessor(int[] ar) {
            this.ar = ar;
        }

        public void preprocess() {

        }

        @Override
        public void update(int index, int value) {
            if (ar[index] == value)
                return;


        }

        @Override
        public int query(int left, int right, int num) {
            return 0;
        }
    }

    /**
     * Test class to validate the actual logic.
     */
    private static final class BruteForceQuery implements QueryProcessor {
        private final int[] ar;
        private final boolean[] map;

        BruteForceQuery(int[] ar) {
            this.ar = ar;
            map = new boolean[ar.length];
        }

        public void preprocess() {
        }

        @Override
        public int query(int left, int right, int num) {
            if (num == 1 || num == 0) return right + 1 - left;
            int count;

            for (int f : PRIME_FACTORS[num])
                for (int i = left; i <= right; i++)
                    map[i] |= ar[i] % f == 0;

            count = count(map, left, right, false);
            Arrays.fill(map, left, right + 1, false);
            return count;
        }

        @Override
        public void update(int index, int value) {
            ar[index] = value;
        }
    }

    private static final class AvlTreeQueryProcessor implements QueryProcessor {
        final int[] ar;
        private final BinarySearchTree[] trees = new BinarySearchTree[LIMIT + 1];
        private final boolean[] map;
        private final CustomArray customArray;

        AvlTreeQueryProcessor(int[] ar) {
            this.ar = ar;
            map = new boolean[ar.length];
            customArray = new CustomArray(ar.length << 3);
        }

        @Override
        public void update(int index, int value) {
            if (ar[index] == value)
                return;

            int oldValue = ar[index];
            int common = gcd(oldValue, value);
            oldValue /= common;
            value /= common;
            ar[index] = value;
            if (oldValue != 1)
                for (int e : PRIME_FACTORS[oldValue])
                    trees[e].remove(index);

            if (value != 1)
                for (int f : PRIME_FACTORS[value])
                    trees[f].add(index);
        }

        @Override
        public int query(int left, int right, int num) {
            if (num == 1 || num == 0)
                return right + 1 - left;

            for (int e : PRIME_FACTORS[num])
                trees[e].collect(left, right, customArray);

            if (customArray.isEmpty())
                return right + 1 - left;

            int[] ar = customArray.array;
            for (int i = 0; i < customArray.index; i++)
                map[ar[i]] = true;

            int count = 0;
            for (int i = 0; i < customArray.index; i++) {
                if (map[ar[i]]) count++;
                map[ar[i]] = false;
            }

            customArray.reset();
            return right + 1 - left - count;
        }

        @Override
        public void preprocess() {
            for (int prime : PRIMES)
                trees[prime] = new BinarySearchTree();

            int index = 0;
            for (int e : ar) {
                if (e != 1)
                    for (int t : PRIME_FACTORS[e]) trees[t].add(index);

                index++;
            }
        }
    }

    private static final class RedBlackTreeQueryProcessor implements QueryProcessor {
        final int[] ar;
        private final RedBlackTree[] trees = new RedBlackTree[LIMIT + 1];
        private final boolean[] map;
        private final CustomArray customArray;

        RedBlackTreeQueryProcessor(int[] ar) {
            this.ar = ar;
            map = new boolean[ar.length];
            customArray = new CustomArray(ar.length << 3);
        }

        @Override
        public void update(int index, int value) {
            if (ar[index] == value)
                return;

            int oldValue = ar[index];
            int common = gcd(oldValue, value);
            oldValue /= common;
            value /= common;
            ar[index] = value;
            if (oldValue != 1)
                for (int e : PRIME_FACTORS[oldValue])
                    trees[e].remove(index);

            if (value != 1)
                for (int f : PRIME_FACTORS[value])
                    trees[f].add(index);
        }

        @Override
        public int query(int left, int right, int num) {
            if (num == 1 || num == 0)
                return right + 1 - left;

            for (int e : PRIME_FACTORS[num])
                trees[e].collect(left, right, customArray);

            if (customArray.isEmpty())
                return right + 1 - left;

            int[] ar = customArray.array;
            for (int i = 0; i < customArray.index; i++)
                map[ar[i]] = true;

            int count = 0;
            for (int i = 0; i < customArray.index; i++) {
                if (map[ar[i]]) count++;
                map[ar[i]] = false;
            }

            customArray.reset();
            return right + 1 - left - count;
        }

        @Override
        public void preprocess() {
            for (int prime : PRIMES)
                trees[prime] = new RedBlackTree();

            int index = 0;
            for (int e : ar) {
                if (e != 1)
                    for (int t : PRIME_FACTORS[e]) trees[t].add(index);

                index++;
            }
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

    private static final class RangeQueryContainer {
        final int size;
        final LinkedList<RangeQueryUpdate> availableQueryUpdateList = new LinkedList<>();

        RangeQueryContainer(int size) {
            this.size = size;
        }

        void release(RangeQueryUpdate rangeQuery) {
            availableQueryUpdateList.add(rangeQuery);
        }

        RangeQueryUpdate getRangeQuery(int[] ar) {
            if (availableQueryUpdateList.isEmpty())
                availableQueryUpdateList.add(new RangeQueryUpdate(ar));

            RangeQueryUpdate queryUpdate = availableQueryUpdateList.removeFirst();
            for (int i = 0; i < size; i++)
                queryUpdate.update(i, ar[i]);

            return queryUpdate;
        }

        RangeQueryUpdate getRangeQuery() {
            if (availableQueryUpdateList.isEmpty())
                return new RangeQueryUpdate(new int[size]);

            return availableQueryUpdateList.removeFirst();
        }
    }

    /**
     * This class is for frequent updates of elements. It updates the element and
     * Structure in order of log(n). If updates are for in range then it's
     * better to use {@link RangeQueryLazy} Lazy propagation.
     */
    private static final class RangeQueryUpdate {
        private Node root;
        private final int[] ar;

        public RangeQueryUpdate(int[] ar) {
            this.ar = ar;
            construct(ar);
        }

        public long query(int L, int R) {
            if (R < L)
                return query(R, L);

            if (R > root.r || L < 0)
                throw new IndexOutOfBoundsException(L + ", " + R);

            return query(root, L, R);
        }

        private int query(Node node, int L, int R) {
            if (node.l == L && node.r == R || node.data == 0)
                return node.data;

            int mid = (node.l + node.r) >>> 1;
            if (L > mid)
                return query(node.right, L, R);

            if (R <= mid)
                return query(node.left, L, R);

            return operation(query(node.left, L, mid),
                    query(node.right, mid + 1, R));
        }

        public void update(int index, int data) {
            if (ar[index] == data)
                return;

            update(root, index, data);
        }

        private void update(Node root, int i, int data) {
            if (data == 1)
                root.data++;
            else
                root.data--;

            if (root.l == root.r)
                return;

            int mid = (root.l + root.r) >>> 1;
            if (i > mid)
                update(root.right, i, data);
            else
                update(root.left, i, data);
        }

        private void construct(int[] ar) {
            root = new Node(0, ar.length - 1);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
            root.data = operation(root.left.data, root.right.data);
        }

        private Node construct(int[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l, ar[l]);

            Node temp = new Node(l, r);
            int mid = (l + r) >>> 1;
            temp.left = construct(ar, l, mid);
            temp.right = construct(ar, mid + 1, r);
            temp.data = operation(temp.left.data, temp.right.data);
            return temp;
        }

        /**
         * Write your own operation while using it. It can be Math.min, max or add
         *
         * @param a
         * @param b
         * @return
         */
        private static int operation(int a, int b) {
            return a + b;
        }

        /**
         * Update function, for the existing data a and update query data b,
         * returns the new value for existing data. This can be replacement,
         * addition, subtraction, multiplication or anything desired.
         *
         * @param a old value
         * @param b value to be updated on the node.
         * @return new value for the node.
         */
        private static int updateOperation(int a, int b) {
            return b;
        }

        final static class Node {
            Node left, right;
            int l, r;
            int data;

            Node(int m, int n) {
                l = m;
                r = n;
            }

            Node(int m, int n, int d) {
                l = m;
                r = n;
                data = d;
            }
        }
    }

    /**
     * This class implements AVL Trees.
     * Basically AVL trees are height balanced Binary Search Tree.
     * It stores height as extra parameter in each node.
     * For each height imbalance it uses some techniques like Left Rotation,
     * Right Rotation etc.
     * For AVL Binary Trees Basic Operations INSERT, DELETE, FIND are always
     * performed in constant O(1) time.
     *
     * @author Ashok Rajpurohit (ashok1113@gmail.com)
     */
    private static final class BinarySearchTree {
        Node root;
        int size = 0;

        public BinarySearchTree() {
        }

        public BinarySearchTree(int[] ar) {
            add(ar);
        }

        public void add(int n) {
            size++;
            if (root == null) {
                root = new Node(n);
                return;
            }
            root = add(root, n);
        }

        private static Node add(Node root, int n) {
            if (root == null) {
                root = new Node(n);
                return root;
            }

            if (root.data < n) {
                root.right = add(root.right, n);
                if (root.right.height - getHeight(root.left) == 2) {
                    if (root.right.data < n)
                        root = RotateRR(root);
                    else
                        root = RotateRL(root);
                }
                root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
                return root;
            }

            root.left = add(root.left, n);
            if (root.left.height - getHeight(root.right) == 2) {
                if (root.left.data < n)
                    root = RotateLR(root);
                else
                    root = RotateLL(root);
            }

            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            return root;
        }

        private static Node findMin(Node node) {
            if (node == null) return node;
            while (node.left != null)
                node = node.left;

            return node;
        }

        private static Node findMax(Node node) {
            if (node == null) return node;
            while (node.right != null)
                node = node.right;

            return node;
        }

        public void remove(int n) {
            root = delete(root, n);
        }

        public void update(int oldVal, int newVal) {
            if (oldVal == newVal)
                return;

            root = delete(root, oldVal);
            root = add(root, newVal);
        }

        private static Node delete(Node node, int n) {
            if (node == null)
                return node;

            if (node.data == n) {
                if (getHeight(node.left) >= getHeight(node.right)) {
                    Node del = findMax(node.left);
                    if (del == null) return del;

                    node.data = del.data;
                    node.left = delete(node.left, node.data);
                    if (getHeight(node.left) - getHeight(node.right) == -2) {
                        if (getHeight(node.right.left) >= getHeight(node.right.right))
                            node = RotateRL(node);
                        else
                            node = RotateRR(node);
                    }

                } else {
                    Node del = findMin(node.right);
                    node.data = del.data;
                    node.right = delete(node.right, node.data);
                    if (getHeight(node.left) - getHeight(node.right) == 2) {
                        if (getHeight(node.left.left) >= getHeight(node.left.right))
                            node = RotateLL(node);
                        else
                            node = RotateLR(node);
                    }
                }
            } else if (node.data < n) {
                node.right = delete(node.right, n);

                if (getHeight(node.left) - getHeight(node.right) == 2) {
                    if (getHeight(node.left.left) > getHeight(node.left.right))
                        node = RotateLL(node);
                    else
                        node = RotateLR(node);
                } else if (getHeight(node.left) - getHeight(node.right) == -2) {
                    if (getHeight(node.right.right) > getHeight(node.right.left))
                        node = RotateRR(node);
                    else
                        node = RotateRL(node);
                }
            } else {
                node.left = delete(node.left, n);
                if (getHeight(node.left) - getHeight(node.right) == 2) {
                    if (getHeight(node.left.left) > getHeight(node.left.right))
                        node = RotateLL(node);
                    else
                        node = RotateLR(node);
                } else if (getHeight(node.left) - getHeight(node.right) == -2) {
                    if (getHeight(node.right.right) > getHeight(node.right.left))
                        node = RotateRR(node);
                    else
                        node = RotateRL(node);
                }
            }

            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            return node;
        }

        private static Node RotateLL(Node root) {
            Node left = root.left;
            root.left = left.right;
            left.right = root;
            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            left.height = Math.max(getHeight(left.left), root.height) + 1;
            return left;
        }

        private static Node RotateLR(Node root) {
            root.left = RotateRR(root.left);
            return RotateLL(root);
        }

        private static Node RotateRL(Node root) {
            root.right = RotateLL(root.right);
            return RotateRR(root);
        }

        private static Node RotateRR(Node root) {
            Node right = root.right;
            root.right = right.left;
            right.left = root;
            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            right.height = Math.max(root.height, getHeight(right.right)) + 1;
            return right;
        }

        private static int getHeight(Node root) {
            return root == null ? 0 : root.height;
        }

        public boolean contains(int n) {
            Node temp = root;
            while (true) {
                if (n == temp.data)
                    return true;
                if (n > temp.data) {
                    if (temp.right == null)
                        return false;
                    temp = temp.right;
                } else {
                    if (temp.left == null)
                        return false;
                    temp = temp.left;
                }
            }
        }

        public void collect(int from, int to, CustomArray customArray) {
            collect(root, from, to, customArray);
        }

        private static void collect(Node node, int from, int to, CustomArray customArray) {
            if (node == null || node.data < from || node.data > to) return;

            collect(node.left, from, to, customArray);
            customArray.add(node.data);
            collect(node.right, from, to, customArray);
        }

        public void add(int[] ar) {
            for (int e : ar)
                add(e);
        }

        final static class Node {
            Node left, right;
            int data, height = 1;

            Node(int i) {
                data = i;
            }
        }
    }

    private final static class Counter implements Comparable<Counter> {
        int count;

        Counter() {
            this(1);
        }

        Counter(int value) {
            count = value;
        }

        void increment() {
            count++;
        }

        void increment(int value) {
            count += value;
        }

        void reset() {
            count = 0;
        }

        @Override
        public int compareTo(Counter counter) {
            return count - counter.count;
        }
    }

    final static class CustomArray {
        final int[] array;
        final int capacity;
        private int index;

        CustomArray(int capacity) {
            array = new int[capacity];
            this.capacity = capacity;
        }

        void add(int v) {
            array[index++] = v;
        }

        public boolean isEmpty() {
            return index == 0;
        }

        public void reset() {
            index = 0;
        }
    }

    /**
     * This is implementation of Red-Black Tree as explained in Introduction
     * to Algorithms by CLRS. Please refer the same for more details.
     * All the methods here are implementation of psuedo code from "Introduction to
     * Algorithms" by Cormen.
     *
     * @author Ashok Rajpurohit (ashok1113@gmail.com)
     * @see BSTAVL
     * @see BSTbyNode
     * @see RankTree
     */
    private final static class RedBlackTree {
        private final static Node NIL = new Node(0);
        private final static boolean RED = true, BLACK = false;
        private int size = 0;
        private Node root;

        static {
            NIL.color = BLACK;
        }

        public RedBlackTree() {

        }

        public RedBlackTree(int t) {
            size = 1;
            root = new Node(t);
            root.color = BLACK;
        }

        public RedBlackTree(int[] ar) {
            this(ar[0]);

            for (int i = 1; i < ar.length; i++)
                add(ar[i]);
        }

        public int size() {
            return size;
        }

        /**
         * Adds the specified element to this tree.
         *
         * @param value element to be added to this tree
         */
        public void add(int value) {
            if (root == null) {
                root = new Node(value);
                root.color = BLACK;
                size++;
                return;
            }

            Node node = insert(value);
            insertFix(node);
        }

        private Node insert(int value) {
            size++;
            Node temp = root;

            while (true) {
                if (temp.key > value) {
                    if (temp.left == NIL) {
                        temp.left = new Node(value);
                        temp.left.parent = temp;
                        return temp.left;
                    }
                    temp = temp.left;
                } else {
                    if (temp.right == NIL) {
                        temp.right = new Node(value);
                        temp.right.parent = temp;
                        return temp.right;
                    }
                    temp = temp.right;
                }
            }
        }

        /**
         * Restructures the tree in case if any property is violated during
         * element addition.
         *
         * @param node
         */
        private void insertFix(Node node) {
            while (node.parent.color == RED) {
                if (node.parent == node.parent.parent.left) {
                    Node tau = node.parent.parent.right;

                    if (tau.color == RED) {
                        node.parent.color = BLACK;
                        tau.color = BLACK;
                        node.parent.parent.color = RED;
                        node = node.parent.parent;
                    } else {
                        if (node == node.parent.right) {
                            node = node.parent;
                            rotateLeft(node);
                        }
                        node.parent.color = BLACK;
                        node.parent.parent.color = RED;
                        rotateRight(node.parent.parent);
                    }
                } else {
                    Node chacha = node.parent.parent.left;

                    if (chacha.color == RED) {
                        node.parent.color = BLACK;
                        chacha.color = BLACK;
                        node.parent.parent.color = RED;
                        node = node.parent.parent;
                    } else {
                        if (node == node.parent.right) {
                            node = node.parent;
                            rotateLeft(node);
                        }
                        node.parent.color = BLACK;
                        node.parent.parent.color = RED;
                        rotateRight(node.parent.parent);
                    }
                }
            }
            root.color = BLACK;
        }

        /**
         * Returns <tt>true</tt> if this set contains the specified element.
         *
         * @param value element whose presence in this tree is to be tested
         * @return <tt>true</tt> if this tree contains the specified element
         */
        public boolean contains(int value) {
            return find(value) != NIL;
        }

        public int successor(int value) {
            Node node = find(value);
            if (node == NIL)
                throw new RuntimeException("No such element exists: " + value);

            if (node.right != NIL)
                return findMin(node.right).key;

            while (node.parent != NIL && node == node.parent.right)
                node = node.parent;

            if (node.parent != NIL)
                return node.parent.key;

            throw new RuntimeException("No successor exists for " + value);
        }

        public int predecessor(int value) {
            Node node = find(value);
            if (node == NIL)
                throw new RuntimeException("No such element exists: " + value);

            if (node.left != NIL)
                return findMax(node.left).key;

            while (node.parent != NIL && node == node.parent.left)
                node = node.parent;

            if (node.parent != NIL)
                return findMin(node.parent).key;

            throw new RuntimeException("No predecessor exists for " + value);
        }

        private void rotateLeft(Node node) {
            if (node.right == NIL)
                return;

            Node right = node.right;
            node.right = right.left;
            right.left.parent = node;
            right.parent = node.parent;

            if (node.parent == NIL)
                root = right;
            else {
                if (node == node.parent.left)
                    node.parent.left = right;
                else
                    node.parent.right = right;
            }

            right.left = node;
            node.parent = right;
        }

        private void rotateRight(Node node) {
            if (node.left == NIL)
                return;

            Node left = node.left;
            node.left = left.right;
            left.right.parent = node;
            left.parent = node.parent;

            if (node.parent == NIL)
                root = left;
            else {
                if (node == node.parent.left)
                    node.parent.left = left;
                else
                    node.parent.right = left;
            }

            left.right = node;
            node.parent = left;
        }

        public void remove(int value) {
            if (size == 0)
                return;

            Node node = find(value);

            if (node != NIL)
                delete(node);
        }

        public void update(int oldValue, int newValue) {
            if (size == 0 || oldValue == newValue)
                return;

            Node node = find(oldValue);
            if (node == NIL)
                throw new RuntimeException("key does not exists");

            Node end = NIL;

            if (node.left != NIL)
                end = findMax(node.left);
            else if (node.right != NIL)
                end = findMin(node.right);
            else {
                node.key = newValue;
                return;
            }

            node.key = end.key;
            delete(end);
            insert(newValue);
        }

        private void delete(Node node) {
            --size;
            Node copy = node, fixUp = NIL;
            boolean copy_red = copy.color;

            if (node.left == NIL) {
                fixUp = node.right;
                replace(node, node.right);
            } else if (node.right == NIL) {
                fixUp = node.left;
                replace(node, node.left);
            } else {
                copy = findMin(node.right);
                copy_red = copy.color;
                fixUp = copy.right;

                if (copy.parent == node)
                    fixUp.parent = copy;
                else {
                    replace(copy, copy.right);
                    copy.right = node.right;
                    copy.right.parent = copy;
                }

                replace(node, copy);
                copy.left = node.left;
                copy.left.parent = copy;
                copy.color = node.color;
            }

            if (!copy_red)
                deleteFix(fixUp);
        }

        private Node findMin(Node node) {
            while (node.left != NIL)
                node = node.left;

            return node;
        }

        private Node findMax(Node node) {
            while (node.right != NIL)
                node = node.right;

            return node;
        }

        private void deleteFix(Node node) {
            while (node != root && node.color == BLACK) {
                if (node == node.parent.left) {
                    Node temp = node.parent.right;
                    if (temp.color == RED) {
                        temp.color = BLACK;
                        node.parent.color = RED;
                        rotateLeft(node.parent);
                        temp = node.parent.right;
                    }

                    if (temp.left.color == BLACK && temp.right.color == BLACK) {
                        temp.color = RED;
                        node = node.parent;
                    } else {
                        if (temp.right.color == BLACK) {
                            temp.left.color = BLACK;
                            temp.color = RED;
                            rotateRight(temp);
                            temp = node.parent.right;
                        }

                        temp.color = node.parent.color;
                        node.parent.color = BLACK;
                        temp.right.color = BLACK;
                        rotateLeft(node.parent);
                        node = root;
                    }
                } else {
                    Node temp = node.parent.left;
                    if (temp.color == RED) {
                        temp.color = BLACK;
                        node.parent.color = RED;
                        rotateRight(node.parent);
                        temp = node.parent.left;
                    }

                    if (temp.left.color == BLACK && temp.right.color == BLACK) {
                        temp.color = RED;
                        node = node.parent;
                    } else {
                        if (temp.left.color == BLACK) {
                            temp.right.color = BLACK;
                            temp.color = RED;
                            rotateLeft(temp);
                            temp = node.parent.left;
                        }

                        temp.color = node.parent.color;
                        node.parent.color = BLACK;
                        temp.left.color = BLACK;
                        rotateRight(node.parent);
                        node = root;
                    }
                }
            }
            node.color = BLACK;
        }

        private void replace(Node u, Node v) {
            if (u.parent == NIL)
                root = v;
            else if (u == u.parent.left)
                u.parent.left = v;
            else
                u.parent.right = v;

            v.parent = u.parent;
        }

        private Node find(int value) {
            Node temp = root;

            while (temp != NIL && temp.key != value) {
                if (temp.key > value)
                    temp = temp.left;
                else
                    temp = temp.right;
            }
            return temp;
        }

        public void collect(int from, int to, CustomArray customArray) {
            collect(root, from, to, customArray);
        }

        private static void collect(Node node, int from, int to, CustomArray customArray) {
            if (node == null || node.key < from || node.key > to) return;

            collect(node.left, from, to, customArray);
            customArray.add(node.key);
            collect(node.right, from, to, customArray);
        }

        final static class Node {
            int key;
            boolean color = RED;
            Node left = NIL, right = NIL, parent = NIL;

            Node(int value) {
                key = value;
            }
        }
    }

}