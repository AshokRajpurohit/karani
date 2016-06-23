package com.ashok.hackerRank.Weekly;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Sum of Absolutes
 * https://www.hackerrank.com/contests/w16/challenges/sum-of-absolutes
 */

public class W16A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        W16A a = new W16A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String odd = "Odd\n", even = "Even\n";
        int n = in.readInt();
        int q = in.readInt();
        int[] ar = in.readIntArray(n);
        StringBuilder sb = new StringBuilder(q * 5);
        boolean[] bar = new boolean[ar.length];
        bar[0] = (ar[0] & 1) == 1;

        for (int i = 1; i < ar.length; i++)
            bar[i] = bar[i - 1] ^ ((ar[i] & 1) == 1);

        while (q > 0) {
            q--;
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            if (l == 0) {
                if (bar[r])
                    sb.append(odd);
                else
                    sb.append(even);
            } else {
                if (bar[l - 1] ^ bar[r])
                    sb.append(odd);
                else
                    sb.append(even);
            }
        }
        out.print(sb);
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
