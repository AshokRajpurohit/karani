package com.ashok.edrepublic;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Largest Palindrome SubString (smallest lexicographically)
 */

public class LongPalin {

    private static PrintWriter out;
    private static InputStream in;
    private int[][] length;
    private int rLeft = 0, rRight = 0; // Largest Palindrome Strings boudries.
    private int count = 0;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LongPalin a = new LongPalin();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(process(in.read()));
        out.println(count);
    }

    private String process(String s) {
        if (isPalin(s))
            return s;

        length = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++)
            for (int j = i + 1; j < s.length(); j++)
                length[i][j] = -1;

        for (int i = 0; i < s.length(); i++)
            length[i][i] = 1;

        int min = 1000;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) < min) {
                min = s.charAt(i);
                rLeft = i;
                rRight = i;
            }

        process(s, 0, s.length() - 1);
        return s.substring(rLeft, rRight + 1);
    }

    private void process(String s, int begin, int end) {
        count++;
        if (begin >= end)
            return;

        if (length[begin][end] != -1)
            return;

        if (s.charAt(begin) == s.charAt(end)) {
            process(s, begin + 1, end - 1);
            if (length[begin + 1][end - 1] + 2 == end + 1 - begin) {
                length[begin][end] = end + 1 - begin;
                if (length[begin][end] > rRight + 1 - rLeft) {
                    rLeft = begin;
                    rRight = end;
                } else if (length[begin][end] == rRight + 1 - rLeft) {
                    if (begin < rLeft) {
                        rLeft = begin;
                        rRight = end;
                    }
                    /*
                    if (compareString(s, begin, end, rLeft, rRight) == -1) {
                        rLeft = begin;
                        rRight = end;
                    }
                     */
                }
            } else
                length[begin][end] = 0;
        } else
            length[begin][end] = 0;

        if (begin + 1 < end) {
            process(s, begin + 1, end);
            process(s, begin, end - 1);
        }
    }

    private static int compareString(String s, int begin, int end, int start,
                                     int last) {
        int i = begin, j = start;
        while (i <= end && j <= last) {
            if (s.charAt(i) < s.charAt(j))
                return -1;
            else if (s.charAt(i) > s.charAt(j))
                return 1;

            i++;
            j++;
        }
        return 0;
    }

    private static boolean isPalin(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--)
            if (s.charAt(i) != s.charAt(j))
                return false;
        return true;
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
