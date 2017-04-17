/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.april;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Bear and Clique Distances
 * Link: https://www.codechef.com/APRIL17/problems/CLIQUED
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BearAndCliqueDistances {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;

            int n = in.readInt(), k = in.readInt(), x = in.readInt(),
                    m = in.readInt(), s = in.readInt();

            LinkedList<Road>[] adjacencyList = new LinkedList[n];
            for (int i = 0; i < n; i++)
                adjacencyList[i] = new LinkedList<>();

            for (int i = 0; i < m; i++)
                addPath(adjacencyList, in.readInt() - 1, in.readInt() - 1, in.readInt());

            out.println(process(adjacencyList, k, s - 1, x));
        }
    }

    private static String process(LinkedList<Road>[] roads, int oldCityCount, int start, int oldRoadLength) {
        long[] pathMap = new long[roads.length];
        Arrays.fill(pathMap, 10000000000000000L);
        pathMap[start] = 0;

        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] queueMap = new boolean[roads.length];

        queue.addLast(start);
        queueMap[start] = true;
        populatePathMapBFS(queueMap, roads, pathMap, queue);

        long min = getMinValue(pathMap, oldCityCount);
        updatePathMap(pathMap, oldCityCount, min + oldRoadLength);
        Arrays.fill(queueMap, false);
        queue.clear();

        for (int i = 0; i < oldCityCount; i++) {
            queueMap[i] = true;
            queue.addLast(i);
        }

        populatePathMapBFS(queueMap, roads, pathMap, queue);
        return toString(pathMap);
    }

    private static void populatePathMapBFS(boolean[] queueMap, LinkedList<Road>[] roads, long[] pathMap, LinkedList<Integer> queue) {
        while (!queue.isEmpty()) {
            int city = queue.removeFirst();
            queueMap[city] = false;

            for (Road road : roads[city]) {
                int connectedCity = road.city1 + road.city2 - city;

                if (pathMap[connectedCity] >= pathMap[city] + road.length) { // TODO: be careful.
                    pathMap[connectedCity] = pathMap[city] + road.length;
                    if (!queueMap[connectedCity])
                        queue.addLast(connectedCity);

                    queueMap[connectedCity] = true;
                }
            }
        }
    }

    private static long getMinValue(long[] ar, int count) {
        long min = Long.MAX_VALUE >>> 1;

        for (int i = 0; i < count; i++)
            min = Math.min(min, ar[i]);

        return min;
    }

    private static void updatePathMap(long[] ar, int count, long value) {
        for (int i = 0; i < count; i++)
            ar[i] = Math.min(ar[i], value);
    }

    private static String toString(long[] ar) {
        StringBuilder sb = new StringBuilder(ar.length * 9);

        for (long e : ar)
            sb.append(e).append(' ');

        return sb.toString();
    }

    private static void addPath(LinkedList<Road>[] adjacencyList, int city1, int city2, int length) {
        ensureInitialized(adjacencyList, city1);
        ensureInitialized(adjacencyList, city2);

        Road road = new Road(city1, city2, length);

        adjacencyList[city1].add(road);
        adjacencyList[city2].add(road);
    }

    private static void ensureInitialized(LinkedList<Road>[] ar, int index) {
        if (ar[index] != null)
            return;

        ar[index] = new LinkedList<>();
    }

    final static class Road {
        final int city1, city2, length;

        Road(int c1, int c2, int length) {
            if (c2 < c1) {
                int t = c2;
                c2 = c1;
                c1 = t;
            }

            city1 = c1;
            city2 = c2;
            this.length = length;
        }

        public boolean equals(Object o) {
            if (o == null)
                return false;

            if (o == this)
                return true;

            if (!(o instanceof Road))
                return false;

            Road road = (Road) o;
            return city1 == road.city1 && city2 == road.city2;
        }

        public String toString() {
            return city1 + " -> " + city2 + ": " + length;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
