package com.ashok.hackerRank.Implementation;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Sherlock and The Beast
 * https://www.hackerrank.com/challenges/sherlock-and-the-beast
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class SherlockBeast {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SherlockBeast a = new SherlockBeast();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(40);

        while (t > 0) {
            t--;
            process(sb, in.readInt());
        }
        out.print(sb);
    }

    private static void process(StringBuilder sb, int n) {
        if (n % 3 == 0)
            populate(sb, n, 0);
        else if (n % 3 == 1)
            populate(sb, n - 10, 10);
        else
            populate(sb, n - 5, 5);

        return;
    }

    private static void populate(StringBuilder sb, int five, int three) {
        if (five < 0) {
            sb.append("-1\n");
            return;
        }

        for (int i = 0; i < five; i++)
            sb.append(5);

        for (int i = 0; i < three; i++)
            sb.append(3);

        sb.append('\n');
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
