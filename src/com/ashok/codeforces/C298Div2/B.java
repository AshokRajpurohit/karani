package com.ashok.codeforces.C298Div2;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link: http://codeforces.com/contest/534/problem/B
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
        int v1 = in.readInt();
        int v2 = in.readInt();
        int t = in.readInt();
        int d = in.readInt();
        int alpha = (v1 - v2) % d == 0 ? 0 : 1;
        int v2_2 = v2;

        if (v1 > v2) {
            v2_2 = v2 + (v1 - v2) % d;
        } else if (alpha == 1) {
            v2_2 = v2 + d - (v1 - v2);
        }

        int p = (v2_2 - v1) / d;
        int r = (t - alpha - 2 + p) / 2;
        int r2 = r - p;
        int d1 = r * v1 + (r * (r - 1) * d) / 2;
        int d2 = r2 * v2_2 + (r2 * (r2 + 1) * d) / 2 + alpha * v2;
        out.println(d1 + d2);
        out.println(r + "," + r2);
        out.println(v2_2);

        //        int r = t - alpha + (v2 - v1) / d;
        //        int v2_2 = v2 + (v1 + r * d - v2) % d;
        //        int r2 = (v1 + r * d - v2);
        //        int d1 = (r + 1) * v1 + (r * (r + 1) * d) / 2;
        //        int d2 = (r2 + 1) * v2_2 + (r2 * (r2 + 1) * d) / 2;
        //        if (v2_2 > v2)
        //            d2 = d2 + v2;
        //        out.println(d1 + d2 - v1 - r * d);
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
