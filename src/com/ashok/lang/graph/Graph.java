package com.ashok.lang.graph;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class uses Java Library functions unlike {@link GraphList} that makes
 * it easy to use for programming competetions. Performance is almost equal to
 * {@link GraphList} which has custom lists and queues to perform operations.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * @see GraphList
 * @see GraphMatrix
 */

public class Graph {
    private int nodes, edges;
    private LinkedList<Integer>[] list;
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

    public void addEdge(int[] from, int[] to) {
        for (int i = 0; i < from.length; i++)
            addEdge(from[i], to[i]);
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
    public boolean addEdge(int from, int to) {
        valid(from);
        valid(to);

        if (directed)
            return add(from, to);

        add(from, to);
        return add(to, from);
    }

    public boolean removeEdge(int from, int to) {
        valid(from);
        valid(to);

        if (directed)
            return remove(from, to);

        remove(from, to);
        return remove(to, from);
    }

    private boolean remove(int from, int to) {
        if (list[from] == null)
            return false;

        if (list[from].remove(new Integer(to))) {
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
    private boolean add(int from, int to) {
        if (list[from] == null)
            list[from] = new LinkedList<Integer>();

        if (list[from].contains(to))
            return false;

        list[from].add(to);
        edges++;
        return true;
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
        valid(u);
        valid(v);

        if (list[u] == null)
            return -1;

        if (!directed && list[v] == null)
            return -1;

        if (u == v)
            return 0;

        int distance = 0;
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
                        if ((Integer)obj == v)
                            return distance;
                        if (!check[(Integer)obj])
                            queue.add(obj);
                        check[(Integer)obj] = true;
                    }
                }
            }
        }

