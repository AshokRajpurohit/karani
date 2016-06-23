package com.ashok.codechef.marathon.year16.MARCH16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef And Special Dishes
 * https://www.codechef.com/MARCH16/problems/CHEFSPL
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFSPL {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHEFSPL a = new CHEFSPL();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String yes = "YES\n", no = "NO\n";
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            if (check(in.read()))
                sb.append(yes);
            else
                sb.append(no);
        }

        out.print(sb);
    }

    private static boolean check(String s) {
        if (s.length() == 1)
            return false;

        if (s.length() % 2 == 0)
            return isDouble(s);

        int mid = s.length() >>> 1;
        int diff = diff(s, 0, mid + 1, true);

        if (diff == 1)
            return true;

        diff = diff(s, 0, mid, false);
        return diff == 1;
    }

    private static int diff(String s, int a, int b, boolean first) {
        int i = a, j = b, diff = 0;
        for (; i < b && j < s.length(); ) {
            if (s.charAt(i) != s.charAt(j)) {
                if (first)
                    i++;
                else
                    j++;

                diff++;
                break;
            } else {
                i++;
                j++;
            }
        }

        while (i < b && j < s.length()) {
            if (s.charAt(i) != s.charAt(j))
                diff++;

            i++;
            j++;
        }

        return diff + s.length() - j + b - i;
    }

    private static boolean isDouble(String s) {
        int mid = s.length() >>> 1;

        for (int i = 0; mid < s.length(); i++, mid++)
            if (s.charAt(i) != s.charAt(mid))
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
