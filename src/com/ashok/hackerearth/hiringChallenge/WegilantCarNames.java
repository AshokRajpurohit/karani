package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113
 * problem: Car Names
 * Wegilant Java Hiring Challenge | Car Names
 */

public class WegilantCarNames {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        WegilantCarNames a = new WegilantCarNames();
        a.solve();
        out.close();
    }

    private static boolean nameOk(String s) {
        if (s.length() % 3 != 0)
            return false;

        int len = s.length() / 3;
        if (s.charAt(0) == s.charAt(len) ||
                s.charAt(0) == s.charAt(s.length() - 1))
            return false;

        char c = s.charAt(0);
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) != c)
                return false;
        }

        c = s.charAt(len);
        for (int i = len; i < 2 * len; i++)
            if (s.charAt(i) != c)
                return false;

        c = s.charAt(len << 1);
        len = len << 1;
        for (int i = len; i < s.length(); i++)
            if (s.charAt(i) != c)
                return false;

        return true;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String yes = "OK\n", no = "Not OK\n";
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            if (nameOk(in.read()))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
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
