package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem: Connected Horses
 * Challenge: Make My Trip Nov15
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class MakeMyTripA {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007, n, m;
    private static boolean[][] board;
    private static int[][] mark;
    private static long[] fact;
    private static int[] hash;

    static {
        fact = new long[1000001];
        fact[0] = 1;
        for (int i = 1; i < 1000001; i++)
            fact[i] = fact[i - 1] * i % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MakeMyTripA a = new MakeMyTripA();
        a.solve();
        out.close();
    }

    private static long process() {
        mark = new int[n][m];
        int count = 1;
        LinkedList<Integer> list = new LinkedList<Integer>();
        hash = new int[n * m + 1];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (board[i][j] && mark[i][j] == 0) {
                    list.add(mark(i, j, count));
                    count++;
                }
            }

        long res = 1;
        for (Integer i : list)
            if (i < 1000001)
                res = res * fact[i] % mod;

        return res;
    }

    private static int mark(int i, int j, int count) {
        if (i < 0 || j < 0 || i >= n || j >= m || !board[i][j] ||
                mark[i][j] != 0)
            return 0;

        mark[i][j] = count;
        return 1 + mark(i + 2, j + 1, count) + mark(i + 2, j - 1, count) +
                mark(i - 2, j + 1, count) + mark(i - 2, j - 1, count) +
                mark(i + 1, j + 2, count) + mark(i + 1, j - 2, count) +
                mark(i - 1, j + 2, count) + mark(i - 1, j - 2, count);
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        while (t > 0) {
            t--;
            n = in.readInt();
            m = in.readInt();
            int q = in.readInt();
            board = new boolean[n][m];
            for (int i = 0; i < q; i++)
                board[in.readInt() - 1][in.readInt() - 1] = true;

            out.println(process());
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
