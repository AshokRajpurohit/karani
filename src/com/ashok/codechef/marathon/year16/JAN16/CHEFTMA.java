package com.ashok.codechef.marathon.year16.JAN16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * Problem: Chef and Time Machine
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFTMA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHEFTMA a = new CHEFTMA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt(), k = in.readInt(), m = in.readInt();
            out.println(process(in.readIntArray(n), in.readIntArray(n),
                                in.readIntArray(k), in.readIntArray(m)));
        }
    }

    private static int process(int[] a, int[] b, int[] c, int[] d) {
        for (int i = 0; i < a.length; i++)
            a[i] -= b[i];

        Arrays.sort(a);
        Arrays.sort(c);
        Arrays.sort(d);

        for (int i = a.length - 1, j = c.length - 1, k = d.length - 1;
             i >= 0 && (j >= 0 || k >= 0); ) {
            while (j >= 0 && a[i] < c[j])
                j--;

            while (k >= 0 && a[i] < d[k])
                k--;

            if (j >= 0 && k >= 0) {
                if (c[j] >= d[k]) {
                    a[i] -= c[j];
                    i--;
                    j--;
                } else {
                    a[i] -= d[k];
                    i--;
                    k--;
                }
            } else if (j >= 0) {
                a[i] -= c[j];
                i--;
                j--;
            } else if (k >= 0) {
                a[i] -= d[k];
                i--;
                k--;
            }
        }

        int res = 0;
        for (int i = 0; i < a.length; i++)
            res += a[i];

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
