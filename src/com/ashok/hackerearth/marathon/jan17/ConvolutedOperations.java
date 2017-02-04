/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.jan17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Convoluted Operations
 * Link: https://www.hackerearth.com/challenge/competitive/january-circuits-17/algorithm/convoluted-operations/
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ConvolutedOperations {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static Operation[] operations;
    private static final int PUSH = 1, POP = 0, QUERY = 2;
    private static List<Operation> queries;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int ops = in.readInt();
        operations = new Operation[ops];

        for (int i = 0; i < ops; i++) {
            int type = in.readInt();
            Operation operation = new Operation(i, type);

            if (type == PUSH)
                operation.value = in.readInt();
            else if (type == QUERY) {
                operation.index = in.readInt() - 1;
                operation.value = in.readInt();
            }

            operations[i] = operation;
        }

        process();
    }

    private static void process() {
        queries = getQueries();
        List<Operation> queries = getIndexOrderedQueries();

        int[] res = new int[operations.length];
        int index = 0;
        BSTAVL tree = new BSTAVL();
        LinkedList<Integer> stack = new LinkedList<>();

        for (Operation operation : queries) {
            while (index <= operation.index) {
                int type = operations[index].type, value = operations[index].value;
                if (type == PUSH) {
                    tree.add(value);
                    stack.addFirst(value);
                } else if (type == POP)
                    tree.remove(stack.removeFirst());

                index++;
            }

            res[operation.id] = tree.countLesserItems(operation.value);
        }

        StringBuilder sb = new StringBuilder(queries.size() * 5);
        queries = getOrderedQueries();

        for (Operation operation : queries)
            sb.append(res[operation.id]).append('\n');

        out.print(sb);
    }

    private static List<Operation> getIndexOrderedQueries() {
        Collections.sort(queries, new Comparator<Operation>() {
            @Override
            public int compare(Operation o1, Operation o2) {
                if (o1.index == o2.index)
                    return o1.id - o2.id;

                return o1.index - o2.index;
            }
        });

        return queries;
    }

    private static List<Operation> getOrderedQueries() {
        Collections.sort(queries, new Comparator<Operation>() {
            @Override
            public int compare(Operation o1, Operation o2) {
                return o1.id - o2.id;
            }
        });

        return queries;
    }

    private static List<Operation> getQueries() {
        LinkedList<Operation> queries = new LinkedList<>();

        for (Operation operation : operations)
            if (operation.type == QUERY)
                queries.add(operation);

        return queries;
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
    }

    private static Node findMin(Node node) {
        while (node.left != null)
            node = node.left;

        return node;
    }

    private static Node findMax(Node node) {
        while (node.right != null)
            node = node.right;

        return node;
    }

    private static Node delete(Node root, int n) {
        if (root.data == n) {
            if (root.left != null) {
                Node del = findMax(root.left);
                root.data = del.data;
                root.left = delete(root.left, root.data);

                if (getHeight(root.left) - getHeight(root.right) == -2)
                    root = RotateRR(root);

                root.update();
                return root;
            } else if (root.right != null) {
                Node del = findMin(root.right);
                root.data = del.data;
                root.right = delete(root.right, root.data);

                if (getHeight(root.right) == 2)
                    root = RotateRR(root);

                root.update();
                return root;
            }

            return null;
        }

        if (root.data < n) {
            root.right = delete(root.right, n);

            if (getHeight(root.left) - getHeight(root.right) == 2) {
                if (getHeight(root.left.left) > getHeight(root.left.right))
                    root = RotateLL(root);
                else
                    root = RotateLR(root);
            } else if (getHeight(root.left) - getHeight(root.right) == -2) {
                if (getHeight(root.right.right) > getHeight(root.right.left))
                    root = RotateRR(root);
                else
                    root = RotateRL(root);
            }

            root.update();
            return root;
        }

        root.left = delete(root.left, n);

        if (getHeight(root.left) - getHeight(root.right) == 2) {
            if (getHeight(root.left.left) > getHeight(root.left.right))
                root = RotateLL(root);
            else
                root = RotateLR(root);
        } else if (getHeight(root.left) - getHeight(root.right) == -2) {
            if (getHeight(root.right.right) > getHeight(root.right.left))
                root = RotateRR(root);
            else
                root = RotateRL(root);
        }

        root.update();
        return root;
    }

    private static Node RotateLL(Node root) {
        Node left = root.left;
        root.left = left.right;
        left.right = root;
        root.update();
        left.update();
        return left;
    }

    private static Node RotateLR(Node root) {
        root.left = RotateRR(root.left);
        return RotateLL(root);
    }

    private static Node RotateRL(Node root) {
        root.right = RotateLL(root.right);
        return RotateRR(root);
    }

    private static Node RotateRR(Node root) {
        Node right = root.right;
        root.right = right.left;
        right.left = root;
        root.update();
        right.update();
        return right;
    }

    private static int getHeight(Node root) {
        if (root == null)
            return 0;

        return root.height;
    }

    private static int getCount(Node node) {
        if (node == null)
            return 0;

        return node.count;
    }

    final static class BSTAVL {
        public BSTAVL() {
            // do nothing.
        }

        Node root;
        int size = 0;

        public void add(int n) {
            size++;
            if (root == null) {
                root = new Node(n);
                return;
            }

            root = add(root, n);
        }

        public void remove(int n) {
            root = delete(root, n);
        }

        public boolean contains(int n) {
            Node temp = root;
            while (true) {
                if (n == temp.data)
                    return true;
                if (n > temp.data) {
                    if (temp.right == null)
                        return false;
                    temp = temp.right;
                } else {
                    if (temp.left == null)
                        return false;
                    temp = temp.left;
                }
            }
        }

        public int countLesserItems(int n) {
            int count = 0;
            Node temp = root;
            while (temp != null) {
                if (n > temp.data) {
                    count += getCount(temp.left) + 1;
                    temp = temp.right;
                } else {
                    temp = temp.left;
                }
            }

            return count + getCount(temp);
        }

        private static Node add(Node root, int n) {
            if (root == null) {
                root = new Node(n);
                return root;
            }
            if (root.data < n) {
                root.right = add(root.right, n);
                if (root.right.height - getHeight(root.left) == 2) {
                    if (root.right.data < n)
                        root = RotateRR(root);
                    else
                        root = RotateRL(root);
                }

                root.update();
                return root;
            }

            root.left = add(root.left, n);
            if (root.left.height - getHeight(root.right) == 2) {
                if (root.left.data < n)
                    root = RotateLR(root);
                else
                    root = RotateLL(root);
            }

            root.update();
            return root;
        }
    }

    final static class Node {
        Node left, right;
        int data, height = 1, count = 1;

        Node(int i) {
            data = i;
        }

        void update() {
            updateHeight();
            updateCount();
        }

        void updateHeight() {
            height = 1 + Math.max(getHeight(left), getHeight(right));
        }

        void updateCount() {
            count = 1 + getCount(left) + getCount(right);
        }

        public String toString() {
            return "value: " + data + ", height: " + height + ", count: " + count;
        }
    }

    final static class Operation {
        final int id, type;
        int index = 0, value = 0;

        Operation(int id, int t) {
            this.id = id;
            type = t;
        }

        Operation(int id, int t, int v1, int v2) {
            this.id = id;
            type = t;
            index = v1;
            value = v2;
        }

        public String toString() {
            return id + ", " + type + ", " + index + ", " + value;
        }
    }
}
