package com.ashok.codechef.marathon.year16.FEB16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Rectangles of Love
 * https://www.codechef.com/FEB16/problems/RECTLOVE
 *
 * Let's analyze the solution:
 * Pi referes to number of rectangles containing atleast i stars.
 * APi referes to number of rectangles containing exactly i stars.
 *
 * So we know that the answer is:
 * AP1 + 2 * AP2 + ... + r * APr + ... + n * APn.
 *
 * and:
 *  AP1 = P1 - 2 * AP2 - 3 * AP3 - ... - r * APr - ... - n * APn
 *  (as we know that APr is included r times while calculating P1,
 *  once for each star)
 *
 * So replacing AP1 from equation 2 to equation 1 the answer is:
 * P1.
 *
 * Oh, P1 is the total value, the expected value is:
 * P1 / Total number of rectangles.
 *
 * Total number of rectangles in n x m matrix is C(n + 1, 2) * C(m + 1, 2).
 * C(n, r) is n! / (r! * (n - r)!)
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class RECTLOVE {

    private static PrintWriter out;
    private static InputStream in;
    private static long mod = 1000000009;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        RECTLOVE a = new RECTLOVE();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt(), k = in.readInt();
            long[] ar = in.readLongArray(k);
            out.println(process(n, m, ar));
        }
    }

    private static double process(int n, int m, long[] ar) {
        double total = 0.25 * n * (n + 1) * m * (m + 1);

        double res = 0.0;
        for (long v : ar) {
            long row = (v - 1) / m;
            long col = (v - 1) % m;
            res += 1.0 * (row + 1) * (n - row) * (col + 1) * (m - col);
        }

        return res / total;
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
