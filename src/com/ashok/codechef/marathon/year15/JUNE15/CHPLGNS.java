package com.ashok.codechef.marathon.year15.JUNE15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113
 *  problem: Chef and Polygons
 *  http://www.codechef.com/JUNE15/problems/CHPLGNS
 */

public class CHPLGNS {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CHPLGNS a = new CHPLGNS();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(1000);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[][] px = new int[n][];
            int[][] py = new int[n][];

            for (int i = 0; i < n; i++) {
                int m = in.readInt();
                px[i] = new int[m];
                py[i] = new int[m];

                for (int j = 0; j < m; j++) {
                    px[i][j] = in.readInt();
                    py[i][j] = in.readInt();
                }
            }
            solve(px, py, sb);
        }
        out.print(sb);
    }

    private void solve(int[][] px, int[][] py, StringBuilder sb) {
        Node root = new Node(0);

        for (int i = 1; i < px.length; i++)
            root = add(root, px, py, i);

        int[] ar = new int[px.length];
        sort(ar, root);
        int[] result = new int[px.length];

        for (int i = 0; i < ar.length; i++) {
            result[ar[i]] = i;
        }

        for (int e : result)
            sb.append(e).append(' ');

        sb.append('\n');
    }

    private static Node add(Node root, int[][] px, int[][] py, int i) {
        if (root == null) {
            root = new Node(i);
            return root;
        }

        if (!PolyinPoly(px[root.data], py[root.data], px[i], py[i])) {
            root.right = add(root.right, px, py, i);
            if (root.right.height - getHeight(root.left) == 2) {
                if (!PointinPoly(px[root.right.data], py[root.right.data],
                                 px[i][0], py[i][0]))
                    root = RotateRR(root);
                else
                    root = RotateRL(root);
            }
            root.height =
                    Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            return root;
        }

        root.left = add(root.left, px, py, i);
        if (root.left.height - getHeight(root.right) == 2) {
            if (!PolyinPoly(px[root.left.data], py[root.left.data], px[i],
                            py[i]))
                root = RotateLR(root);
            else
                root = RotateLL(root);
        }
        root.height =
                Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        return root;
    }

    private static Node RotateLL(Node root) {
        Node left = root.left;
        root.left = left.right;
        left.right = root;
        root.height =
                Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        left.height = Math.max(getHeight(left.left), getHeight(root)) + 1;
        return left;
    }

    private static Node RotateLR(Node root) {
        root.left = RotateRR(root.left);
        return RotateLL(root);
    }

    private static Node RotateRL(Node root) {
        root.right = RotateLL(root.right);
        return RotateRR(root);
    }

    private static Node RotateRR(Node root) {
        Node right = root.right;
        root.right = right.left;
        right.left = root;
        root.height =
                Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        right.height = Math.max(getHeight(root), getHeight(right.right)) + 1;
        return right;
    }

    private static int getHeight(Node root) {
        if (root == null)
            return 0;
        return root.height;
    }

    /**
     * returns true if the second polygon is inside the first one.
     * @param px x cordinates of points for first polygon.
     * @param py y cordinates of points for first polygon.
     * @param qx x cordinates of points for second polygon.
     * @param qy y cordinates of points for second polygon.
     * @return
     */
    private static boolean PolyinPoly(int[] px, int[] py, int[] qx, int[] qy) {
        if (px.length > qx.length)
            return !PolyinPoly(qx, qy, px, py);

        int max_x = px[0];

        for (int i = 1; i < px.length; i++) {
            if (max_x < px[i])
                max_x = px[i];
        }

        for (int i = 0; i < qx.length; i++)
            if (qx[i] > max_x)
                return false;

        return true;
    }

    private static boolean PointinPoly(int[] px, int[] py, int x, int y) {
        int i, j;
        boolean result = false;

        for (i = 0, j = px.length - 1; i < px.length; j = i++) {
            if (((py[i] > y) != (py[j] > y)) &&
                (x < 1L * (px[j] - px[i]) * (y - py[i]) / (py[j] - py[i]) +
                 px[i]))
                result = !result;
        }
        return result;
    }

    final static class Node {
        Node left, right;
        int data, height = 1;

        Node(int i) {
            data = i;
        }
    }

    private static void sort(int[] ar, Node node) {
        int index = 0;
        if (node.left != null)
            index = sort(ar, node.left, 0);
        ar[index] = node.data;
        if (node.right != null)
            sort(ar, node.right, index + 1);
    }

    private static int sort(int[] ar, Node node, int index) {
        if (node.left != null)
            index = sort(ar, node.left, index);

        ar[index] = node.data;
        index++;
        if (node.right != null)
            index = sort(ar, node.right, index);

        return index;
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
