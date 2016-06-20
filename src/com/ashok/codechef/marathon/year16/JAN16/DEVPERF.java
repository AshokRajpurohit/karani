package com.ashok.codechef.marathon.year16.JAN16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Devu and Perfume
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class DEVPERF {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        DEVPERF a = new DEVPERF();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();
            String[] town = new String[n];
            for (int i = 0; i < n; i++)
                town[i] = in.read(m);

            out.println(process(town));
        }
    }

    private static int process(String[] town) {
        int n = town.length, m = town[0].length();
        int xmin = m, xmax = -1, ymin = n, ymax = -1;

        for (int i = 0; i < n && ymin == n; i++)
            for (int j = 0; j < m; j++)
                if (town[i].charAt(j) == '*') {
                    ymin = i;
                    break;
                }

        if (ymin == n)
            return 0;

        for (int j = 0; xmin == m; j++)
            for (int i = 0; i < n; i++)
                if (town[i].charAt(j) == '*') {
                    xmin = j;
                    break;
                }

        for (int i = n - 1; ymax == -1; i--)
            for (int j = 0; j < m; j++)
                if (town[i].charAt(j) == '*') {
                    ymax = i;
                    break;
                }

        for (int j = m - 1; xmax == -1; j--)
            for (int i = 0; i < n; i++)
                if (town[i].charAt(j) == '*') {
                    xmax = j;
                    break;
                }

        int dx = xmax - xmin + 1, dy = ymax - ymin + 1;
        if (dy > dx)
            return 1 + (dy >>> 1);

        return 1 + (dx >>> 1);
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
