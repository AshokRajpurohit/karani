package com.ashok.codechef.marathon.year15.MARCH15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link:    http://www.codechef.com/MARCH15/problems/STRSUB
 */
public class A {

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
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int k = in.readInt();
            int q = in.readInt();
            String s = in.read();

            if (k == 1) {
                for (int i = 0; i < q; i++) {
                    int l = in.readInt();
                    int r = in.readInt();
                    int temp = r + 1 - l;
                    sb.append(temp).append('\n');
                }
            } else {
                for (int i = 0; i < q; i++) {
                    int l = in.readInt() - 1;
                    int r = in.readInt() - 1;
                    sb.append(solve(s, k, l, r)).append('\n');
                }
            }
        }
        out.print(sb);
    }

    private int solve(String s, int k, int l, int r) {
        if (r + 1 - l <= k) {
            int temp = r + 1 - l;
            temp = temp * (temp + 1);
            temp = temp >> 1;
            return temp;
        }

        int i = l, j = l;
        int count_0 = 0, count_1 = 0, max_count = 0, substr_count = 0;

        while (i != r + 1) {
            if (s.charAt(i) == '0') {
                count_0++;
                max_count = count_0 > max_count ? count_0 : max_count;
            } else {
                count_1++;
                max_count = count_1 > max_count ? count_1 : max_count;
            }

            if (max_count > k) {
                while (s.charAt(j) != s.charAt(i)) {
                    j++;
                }
                j++;
                if (count_0 == max_count)
                    count_0 = k;
                else
                    count_1 = k;
                max_count = k;
            }
            substr_count = substr_count + i + 1 - j;
            i++;
        }
        return substr_count;
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
