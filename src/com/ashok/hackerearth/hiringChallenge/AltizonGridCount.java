package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Grid Count
 * Challenge: Altizon
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class AltizonGridCount {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        AltizonGridCount a = new AltizonGridCount();
        a.solve();
        out.close();
    }

    private static long getSum(long[][] ar, int si, int sj, int ei, int ej) {
        return ar[ei][ej] - getSum(ar, ei - 1, sj) - getSum(ar, ej, sj - 1) +
                getSum(ar, ei - 1, ej - 1);
    }

    private static long getSum(long[][] ar, int i, int j) {
        if (i < 0 || j < 0)
            return 0;

        return ar[i][j];
    }

    private static boolean isCube(long n) {
        long root = (long) Math.cbrt(n);
        return n == root * root * root;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), k = in.readInt(), count = 0;
        long[][] matrix = new long[n][];

        for (int i = 0; i < n; i++)
            matrix[i] = in.readLongArray(n);

        long[][] sum = new long[n][n];

        for (int i = 0; i < n; i++) {
            sum[i][0] = matrix[i][0];

            for (int j = 1; j < n; j++)
                sum[i][j] = sum[i][j - 1] + matrix[i][j];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++)
                sum[i][j] += sum[i - 1][j];
        }

        for (int i = k - 1; i < n; i++) {
            for (int j = k - 1; j < n; j++) {
                long value = getSum(sum, i - k + 1, j - k + 1, i, j);

                if (isCube(value))
                    count++;
            }
        }

        out.println(count);
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
