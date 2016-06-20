package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Diamonds
 * https://www.hackerearth.com/magnasoft-java-hiring-challenge/problems/1d8578bec6f84f80b9dce405c6dd2c8d/
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class MagnaSoftDiamonds {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MagnaSoftDiamonds a = new MagnaSoftDiamonds();
        a.solve();
        out.close();
    }

    private static int process(String[] ar) {
        int n = ar.length, m = ar[0].length(), count = 0;

        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < m - 1; j++)
                if (match(ar, i, j))
                    count++;

        return count;
    }

    private static boolean match(String[] ar, int i, int j) {
        return ar[i].charAt(j) == '/' && ar[i].charAt(j + 1) == '\\' &&
                ar[i + 1].charAt(j) == '\\' && ar[i + 1].charAt(j + 1) == '/';
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.read(in.readInt(), in.readInt())));
        }
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
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (buffer[offset] != ' ') {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }

        public String[] read(int n, int m) throws IOException {
            String[] ar = new String[n];
            for (int i = 0; i < n; i++)
                ar[i] = read(m);

            return ar;
        }
    }
}
