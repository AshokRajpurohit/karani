package com.ashok.lang.dsa;

/**
 * This class is to process range based queries on two dimensional array (space).
 * This implementation does not support bulk updates (updating all elements in
 * a given range), Only single update per operation.
 * <p>
 * Point co-ordinates are represented in row and column number, starting from
 * 0. Top row is row 0 and first column (left-most) is column 0, unlike the
 * general two dimensional co-ordinate geometry where the x increases rightwards
 * and y increases upwards, here x increases downwards.
 * <p>
 * First quadrant is the top-left quadrant and others are clockwise to it,
 * like second quadrant is the top-right quadrant, fourth is bottom-left.
 * <p>
 * q1 | q2
 * -------
 * q4 | q3
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * @see SparseTable2D
 */
public class Quadtree {
    private static final int q1 = 0, q2 = 1, q3 = 2, q4 = 3;
    private final static Node INVALID = new Node(-1, -1, -1, -1);
    private int n, m;
    private int[][] space;
    private Node root;

    public Quadtree(int[][] table) {
        n = table.length;
        m = table[0].length;

        space = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                space[i][j] = table[i][j];
            }
        }

        populate();
    }

    public long query(int sr, int er, int sc, int ec) {
        // when it's query for single element why waste processing power.
        if (sr == er && sc == ec)
            return space[sr][sc];

        return query(root, sr, er, sc, ec);
    }

    private static long query(Node node, int sr, int er, int sc, int ec) {
        if (!rangeOverlap(node, sr, er, sc, ec))
            return 0;

        if (node.startRow == sr && node.endRow == er && node.startCol == sc && node.endCol == ec)
            return node.value;

        long v1 = 0, v2 = 0, v3 = 0, v4 = 0;

        if (sr <= node.midRow && sc <= node.midCol) {
            v1 = query(node.first, sr, Math.min(er, node.midRow), sc, Math.min(ec, node.midCol));
        }

        if (sr <= node.midRow && ec > node.midCol) {
            v2 = query(node.second, sr, Math.min(er, node.midRow), Math.max(sc, node.midCol + 1), ec);
        }

        if (er > node.midRow && ec > node.midCol) {
            v3 = query(node.third, Math.max(sr, node.midRow + 1), er, Math.max(sc, node.midCol + 1), ec);
        }

        if (er > node.midRow && sc <= node.midCol) {
            v4 = query(node.fourth, Math.max(sr, node.midRow + 1), er, sc, Math.min(ec, node.midCol));
        }

        return operation(v1, v2, v3, v4);
    }

    private static boolean rangeOverlap(Node node, int sr, int er, int sc, int ec) {
        boolean result = contains(node, sr, sc) || contains(node, sr, ec)
                || contains(node, er, sc) || contains(node, er, ec);

        if (result)
            return true;

        return contains(sr, sc, er, ec, node);
    }

    private static boolean contains(int sr, int sc, int er, int ec, Node node) {
        return contains(sr, sc, er, ec, node.startRow, node.startCol) ||
                contains(sr, sc, er, ec, node.startRow, node.endCol) ||
                contains(sr, sc, er, ec, node.endRow, node.startCol) ||
                contains(sr, sc, er, ec, node.endRow, node.endCol);
    }

    private static boolean contains(int sr, int sc, int er, int ec, int x, int y) {
        return x >= sr && x <= er && y >= sc && y <= ec;
    }

    private static boolean contains(Node node, int x, int y) {
        return x >= node.startRow && x <= node.endRow && y >= node.startCol && y <= node.endCol;
    }

    public void update(int x, int y, int value) {
        if (space[x][y] == value)
            return;

        update(root, x, y, value);
        space[x][y] = value;
    }

    private static void update(Node node, int x, int y, int value) {
        validatePoint(node, x, y);

        if (isSingleCell(node)) {
            node.value = value;
            return;
        }

        int quad = quadrant(node, x, y);

        if (quad == q1)
            update(node.first, x, y, value);
        else if (quad == q2)
            update(node.second, x, y, value);
        else if (quad == q3)
            update(node.third, x, y, value);
        else
            update(node.fourth, x, y, value);

        node.value =
                operation(node.first.value, node.second.value, node.third.value,
                        node.fourth.value);
    }

    private static void validatePoint(Node node, int x, int y) {
        if (x < node.startRow || x > node.endRow || y < node.startCol || y > node.endCol)
            throw new RuntimeException("Invalid Co-ordinates, You deserve this exception");

        return;
    }

    private void populate() {
        root = getNode(0, n - 1, 0, m - 1);
        populate(root);
    }

    private void populate(Node node) {
        if (node == INVALID)
            return;

        if (isSingleCell(node)) {
            node.value = space[node.startRow][node.startCol];
            return;
        }

        node.first = getNode(node.startRow, node.midRow, node.startCol, node.midCol);
        node.second = getNode(node.startRow, node.midRow, node.midCol + 1, node.endCol);
        node.third = getNode(node.midRow + 1, node.endRow, node.midCol + 1, node.endCol);
        node.fourth = getNode(node.midRow + 1, node.endRow, node.startCol, node.midCol);

        populate(node.first);
        populate(node.second);
        populate(node.third);
        populate(node.fourth);

        node.value =
                operation(getValue(node.first), getValue(node.second), getValue(node.third),
                        getValue(node.fourth));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(n * m * 4);

        for (int[] ar : space) {
            for (int e : ar)
                sb.append(e).append(' ');

            sb.append('\n');
        }

        return sb.toString();
    }

    private static long getValue(Node node) {
        return node.value;
    }

    private static boolean isSingleCell(Node node) {
        return node.startRow == node.endRow && node.startCol == node.endCol;
    }

    /**
     * Returns the quadrant for the point in the space represented by node.
     *
     * @param node
     * @param x
     * @param y
     * @return quadrant the point (x, y) is in.
     */
    private static int quadrant(Node node, int x, int y) {
        if (x <= node.midRow) {
            if (y <= node.midCol)
                return q1;

            return q2;
        }

        if (y <= node.midCol)
            return q4;

        return q3;
    }

    /**
     * let's keep the operation seperate So it's easy to change it for different
     * type of problems, like finding maximum element or sum of elements of
     * or multiplication of elements.
     *
     * @param a
     * @param b
     * @return
     */
    private static long operation(long a, long b, long c, long d) {
        return a + b + c + d;
    }

    private static Node getNode(int sr, int er, int sc, int ec) {
        if (er < sr || ec < sc)
            return INVALID;

        return new Node(sr, er, sc, ec);
    }

    final static class Node {
        long value = 0;
        final int startRow, endRow, startCol, endCol;
        final int midRow, midCol;

        Node first, second, third, fourth; // quadrants in 2D space.

        Node(int sr, int er, int sc, int ec) {
            startRow = sr;
            endRow = er;
            startCol = sc;
            endCol = ec;
            midRow = (sr + er) >>> 1;
            midCol = (sc + ec) >>> 1;
        }
    }
}
