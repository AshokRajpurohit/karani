package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:
 *  https://www.hackerearth.com/march-clash-15/algorithm/bits-transformation-1/
 */

public class Mar152 {

    private static PrintWriter out;
    private static InputStream in;
    private static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Mar152 a = new Mar152();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        sb = new StringBuilder(500);
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int x = in.readInt(); // '0' to '1' cost
            int y = in.readInt(); // '1' to '0' cost
            int z = in.readInt(); // '?' to '*' cost
            int sw = in.readInt(); // swap cost
            String a = in.read();
            String b = in.read();
            int count1 = 0, count0 = 0, countq0 = 0, countq1 = 0;

            for (int i = 0; i < n; i++) {
                if (a.charAt(i) != b.charAt(i)) {
                    if (a.charAt(i) == '?') {
                        if (b.charAt(i) == '0')
                            countq0++;
                        else
                            countq1++;
                    } else if (a.charAt(i) == '1')
                        count1++;
                    else
                        count0++;
                }
            }

            int cost = 0, count = countq0 + countq1;
            cost = count * z;

            if (t >= x + y) {
                cost = cost + count1 * y + count0 * x;
            } else {
                if (count1 > count0) {
                    count1 = count1 - count0;
                    if (y > sw) {
                        if (count1 > countq1) {
                            count1 = count1 - countq1;
                            cost = cost + countq1 * sw;
                        } else {
                            cost = cost + count1 * sw;
                            count1 = 0;
                        }
                    }
                    cost = cost + count0 * sw + count1 * y;
                } else {
                    count0 = count0 - count1;
                    if (x > sw) {
                        if (count0 > countq0) {
                            count0 = count0 - countq0;
                            cost = cost + countq0 * sw;
                        } else {
                            cost = cost + count0 * sw;
                            count0 = 0;
                        }
                    }
                    cost = cost + count1 * sw + count0 * x;
                }
            }

            sb.append(cost).append('\n');
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
