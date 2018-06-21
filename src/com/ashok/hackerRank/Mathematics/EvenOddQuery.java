package com.ashok.hackerRank.mathematics;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Even Odd Query
 * https://www.hackerrank.com/challenges/even-odd-query
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

class EvenOddQuery {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar, zero;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        EvenOddQuery a = new EvenOddQuery();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        ar = in.readIntArray(n);
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q * 5);
        String even = "Even\n", odd = "Odd\n";
        process();

        while (q > 0) {
            q--;
            int v = find(in.readInt() - 1, in.readInt() - 1);

            if ((v & 1) == 0)
                sb.append(even);
            else
                sb.append(odd);
        }

        out.print(sb);
    }

    private static void process() {
        int n = ar.length;
        zero = new int[n];
        zero[n - 1] = n + 1;
        int p = n + 1;
        for (int i = n - 1; i >= 0; i--) {
            zero[i] = p;
            if (ar[i] == 0)
                p = i;
        }
    }

    private static int find(int x, int y) {
        if (x > y)
            return 1;

        if (x == y)
            return ar[x];

        int p = zero[x];
        if (p > x + 1)
            return ar[x];

        return 1;
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
