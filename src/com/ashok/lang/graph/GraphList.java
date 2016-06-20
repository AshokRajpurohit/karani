package com.ashok.lang.graph;

import com.ashok.lang.dsa.List;

import com.ashok.lang.dsa.Queue;

import java.util.Iterator;

/**
 * Graph using Adjacency list.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class GraphList {
    private List[] nodes;
    private int nodeCount = 0, edgeCount = 0;
    private boolean directed = false;

    public GraphList(int verticeCount) {
        nodes = new List[verticeCount];
        nodeCount = verticeCount;
    }

    public GraphList(int verticeCount, boolean directed) {
        this.directed = directed;
        nodes = new List[verticeCount];
        nodeCount = verticeCount;
    }

    public boolean connected(int from, int to) {
        if (from >= nodeCount || to >= nodeCount || from < 0 || to < 0)
            return false;

        if (from == to)
            return true;

        //        return distance(from, to) != -1;

        //        if (from >= nodeCount || to >= nodeCount)
        //            throw new ArrayIndexOutOfBoundsException("Array index of bound, limit: " +
        //                                                     (nodeCount - 1));
        boolean[] visited = new boolean[nodeCount];

        return connected(from, to, visited);
    }

    /**
     * DFS is used to check the connectivity of two nodes in graph.
     *
     * @param from
     * @param to
     * @param vis
     * @return
     */
    private boolean connected(int from, int to, boolean[] vis) {
        if (from == to)
            return true;

        if (vis[from] || nodes[from] == null)
            return false;

        vis[from] = true;
        for (Object obj : nodes[from])
            if (connected((Integer)obj, to, vis))
                return true;

        //        Iterator iter = nodes[from].iterator();
        //        while (iter.hasNext())
        //            if (connected((Integer)iter.next(), to, vis))
        //                return true;

        return false;
    }

    public boolean addEdge(int from, int to) {
        if (from == to)
            return false;

        if (nodes[from] == null) {
            nodes[from] = new List(to);
            ++edgeCount;
        } else if (add(nodes[from], to))
            ++edgeCount;
        else
            return false;

        if (!directed) {
            if (nodes[to] == null) {
                nodes[to] = new List(from);
                ++edgeCount;
            } else if (add(nodes[to], from))
                ++edgeCount;
            else
                return false;
        }
        return true;
    }

    /**
     * Breadth First Search algorithm is used to calculate distance between
     * two nodes.
     *
     * @param from
     * @param to
     * @return
     */
    public int distance(int from, int to) {
        if (from == to)
            return 0;

        if (nodes[from] == null)
            return -1;

        Queue queue = new Queue<Integer>();
        queue.push(from);
        queue.push(-1);
        int distance = 0;
        boolean[] vis = new boolean[nodeCount];
        vis[from] = true;

        while (!vis[to] && queue.size() > 1) {
            distance++;
            Iterator iter = queue.iterator();
            boolean cont = true;
            while (iter.hasNext() && cont) {
                Integer temp = (Integer)iter.next();
                cont = temp != -1;
                if (cont && nodes[temp] != null)
                    for (Object obj : nodes[temp]) {
                        Integer node = (Integer)obj;
                        if (node == to)
                            return distance;
                        if (!vis[node])
                            queue.push(node);
                        vis[node] = true;
                    }
            }
            queue.push(-1);
        }

        return -1;
    }

    public int addEdge(int[] from, int[] to) {
        int count = 0;
        for (int i = 0; i < to.length; i++)
            if (addEdge(from[i], to[i]))
                ++count;

        return count;
    }

    /**
     * Adds element to list supplied.
     * If the list contains the item already, function will return false.
     * We can not allow duplicate elements in the graph list as there can be
     * only one direct path from one node to another.
     *
     * @param list List to add new element
     * @param to element to be added to list
     * @return true if the element is added, false if element already exists.
     */
    private static boolean add(List list, int to) {
        if (list == null)
            list = new List(to);
        else if (list.contains(to))
            return false;
        else
            list.add(to);

        return true;
    }

    /**
     * Removes edge between to nodes if it exists.
     * If the edge does not exists it returns false.
     *
     * @param from start node of the edge
     * @param to end node of the edge
     * @return true or false depending on the edge existed.
     */
    public boolean removeEdge(int from, int to) {
        if (nodes[from] != null)
            if (nodes[from].remove(to)) {
                edgeCount--;
                return true;
            }

        if (directed)
            return false;

        if (nodes[to] != null)
            if (nodes[to].remove(from)) {
                edgeCount--;
                return true;
            }

        return false;
    }

    /**
     * Returns the number of edges starting from the node.
     *
     * @param from the node
     * @return edge count
     */
    public int edgesFromNodeCount(int from) {
        if (nodes[from] == null) {
            System.out.println("Shout");
            return 0;
        }

        return nodes[from].size();
    }

    public String edgesFromNode(int from) {
        if (nodes[from] == null)
            return "null";

        return nodes[from].toString();
    }

    /**
     * Merges another graph into this graph.
     *
     * @param gl
     */
    public void merge(GraphList gl) {
        if (this == gl)
            return;

        List[] temp = new List[nodeCount + gl.nodeCount];
        for (int i = 0; i < nodeCount; i++)
            temp[i] = nodes[i];

        for (int i = nodeCount; i < temp.length; i++) {
            if (gl.nodes[i - nodeCount] != null) {
                temp[i] = new List();
                for (Object obj : gl.nodes[i - nodeCount])
                    temp[i].add((Integer)obj + nodeCount);
            }
        }

        nodeCount += gl.nodeCount;
        edgeCount += gl.edgeCount;
    }
    /*
    final static class Node {
        int value;

        Node(int d) {
            value = d;
        }

        public boolean equals(Object obj) {
            return true; // later
        }
    }
    */

    public int nodeCount() {
        return nodeCount;
    }

    public int edgeCount() {
        return edgeCount;
    }
}
