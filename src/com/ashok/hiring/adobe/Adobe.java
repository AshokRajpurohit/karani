/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.adobe;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Adobe {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        List<Integer> list = IntStream.range(1, 11).mapToObj(v -> v).collect(Collectors.toList());
        List<Integer> list1 = list.stream().filter(v -> v % 2 == 0).map(v -> v * 10).collect(Collectors.toList());
    }

    public static void test() {
        String s = "abcddecba";
        String t = "abcde";
    }

    public static boolean isMirrorImage(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null) return false;

        if (root1.val != root2.val) return false;

        return isMirrorImage(root1.left, root2.right) && isMirrorImage(root1.right, root2.left);
    }

    public static TreeNode leastAncestorNode(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == node1 || root == node2) return root;

        TreeNode p1 = leastAncestorNode(root.left, node1, node2);
        TreeNode p2 = leastAncestorNode(root.left, node1, node2);

        if (p1 != null && p2 != null) return root;
        return p1 != null ? p1 : p2 != null ? p2 : null;
    }

    private static boolean findPath(Deque<TreeNode> path, TreeNode root, TreeNode node) {
        if (root == null) return false;
        if (root == node) {
            path.add(root);
            return true;
        }

        path.addLast(root);
        if (findPath(path, root.left, node)) return true;
        if (findPath(path, root.right, node)) return true;

        path.removeLast();
        return false;
    }

    /**
     *              1
     *         2        3
     *        4  5     6  7
     *
     *
     *              1
     *          3       2
     *        7   6    5  4
     */

    final static class TreeNode {
        int val;
        TreeNode left, right;
    }
}
