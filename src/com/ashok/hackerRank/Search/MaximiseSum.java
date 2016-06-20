package com.ashok.hackerRank.Search;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Maximise Sum
 * https://www.hackerrank.com/challenges/maximise-sum
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class MaximiseSum {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MaximiseSum a = new MaximiseSum();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;

            int n = in.readInt();
            long m = in.readLong();
            out.println(process(in.readLongArray(n), m));
        }
    }

    private static long process(long[] ar, long mod) {
        if (mod == 1)
            return 0;

        if (ar.length == 2) {
            long a = Math.max(ar[0] % mod, ar[1] % mod);
            return Math.max(a, (ar[0] + ar[1]) % mod);
        }

        ar[0] %= mod;

        for (int i = 1; i < ar.length; i++)
            ar[i] = (ar[i - 1] + ar[i]) % mod;

        BSTAVL tree = new BSTAVL();
        tree.add(ar[0]);
        long max = ar[0];

        for (int i = 1; i < ar.length && max != mod - 1; i++) {
            if (ar[i] == mod - 1)
                return ar[i];

            long value = tree.search(ar[i] + 1, mod);
            value = mod + ar[i] - value;

            max = Math.max(max, value);
            tree.add(ar[i]);
        }

        return max;
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }

    final static class BSTAVL {
        public BSTAVL() {
            super();
        }

        public BSTAVL(long[] ar) {
            add(ar);
        }

        Node root;
        int size = 0;

        public void add(long n) {
            size++;
            if (root == null) {
                root = new Node(n);
                return;
            }
            root = add(root, n);
        }

        public void add(long[] ar) {
            for (long e : ar)
                add(e);
        }

        private static Node add(Node root, long n) {
            if (root == null) {
                root = new Node(n);
                return root;
            }
            if (root.data < n) {
                root.right = add(root.right, n);
                if (root.right.height - getHeight(root.left) == 2) {
                    if (root.right.data < n)
                        root = RotateRR(root);
                    else
                        root = RotateRL(root);
                }
                root.height =
                        Math.max(getHeight(root.left), getHeight(root.right)) +
                        1;
                return root;
            }

            root.left = add(root.left, n);
            if (root.left.height - getHeight(root.right) == 2) {
                if (root.left.data < n)
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
            left.height = Math.max(getHeight(left.left), root.height) + 1;
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
            right.height = Math.max(root.height, getHeight(right.right)) + 1;
            return right;
        }

        private static int getHeight(Node root) {
            if (root == null)
                return 0;
            return root.height;
        }

        private long search(long low, long mod) {
            Node temp = root;
            long res = mod;

            while (true) {
                if (low == temp.data)
                    return low;

                if (temp.data < low) {
                    if (temp.right == null)
                        return res;

                    temp = temp.right;
                } else {
                    res = Math.min(res, temp.data);

                    if (temp.left == null)
                        return res;

                    temp = temp.left;
                }
            }
        }

        final static class Node {
            Node left, right;
            long data;
            int height = 1;

            Node(long i) {
                data = i;
            }
        }
    }
}
