package com.ashok.codechef.PracticeEasy;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem: Gcd Queries
 * http://www.codechef.com/problems/GCDQ
 */

public class GCDQ {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        GCDQ a = new GCDQ();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int q = in.readInt();
            StringBuilder sb = new StringBuilder(q * 6);
            int[] ar = in.readIntArray(n);
            int[] left = new int[n], right = new int[n];

            left[0] = ar[0];
            for (int i = 1; i < n; i++)
                left[i] = gcd(left[i - 1], ar[i]);

            right[n - 1] = ar[n - 1];
            for (int i = n - 2; i >= 0; i--)
                right[i] = gcd(right[i + 1], ar[i]);

            while (q > 0) {
                q--;
                int l = in.readInt() - 2;
                int r = in.readInt();

                int a = 0, b = 0;
                if (l >= 0)
                    a = left[l];

                if (r < n)
                    b = right[r];

                sb.append(gcd(a, b)).append('\n');
            }
            out.print(sb);
        }
    }

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
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
