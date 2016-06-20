package com.ashok.codechef.cook;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:
 */

public class C58D {

    private static PrintWriter out;
    private static InputStream in;
    private int[][] dp, banana;
    private boolean[][] vis;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C58D a = new C58D();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        solve(in.readIntArrays(n, n));
        out.println(dp[0][0]);
    }

    private void solve(int[][] ar) {
        banana = ar;
        dp = new int[ar.length][ar.length];
        vis = new boolean[ar.length][ar.length];
        solve(0, 0);
    }

    private int solve(int i, int j) {
        if ((i == dp.length - 1 && j == dp.length) ||
            (i == dp.length && j == dp.length - 1))
            return 0;

        if (i == dp.length || j == dp.length)
            return -1;

        if (vis[i][j])
            return dp[i][j];

        vis[i][j] = true;
        dp[i][j] =
                Math.max(banana[i][j] ^ solve(i + 1, j), banana[i][j] ^ solve(i,
                                                                              j +
                                                                              1));
        vis[i][j] = false;
        return dp[i][j];
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

        public int[][] readIntArrays(int m, int n) throws IOException {
            int[][] ar = new int[m][n];
            for (int i = 0; i < m; i++)
                ar[i] = readIntArray(n);
            return ar;
        }
    }
}
