/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.jan;

import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/**
 * Problem Name: Tourists in Mancunia
 * Link: https://www.codechef.com/JAN17/problems/TOURISTS
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class TouristsInMancunia {
    private static Output out;
    private static PrintWriter sysOut = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String yes = "YES\n", no = "NO\n";
    private static final Triplet INVALID_TRIPLET = new Triplet(0, 0, 0);
    private static StringBuilder sb;
    private static long time = 0;

    public static void main(String[] args) throws IOException {
        out = new Output("C:\\Projects\\others\\karani\\src\\com\\ashok\\codechef\\marathon\\year17\\jan\\TouristsInMancunia.out");
        solve();
        in.close();
        out.close();
        sysOut.println(System.currentTimeMillis() - time);
        sysOut.close();
    }

    private static void solve() throws IOException {
        try {
            play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int nodes = in.readInt(), edges = in.readInt();
        time = System.currentTimeMillis();
        // not going to read edges as it is un-necessary.
        // we never needs connections as we are going to re-arrange, so it's like populating new edges.
        out.print(process(nodes, edges));
    }

    private static void play() {
        while (true) {
            time = System.currentTimeMillis();
            Random random = new Random();
            int a = random.nextInt(100000) + 1, b = random.nextInt(200000) + 1;
            if (a > b) {
                int t = a;
                a = b;
                b = t;
            }

            sysOut.println("test date, cities: " + a + ", paths: " + b);

            try {
                boolean res = false;
                if (a <= b)
                    process(a, b);
                else
                    process(b, a);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Exception for a, b: " + a + ", " + b);
            }

            sysOut.println(System.currentTimeMillis() - time);
            sysOut.flush();
        }
    }

    private static String process(int nodes, int edges) {
        if (!possible(nodes, edges))
            return no;

        Kingdom kingdom = new Kingdom(nodes);
        formatPathsInKingdom(kingdom, edges);

        if (!validateKingdom(kingdom.clone(), edges))
            throw new RuntimeException("Failed validation for " + nodes + ", " + edges);
//            return no;

        sb = new StringBuilder(edges << 3);
        sb.append(yes);
        append(kingdom);

        return sb.toString();
    }

    private static boolean validateKingdom(Kingdom kingdom, int edges) {
        if (kingdom.pathCount != edges)
//            return false;
            throw new RuntimeException("le kanjar, path count is not matching, expected: " + edges + ", actual: " + kingdom.pathCount);

        Set<Edge> paths = kingdom.getEdgeSet();
        if (paths.size() != edges)
//            return false;
            throw new RuntimeException(("number of paths are not correct. expected: " + edges + ", actual: " + paths.size()));

        validateIncomingOutgoingEdgeCount(kingdom, paths);
        validateKingdom(kingdom, paths);
        return true;
    }

    private static void validateKingdom(Kingdom kingdom, Set<Edge> edges) {
        removeTwoWayPaths(kingdom, edges);
        edges = kingdom.getEdgeSet(); // edges after removing two way paths as these don't affect our testing.

        if (edges.size() == 0)
            return;

        boolean[] map = new boolean[kingdom.cityCount + 1]; // if city i is in loop, value in map at index i is true.
        LinkedList<City> stack = new LinkedList<>();
        boolean[] check = new boolean[kingdom.cityCount + 1]; // to check whether the city is already added to stack.

        for (City city : kingdom.cities) {
            if (check[city.id] || city.destinations.size() != 1) {// already processed or junction of two euler tours or twoWays.
                map[city.id] = true;
                continue;
            }

            try {
                validate(city, check, map, stack, 0);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error while processing euler tour for: " + kingdom.cityCount + ", " + kingdom.pathCount);
            }
        }

        for (int i = 1; i <= kingdom.cityCount; i++)
            if (!map[i])
                throw new RuntimeException("Euler tour does not exists. " + kingdom.cityCount + ", " + kingdom.pathCount);
    }

    private static void validate(City city, boolean[] check, boolean[] map, LinkedList<City> stack, int count) {
        if (count == 1000000)
            throw new RuntimeException();

        if (check[city.id] && city.destinations.size() == 1) { // peel off stack till we find the same city and mark all the cities as processed and in loop.
            while (stack.size() > 0) {
                City prev = stack.removeLast();
                check[prev.id] = true;
                map[prev.id] = true;

                if (prev.equals(city))
                    return;
            }
        }

        check[city.id] = true;
        stack.addLast(city);

        while (city.destinations.size() == 1) {
            city = city.destinations.getFirst();
            if (check[city.id]) {
                validate(city, check, map, stack, count + 1);
                return;
            }

            check[city.id] = true;
            stack.addLast(city);
        }

        for (City target : city.destinations)
            validate(target, check, map, stack, count + 1);
    }

    private static void removeTwoWayPaths(Kingdom kingdom, Set<Edge> edges) {
        City[] cities = new City[kingdom.cityCount];
        for (int i = 1; i <= kingdom.cityCount; i++)
            cities[i - 1] = new City(i);

        for (City city : kingdom.cities) {
            LinkedList<City> cityDestinations = new LinkedList<>();
            City copy = cities[city.id - 1];

            for (City destination : city.destinations) {
                Edge path = new Edge(city.id, destination.id);

                if (edges.contains(path.reverse()))
                    kingdom.pathCount--;
                else
                    cityDestinations.add(cities[destination.id - 1]);
            }

            copy.destinations = cityDestinations;
        }

        for (int i = 0; i < kingdom.cityCount; i++)
            kingdom.cities[i] = cities[i];
    }

    private static void validateIncomingOutgoingEdgeCount(Kingdom kingdom, Set<Edge> edges) {
        int[] inEdgeCounts = new int[kingdom.cityCount + 1], outEdgeCounts = new int[kingdom.cityCount + 1];
        for (City city : kingdom.cities) {
            for (City destination : city.destinations) {
                inEdgeCounts[city.id]++;
                outEdgeCounts[destination.id]++;
            }
        }

        for (int i = 1; i <= kingdom.cityCount; i++)
            if (inEdgeCounts[i] != outEdgeCounts[i] || inEdgeCounts[i] == 0)
                throw new RuntimeException("Euler tour condition is not satisfied for " + kingdom.cityCount + ", "
                        + edges.size() + ". Incoming and outgoing path count should be equal for each node.");
    }

    private static void formatPathsInKingdom(Kingdom kingdom, int edges) {
        int nodes = kingdom.cityCount;

        if (nodes == edges) {
            kingdom.singleForwardLoop(1, nodes);
            return;
        }

        if (edges == (nodes - 1) * 2) {
            kingdom.addTwoWayPaths(nodes);
            return;
        }

        if (edges == nodes + 1) {
            kingdom.singleForwardLoop(1, nodes - 1);
            kingdom.addTwoWayPath(nodes - 1, nodes);
            return;
        }

        if (edges == nodes * 2) {
            kingdom.twoWayLoop(1, nodes);
            return;
        }

        if (edges > (nodes - 1) * 2) {
            Triplet triplet = INVALID_TRIPLET;
            if (edges % 2 == 1) {
                kingdom.singleForwardLoop(nodes - 2, nodes);
                edges -= 3;
                triplet = new Triplet(nodes - 2, nodes - 1, nodes);
                nodes -= 2;
            }

            int remaining = edges - (nodes - 1) * 2;
            kingdom.addTwoWayPaths(nodes);
            kingdom.dumpPathsForTwoWays(remaining, triplet);
            return;
        }

        kingdom.singleForwardLoop(1, nodes);
        int remaining = edges - nodes;

        if (remaining == 2)
            kingdom.dumpPathsForOneWays(remaining, nodes);
        else
            kingdom.singleBackwardLoop(1, remaining);

        return;
    }

    private static boolean possible(int nodes, int edges) {
        if (nodes == 1)
            return false;

        if (nodes == 2)
            return edges == 2;

        if (nodes > edges)
            return false;

        if (nodes == edges || nodes * 2 == edges || edges == (nodes - 1) * 2)
            return true;

        if (nodes <= 3)
            return false;

        long maxDumpEdges = getMaxEdges(nodes);
        return edges <= maxDumpEdges && maxDumpEdges - edges != 1;
    }

    private static int getMinEdges(int n) {
        n -= 3;
        int min = 3 + (n >>> 1) * 3;

        if ((n & 1) == 1)
            min += 2;

        return min;
    }

    /**
     * Returns maximum edges, that can be used to create the desirable structure.
     *
     * @param nodes Number of nodes, in the question {@link City}
     * @return number of edges that can be used.
     */
    private static long getMaxEdges(int nodes) {
        return 1L * nodes * (nodes - 1);

    }

    private static void append(Kingdom kingdom) {
        for (City city : kingdom.cities)
            append(city);
    }

    private static void append(City city) {
        for (City destination : city.destinations)
            sb.append(city.id).append(' ').append(destination.id).append('\n');
    }

    final static class Edge {
        final int start, end;

        Edge(int a, int b) {
            start = a;
            end = b;
        }

        public int hashCode() {
            return start * 1001 + end;
        }

        public boolean equals(Object object) {
            Edge edge = (Edge) object;

            return start == edge.start && end == edge.end;
        }

        public Edge reverse() {
            return new Edge(end, start);
        }

        public String toString() {
            return start + " -> " + end;
        }
    }

    final static class Loop {
        final int start, end;

        Loop(int a, int b) {
            start = a;
            end = b;
        }

        private boolean equals(int a, int b) {
            return start == a && end == b || start == b && end == a;
        }

        public int hashCode() {
            return start * 1111 + end;
        }

        public boolean eqauls(Object o) {
            Loop loop = (Loop) o;

            return start == loop.start && end == loop.end;
        }
    }

    final static class Kingdom {
        final City[] cities;
        final int cityCount;
        private int pathCount;

        private Kingdom(int cityCount) {
            this.cityCount = cityCount;
            cities = new City[cityCount];

            for (int i = 1; i <= cityCount; i++)
                cities[i - 1] = new City(i);
        }

        private City getCity(int id) {
            return cities[id - 1];
        }

        private void dumpPathsForOneWays(int edges, int loopCity) {

            for (int i = 1; i <= cityCount && edges > 0; i++) {
                for (int j = i + 2; j <= cityCount && edges > 0; j++) {
                    if (i == 1 && j == loopCity) // there is a connection between these, we can't create a new one.
                        continue;

                    edges--;
                    edges--;
                    addTwoWayPath(i, j);
                }
            }
        }

        private void addLoop(Triplet triplet) {
            City a = getCity(triplet.a), b = getCity(triplet.b), c = getCity(triplet.c);
            a.destinations.add(b);
            b.destinations.add(c);
            c.destinations.add(a);
        }

        private void addTwoWayPaths(int nodes) {
            for (int i = 1; i < nodes; i++)
                addTwoWayPath(i, i + 1);
        }

        private void twoWayLoop(int start, int end) {
            singleForwardLoop(start, end);
            singleBackwardLoop(start, end);
        }

        private void addTwoWayPath(int first, int second) {
            addPath(first, second);
            addPath(second, first);
        }

        private void addPath(int first, int second) {
            City.addDestination(getCity(first), getCity(second));
            pathCount++;
        }

        private void dumpPathsForTwoWays(int edges, Triplet triplet) {
            if (triplet == INVALID_TRIPLET) {
                dumpPathsForTwoWays(edges);
                return;
            }

            for (int i = 1; i <= cityCount && edges > 0; i++) {
                for (int j = i + 2; j <= cityCount && edges > 0; j++) {
                    if (triplet.contains(i, j))
                        continue;

                    edges -= 2;
                    addTwoWayPath(i, j);
                }
            }
        }

        private void dumpPathsForTwoWays(int edges) {
            for (int i = 1; i <= cityCount && edges > 0; i++) {
                for (int j = i + 2; j <= cityCount && edges > 0; j++) {
                    edges -= 2;
                    addTwoWayPath(i, j);
                }
            }
        }

        private void dumpPaths(int edges, Loop a, Loop b) {
            for (int i = 1; i < cityCount && edges > 0; i++) {
                for (int j = i + 2; j <= cityCount && edges > 0; j++) {
                    if (a.equals(i, j) || b.equals(i, j))
                        continue;

                    addTwoWayPath(i, j);
                    edges -= 2;
                }
            }
        }

        private void singleForwardLoop(int start, int end) {
            for (int i = start; i < end; i++)
                addPath(i, i + 1);

            addPath(end, start);
        }

        private void singleBackwardLoop(int start, int end) {
            addPath(start, end);

            for (int i = start; i < end; i++)
                addPath(i + 1, i);
        }

        public Kingdom clone() {
            Kingdom kingdom = new Kingdom(cityCount);
            kingdom.pathCount = pathCount;

            for (int i = 0; i < cityCount; i++)
                kingdom.cities[i].destinations.addAll(cities[i].destinations);

            return kingdom;
        }

        private Set<Edge> getEdgeSet() {
            Set<Edge> edgeSet = new HashSet<>();
            for (City city : cities) {
                for (City target : city.destinations)
                    edgeSet.add(new Edge(city.id, target.id));
            }

            return edgeSet;
        }
    }

    final static class Triplet {
        int a, b, c;

        private Triplet(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        private boolean contains(int first, int second) {
            return a == first && b == second || b == first && c == second;
        }
    }

    final static class City {
        final int id; // city index.
        LinkedList<City> destinations = new LinkedList<>(); // connected cities, we can go from this city.

        City(int id) {
            this.id = id;
        }

        public String toString() {
            return id + " -> " + destinations.size();
        }

        public static void addDestination(City source, City target) {
            source.destinations.add(target);
        }

        public boolean equals(City city) {
            return id == city.id;
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
