package com.ashok.codechef.marathon.year16.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Keyboard
 * Link: https://www.codechef.com/OCT16/problems/CHEFKEY
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CHEFKEY {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;

            sb.append(process(in.readInt(), in.readInt(), in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static int process(int n, int m, int c) {
        long mul = 1L * n * m;
        int sqrt = (int) Math.sqrt(c);
        int count = 0;

        for (int i = 1; i <= n && i <= sqrt; i++) {
            if (c % i != 0)
                continue;

            int j = c / i;
            if (j <= m)
                count++;

            if (i != j && i <= m && j <= n)
                count++;
        }

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
