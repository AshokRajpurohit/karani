package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Special Paths
 * Link: Moonfrog Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class MoonfrogC {

    private static PrintWriter out;
    private static InputStream in;
    private static int n, m, k, mod = 1000007;
    private static boolean[][] field, vis;
    private static int[][][] matrix;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MoonfrogC a = new MoonfrogC();
        a.solve();
        out.close();
    }

    private static void process() {
        vis = new boolean[n][m];
        matrix = new int[n][m][k + 1];

        vis[n - 1][m - 1] = true;
        if (field[n - 1][m - 1])
            matrix[n - 1][m - 1][1] = 1;
        else
            matrix[n - 1][m - 1][0] = 1;

        process(0, 0);
    }

    private static void process(int row, int col) {
        if (row == n || col == m || vis[row][col])
            return;

        vis[row][col] = true;
        process(row + 1, col);
        process(row, col + 1);

        if (row != n - 1)
            for (int i = 0; i <= k; i++)
                matrix[row][col][i] = matrix[row + 1][col][i];

        if (col != m - 1)
            for (int i = 0; i <= k; i++)
                matrix[row][col][i] += matrix[row][col + 1][i];

        if (field[row][col]) {
            for (int i = k; i >= 1; i--)
                matrix[row][col][i] = matrix[row][col][i - 1] % mod;

            matrix[row][col][0] = 0;
        } else
            for (int i = 0; i <= k; i++)
                matrix[row][col][i] %= mod;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        n = in.readInt();
        m = in.readInt();
        k = in.readInt();
        field = new boolean[n][m];

        for (int i = 0; i < k; i++)
            field[in.readInt() - 1][in.readInt() - 1] = true;

        process();
        StringBuilder sb = new StringBuilder(k << 2);
        for (int i = 0; i <= k; i++)
            sb.append(matrix[0][0][i]).append(' ');

        out.println(sb);
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
