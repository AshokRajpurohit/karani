package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 * problem: Intelligent Girl
 */

public class ThoughtWorksB {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] digits = new int[256];

    static {
        for (int i = '0'; i <= '9'; i++)
            digits[i] = i - '0';

        for (int i = '0'; i <= '9'; i++) {
            if ((digits[i] & 1) == 0)
                digits[i] = 1;
            else
                digits[i] = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        ThoughtWorksB a = new ThoughtWorksB();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String s = in.read();
        StringBuilder sb = new StringBuilder(s.length() << 2);
        int sum = 0;
        for (int i = 0; i < s.length(); i++)
            sum += digits[s.charAt(i)];

        for (int i = 0; i < s.length(); i++) {
            sb.append(sum).append(' ');
            sum -= digits[s.charAt(i)];
        }
        sb.append('\n');
        out.print(sb);
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
