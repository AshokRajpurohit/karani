package com.ashok.codechef.marathon.year15.OCT15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Problem: Cyclic shifts in permutation
 * https://www.codechef.com/OCT15/problems/PERSHFTS
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class PERSHFTS {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] fact;
    private static int mod = 1000000007;
    private static int[] map;

    private static void format(int n) {
        //        long t = System.currentTimeMillis();
        Integer[] ar = new Integer[n + 1];
        ar[0] = 0;
        for (int i = 1; i < ar.length; i++)
            ar[i] = i;

        Arrays.sort(ar, Lex);
        map = new int[ar.length];
        for (int i = 1; i < ar.length; i++)
            map[ar[i]] = i;

        fact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++)
            fact[i] = fact[i - 1] * i % mod;
        //        System.out.println(System.currentTimeMillis() - t);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PERSHFTS a = new PERSHFTS();
        a.solve();
        out.close();
    }

    private static Integer[] gen_rand(int n) {
        Random random = new Random();
        Integer[] ar = new Integer[n];
        for (int i = 0; i < n; i++)
            ar[i] = 1 + random.nextInt(n);

        return ar;
    }

    private static void print(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length * 3);
        for (int e : ar)
            sb.append(e).append(", ");
        System.out.println(sb);
    }

    private static void print(Integer[] ar) {
        StringBuilder sb = new StringBuilder(ar.length * 3);
        for (int i = 0; i < ar.length; i++)
            sb.append(ar[i]).append(", ");
        sb.append('\n');
        System.out.print(sb);
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        format(1000000);

        while (t > 0) {
            t--;
            int n = in.readInt(), k = in.readInt();
            //            Integer[] ar = gen_rand(n);
            //            Arrays.sort(ar, Lex);
            //            print(ar);
            //            int[] p = in.readIntArray(n);
            //            int[] q = in.readIntArray(n);
            sb.append(process(in.readIntArray(n), in.readIntArray(n),
                              k)).append('\n');
        }
        out.print(sb);
    }

    private static long process(int[] p, int[] q, int k) {
        if (p.length == 1)
            return 1;

        if (k == p.length) {
            if (cyclic(p, q))
                return indexOf(q);
            else
                return -1;
        }

        if (k == 1)
            if (isEqual(p, q))
                return indexOf(q);
            else
                return -1;

        return indexOf(q);
    }

    private static boolean isEqual(int[] p, int[] q) {
        for (int i = 0; i < p.length; i++)
            if (p[i] != q[i])
                return false;

        return true;
    }

    private static boolean cyclic(int[] p, int[] q) {
        if (p.length < 3)
            return true;

        int i = 0, j = 0;
        while (i < p.length && p[i] != q[0])
            i++;

        for (; i < p.length; i++, j++)
            if (p[i] != q[j])
                return false;

        for (i = 0; j < p.length; i++, j++)
            if (p[i] != q[j])
                return false;

        return true;
    }

    private static long indexOf(int[] ar) {
        long res = 1;
        int i = ar.length - 1;
        BSTAVL bt = new BSTAVL();
        bt.add(ar[i]);
        i--;
        int j = 1;
        for (; i >= 0; i--, j++) {
            res += fact[j] * bt.getIndex(ar[i]) % mod;
            bt.add(ar[i]);
        }

        return res;
    }

    final static COMP Lex = new COMP();

    final static class COMP implements Comparator<Integer> {

        public int compare(Integer a, Integer b) {
            if (a == b)
                return 0;

            if (a < 10 && b < 10)
                return a - b;

            int ca = 10, cb = 10;
            int r = 10;
            while (ca <= a)
                ca = (ca << 3) + (ca << 1);

            while (cb <= b)
                cb = (cb << 3) + (cb << 1);

            if (ca > cb)
                b *= (ca / cb);
            else
                a *= (cb / ca);

            return a - b;

            //            return compare(a.toString(), b.toString());

            //            String s1 = String.valueOf(a);
            //            String s2 = String.valueOf(b);
            //            int minLen = s1.length() < s2.length() ? s1.length() : s2.length();
            //            for (int i = 0; i < minLen; i++)
            //                if (s1.charAt(i) != s2.charAt(i))
            //                    return s1.charAt(i) - s2.charAt(i);
            //
            //            return s1.length() - s2.length();
        }

        private int compare(String s1, String s2) {
            int minLen = s1.length() < s2.length() ? s1.length() : s2.length();
            for (int i = 0; i < minLen; i++)
                if (s1.charAt(i) != s2.charAt(i))
                    return s1.charAt(i) - s2.charAt(i);

            return s1.length() - s2.length();
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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

    final static class BSTAVL {
        public BSTAVL() {
            super();
        }

        public BSTAVL(int[] ar) {
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

        private static Node add(Node root, int n) {
            if (root == null) {
                root = new Node(n);
                return root;
            }
            if (Lex.compare(root.data, n) < 0) {
                root.right = add(root.right, n);
                //                if (root.right.height - getHeight(root.left) == 2) {
                //                    if (Lex.compare(root.right.data, n) < 0)
                //                        root = RotateRR(root);
                //                    else
                //                        root = RotateRL(root);
                //                }
                //                root.height =
                //                        Math.max(getHeight(root.left), getHeight(root.right)) +
                //                        1;
                return root;
            }

            root.left = add(root.left, n);
            root.count++;
            //            if (root.left.height - getHeight(root.right) == 2) {
            //                if (Lex.compare(root.left.data, n) < 0)
            //                    root = RotateLR(root);
            //                else
            //                    root = RotateLL(root);
            //            }
            //            root.height =
            //                    Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            return root;
        }

        private static Node RotateLL(Node root) {
            Node left = root.left;
            root.left = left.right;
            left.right = root;
            root.height =
                    Math.max(getHeight(root.left), getHeight(root.right)) + 1;
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
            root.height =
                    Math.max(getHeight(root.left), getHeight(root.right)) + 1;
            right.height = Math.max(root.height, getHeight(right.right)) + 1;
            return right;
        }

        private static int getHeight(Node root) {
            if (root == null)
                return 0;
            return root.height;
        }

        public boolean find(int n) {
            Node temp = root;
            while (true) {
                if (n == temp.data)
                    return true;
                if (Lex.compare(n, temp.data) > 0) {
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

        public int getIndex(int n) {
            Node temp = root;
            int count = 0;
            while (true) {
                if (Lex.compare(n, temp.data) > 0) {
                    count += temp.count + 1;
                    if (temp.right == null)
                        return count;
                    temp = temp.right;
                } else {
                    if (temp.left == null)
                        return count;
                    temp = temp.left;
                }
            }
        }

        public void add(int[] ar) {
            for (int e : ar)
                add(e);
        }

        final static class Node {
            Node left, right;
            int count = 0, data, height = 1;

            Node(int i) {
                data = i;
            }
        }
    }
}
