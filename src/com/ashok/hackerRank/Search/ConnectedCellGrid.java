package com.ashok.hackerRank.Search;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Connected Cell in a Grid
 * https://www.hackerrank.com/challenges/connected-cell-in-a-grid
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

class ConnectedCellGrid {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        ConnectedCellGrid a = new ConnectedCellGrid();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt();
        int[][] matrix = new int[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrix[i][j] = in.readInt();

        out.println(process(matrix, n, m));

    }

    private static int process(int[][] matrix, int n, int m) {
        if (n == 0 || m == 0)
            return 0;

        int max = 0;
        boolean[][] mark = new boolean[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (!mark[i][j] && matrix[i][j] == 1)
                    max = Math.max(max, process(matrix, mark, i, j));
            }

        return max;
    }

    private static int process(int[][] matrix, boolean[][] mark, int row,
                               int col) {
        if (row >= matrix.length || row < 0 || col < 0 ||
            col >= matrix[0].length || mark[row][col] || matrix[row][col] == 0)
            return 0;

        mark[row][col] = true;

        return 1 + process(matrix, mark, row + 1, col) +
            process(matrix, mark, row - 1, col) +
            process(matrix, mark, row, col + 1) +
            process(matrix, mark, row, col - 1) +
            process(matrix, mark, row - 1, col - 1) +
            process(matrix, mark, row - 1, col + 1) +
            process(matrix, mark, row + 1, col - 1) +
            process(matrix, mark, row + 1, col + 1);
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
