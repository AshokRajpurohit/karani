package com.ashok.hackerearth.marathon.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Shino and Fibonacci
 * Link: https://www.hackerearth.com/july-circuits/algorithm/little-shino-and-fibonacci/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ShinoAndFibonacci {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int mod = 1000000007;
    private static long[] fibs = new long[15000];
    private static long loopSum = 0;

    static {
        fibs[1] = 1;

        for (int i = 2; i < fibs.length; i++)
            fibs[i] = (fibs[i - 1] + fibs[i - 2]) % 10000;

        for (int i = 1; i < fibs.length; i++) {
            fibs[i] += fibs[i - 1];

            if (fibs[i] >= mod)
                fibs[i] -= mod;
        }

        loopSum = fibs[fibs.length - 1];
    }

    public static void main(String[] args) throws IOException {
        ShinoAndFibonacci a = new ShinoAndFibonacci();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;

            sb.append(process(in.readLong(), in.readLong())).append('\n');
        }

        out.print(sb);
    }

    private static long process(long start, long end) {
        long res = process(end - 1) - process(start - 2);

        return res < 0 ? res + mod : res;
    }

    private static long process(long n) {
        if (n < fibs.length)
            return n < 0 ? 0 : fibs[(int) n];

        long count = (n / fibs.length) % mod;

        return ((count * loopSum % mod)
                + fibs[(int) (n % fibs.length)]) % mod;
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
