package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem  Detecting Valid Latitude and Longitude Pairs - Key-line
 * https://www.hackerrank.com/contests/magiclines/challenges/detecting-valid-latitude-and-longitude-pairs-key-line
 */

public class MagicLineC {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MagicLineC a = new MagicLineC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String valid = "Valid\n", invalid = "Invalid\n";
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(7 * t);

        while (t > 0) {
            t--;
            if (solve(in.read(), in.read()))
                sb.append(valid);
            else
                sb.append(invalid);
        }
        out.println(sb);
    }

    private static boolean solve(String s1, String s2) {
        double longi, latti;
        int i = 1;
        if (s1.charAt(i) == '+' || s1.charAt(i) == '-')
            i++;

        if (s1.charAt(i) == '0')
            return false;

        while (s1.charAt(i) != '.' && s1.charAt(i) != ',')
            i++;

        if (s1.charAt(i) == '.' && s1.charAt(i + 1) == ',')
            return false;

        i = 0;
        if (s2.charAt(i) == '+' || s2.charAt(i) == '-')
            i++;

        if (s2.charAt(i) == '0')
            return false;

        while (s2.charAt(i) != '.' && s2.charAt(i) != ')')
            i++;

        if (s2.charAt(i) == '.' && s2.charAt(i + 1) == ')')
            return false;

        latti = Double.parseDouble(s1.substring(1, s1.length() - 1));
        longi = Double.parseDouble(s2.substring(0, s2.length() - 1));

        if (latti > 90 || latti < -90 || longi > 180 || longi < -180)
            return false;

        return true;
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
