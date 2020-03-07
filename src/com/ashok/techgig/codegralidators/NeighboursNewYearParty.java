/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.techgig.codegralidators;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Neighbours and New Year Party
 * Link: Open Contest - Code Gladiators 2019
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class NeighboursNewYearParty {
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
            int n = in.readInt();
            int[] tickets = in.readIntArray(n);
            out.println(toString(getList(tickets)));
        }
    }

    private static Node getList(int[] tickets) {
        int len = tickets.length;
        Node prev1 = INVALID_NODE, prev2 = INVALID_NODE;
        List<Node> nodes = new LinkedList<>();
        for (int ticket : tickets) {
            Node temp = max(new Node(ticket, ticket), new Node(ticket, prev2), prev1, prev2);
            nodes.add(temp);
            prev2 = prev1;
            prev1 = temp;
        }

        return nodes.stream().max(NODE_COMPARATOR).orElse(INVALID_NODE);
    }

    private static Node max(Node... nodes) {
        return Arrays.stream(nodes).max(NODE_COMPARATOR).get();
    }

    private static String toString(Node node) {
        StringBuilder sb = new StringBuilder();
        while (node != INVALID_NODE) {
            sb.append(node.value);
            node = node.prev;
        }

        return sb.toString();
    }

    public static final Comparator<Node> NODE_COMPARATOR = (a, b) -> a.sum != b.sum ? a.sum - b.sum : a.value - b.value;

    private static final Node INVALID_NODE = new Node(-1, 0);

    final static class Node {
        final int value, sum;
        Node prev = INVALID_NODE;

        Node(int value, int sum) {
            this.value = value;
            this.sum = sum;
        }

        Node(int value, Node prev) {
            this(value, value + prev.sum);
            this.prev = prev;
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