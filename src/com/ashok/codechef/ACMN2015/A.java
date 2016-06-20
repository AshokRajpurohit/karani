package com.ashok.codechef.ACMN2015;


//import static java.lang.System.in;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class A {

    //    private static NewInputReader in;
    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder();

        while (t > 0) {
            t--;
            String s = in.read();
            sb.append(lpm(s)).append('\n');
        }
        out.print(sb);
    }

    public String lpm(String s) {
        int i = 0;
        boolean[][] lp = new boolean[s.length()][s.length()];

        int maxl = 0;
        int b = 0, c = 0;

        for (int j = 0; j < s.length(); j++) {
            for (int k = i; k < s.length(); k++) {
                lpm(s, j, k, lp);
                if (lp[j][k]) {
                    if (maxl < k + 1 - j) {
                        maxl = k + 1 - j;
                        b = j;
                        c = k;
                    }
                }
            }
        }

        return s.substring(b, c + 1);
    }

    private boolean lpm(String s, int i, int j, boolean[][] lp) {
        if (i >= j || lp[i][j])
            return true;

        if (s.charAt(i) == s.charAt(j)) {
            lp[i][j] = lpm(s, i + 1, j - 1, lp);
        }

        return lp[i][j];
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
                         '\n'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n')
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
