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
public class C_V3 {

    private static PrintWriter out;
    private static InputStream in;
    //    private static int[][] lr; // to save the calculated values
    //    private static boolean[][] chk; // to save the status of visited l,r
    private static int[] ar, m1_ar, m2_ar;
    private static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C_V3 a = new C_V3();
        a.solve();
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
            //            lr = new int[n][n];
            //            chk = new boolean[n][n];
            m1_ar = new int[m + 1];
            m2_ar = new int[m + 1];
            for (int i = 0; i < k; i++) {
                int l = in.readInt() - 1;
                int r = in.readInt() - 1;
                if (l >= r)
                    sb.append("0\n");
                else
                    sb.append(super_solve(l, r)).append('\n');
            }
        }
        //        out.print(sb);
    }

    private int super_solve(int l, int r) {
        //        if (l < lr.length && r < lr.length)
        //            return solve(l, r);

        //        if (l >= r)
        //            return 0;

        for (int i = l; i <= r; i++) {
            m1_ar[ar[i]] = -1;
        }

        for (int i = l; i <= r; i++) {
            if (m1_ar[ar[i]] == -1) {
                m1_ar[ar[i]] = i;
            }
            m2_ar[ar[i]] = i;
        }

        int mdif = 0, tdif;
        for (int i = l; i <= r; i++) {
            tdif = m2_ar[ar[i]] - m1_ar[ar[i]];
            if (mdif < tdif)
                mdif = tdif;
        }

        return mdif;
    }

    //    private int solve(int l, int r) {
    //        if (l >= r)
    //            return 0;
    //
    //        if (chk[l][r])
    //            return lr[l][r];
    //
    //        chk[l][r] = true;
    //
    //        if (ar[l] == ar[r]) {
    //            lr[l][r] = r - l;
    //            return lr[l][r];
    //        }
    //
    //        lr[l][r] = Math.max(solve(l + 1, r), solve(l, r - 1));
    //
    //        return lr[l][r];
    //    }

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
