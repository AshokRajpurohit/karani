package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 */

public class MacAfeeA {

    private static PrintWriter out;
    private static InputStream in;
    private static String s;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MacAfeeA a = new MacAfeeA();
        a.solve();
        out.close();
    }

    private static int noPal() {
        if (s.charAt(0) != s.charAt(s.length() - 1))
            return s.length();

        int[] ar = new int[256];
        for (int i = 0; i < s.length(); i++)
            ar[s.charAt(i)]++;

        int count = 0;

        for (int i = 'a'; i <= 'z'; i++)
            if (ar[i] != 0)
                count++;

        if (count == 1)
            return 1;

        if (check())
            return s.length() - 1;
        return s.length();
    }

    private static boolean check() {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--)
            if (s.charAt(i) != s.charAt(j))
                return false;

        return true;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        s = in.read();
        out.println(noPal());
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
