package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link: https://www.hackerrank.com/contests/101hack23/challenges/devu-and-minimizing-runs-of-a-sequence
 */

public class Hack23B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Hack23B a = new Hack23B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            String s = in.read();
            sb.append(solve(s)).append('\n');
        }
        out.print(sb);
    }

    private static int solve(String s) {
        if (s.length() == 1)
            return 1;
        int[] ar = new int[26];
        int count = 1;
        ar[s.charAt(0) - 'A'] = 1;
        for (int i = 1; i < s.length(); i++) {
            ar[s.charAt(i) - 'A']++;
            if (s.charAt(i) != s.charAt(i - 1))
                count++;
        }
        if (count == 1)
            return count;
        
        int countd = 0;

        if ((s.charAt(0) != s.charAt(1)) && (ar[s.charAt(0) - 'A'] > 1)) {
            countd = 1;
        }

        if ((s.charAt(s.length() - 1) != s.charAt(s.length() - 2)) &&
            (ar[s.charAt(s.length() - 1) - 'A'] > 1))
            countd = 1;

        int i = 1;
        for (i = 1; i < s.length() - 1; i++) {
            if ((s.charAt(i) != s.charAt(i + 1)) &&
                (s.charAt(i + 1) == s.charAt(i - 1))) {
                if (ar[s.charAt(i) - 'A'] > 1)
                    return count - 2;
                else
                    countd = 1;
            } else if (s.charAt(i) != s.charAt(i + 1) &&
                       s.charAt(i) != s.charAt(i - 1) &&
                       ar[s.charAt(i) - 'A'] > 1)
                countd = 1;
        }

        return count - countd;
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
