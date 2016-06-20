package com.ashok.lang.graph;

import java.util.Comparator;

public class Edge {
    public final int from, to, weight;

    public Edge(int source, int destination) {
        from = source;
        to = destination;
        weight = 1;
    }

    public Edge(int source, int destination, int w) {
        from = source;
        to = destination;
        weight = w;
    }

    public boolean equals(int source, int destination) {
        return this.from == source && this.to == destination;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Edge))
            return false;

        Edge edge = (Edge)o;
        return from == edge.from && to == edge.to;
    }

    public Edge clone() {
        Edge e = new Edge(from, to, weight);
        return e;
    }

    public String toString() {
        return from + " --> " + to + " | " + weight;
    }

    public final static Comparator<Edge> DESTINATION_COMPARATOR =
        new Comparator<Edge>() {
        public int compare(Edge e1, Edge e2) {
            return e1.to - e2.to;
        }
    };

    public final static Comparator<Edge> SOURCE_COMPARATOR =
        new Comparator<Edge>() {
        public int compare(Edge a, Edge b) {
            return a.from - b.from;
        }
    };

    public final static Comparator<Edge> WEIGHT_COMPARATOR =
        new Comparator<Edge>() {
        public int compare(Edge a, Edge b) {
            return a.weight - b.weight;
        }
    };
}
