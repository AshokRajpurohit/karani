package com.ashok.lang.dsa;

/**
 * Rank Tree class is to support index related queries in an array.
 * for each element we want to know how many elements are smaller than this
 * or greater than this. I am using AVL tree to height balance it and for
 * optimum performance.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class RankTree {
    public RankTree() {
        super();
    }

    public RankTree(int[] ar) {
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
            root.height =
                    Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            return root;
        }

        root.left = add(root.left, n);
        root.count++;
        if (root.left.height - getHeight(root.right) == 2) {
            if (root.left.data < n)
                root = RotateLR(root);
            else
                root = RotateLL(root);
        }
        root.height =
                Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        return root;
    }

    private static Node RotateLL(Node root) {
        Node left = root.left;
        root.left = left.right;
        left.right = root;
        root.count -= left.count - 1;

        root.height =
                Math.max(getHeight(root.left), getHeight(root.right)) + 1;
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
        right.count += root.count + 1;
        root.height =
                Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        right.height = Math.max(root.height, getHeight(right.right)) + 1;
        return right;
    }

    private static int getHeight(Node root) {
        if (root == null)
            return 0;
        return root.height;
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

    /**
     * This function returns how many elements smaller than or equal to
     * the parameter n are present(inserted previously) in the data structure.
     * If you want to know how many elements greater than n are present,
     * use size - getIndex(n)
     *
     * @param n
     * @return
     */
    public int getIndex(int n) {
        Node temp = root;
        int res = 0;
        while (true) {
            if (n == temp.data)
                return res + temp.count;
            if (n > temp.data) {
                if (temp.right == null)
                    return res + temp.count + 1;

                res += temp.count + 1;
                temp = temp.right;
            } else {
                if (temp.left == null)
                    return res + 1;
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

    private static void print(StringBuilder sb, Node node) {
        if (node.left != null)
            print(sb, node.left);
        sb.append(", ").append(node.data);
        if (node.right != null)
            print(sb, node.right);
    }

    /**
     * count in Node is the number of smaller elements than this current element.
     */
    final static class Node {
        Node left, right;
        int data, height = 1, count = 0;

        Node(int i) {
            data = i;
        }
    }
}
