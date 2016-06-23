package com.ashok.codechef.lunch.LunchTime;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Save the nature
 * https://www.codechef.com/LTIME29/problems/SVNTR
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class LTime29C {

    private static PrintWriter out;
    private static InputStream in;
    private int[][] matrix;
    private long[][] sum;
    private int n, m, s;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LTime29C a = new LTime29C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            n = in.readInt();
            m = in.readInt();
            s = in.readInt();
            matrix = new int[n][];
            for (int i = 0; i < n; i++)
                matrix[i] = in.readIntArray(m);

            format();
            //            print(sum);
            //            out.println("-----------------------");
            out.println(getCount());
        }
    }

    private void format() {
        sum = new long[n][m];
        sum[0][0] = matrix[0][0];

        for (int i = 1; i < m; i++)
            sum[0][i] = sum[0][i - 1] + matrix[0][i];

        for (int i = 1; i < n; i++)
            sum[i][0] = matrix[i][0];

        for (int i = 1; i < n; i++)
            for (int j = 1; j < m; j++)
                sum[i][j] = sum[i][j - 1] + matrix[i][j];

        for (int i = 1; i < n; i++)
            for (int j = 0; j < m; j++)
                sum[i][j] += sum[i - 1][j];
    }

    private static void print(int[][] ar) {
        StringBuilder sb = new StringBuilder(ar.length * ar[0].length * 2);
        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar[0].length; j++)
                sb.append(ar[i][j]).append(' ');
            sb.append('\n');
        }
        out.print(sb);
    }

    private static void print(long[][] ar) {
        StringBuilder sb = new StringBuilder(ar.length * ar[0].length * 2);
        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar[0].length; j++)
                sb.append(ar[i][j]).append(' ');
            sb.append('\n');
        }
        out.print(sb);
    }

    private int getCount() {
        int count = 0;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                for (int k = i; k < n; k++)
                    for (int l = j; l < m; l++)
                        if (isValid(i, j, k, l))
                            count++;
            }

        return count;
    }

    private boolean isValid(int i, int j, int k, int l) {
        if (i == k && j == l)
            return matrix[i][j] <= s;

        if (i == 0 && j == 0)
            return sum[k][l] <= s;

        if (i == 0)
            return sum[k][l] - sum[k][j - 1] <= s;

        if (j == 0)
            return sum[k][l] - sum[i - 1][l] <= s;

        return sum[k][l] + sum[i - 1][j - 1] - sum[i - 1][l] - sum[k][j - 1] <=
            s;
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
