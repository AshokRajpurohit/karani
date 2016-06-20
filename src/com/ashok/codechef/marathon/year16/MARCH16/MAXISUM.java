package com.ashok.codechef.marathon.year16.MARCH16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: maximize the sum
 * https://www.codechef.com/MARCH16/problems/MAXISUM
 *
 * let's say without any operation the sum of interaction is S.
 * now let's increase A[i]'s value for each operation.
 * New value at A[i] is A[i] + k
 *
 * New value of S = S + B[i] * k.
 *
 * the only variable in the above equation is B[i].
 * For maximum value of S, B[i] * k should be maximum.
 *
 * the same case when we decrease A[i]'s value for each operation.
 * in that case -B[i] * k should be maximum.
 *
 * whichever brings the max value we choose that.
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class MAXISUM {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MAXISUM a = new MAXISUM();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt(), k = in.readInt();
            int[] a = in.readIntArray(n), b = in.readIntArray(n);

            out.println(process(a, b, k));
        }
    }

    private static long process(int[] a, int[] b, int k) {
        long res = 0;
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            res += 1L * a[i] * b[i];
            max = Math.max(max, b[i]);
            min = Math.min(min, b[i]);
        }

        max = Math.max(Math.abs(max), Math.abs(min));

        res += 1L * k * max;
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
    }
}
