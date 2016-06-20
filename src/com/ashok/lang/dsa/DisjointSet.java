package com.ashok.lang.dsa;

import java.util.HashMap;

public class DisjointSet<K> {
    private static final Node NIL = new Node();
    private static Node root;
    private int size = 1;
    private HashMap<K, Node> map = new HashMap<K, Node>();

    public DisjointSet(K key) {
        root = new Node(key);
        map.put(key, root);
    }

    public void addNode(K key) {
        size++;
        map.put(key, new Node(key, root));
    }

    public void merge(DisjointSet<K> dset) {
        dset.root.parent = this.root;
        this.size += dset.size;

        map.putAll(dset.map);
    }

    final static class Node<K> {
        Node parent;
        K value;

        Node(K k, Node p) {
            this.value = k;
            this.parent = p;
        }

        Node(K k) {
            this.value = k;
            this.parent = NIL;
        }

        Node() {
        }
    }
}
