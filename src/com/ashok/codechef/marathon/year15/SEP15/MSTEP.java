package com.ashok.codechef.marathon.year15.SEP15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Count Steps in Matrix
 * https://www.codechef.com/SEPT15/problems/MSTEP
 */

public class MSTEP {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MSTEP a = new MSTEP();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            out.println(process(in.readMatrix(n)));
        }
    }

    private static int process(int[][] matrix) {
        int[] row = new int[matrix.length * matrix.length + 1];
        int[] col = new int[matrix.length * matrix.length + 1];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++) {
                row[matrix[i][j]] = i;
                col[matrix[i][j]] = j;
            }

        int res = 0;
        for (int i = 2; i < row.length; i++)
            res +=
Math.abs(row[i] - row[i - 1]) + Math.abs(col[i] - col[i - 1]);

        return res;
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

        public int[][] readMatrix(int n) throws IOException {
            int[][] ar = new int[n][n];
            for (int i = 0; i < n; i++)
                ar[i] = readIntArray(n);

            return ar;
        }
    }
}
