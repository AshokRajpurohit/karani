package com.ashok.hackerRank.NumberTheory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Closest Number
 * Link: https://www.hackerrank.com/challenges/closest-number
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ClosestNumber {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ClosestNumber a = new ClosestNumber();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(process(in.readInt(), in.readInt(), in.readInt()))
                    .append('\n');
        }

        out.print(sb);
    }

    private static long process(int a, int b, int c) {
        if (a == 0)
            return 0;

        if (a == 1)
            return c == 1 ? 1 : 0;

        if (b < 0)
            return 0;

        long pow = pow(a, b);
        long mod = pow % c;

        if (mod * 2 <= c)
            return pow - mod;

        return pow + c - mod;
    }

    private static long pow(long a, long b) {

        if (a == 1 || a == 0 || b == 1)
            return a;

        if (b == 0)
            return 1;

        boolean sign = true;
        if ((b & 1) == 1 && a < 0)
            sign = false;

        if (a < 0)
            a = -a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = res * res;
            if ((b & r) != 0) {
                res = res * a;
            }
        }
        return sign ? res : -res;
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
