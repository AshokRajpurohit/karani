/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Problem Name:
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SamsungIndia {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        BeautyOfArray.solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;

        }
    }

    private static int getMax(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(e, max);

        return max;
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @param mod
     * @return
     */
    private static long pow(long a, long b, long mod) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }

        if (res < 0) res += mod;
        return res;
    }

    private static long[] getPowerArray(long base, int n, long mod) {
        long[] powers = new long[n];
        powers[0] = 1;
        for (int i = 1; i < n; i++) {
            powers[i] = powers[i - 1] * base % mod;
        }

        return powers;
    }

    private static TreeNode[] generateTree(int[] values, Edge[] edges) {
        int n = values.length;
        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new TreeNode(i + 1, values[i]);

        TreeNode[] adjacancyMatrix = new TreeNode[n + 1];

        for (Edge edge : edges) {
            // write code here TODO:
        }

        return nodes;
    }

    final static class BeautyOfArray {
        private static final long MOD = 1000000007;

        private static void solve() throws IOException {
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int[] map = getMap(ar);
            long[] twoPowers = getPowerArray(2, n + 1, MOD);

            int len = map.length;
            long res = 0;
            for (int i = 1; i < len; i++) {
                if (map[i] == 0)
                    continue;

                int a = map[i], count = 0;
                res += (twoPowers[a] - 1 - a) * i % MOD;
                for (int j = i + 1; j < len; j++) {
                    if (map[j] == 0)
                        continue;

                    int b = map[j];
                    long sets = ((twoPowers[a] - 1) * (twoPowers[b] - 1) % MOD) * twoPowers[count] % MOD;
                    res += sets * (i | j) % MOD;
                    count += b;
                }
            }

            out.println(res % MOD);
        }

        private static int[] getMap(int[] ar) {
            int max = getMax(ar);
            int[] res = new int[max + 1];
            for (int e : ar)
                res[e]++;

            return res;
        }
    }

    final static class TreeStockMarket {
        private static void solve() throws IOException {
            int n = in.readInt(), q = in.readInt();
            Edge[] edges = new Edge[n - 1];
            for (int i = 0; i < n - 1; i++)
                edges[i] = new Edge(in.readInt(), in.readInt());

            int[] values = in.readIntArray(n);
            TreeNode[] nodes = generateTree(values, edges);
        }
    }

    final static class Edge {
        final int a, b;

        Edge(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    final static TreeNode INVALID = new TreeNode(-1, -1);

    final static class TreeNode {
        final int id, value;
        TreeNode parent = INVALID;
        List<TreeNode> childNodes = new LinkedList<>();

        TreeNode(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    final static class TreeNodeMap<V> {
        final int size;
        final V[] values;

        TreeNodeMap(int size, IntFunction<V[]> generator, V defaultValue) {
            this.size = size;
            values = generator.apply(size);
            Arrays.fill(values, defaultValue);
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