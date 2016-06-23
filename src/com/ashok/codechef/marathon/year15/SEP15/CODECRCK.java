package com.ashok.codechef.marathon.year15.SEP15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * problem: Cracking the Code
 * https://www.codechef.com/SEPT15/problems/CODECRCK
 *
 * If we multiply the matrix two times with matrix [an, bn] the result is
 * [16an + 16bn]. So we can find how many times we need to multiply the matrix
 * and let's say it is t times, so we need to multiply matrix * matrix t / 2
 * times, and plainly final will be pow(4, t/2) [an, bn]. Now we need to think
 * when t is odd, in that case we can multiply the final value (just we
 * calculated) pow(4, t / 2) * [an, bn] to matrix.
 * In the case when t is negative, so we can go backward.
 * Let's say t is even and it's absolute value (+ive). if it is even, then we
 * don't need to worry otherwise increment it and make it even (t + 1).
 * Now our final matrix should be pow(4, -t/2)[an, bn]. If t was odd then we
 * can multiply it with matrix else this is the answer.
 * @author Deepak Kumar
 * @author Ashok Rajpurohit
 */

class CODECRCK {

    private static PrintWriter out;
    private static InputStream in;
    private static double x = Math.sqrt(2), y = Math.sqrt(3);
    private static double[][] matrix = new double[2][2];

    static {
        matrix[0][0] = x - x * y;
        matrix[0][1] = x + x * y;
        matrix[1][0] = x + x * y;
        matrix[1][1] = x * y - x;
    }

    public static void main(String[] args) throws IOException, Exception {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CODECRCK a = new CODECRCK();
        a.solve();
        out.close();
    }

    public void solve() throws IOException, Exception {
        InputReader in = new InputReader();
        long i = in.readLong();
        long k = in.readLong();
        long s = in.readLong();
        double[][] ar = new double[1][2];
        ar[0][0] = in.readInt();
        ar[0][1] = in.readInt();

        if (k == i) {
            out.println((ar[0][0] + ar[0][1]) * Math.pow(2, -s));
            return;
        }

        long odd, p;

        if (k > i) {
            odd = (k - i) & 1;
            p = ((k - i - odd) << 1) - s;
        } else {
            odd = (i - k) & 1;
            p = -((i - k + odd) << 1) - s;
        }

        if (odd == 0) {
            out.println((ar[0][0] + ar[0][1]) * Math.pow(2, p));
            return;
        }

        double[][] res = new double[1][2];
        multiply(ar, matrix, res);

        out.println((res[0][0] + res[0][1]) * Math.pow(2, p));
    }

    private static void multiply(double[][] a, double[][] b,
                                 double[][] result) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++)
                    result[i][j] += a[i][k] * b[k][j];
            }
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
    }
}
