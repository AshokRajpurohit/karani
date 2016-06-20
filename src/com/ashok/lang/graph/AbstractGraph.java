package com.ashok.lang.graph;

import java.util.List;

public interface AbstractGraph {
    public boolean addEdge(int from, int to, int weight);

    public boolean removeEdge(int from, int to);

    public boolean connected(int from, int to);

    public int distance(int from, int to);

    public void valid(int node);

    public List<Edge> getEdges();
}
