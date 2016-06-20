package com.ashok.codechef.marathon.year16.june16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Random;

/**
 * Problem: Chef and Rectangle Array
 * https://www.codechef.com/JUNE16/problems/CHSQARR
 *
 * Using Sparse Tree Algorithm for 2D matrix.
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHSQARR {

    private static PrintWriter out;
    private static InputStream in;
    private static int M, N;
    private static int[][] matrix, sum;
    private static SparseTable2D table;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHSQARR a = new CHSQARR();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt();
        matrix = new int[n][m];
        N = n;
        M = m;
        long t = System.currentTimeMillis();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrix[i][j] = in.readInt();

        process();

        int q = in.readInt();
        Random random = new Random();
        while (q > 0) {
            q--;
            out.println(query(in.readInt(), in.readInt()));
        }
    }

    private static void process() {
        table = new SparseTable2D(matrix);

        sum = new int[N][M];

        for (int i = 0; i < N; i++) {
            sum[i][0] = matrix[i][0];

            for (int j = 1; j < M; j++)
                sum[i][j] = sum[i][j - 1] + matrix[i][j];
        }

        for (int i = 1; i < N; i++)
            for (int j = 0; j < M; j++)
                sum[i][j] += sum[i - 1][j];
    }

    private static int query(int r, int c) {
        if (r == 1 && c == 1)
            return 0;

        int sr = 0, sc = 0, er = r - 1, ec = c - 1;
        int min = Integer.MAX_VALUE;

        while (er < N && min > 0) {
            while (ec < M && min > 0) {
                int maxElement = table.query(sr, sc, er, ec);
                int diff = maxElement * r * c - sum(sr, sc, er, ec);

                min = Math.min(min, diff);
                ec++;
                sc++;
            }

            sr++;
            er++;
            sc = 0;
            ec = c - 1;
        }

        return min;

    }

    private static int sum(int sr, int sc, int er, int ec) {
        return sum(er, ec) - sum(er, sc - 1) - sum(sr - 1, ec) +
            sum(sr - 1, sc - 1);
    }

    private static int sum(int row, int col) {
        if (row < 0 || col < 0)
            return 0;

        return sum[row][col];
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

    final static class SparseTable2D {
        private int[][][][] matrix;
        int m, n;

        public SparseTable2D(int[][] ar) {
            format(ar);
        }

        public int query(int sr, int sc, int er, int ec) {
            int halfRow = Integer.highestOneBit(er + 1 - sr), halfCol =
                Integer.highestOneBit(ec + 1 - sc);

            return operation(matrix[halfRow][halfCol][sr][sc],
                             matrix[halfRow][halfCol][er + 1 - halfRow][sc],
                             matrix[halfRow][halfCol][sr][ec + 1 - halfCol],
                             matrix[halfRow][halfCol][er + 1 -
                             halfRow][ec + 1 - halfCol]);
        }

        private void format(int[][] ar) {
            m = ar.length;
            n = ar[0].length;

            matrix = new int[m + 1][n + 1][][];
            matrix[1][1] = ar;

            for (int bitj = 2; bitj <= n; bitj <<= 1) {
                matrix[1][bitj] = new int[m][n + 1 - bitj];
                int halfj = bitj >>> 1;
                for (int i = 0; i < m; i++)
                    for (int j = 0; j <= n - bitj; j++) {
                        matrix[1][bitj][i][j] =
                                operation(matrix[1][halfj][i][j],
                                          matrix[1][halfj][i][j + halfj]);
                    }
            }

            for (int biti = 2; biti <= m; biti <<= 1) {
                int halfi = biti >>> 1;

                matrix[biti][1] = new int[m + 1 - biti][n];
                for (int i = 0; i <= m - biti; i++) {
                    for (int j = 0; j <= n - 1; j++) {
                        matrix[biti][1][i][j] =
                                operation(matrix[halfi][1][i][j],
                                          matrix[halfi][1][i + halfi][j]);
                    }
                }

                for (int bitj = 2; bitj <= n; bitj <<= 1) {
                    matrix[biti][bitj] = new int[m + 1 - biti][n + 1 - bitj];
                    int halfj = bitj >>> 1;

                    for (int i = 0; i <= m - biti; i++)
                        for (int j = 0; j <= n - bitj; j++) {
                            matrix[biti][bitj][i][j] =
                                    operation(matrix[halfi][halfj][i][j],
                                              matrix[halfi][halfj][i +
                                              halfi][j],
                                              matrix[halfi][halfj][i][j +
                                              halfj],
                                              matrix[halfi][halfj][i + halfi][j +
                                              halfj]);
                        }
                }
            }
        }

        private int operation(int a, int b, int c, int d) {
            return Math.max(a, Math.max(b, c > d ? c : d));
        }

        private int operation(int a, int b) {
            return a > b ? a : b;
        }
    }
}
