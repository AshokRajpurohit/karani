/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.target;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Target Onsite Hiring Challenge.
 * Link: No Link. It's Onsite.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Target {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static final char VALID_POINT = ' ';

    public static void main(String[] args) throws IOException, InvalidArgumentException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException, InvalidArgumentException {
        while (true) {
            int n = in.readInt();
            String[] maze = in.readLineArray(n);
            out.println(shortestPath(maze));
            out.flush();
        }
    }

    private static List<MazeNode> shortestPath(String[] mazeStrings) throws InvalidArgumentException {
        if (mazeStrings.length == 0 || mazeStrings[0].length() == 0)
            throw new InvalidArgumentException(new String[]{"maze can not be empty", "size = 0"});

        Maze maze = new Maze(mazeStrings);
        return maze.shortestPath();
//        return maze.shortestPath(0, 0, mazeStrings.length - 1, mazeStrings[0].length() - 1);
    }

    private static final MazeNode INVALID_NODE = new MazeNode(-1, -1, false);

    final static class MazeNode {
        final int x, y;
        MazeNode previous = INVALID_NODE;
        final boolean traverseable;
        int distance = 0;

        MazeNode(int x, int y, boolean traverseable) {
            this.x = x;
            this.y = y;
            this.traverseable = traverseable;
        }

        public String toString() {
            return "( " + x + "," + y + " )";
        }
    }

    final static class Maze {
        final int row, col;
        final MazeNode[][] nodes;

        Maze(String[] ar) {
            row = ar.length;
            col = ar[0].length();
            nodes = new MazeNode[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++)
                    nodes[i][j] = new MazeNode(i, j, ar[i].charAt(j) == VALID_POINT);
            }
        }

        private void reset() {
            for (MazeNode[] row : nodes)
                for (MazeNode node : row) {
                    node.distance = 0;
                    node.previous = INVALID_NODE;
                }
        }

        private List<MazeNode> shortestPath() {
            return shortestPath(nodes[0], nodes[row - 1]);
        }

        private List<MazeNode> shortestPath(MazeNode[] startNodes, MazeNode[] endNodes) {
            process(startNodes);
            List<MazeNode> path = path(endNodes);
            reset();
            return path;
        }

        private List<MazeNode> path(MazeNode[] endNodes) {
            MazeNode endNode = getMinDistanceNode(endNodes);
            if (endNode.previous == INVALID_NODE)
                return new LinkedList<>();

            return path(endNode);

        }

        private List<MazeNode> path(MazeNode endNode) {
            LinkedList<MazeNode> path = new LinkedList<>();
            while (endNode.previous != INVALID_NODE && endNode.previous != endNode) {
                path.addFirst(endNode);
                endNode = endNode.previous;
            }

            if (endNode != INVALID_NODE)
                path.addFirst(endNode);

            return path;
        }

        private static MazeNode getMinDistanceNode(MazeNode[] nodes) {
            MazeNode min = nodes[0];
            for (MazeNode node : nodes) {
                if (node.previous != INVALID_NODE)
                    min = min.previous == INVALID_NODE ? node : min.distance > node.distance ? node : min;
            }

            return min;
        }

        private void process(MazeNode[] startNodes) {
            // we will use BFS, while adding to the queue, we will mark who was the previous node,
            // added this node. In that way we can get the path once we reach the final node.
            // just backtrack. Oh in this code, we are marking the distance also from the first node.
            LinkedList<MazeNode> queue = new LinkedList<>(),
                    buffer = new LinkedList<>();

            for (MazeNode start : startNodes) {
                if (!start.traverseable)
                    continue;

                queue.add(start);
                start.previous = start;
            }

            while (!queue.isEmpty()) {
                MazeNode top = queue.removeFirst();
                nextMazeNodes(buffer, top);
                for (MazeNode nextNode : buffer) {
                    if (nextNode.traverseable && nextNode.previous == INVALID_NODE) {
                        nextNode.previous = top;
                        nextNode.distance = top.distance + 1;
                        queue.addLast(nextNode);
                    }
                }

                buffer.clear();
            }
        }

        private List<MazeNode> shortestPath(int sx, int sy, int ex, int ey) {
            process(nodes[sx][sy], nodes[ex][ey]);
            List<MazeNode> path = path(nodes[sx][sy], nodes[ex][ey]);
            reset();
            return path;
        }

        private List<MazeNode> shortestPath(MazeNode start, MazeNode end) {
            process(start, end);
            List<MazeNode> path = path(end);
            reset();
            return path;
        }

        private void process(MazeNode start, MazeNode end) {
            // we will use BFS, while adding to the queue, we will mark who was the previous node,
            // added this node. In that way we can get the path once we reach the final node.
            // just backtrack.
            LinkedList<MazeNode> queue = new LinkedList<>(),
                    buffer = new LinkedList<>();

            queue.add(start);
            start.previous = start;

            while (end.previous != INVALID_NODE && !queue.isEmpty()) {
                MazeNode top = queue.removeFirst();
                nextMazeNodes(buffer, top);
                for (MazeNode nextNode : buffer) {
                    if (nextNode.traverseable && nextNode.previous != INVALID_NODE) {
                        nextNode.previous = top;
                        queue.addLast(nextNode);
                    }
                }

                buffer.clear();
            }
        }

        private static List<MazeNode> path(MazeNode start, MazeNode end) {
            LinkedList<MazeNode> path = new LinkedList<>();
            if (end.previous == INVALID_NODE)
                return path;

            MazeNode iterator = end;
            while (iterator != start && iterator != INVALID_NODE) {
                path.addFirst(iterator);
                iterator = iterator.previous;
            }

            if (iterator != start)
                throw new RuntimeException("No Path exists between " + start + " and " + end);

            path.addFirst(start);
            return path;
        }

        private void nextMazeNodes(List<MazeNode> buffer, MazeNode node) {
            buffer.add(left(node));
            buffer.add(right(node));
            buffer.add(up(node));
            buffer.add(down(node));
        }

        private MazeNode left(MazeNode node) {
            return getNode(node.x, node.y - 1);
        }

        private MazeNode right(MazeNode node) {
            return getNode(node.x, node.y + 1);
        }

        private MazeNode up(MazeNode node) {
            return getNode(node.x - 1, node.y);
        }

        private MazeNode down(MazeNode node) {
            return getNode(node.x + 1, node.y);
        }

        private MazeNode getNode(int x, int y) {
            return isValid(x, y) ? nodes[x][y] : INVALID_NODE;
        }

        private boolean isValid(int x, int y) {
            return x >= 0 && x < row && y >= 0 && y < col;
        }
    }
}
