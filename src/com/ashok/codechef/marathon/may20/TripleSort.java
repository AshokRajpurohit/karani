/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.may20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Problem Name: Triple Sort
 * Link: https://www.codechef.com/MAY20A/problems/TRPLSRT
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class TripleSort {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final Predicate<String> SUCCESS = s -> s.contains("-1");


    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        test();
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            long k = in.readLong();
            int[] ar = in.readIntArray(n);
            String response = process(k, ar);
            out.println(response);
        }
    }

    private static void test() throws IOException {
        while (true) {
            int n = in.readInt();
            while (true) {
                int[] ar = IntStream.range(1, n + 1).toArray();
                long time = System.currentTimeMillis();
                randomize(ar);
                out.println("randomized ");
                out.println("randomization took " + (System.currentTimeMillis() - time) + " ms");
                time = System.currentTimeMillis();
                String res = process(n * n, ar);
                out.println("execution time " + (System.currentTimeMillis() - time) + " ms");
                out.flush();
                if (SUCCESS.test(res)) {
                    out.println("failed: \n" + res);
                    out.flush();
                    process(n * n, ar);
                    break;
                }
            }
        }
    }

    private static void randomize(int[] ar) {
        Random random = new Random();
        int n = ar.length;
        int limit = (int) (n * Math.sqrt(n));
        for (int i = 0; i < limit; i++) {
            int a = random.nextInt(n);
            int b = random.nextInt(n);
            while (b == a) b = random.nextInt(n);
            int c = random.nextInt(n);
            while (c == a || c == b) c = random.nextInt(n);
            rotate(new Rotation(a, b, c), ar);
        }
    }

    private static String process(long k, int[] ar) {
        int n = ar.length;
        int[] permutation = new int[n + 1];
        for (int i = 0; i < n; i++) permutation[i + 1] = ar[i];
        List<Rotation> ops = new ArrayList();
        perform3PointRotations(ops, permutation);
        performPairOps(ops, permutation);
        if (ops.size() > k || !IS_SORTED.test(permutation)) {
            return "-1";
        }

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(ops.size() + "");
        for (Rotation op : ops) joiner.add(op.toString());
        return joiner.toString();
    }

    private static void perform3PointRotations(List<Rotation> ops, int[] permutation) {
        int n = permutation.length;
        // performing rotaions which does not excluding reversed pairs (a -> b -> a)
        for (int i = 1; i < n; ) {
            if (permutation[i] == i) {
                i++;
                continue;
            }
            int a = i, b = permutation[a], c = permutation[b];
            if (a == c) { // reverse pair, let's ignore as of now.
                i++;
                continue;
            }
            // now let's shift a to b, b to c and c to a.
            Rotation rotation = new Rotation(a, b, c);
            rotate(rotation, permutation);
            ops.add(rotation);
        }
    }

    private static void performPairOps(List<Rotation> ops, int[] permutation) {
        int n = permutation.length;
        int index = 0;
        List<Pair> pairs = getReversePairs(permutation);
        if (pairs.isEmpty()) return;
        if (!isEven(pairs.size())) {
            if (pairs.size() > 2) {
                index = 3;
                perform3PairOps(ops, permutation, pairs.subList(0, 3));
            } else if (n > 6) {// at least 6 elements
                // first select any 4 elements and make two pairs and reverse them using two pair approach.
                // now we have 3 pairs of swaps and we can perform #perform3PairOps to correct it.
                int[] ar = IntStream.range(1, n).filter(i -> i == permutation[i]).limit(4).toArray();
                Pair a = new Pair(ar[0], ar[1]), b = new Pair(ar[2], ar[3]);
                performTwoPairOp(ops, permutation, a, b);
                perform3PairOps(ops, permutation, Arrays.asList(a, b, pairs.get(0)));
                index++;
            } else {
                return; // nothing can be done for this permutation.
            }
        }

        while (index < pairs.size()) {
            performTwoPairOp(ops, permutation, pairs.get(index++), pairs.get(index++));
        }
    }

    private static void perform3PairOps(List<Rotation> ops, int[] permutation, List<Pair> pairs) {
        if (pairs.size() != 3) throw new RuntimeException("something is wrong, debug and find out.");
        Pair p1 = pairs.get(0), p2 = pairs.get(1), p3 = pairs.get(2);
        Rotation r = new Rotation(p1.a, p1.b, p2.a);
        ops.add(r);
        rotate(r, permutation);
        r = new Rotation(p2.b, p3.a, p3.b);
        ops.add(r);
        rotate(r, permutation);
        performTwoPairOp(ops, permutation, new Pair(p1.a, p2.a), new Pair(p2.b, p3.a));
    }

    private static List<Pair> getReversePairs(int[] permutation) {
        int n = permutation.length;
        List<Pair> pairs = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (permutation[i] == i) continue;
            int b = permutation[i], a = permutation[b];
            if (a != i || b < a) continue;
            pairs.add(new Pair(a, b));
        }

        return pairs;
    }

    private static void performTwoPairOp(List<Rotation> rotations, int[] permutation, Pair p1, Pair p2) {
        Rotation r = new Rotation(p1.a, p1.b, p2.a);
        rotations.add(r);
        rotate(r, permutation);
        r = new Rotation(p1.a, p2.b, p2.a);
        rotations.add(r);
        rotate(r, permutation);
    }

    private static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    /**
     * Performs the rotations, which include 3 elements which all can be placed at their correct position
     * in single rotation, in other words, the 3 elements form a cycle like a -> b -> c -> a
     *
     * @param ops
     * @param permutation
     */
    private static void performCyclicRotations(List<Rotation> ops, int[] permutation) {
        int n = permutation.length;
        for (int i = 1; i < n; i++) {
            if (permutation[i] == i) continue;
            int b = permutation[i], c = permutation[b];
            if (i != permutation[c]) continue; // c's final destination should be i.

            Rotation rotation = new Rotation(i, b, c);
            rotate(rotation, permutation);
            ops.add(rotation);
        }
    }

    private static void rotate(Rotation r, int[] ar) {
        int temp = ar[r.b];
        ar[r.b] = ar[r.a];
        int t = ar[r.c];
        ar[r.c] = temp;
        ar[r.a] = t;
    }

    private static final Predicate<int[]> IS_SORTED = ar -> {
        for (int i = 1; i < ar.length; i++) if (ar[i] < ar[i - 1]) return false;
        return true;
    };

    final static class Rotation {
        final int a, b, c;

        Rotation(final int a, final int b, final int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public String toString() {
            return a + " " + b + " " + c;
        }
    }

    final static class Pair {
        final int a, b;

        Pair(final int a, final int b) {
            this.a = a;
            this.b = b;
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