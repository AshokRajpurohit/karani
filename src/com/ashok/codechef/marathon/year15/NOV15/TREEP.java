package com.ashok.codechef.marathon.year15.NOV15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.LinkedList;

/**
 * Problem: Period on tree
 * https://www.codechef.com/NOV15/problems/TREEP
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class TREEP {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        TREEP a = new TREEP();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            int n = in.readInt();
            Graph graph = new Graph(n, false);
            while (n > 1) {
                graph.addEdge(in.readInt() - 1, in.readInt() - 1,
                              in.read(1).charAt(0));
                n--;
            }

            int m = in.readInt();
            while (m > 0) {
                m--;
                int qtype = in.readInt(), u = in.readInt() - 1, v =
                    in.readInt() - 1;
                if (qtype == 2)
                    graph.edit(u, v, in.read(1).charAt(0));
                else
                    sb.append(stringPeriod(graph.shortPath(u,
                                                           v))).append('\n');
            }
        }

        out.print(sb);
    }

    private static int stringPeriod(String s) {
        if (s.length() == 1)
            return 1;

        if (s.length() == 2)
            return s.charAt(0) == s.charAt(1) ? 1 : 2;

        int half = s.length() >>> 1, len = s.length();
        for (int i = 1; i <= half; i++) {
            if (len % i == 0 && check(s, i))
                return i;
        }

        return s.length();
    }

    private static boolean check(String s, int len) {
        for (int i = len; i < s.length(); i += len) {
            for (int j = i, k = 0; k < len; j++, k++)
                if (s.charAt(j) != s.charAt(k))
                    return false;
        }

        return true;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }

    final static class Graph {
        private int nodes, edges;
        private LinkedList[] list;
        private boolean directed = false;

        private Graph() {
            // for internal purpose only.
        }

        public Graph(int nodes) {
            this.nodes = nodes;
            edges = 0;
            list = new LinkedList[nodes];
        }

        public Graph(int nodes, boolean directed) {
            this(nodes);
            this.directed = directed;
        }

        /**
         * Adds new edge between given nodes and returns the success of it.
         * If the nodes are invalid or the edge already exists in the graph, it
         * will return false.
         *
         * @param from start node of the edge
         * @param to end node of the edge
         * @return success of edge insertion in graph.
         */
        public void addEdge(int from, int to, char c) {
            add(from, to, c);
            add(to, from, c);
        }

        public boolean removeEdge(int from, int to) {
            if (from == to || from >= nodes || to >= nodes || from < 0 ||
                to < 0)
                return false;

            boolean res = remove(from, to);

            if (!directed)
                return res || remove(to, from);

            return res;
        }

        private boolean remove(int from, int to) {
            if (list[from] == null)
                return false;

            if (list[from].remove(new Edge(to, 'a'))) {
                edges--;
                return true;
            }

            return false;
        }

        /**
         * Adds new edges in the graph and returns the success of edge addition.
         * If the edge already exists it returns false.
         *
         * @param from start node of the edge
         * @param to end node of the edge
         * @return success of edge addition
         */
        private void add(int from, int to, char c) {
            if (list[from] == null)
                list[from] = new LinkedList<Edge>();

            Edge edge = new Edge(to, c);
            list[from].add(edge);
            edges++;
        }

        private void edit(int from, int to, char c) {
            list[from].remove(new Edge(to, c));
            list[to].remove(new Edge(from, c));
            list[from].add(new Edge(to, c));
            list[to].add(new Edge(from, c));
        }

        /**
         * Returns true if there exists a path from start node to destination node.
         * If the nodes are invalid then it returns false.
         *
         * @param start source node of the path
         * @param destination end node of the path
         * @return connectivity from start node to destination node.
         */
        public boolean connected(int start, int destination) {
            return distance(start, destination) != -1;
        }

        /**
         * Returns the distance between two nodes in the graph using BFS (Breadth
         * First Search) Algorithm. If nodes are invalid or not connected then it
         * returns -1. For more information on BFS please refer "Introduction to
         * Algorithms by CLRS"
         *
         * @param u start node in the graph (path)
         * @param v end node in the graph (path)
         * @return distance between node u and v.
         */
        public int distance(int u, int v) {
            if (u >= nodes || v >= nodes || u < 0 || v < 0 || list[u] == null)
                return -1;

            if (u == v)
                return 0;

            int distance = 0;
            Integer sep = new Integer(-1);
            boolean[] check = new boolean[nodes];
            LinkedList queue = new LinkedList<Integer>();
            queue.add(u);
            queue.add(-1);
            check[u] = true;

            while (!check[v] && queue.size() > 1) {
                distance++;
                boolean cont = true;
                while (cont) {
                    Integer temp = (Integer)queue.removeFirst();
                    if (temp == -1) {
                        cont = false;
                        queue.add(-1);
                    }

                    if (cont && list[temp] != null) {
                        for (Object obj : list[temp]) {
                            Edge ed = (Edge)obj;
                            if (ed.to == v)
                                return distance;
                            if (!check[ed.to])
                                queue.add(ed.to);
                            check[ed.to] = true;
                        }
                    }
                }
            }

            return -1;
        }

        private String shortPath(int u, int v) {
            //            if (u >= nodes || v >= nodes || u < 0 || v < 0 || list[u] == null)
            //                return "";
            //
            //            if (u == v)
            //                return "";

            //            int distance = 0;
            //            Integer sep = new Integer(-1);

            /*
             * This check array will server three purpose.
             * 1. Whether the element is already visited.
             * 2. Who is predecessor to this node.
             * 3. Who is successsor of this node.
             *
             * first we will populate predecessor and then successor.
             */
            int[] check = new int[nodes];
            char[] path = new char[nodes];

            for (int i = 0; i < nodes; i++)
                check[i] = -1;

            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.add(u);
            queue.add(-1);
            check[u] = 0;

            while (check[v] == -1 && queue.size() > 1) {
                //                distance++;
                boolean cont = true;
                while (cont) {
                    Integer temp = queue.removeFirst();
                    if (temp == -1) {
                        cont = false;
                        queue.add(-1);
                    }

                    if (cont && list[temp] != null) {
                        for (Object obj : list[temp]) {
                            Edge ed = (Edge)obj;
                            if (check[ed.to] == -1) {
                                queue.add(ed.to);
                                check[ed.to] = temp;
                                path[ed.to] = ed.c;

                                if (ed.to == v)
                                    cont = false;
                            }
                        }
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            while (v != u) {
                sb.append(path[v]);
                v = check[v];
            }

            return sb.toString();
        }

        private String shortPathDFS(int u, int v) {
            LinkedList<Character> path = new LinkedList<Character>();
            boolean[] vis = new boolean[nodes];
            dfs(u, v, vis, path);
            StringBuilder sb = new StringBuilder(path.size());
            for (Character c : path)
                sb.append(c);

            return sb.toString();
        }

        private boolean dfs(int u, int v, boolean[] vis,
                            LinkedList<Character> path) {
            if (u == v)
                return true;

            if (list[u] == null)
                return false;

            vis[u] = true;
            for (Object obj : list[u]) {
                Edge ed = (Edge)obj;
                if (!vis[ed.to]) {
                    path.add(ed.c);
                    if (dfs(ed.to, v, vis, path))
                        return true;
                    path.removeLast();
                }
            }

            return false;
        }
    }

    final static class Edge {
        char c;
        int to;

        Edge(int node, char ch) {
            to = node;
            c = ch;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Edge))
                return false;

            Edge edge = (Edge)o;
            return this.to == edge.to;
        }
    }
}
