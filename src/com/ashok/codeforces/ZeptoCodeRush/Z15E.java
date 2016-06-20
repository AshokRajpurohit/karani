package com.ashok.codeforces.ZeptoCodeRush;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  http://codeforces.com/contest/526/problem/E
 */

public class Z15E {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar, bak;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Z15E a = new Z15E();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int q = in.readInt();
        ar = new int[n];
        StringBuilder sb = new StringBuilder(q << 4);

        for (int i = 0; i < n; i++)
            ar[i] = in.readInt();

        long[] bar = new long[q];
        for (int i = 0; i < q; i++) {
            bar[i] = in.readLong();
        }

        for (int i = 0; i < q; i++) {
            sb.append(solve(bar[i])).append('\n');
        }

        out.print(sb);
    }

    private static int solve(long b) {
        bak = new int[ar.length];
        long cur = ar[0];
        int i = 0, min = solve(b, 1);
        i++;

        while (cur <= b && i < ar.length) {
            min = min > solve(b, i) ? solve(b, i) : min;
            cur = cur + ar[i];
            i++;
        }

        bak[0] = min + 1;
        return min;
    }

    private static int solve(long b, int i) {
        if (i == ar.length)
            return 0;

        if (i == ar.length - 1)
            return 1;

        if (bak[i] != 0)
            return bak[i];

        long cur = ar[i];
        int j = i + 1;
        int min = solve(b, j);
        while (cur <= b && j < ar.length) {
            min = min > solve(b, j) ? solve(b, j) : min;
            cur = cur + ar[j];
            j++;
        }
        bak[i] = 1 + min;
        return bak[i];
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
