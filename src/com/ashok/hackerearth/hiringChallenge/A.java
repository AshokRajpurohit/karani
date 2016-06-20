package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 */
public class A {

    private static PrintWriter out;
    private static InputStream in;
    private static char[] ar;
    private static char xor = 'W' ^ 'B';

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    private static int formatw() {
        return formatw(0);
    }

    private static int formatb() {
        return formatb(0);
    }

    private static int formatw(int i) {

        while (i < ar.length && ar[i] == 'W')
            i++;

        if (i == ar.length - 1)
            return Integer.MAX_VALUE >> 10;

        if (i == ar.length)
            return 0;

        int res2 = 0, res3 = 0;

        ar[i] = (char) (ar[i] ^ xor);
        ar[i + 1] = (char) (ar[i + 1] ^ xor);

        res2 = 1 + formatw(i + 1);

        if (i < ar.length - 2) {
            ar[i + 2] = (char) (ar[i + 2] ^ xor);
            res3 = 1 + formatw(i + 1);
            ar[i + 2] = (char) (ar[i + 2] ^ xor);
        }

        ar[i] = (char) (ar[i] ^ xor);
        ar[i + 1] = (char) (ar[i + 1] ^ xor);

        if (res2 > res3)
            return res3;

        return res2;
    }

    private static int formatb(int i) {

        while (i < ar.length && ar[i] == 'B')
            i++;

        if (i == ar.length - 1)
            return Integer.MAX_VALUE >> 10;

        if (i == ar.length)
            return 0;

        int res2 = 0, res3 = 0;

        ar[i] = (char) (ar[i] ^ xor);
        ar[i + 1] = (char) (ar[i + 1] ^ xor);

        res2 = 1 + formatb(i + 1);

        if (i < ar.length - 2) {
            ar[i + 2] = (char) (ar[i + 2] ^ xor);
            res3 = 1 + formatb(i + 1);
            ar[i + 2] = (char) (ar[i + 2] ^ xor);
        }

        ar[i] = (char) (ar[i] ^ xor);
        ar[i + 1] = (char) (ar[i + 1] ^ xor);

        if (res2 > res3)
            return res3;

        return res2;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        String s = in.read();
        ar = s.toCharArray();
        int w = formatw();
        int b = formatb();
        w = w > b ? b : w;
        out.println(w);

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
