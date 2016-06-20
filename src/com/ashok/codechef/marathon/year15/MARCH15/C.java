package com.ashok.codechef.marathon.year15.MARCH15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.HashMap;

/**
 * @author  Ashok Rajpurohit
 * prblme Link: http://www.codechef.com/MARCH15/problems/QCHEF
 */
public class C {

    private static PrintWriter out;
    private static InputStream in;
    private static int[][] lr; // to save the calculated values
    private static boolean[][] chk; // to save the status of visited l,r
    private static int[] ar;
    private static StringBuilder sb;
    private static HashMap<Integer, Integer> hm;
    private static BTree[] b_ar;
    private static boolean[] b_chk;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C a = new C();
        long t1 = System.currentTimeMillis();
        a.solve();
        long t2 = System.currentTimeMillis() - t1;
        sb.append(t2).append('\n');
        out.print(sb);
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        sb = new StringBuilder();

        int n = in.readInt();
        int m = in.readInt();
        int k = in.readInt();
        ar = new int[n];

        for (int i = 0; i < n; i++) {
            ar[i] = in.readInt();
        }

        if (n <= 1) {
            for (int i = 0; i < k; i++) {
                int l = in.readInt();
                int r = in.readInt();
                sb.append("0\n");
            }
            //        } else if (n <= 1000) {
            //            lr = new int[n][n];
            //            chk = new boolean[n][n];
            //            for (int i = 0; i < k; i++) {
            //                int l = in.readInt() - 1;
            //                int r = in.readInt() - 1;
            //                if (l == r) {
            //                    sb.append("0\n");
            //                } else
            //                    sb.append(solve(l, r)).append('\n');
            //            }
        } else {
            b_ar = new BTree[n];
            b_chk = new boolean[n];
            createB_ar();


            //            BTree root = new BTree(ar, b_chk, 0);
            //            format_bar();
            for (int i = 0; i < k; i++) {
                int l = in.readInt() - 1;
                int r = in.readInt() - 1;
                if (l == r)
                    sb.append("0\n");
                else
                    sb.append(super_solve(l, r)).append('\n');
            }
        }
        //        out.print(sb);
    }

    private static void createB_ar() {
        for (int i = 0; i != ar.length; i++) {
            b_ar[i] = new BTree(i);
        }

        restructB_ar();
        format_bar();
    }

    private static void restructB_ar() {
        for (int i = 0; i != ar.length; i++) {
            if (!b_chk[i]) {
                BTree temp = b_ar[i];
                b_chk[i] = true;
                int j = i + 1;
                while (j != ar.length) {
                    if (ar[j] == ar[i]) {
                        temp.equal = b_ar[j];
                        temp = temp.equal;
                        b_chk[j] = true;
                    }
                    j++;
                }
            }
        }
    }

    private int solve(int l, int r) {
        if (l >= r)
            return 0;

        if (chk[l][r])
            return lr[l][r];

        chk[l][r] = true;

        if (ar[l] == ar[r]) {
            lr[l][r] = r - l;
            return lr[l][r];
        }

        lr[l][r] = Math.max(solve(l + 1, r), solve(l, r - 1));

        return lr[l][r];
    }

    private int super_solve(int l, int r) {
        for (int i = l; i != r + 1; i++) {
            b_chk[i] = false;
        }

        int m_len = 0, t_len = 0;
        for (int i = l; i < r + 1 - m_len; i++) {
            if (!b_chk[i]) {
                //                b_chk[i] = true;
                t_len = find_dis(i, r);
                if (t_len > m_len)
                    m_len = t_len;
            }
        }
        return m_len;
    }

    private static int find_dis(int ar_ind, int ar_end) {
        BTree temp = b_ar[ar_ind];
        while (temp.equal != null && temp.equal.data <= ar_end) {
            b_chk[temp.data] = true;
            temp = temp.equal;
        }
        return temp.data - ar_ind;
    }

    final static class BTree {
        BTree equal, unequal;
        int data;

        BTree(int data) {
            this.data = data;
        }

        BTree(int[] ar, boolean[] check, int index) {
            this.data = index;
            check[index] = true;
            b_ar[index] = this;
            int i = index + 1;
            BTree temp = this;
            while (i != ar.length) {
                if (ar[i] == this.data) {
                    temp.equal = new BTree(i);
                    temp = temp.equal;
                    b_ar[i] = temp;
                    check[i] = true;
                }
                i++;
            }

            i = index + 1;
            while (i != ar.length && ar[i] == ar[index])
                i++;

            if (i == ar.length)
                return;

            b_ar[i - 1].unequal = new BTree(ar, check, i);
        }
    }

    private static void format_bar() {
        int i = 0;
        while (i != ar.length - 1) {
            if (ar[i] != ar[i + 1]) {
                b_ar[i].unequal = b_ar[i + 1];
            }
            i++;
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
    }
}
