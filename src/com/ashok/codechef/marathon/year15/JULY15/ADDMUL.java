package com.ashok.codechef.marathon.year15.JULY15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Addition and Multiplication
 * http://www.codechef.com/JULY15/problems/ADDMUL
 */

public class ADDMUL {

    private static PrintWriter out;
    private static InputStream in;
    private Node root;
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        ADDMUL a = new ADDMUL();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 2);
        construct(in.readIntArray(n));

        while (q > 0) {
            q--;
            int t = in.readInt();

            if (t == 1)
                add(in.readInt() - 1, in.readInt() - 1, in.readInt());
            else if (t == 2)
                multi(in.readInt() - 1, in.readInt() - 1, in.readInt());
            else if (t == 3)
                replace(in.readInt() - 1, in.readInt() - 1, in.readInt());
            else
                sb.append(query(in.readInt() - 1,
                                in.readInt() - 1)).append('\n');
        }

        out.print(sb);
    }

    private long query(int L, int R) {
        return query(root, L, R);
    }

    private static long query(Node root, int L, int R) {
        if (root.L == L && root.R == R)
            return root.sum;

        int mid = (root.L + root.R) >>> 1;
        updateChild(root);

        if (L > mid)
            return query(root.right, L, R);

        if (R <= mid)
            return query(root.left, L, R);

        return (query(root.left, L, mid) + query(root.right, mid + 1, R)) %
            mod;
    }

    /**
     * Increments all the integers from index l to index r both inclusive
     * by add.
     * @param l start index in the array
     * @param r end index in the array
     * @param add number to be added to each integer.
     */
    private void add(int l, int r, int add) {
        add(root, l, r, add);
    }

    /**
     * Increments all the numbers from index L to index R by add in the
     * structure.
     * @param root
     * @param L
     * @param R
     * @param add
     */
    private static void add(Node root, int L, int R, long add) {
        if (root.L == root.R) {
            root.sum = (root.sum + add) % mod;
            return;
        }

        if (root.L == L && root.R == R) {
            addChild(root, add);
            return;
        }

        updateChild(root);
        int mid = (root.L + root.R) >>> 1;

        if (L > mid) {
            add(root.right, L, R, add);
            root.sum = (root.left.sum + root.right.sum) % mod;
            return;
        }

        if (R <= mid) {
            add(root.left, L, R, add);
            root.sum = (root.left.sum + root.right.sum) % mod;
            return;
        }

        add(root.left, L, mid, add);
        add(root.right, mid + 1, R, add);
        root.sum = (root.left.sum + root.right.sum) % mod;
    }

    private void multi(int L, int R, long multi) {
        multi(root, L, R, multi);
    }

    private static void multi(Node root, int L, int R, long multi) {
        if (multi == 1)
            return;

        if (multi == 0) {
            replace(root, L, R, multi);
            return;
        }

        if (root.L == root.R) {
            root.sum = (root.sum * multi) % mod;
            return;
        }

        if (root.L == L && root.R == R) {
            multiChild(root, multi);
            return;
        }

        updateChild(root);
        int mid = (root.L + root.R) >>> 1;

        if (L > mid) {
            multi(root.right, L, R, multi);
            root.sum = (root.left.sum + root.right.sum) % mod;
            return;
        }

        if (R <= mid) {
            multi(root.left, L, R, multi);
            root.sum = (root.left.sum + root.right.sum) % mod;
            return;
        }

        multi(root.left, L, mid, multi);
        multi(root.right, mid + 1, R, multi);
        root.sum = (root.left.sum + root.right.sum) % mod;
    }

    private void replace(int L, int R, int value) {
        replace(root, L, R, value);
    }

    private static void replace(Node root, int L, int R, long value) {
        if (root.L == L && root.R == R) {
            root.sum = (R + 1 - L) * value % mod;
            root.update = value;
            root.replace = true;
            root.add = false;
            root.multi = false;
            return;
        }

        updateChild(root);
        int mid = (root.L + root.R) >>> 1;

        if (L > mid) {
            replace(root.right, L, R, value);
            root.sum = (root.left.sum + root.right.sum) % mod;
            return;
        }

        if (R <= mid) {
            replace(root.left, L, R, value);
            root.sum = (root.left.sum + root.right.sum) % mod;
            return;
        }

        replace(root.left, L, mid, value);
        replace(root.right, mid + 1, R, value);
        root.sum = (root.left.sum + root.right.sum) % mod;
    }

    private static void updateChild(Node root) {
        if (root.L == root.R)
            return;

        if (root.replace) {
            replaceChild(root.left, root.update);
            replaceChild(root.right, root.update);
            root.replace = false;
            return;
        }

        if (root.add) {
            addChild(root.left, root.update);
            addChild(root.right, root.update);
            root.add = false;
            return;
        }

        if (root.multi) {
            multiChild(root.left, root.update);
            multiChild(root.right, root.update);
            root.multi = false;
            return;
        }
    }

    private static void addChild(Node root, long add) {
        if (root.L == root.R) {
            root.sum = (root.sum + add) % mod;
            return;
        }

        root.sum = (root.sum + (root.R + 1 - root.L) * add) % mod;

        if (root.add || root.replace) {
            root.update = (root.update + add) % mod;
            return;
        }

        if (root.multi) {
            multiChild(root.left, root.update);
            multiChild(root.right, root.update);
            root.multi = false;
        }

        root.update = add;
        root.add = true;
    }

    private static void multiChild(Node root, long multi) {
        if (multi == 0) {
            root.replace = true;
            root.update = 0;
            root.sum = 0;
            return;
        }
        root.sum = (root.sum * multi) % mod;

        if (root.L == root.R)
            return;

        if (root.multi || root.replace) {
            root.update = (root.update * multi) % mod;
            return;
        }

        if (root.add) {
            multiChild(root.left, multi);
            multiChild(root.right, multi);

            root.update = root.update * multi % mod;
            return;
        }

        root.update = multi;
        root.multi = true;
    }

    private static void replaceChild(Node root, long replace) {
        if (root.L == root.R) {
            root.sum = replace;
            return;
        }

        root.sum = (root.R + 1 - root.L) * replace % mod;
        root.replace = true;
        root.add = false;
        root.multi = false;
        root.update = replace;
    }

    private void construct(int[] ar) {
        root = new Node(0, ar.length - 1, 0);
        int mid = (ar.length - 1) >>> 1;
        root.left = construct(ar, 0, mid);
        root.right = construct(ar, mid + 1, ar.length - 1);
        root.sum = (root.left.sum + root.right.sum) % mod;
    }

    private Node construct(int[] ar, int l, int r) {
        if (l == r)
            return new Node(l, l, ar[l]);

        Node temp = new Node(l, r, 0);
        int mid = (l + r) >>> 1;
        temp.left = construct(ar, l, mid);
        temp.right = construct(ar, mid + 1, r);
        temp.sum = (temp.left.sum + temp.right.sum) % mod;
        return temp;
    }

    final static class Node {
        Node left, right;
        int L, R;
        boolean add, multi, replace;
        long sum, update;

        Node(int le, int ri, int data) {
            L = le;
            R = ri;
            sum = data;
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
}
