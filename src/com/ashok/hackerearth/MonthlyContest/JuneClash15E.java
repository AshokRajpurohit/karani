package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem: Table coloring
 * https://www.hackerearth.com/june-clash-15/approximate/table-coloring/
 */

public class JuneClash15E {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        JuneClash15E a = new JuneClash15E();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int m = in.readInt();
        int[][] ar = new int[n][];
        for (int i = 0; i < n; i++)
            ar[i] = in.readIntArray(m);
        process(ar);
    }

    private static void process(int[][] ar) {
        int n = ar.length, m = ar[0].length;
        int[][] table = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (ar[i][j] == 1) {
                    table[i][j] = 1;
                    if (i > 0)
                        table[i - 1][j] = -1;
                    if (i < n - 1)
                        table[i + 1][j] = -1;
                    if (j > 0)
                        table[i][j - 1] = -1;
                    if (j < m - 1)
                        table[i][j + 1] = -1;
                }
            }
        }
        boolean falt = true;
        int k = n + m - 1;
        while (k > 1) {
            k--;
            falt = false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (table[i][j] == 0) {
                        if (ar[i][j] > 0) {
                            table[i][j] = 1;
                            if (options(table, i, j) == ar[i][j])
                                fill(table, i, j);
                            else
                                falt = true;
                        }
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder(2 * n * m);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (table[i][j] == -1)
                    table[i][j] = 1;
                else
                    table[i][j] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                sb.append(table[i][j]).append(' ');
            sb.append('\n');
        }
        out.print(sb);
    }

    private static void fill(int[][] table, int i, int j) {
        for (int k = i + 1; k < table.length; k++) {
            if (table[k][j] == -1)
                break;
            table[k][j] = 1;
        }
        for (int k = i - 1; k >= 0; k--) {
            if (table[k][j] == -1)
                break;
            table[k][j] = 1;
        }
        for (int k = j + 1; k < table[0].length; k++) {
            if (table[i][k] == -1)
                break;
            table[i][k] = 1;
        }
        for (int k = j - 1; k >= 0; k--) {
            if (table[i][k] == -1)
                break;
            table[i][k] = 1;
        }
    }

    private static int options(int[][] table, int i, int j) {
        int count = 1;
        for (int k = i + 1; k < table.length; k++) {
            if (table[k][j] == -1)
                break;
            count++;
        }
        for (int k = i - 1; k >= 0; k--) {
            if (table[k][j] == -1)
                break;
            count++;
        }
        for (int k = j + 1; k < table[0].length; k++) {
            if (table[i][k] == -1)
                break;
            count++;
        }
        for (int k = j - 1; k >= 0; k--) {
            if (table[i][k] == -1)
                break;
            count++;
        }
        return count;
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
