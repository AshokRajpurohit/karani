package com.ashok.hackerRank.Strings;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Bigger is Greater
 * https://www.hackerrank.com/challenges/bigger-is-greater
 */

public class BiggerIsGreater {

    private static PrintWriter out;
    private static InputStream in;
    private static String no = "no answer";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        BiggerIsGreater a = new BiggerIsGreater();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(process(in.read())).append('\n');
        }
        out.print(sb);
    }

    private static String process(String s) {
        if (notPossible(s))
            return no;

        StringBuilder sb = new StringBuilder(s);
        int i = s.length() - 2;
        while (s.charAt(i) >= s.charAt(i + 1))
            i--;

        int j = s.length() - 1;
        while (s.charAt(j) <= s.charAt(i))
            j--;

        sb.setCharAt(i, s.charAt(j));
        sb.setCharAt(j, s.charAt(i));

        s = sort(sb.substring(i + 1));

        sb.delete(i + 1, sb.length());
        sb.append(s);
        return sb.toString();
    }

    private static String sort(String s) {
        int[] ar = new int[256];
        for (int i = 0; i < s.length(); i++)
            ar[s.charAt(i)]++;

        StringBuilder sb = new StringBuilder(s.length() + 1);
        for (int i = 'a'; i <= 'z'; i++)
            while (ar[i] > 0) {
                ar[i]--;
                sb.append((char)i);
            }

        return sb.toString();
    }

    private static boolean notPossible(String s) {
        if (s.length() == 1)
            return true;

        for (int i = 1; i < s.length(); i++)
            if (s.charAt(i) > s.charAt(i - 1))
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
