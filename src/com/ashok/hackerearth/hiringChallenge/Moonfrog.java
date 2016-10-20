package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Moonfrog Labs Backend Hiring Challenge
 * Link: https://www.hackerearth.com/moonfrog-backend-hiring-challenge
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Moonfrog {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        VanyaArray.solve();
        in.close();
        out.close();
    }

    private static class VanyaArray {
        static void solve() throws IOException {
            int n = in.readInt(), k = in.readInt();
            int[] ar = in.readIntArray(n);
            int[] f_ar = new int[n];

            if (n == 1) {
                out.println(0);
                return;
            }

            BSTbyNode tree = new BSTbyNode();
            for (int i = n - 1; i >= 0; i--) {
                f_ar[i] = tree.add(ar[i]);
            }

            Arrays.sort(f_ar);

            long res = 0;
            int i = 0, j = n - 1;

            if (f_ar[n - 1] + f_ar[n - 2] < k) {
                out.println(0);
                return;
            }

            while (i != j) {
                if (f_ar[i] + f_ar[j] < k) {
                    res += n - 1 - j;
                    i++;
                    continue;
                }

                res += n - j;
                j--;
            }

            out.println(res);
        }
    }

    private static class BSTbyNode {
        Node root;
        int size = 0;

        public int add(int n) {
            size++;

            if (root == null) {
                root = new Node(n);
                return 0;
            }

            Node temp = root;
            int count = 0;
            while (true) {
                temp.size++;
                if (n > temp.data) {
                    if (temp.right == null) {
                        temp.right = new Node(n);
                        return count;
                    }

                    temp = temp.right;
                } else {
                    count += getSize(temp.right);
                    if (temp.data > n)
                        count++;

                    if (temp.left == null) {
                        temp.left = new Node(n);
                        return count;
                    }
                    temp = temp.left;
                }
            }
        }

        private int getSize(Node node) {
            if (node == null)
                return 0;

            return node.size;
        }

        class Node {
            Node left, right;
            int data, size = 1;

            Node(int i) {
                data = i;
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
    }
}
