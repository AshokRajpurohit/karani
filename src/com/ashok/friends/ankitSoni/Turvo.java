/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankitSoni;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Problem Name:
 * Link:
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Turvo {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        MergingConglomerates.solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.println(in.readInt());
            out.flush();
        }
    }

    final static class MergingConglomerates {
        private static final int MERGE = 77;

        private static void solve() throws IOException {
            int n = in.readInt(), q = in.readInt();
            Conglomerate[] conglomerates = new Conglomerate[n];
            for (int i = 0; i < n; i++)
                conglomerates[i] = new Conglomerate();


            StringBuilder sb = new StringBuilder();

            while (q > 0) {
                q--;
                int type = in.readInt();
                if (type == MERGE) {
                    int first = in.readInt() - 1, second = in.readInt() - 1;
                    Conglomerate firstC = conglomerates[first],
                            secondC = conglomerates[second];

                    long total = firstC.size + secondC.size;
                    firstC.size = total;

                    conglomerates[second] = firstC;
                } else {
                    Conglomerate conglomerate = conglomerates[in.readInt() - 1];
                    sb.append(conglomerate.size).append('\n');
                }
            }

            out.print(sb);
        }
    }

    final static class Conglomerate {
        long size = 1;
    }

    final static class BobAndTrucks {
        private static final String yes = "TRUE\n", no = "FALSE\n";

        private static void solve() throws IOException {
            int t = in.readInt();
            StringBuilder sb = new StringBuilder();

            while (t > 0) {
                t--;

                int p = in.readInt(), r = in.readInt();
                Pair[] pairs = new Pair[r];

                for (int i = 0; i < r; i++)
                    pairs[i] = new Pair(in.readInt(), in.readInt());

                sb.append(process(p, pairs) ? yes : no);
            }

            out.print(sb);
        }

        private static boolean process(int p, Pair[] pairs) {
            LinkedList<Integer>[] adjacencyList = getAdjacencyList(pairs, p);
            int[] groupIdMap = new int[p];

            Pair[] indexNodeCountMap = new Pair[p];
            for (int i = 0; i < p; i++)
                indexNodeCountMap[i] = new Pair(i, adjacencyList[i].size());

            Arrays.sort(indexNodeCountMap, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    return o2.b - o1.b;
                }
            });

            for (Pair pair : indexNodeCountMap) {
                if (groupIdMap[pair.a] != 0)
                    continue;

                if (!colorGraph(adjacencyList, groupIdMap, pair.a, 1))
                    return false;
            }

            return true;
        }

        private static boolean colorGraph(LinkedList<Integer>[] adjacencyList, int[] colorMap, int index, int color) {
            if (colorMap[index] != 0)
                return colorMap[index] == color;

            colorMap[index] = color;
            color = -color;

            for (int nextNode : adjacencyList[index]) {
                if (colorGraph(adjacencyList, colorMap, nextNode, color))
                    continue;

                return false;
            }

            return true;
        }

        private static LinkedList<Integer>[] getAdjacencyList(Pair[] edges, int nodes) {
            LinkedList<Integer>[] adjacencyList = new LinkedList[nodes];
            for (int i = 0; i < nodes; i++)
                adjacencyList[i] = new LinkedList<>();

            for (Pair edge : edges)
                adjacencyList[edge.a].add(edge.b);

            return adjacencyList;
        }
    }

    final static class Pair {
        int a, b;

        Pair(int a, int b) {
            if (a < b) {
                this.a = a;
                this.b = b;
            } else {
                this.a = b;
                this.b = a;
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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
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
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String readLine() throws IOException {
            StringBuilder sb = new StringBuilder();
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
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
