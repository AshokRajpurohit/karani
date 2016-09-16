package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Problem Name: Ticket Counter
 * Link: https://www.hackerearth
 * .com/walmartlabs-java-developer-hiring-challenge/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class WalmartLabsAugust16 {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        WalmartLabsAugust16 a = new WalmartLabsAugust16();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int junctions = in.readInt(), queues = in.readInt();

        Node[] nodes = new Node[junctions + 1];
        for (int i = 1; i <= junctions; i++)
            nodes[i] = new Node(i);

        for (int i = 0; i < queues; i++) {
            int startNode = in.readInt(), endNode = in.readInt(),
                    size = in.readInt();

            nodes[startNode].peopleCount = size;
            nodes[endNode].fromNodes.add(nodes[startNode]);
        }

        Node exitNode = nodes[1];
        for (int i = 1; i <= junctions; i++)
            if (nodes[i].peopleCount == 0) {
                exitNode = nodes[i];
                break;
            }

        for (int i = 1; i <= junctions; i++) {
            Collections.sort(nodes[i].fromNodes);
        }

        PathNode pathNode = new PathNode(exitNode.index);
        process(exitNode);
        out.println(exitNode.minCost);
        formPath(pathNode, exitNode);
        printPath(pathNode);
    }

    private static void printPath(PathNode end) {
        LinkedList<PathNode> starts = new LinkedList<>();
        populate(starts, end);
        Collections.sort(starts);
        StringBuilder sb = new StringBuilder();
        for (PathNode pathNode : starts)
            print(sb, pathNode);

        out.print(sb);
    }

    private static void print(StringBuilder sb, PathNode node) {
        while (true) {
            sb.append(node.index);
            node = node.next;
            if (node == null)
                break;

            sb.append(" -> ");
        }

        sb.append('\n');
    }

    private static void populate(LinkedList<PathNode> starts, PathNode node) {
        if (node.prevNodeList.size() == 0) {
            starts.add(node);
            return;
        }

        for (PathNode source : node.prevNodeList) {
            populate(starts, source);
        }
    }

    private static void formPath(PathNode pathNode, Node node) {
        int count = 0, cost = 0;
        boolean cont = true;

        while (cont) {
            cont = false;
            count++;

            for (Node from : node.fromNodes) {
                if (count > from.peopleCount) {
                    if (node.minCost == from.minCost + cost + 1) {
                        PathNode prev = new PathNode(from.index);
                        prev.next = pathNode;
                        pathNode.prevNodeList.add(prev);
                        formPath(prev, from);
                    }
                    continue;
                }

                cont = true;
                cost++;
                /*if (node.minCost == from.minCost + cost + 1) {
                    PathNode prev = new PathNode(from.index);
                    prev.next = pathNode;
                    pathNode.prevNodeList.add(prev);
                    formPath(prev, from);
                }*/
            }
        }
    }

    private static void process(Node node) {
        if (node.fromNodes.size() == 0) {
            node.minCost = 1;
            return;
        }

        for (Node from : node.fromNodes)
            process(from);

        int count = 0, cost = 0;
        boolean cont = true;

        while (cont) {
            cont = false;
            count++;

            for (Node from : node.fromNodes) {
                if (count > from.peopleCount) {
                    node.minCost = Math.min(node.minCost, from.minCost + cost
                            + 1);
                    continue;
                }

                cont = true;
                cost++;
                /*if (count == from.peopleCount) {
                    node.minCost = Math.min(node.minCost, from.minCost + cost
                            + 1);
                }*/
            }
        }
    }

    final static class PathNode implements Comparable<PathNode> {
        int index = 0;
        PathNode next;
        LinkedList<PathNode> prevNodeList = new LinkedList<>();

        PathNode(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(PathNode o) {
            return this.index - o.index;
        }
    }

    final static class Node implements Comparable<Node> {
        int index = 0;
        LinkedList<Node> fromNodes = new LinkedList<>();
        int peopleCount = 0; // outgoing queue length
        int minCost = 300 * 300 * 300;

        Node(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(Node o) {
            return index - o.index;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
    }
}
