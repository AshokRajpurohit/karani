package com.ashok.hackerRank.NumberTheory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Manasa and Factorials
 * Link: https://www.hackerrank.com/challenges/manasa-and-factorials
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ManasaAndFactorials {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        ManasaAndFactorials a = new ManasaAndFactorials();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        while (t > 0) {
            t--;
            sb.append(find(in.readLong())).append('\n');
        }

        out.print(sb);
    }

    private static long find(long zeroes) {
        if (zeroes == 1)
            return 5;

        long start = 5, end = Long.MAX_VALUE;
        long mid = (start + end) >>> 1;

        while (start != mid) {
            if (zeroes(mid) >= zeroes)
                end = mid;
            else
                start = mid;

            mid = (start + end) >>> 1;
        }

        return zeroes == zeroes(start) ? start : end;
    }

    private static long zeroes(long n) {
        long res = 0;

        while (n > 4) {
            n /= 5;
            res += n;
        }

        return res;
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
