package com.ashok.codechef.marathon.year15.AUG15;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Gravity Guy
 * https://www.codechef.com/AUG15/problems/GRGUY
 */

public class GRGUY {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        GRGUY a = new GRGUY();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 6);

        while (t > 0) {
            t--;
            process(in.read(), in.read(), sb);
        }
        out.print(sb);
    }

    private static void process(String a, String b, StringBuilder sb) {
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '#' && b.charAt(i) == '#') {
                sb.append("No\n");
                return;
            }
        }

        sb.append("Yes\n");
        int ajump = 0, bjump = 0;
        int i = 0;
        while (i < a.length()) {
            while (i < a.length() && a.charAt(i) == '.')
                i++;

            if (i != a.length())
                ajump++;

            while (i < a.length() && b.charAt(i) == '.')
                i++;

            if (i != a.length())
                ajump++;
        }

        i = 0;
        while (i < a.length()) {
            while (i < a.length() && b.charAt(i) == '.')
                i++;

            if (i != a.length())
                bjump++;

            while (i < a.length() && a.charAt(i) == '.')
                i++;

            if (i != a.length())
                bjump++;
        }

        sb.append(Math.min(ajump, bjump)).append('\n');
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
