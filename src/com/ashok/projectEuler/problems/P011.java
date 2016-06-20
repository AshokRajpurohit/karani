package com.ashok.projectEuler.problems;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 *         problem Link: https://projecteuler.net/problem=11
 */

public class P011 {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        P011 a = new P011();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(solve(in.readIntDoubleArray(20, 20)));
    }

    private long solve(int[][] ar) {
        long max = 0, temp = 1;

        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++)
                if (ar[i][j] == 0)
                    ar[i][j] = 1;

        // horizontal

        for (int i = 0; i < 20; i++) {
            temp = 1;
            for (int j = 0; j < 4; j++)
                temp = temp * ar[i][j];

            max = Math.max(max, temp);

            for (int j = 4; j < 20; j++) {
                temp = (temp * ar[i][j]) / ar[i][j - 4];
                max = Math.max(temp, max);
            }
        }

        // vertical multiplication

        for (int j = 0; j < 20; j++) {
            temp = 1;
            for (int i = 0; i < 4; i++)
                temp = temp * ar[i][j];

            max = Math.max(max, temp);

            for (int i = 4; i < 20; i++) {
                temp = (temp * ar[i][j]) / ar[i - 4][j];
                max = Math.max(max, temp);
            }
        }

        // Diagonal righward multiplication

        for (int i = 0; i < 17; i++) {
            temp = 1;
            for (int j = 0; j < 4; j++)
                temp = temp * ar[i + j][j];
            max = Math.max(max, temp);

            for (int j = 4; i + j < 20; j++) {
                temp = (temp * ar[i + j][j]) / ar[i + j - 4][j - 4];
                max = Math.max(max, temp);
            }
        }

        for (int j = 0; j < 17; j++) {
            temp = 1;
            for (int i = 0; i < 4; i++)
                temp = temp * ar[i][i + j];

            max = Math.max(max, temp);

            for (int i = 4; i + j < 20; i++) {
                temp = (temp * ar[i][i + j]) / ar[i - 4][i + j - 4];
                max = Math.max(max, temp);
            }
        }

        // Diagonal leftward multiplication

        for (int j = 3; j < 20; j++) {
            temp = 1;

            for (int k = 0; k < 4; k++)
                temp = temp * ar[k][j - k];

            max = Math.max(max, temp);

            for (int k = 4; j - k >= 0; k++) {
                temp = (temp * ar[k][j - k]) / ar[k - 4][j - k + 4];
                max = Math.max(max, temp);
            }
        }

        for (int i = 1; i < 17; i++) {
            temp = 1;

            for (int k = 0; k < 4; k++)
                temp = temp * ar[i + k][19 - k];

            max = Math.max(max, temp);

            for (int k = 4; i + k < 20; k++) {
                temp = (temp * ar[i + k][19 - k]) / ar[i + k - 4][23 - k];
                max = Math.max(max, temp);
            }
        }


        return max;
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

        public int[][] readIntDoubleArray(int m, int n) throws IOException {
            int[][] ar = new int[m][];
            for (int i = 0; i < m; i++)
                ar[i] = readIntArray(n);

            return ar;
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
