package com.ashok.codechef.marathon.year15.AUG15;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Way Out
 * https://www.codechef.com/AUG15/problems/WOUT
 */

public class WOUT {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        WOUT a = new WOUT();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int h = in.readInt();
            int[] li = new int[n];
            int[] ri = new int[n];
            for (int i = 0; i < n; i++) {
                li[i] = in.readInt();
                ri[i] = in.readInt();
            }
            // you can use solve or process function.
            // you have two choice.
            sb.append(solve(n, h, li, ri)).append('\n');
        }
        out.print(sb);
    }

    /**
     * Let me Thank Chacha for the beautifull algorithm and obviously
     * explaination. Chacha in my last submission I used this function
     * and YES that is the fastest submission (in time consumption terms).
     * @param n
     * @param h
     * @param li
     * @param ri
     * @return
     */
    private static long solve(int n, int h, int[] li, int[] ri) {
        if (n == h)
            return leAll(n, li, ri);

        int[] low = new int[n + 1];
        int[] high = new int[n + 1];
        for (int i = 0; i < n; i++) {
            low[li[i]]++;
            high[ri[i] + 1]++;
        }

        int[] cost = new int[n];
        cost[0] = low[0] - high[0];
        for (int i = 1; i < n; i++) {
            cost[i] = cost[i - 1] + low[i] - high[i];
        }

        long res = 0, min = 0;
        for (int i = 0; i < h; i++)
            res += cost[i];

        min = res;
        for (int i = h; i < n; i++) {
            res += cost[i] - cost[i - h];
            min = Math.max(res, min);
        }

        return (long)n * h - min;
    }

    /**
     * This is my lengthy, complex, khichadi solution that I could come up.
     * to reduce time complexity from n^2 I used segment tree and again
     * with lazy propogation. And yes, finally I was able to get 100 marks
     * after many time reduction tekniks ( I chose to write tekniks instead
     * of technique as I don't like English). But from this solution, you
     * can know how segment tree with lazy propogation works.
     * @param n
     * @param h
     * @param li
     * @param ri
     * @return
     */
    private static long process(int n, int h, int[] li, int[] ri) {
        if (n == h)
            return leAll(n, li, ri);
        long res = 0;
        int[] cost = new int[n];
        for (int i = 0; i < n; i++)
            cost[i] = n;

        RangeQueryLazy rql = new RangeQueryLazy(cost);
        for (int i = 0; i < n; i++)
            rql.update(li[i], ri[i], -1);

        /**
         * let me say minIndex and maxIndex were created to
         * reduce the time complexity as I was getting TLEs in last test case.
         */
        int minIndex = n - h, maxIndex = 0;
        for (int i = 0; i < n; i++) {
            minIndex = Math.min(li[i], minIndex);
            maxIndex = Math.max(ri[i], maxIndex);
        }

        if (maxIndex - minIndex + 1 < h)
            maxIndex = minIndex + h - 1;

        for (int i = minIndex; i < minIndex + h; i++)
            res += rql.query(i);

        long min = res;
        for (int i = minIndex + h; i <= maxIndex; i++) {
            res += rql.query(i) - rql.query(i - h);
            min = Math.min(min, res);
        }

        return min;
    }

    /**
     * Oh this I created while using segment tree. I thought if he need the
     * whole matrix cleared then why to worry about which ones. Let's clear
     * them all and calculate the cost. leAll means le all again it's literal
     * meaning is "take all".
     * @param n
     * @param li
     * @param ri
     * @return
     */
    private static long leAll(int n, int[] li, int[] ri) {
        long res = 1L * n * n;
        for (int i = 0; i < n; i++)
            res -= (ri[i] + 1 - li[i]);

        return res;
    }

    /**
     * Let me introduce You to My own version of Segment Tree with lazy
     * propogation or we can say late update.
     */
    final static class RangeQueryLazy {
        private Node root;

        public RangeQueryLazy(int[] ar) {
            construct(ar);
        }

        public void update(int l, int r, int data) {
            update(root, l, r, data);
        }

        public long query(int index) {
            return query(root, index);
        }

        private static void update(Node root, int L, int R, long data) {
            if (data == 0)
                return;

            if (root.l == L && root.r == R) {
                root.udata += data;
                root.data += data;
                return;
            }
            int mid = (root.l + root.r) >>> 1;
            update(root.right, mid + 1, root.r, root.udata);
            update(root.left, root.l, mid, root.udata);
            root.udata = 0;

            if (L > mid) {
                update(root.right, L, R, data);
                root.data = root.left.data + root.right.data;
                return;
            }

            if (R <= mid) {
                update(root.left, L, R, data);
                root.data = root.left.data + root.right.data;
                return;
            }

            update(root.left, L, mid, data);
            update(root.right, mid + 1, R, data);
            root.data = root.left.data + root.right.data;
        }

        private static long query(Node root, int index) {
            if (root.l == root.r)
                return root.data;

            int mid = (root.l + root.r) >>> 1;
            update(root.right, mid + 1, root.r, root.udata);
            update(root.left, root.l, mid, root.udata);
            root.udata = 0;

            if (index > mid)
                return query(root.right, index);

            return query(root.left, index);
        }

        private void construct(int[] ar) {
            root = new Node(0, ar.length - 1, 0);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
            root.data = Math.min(root.left.data, root.right.data);
        }

        private Node construct(int[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l, ar[l]);

            Node temp = new Node(l, r, 0);
            int mid = (l + r) >>> 1;
            temp.left = construct(ar, l, mid);
            temp.right = construct(ar, mid + 1, r);
            temp.data = Math.min(temp.left.data, temp.right.data);
            return temp;
        }

        private final static class Node {
            Node left, right;
            int l, r;
            long data, udata;
            //        boolean update = false;

            Node(int i, int j, int d) {
                l = i;
                r = j;
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
    }
}