        return -1;
    }

    public boolean connectedDFS(int u, int v) {
        valid(u);
        valid(v);

        if (list[u] == null)
            return false;

        if (!directed && list[v] == null)
            return false;

        if (u == v)
            return true;

        boolean[] vis = new boolean[nodes];
        return dfs(u, v, vis);
    }

    private boolean dfs(int u, int v, boolean[] vis) {
        if (u == v)
            return true;

        if (vis[u] || list[u] == null)
            return false;

        vis[u] = true;
        for (Object obj : list[u])
            if (dfs((Integer)obj, v, vis))
                return true;

        return false;
    }

    public static Graph merge(Graph a, Graph b) {
        return a.merge(b);
    }

    /**
     * Merges two graphs and creates a new graph.
     *
     * @param graph
     * @return
     */
    public Graph merge(Graph graph) {
        if (this == graph || graph == null)
            return clone();

        Graph merge = new Graph();
        merge.nodes = this.nodes + graph.nodes;
        merge.edges = this.edges + graph.edges;
        merge.list = new LinkedList[nodes];

        for (int i = 0; i < nodes; i++)
            merge.list[i] = copy(list[i]);

        for (int i = 0; i < graph.nodes; i++)
            addAll(merge.list[i + nodes], graph.list[i], nodes);

        return merge;
    }

    /**
     * Adds all the nodes from second list to first list with data incremented
     * by parameter value.
     *
     * @param a First list to be updated.
     * @param b second list
     * @param value node data to be incremented.
     */
    private static void addAll(LinkedList a, LinkedList b, int value) {
        if (b == null)
            return;

        for (Object obj : b)
            a.add((Integer)obj + value);
    }

    /**
     * Creates completely new identical graph.
     * @return
     */
    public Graph clone() {
        Graph graph = new Graph();
        graph.nodes = this.nodes;
        graph.edges = this.edges;
        graph.directed = this.directed;
        graph.list = new LinkedList[nodes];
        for (int i = 0; i < nodes; i++)
            graph.list[i] = (LinkedList<Integer>)list[i].clone();

        return graph;
    }

    /**
     * Returns the transpose of this graph. The transpose of a directed graph
     * G = (V, E) is the graph GT = (V, ET), where ET is the reversed edge E.
     * For more information please refer "Introduction to Algorithms by CLRS,
     * Chapter 22 Elementary Graph Algorithms"
     *
     * @return transpose of this graph.
     */
    public Graph transpose() {
        if (!directed)
            return clone();

        Graph graph = new Graph();
        graph.nodes = this.nodes;
        graph.edges = this.edges;
        graph.list = new LinkedList[nodes];
        graph.directed = true;

        for (int i = 0; i < nodes; i++)
            if (list[i] != null) {
                for (Object obj : list[i])
                    graph.add((Integer)obj, i);
            }

        return graph;
    }

    /**
     * Creates a fresh copy of parameter list and returns. Originally it was
     * used to create a clone of the parameter list but as I discovered later
     * that LinkedList has a clone function that create a new copy, so now it's
     * not in use.
     *
     * @param list
     * @return
     */
    private static LinkedList<Integer> copy(LinkedList<Integer> list) {
        if (list == null)
            return list;

        LinkedList<Integer> copy = new LinkedList<Integer>();
        for (Integer obj : list)
            copy.add(obj);

        return copy;
    }

    /**
     * Compares the specified object with this graph for equality.  Returns
     * <tt>true</tt> if and only if the specified object is also a graph, both
     * graphs have the same number of nodes and edges, and all corresponding lists
     * the two graphs are <i>equal</i>.  (Two elements <tt>e1</tt> and
     * <tt>e2</tt> are <i>equal</i> if <tt>(e1==null ? e2==null :
     * e1.equals(e2))</tt>.)  In other words, two graphs are defined to be
     * equal if they contain the same lists in the same order.
     *
     * @param graph the object to be compared for equality with this graph
     * @return <tt>true</tt> if the specified object is equal to this graph
     */
    public boolean equals(Object graph) {
        if (graph == this)
            return true;

        if (!(graph instanceof Graph))
            return false;

        Graph og = (Graph)graph;

        if (this.nodes == og.nodes ^ this.edges == og.edges)
            return false;

        for (int i = 0; i < nodes; i++)
            if (!list[i].equals(og.list[i]))
                return false;

        return true;
    }

    /**
     * Returns the number of nodes in this graph.
     *
     * @return the number of nodes in this graph
     */
    public int nodes() {
        return nodes;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph.
     */
    public int edges() {
        if (directed)
            return edges;

        return edges >>> 1;
    }

    /**
     * Returns the number of edges starting from this node. If the node is
     * invalid then it returns -1.
     *
     * @param from source node of the edge
     * @return number of edges from the source node.
     */
    public int degreeOut(int from) {
        if (from >= nodes || from < 0)
            return -1;

        if (list[from] == null)
            return 0;

        return list[from].size();
    }

    /**
     * This function returns the number of edges coming to the node.
     *
     * @param to node index/value
     * @return number of edges coming to this node.
     */

    public int degreeIn(int to) {
        int degree = 0;
        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                for (Object o : list[i]) {
                    if (to == (Integer)o)
                        degree++;
                }
            }
        }

        return degree;
    }

    public boolean isCyclic() {
        if (!directed)
            return true;

        boolean[] processing = new boolean[nodes], processed =
            new boolean[nodes];
        for (int i = 0; i < nodes; i++) {
            if (!processing[i] && !processed[i]) {
                processing[i] = true;
                if (cyclic(i, processing, processed))
                    return true;

                processed[i] = true;
            }
        }

        return false;
    }

    private boolean cyclic(int n, boolean[] brown, boolean[] black) {
        if (list[n] == null)
            return false;

        for (Object o : list[n]) {
            if (brown[(Integer)o] && !black[(Integer)o])
                return true;

            brown[(Integer)o] = true;
            if (cyclic((Integer)o, brown, black))
                return true;

            black[(Integer)o] = true;
        }
        return false;
    }

    /**
     * Returns the topologically sorted array of nodes in the Graph.
     * This method uses queue to sort them topologically.
     * For further reference please see Graph Traversal, The Algorithm
     * Design Manual by Steven Skiena and
     * Introduction to Algorithms by CLRS.
     *
     * @return array of topologically sorted nodes
     */
    public int[] topologicalSort() {
        if (isCyclic())
            throw new RuntimeException("Graph is Cyclic");

        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] root = new boolean[nodes];
        int[] level = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            if (list[i] != null && list[i].size() > 0) {
                root[i] = true;
            }
        }

        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                for (Integer o : list[i])
                    root[o] = false;
            }
        }

        for (int i = 0; i < nodes; i++)
            if (root[i]) {
                queue.add(i);
            }

        queue.add(-1);
        int count = 0;
        while (queue.size() > 1) {
            count++;
            boolean cont = true;
            while (cont) {
                int temp = queue.removeFirst();
                if (temp == -1) {
                    cont = false;
                    queue.add(-1);
                    continue;
                }

                level[temp] = count;
                if (list[temp] == null)
                    continue;

                for (Object o : list[temp]) {
                    queue.add((Integer)o);
                }
            }
        }

        count = 0;
        boolean[] check = new boolean[nodes];
        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                check[i] = true;

                for (Object o : list[i]) {
                    check[(Integer)o] = true;
                }
            }
        }

        for (int i = 0; i < nodes; i++) {
            if (check[i])
                count++;
        }

        LinkedList[] topos = new LinkedList[count + 1];
        for (int i = 0; i < nodes; i++) {
            if (!check[i])
                continue;

            if (topos[level[i]] == null)
                topos[level[i]] = new LinkedList<Integer>();

            topos[level[i]].add(i);
        }

        int[] res = new int[count];
        for (int i = 0, j = 0; i < count && j < count; j++) {
            if (topos[j] != null) {
                for (Object o : topos[j]) {
                    res[i] = (Integer)o;
                    i++;
                }
            }
        }

        return res;
    }

    public int[] topologicalSortIndegree() {
        if (isCyclic())
            throw new RuntimeException("Graph is Cyclic");

        LinkedList<Integer> zero = new LinkedList<Integer>();
        LinkedList<Integer> sorted = new LinkedList<Integer>();

        int[] indegree = new int[nodes];
        for (int i = 0; i < nodes; i++)
            indegree[i] = -1;

        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                indegree[i] = 0;
                for (Object o : list[i]) {
                    indegree[(Integer)o] = 0;
                }
            }
        }

        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                for (Object o : list[i]) {
                    indegree[(Integer)o]++;
                }
            }
        }

        for (int i = 0; i < nodes; i++) {
            if (indegree[i] == 0) {
                zero.add(i);
                sorted.add(i);
            }
        }

        while (!zero.isEmpty()) {
            int temp = zero.removeFirst();
            if (list[temp] != null) {
                for (Object o : list[temp]) {
                    indegree[(Integer)o]--;
                    if (indegree[(Integer)o] == 0) {
                        zero.add((Integer)o);
                        sorted.add((Integer)o);
                    }
                }
            }
        }

        int[] res = new int[sorted.size()];
        int i = 0;
        for (Integer e : sorted)
            res[i++] = e;

        return res;
    }

    /**
     * Returns list of connected components to node. It traverses through
     * the graph in BFS manner.
     *
     * @param node
     * @return
     */
    public LinkedList<Integer> connectedComponents(int node) {
        valid(node);

        LinkedList<Integer> res = new LinkedList<Integer>();

        boolean[] check = new boolean[nodes];
        LinkedList queue = new LinkedList();
        queue.add(node);
        check[node] = true;

        while (!queue.isEmpty()) {
            Integer temp = (Integer)queue.removeFirst();
            if (list[temp] != null) {
                for (Object obj : list[temp]) {
                    if (!check[(Integer)obj]) {
                        queue.add(obj);
                        res.add((Integer)obj);
                    }
                    check[(Integer)obj] = true;
                }
            }
        }

        return res;
    }

    /**
     * Returns the strongly connected components list that contains the node.
     *
     * @param node
     * @return
     */
    public LinkedList<Integer> stronglyConnected(int node) {
        valid(node);

        LinkedList<Integer> res = new LinkedList<Integer>();
        LinkedList<Integer> connectedTo =
            connectedComponents(node), connectedFrom =
            transpose().connectedComponents(node);

        boolean[] connected = new boolean[nodes], check = new boolean[nodes];
        for (Integer e : connectedTo)
            connected[e] = true;

        for (Integer e : connectedFrom)
            check[e] = true;

        for (int i = 0; i < nodes; i++)
            if (connected[i] && check[i])
                res.add(i);

        res.add(node);
        return res;
    }

    private void valid(int v) {
        if (v < 0 || v >= nodes)
            throw new RuntimeException("Invalid node value: " + v);
    }

    /**
     * Checks whether Euler Tour exists or not in the graph. Euler Tour is the
     * path where each edge is visited only once
     *
     * @return existense of Euler Tour.
     */
    private boolean eulerTourExists() {
        LinkedList<Integer> connected = connectedComponents(0);
        if (connected.size() < nodes)
            return false;

        int[] indegree = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                for (Object e : list[i])
                    indegree[(Integer)e]++;
            }
        }

        for (int i = 0; i < nodes; i++) {
            if (indegree[i] != degreeOut(i))
                return false;
        }

        return true;
    }

    /**
     * Returns true if the arborescence for the graph exists.
     * Arborescence is the rooted tree such that there is a directed path from
     * the root to every other vertex in the graph.
     *
     * @return Existense of arborescence in the graph.
     */
    public boolean arborescence() {
        if (isCyclic())
            return false;

        boolean[] check = new boolean[nodes];

        for (int i = 0; i < nodes; i++) {
            if (list[i] != null && !check[i]) {
                LinkedList<Integer> vis = connectedComponents(i);
                if (vis.size() == nodes - 1)
                    return true;

                for (Integer e : vis) {
                    check[e] = true;
                }
                check[i] = true;
            }
        }

        return false;
    }

    /**
     * Returns the root node of the arborescence tree.
     * If the arborescence does not exists it returns -1.
     *
     * @return root node of arborescence.
     */
    public int rootArborescence() {
        if (isCyclic())
            return -1;

        boolean[] check = new boolean[nodes];

        for (int i = 0; i < nodes; i++) {
            if (list[i] != null && !check[i]) {
                LinkedList<Integer> vis = connectedComponents(i);
                if (vis.size() == nodes - 1)
                    return i;

                for (Integer e : vis) {
                    check[e] = true;
                }
                check[i] = true;
            }
        }

        return -1;
    }

    public Graph MinSpanningTree() {
        return Prim();
    }

    public LinkedList<Edge> getEdgeList() {
        LinkedList<Edge> edgeList = new LinkedList<Edge>();

        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                for (Integer d : list[i])
                    edgeList.add(new Edge(i, d));
            }
        }

        return edgeList;
    }

    /**
     * Returns Minimum Spanning Tree for un-weighted graph.
     * It is just the same graph without any cycle.
     *
     * This algorithms is for un-weighted graphs only.
     * For weighted graphs, the algorithm is different.
     *
     * @return
     */
    private Graph Prim() {
        boolean[] nodeAdded = new boolean[nodes];
        Graph msp = new Graph(nodes);
        LinkedList<Integer> sl = new LinkedList<Integer>(), dl =
            new LinkedList<Integer>();

        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                for (Integer e : list[i]) {
                    sl.add(i);
                    dl.add(e);
                }
            }
        }

        msp.add(sl.getFirst(), dl.getFirst());
        nodeAdded[sl.getFirst()] = true;
        nodeAdded[dl.getFirst()] = true;

        boolean cont = true;
        while (cont) {
            cont = false;
            Iterator<Integer> slIter = sl.iterator();
            Iterator<Integer> dlIter = dl.iterator();

            while (slIter.hasNext()) {
                int s = slIter.next(), d = dlIter.next();
                if (nodeAdded[s] ^ nodeAdded[d]) {
                    msp.add(s, d);
                    nodeAdded[s] = true;
                    nodeAdded[d] = true;
                    cont = true;
                }
            }
        }

        return msp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes; i++) {
            if (list[i] != null) {
                for (Integer e : list[i]) {
                    sb.append(i + "->" + e + "\n");
                }
            }
        }
        return sb.toString();
    }
}
