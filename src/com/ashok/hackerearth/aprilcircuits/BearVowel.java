package com.ashok.hackerearth.aprilcircuits;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: 1A - Bear and Vowels
 * Challenge: April Circuits
 * https://www.hackerearth.com/april-circuits/algorithm/circ-bear-and-vowels-2/
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class BearVowel {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[] map = new boolean[256];
    private static String vowels = "aeiouy";

    static {
        for (int i = 0; i < 6; i++)
            map[vowels.charAt(i)] = true;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        BearVowel a = new BearVowel();
        a.solve();
        out.close();
    }

    private static boolean process(String s) {
        int vowels = 0;

        for (int i = 0; i < s.length(); i++)
            if (map[s.charAt(i)])
                vowels++;

        if (s.length() > (vowels << 1))
            return true;

        for (int i = 1; i < s.length() - 1; i++) {
            if (map[s.charAt(i)] || map[s.charAt(i - 1)] ||
                    map[s.charAt(i + 1)])
                continue;

            return true;
        }

        return false;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 5);

        while (t > 0) {
            t--;
            if (process(in.read()))
                sb.append("hard\n");
            else
                sb.append("easy\n");
        }

        out.print(sb);
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
