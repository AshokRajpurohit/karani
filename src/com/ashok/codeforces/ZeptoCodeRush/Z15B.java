package com.ashok.codeforces.ZeptoCodeRush;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  http://codeforces.com/contest/526/problem/B
 */

public class Z15B {

    private static PrintWriter out;
    private static InputStream in;
    private static long count;
    private static int[] ar;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Z15B a = new Z15B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        ar = new int[(1 << (n + 1)) - 2];

        for (int i = 0; i < ar.length; i++) {
            ar[i] = in.readInt();
        }

        count = 0;
        solve(0);
        out.println(count);
    }

    private static long solve(int i) {
        if (i >= ar.length / 2)
            return 0;
        long left = solve(i + i + 1) + ar[i + i];
        long right = solve(i + i + 2) + ar[i + i + 1];
        count = left > right ? count + left - right : count + right - left;
        return left > right ? left : right;
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
