package com.ashok.lang.graph;

import java.util.Comparator;

public class Edge {
    public final int source, destination, weight;

    public Edge(int source, int destination) {
        this.source = source;
        this.destination = destination;
        weight = 1;
    }

    public Edge(int source, int destination, int w) {
        this.source = source;
        this.destination = destination;
        weight = w;
    }

    public boolean equals(int source, int destination) {
        return this.source == source && this.destination == destination;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Edge))
            return false;

        Edge edge = (Edge)o;
        return source == edge.source && destination == edge.destination;
    }

    public Edge clone() {
        return this;
    }

    public String toString() {
        return source + " --> " + destination + " | " + weight;
    }

    public final static Comparator<Edge> DESTINATION_COMPARATOR =
        new Comparator<Edge>() {
        public int compare(Edge e1, Edge e2) {
            return e1.destination - e2.destination;
        }
    };

    public final static Comparator<Edge> SOURCE_COMPARATOR =
        new Comparator<Edge>() {
        public int compare(Edge a, Edge b) {
            return a.source - b.source;
        }
    };

    public final static Comparator<Edge> WEIGHT_COMPARATOR =
        new Comparator<Edge>() {
        public int compare(Edge a, Edge b) {
            return a.weight - b.weight;
        }
    };
}
