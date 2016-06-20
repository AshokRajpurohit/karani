package com.ashok.hackerearth.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link: https://www.hackerearth.com/problem/algorithm/next-lucky-number/description/
 */

public class NextLuckyNumber {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        NextLuckyNumber a = new NextLuckyNumber();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(500);

        while (t > 0) {
            t--;
            String s = in.read();
            int i = 0;
            while (i < s.length() &&
                   (s.charAt(i) == '3' || s.charAt(i) == '5'))
                i++;

            if (i < s.length()) {
                if (s.charAt(i) < '3') {
                    for (int j = 0; j < i; j++)
                        sb.append(s.charAt(j));

                    while (i < s.length()) {
                        i++;
                        sb.append('3');
                    }

                } else if (s.charAt(i) < '5') {
                    for (int j = 0; j < i; j++)
                        sb.append(s.charAt(j));

                    sb.append('5');
                    i++;
                    while (i < s.length()) {
                        i++;
                        sb.append('3');
                    }

                } else {
                    int j = i - 1;
                    while (j >= 0 && s.charAt(j) == '5')
                        j--;
                    if (j >= 0) {
                        for (int k = 0; k < j; k++)
                            sb.append(s.charAt(k));

                        sb.append('5');
                        j++;
                        while (j < s.length()) {
                            j++;
                            sb.append('3');
                        }
                    } else {
                        sb.append('3');
                        for (int k = 0; k < s.length(); k++)
                            sb.append('3');
                    }
                }
            } else {
                int j = i - 1;
                while (j >= 0 && s.charAt(j) == '5')
                    j--;

                if (j < 0) {
                    for (int k = 0; k < s.length(); k++)
                        sb.append('3');

                    sb.append('3');
                } else {
                    for (int k = 0; k < j; k++)
                        sb.append(s.charAt(k));

                    sb.append('5');
                    j++;
                    while (j < s.length()) {
                        j++;
                        sb.append('3');
                    }
                }
            }
            sb.append('\n');
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
