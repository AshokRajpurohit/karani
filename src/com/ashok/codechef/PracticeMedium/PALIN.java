package com.ashok.codechef.PracticeMedium;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: PALIN
 * http://www.codechef.com/problems/PALIN
 */

public class PALIN {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        PALIN a = new PALIN();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(solve(in.read()));
        }
    }

    private static String solve(String s) {
        if (allnine(s))
            return next(s.length() + 1);

        char[] ar = new char[s.length()];
        int first = (s.length() - 1) >>> 1;
        int end = s.length() - 1 - first;
        boolean changed = false;
        int i = 0, j = 0;

        for (i = first, j = end; i >= 0 && j < s.length(); i--, j++) {
            if (s.charAt(i) != s.charAt(j))
                break;
        }

        if (i < 0 || s.charAt(i) < s.charAt(j)) {
            i = first;
            j = end;
            while (s.charAt(i) == '9') {
                ar[i] = '0';
                ar[j] = '0';
                i--;
                j++;
            }
            ar[i] = (char)(s.charAt(i) + 1);
            ar[j] = ar[i];
            i--;
            j++;
            while (i >= 0) {
                ar[i] = s.charAt(i);
                ar[j] = ar[i];
                i--;
                j++;
            }
            return String.valueOf(ar);
        } else {
            i = first;
            j = end;
            while (i >= 0) {
                ar[i] = s.charAt(i);
                ar[j] = ar[i];
                i--;
                j++;
            }
        }
        return String.valueOf(ar);
    }

    private static String next(int n) {
        char[] ar = new char[n];
        ar[0] = '1';
        ar[n - 1] = '1';
        for (int i = 1; i < n - 1; i++)
            ar[i] = '0';
        return String.valueOf(ar);
    }

    private static boolean allnine(String s) {
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) != '9')
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
