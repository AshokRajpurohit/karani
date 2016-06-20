package com.ashok.codechef.marathon.year15.SEP15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Terrible vectors
 * https://www.codechef.com/SEPT15/problems/TERVEC
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class TERVEC {

    private static PrintWriter out;
    private static InputStream in;
    private static String yes = "YES\n", no = "NO\n";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        TERVEC a = new TERVEC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            process(sb, in.readInt());
        }
        out.print(sb);
    }

    private static void process(StringBuilder sb, int n) {
        if (n == 1 || n == 2 || n == 4)
            sb.append(yes);
        else {
            sb.append(no);
            return;
        }

        if (n == 1)
            sb.append("1\n");
        else if (n == 2)
            sb.append("1 1 1 -1\n");
        else
            sb.append("1 1 1 -1 -1 1 1 1 1 -1 1 1 1 1 -1 1\n");
        return;
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
