package com.ashok.lang.dsa;

import com.ashok.lang.graph.Edge;
import com.ashok.lang.utils.ArrayUtils;
import com.ashok.lang.utils.Utils;

import java.util.LinkedList;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class TreeBuilder {
    public static LinkedList<Edge> generateRandomTree(int size) {
        return generateRandomTree(0, size - 1);
    }

    public static LinkedList<Edge> generateRandomTree(int from, int to) {
        int size = to + 1 - from;
        int[] idArray = Utils.getIndexArray(size);
        for (int i = 0; i < size; i++)
            idArray[i] += from;

        ArrayUtils.randomizeArray(idArray);
        LinkedList<Edge> edges = new LinkedList<>();
        buildTree(edges, idArray);
        return edges;
    }

    private static void buildTree(LinkedList<Edge> edges, int[] ar) {
        int root = ar[0];

    }

    private static void buildTree(LinkedList<Edge> edges, int[] ar, int from, int to) {

    }
}
