package com.ashok.lang.dsa;

/**
 * This class is for frequent updates of elements. It updates the element and
 * Structure in order of long(n). If updates are for in range then it's
 * better to use {@link RangeQueryLazy} Lazy propagation.
 */
public class RangeQueryUpdate {
    private Node root;

    public RangeQueryUpdate(int[] ar) {
        construct(ar);
    }

    public long query(int L, int R) {
        if (R < L)
            return query(R, L);

        if (R > root.r || L < 0)
            throw new IndexOutOfBoundsException(L + ", " + R);

        return query(root, L, R);
    }

    private long query(Node node, int L, int R) {
        if (node.l == L && node.r == R)
            return node.data;

        int mid = (node.l + node.r) >>> 1;
        if (L > mid)
            return query(node.right, L, R);

        if (R <= mid)
            return query(node.left, L, R);

        return operation(query(node.left, L, mid),
                         query(node.right, mid + 1, R));
    }

    public void update(int i, int data) {
        if (i > root.r || i < 0)
            throw new IndexOutOfBoundsException(i + "");

        update(root, i, data);
    }

    public void update(int L, int R, int data) {
        for (int i = L; i <= R; i++)
            update(root, i, data);
    }

    private void update(Node root, int i, int data) {
        if (root.l == root.r) {
            root.data += data;
            return;
        }

        int mid = (root.l + root.r) >>> 1;
        if (i > mid)
            update(root.right, i, data);
        else
            update(root.left, i, data);

        root.data = operation(root.left.data, root.right.data);
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
    private static long operation(long a, long b) {
        return a > b ? b : a;
    }

    final static class Node {
        Node left, right;
        int l, r;
        long data;

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
