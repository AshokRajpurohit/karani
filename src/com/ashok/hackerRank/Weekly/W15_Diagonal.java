package com.ashok.hackerRank.Weekly;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Diagonal Queries
 * https://www.hackerrank.com/contests/w15/challenges/diagonal-queries
 */

public class W15_Diagonal {

    private static PrintWriter out;
    private static InputStream in;
    private long[] diag;
    private static int mod = 1000000007;
    private static int qc, qr, qs, qd;
    private RangeQueryUpdate rq;
    private int rows, cols;

    static {
        int temp = 'Q';
        qc = temp + 'c';
        qr = temp + 'r';
        qs = temp + 's';
        qd = temp + 'd';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        //        String input = "input_file.in", output = "output_file.out";
        //        FileInputStream fip = new FileInputStream(input);
        //        FileOutputStream fop = new FileOutputStream(output);
        //        in = fip;
        //        out = new PrintWriter(fop);

        W15_Diagonal a = new W15_Diagonal();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int m = in.readInt();
        int q = in.readInt();
        StringBuilder sb = new StringBuilder();
        diag = new long[n + m - 1];
        rows = n;
        cols = m;
        rq = new RangeQueryUpdate(diag);
        while (q > 0) {
            q--;
            int qu = in.read();
            if (qu == qc)
                column(in.readInt() - 1, in.readInt());
            else if (qu == qr)
                row(in.readInt() - 1, in.readInt());
            else if (qu == qs)
                cube(in.readInt(), in.readInt(), in.readInt(), in.readInt(),
                     in.readInt());
            else
                sb.append(solve(in.readInt() - 1,
                                in.readInt() - 1)).append('\n');
        }
        out.print(sb);
    }

    private long solve(int L, int R) {
        return rq.query(L, R);
    }

    private void column(int c, int d) {
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            sum = (sum + d) % mod;
            rq.update(rows + c - i - 1, sum);
        }
    }

    private void update(int r, int c, int d) {
        rq.update(rows + c - r - 1, d);
    }

    private void row(int r, int d) {
        int sum = 0;
        for (int i = 0; i < cols; i++) {
            sum = (sum + d) % mod;
            rq.update(rows + i - r - 1, sum);
        }
    }

    private void cube(int x1, int y1, int x2, int y2, int d) {
        int start = rows + y1 - x2 - 1, end = rows + y2 - x1 - 1;
        int sum = 0;
        for (; start < end; start++, end--) {
            sum = (sum + d) % mod;
            rq.update(start, sum);
            rq.update(end, sum);
        }
        if (sum == end) {
            rq.update(start, sum + d);
        }
    }

    class RangeQueryUpdate {
        private Node root;

        private RangeQueryUpdate() {
            super();
        }

        public RangeQueryUpdate(long[] ar) {
            construct(ar);
        }

        public long query(int L, int R) {
            return query(root, L, R);
        }

        private long query(Node node, int L, int R) {
            if (node.l == L && node.r == R)
                return node.data;

            int mid = (node.l + node.r) >>> 1;
            if (L > mid)
                return query(node.right, L, R);

            if (R <= mid)
                return query(node.left, L, R);

            return (query(node.left, L, mid) + query(node.right, mid + 1, R)) %
                mod;
        }

        public void update(int i, int data) {
            update(root, i, data);
        }

        private void update(Node root, int i, int data) {
            if (root.l == root.r) {
                root.data = (root.data + data) % mod;
                return;
            }

            int mid = (root.l + root.r) >>> 1;
            if (i > mid)
                update(root.right, i, data);
            else
                update(root.left, i, data);

            root.data = (root.left.data + root.right.data) % mod;
        }

        private void construct(long[] ar) {
            root = new Node(0, ar.length - 1);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
        }

        private Node construct(long[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l, ar[l]);

            Node temp = new Node(l, r);
            int mid = (l + r) >>> 1;
            temp.left = construct(ar, l, mid);
            temp.right = construct(ar, mid + 1, r);
            temp.data = Math.min(temp.left.data, temp.right.data);
            return temp;
        }

        class Node {
            Node left, right;
            int l, r;
            long data;

            Node(int m, int n) {
                l = m;
                r = n;
            }

            Node(int m, int n, long d) {
                l = m;
                r = n;
                data = d;
            }
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

        public int read() throws IOException {
            int number = 0;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                number += buffer[offset];
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return number;
        }
    }
}
