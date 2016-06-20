package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem: Line Segments
 * https://www.hackerrank.com/contests/epiccode/challenges/line-segments
 */

public class EpicCodeE {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        EpicCodeE a = new EpicCodeE();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] x = new int[n], y = new int[n], count = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = in.readInt();
            y[i] = in.readInt();
        }

        Node root = new Node(x[0], y[0]);
        for (int i = 1; i < n; i++) {
            root.add(new Node(x[i], y[i]));
        }

        System.out.println(traverse(root));

        /*
        // let's apply brute force that is surely going to give TLE
        for (int i = 0; i < n; i++) {
            count[i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (!((x[i] <= x[j] && y[i] >= y[j]) ||
                      (x[i] >= x[j] && y[i] <= y[j])))
                    count[i]++;
            }
        }
        int max = 0;
        for (int i = 0; i < n; i++)
            if (max < count[i])
                max = count[i];
        out.println(max);
         */
    }

    private static int traverse(Node root) {
        if (root == null)
            return 0;
        return Math.max(root.count,
                        Math.max(Math.max(traverse(root.good), traverse(root.inner)),
                                 traverse(root.outer)));
    }

    private final static class Node {
        int count = 1;
        int x, y;
        Node good, inner, outer;

        Node(int a, int b) {
            x = a;
            y = b;
        }

        void add(Node node) {
            int cover = coverFunc(this, node);
            if (cover == 0) {
                count++;
                if (good == null)
                    good = node;
                else
                    good.add(node);

                if (inner != null)
                    inner.add(node);
                if (outer != null)
                    outer.add(node);
                return;
            }

            if (cover == 1) {
                if (outer == null)
                    outer = node;
                else
                    outer.add(node);
                if (good != null)
                    good.add(node);
                return;
            }

            if (inner == null)
                inner = node;
            else
                inner.add(node);
            if (good != null)
                good.add(node);
        }

        /**
         * checks whether the root is covering or covered by node.
         * if root is covering the node than it returns -1.
         * If node is covering root return 1, otherwise 0 indicating Good Pair.
         * @param root
         * @param node
         * @return 1, -1 or 0
         */
        static int coverFunc(Node root, Node node) {
            if (root.x <= node.x && root.y >= node.y)
                return -1;
            if (root.x >= node.x && root.y <= node.y)
                return 1;
            return 0;
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
