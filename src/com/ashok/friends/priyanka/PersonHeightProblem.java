/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.priyanka;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class PersonHeightProblem {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        PersonHeightProblem a = new PersonHeightProblem();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            int[] heights = in.readIntArray(n), positions = in.readIntArray(n);
            out.print(process(heights, positions));
            out.flush();
        }
    }

    private static int[] process(int[] heights, int[] positions) {
        Pair[] pairs = getPairs(heights, positions);
        Arrays.sort(pairs, (a, b) -> b.height - a.height);
        LinkedList<Pair> list = new LinkedList<>();
        for (Pair pair : pairs) {
            list.add(pair.position, pair);
        }

        return Arrays.stream(list.toArray()).mapToInt((t) -> ((Pair) t).height).toArray();
    }

    private static Pair[] getPairs(int[] heights, int[] positions) {
        int n = heights.length;
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++)
            pairs[i] = new Pair(heights[i], positions[i]);

        return pairs;
    }

    final static class Pair {
        final int height, position;

        Pair(int h, int p) {
            height = h;
            position = p;
        }

        @Override
        public String toString() {
            return "[" + height + ", " + position + "]";
        }
    }
}
