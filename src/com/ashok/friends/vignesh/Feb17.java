/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.vignesh;

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
public class Feb17 {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Feb17 a = new Feb17();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        int n = in.readInt(), m = in.readInt(), x = in.readInt();
        int[] smartCities = in.readIntArray(m);
        Edge[] edges = new Edge[x];

        for (int i = 0; i < x; i++)
            edges[i] = new Edge(in.readInt(), in.readInt());

        out.println(SmartCities.solve(n, smartCities, edges));
    }

    final static class SmartCities {
        public static long solve(int cities, int[] smartCities, Edge[] edges) {
            boolean[] smartCityMap = new boolean[cities];

            for (int e : smartCities)
                smartCityMap[e - 1] = true;

            LinkedList<Integer>[] cityGroups = getCityGroups(cities, edges);
            boolean[] checked = new boolean[cities];
            long res = 0;

            for (int i = 0; i < cityGroups.length; i++) {
                int smartCityCount = 0, normalCity = 0;

                for (int e : cityGroups[i]) {
                    if (smartCityMap[e]) {
                        if (!checked[e])
                            smartCityCount++;

                        checked[e] = true;
                    }
                }

                for (int e : cityGroups[i]) {
                    if (!checked[e])
                        normalCity++;

                    checked[e] = true;
                }

                res += (normalCity + smartCityCount) * smartCityCount;
            }

            return res;
        }

        private static LinkedList<Integer>[] getCityGroups(int cities, Edge[] edges) {
            LinkedList<Integer>[] adjacencyMatrix = getAdjacencyMatrix(cities, edges);
            LinkedList<LinkedList<Integer>> cityGroups = new LinkedList<>();
            int[] cityGroupMap = new int[cities];
            Arrays.fill(cityGroupMap, -1);
            int groupCounter = 0;
            boolean[] validation = new boolean[cities];

            for (int i = 0; i < cities; i++) {
                if (cityGroupMap[i] == -1) {
                    LinkedList<Integer> groupCities = getConnectedCities(i, adjacencyMatrix, validation);
                    for (int city : groupCities)
                        cityGroupMap[city] = groupCounter;

                    cityGroups.add(groupCities);
                    groupCounter++;
                }
            }

            LinkedList<Integer>[] cityGroupList = new LinkedList[groupCounter];
            int index = 0;

            for (LinkedList<Integer> cityGroup : cityGroups)
                cityGroupList[index++] = cityGroup;

            return cityGroupList;
        }

        private static LinkedList<Integer> getConnectedCities(int city, LinkedList<Integer>[] matrix, boolean[] validation) {
            LinkedList<Integer> list = new LinkedList<>(), queue = new LinkedList<>();

            list.add(city);
            queue.add(city);
            validation[city] = true;

            while (!queue.isEmpty()) {
                int city1 = queue.removeFirst();

                for (int e : matrix[city1]) {
                    if (!validation[e]) {
                        validation[e] = true;
                        queue.addLast(e);
                        list.add(e);
                    }
                }
            }

            return list;
        }

        private static LinkedList<Integer>[] getAdjacencyMatrix(int cities, Edge[] edges) {
            LinkedList<Integer>[] matrix = new LinkedList[cities];
            for (int i = 0; i < cities; i++)
                matrix[i] = new LinkedList<>();

            for (Edge e : edges) {
                matrix[e.from - 1].add(e.to - 1);
                matrix[e.to - 1].add(e.from - 1);
            }

            return matrix;
        }
    }

    final static class Edge {
        final int from, to;

        Edge(int s, int e) {
            from = s;
            to = e;
        }
    }

    final static class StudentsInClass {
        public static int process(int[] students, int rows, int capacity) {
            int[] rowCapacity = new int[rows];
            Arrays.fill(rowCapacity, capacity);
            int count = 0;

            for (int row : students) {
                row--;

                if (rowCapacity[row] == 0) {
                    count++;
                    row++;
                    for (int j = 1; j < rows; j++, row++) {
                        if (row == rows)
                            row = 0;

                        if (rowCapacity[row] > 0) {
                            rowCapacity[row]--;
                            break;
                        }
                    }
                } else {
                    rowCapacity[row]--;
                }
            }

            return count;
        }
    }
}
