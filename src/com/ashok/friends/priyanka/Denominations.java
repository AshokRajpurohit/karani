package com.ashok.friends.priyanka;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Denominations {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int a = 5, b = 6;
        int c = ++b * a / b * b;
        out.println(c);
        out.flush();
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.readInt()));
        }
    }

    private static long process(int n) {
        if (n < 3)
            return 0;

        if (n == 3)
            return 1;

        if (n == 5)
            return 1;

        if (n < 5)
            return 0;

        while (n % 5 != 0 && n > 0)
            n -= 3;

        if (n == 0)
            return 1;

        long count = 0;
        int s = 1 + n / 10;
        int tCount = (s + 2) / 3; // number of terms in series.
        count = 1L * tCount * (1 + (s - 1) % 3 + s) / 2; // it is like n * [a + L] / 2 in a AP series.
        n -= 15;
        if (n < 0)
            return count;

        s = 1 + n / 10;
        tCount = (s + 2) / 3;
        count += 1L * tCount * (1 + (s - 1) % 3 + s) / 2;
        return count;
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