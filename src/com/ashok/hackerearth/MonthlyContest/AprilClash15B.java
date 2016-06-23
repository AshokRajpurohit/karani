package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit le
 * problem Link: https://www.hackerearth.com/april-clash-15/algorithm/frames-4/
 */

public class AprilClash15B {

    private static PrintWriter out;
    private static InputStream in;
    private static String[] ar;
    private static int[][] mat;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        AprilClash15B a = new AprilClash15B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int m = in.readInt();
        mat = new int[n][m];
        ar = new String[n];
        for (int i = 0; i < n; i++)
            ar[i] = in.read();

        process();
        long res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                res += mat[i][j];
        }
        out.println(res);
    }

    private static void process() {
        for (int i = ar.length - 1, j = 0; j < ar[0].length(); j++)
            mat[i][j] = ar[i].charAt(j) == '0' ? 1 : 0;

        for (int i = 0, j = ar[0].length() - 1; i < ar.length; i++)
            mat[i][j] = ar[i].charAt(j) == '0' ? 1 : 0;

        for (int i = ar.length - 2; i >= 0; i--) {
            for (int j = ar[0].length() - 2; j >= 0; j--) {
                if (ar[i].charAt(j) == '0') {
                    for (int k = 0; k <= mat[i + 1][j + 1]; k++) {
                        if (ar[i + k].charAt(j) == '0' &&
                            ar[i].charAt(j + k) == '0')
                            mat[i][j]++;
                        else
                            break;
                    }
                }
            }
        }
    }

    private static int format(int i, int j) {
        return mat[i][j];
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
