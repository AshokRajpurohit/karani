/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.harsh;

import com.ashok.lang.dsa.RandomStrings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name:
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CureSkin {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        play();
        KVowels.solve();
//        SpecialArrayOperation.solve();
        in.close();
        out.close();
    }

    final static class KVowels {
        final static boolean[] SPECIAL_CHAR_MAP = new boolean[256];
        private static final char[] TO_LOWER_CASE = new char[256];
        private static final char[] VOWELS = new char[]{'a', 'e', 'i', 'o', 'u'};

        private int count = 0;
        private int[] countMap = new int[256];

        static {
            for (char vowel : VOWELS) {
                SPECIAL_CHAR_MAP[vowel] = true;
            }

            for (char i = 'a', j = 'A'; i <= 'z'; i++, j++) {
                TO_LOWER_CASE[i] = i;
                TO_LOWER_CASE[j] = i;
            }
        }

        private static void solve() throws IOException {
            int t = in.readInt();
            while (t > 0) {
                t--;
                int n = in.readInt(), k = in.readInt();
                out.println(bruteForce(in.read(n).toCharArray(), k));
            }
        }

        private static int bruteForce(char[] chars, int k) {
            toLowerCase(chars);
            int len = chars.length, max = 0;
            for (int i = 0; i < len; i++) {
                int count = 0, j = i;
                int[] map = new int[256];
                for (; j < len && count <= k; j++) {
                    char ch = chars[j];
                    if (!SPECIAL_CHAR_MAP[ch])
                        continue;

                    if (map[ch] == 0)
                        count++;

                    map[ch]++;
                }

                if (count > k)
                    j--;

                max = Math.max(max, j - i);
            }

            return max;
        }

        private static int bruteForce(char[] chars) {
            return 0;
        }

        private static int process(char[] chars, int k) {
            if (k == 0)
                return process(chars);

            KVowels kVowels = new KVowels();
            int start = 0, end = 0, len = chars.length;
            int max = 0;
            while (end < len) {
                while (end < len && kVowels.count <= k) {
                    kVowels.add(chars[end++]);
                }

                if (kVowels.count > k)
                    kVowels.remove(chars[--end]);

                if (kVowels.count != k)
                    break;

                max = Math.max(max, end - start);
                while (start < len && kVowels.count == k)
                    kVowels.remove(chars[start++]);
            }

            return max;
        }

        private static int process(char[] chars) {
            int start = 0, end = 0, len = chars.length, max = 0;
            while (start < len && SPECIAL_CHAR_MAP[chars[start]])
                start++;

            end = start;
            while (start < len) {
                while (end < len && !SPECIAL_CHAR_MAP[chars[end]])
                    end++;

                max = Math.max(end - start, max);
                while (end < len && SPECIAL_CHAR_MAP[chars[end]])
                    end++;

                start = end;
            }

            return max;
        }

        private void add(char ch) {
            if (!SPECIAL_CHAR_MAP[ch])
                return;

            if (countMap[ch] == 0)
                count++;

            countMap[ch]++;
        }

        private void remove(char ch) {
            if (!SPECIAL_CHAR_MAP[ch])
                return;

            if (countMap[ch] == 1)
                count--;

            countMap[ch]--;
        }

        private static void toLowerCase(char[] chars) {
            int len = chars.length;
            for (int i = 0; i < len; i++)
                chars[i] = TO_LOWER_CASE[chars[i]];
        }
    }

    final static class SpecialArrayOperation {

        private static void solve() throws IOException {
            int n = in.readInt(), m = in.readInt();
            int[] ar = in.readIntArray(n), br = in.readIntArray(m);
            Query[] queries = process(ar, br);
            Arrays.sort(queries, (a, b) -> a.id - b.id);
            StringBuilder sb = new StringBuilder(m << 2);
            for (Query query : queries)
                sb.append(query.result).append('\n');

            out.print(sb);
        }

        private static Query[] process(int[] ar, int[] br) {
            int n = ar.length, m = br.length;
            Query[] queries = getQueries(br);
            Arrays.sort(queries, (a, b) -> a.value - b.value);
            BinarySearchTree tree = new BinarySearchTree(ar);
            int k = 0;
            long sum = sum(ar);
            for (Query query : queries) {
                while (k < query.value) {
                    k++;
                    int max = tree.findMax(), min = tree.findMin(), diff = max - min;
                    sum += diff - max - min;
                    tree.remove(max);
                    tree.remove(min);
                    tree.add(diff);
                }

                query.result = sum;
            }

            return queries;
        }

        private static Query[] getQueries(int[] ar) {
            int n = ar.length;
            Query[] queries = new Query[n];
            for (int i = 0; i < n; i++)
                queries[i] = new Query(i, ar[i]);

            return queries;
        }

        private static long sum(int[] ar) {
            long res = 0;
            for (int e : ar)
                res += e;

            return res;
        }

    }

    final static class Query {
        final int id, value;
        long result = 0;

        Query(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    final static class BinarySearchTree {
        public BinarySearchTree() {
            super();
        }

        public BinarySearchTree(int[] ar) {
            add(ar);
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

        public void update(int oldVal, int newVal) {
            if (oldVal == newVal)
                return;

            root = delete(root, oldVal);
            root = add(root, newVal);
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

        public void add(int[] ar) {
            for (int e : ar)
                add(e);
        }

        public int findMin() {
            return findMin(root).data;
        }

        public int findMax() {
            return findMax(root).data;
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
                root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
                return root;
            }

            root.left = add(root.left, n);
            if (root.left.height - getHeight(root.right) == 2) {
                if (root.left.data < n)
                    root = RotateLR(root);
                else
                    root = RotateLL(root);
            }
            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            return root;
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
                        root = RotateLL(root);

                    root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
                    return root;
                } else if (root.right != null) {
                    Node del = findMin(root.right);
                    root.data = del.data;
                    root.right = delete(root.right, root.data);

                    if (getHeight(root.right) == 2)
                        root = RotateRR(root);

                    root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
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

                root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
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

            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            return root;
        }

        private static Node RotateLL(Node root) {
            if (root.left == null)
                return root;

            Node left = root.left;
            root.left = left == null ? left : left.right;
            left.right = root;
            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            left.height = Math.max(getHeight(left.left), root.height) + 1;
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
            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            right.height = Math.max(root.height, getHeight(right.right)) + 1;
            return right;
        }

        private static int getHeight(Node root) {
            if (root == null)
                return 0;
            return root.height;
        }
    }

    final static class Node {
        Node left, right;
        int data, height = 1;

        Node(int i) {
            data = i;
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