/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.june20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Problem Name: Convenient Airports
 * Link: https://www.codechef.com/JUNE20A/problems/CONVAIR
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ConvenientAirports {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();
            Edge[] edges = new Edge[m];
            for (int i = 0; i < m; i++) {
                edges[i] = new Edge(in.readInt() - 1, in.readInt() - 1);
            }
            out.print(process(n, edges));
        }
    }

    private static void test() throws IOException {
        while (true) {
            out.println("enter number of nodes and edges");
            out.flush();
            Random random = new Random();
            int n = in.readInt(), m = in.readInt();
            m = random.nextInt(m);
            while (true) {
                Edge[] edges = new Edge[m];
                for (int i = 0; i < m; i++) {
                    edges[i] = new Edge(random.nextInt(n), random.nextInt(n));
                }

                long time = System.currentTimeMillis();
                process(n, edges);
                out.println("wow " + (System.currentTimeMillis() - time));
                out.flush();
            }
        }
    }

    private static String process(int n, Edge[] edges) {
        Graph graph = new Graph(n);
        for (Edge edge : edges) graph.addEdge(edge);
        int inconv = graph.makeConnections();
        StringBuilder sb = new StringBuilder();
        Collection<Edge> graphEdges = graph.getEdges();
        int count = graphEdges.stream().mapToInt(ge -> ge.degree).sum();
        sb.append(inconv).append(' ').append(count).append('\n');

        graphEdges.forEach(edge -> addEdgeStrings(sb, edge));
        return sb.toString();
    }

    private static void addEdgeStrings(StringBuilder sb, Edge edge) {
        if (edge.degree == 0) return;
        for (int i = 0; i < edge.degree; i++) {
            sb.append(edge.source + 1)
                    .append(' ')
                    .append(edge.destination + 1)
                    .append('\n');
        }
    }

    final static class Graph {
        final int n;
        final Map<Integer, Edge>[] adjacencyList;
        final List<Group> groups = new ArrayList<>();
        final int[] groupIds;

        Graph(int n) {
            this.n = n;
            groupIds = new int[n];
            Arrays.fill(groupIds, -1);
            adjacencyList = new HashMap[n];
            for (int i = 0; i < n; i++) adjacencyList[i] = new HashMap();
        }

        void addEdge(int source, int destination) {
            Edge edge = new Edge(source, destination);
            adjacencyList[source].putIfAbsent(destination, edge);
            edge = adjacencyList[source].get(destination);
            edge.degree++;
            adjacencyList[destination].putIfAbsent(source, edge);
        }

        void addEdge(Edge edge) {
            int source = edge.source, destination = edge.destination;
            adjacencyList[source].putIfAbsent(destination, edge);
            edge = adjacencyList[source].get(destination);
            edge.degree++;
            adjacencyList[destination].putIfAbsent(source, edge);
        }

        private int makeConnections() {
            int inconv = 0;
            formGroups();
            updateGroups();
            Collections.sort(groups, GROUP_COMPARATOR.reversed());
            int n = groups.size();
            Group group = groups.get(0);
            for (int i = 1; i < n; i++) {
                Group g = groups.get(i);
                inconv += connect(group, g);
                group = merge(group, g);
            }

            return inconv;
        }

        private int connect(Group g1, Group g2) {
            Edge g1e = g1.multiDegree.isEmpty() ? g1.singleDegree.peek() : g1.multiDegree.peek();
            Edge g2e = g2.multiDegree.isEmpty() ? g2.singleDegree.peek() : g2.multiDegree.peek();

            if (g2e == null) {
                Edge edge = new Edge(g1e.source, g2.members.get(0));
                edge.degree++;
                adjacencyList[edge.source].put(edge.destination, edge);
                adjacencyList[edge.destination].put(edge.source, edge);
                g1.singleDegree.add(edge);
                return 2;
            }

            Edge e1 = new Edge(g1e.source, g2e.source);
            e1.degree++;
            adjacencyList[e1.source].put(e1.destination, e1);
            adjacencyList[e1.destination].put(e1.source, e1);
            g1.singleDegree.add(e1);

            // add both edges e1 and e2 to group g1 only.

            // let's decide which edge g1e or g2e should be removed or degree reduced.
            // if g2e is one degree edge and g2 has less edges than members (basically not making a cycle but one path)
            // then g2e can be removed only if g1e is multi-degree edge or g1 forms a complete cycle.
            // both the edges gets their degree reduced or none.
            // better to check if both the group doesn't form the cyle.
            if (g1.edges.size() < g1.members.size() && g2.edges.size() < g2.members.size()) {
                // not going to remove any edges.
                return 2;
            }

            Edge e2 = new Edge(g1e.destination, g2e.destination);
            adjacencyList[e2.source].put(e2.destination, e2);
            adjacencyList[e2.destination].put(e2.source, e2);
            e2.degree++;
            g1.singleDegree.add(e2);

            g1e.degree--;
            g2e.degree--;
            if (g1e.degree == 1) {
                g1.multiDegree.remove();
                g1.singleDegree.add(g1e);
            } else if (g1e.degree == 0) {
                g1.singleDegree.remove();
            }

            if (g2e.degree == 1) {
                g2.multiDegree.remove();
                g2.singleDegree.add(g2e);
            } else if (g2e.degree == 0) {
                g2.singleDegree.remove();
            }

            return 0;
        }

        /**
         * Edges are already connected, just need to update the group edges and group-id for nodes.
         *
         * @param g1 first Group
         * @param g2 second Group
         * @return merged Group, one of g1 or g2.
         */
        private Group merge(Group g1, Group g2) {
            g1.multiDegree.addAll(g2.multiDegree);
            g1.singleDegree.addAll(g2.singleDegree);
            g1.edges.addAll(g2.edges);
            g2.multiDegree.clear();
            g2.singleDegree.clear();
            g2.edges.clear();
            return g1;
        }

        private Collection<Edge> getEdges() {
            return Arrays.stream(adjacencyList)
                    .flatMap(al -> al.values().stream())
                    .collect(Collectors.toSet());
        }

        private void formGroups() {
            boolean[] visitMap = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (groupIds[i] != -1) continue;
                Group group = new Group();
                Queue<Integer> queue = new ArrayDeque<>();
                List<Integer> groupMembers = new LinkedList<>();
                queue.add(i);
                groupMembers.add(i);
                visitMap[i] = true;
                while (!queue.isEmpty()) {
                    int node = queue.remove();
                    for (int dest : adjacencyList[node].keySet()) {
                        if (visitMap[dest]) continue;
                        visitMap[dest] = true;
                        groupMembers.add(dest);
                        queue.add(dest);
                    }
                }

                groupMembers.forEach(e -> visitMap[e] = false);
                groupMembers.forEach(e -> groupIds[e] = group.groupId);
                group.members.addAll(groupMembers);
                groups.add(group);
            }
        }

        private void updateGroups() {
            for (Group group : groups) {
                Set<Edge> edges = new HashSet<>();
                for (int e : group.members) {
                    edges.addAll(adjacencyList[e].values());
                }
                group.edges.addAll(edges);
                group.reArrangeEdges();
            }
        }
    }

    private static final Edge INVALID_EDGE = new Edge(-1, -1);

    final static class Edge {
        final int source, destination;
        private int degree = 0;

        Edge(final int source, final int destination) {

            this.source = Math.min(source, destination);
            this.destination = Math.max(source, destination);
        }

        @Override
        public int hashCode() {
            return (source << 16) | destination;
        }

        @Override
        public boolean equals(Object o) {
            Edge edge = (Edge) o;
            return edge.source == source && edge.destination == destination;
        }

        @Override
        public String toString() {
            return source + " " + destination + ": " + degree;
        }
    }

    private static final Comparator<Group> GROUP_COMPARATOR = (a, b) -> {
        if (a.multiDegree.size() == b.multiDegree.size()) {
            return a.singleDegree.size() - b.singleDegree.size();
        }

        return a.multiDegree.size() - b.multiDegree.size();
    };

    final static class Group {
        private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
        final int groupId = ID_GENERATOR.getAndIncrement();
        final List<Integer> members = new ArrayList<>();
        private List<Edge> edges = new LinkedList<>();
        private Queue<Edge> multiDegree = new ArrayDeque<>();
        private Queue<Edge> singleDegree = new ArrayDeque<>();

        private void reArrangeEdges() {
            for (Edge edge : edges) {
                if (edge.degree == 1) singleDegree.add(edge);
                else multiDegree.add(edge);
            }
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