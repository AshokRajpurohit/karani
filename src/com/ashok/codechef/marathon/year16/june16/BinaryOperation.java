package com.ashok.codechef.marathon.year16.june16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef And Binary Operation
 * https://www.codechef.com/JUNE16/problems/BINOP
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class BinaryOperation {

    private static PrintWriter out;
    private static InputStream in;
    private static final String lucky = "Lucky Chef ", unlucky =
        "Unlucky Chef\n";
    private static final char zero = '0', one = '1';

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        BinaryOperation a = new BinaryOperation();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 15);

        while (t > 0) {
            t--;
            int count = count(in.read(), in.read());

            if (count == 0)
                sb.append(unlucky);
            else
                sb.append(lucky).append(count).append('\n');
        }

        out.print(sb);
    }

    private static int count(String a, String b) {
        if (!possible(a))
            return 0;

        int zeroes = 0, ones = 0;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i))
                continue;

            if (a.charAt(i) == zero)
                zeroes++;
            else
                ones++;
        }

        return zeroes > ones ? zeroes : ones;
    }

    private static boolean possible(String s) {
        if (s.length() == 1)
            return false;

        for (int i = 1; i < s.length(); i++)
            if (s.charAt(i) != s.charAt(i - 1))
                return true;

        return false;
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
                if (buffer[offset] == '\n' || buffer[offset] == '\r' ||
                    buffer[offset] == '\t' || buffer[offset] == ' ')
                    break;

                sb.appendCodePoint(buffer[offset]);
                //                if (Character.isValidCodePoint(buffer[offset])) {
                //                    sb.appendCodePoint(buffer[offset]);
                //                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
