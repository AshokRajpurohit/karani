package com.ashok.codechef.marathon.year16.APRIL16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Devu and good strings
 * https://www.codechef.com/APRIL16/problems/DEVGOSTR
 *
 * You can see that in this solution, literally I have created all the
 * possible strings. Not a nice solution to go through.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class DEVGOSTR {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static char[] charArray = new char[26];
    private static int maxDistance = 0, charSize = 0;

    static {
        for (int i = 0; i < 26; i++)
            charArray[i] = (char)('a' + i);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        DEVGOSTR a = new DEVGOSTR();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            charSize = in.readInt();
            maxDistance = in.readInt();

            out.println(process(in.read()));
        }
    }

    private static long process(String s) {
        if (maxDistance == 0) {
            char[] ar = s.toCharArray();
            for (int i = 2; i < s.length(); i++) {
                if (!validate(ar, i))
                    return 0;
            }

            return 1;
        }

        long res = 0;
        char[] original = s.toCharArray(), ar = new char[s.length()];
        for (int i = 0; i < charSize; i++) {
            ar[0] = charArray[i];
            if (ar[0] != original[0])
                res += process(original, ar, 1, 1);
            else
                res += process(original, ar, 1, 0);
        }

        return res;
    }

    private static long process(char[] source, char[] var, int index,
                                int distance) {
        if (index == var.length) {
            return 1;
        }

        if (distance == maxDistance) {
            for (int i = index; i < source.length; i++) {
                var[i] = source[i];

                if (!validate(var, i))
                    return 0;
            }

            return 1;
        }

        long res = 0;

        for (int i = 0; i < charSize; i++) {
            var[index] = charArray[i];
            if (validate(var, index)) {
                if (var[index] == source[index])
                    res += process(source, var, index + 1, distance);
                else
                    res += process(source, var, index + 1, distance + 1);
            }
        }

        return res;
    }

    private static boolean validate(char[] ar, int index) {
        for (int i = index - 1, j = index - 2; j >= 0; i--, j -= 2) {
            if (ar[i] == ar[j] && ar[j] == ar[index])
                return false;
        }

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
