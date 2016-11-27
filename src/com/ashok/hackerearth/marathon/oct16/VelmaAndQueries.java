package com.ashok.hackerearth.marathon.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Problem Name: Velma and Queries
 * Link: https://www.hackerearth.com/october-circuits/algorithm/velma-and-queries/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class VelmaAndQueries {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static Node[] nodes;
    private static LinkedList<Node>[] nodesAtLevel;
    private static final Node INVALID = new Node(-1, -1);

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;

            int n = in.readInt(), q = in.readInt();
            StringBuilder sb = new StringBuilder(q << 1);
            initialize(in.readIntArray(n));

            for (int i = 1; i < n; i++)
                update(in.readInt() - 1, in.readInt() - 1);

            process();

            while (q > 0) {
                q--;

                sb.append(query(in.readInt() - 1, in.readInt())).append('\n');
            }

            out.print(sb);
        }
    }

    private static long query(int index, int level) {
        Node node = nodes[index];
        Node first = nodes[index], last = nodes[index];
        level -= node.level;

        if (node.depth < level)
            return 0;

        if (level == 0)
            return node.value;

        while (level != 0) {
            level--;

            if (first.children.size() == 0 || last.children.size() == 0)
                break;

            first = first.children.getFirst();
            last = last.children.getLast();
        }

        return last.sum - first.sum + first.value;
    }

    private static void process() {
        nodes[0].level = 1;

        int level = 1;
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(nodes[0]);
        queue.add(INVALID);

        while (queue.size() != 1) {
            level++;

            Node temp = queue.removeFirst();

            while (temp != INVALID) {
                temp.level = level;
                updateChildren(temp);

                for (Node child : temp.children) {
                    queue.addLast(child);
                }

                temp = queue.removeFirst();
            }

            queue.addLast(INVALID);
        }

        nodesAtLevel = new LinkedList[level + 1];

        for (Node node : nodes) {
            if (nodesAtLevel[node.level] == null)
                nodesAtLevel[node.level] = new LinkedList<>();

            nodesAtLevel[node.level].addLast(node);
        }

        for (int i = 1; i <= level; i++) {
            if (nodesAtLevel[i] == null)
                continue;

            Collections.sort(nodesAtLevel[i]);

            long sum = 0;
            for (Node node : nodesAtLevel[i]) {
                sum += node.value;
                node.sum = sum;
            }
        }

        for (int i = level; i > 0; i--) {
            if (nodesAtLevel[i] == null)
                continue;

            for (Node node : nodesAtLevel[i]) {
                if (node.children.size() == 0)
                    continue;

                int maxDepth = 0;
                for (Node child : node.children)
                    maxDepth = Math.max(maxDepth, child.depth);

                node.depth = maxDepth + 1;
            }
        }
    }

    /**
     * Remove parents form child node list.
     *
     * @param node
     */
    private static void updateChildren(Node node) {
        LinkedList<Node> children = new LinkedList<>();

        for (Node child : node.children)
            if (child.level == 0)
                children.addLast(child);

        Collections.sort(children);
        node.children = children;
        for (Node child: node.children)
            child.parent = node;
    }

    private static void update(int u, int v) {
        nodes[u].children.add(nodes[v]);
        nodes[v].children.add(nodes[u]);
    }

    private static void initialize(int[] ar) {
        nodes = new Node[ar.length];

        for (int i = 0; i < ar.length; i++)
            nodes[i] = new Node(ar[i], i);
    }

    final static class Node implements Comparable<Node> {
        int value, level = 0, index, depth = 0;
        LinkedList<Node> children = new LinkedList<>();
        long sum;
        Node parent = INVALID;

        Node(int value, int index) {
            this.value = value;
            sum = value;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
