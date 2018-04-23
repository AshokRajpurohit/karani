/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.tridip;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Algorythma Software Developer Hiring Test Set 2
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Algorythma {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ClosestDivisor.solve();
//        DivisibleSubsequence.solve();
//        AppTest.solve();
        in.close();
        out.close();
    }

    private static int max(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(max, e);

        return max;
    }

    private static int[] toArray(LinkedList<Integer> list) {
        int[] ar = new int[list.size()];
        int index = 0;
        for (int e : list)
            ar[index++] = e;

        return ar;
    }

    private static LinkedList<Integer>[] getListArray(int size) {
        LinkedList<Integer>[] lists = new LinkedList[size];
        for (int i = 0; i < size; i++)
            lists[i] = new LinkedList<>();

        return lists;
    }

    final static class ClosestDivisor {
        private static final int LIMIT = 100000;

        private static void solve() throws IOException {
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            StringBuilder sb = new StringBuilder(n << 2);
            int[] res = process(ar);
            for (int e : res)
                sb.append(e).append('\n');

            out.print(sb);
        }

        private static int[] process(int[] ar) {
            int n = ar.length;
            int max = max(ar);
            LinkedList<Integer>[] divisors = getListArray(max + 1);

            LinkedList<Integer>[] indices = getListArray(max + 1);
            for (int i = 0; i < n; i++) {
                indices[ar[i]].add(i);
            }

            for (int i = 1; i <= max; i++)
                for (int index : indices[i]) {
                    for (int j = i; j <= max; j += i) {
                        divisors[j].add(index);
                    }
                }

            int[][] dis = new int[max + 1][];
            for (int i = 0; i <= max; i++) {
                dis[i] = toArray(divisors[i]);
                Arrays.sort(dis[i]);
            }

            int[] res = new int[n];
            int index = 0;
            for (int e : ar) {
                int j = Arrays.binarySearch(dis[e], index + 1);
                res[index++] = j >= 0 ? ar[j] : -1;
            }

            return new int[n];
        }
    }

    final static class AppTest {
        private static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt();
            int[] ar = in.readIntArray(n);
            Node[] nodes = new Node[n];
            for (int i = 0; i < n; i++) {
                nodes[i] = new Node(i + 1, ar[i]);
            }

            for (int i = 0; i < m; i++) {
                int u = in.readInt() - 1, v = in.readInt() - 1;
                nodes[u].dependencies.add(nodes[v]);
            }

            process(nodes);
            StringBuilder sb = new StringBuilder(n << 2);
            for (Node node : nodes) {
                sb.append(node.allResolved == 1 ? "YES" : "NO").append('\n');
            }

            out.print(sb);
        }

        private static void process(Node[] nodes) {
            for (Node node : nodes) {
                process(node);
            }
        }

        private static void process(Node node) {
            if (node.allResolved != -1 || !node.passed)
                return;

            node.dependencies.forEach((t) -> process(t));
            boolean res = true;
            for (Node dependency : node.dependencies)
                res = res && dependency.allResolved == 1;

            node.allResolved = res ? 1 : 0;
        }
    }

    final static class Node {
        final int id;
        boolean passed;
        List<Node> dependencies = new LinkedList<>();
        int allResolved = -1;

        Node(int id, int result) {
            this.id = id;
            passed = result == 1;
        }
    }

    final static class DivisibleSubsequence {
        private static final int MOD = 1000000007;

        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt();
                out.println(process(in.read(n).toCharArray()));
            }
        }

        private static long process(char[] digits) {
            long[] map = new long[101], copy = new long[101];
            for (char digit : digits) {
                int d = digit - '0';
                map[d]++;
                for (int i = 0, j = d; i < 101; i++, j += 10) { // j = 10i + d
                    if (j >= 101) j -= 101;
                    map[j] = (map[j] + copy[i]) % MOD; // original value of map[j] and plus map[i] as i is also giving j as remainder.
                }

                System.arraycopy(map, 0, copy, 0, 101);
            }

            return map[0];
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