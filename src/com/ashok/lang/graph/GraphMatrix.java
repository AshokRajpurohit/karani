package com.ashok.lang.graph;

/**
 * Graph using Adjacency matrix
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class GraphMatrix {
    private boolean[][] matrix;
    private int size;

    public GraphMatrix(int capacity) {
        matrix = new boolean[capacity][capacity];
        size = capacity;
    }

    public int size() {
        return size;
    }

    public void addEdge(int from, int to) {
        matrix[from][to] = true;
    }

    public void removeEdge(int from, int to) {
        matrix[from][to] = false;
    }
}
