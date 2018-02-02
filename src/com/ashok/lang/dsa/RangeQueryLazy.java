package com.ashok.lang.dsa;

/**
 * Implementation of Range Query Lazy Updation.
 * This kind of Range query is useful when the update is for an index range.
 * This is to find the minimum element in the range and the update function is
 * incrementing all the elements by parameter in the range.
 * If the upate is always for single element then use {@link RangeQueryUpdate}.
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */
public class RangeQueryLazy {
    private Node root;

    public RangeQueryLazy(int[] ar) {
        construct(ar);
    }

    public void update(int l, int r, int data) {
        update(root, l, r, data);
    }

    public long query(int L, int R) {
        return query(root, L, R);
    }

    /**
     * Updates the node and child nodes if necessary.
     *
     * @param root
     * @param L    start index
     * @param R    end index
     * @param data to be added to each element from index L to index R.
     */
    private static void update(Node root, int L, int R, long data) {
        if (data == 0)
            return;

        if (root.l == L && root.r == R) {
            root.udata += data;
            root.data += data;
            return;
        }
        int mid = (root.l + root.r) >>> 1;
        update(root.right, mid + 1, root.r, root.udata);
        update(root.left, root.l, mid, root.udata);
        root.udata = 0;

        if (L > mid) {
            update(root.right, L, R, data);
            root.data = operation(root.left.data, root.right.data);
            return;
        }

        if (R <= mid) {
            update(root.left, L, R, data);
            root.data = operation(root.left.data, root.right.data);
            return;
        }

        update(root.left, L, mid, data);
        update(root.right, mid + 1, R, data);
        root.data = operation(root.left.data, root.right.data);
    }

    private static long query(Node root, int L, int R) {
        if (root.l == L && root.r == R)
            return root.data;

        int mid = (root.l + root.r) >>> 1;
        update(root.right, mid + 1, root.r, root.udata);
        update(root.left, root.l, mid, root.udata);
        root.udata = 0;

        if (L > mid)
            return query(root.right, L, R);

        if (R <= mid)
            return query(root.left, L, R);

        return operation(query(root.left, L, mid), query(root.right, mid + 1, R));

    }

    private void construct(int[] ar) {
        root = new Node(0, ar.length - 1, 0);
        int mid = (ar.length - 1) >>> 1;
        root.left = construct(ar, 0, mid);
        root.right = construct(ar, mid + 1, ar.length - 1);
        root.data = operation(root.left.data, root.right.data);
    }

    private Node construct(int[] ar, int l, int r) {
        if (l == r)
            return new Node(l, l, ar[l]);

        Node temp = new Node(l, r, 0);
        int mid = (l + r) >>> 1;
        temp.left = construct(ar, l, mid);
        temp.right = construct(ar, mid + 1, r);
        temp.data = operation(temp.left.data, temp.right.data);
        return temp;
    }

    private static long operation(long a, long b) {
        return a > b ? b : a;
    }

    private final static class Node {
        Node left, right;
        int l, r;
        long data, udata;
        //        boolean update = false;

        Node(int i, int j, int d) {
            l = i;
            r = j;
            data = d;
        }
    }
}
