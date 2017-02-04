/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.jan;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Problem Name: Capital Movement
 * Link: https://www.codechef.com/JAN17/problems/CAPIMOVE
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CapitalMovement {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(100000);

        while (t > 0) {
            t--;

            int n = in.readInt();
            Planet[] planets = new Planet[n];

            for (int i = 0; i < n; i++)
                planets[i] = new Planet(i + 1, in.readInt());

            for (int i = 1; i < n; i++) {
                Planet source = planets[in.readInt() - 1], target = planets[in.readInt() - 1];
                source.neighbours.add(target);
                target.neighbours.add(source);
            }

            process(sb, planets);
        }

        out.print(sb);
    }

    private static void process(StringBuilder sb, Planet[] planets) {
        if (planets.length == 1) {
            sb.append("0\n");
            return;
        }

        if (planets.length == 2) {
            sb.append("0 0\n");
            return;
        }

        Planet[] ref = planets.clone();
        Arrays.sort(planets);

        for (Planet planet : ref)
            sb.append(getCapital(planets, planet)).append(' ');

        sb.append('\n');
    }

    /**
     * Finds the first planet in {@code planets} which is not {@code planet}'s neighbour.
     * As planets are in sorted order, it is like comparing to sorted list.
     *
     * @param planets
     * @param planet
     * @return
     */
    private static int getCapital(Planet[] planets, Planet planet) {
        if (planet.neighbours.size() == planets.length) // when all planets are connected.
            return 0;

        normalize(planet);
        int index = 0;
        for (Planet p : planet.neighbours) {
            if (planets[index] != p) // planet which is not neighbour, return it's index.
                return planets[index].index;

            index++;
        }

        return planets[index].index;
    }

    /**
     * orders planet's neighbours in decreasing order of population.
     *
     * @param planet
     */
    private static void normalize(Planet planet) {
        if (planet.neighbours.size() < 2)
            return;

        Collections.sort(planet.neighbours);
    }

    final static class Planet implements Comparable<Planet> {
        final int index, population;
        LinkedList<Planet> neighbours = new LinkedList<>(); // all connected planets, including itself.

        public Planet(int index, int population) {
            this.index = index;
            this.population = population;
            neighbours.add(this);
        }

        /**
         * This function is to order in decreasing population.
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(Planet o) {
            return o.population - population;
        }

        public String toString() {
            return index + ", " + population;
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
    }
}
