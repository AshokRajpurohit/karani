package com.ashok.hackerRank.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit ashok1113@gmail.com
 * problem Link:  https://www.hackerrank.com/challenges/john-and-gcd-list
 * Number Theory
 */

public class NTE2 {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        NTE2 a = new NTE2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(1000);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int a = in.readInt(), b = 0;
            sb.append(a).append(' ');
            for (int i = 1; i < n; i++) {
                b = in.readInt();
                sb.append((a * b) / gcd(a, b)).append(' ');
                a = b;
            }
            sb.append(b).append('\n');
        }
        out.print(sb);
    }

    private static int gcd(int a, int b) {
        if (a == 0 || b == 0)
            return a | b;

        if (a == 1 || b == 1)
            return 1;

        return gcd(b, a % b);
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
