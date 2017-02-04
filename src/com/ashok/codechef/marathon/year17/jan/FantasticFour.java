/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.jan;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem Name: Fantastic Four
 * Link: https://www.codechef.com/JAN17/problems/FOURSQ
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class FantasticFour {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static long mod = 0;
    private static BigInteger BIG_MOD;
    private static int[] ar;
    private static final int REPLACE = 1, QUERY = 2;
    private static final Map<Long, Quadruple> cache = new HashMap<>();

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
            mod = in.readLong();
            BIG_MOD = new BigInteger(String.valueOf(mod));
            ar = in.readIntArray(n);//Generators.generateRandomIntegerArray(n, 1, 1000);
            StringBuilder sb = new StringBuilder(q << 3);

            RangeQueryUpdate rq = new RangeQueryUpdate(ar);

            while (q > 0) {
                q--;
                int type = in.readInt();

                if (type == REPLACE) {
                    int index = in.readInt() - 1, value = in.readInt();
                    rq.update(index, value);
                } else {
                    int start = in.readInt() - 1, end = in.readInt() - 1;
                    sb.append(rq.query(start, end)).append('\n');
                }
            }

            out.print(sb);
        }
    }

    private static Quadruple getQuadruple(long n) {
        if (cache.containsKey(n))
            return cache.get(n);

        Quadruple quadruple = new Quadruple(n);
        cache.put(n, quadruple);

        return quadruple;
    }

    /**
     * This method is now used only for inverse modulo calculation.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static long gcd(long a, long b) {
        if (a == 0)
            return b;

        return gcd(b % a, a);
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @return
     */
    public static long inverseModulo(long a) {
        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0)
            return a;

        long b = mod - 1;
        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return res;
    }

    final static class Quadruple {
        int a, b, c, d;

        Quadruple(long n) {
            double dmin = Math.sqrt(n / 4);
            d = (int) Math.sqrt(n);
            long v = n - d * d;
            long d2 = d * d;
            c = (int)Math.sqrt(v);
            v -= d2;

            while (v != 0 && d >= dmin) {
                while (v % 4 >= 2 && c > 0) {
                    c--;
                    v =  n - d2 - c * c;
                }

                d--;
                d2 = d * d;
                v = n - d * d;
            }
        }

        Quadruple(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    /**
     * This class is for frequent updates of elements. It updates the element and
     * Structure in order of log(n). If updates are for in range then it's
     * better to use {@link RangeQueryLazy} Lazy propagation.
     */
    final static class RangeQueryUpdate {
        private Node root;

        public RangeQueryUpdate(int[] ar) {
            construct(ar);
        }

        public long query(int L, int R) {
            if (R < L)
                return query(R, L);

            if (R > root.r || L < 0)
                throw new IndexOutOfBoundsException(L + ", " + R);

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

            return operation(query(node.left, L, mid),
                    query(node.right, mid + 1, R));
        }

        public void update(int i, int data) {
            if (i > root.r || i < 0)
                throw new IndexOutOfBoundsException(i + "");

            update(root, i, data);
        }

        private void update(Node root, int i, int data) {
            if (root.l == root.r) {
                root.data = updateOperation(root.data, data);
                return;
            }

            int mid = (root.l + root.r) >>> 1;
            if (i > mid)
                update(root.right, i, data);
            else
                update(root.left, i, data);

            root.data = operation(root.left.data, root.right.data);
        }

        private void construct(int[] ar) {
            root = new Node(0, ar.length - 1);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
            root.data = operation(root.left.data, root.right.data);
        }

        private Node construct(int[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l, ar[l]);

            Node temp = new Node(l, r);
            int mid = (l + r) >>> 1;
            temp.left = construct(ar, l, mid);
            temp.right = construct(ar, mid + 1, r);
            temp.data = operation(temp.left.data, temp.right.data);
            return temp;
        }

        /**
         * Write your own operation while using it. It can be Math.min, max or add
         *
         * @param a
         * @param b
         * @return
         */
        private static long operation(long a, long b) {
            if (a == 0 || b == 0)
                return 0;

            if (b > Long.MAX_VALUE / a)
                return useBigNumbers(a, b);

            return a * b % mod;
        }

        private static long useBigNumbers(long a, long b) {
            long gcd = gcd(a, b);
            a /= gcd;
            b /= gcd;

            if (b > Long.MAX_VALUE / a || gcd > Long.MAX_VALUE / gcd) {
                a *= gcd;
                b *= gcd;

                BigInteger res = new BigInteger(String.valueOf(a)).multiply(new BigInteger(String.valueOf(b)));
                return Long.valueOf(res.mod(BIG_MOD).toString());
            }

            long res = a * b % mod;
            return res * (gcd * gcd % mod) % mod;
        }

        /**
         * Update function, for the existing data a and update query data b,
         * returns the new value for existing data. This can be replacement,
         * addition, subtraction, multiplication or anything desired.
         *
         * @param a old value
         * @param b value to be updated on the node.
         * @return new value for the node.
         */
        private static long updateOperation(long a, long b) {
            return b;
        }

        final static class Node {
            Node left, right;
            int l, r;
            long data;

            Node(int m, int n) {
                l = m;
                r = n;
            }

            Node(int m, int n, int d) {
                l = m;
                r = n;
                data = d;
            }
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
    }
}
