package com.ashok.lang.dsa;

import java.util.*;
import java.util.List;
import java.util.Stack;

public class BSTbyNode {
    public BSTbyNode() {
        super();
    }

    public BSTbyNode(int[] ar) {
        add(ar);
    }

    Node root;
    int size = 0;

    public void add(int n) {
        size++;

        if (root == null) {
            root = new Node(n);
            return;
        }

        Node temp = root;
        while (true) {
            if (n > temp.data) {
                if (temp.right == null) {
                    temp.right = new Node(n);
                    return;
                }
                temp = temp.right;
            } else {
                if (temp.left == null) {
                    temp.left = new Node(n);
                    return;
                }
                temp = temp.left;
            }
        }
    }

    public boolean find(int n) {
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

    public void add(int[] ar) {
        for (int e : ar)
            add(e);
    }

    public int[] sort() {
        int[] ar = new int[size];
        sort(ar, root);
        return ar;
    }

    public List<Integer> collect(int from, int to) {
        List<Integer> list = new LinkedList<>();
        collect(root, from, to, list);
        return list;
    }

    private static void collect(Node node, int from, int to, List<Integer> list) {
        if (node == null || node.data < from || node.data > to) return;

        collect(node.left, from, to, list);
        list.add(node.data);
        collect(node.right, from, to, list);
    }

    private static void sort(int[] ar, Node node) {
        int index = 0;
        if (node.left != null)
            index = sort(ar, node.left, 0);
        ar[index] = node.data;
        if (node.right != null)
            sort(ar, node.right, index + 1);
    }

    private static int sort(int[] ar, Node node, int index) {
        if (node.left != null)
            index = sort(ar, node.left, index);

        ar[index] = node.data;
        index++;
        if (node.right != null)
            index = sort(ar, node.right, index);

        return index;
    }

    public String print() {
        StringBuilder sb = new StringBuilder(size << 3);
        print(sb, root);
        return sb.toString();
    }

    public String toString() {
        return print();
    }

    /**
     * kartecy: Ankit Agrawal aka Mr. P
     * slightly modified for my need.
     * @param ar
     * @param root
     */
    private static void inOrderTraversal(int[] ar, Node root) {
        int[] inOrder = ar;
        int index = 0;
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        Node temp = root;
        temp = root.left;
        while (!stack.empty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;
            }
            if (temp == null && !stack.empty()) {
                temp = stack.pop();
                inOrder[index] = temp.data;
                index++;
                temp = temp.right;
            }
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;
            }
        }
    }

    private static void print(StringBuilder sb, Node node) {
        if (node.left != null)
            print(sb, node.left);
        sb.append(", ").append(node.data);
        if (node.right != null)
            print(sb, node.right);
    }

    class Node {
        Node left, right;
        int data;

        Node(int i) {
            data = i;
        }
    }
}
