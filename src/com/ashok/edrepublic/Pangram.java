package com.ashok.edrepublic;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * check whether a string is pangram or not.
 */

public class Pangram {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] charIndex = new int[256];
    private static char[] charOrder = new char[26];
    private static boolean[] charValid = new boolean[256];

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            charIndex[i] = i - 'a';
            charValid[i] = true;
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            charIndex[i] = i - 'A';
            charValid[i] = true;
        }

        for (int i = 0; i < 26; i++)
            charOrder[i] = (char)(i + 'A');
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Pangram a = new Pangram();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.print(process(in.read()));
    }

    private static String process(String s) {
        boolean[] charAr = new boolean[26];
        for (int i = 0; i < s.length(); i++) {
            if (charValid[s.charAt(i)])
                charAr[charIndex[s.charAt(i)]] = true;
        }

        int count = 0;
        for (int i = 0; i < 26; i++)
            if (!charAr[i])
                count++;

        if (count == 0)
            return "-\n";

        StringBuilder sb = new StringBuilder(count + 1);
        for (int i = 0; i < 26; i++) {
            if (!charAr[i])
                sb.append(charOrder[i]);
        }
        sb.append('\n');
        return sb.toString();
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
                if (buffer[offset] == '\t' || buffer[offset] == '\n' ||
                    buffer[offset] == '\r')
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
