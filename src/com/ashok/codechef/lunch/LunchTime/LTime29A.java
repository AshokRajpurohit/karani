package com.ashok.codechef.lunch.LunchTime;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: LTIME29 | emitL
 * https://www.codechef.com/LTIME29/problems/EMITL
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class LTime29A {

    private static PrintWriter out;
    private static InputStream in;
    private static String str = "EMITL";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LTime29A a = new LTime29A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String yes = "YES\n", no = "NO\n";
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            if (possible(in.read()))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
    }

    private static boolean possible(String s) {
        if (s.length() < 9)
            return false;

        int[] ar = new int[256];
        boolean res = true;

        for (int i = 0; i < s.length(); i++)
            ar[s.charAt(i)]++;

        if (s.length() == 9) {
            if (ar[str.charAt(0)] != 1)
                return false;

            for (int i = 1; i < str.length(); i++)
                if (ar[str.charAt(i)] != 2)
                    return false;

            return true;
        }

        for (int i = 0; i < str.length(); i++)
            if (ar[str.charAt(i)] < 2)
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
