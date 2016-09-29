package com.ashok.hackerRank.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Problem Name: Sherlock and Valid String
 * Link: https://www.hackerrank.com/challenges/sherlock-and-valid-string
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SherlockAndValidString {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final String yes = "YES", no = "NO";
    private static int[] charToIndex = new int[256];

    static {
        for (int i = 'b'; i <= 'z'; i++)
            charToIndex[i] = i - 'a';
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        String s = in.read();
        int[] count = new int[256];

        for (int i = 0; i < s.length(); i++)
            count[s.charAt(i)]++;

        LinkedList<Integer> frequency = new LinkedList<>();
        for (int i = 'a'; i <= 'z'; i++)
            if (count[i] != 0)
                frequency.add(count[i]);

        Collections.sort(frequency);

        int f = frequency.getFirst(), se = frequency.getLast();
        if (f == se) {
            out.println(yes);
            return;
        }

        if (frequency.getFirst() == 1) {
            frequency.removeFirst();
            f = frequency.getFirst();
            se = frequency.getLast();

            if (f == se)
                out.println(yes);
            else
                out.println(no);

            return;
        }

        if (frequency.getLast() - frequency.getFirst() != 1) {
            out.println(no);
            return;
        }

        int last = frequency.removeLast();

        if (last != frequency.removeLast())
            out.println(yes);
        else
            out.println(no);
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
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
