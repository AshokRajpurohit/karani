package com.ashok.codechef.marathon.year16.MAY16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Balls
 * https://www.codechef.com/MAY16/problems/CHBLLS
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHBLLS {

    private static PrintWriter out;
    private static InputStream in;
    private static final String check = "1\n3 1 2 2\n3 3 4 4\n";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CHBLLS a = new CHBLLS();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();

        out.print(check);
        out.flush();

        int diff = in.readInt(), ans = 0;

        if (diff == 0)
            ans = 5;
        else if (diff == -1)
            ans = 3;
        else if (diff == 1)
            ans = 1;
        else if (diff == 2)
            ans = 2;
        else
            ans = 4;

        out.println("2\n" +
                ans);
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
