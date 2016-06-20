package com.ashok.codechef.marathon.year16.FEB16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Devu and a light discussion
 * https://www.codechef.com/FEB16/problems/DEVLDISC
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class DEVLDISC {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        DEVLDISC a = new DEVLDISC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 6);
        String yes =
            "\n1 5\n1 6\n2 7\n2 8\n3 5\n3 8\n4 6\n4 7\n6 7\n5 8\n", no =
            "-1\n";

        String seven = "8\n1 3\n1 2\n1 6\n2 4\n3 5\n6 4\n6 5\n5 7\n3\n";

        while (t > 0) {
            t--;
            int n = in.readInt();

            if (n < 7)
                sb.append(no);
            else if (n == 7) {
                sb.append(seven);
            } else {
                int edges = 10 + (n - 8) * 2;
                sb.append(edges);

                sb.append(yes);
                for (int i = 9; i <= n; i++) {
                    sb.append("1 " + i + "\n" +
                            i + " 7\n");
                }

                sb.append("1\n");
            }
        }

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
