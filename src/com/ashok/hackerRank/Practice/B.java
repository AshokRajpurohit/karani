package com.ashok.hackerRank.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link:    https://www.hackerrank.com/contests/101hack22/challenges/lcm-and-gcd
 */

public class B {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 10000000;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        B a = new B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = new int[n];

            for (int i = 0; i < n; i++)
                ar[i] = in.readInt();

            int gcd = 0;
            for (int i = 0; i < n; i++)
                gcd = gcd(gcd, ar[i]);

            for (int i = 0; i < n; i++)
                ar[i] = ar[i] / gcd;

            int ls = gcd;
            for (int i = 0; i < n; i++) {
                ls = ls * ar[i];
                ls = ls % mod;
            }
            ls = ls * gcd;
            out.println(ls);
        }
    }

    public static int gcd(int i, int j) {

        if (i == 0)
            return j;

        if (j == 0)
            return i;

        if (i == 1)
            return 1;

        if (j == 1)
            return 1;

        int res = 0;

        while (((i | j) & 1) == 0) {
            res++;
            i = i >> 1;
            j = j >> 1;
        }

        while ((i & 1) == 0)
            i = i >> 1;

        while ((j & 1) == 0)
            j = j >> 1;

        while ((i ^ j) != 0) {
            if (i > j) {
                i = i - j;
                while ((i & 1) == 0)
                    i = i >> 1;
            }
            if (j > i) {
                j = j - i;
                while ((j & 1) == 0)
                    j = j >> 1;
            }
        }

        return i << res;
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
