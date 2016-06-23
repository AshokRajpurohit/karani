package com.ashok.codechef.lunch.LunchTime;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Chef and Submatrix
 * http://www.codechef.com/LTIME25/problems/CHEFAXR
 */

public class June15B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        June15B a = new June15B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(process(in.readMatrix(in.readInt())));
        }
    }

    private static int process(int[][] table) {
        int n = table.length;
        int[][] left = new int[n][n];
        int[][] top = new int[n][n];

        for (int i = 0; i < n; i++)
            left[i][0] = table[i][0];

        for (int i = 0; i < n; i++)
            top[0][i] = table[0][i];


        return 4;
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
