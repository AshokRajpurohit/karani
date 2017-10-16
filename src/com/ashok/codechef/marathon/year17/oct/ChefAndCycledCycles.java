/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.oct;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Cycled Cycles
 * Link: https://www.codechef.com/OCT17/problems/CHEFCCYL
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndCycledCycles {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

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
            StringBuilder sb = new StringBuilder(q << 2);
            CycleFactory factory = new CycleFactory(n);
            CycleOfCyles cycleOfCyles = new CycleOfCyles(factory);

            for (int i = 0; i < n; i++) {
                Cycle cycle = factory.newCycle(in.readInt());
                cycle.edges = in.readIntArray(cycle.size);
                cycle.process();
            }

            for (int i = 0; i < n; i++)
                factory.get(i).connection = new Connection(in.readInt() - 1, in.readInt() - 1, in.readInt(), factory.get(i + 1));

            cycleOfCyles.process();
            while (q > 0) {
                q--;
                sb.append(cycleOfCyles.query(in.readInt() - 1, in.readInt() - 1, in.readInt() - 1, in.readInt() - 1)).append('\n');
            }

            out.print(sb);
        }
    }

    final static class CycleOfCyles {
        final CycleFactory factory;
        final int[] edges; // edges between Cycles
        private CycleQueryProcessor queryProcessor;

        CycleOfCyles(CycleFactory factory) {
            this.factory = factory;
            edges = new int[factory.size];
        }

        /**
         * Populates edges and prepares query processor.
         */
        void process() {
            int len = edges.length;
            Cycle cycle = factory.get(0);
            int startNode = factory.get(edges.length - 1).connection.targetNode;
            edges[0] = cycle.connection.edgeWeight + cycle.queryProcessor.queryOptimum(startNode - 1, cycle.connection.sourceNode - 1);
            int currentNode = cycle.connection.targetNode;
            for (int i = 1; i < len; i++) {
                cycle = factory.get(i);

                // edge index is one minus the node index for query processing.
                edges[i] = cycle.queryProcessor.queryOptimum(currentNode - 1, cycle.connection.sourceNode - 1) + cycle.connection.edgeWeight;
                currentNode = cycle.connection.targetNode;
            }

            queryProcessor = new CycleQueryProcessor(edges);
        }

        private int query(int v1, int c1, int v2, int c2) {
            return Math.min(calculate(v1, c1, v2, c2), calculate(v2, c2, v1, c1));
        }

        /**
         * Calculates minmum path value when travelled from cycle c1 to cycle c2 in the natural direction.
         *
         * @param v1 start point
         * @param c1 first cycle
         * @param v2 end point
         * @param c2 final cycle
         * @return minimum path value.
         */
        private int calculate(int v1, int c1, int v2, int c2) {
            int value = queryProcessor.query(c1 - 1, c2 - 1);
            Cycle first = factory.get(c1);
            int startNode = factory.get(c1 - 1).connection.targetNode;
            value -= first.queryProcessor.queryOptimum(startNode - 1, first.connection.sourceNode - 1);
            value += first.queryProcessor.queryOptimum(v1 - 1, first.connection.sourceNode - 1);

            Cycle last = factory.get(c2);
            int lastRefNode = factory.get(c2 - 1).connection.targetNode;
            return value + last.queryProcessor.queryOptimum(v2 - 1, lastRefNode - 1);
        }
    }

    final static class CycleFactory {
        private int index = 0;
        final Cycle[] cycles;
        final int size;

        CycleFactory(int size) {
            this.size = size;
            cycles = new Cycle[size];
        }

        private Cycle newCycle(int size) {
            Cycle cycle = new Cycle(index, size);
            cycles[index++] = cycle;
            return cycle;
        }

        private Cycle get(int id) {
            return cycles[id == size ? 0 : id < 0 ? id + size : id];
        }
    }

    final static class Cycle {
        final int id, size;
        int[] edges;
        private Connection connection;
        private CycleQueryProcessor queryProcessor;

        Cycle(int id, int size) {
            this.id = id;
            this.size = size;
        }

        void process() {
            queryProcessor = new CycleQueryProcessor(edges);
        }

        private void setConnection(Connection connection) {
            this.connection = connection;
        }

        public String toString() {
            return "Cycle " + id;
        }
    }

    final static class Connection {
        final int sourceNode, targetNode, edgeWeight;
        final Cycle target;

        Connection(int sourceNode, int targetNode, int edgeWeight, Cycle target) {
            this.sourceNode = sourceNode;
            this.targetNode = targetNode;
            this.edgeWeight = edgeWeight;
            this.target = target;
        }
    }

    final static class CycleQueryProcessor {
        final int[] sum;
        final int querySum, size;

        CycleQueryProcessor(int[] ar) {
            size = ar.length;
            sum = new int[size];

            sum[0] = ar[0];
            for (int i = 1; i < size; i++) {
                sum[i] = sum[i - 1] + ar[i];
            }

            querySum = sum[size - 1];
        }

        private int query(int from, int to) {
            if (from < 0)
                from += size;

            if (to < 0)
                to += size;

            if (from >= size)
                from -= size;

            if (to >= size)
                to -= size;

            int value = sum[to] - sum[from];
            return value < 0 ? value + querySum : value;
        }

        private int queryOptimum(int from, int to) {
            int value = query(from, to);
            return Math.min(value, querySum - value);
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