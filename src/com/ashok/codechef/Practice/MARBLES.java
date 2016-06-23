package com.ashok.codechef.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113
 * problem Link: http://www.codechef.com/problems/MARBLES
 */

public class MARBLES {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MARBLES a = new MARBLES();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int k = in.readInt();
            sb.append(solve(n, k)).append('\n');
        }
        out.print(sb);
        out.flush();
    }

    private static long solve(int n, int k) {
        if (n == k || k == 1)
            return 1;

        if (n == 1)
            return k;

        int r = n - k;
        int c = n - 1;
        r = r > c - r ? c - r : r;

        long res = c + 1 - r;
        for (int j = (int)res + 1, i = 2; j <= c; j++, i++) {
            res = (res * j) / i;
        }
        return res;
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
    }
}
