package com.ashok.hackerearth.marathon.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Mancunian in Palindromia
 * Link: https://www.hackerearth.com/october-circuits/algorithm/mancunian-in-palindromia-3/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MancunianInPalindromia {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        long time = System.currentTimeMillis();
        solve();
        out.println("time: " + (System.currentTimeMillis() - time));
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), L = in.readInt();
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (possible(in.read(L).toCharArray()))
                count++;
        }

        out.println(count);
    }

    private static boolean possible(char[] name) {
        if (isPalindrom(name))
            return true;

        if (name.length == 3)
            return name[0] == name[1] || name[2] == name[1];

        for (int i = 0; i < name.length; i++)
            for (int j = i + 1; j < name.length; j++) {
                reverse(name, i, j);
                if (isPalindrom(name))
                    return true;


                for (int k = j + 1; k < name.length; k++)
                    for (int l = k + 1; l < name.length; l++) {
                        reverse(name, k, l);
                        if (isPalindrom(name))
                            return true;

                        reverse(name, k, l);
                    }

                reverse(name, i, j);
            }

        return false;
    }

    private static void reverse(char[] ar, int start, int end) {
        while (start < end) {
            char temp = ar[start];
            ar[start] = ar[end];
            ar[end] = temp;

            start++;
            end--;
        }
    }

    private static boolean isPalindrom(char[] ar) {
        if (ar.length == 1)
            return true;

        for (int i = 0, j = ar.length - 1; i < j; i++, j--)
            if (ar[i] != ar[j])
                return false;

        return true;
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
