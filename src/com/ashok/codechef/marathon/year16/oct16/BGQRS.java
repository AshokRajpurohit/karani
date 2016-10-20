package com.ashok.codechef.marathon.year16.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Big Queries
 * Link: https://www.codechef.com/OCT16/problems/BGQRS
 * <p>
 * Concept: Number of trailing zeroes is minimum of 2's power and
 * 5's power. We know a single zero is formed by one 2 and one 5.
 * So we have to count 2's and 5's in the range and update the same.
 * <p>
 * In the replace query, we need to replace elements by
 * Y, Y*2, Y*3,.., Y*n
 * <p>
 * think of this replace query in this way:
 * 1. replace elements by 1, 2, 3, .., n
 * 2. multiply all the elements in the range by Y
 * <p>
 * So we need to know the number of trailing zeroes in factorial
 * (or product of consecutive elements).
 * <p>
 * Now you can go through the code.
 * The code is self-explainatory.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BGQRS {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static Multiplier[] factorials = new Multiplier[100001];

    static {
        int[] twos = new int[factorials.length], fives = new int[factorials.length];

        twos[2] = 1;
        fives[5] = 1;

        for (int i = 4; i < factorials.length; i += 2) {
            twos[i] = twos[i >>> 1] + 1;
        }

        for (int i = 10; i < factorials.length; i += 5)
            fives[i] = fives[i / 5] + 1;

        for (int i = 1; i < factorials.length; i++) {
            twos[i] += twos[i - 1];
            fives[i] += fives[i - 1];
        }

        for (int i = 0; i < factorials.length; i++)
            factorials[i] = new Multiplier(twos[i], fives[i]);
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int multi = 1, replace = 2, query = 3;
        int t = in.readInt();

        while (t > 0) {
            t--;

            int n = in.readInt(), q = in.readInt();
            int[] ar = in.readIntArray(n);
            long ans = 0;

            RangeQueryLazy rq = new RangeQueryLazy(ar);

            while (q > 0) {
                q--;

                int type = in.readInt();
                if (type == multi)
                    rq.multiply(in.readInt() - 1, in.readInt() - 1, in.readInt());
                else if (type == replace)
                    rq.replace(in.readInt() - 1, in.readInt() - 1, in.readInt());
                else
                    ans += rq.query(in.readInt() - 1, in.readInt() - 1);
            }

            out.println(ans);
        }
    }

    private static int getTwos(long n) {
        if (n < 2)
            return 0;

        int count = 0;
        while ((n & 1) == 0) {
            count++;
            n = n >>> 1;
        }

        return count;
    }

    private static int getFives(long n) {
        if (n < 5)
            return 0;

        int count = 0;

        while (n % 5 == 0) {
            count++;
            n /= 5;
        }

        return count;
    }

    private static void populateMultiplierForRange(Multiplier multiplier, int start, int n) {
        int b = start + n - 1;
        multiplier.twos += factorials[b].twos - factorials[start - 1].twos;
        multiplier.fives += factorials[b].fives - factorials[start - 1].fives;
    }

    /**
     * Returns power of Two in the multiplication of all integers between
     * parameters start and end both inclusive.
     *
     * @param start
     * @param end
     * @return Returns the exponent of factor Two in the multiplication.
     */
    private static long getTwos(int start, int end) {
        return factorials[end].twos - factorials[start - 1].twos;
    }

    /**
     * Returns power of Five in the multiplication of all integers between
     * parameters start and end both inclusive.
     *
     * @param start
     * @param end
     * @return Returns the exponent of factor Five in the multiplication.
     */
    private static long getFives(int start, int end) {
        return factorials[end].fives - factorials[start - 1].fives;
    }

    /**
     * Returns true if the number is equivalent to unit(1) with respect to
     * Two and Five.
     *
     * @param multiplier
     * @return true if powers equal to 0.
     */
    private static boolean isUnit(Multiplier multiplier) {
        return multiplier.twos + multiplier.fives == 0;
    }

    final static class RangeQueryLazy {
        private Node root;
        private Multiplier result = new Multiplier(0, 0),
                value = new Multiplier(0, 0);

        public RangeQueryLazy(int[] ar) {
            construct(ar);
        }

        /**
         * Multiplies all the numbers between indices start and end
         * Multiplies the specified int data to each element of the specified
         * range of the specified array of ints.
         * The range to be updated extends from
         * index <tt>l</tt>, inclusive, to index
         * <tt>r</tt>, inclusive.
         *
         * @param l
         * @param r
         * @param data
         */
        public void multiply(int l, int r, int data) {
            Arrays.fill(new int[10], 1, 2, 3);
            value.reset();
            value.multiply(data);

            if (isUnit(value))
                return;

            multiply(root, l, r, value);
        }

        public void replace(int L, int R, int data) {
            value.reset();
            value.multiply(data);
            replace(root, L, R, value, 1);
        }

        private static void replace(Node node, int L, int R, Multiplier value, int start) {
            if (node == null)
                return;

            if (node.l == L && node.r == R) {
                node.data.reset();
                populateMultiplierForRange(node.data, start, R + 1 - L);
                node.data.multiply(value, R + 1 - L);
                node.multiply = true;
                node.replace = true;
                node.start = start;
                node.multiplier.reset();
                node.multiplier.multiply(value);
                return;
            }

            update(node);
            int mid = (node.l + node.r) >>> 1;

            if (L > mid)
                replace(node.right, L, R, value, start);
            else if (R <= mid)
                replace(node.left, L, R, value, start);
            else {
                replace(node.left, L, mid, value, start);
                replace(node.right, mid + 1, R, value, start + mid + 1 - L);
            }

            node.data.reset();
            node.data.multiply(node.left.data);
            node.data.multiply(node.right.data);
        }

        /**
         * Multiplies the specified Multiplier value to each element of the specified
         * range of the specified array of ints.
         * The range to be updated extends from
         * index <tt>L</tt>, inclusive, to index
         * <tt>R</tt>, inclusive.
         *
         * @param L
         * @param R
         * @param value
         */
        private static void multiply(Node node, int L, int R, Multiplier value) {
            node.data.multiply(value, R + 1 - L);

            if (node.l == L && node.r == R) {
                node.multiplier.multiply(value);
                node.multiply = true;
                return;
            }

            replace(node);
            int mid = (node.l + node.r) >>> 1;

            if (L > mid)
                multiply(node.right, L, R, value);
            else if (R <= mid)
                multiply(node.left, L, R, value);
            else {
                multiply(node.left, L, mid, value);
                multiply(node.right, mid + 1, R, value);
            }
        }

        /**
         * Multiplies all the nodes under the specified <<>node</>
         * with <<>multiplier</> and at the same time update the data
         * value in node.
         *
         * @param node
         * @param multiplier
         */
        private static void multiply(Node node, Multiplier multiplier) {
            if (isUnit(multiplier))
                return;

            int len = node.r + 1 - node.l;
            node.data.multiply(multiplier, len);
            node.multiplier.multiply(multiplier);

            node.multiply = true;
        }

        public long query(int L, int R) {
            result.reset();
            query(root, L, R, result);

            return Math.min(result.twos, result.fives);
        }

        /**
         * Populates the {@code Multiplier} <<tt>result</tt> with
         * power of Two's and Five's when all the numbers from index
         * <<tt>L</tt> inclusive, to index <<tt>R</tt>.
         *
         * @param root
         * @param L
         * @param R
         * @param result
         */
        private static void query(Node root, int L, int R, Multiplier result) {
            if (root == null)
                return;

            if (root.l == L && root.r == R) {
                result.multiply(root.data);
                return;
            }

            // this multiplier is anyways going to be multilied
            // to all the child nodes irrespective or L and R (range)
            // so let's multiply it here and don't update children.

            result.multiply(root.multiplier, R + 1 - L);
            if (root.replace) {
                // if the query is replacement for all childs, we can get the
                // value directly, no need to go to further down.

                int start = root.start + L - root.l, end = root.start + R - root.l;
                long twos = getTwos(start, end), fives = getFives(start, end);
                result.multiply(twos, fives);
                return;
            }

            int mid = (root.l + root.r) >>> 1;

            // replace query can change the data on child nodes and
            // that is going to affect our query result.
            replace(root);

            if (L > mid) {
                query(root.right, L, R, result);
                return;
            }

            if (R <= mid) {
                query(root.left, L, R, result);
                return;
            }

            query(root.left, L, mid, result);
            query(root.right, mid + 1, R, result);

        }

        private static void replace(Node node, int start) {
            node.start = start;
            node.replace = true;
            node.multiply = false;
            node.multiplier.reset();
            node.data.reset();

            populateMultiplierForRange(node.data, start, node.r + 1 - node.l);
        }

        private static void replace(Node node) {
            if (!node.replace)
                return;

            node.replace = false;
            int mid = (node.l + node.r) >>> 1;

            replace(node.left, node.start);
            replace(node.right, node.start + mid + 1 - node.l);
        }

        private static void update(Node node) {
            int mid = (node.l + node.r) >>> 1;
            if (node.replace) {
                replace(node.left, node.start);
                replace(node.right, node.start + mid + 1 - node.l);
            }

            if (node.multiply && !isUnit(node.multiplier)) {
                multiply(node.left, node.multiplier);
                multiply(node.right, node.multiplier);
            }

            node.replace = false;
            node.multiply = false;
            node.multiplier.reset();
        }

        private void construct(int[] ar) {
            root = construct(ar, 0, ar.length - 1);
            if (ar.length == 1)
                return;

            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
            operation(root);
        }

        private Node construct(int[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l, ar[l]);

            Node temp = new Node(l, r, 0);
            int mid = (l + r) >>> 1;
            temp.left = construct(ar, l, mid);
            temp.right = construct(ar, mid + 1, r);
            operation(temp);
            return temp;
        }

        private static void operation(Node node) {
            node.data.reset();
            node.data.multiply(node.multiplier, node.r + 1 - node.l);
            node.data.multiply(node.left.data);
            node.data.multiply(node.right.data);
        }

        private static long operation(long a, long b) {
            return a > b ? b : a;
        }

    }

    /**
     * The Node is the basic unit to represent the range or
     * single number for RangeQueryLazy data structure.
     * <p>
     * data property is to hold result for the range.
     * multiplier is the value to be multiplied to all the members
     * in the range.
     */
    private final static class Node {
        Node left, right;
        int l, r;
        Multiplier multiplier, data;
        int start = 0;
        boolean multiply, replace;

        Node(int i, int j, int d) {
            l = i;
            r = j;
            multiplier = new Multiplier(0, 0);
            data = new Multiplier(d);
        }
    }

    /**
     * Class to hold Two's and Five's power.
     */
    private final static class Multiplier {
        long twos = 0, fives = 0;

        Multiplier(long n) {
            twos = getTwos(n);
            fives = getFives(n);
        }

        Multiplier(long a, long b) {
            twos = a;
            fives = b;
        }

        void multiply(long n) {
            twos += getTwos(n);
            fives += getFives(n);
        }

        void multiply(long two, long five) {
            twos += two;
            fives += five;
        }

        void multiply(Multiplier multiplier) {
            twos += multiplier.twos;
            fives += multiplier.fives;
        }

        void multiply(Multiplier multiplier, int count) {
            twos += multiplier.twos * count;
            fives += multiplier.fives * count;
        }

        void reset() {
            twos = 0;
            fives = 0;
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
 