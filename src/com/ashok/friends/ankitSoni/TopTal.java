/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankitSoni;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Ankit Soni
 * Link: Private Link
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TopTal {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        TopTal a = new TopTal();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    final static class ZigZagTree {
        public int solution(Tree tree) {
            TreeWrapper treeWrapper = new TreeWrapper(tree);
            return Math.max(treeWrapper.leftCount, treeWrapper.rightCount);
        }

        final static class TreeWrapper {
            TreeWrapper left, right;
            int leftCount = -1, rightCount = -1;

            TreeWrapper(Tree tree) {
                if (tree.l != null)
                    left = new TreeWrapper(tree.l);

                if (tree.r != null)
                    right = new TreeWrapper(tree.r);

                leftCount = left != null ? Math.max(left.leftCount, 1 + left.rightCount) : -1;
                rightCount = right != null ? Math.max(right.rightCount, 1 + right.leftCount) : -1;
            }
        }

        final static class Tree {
            public int x;
            public Tree l, r;
        }
    }
}
