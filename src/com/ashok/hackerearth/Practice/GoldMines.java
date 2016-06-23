package com.ashok.hackerearth.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Gold Mines
 * https://www.hackerearth.com/angelprime-challenge/algorithm/gold-mines-10/
 */

public class GoldMines {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        GoldMines a = new GoldMines();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int r = in.readInt();
        int c = in.readInt();
        long[][] matrix = new long[r][c];
        for (int i = 0; i < r; i++)
            matrix[i] = in.readLongArray(c);

        format(matrix);
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 3);

        while (q > 0) {
            q--;
            sb.append(query(matrix, in.readInt(), in.readInt(), in.readInt(),
                            in.readInt())).append('\n');
        }
        out.print(sb);
    }

    private static long query(long[][] matrix, int x, int y, int a, int b) {
        long res = matrix[a - 1][b - 1];
        long left = 0, top = 0, corner = 0;

        if (y > 1)
            left = matrix[a - 1][y - 2];

        if (x > 1)
            top = matrix[x - 2][b - 1];

        if (x > 1 && y > 1)
            corner = matrix[x - 2][y - 2];

        return res + corner - left - top;
    }

    private static void format(long[][] matrix) {
        int r = matrix.length, c = matrix[0].length;
        for (int i = 0; i < r; i++)
            for (int j = 1; j < c; j++)
                matrix[i][j] += matrix[i][j - 1];

        for (int i = 1; i < r; i++) {
            for (int j = 0; j < c; j++)
                matrix[i][j] += matrix[i - 1][j];
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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }
}
