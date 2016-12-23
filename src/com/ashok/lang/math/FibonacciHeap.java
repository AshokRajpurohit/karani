package com.ashok.lang.math;

import java.lang.reflect.Array;
import java.util.*;

/**
 *
 * This class is implementation of Fibonacci Heap.
 * For Reference please follow Fibonacci Heaps from Introduction
 * to Algorithms by CLRS.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FibonacciHeap<K, V> {
    private List rootList = null;
    private final Comparator<? super K> comparator;
    private int size = 0;
    private K MINUS_INFINITY;

    public FibonacciHeap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    public void add(K key, V value) {
        size++;

        if (rootList == null) {
            rootList = new List();
        }

        rootList.add(new Node(key, value));
    }

    private int compare(Node k1, Node k2) {
        return comparator.compare(k1.key, k2.key);
    }

    private void delete(Node node) {
        decreaseKey(node, MINUS_INFINITY);
        extractMin();
    }

    private void decreaseKey(Node node, K newKey) {
        if (comparator.compare(node.key, newKey) <= 0)
            throw new RuntimeException("Key should be smaller.");

        node.key = newKey;
        Node parent = node.parent;

        if (parent != null && comparator.compare(node.key, parent.key) < 0) {
            cut(node, parent);
            cascadingCut(parent);
        }

        if (comparator.compare(node.key, rootList.min.key) < 0)
            rootList.min = node;
    }

    private void cut(Node x, Node y) {
        y.child.delete(x);
        rootList.add(x);
        x.parent = null;
        x.mark = false;
    }

    private void cascadingCut(Node y) {
        Node p = y.parent;

        if (p != null) {
            if (!y.mark)
                y.mark = true;
            else {
                cut(y, p);
                cascadingCut(p);
            }
        }
    }

    /**
     * Removes the Entry associated with minimum key and returns the same.
     *
     * @return Entry associated with min key
     */
    public Map.Entry<K, V> extractMin() {
        Node ret = rootList.min;

        if (ret != null) {
            if (ret.child != null) {
                for (Node node : ret.child) {
                    rootList.add(node);
                }
                rootList.delete(ret);
                consolidate();
            }

            ret.child = null;
        }
        return ret;
    }

    /**
     * Resets the heap after min key is changed.
     *
     */
    private void consolidate() {
        HashMap<Integer, Node> map = new HashMap<Integer, Node>();
        boolean cont = true;

        while (cont) {
            cont = false;

            Iterator<Node> iter = rootList.iterator();

            while (iter.hasNext()) {
                Node node = iter.next();
                if (map.containsKey(degree(node))) {
                    Node old = map.get(degree(node));
                    if (old == node)
                        continue;

                    cont = true;
                    if (compare(old, node) <= 0) {
                        rootList.delete(node);
                        node.mark = false;
                        old.addChild(node);
                        map.remove(degree(node));
                        map.put(degree(old), old);
                    } else {
                        rootList.delete(old);
                        old.mark = false;
                        node.addChild(old);
                        map.remove(degree(old));
                        map.put(degree(node), node);
                    }
                }
            }
        }
    }

    /**
     * Returns the Entry associated with minimum key.
     *
     * @return entry associated with minimum key.
     */
    public Map.Entry<K, V> getMin() {
        return rootList.min;
    }

    /**
     * This will merge the parameter heap into this heap and remove elements
     * from the second one so no update can be done to the second heap.
     *
     * @param fibonacciHeap
     */
    public void merge(FibonacciHeap<K, V> fibonacciHeap) {
        if (rootList == null)
            rootList = fibonacciHeap.rootList;
        else
            rootList.merge(fibonacciHeap.rootList);

        size += fibonacciHeap.size;
        delete(fibonacciHeap);
    }

    /**
     * Removes the elements from the fibonacci heap to make it un-usable.
     * When we merge two heaps we don't want any operation performed on the
     * second heap affects the merger heap, So all the connections to old
     * elements has to wiped out.
     *
     * @param fibonacciHeap
     */
    private static void delete(FibonacciHeap fibonacciHeap) {
        fibonacciHeap.rootList = null;
        fibonacciHeap.size = 0;
    }

    private int degree(Node node) {
        if (node.child == null)
            return 0;

        return node.child.size;
    }

    /**
     * This method arranges the elements in increasing order of degree.
     * The head pointer in the list (min) is not changed.
     *
     * @param list
     */
    private void sortListByDegree(List list) {
        if (list == null || list.size < 2)
            return;

        Node[] nodes =
            (FibonacciHeap.Node[])Array.newInstance(rootList.min.getClass(),
                                                    list.size);
        int i = 0;

        for (Node node : list)
            nodes[i++] = node;

        Arrays.sort(nodes, NODE_COMPARATOR);

        for (i = 1; i < list.size; i++) {
            nodes[i].prev = nodes[i - 1];
            nodes[i - 1].next = nodes[i];
        }

        nodes[0].prev = nodes[list.size - 1];
        nodes[list.size - 1].next = nodes[0];
        list.min = nodes[0];
    }

    private final Comparator<Node> NODE_COMPARATOR = new NodeComparator();

    final class NodeComparator implements Comparator<Node> {

        public int compare(Node o1, Node o2) {
            int diff = degree(o1) - degree(o2);

            if (diff != 0)
                return diff;

            return o1.compareTo(o2);
        }
    }

    final class Node implements Map.Entry<K, V>, Comparable<Node> {
        K key;
        V value;
        Node next, prev;
        List child;
        boolean mark = false;
        Node parent;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            next = this;
            prev = this;
        }

        void add(Node node) {
            node.next = this.next;
            node.prev = this;
            this.next.prev = node;
            this.next = node;
        }

        void addChild(Node node) {
            if (child == null) {
                child = new List();
            }

            child.add(node);
            node.parent = this;
        }

        public boolean equals(Object o) {
            return this == o;
        }

        public int compareTo(Node node) {
            return compare(this, node);
        }

        public K getKey() {
            return this.key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key
         */
        public V getValue() {
            return this.value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         *         called
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    final class List implements Iterable<Node> {
        Node min = null;
        int size = 0;

        void add(Node node) {
            size++;

            if (min == null) {
                min = node;
                return;
            }

            min.add(node);
            if (compare(min, node) < 0)
                min = node;
        }

        /**
         * Deletes the specified node from the list. It is assumed that the
         * node exists in the list, So any checks to performe it's existance
         * are ignored currently.
         *
         * @param node
         */
        void delete(Node node) {
            if (size < 2) {
                min = null;
                size = 0;
                return;
            }

            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;

            if (node == min) {
                min = node.next;
                reset();
            }
        }

        /**
         * Resets the minimum key node for the list.
         *
         */
        void reset() {
            if (size < 2)
                return;

            Node iter = min.next, ref = min;
            while (iter != ref) {
                if (compare(iter, min) < 0)
                    min = iter;

                iter = iter.next;
            }
        }

        void merge(List list) {
            if (list == null)
                return;

            size += list.size;

            min.next.prev = list.min.prev;
            list.min.next = this.min.next;
            this.min.next = list.min;
            list.min.prev = this.min;

            if (compare(this.min, list.min) > 0)
                min = list.min;
        }

        public Iterator<Node> iterator() {
            return new ListIterator(min);
        }
    }

    final class ListIterator implements Iterator<Node> {
        Node head, iter;
        boolean moved = false;

        ListIterator(Node head) {
            iter = head;
            this.head = head;
        }

        public boolean hasNext() {
            return !(moved && iter == head);
        }

        public Node next() {
            Node cur = iter;
            iter = iter.next;

            moved = true;
            return cur;
        }
    }
}
