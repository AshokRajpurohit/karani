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
public class C_V2 {

    private static PrintWriter out;
    private static InputStream in;
    private static int[][] lr; // to save the calculated values
    private static boolean[][] chk; // to save the status of visited l,r
    private static int[] ar;
    private static int[] sar;
    private static int[] m_ar;
    private static StringBuilder sb;
    private static HashMap<Integer, Integer> hm;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C_V2 a = new C_V2();
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
        } else if (n <= 1000) {
            lr = new int[n][n];
            chk = new boolean[n][n];
            for (int i = 0; i < k; i++) {
                int l = in.readInt() - 1;
                int r = in.readInt() - 1;
                if (l == r) {
                    sb.append("0\n");
                } else
                    sb.append(solve(l, r)).append('\n');
            }
        } else {
            m_ar = new int[m + 1];
            lr = new int[1000][1000];
            chk = new boolean[1000][1000];
            //            format_array();
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

    private void format_array() {
        sar = new int[ar.length];
        for (int i = 0; i < ar.length; i++) {
            if (m_ar[ar[i]] != 0)
                m_ar[ar[i]] =
                        i; // 1 based index to differentiate 0 and index 0
            else {
                sar[m_ar[ar[i]]] = i;
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
        if (l == r)
            return 0;

        if (ar[l] == ar[r])
            return r - l;

        if (l < 1000 && r < 1000)
            return solve(l, r);

        int count = 0, index = l;
        int[] check_ar = new int[m_ar.length];
        while (index <= r && count < m_ar.length - 1) {
            if (m_ar[ar[index]] == 0) {
                count++;
                m_ar[ar[index]] = index;
            }
            index++;
        }
        count = 0;
        index = r;
        int m_dis = 0;

        while (index >= l && count < m_ar.length - 1) {
            if (m_ar[ar[index]] != 0) {
                count++;
                m_dis =
                        m_dis > index - m_ar[ar[index]] ? m_dis : index - m_ar[ar[index]];
            }
            index--;
        }

        //        for (int i = 0; i < check_ar.length; i++)
        //            m_dis = m_dis > check_ar[i] ? m_dis : check_ar[i];
        return m_dis;

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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }
    }
}
