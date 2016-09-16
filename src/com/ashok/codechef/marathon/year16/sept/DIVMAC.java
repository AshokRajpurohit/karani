package com.ashok.codechef.marathon.year16.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Dividing Machine
 * Link: https://www.codechef.com/SEPT16/problems/DIVMAC
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class DIVMAC {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] factor, factorLength;

    static {
        int limit = 1000001;

        factor = new int[limit];
        factor[0] = 1;
        factor[1] = 1;

        for (int i = 2; i < 1001; i++) {
            if (factor[i] != 0)
                continue;

            for (int j = i; j < limit; j += i)
                if (factor[j] == 0)
                    factor[j] = i;
        }

        for (int i = 1001; i < limit; i++) {
            if (factor[i] == 0)
                factor[i] = i;
        }

        factorLength = new int[limit];
        factorLength[2] = 1;

        for (int i = 3; i < limit; i++) {
            factorLength[i] = 1 + factorLength[i / factor[i]];
        }
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;

            int n = in.readInt(), m = in.readInt();
            int[] ar = in.readIntArray(n);

            RangeQueryLazy rq = new RangeQueryLazy(ar);
            while (m > 0) {
                m--;

                if (in.readInt() == 0)
                    rq.update(in.readInt() - 1, in.readInt() - 1);
                else
                    sb.append(rq.query(in.readInt() - 1, in.readInt() - 1)).append(' ');
            }

            sb.append('\n');
        }

        out.print(sb);
    }

    final static class RangeQueryLazy {
        Node root;

        RangeQueryLazy(int[] ar) {
            root = new Node();
            root.l = 0;
            root.r = ar.length - 1;

            int mid = root.r >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, root.r);

            format(root, ar);
        }

        private int query(int start, int end) {
            return query(root, start, end);
        }

        private static int query(Node node, int start, int end) {
            if (node.maxValue == 1)
                return 1;

            if (node.l == start && node.r == end) {
                return node.maxValue;
            }

            int mid = (node.l + node.r) >>> 1;
            int value = 1;

            if (mid >= start)
                value = query(node.left, start, Math.min(mid, end));

            if (mid < end)
                value = Math.max(value, query(node.right, Math.max(start, mid + 1), end));

            return value;
        }

        private void update(int start, int end) {
            update(root, start, end);
        }

        private static void update(Node node, int start, int end) {
            if (node.maxValue == 1)
                return;

            if (node.l == node.r) {
                if (node.length == 1) {
                    node.maxValue = 1;
                    node.length = 0;
                    return;
                }

                node.value /= node.maxValue;
                node.maxValue = factor[node.value];
                node.length--;
                return;
            }

            if (node.l == start && node.r == end) {
                if (node.length == 1) {
                    node.maxValue = 1;
                    node.length = 0;
                    return;
                }

                update(node.left);
                update(node.right);
                node.maxValue = Math.max(node.left.maxValue, node.right.maxValue);
                node.length = Math.max(node.left.length, node.right.length);

                return;
            }

            int mid = (node.l + node.r) >>> 1;

            if (mid >= start)
                update(node.left, start, Math.min(mid, end));

            if (mid < end)
                update(node.right, Math.max(start, mid + 1), end);

            node.maxValue = Math.max(node.left.maxValue, node.right.maxValue);
            node.length = Math.max(node.left.length, node.right.length);
        }

        private static void update(Node node) {
            if (node.length == 0)
                return;

            if (node.l == node.r) {
                node.value /= node.maxValue;
                node.maxValue = factor[node.value];
                node.length--;
                return;
            }

            update(node.left);
            update(node.right);

            node.maxValue = Math.max(node.left.maxValue, node.right.maxValue);
            node.length = Math.max(node.left.length, node.right.length);

            return;
        }

        private static Node construct(int[] ar, int start, int end) {
            if (start == end)
                return newNode(ar, start);

            Node node = new Node();
            node.l = start;
            node.r = end;

            int mid = (start + end) >>> 1;
            node.left = construct(ar, start, mid);
            node.right = construct(ar, mid + 1, end);

            format(node, ar);

            return node;
        }

        private static Node newNode(int[] ar, int index) {
            Node node = new Node();
            node.l = index;
            node.r = index;
            node.value = ar[index];
            node.maxValue = factor[node.value];
            node.length = factorLength[node.value];

            return node;
        }

        private static void format(Node root, int[] ar) {
            if (root.l == root.r) {
                root.value = ar[root.l];
                root.length = factorLength[root.value];
                root.maxValue = factor[root.value];
                return;
            }

            root.length = Math.max(root.left.length, root.right.length);
            root.maxValue = Math.max(root.left.maxValue, root.right.maxValue);
        }
    }

    final static class Node {
        Node left, right;
        int l, r;
        int value = 1, maxValue = 1, length = 0;
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
