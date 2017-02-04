/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.facebook.hacker2017.round1;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Manic Moving
 * Link: https://www.facebook.com/hackercup/problem/300438463685411/
 * <p>
 * For full implementation please see {@link github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ManicMoving {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static final String CASE = "Case #";
    private static int[][] map;
    private static int[] cache1, cache2, cache3;
    private static final int INFINITE = 1000000; // it can't be more than n_max (100) * g_max(1000).

    public static void main(String[] args) throws IOException {
        String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\facebook\\hacker2017\\round1\\";
//        in = new InputReader(path + "ManicMoving.in");
//        out = new Output(path + "ManicMoving.out");
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            int n = in.readInt(), m = in.readInt(), k = in.readInt();
            map = new int[n][n];
            for (int[] ar : map)
                Arrays.fill(ar, INFINITE);

            for (int j = 0; j < m; j++) {
                int townA = in.readInt() - 1, townB = in.readInt() - 1, gas = in.readInt();
                map[townA][townB] = gas;
                map[townB][townA] = gas;
            }

            Pair[] pairs = new Pair[k];
            for (int j = 0; j < k; j++)
                pairs[j] = new Pair(in.readInt() - 1, in.readInt() - 1);

            if (m < k - 1)
                append(sb, i, -1);
            else
                append(sb, i, process(pairs));
        }

        out.print(sb);
    }

    private static int process(Pair[] pairs) {
        populateMatrix();
        if (impossible(pairs))
            return -1;

        Truck truck = new Truck();
        truck.currentLocation = pairs[0].first;
        int cost = truck.load(pairs[0]);
        cache1 = new int[pairs.length];
        cache2 = new int[pairs.length];
        cache3 = new int[pairs.length];
        Arrays.fill(cache1, INFINITE);
        Arrays.fill(cache2, INFINITE);
        Arrays.fill(cache3, INFINITE);

        return cost + process(pairs, truck, 1);
    }

    private static boolean impossible(Pair[] deliveries) {
        for (Pair delivery : deliveries)
            if (map[delivery.first][delivery.second] == INFINITE)
                return true;

        return false;
    }

    private static int process(Pair[] pairs, Truck truck, int index) {
        truck = truck.clone();
        if (index == pairs.length)
            return truck.empty();

        if (cache1[index] != INFINITE)
            return cache1[index];

        Truck copy = truck.clone();
        int cost = Math.min(wayOne(pairs, truck, index), wayTwo(pairs, truck, index));
        cache1[index] = cost;

        return cost;
    }

    private static int wayOne(Pair[] pairs, Truck truck, int index) {
        truck = truck.clone();
        int cost = truck.unload();
        cost += truck.load(pairs[index++]);
        return cost + process(pairs, truck, index);
    }

    private static int wayTwo(Pair[] pairs, Truck truck, int index) {
        truck = truck.clone();
        if (cache2[index] != INFINITE)
            return cache2[index];

        int cost = truck.load(pairs[index++]);
        if (index == pairs.length)
            return cost + truck.empty();

        cost += truck.unload();
        Truck copy = truck.clone();
        int costA = truck.unload(), costB = 0;
        costA += truck.load(pairs[index]);
        costA = process(pairs, truck, index + 1);

        costB = copy.load(pairs[index]);
        costB = copy.unload();
        costB = process(pairs, copy, index + 1);

        cache2[index - 1] = cost + Math.min(costA, costB);
        return cache2[index - 1];
    }

    private static void populateMatrix() {
        int n = map.length;

        for (int i = 0; i < n; i++)
            populateMatrixForNode(i);
    }

    private static void populateMatrixForNode(int node) {
        LinkedList<Pair> queue = new LinkedList<>();
        Pair seperator = new Pair(-1, -1);

        queue.add(new Pair(node, 0));
        queue.add(seperator);

        while (queue.size() > 1) {

            Pair next = queue.removeFirst();
            while (next != seperator) {
                for (int j = 0; j < map.length; j++) {
                    if (j != node && map[node][j] > next.second + map[next.first][j]) {
                        map[node][j] = next.second + map[next.first][j];
                        queue.add(new Pair(j, map[node][j]));
                    }
                }

                next = queue.removeFirst();
            }
        }
    }

    final static class Truck {
        LinkedList<Pair> deliveries = new LinkedList<>();
        int currentLocation = 0;
        int count = 0;

        int load(Pair delivery) {
            count++;
            deliveries.add(delivery);
            currentLocation = delivery.first;

            return map[currentLocation][delivery.first];
        }

        int unload() {
            int cost = unloadCost();
            Pair delivery = deliveries.removeFirst();
            currentLocation = delivery.second;
            count--;
            return cost;
        }

        int unloadCost() {
            Pair deliveryDetail = deliveries.getFirst();
            return map[currentLocation][deliveryDetail.second];
        }

        int empty() {
            int cost = 0;
            while (count > 0) {
                cost += unload();
            }

            return cost;
        }

        public Truck clone() {
            Truck truck = new Truck();
            truck.currentLocation = currentLocation;
            truck.count = count;
            truck.deliveries.addAll(deliveries);

            return truck;
        }
    }

    final static class Pair {
        int first, second;

        Pair(int a, int b) {
            first = a;
            second = b;
        }

        public String toString() {
            return first + ", " + second;
        }
    }

    private static void append(StringBuilder sb, int test, int trips) {
        sb.append(CASE).append(test).append(": ").append(trips).append('\n');
    }
}
