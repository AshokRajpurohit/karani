package com.ashok.codechef.marathon.year16.MARCH16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Palindromic substrings
 * https://www.codechef.com/MARCH16/problems/STRPALIN
 *
 * Strings s1 and s2 are substrings of A and B respectively.
 * Given that s1 + s2 is a palindrome, so first character of s1
 * and last character of s2 are equal.
 * So we know that atleast one character has to be common in s1 and s2.
 * what if s1 and s2 has exactly 2 characters then both the characters should
 * be equal, and this is a valid palindrome.
 *
 * So we just have to check if both the strings have one common character.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class STRPALIN {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        STRPALIN a = new STRPALIN();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String yes = "Yes\n", no = "No\n";

        while (t > 0) {
            t--;
            if (check(in.read(), in.read()))
                out.print(yes);
            else
                out.print(no);
        }
    }

    private static boolean check(String a, String b) {
        if (a.length() < 10 && b.length() < 10)
            return brute(a, b);

        boolean[] hash = new boolean[256];
        for (int i = 0; i < a.length(); i++)
            hash[a.charAt(i)] = true;

        for (int i = 0; i < b.length(); i++) {
            if (hash[b.charAt(i)])
                return true;
        }

        return false;
    }

    private static boolean brute(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < b.length(); j++) {
                if (a.charAt(i) == b.charAt(j))
                    return true;
            }
        }

        return false;
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
