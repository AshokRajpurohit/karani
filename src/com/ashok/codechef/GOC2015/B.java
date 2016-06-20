package com.ashok.codechef.GOC2015;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 *         problem Link:
 */
public class B {

    private static PrintWriter out;
    private static InputStream in;

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
        int q = in.readInt();

        while (q > 0) {
            q--;
            long a = in.readLong();
            long b = in.readLong();
            out.println(solve(a, b));
        }
    }

    private static long solve(long a, long b) {

        if (a == 1)
            return solve(b);

        a = a - 1;
        long ai;
        double a1 = Math.sqrt(a << 1);
        long bi;
        double b1 = Math.sqrt(b << 1);

        ai = (long) a1;
        bi = (long) b1;
        ai = ai == a1 ? ai - 1 : ai;
        bi = bi == b1 ? bi - 1 : bi;

        long adif = a - ai * (ai + 1) / 2;
        long ra = 0;
        if ((adif & 1) == 1)
            ra = ai + 1;
        ai = (ai + 1) >> 1;
        ai = ai * ai;

        long bdif = b - bi * (bi + 1) / 2;
        long res = 0;
        if (bdif % 2 == 1)
            res = bi + 1;

        bi = (bi + 1) >> 1;
        bi = bi * bi;

        return ai - bi + bdif - adif;

    }

    private static long solve(long b) {
        long bi;
        double b1 = Math.sqrt(b << 1);
        bi = (long) b1;
        bi = bi == b1 ? bi - 1 : bi;
        long bdif = b - bi * (bi + 1) / 2;
        long res = 0;
        if (bdif % 2 == 1)
            res = bi + 1;

        bi = (bi + 1) >> 1;
        bi = bi * bi;
        return bdif - bi + 2;
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
