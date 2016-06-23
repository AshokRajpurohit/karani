package com.ashok.codechef.marathon.year15.APRIL15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:  http://www.codechef.com/APRIL15/problems/CARLOS
 */

public class E {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] arm = new int[200];
    private static boolean[] vis = new boolean[200];

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        E a = new E();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            int m = in.readInt();
            int k = in.readInt();
            int n = in.readInt();
            boolean[][] con = new boolean[m][m];
            int x, y;

            for (int i = 0; i < k; i++) {
                x = in.readInt() - 1;
                y = in.readInt() - 1;
                con[x][y] = true;
            }

            format(con);

            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = in.readInt();

            for (int i = 0; i < m; i++)
                arm[i] = 0;

            for (int i = 0; i < n; i++) {
                arm[ar[i] - 1]++;
            }

            int[] arb = new int[n];
            int i = 0, j = 0;
            while (i < n && j < m) {
                while (arm[j] > 0) {
                    arb[i] = j + 1;
                    i++;
                    arm[j]--;
                }
                j++;
            }

            int count = 0;

            for (i = 0; i < m; i++)
                vis[i] = false;

            for (i = 0; i < n; i++) {
                if (ar[i] != arb[i] && con[ar[i] - 1][arb[i] - 1])
                    count++;
                else if (ar[i] != arb[i]) {
                    count = -1;
                    break;
                }
            }

            sb.append(count).append('\n');
        }
        out.print(sb);
    }

    private static boolean connect(boolean[][] con, int i, int j) {
        if (con[i][j])
            return true;

        boolean res = false;
        vis[i] = true;

        for (int k = 0; k < con.length && !res; k++) {
            if (con[i][k] && !vis[k]) {
                res = connect(con, k, j);
            }
        }
        vis[i] = false;
        return res;
    }

    private static void format(boolean[][] con) {
        for (int i = 0; i < con.length; i++)
            vis[i] = false;

        for (int i = 0; i < con.length; i++) {
            for (int j = 0; j < con.length; j++) {
                if (con[i][j]) {
                    vis[i] = true;
                    format(con, j);
                    vis[i] = false;
                    for (int k = 1; k < con.length; k++) {
                        if (con[j][k])
                            con[i][k] = con[j][k];
                    }
                }
            }
        }
    }

    private static void format(boolean[][] con, int j) {
        for (int k = 0; k < con.length; k++) {
            if (con[j][k] && !vis[k]) {
                vis[k] = true;
                for (int l = 0; l < con.length; l++)
                    format(con, l);
                vis[k] = false;
                for (int l = 0; l < con.length; l++)
                    if (con[k][l])
                        con[j][k] = con[k][l];
            } else if (con[j][k] && vis[k]) {
                for (int l = 0; l < con.length; l++)
                    if (con[k][l])
                        con[j][k] = con[k][l];
            }
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
