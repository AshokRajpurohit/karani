/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.tower;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TowerResearch {
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
        while (true) {
            out.println(in.read());
            out.flush();
        }
    }

    public static int flowerBouquets(int p, int q, String s) {
        char[] flowers = s.toCharArray();
        int len = s.length();
        int[] profit = new int[len];
        for (int i = len - 2; i >= 0; i--) {
            int value = 0;
            if (type1(flowers, i))
                value = Math.max(p + getValue(profit, i + 3), getValue(profit, i + 1));

            if (type2(flowers, i))
                value = Math.max(value, q + getValue(profit, i + 2));

            profit[i] = Math.max(value, profit[i + 1]);
        }

        return Arrays.stream(profit).max().getAsInt();
    }

    private static boolean type1(char[] flowers, int index) {
        return index + 3 <= flowers.length && count(flowers, index, index + 3, '0') == 3;
    }

    private static boolean type2(char[] flowers, int index) {
        return index + 2 <= flowers.length && count(flowers, index, index + 2, '0') == 1;
    }

    private static int count(char[] ar, int from, int to, char value) {
        int count = 0;
        for (int i = from; i < to; i++) if (ar[i] == value) count++;
        return count;
    }

    private static int getValue(int[] ar, int index) {
        return index >= ar.length ? 0 : ar[index];
    }

    private static final String yes = "Yes", no = "No";

    public static String canReach(int x1, int y1, int x2, int y2) {
        return reachable(x1, y1, x2, y2) ? yes : no;
    }

    private static boolean reachable(int x1, int y1, int x2, int y2) {
        if (x1 > x2 || y1 > y2) return false;
        if (x1 == x2 && y1 == y2) return true;

        return reachable(x1 + y1, y1, x2, y2) || reachable(x1, x1 + y1, x2, y2);
    }

    public static int findBestPath(int n, int m, int totalTime, List<Integer> beauty, List<Integer> u, List<Integer> v, List<Integer> t) {
        Sight[] sights = beauty.stream().map(i -> new Sight(i)).toArray(size -> new Sight[size]);
        Iterator<Integer> uIter = u.iterator(), vIter = v.iterator(), tIter = t.iterator();
        while (uIter.hasNext()) {
            int from = uIter.next(), to = vIter.next(), time = tIter.next();
            Edge edge = new Edge(from, to, time);
            sights[from].connectedSights.add(edge);
            sights[to].connectedSights.add(edge);
        }

        doBFS(sights[0], totalTime, sights);
        return -1;
    }

    private static int doBFS(Sight start, int time, Sight[] sights) {
//        start.connectedSights.forEach(t -> t.connectedSights.remove(t));
        return 1;
    }

    final static class Sight {
        private static int sequence = 0;
        final int id = sequence++;
        final int beuty;
        private Sight prev;
        private int reachingTime;
        Set<Edge> connectedSights = new HashSet<>();

        Sight(int beuty) {
            this.beuty = beuty;
        }

        public int hashCode() {
            return id;
        }
    }

    final static class Edge {
        final int from, to;
        final int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int hashCode() {
            return from * to;
        }

        public boolean equals(Object o) {
            Edge edge = (Edge) o;
            return (edge.from == from && to == edge.to) || (from == edge.to && to == edge.from);
        }
    }
}
