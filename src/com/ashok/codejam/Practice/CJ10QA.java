package com.ashok.codejam.Practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem Link: Qualification Round Africa 2010 Problem A. Store Credit
 * CJ10QA_s.in and CJ10QA_s.out are small input and output files.
 */

public class CJ10QA {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] par = new int[1001];
    private static String format = "Case #";

    public static void main(String[] args) throws IOException {
        //        OutputStream outputStream = System.out;
        //        in = System.in;
        //        out = new PrintWriter(outputStream);

        String input = "CJ10QA_l.in", output = "CJ10QA_l.out";
        FileInputStream fip = new FileInputStream(input);
        FileOutputStream fop = new FileOutputStream(output);
        in = fip;
        out = new PrintWriter(fop);

        CJ10QA a = new CJ10QA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 5);

        for (int j = 1; j <= t; j++) {
            int c = in.readInt();
            int l = in.readInt();
            int[] ar = new int[l];
            for (int i = 0; i < l; i++) {
                ar[i] = in.readInt();
            }
            sb.append(format).append(j).append(": ");
            solve(ar, c, sb);
        }
        out.print(sb);
    }

    private static void solve(int[] ar, int c, StringBuilder sb) {
        for (int i = 1; i <= 1000; i++) {
            par[i] = 0;
        }

        for (int i = 0; i < ar.length; i++) {
            par[ar[i]] = 1;
        }

        for (int i = 0; i < ar.length; i++) {
            if (c > ar[i] && par[c - ar[i]] != 0) {
                int j = i + 1;
                for (; j < ar.length && ar[j] != c - ar[i]; j++)
                    ;
                if (j != ar.length) {
                    sb.append(i + 1).append(' ');
                    sb.append(j + 1).append('\n');
                    return;
                }
            }
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
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
