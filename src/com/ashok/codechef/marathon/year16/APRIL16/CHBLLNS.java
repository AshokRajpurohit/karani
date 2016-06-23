package com.ashok.codechef.marathon.year16.APRIL16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Ballons
 * https://www.codechef.com/APRIL16/problems/CHBLLNS
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHBLLNS {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHBLLNS a = new CHBLLNS();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(process(in.readInt(), in.readInt(), in.readInt(),
                              in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static long process(int r, int g, int b, int k) {
        if (k == 1)
            return 1;

        if (k == 2)
            return 4;

        long balls = 0;
        if (r < k)
            balls += r;
        else
            balls += k - 1;

        if (g < k)
            balls += g;
        else
            balls += k - 1;

        if (b < k)
            balls += b;
        else
            balls += k - 1;

        return balls + 1;
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
