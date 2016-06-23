package com.ashok.hackerearth.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * @author Ashok Rajpurohit
 * problem Link: https://www.hackerearth.com/problem/algorithm/small-factorials/;
 */

public class SmallFactorials {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] fact;
    private static BigInteger[] facto;

    static {
        fact = new long[21];
        fact[0] = 1;
        for (int i = 1; i < 21; i++)
            fact[i] = fact[i - 1] * i;

        facto = new BigInteger[81];
        facto[0] = new BigInteger(String.valueOf(fact[20]));

        for (int i = 1, j = 21; i <= 80; i++, j++) {
            facto[i] =
                    facto[i - 1].multiply(new BigInteger(String.valueOf(j)));
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SmallFactorials a = new SmallFactorials();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            solve(in.readInt());
        }
    }

    private static void solve(int n) {
        if (n < 21) {
            out.println(fact[n]);
            return;
        }
        out.println(facto[n - 20]);
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
