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
import java.util.Comparator;
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
        Comparator<Pair> comparator = (a, b) -> a.position == b.position ? a.height - b.height : a.position - b.position;
        Arrays.sort(pairs, comparator);
        out.println(pairs);
        LinkedList<Pair> list = new LinkedList<>();
        for (Pair pair : pairs) {
            list.add(pair.position, pair);
        }

        int n = pairs.length;
        Pair[] order = new Pair[n];
        int[] nextEmptyIndexMap = new int[n], prevEmptyIndexMap = new int[n];
        for (int i = 0; i < n; i++) {
            nextEmptyIndexMap[i] = i + 1;
            prevEmptyIndexMap[i] = i - 1;
        }

        Arrays.sort(pairs, (a, b) -> a.height - b.height);
        for (Pair pair : pairs) {
            int index = pair.position;
            int prev = prevEmptyIndexMap[index];
            int next = nextEmptyIndexMap[index];
            if (order[index] != null) {
                index = nextEmptyIndexMap[index];
                next = nextEmptyIndexMap[index];
            }

            if (prev != -1)
                nextEmptyIndexMap[prev] = next;

            if (next != n)
                prevEmptyIndexMap[next] = prev;

            order[index] = pair;
        }

        out.println("Correct way");
        out.println(order);

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
