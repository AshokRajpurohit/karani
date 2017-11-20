/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.nov;

import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Subarray Queries
 * Link: https://www.codechef.com/NOV17/problems/CSUBQ
 * <p>
 * Chef has an array A consisting of N non-negative integers. Initially, all the elements are zero.
 * Assume 1-based indexing. Chef is given two positive integers L and R. (L <= R)
 * Chef has to execute Q number of queries on the array A. These queries can be of the following two types:
 * <p>
 * 1 x y (1 ≤ x ≤ n) - Replace the value of xth array element by y.
 * <p>
 * 2 l r (1 ≤ l ≤ r ≤ n) - Return the number of subarrays [a , b] that lies in subarray [l , r] such that
 * the value of the maximum array element in that subarray is atleast L and atmost R.
 * <p>
 * (A subarray [a , b] lies in a subarray [l , r] if and only if a >= l and b <= r)
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndSubarrayQueries {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int UPDATE = 1;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        play();
        int n = in.readInt(), q = in.readInt(), L = in.readInt(), R = in.readInt();
//        RangeQuery rangeQuery = new RangeQuery(L, R, n);
        BruteForce bruteForce = new BruteForce(n, L, R);
        StringBuilder sb = new StringBuilder(q << 2);

        while (q > 0) {
            q--;
            int type = in.readInt(), l = in.readInt(), r = in.readInt();
            if (type == UPDATE) {
                bruteForce.update(l - 1, r);
//                rangeQuery.update(l - 1, r);
            } else {
//                sb.append(rangeQuery.query(l - 1, r - 1)).append('\n');
                sb.append(bruteForce.query(l - 1, r - 1)).append('\n');
            }
        }

        out.print(sb);
    }

    private static void play() throws IOException {
        Output output = new Output();
        while (true) {
            out.println("Enter array size, L and R, and number of queries, valueLimit");
            out.flush();

            int size = in.readInt(), L = in.readInt(), R = in.readInt(), q = in.readInt();
            int[] type = Generators.generateRandomIntegerArray(q, 2),
                    left = Generators.generateRandomIntegerArray(q, size),
                    right = Generators.generateRandomIntegerArray(q, size),
                    values = Generators.generateRandomIntegerArray(q, in.readInt());

            BruteForce bruteForce = new BruteForce(size, L, R);
            RangeQuery query = new RangeQuery(L, R, size);

            for (int i = 0; i < q; i++) {
                if (type[i] == 1) {
                    bruteForce.update(left[i], values[i]);
                    query.update(left[i], values[i]);
                } else {
                    int li = Math.min(left[i], right[i]), ri = left[i] + right[i] - li;
                    long bv = bruteForce.query(li, ri), bq = query.query(li, ri);
                    if (bv != bq) {
                        output.println("Value mismatch: actual: " + bq + ", Expected: " + bv);
                        output.print(type);
                        output.print(left);
                        output.print(right);
                        output.print(values);
                        output.flush();
                        break;
                    }
                }
            }
        }
    }

    private static long subarrayCount(long size) {
        long value = size * (size + 1);
        return value >>> 1;
    }

    private static class BruteForce {
        final int[] ar;
        final int smallerPole, largerPole, size;

        BruteForce(int size, int s, int l) {
            ar = new int[size];
            this.size = size;
            smallerPole = s;
            largerPole = l;
        }

        private void update(int index, int value) {
            if (noDataChange(index, value))
                return;

            ar[index] = value;
        }

        private long query(int left, int right) {
            if (left > right)
                return 0;

            int nextLargerPole = Math.min(right + 1, nextLargerPole(left));
            long value = subarrayCount(nextLargerPole - left);
            int rightPole = Math.min(nextLargerPole, nextSmallerPole(left));
            while (true) {
                value -= subarrayCount(rightPole - left);
                left = rightPole + 1;

                if (rightPole == nextLargerPole)
                    break;

                rightPole = Math.min(nextLargerPole, nextSmallerPole(left));
            }

            return value + query(nextLargerPole + 1, right);
        }

        private int nextLargerPole(int index) {
            while (index < size && ar[index] <= largerPole) index++;

            return index;
        }

        private int nextSmallerPole(int index) {
            while (index < size && ar[index] <= smallerPole) index++;

            return index;
        }

        private boolean noDataChange(int index, int value) {
            return relativeValue(ar[index]) == relativeValue(value);
        }

        private int relativeValue(int value) {
            return value < smallerPole ? -1 : value > largerPole ? 1 : 0;
        }

    }

    private static class InputReader {
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

    /**
     * This class is for frequent updates of elements. It updates the element and
     * Structure in order of log(n). If updates are for in range then it's
     * better to use {@link com.ashok.lang.dsa.RangeQueryLazy} Lazy propagation.
     */
    private static class RangeQuery {
        private Node root;
        private final int[] ar;
        private static final int INVALID = -1;
        private final int smallerPoleValue, largerPoleValue;

        public RangeQuery(int smallerPoleValue, int largerPoleValue, int size) {
            ar = new int[size];
            this.smallerPoleValue = smallerPoleValue;
            this.largerPoleValue = largerPoleValue;
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

            return query(node.left, L, mid) + query(node.right, mid + 1, R) + operation(node.left, node.right, L, R);
        }

        public void update(int i, int data) {
            if (noDataChange(i, data))
                return;

            ar[i] = data;
            update(root, i);
        }

        private boolean noDataChange(int index, int value) {
            return relativeValue(ar[index]) == relativeValue(value);
        }

        private int relativeValue(int value) {
            return value < smallerPoleValue ? -1 : value > largerPoleValue ? 1 : 0;
        }

        private void update(Node root, int index) {
            if (root.l == root.r) {
                root.update();
                return;
            }

            int mid = (root.l + root.r) >>> 1;
            if (index > mid)
                update(root.right, index);
            else
                update(root.left, index);

            root.update();
        }

        private void construct(int[] ar) {
            root = new Node(0, ar.length - 1);
            int mid = (ar.length - 1) >>> 1;
            root.left = construct(ar, 0, mid);
            root.right = construct(ar, mid + 1, ar.length - 1);
        }

        private Node construct(int[] ar, int l, int r) {
            if (l == r)
                return new Node(l, l);

            Node node = new Node(l, r);
            int mid = (l + r) >>> 1;
            node.left = construct(ar, l, mid);
            node.right = construct(ar, mid + 1, r);
            return node;
        }

        private static int leftValue(int left, int right) {
            return left == INVALID ? right : left;
        }

        private static int rightValue(int left, int right) {
            return leftValue(right, left);
        }

        /**
         * Write your own operation while using it. It can be Math.min, max or add
         *
         * @param left
         * @param right
         * @return
         */
        private static long operation(Node left, Node right, int L, int R) {
            /*int leftBoundary = left.rightBigPole, rightBoundary = right.leftBigPole;

            if (leftBoundary == INVALID) leftBoundary = L - 1;
            if (rightBoundary == INVALID) rightBoundary = R + 1;

            long total = subarrayCount(rightBoundary - leftBoundary - 1);

            leftBoundary = left.rightSmallPole != INVALID ? left.rightSmallPole : leftBoundary;
            rightBoundary = right.leftSmallPole != INVALID ? right.leftSmallPole : rightBoundary;

            total -= subarrayCount(rightBoundary - leftBoundary - 1);
            total = Math.max(total, 0);
            return total;*/

            int leftBoundary = left.rightBigPole, rightBoundary = right.leftBigPole;

            leftBoundary = Math.max(leftBoundary, L - 1);
            rightBoundary = Math.min(rightBoundary, R + 1);

//            if (leftBoundary == INVALID) leftBoundary = L - 1;
            if (rightBoundary == INVALID) rightBoundary = R + 1;

            long total = (left.r - leftBoundary) * (rightBoundary - right.l);

            leftBoundary = left.rightSmallPole != INVALID ? left.rightSmallPole : leftBoundary;
            rightBoundary = right.leftSmallPole != INVALID ? right.leftSmallPole : rightBoundary;

            leftBoundary = Math.max(leftBoundary, L - 1);
            rightBoundary = Math.min(rightBoundary, R + 1);
            if (rightBoundary == INVALID) rightBoundary = R + 1;

            total -= (left.r - leftBoundary) * (rightBoundary - right.l);

            if (total < 0)
                throw new RuntimeException("Kuchh gadbad hai");

            return total;
        }

        final class Node {
            Node left, right;
            int l, r, rightBigPole = INVALID, rightSmallPole = INVALID, leftBigPole = INVALID, leftSmallPole = INVALID;
            long data = 0;

            Node(int m, int n) {
                l = m;
                r = n;
            }

            private void update() {
                if (l == r) {
                    int val = ar[l];
                    if (val > largerPoleValue) {
                        rightBigPole = l;
                        leftBigPole = l;
                    } else if (val >= smallerPoleValue) {
                        leftSmallPole = l;
                        rightSmallPole = l;
                        data = 1;
                    }

                    return;
                }

                leftBigPole = leftValue(left.leftBigPole, right.leftBigPole);
                rightBigPole = rightValue(left.rightBigPole, right.rightBigPole);
                leftSmallPole = leftValue(left.leftSmallPole, right.leftSmallPole);
                rightSmallPole = rightValue(left.rightSmallPole, right.rightSmallPole);
                data = calculate();
            }

            private long calculate() {
                long value = left.data + right.data;
                int leftBoundary = left.rightBigPole, rightBoundary = right.leftBigPole;

                if (leftBoundary == INVALID) leftBoundary = l - 1;
                if (rightBoundary == INVALID) rightBoundary = r + 1;

                long total = (left.r - leftBoundary) * (rightBoundary - right.l);

                leftBoundary = left.rightSmallPole != INVALID ? left.rightSmallPole : leftBoundary;
                rightBoundary = right.leftSmallPole != INVALID ? right.leftSmallPole : rightBoundary;

                total -= (left.r - leftBoundary) * (rightBoundary - right.l);
                total = Math.max(total, 0);
                return value + total;
            }

            public String toString() {
                return l + " -> " + r + ", " + data;
            }
        }
    }
}