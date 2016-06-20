package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problme Name | Confused Gorilla
 * https://www.hackerrank.com/contests/infinitum11/challenges/confused-gorilla
 */

public class Infinitum11A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Infinitum11A a = new Infinitum11A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            solve(n);
        }
    }

    private static void solve(int n) {
        StringBuilder sb = new StringBuilder(n << 3);
        if ((n & 1) == 0) {
            int r = n >> 1;
            int r1 = 0;

            while (r1 < r) {
                sb.append(r1).append(' ').append(n).append('\n');
                sb.append(n).append(' ').append(r1).append('\n');
                r1++;
                n--;
            }
            sb.append(r).append(' ').append(r).append('\n');
            out.print(sb);
            return;
        }

        int r = n >> 1;
        int r1 = 0;
        while (r1 <= r) {
            sb.append(r1).append(' ').append(n).append('\n');
            sb.append(n).append(' ').append(r1).append('\n');
            r1++;
            n--;
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
    }
}
