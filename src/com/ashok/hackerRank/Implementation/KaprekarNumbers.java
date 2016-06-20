package com.ashok.hackerRank.Implementation;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Modified Kaprekar Numbers
 * https://www.hackerrank.com/challenges/kaprekar-numbers
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class KaprekarNumbers {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        KaprekarNumbers a = new KaprekarNumbers();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int p = in.readInt(), q = in.readInt();
        StringBuilder sb = new StringBuilder();

        int base = 10;
        while (base <= p)
            base = (base << 3) + (base << 1);

        for (int i = p; i <= q; i++) {
            if (base == i)
                base = (base << 3) + (base << 1);

            long square = 1L * i * i;
            long sum = square / base + square % base;

            if (sum == i)
                sb.append(i).append(' ');
        }

        if (sb.length() == 0)
            sb.append("INVALID RANGE");

        out.println(sb);
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
    }
}
