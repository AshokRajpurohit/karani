package com.ashok.lang.graph;

import com.ashok.lang.dsa.DisjointSet;
import com.ashok.lang.math.FibonacciHeap;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements data structures and algorithms for weighted graphs.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * @see Graph
 */
public class WGraph {
    private int[] edgeOut, edgeIn;
    private int nodeCount, edgeCount;
    private final int capacity;
    private LinkedList<Edge>[] list;
    public final boolean directed;
    private final static LinkedList<Edge> listRef = new LinkedList<Edge>();

    private WGraph() {
        capacity = 0;
        directed = false;
    }

    public WGraph(int nodes) {
        directed = false;
        capacity = nodes;
        edgeIn = new int[nodes];
        edgeOut = new int[nodes];
        LinkedList<Edge> temp = new LinkedList<Edge>();
        list =
(LinkedList<Edge>[])Array.newInstance(listRef.getClass(), nodes);
    }

    public WGraph(int nodes, boolean directed) {
        this.directed = directed;
        capacity = nodes;
        edgeIn = new int[nodes];
        edgeOut = new int[nodes];
        LinkedList<Edge> temp = new LinkedList<Edge>();
        list =
(LinkedList<Edge>[])Array.newInstance(listRef.getClass(), nodes);
    }

    public int nodeCount() {
        return nodeCount;
    }

    public int edgeCount() {
        return edgeCount;
    }

    public int size() {
        return capacity;
    }

    public boolean addEdge(int from, int to) {
        return addEdge(from, to, 1);
    }

    public boolean addEdge(int from, int to, int weight) {
        valid(from);
        valid(to);

        if (from == to)
            throw new RuntimeException("Invalid Edge, source and destination point can not be same");

        if (directed || from < to)
            return add(from, to, weight);

        return add(to, from, weight);
    }

    public boolean add(int from, int to, int weight) {
        if (list[from] == null)
            list[from] = new LinkedList<Edge>();

        Edge edge = new Edge(from, to, weight);
        if (list[from].contains(edge))
            return false;

        list[from].add(edge);
        edgeOut[from]++;
        edgeIn[to]++;
        edgeCount++;

        if (edgeOut[from] + edgeIn[from] == 1)
            nodeCount++;

        if (edgeOut[to] + edgeIn[to] == 1)
            nodeCount++;

        return true;
    }

    public void valid(int v) {
        if (v < 0 || v >= capacity)
            throw new RuntimeException("Invalid node value: " + v);
    }

    public LinkedList<Edge> edgeList() {
        LinkedList<Edge> edgeList = new LinkedList<Edge>();
        for (int i = 0; i < capacity; i++) {
            if (list[i] != null)
                for (Edge e : list[i])
                    edgeList.add(e);
        }

        return edgeList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++)
            if (list[i] != null) {
                for (Edge e : list[i])
                    sb.append(e).append('\n');
            }

        return sb.toString();
    }


    public WGraph minimumSpanningTree() {
        return Kruskal();
    }

    private WGraph Kruskal() {
        FibonacciHeap fh =
            new FibonacciHeap<Edge, Edge>(Edge.WEIGHT_COMPARATOR);

        for (int i = 0; i < capacity; i++) {
            if (list[i] != null)
                for (Edge e : list[i])
                    fh.add(e, e);
        }

        WGraph graph = new WGraph();
        ArrayList aList = new ArrayList<DisjointSet<Integer>>();
        LinkedList lList = new LinkedList<DisjointSet<Integer>>();
        //        lList.re

        // to be completed.
        return graph;
    }

    final static class FibonacciHeapWrapper<K, V> {
        FibonacciHeap<Edge, Edge> root =
            new FibonacciHeap<Edge, Edge>(Edge.WEIGHT_COMPARATOR);
        List<FibonacciHeap> heapList = new LinkedList<FibonacciHeap>();
        
        void addEdge(Edge edge) {
            
        }
    }
}
