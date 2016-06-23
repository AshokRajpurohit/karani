package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem: Akash and too many assignments
 * problem Link: HackerEarth Hiring Challenge | XSEED Hiring Challenge
 */

/**
 * Problem Statement: Copy nahin kar paya :(
 */

public class XSEEDA {

    private static PrintWriter out;
    private static InputStream in;
    private static Node[] bak = new Node[26];
    private static char[] numChar = new char[256];
    private static int[] counter = new int[26];

    static {
        for (int i = 0; i < 26; i++)
            numChar[i] = (char) (i + 'a');

        for (int i = 0; i <= 26; i++)
            numChar[49 + i] = (char) ('a' + i);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        XSEEDA a = new XSEEDA();
        a.solve();
        out.close();
    }

    private static char query(int L, int R, int k) {
        int temp = 0;
        for (int i = 0; i < 26; i++) {
            temp += query(bak[i], L, R);
            if (temp >= k)
                return numChar[i];
        }
        return 'z';
    }

    private static int query(Node node, int L, int R) {
        if (node.l == L && node.r == R)
            return node.count;

        int mid = (node.l + node.r) >>> 1;
        if (L > mid)
            return query(node.right, L, R);

        if (R <= mid)
            return query(node.left, L, R);

        return query(node.left, L, mid) + query(node.right, mid + 1, R);
    }

    private static void update(int index, char c) {
        for (int i = 0; i < 26; i++)
            update(bak[i], index, 0);

        update(bak[c - 'a'], index, 1);
        System.out.println("Updating " + (c - 'a'));
    }

    private static void update(Node root, int i, int data) {
        if (root.l == root.r) {
            root.count = data;
            return;
        }

        int mid = (root.l + root.r) >>> 1;
        if (i > mid)
            update(root.right, i, data);
        else
            update(root.left, i, data);

        root.count = root.left.count + root.right.count;
    }

    private static void process(String s) {
        int[] temp = new int[s.length()];

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == numChar[i])
                    temp[j] = 1;
                else
                    temp[j] = 0;
            }
            construct(temp, i);
        }
    }

    private static void construct(int[] ar, int index) {
        bak[index] = new Node(0, ar.length - 1);
        int mid = (ar.length - 1) >>> 1;
        bak[index].left = construct(ar, 0, mid);
        bak[index].right = construct(ar, mid + 1, ar.length - 1);
        bak[index].count = bak[index].left.count + bak[index].right.count;
    }

    private static Node construct(int[] ar, int l, int r) {
        if (l == r)
            return new Node(l, l, ar[l]);

        Node temp = new Node(l, r);
        int mid = (l + r) >>> 1;
        temp.left = construct(ar, l, mid);
        temp.right = construct(ar, mid + 1, r);
        temp.count = temp.left.count + temp.right.count;
        return temp;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int q = in.readInt();
        String str = in.read(n);
        StringBuilder sb = new StringBuilder(q);
        process(str);

        while (q > 0) {
            q--;
            int qtype = in.readInt();
            if (qtype == 0) {
                update(in.readInt(), numChar[in.readInt()]);
            } else {
                int L = in.readInt() - 1, R = in.readInt() - 1, k =
                        in.readInt();
                sb.append(query(L, R, k)).append('\n');
                for (int i = 0; i < 26; i++)
                    System.out.print(query(bak[i], L, R) + " ");
                System.out.println();
            }
        }
        out.print(sb);
    }

    final static class Node {
        Node left, right;
        int l, r;
        int count;

        Node(int m, int n) {
            l = m;
            r = n;
        }

        Node(int m, int n, int d) {
            l = m;
            r = n;
            count = d;
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
