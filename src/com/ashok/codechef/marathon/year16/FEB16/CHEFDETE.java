package com.ashok.codechef.marathon.year16.FEB16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef-Detective
 * https://www.codechef.com/FEB16/problems/CHEFDETE
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFDETE {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHEFDETE a = new CHEFDETE();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        StringBuilder sb = new StringBuilder(n);
        boolean[] check = new boolean[n + 1];

        for (int i = 0; i < n; i++) {
            check[in.readInt()] = true;
        }

        for (int i = 1; i <= n; i++)
            if (!check[i])
                sb.append(i).append(' ');

        out.println(sb);
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
