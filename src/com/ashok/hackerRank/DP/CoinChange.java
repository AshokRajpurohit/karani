package com.ashok.hackerRank.DP;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: The Coin Change Problem
 * https://www.hackerrank.com/challenges/coin-change
 */

public class CoinChange {

    private static PrintWriter out;
    private static InputStream in;
    private long[][] bak;
    private int[] coins;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CoinChange a = new CoinChange();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int m = in.readInt();
        bak = new long[m + 1][n + 1];

        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                bak[i][j] = -1;

        for (int i = 0; i <= n; i++)
            bak[0][i] = 1;

        coins = in.readIntArray(m); // let's assume it's sorted
        out.println(process(m, n));
    }

    private long process(int m, int n) {
        if (m < 0 || (m == 0 && n > 0) || n < 0)
            return 0;

        if (n == 0)
            return 1;

        if (bak[m][n] != -1)
            return bak[m][n];

        bak[m][n] = process(m - 1, n) + process(m, n - coins[m - 1]);
        return bak[m][n];
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
