package com.ashok.hackerRank.Strings;


import java.io.*;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Morgan and a String
 * https://www.hackerrank.com/challenges/morgan-and-a-string
 */

public class MorganString {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        //        OutputStream outputStream = System.out;
        //        in = System.in;
        //        out = new PrintWriter(outputStream);


        String input = "input_file.in", output = "output_file.out";
        FileInputStream fip = new FileInputStream(input);
        FileOutputStream fop = new FileOutputStream(output);
        in = fip;
        out = new PrintWriter(fop);

        MorganString a = new MorganString();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(1000000);

        while (t > 0) {
            t--;
            sb.append(process(in.read(), in.read())).append('\n');
        }
        out.print(sb);
    }

    /**
     * Work in progress
     * @param a
     * @param b
     * @return
     */
    private static String process(String a, String b) {
        StringBuilder sb = new StringBuilder(a.length() + b.length());
        int i = 0, j = 0;
        for (; i < a.length() && j < b.length(); ) {
            if (a.charAt(i) < b.charAt(j)) {
                sb.append(a.charAt(i));
                i++;
            } else if (a.charAt(i) > b.charAt(j)) {
                sb.append(b.charAt(j));
                j++;
            } else {
                int m = i, n = j;
                char cha = a.charAt(i), chb = b.charAt(j);

                while (m < a.length() && a.charAt(m) == cha)
                    m++;

                if (m < a.length())
                    cha = a.charAt(m);

                while (n < b.length() && b.charAt(n) == chb)
                    n++;

                if (n < b.length())
                    chb = b.charAt(n);

                if (cha < chb) {
                    while (i < m) {
                        sb.append(a.charAt(i));
                        i++;
                    }
                } else {
                    while (j < n) {
                        sb.append(b.charAt(j));
                        j++;
                    }
                }
            }
        }

        while (i < a.length()) {
            sb.append(a.charAt(i));
            i++;
        }

        while (j < b.length()) {
            sb.append(b.charAt(j));
            j++;
        }
        return sb.toString();
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
