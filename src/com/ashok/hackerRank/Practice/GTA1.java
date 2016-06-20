package com.ashok.hackerRank.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link:
 */

public class GTA1 {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static long[] fact;
    private static long modinv2;
    static {
        modinv2 = pow(2, mod - 2);
        fact = new long[31];
        fact[0] = 1;
        for (int i = 1; i < 31; i++)
            fact[i] = (fact[i - 1] * i) % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        GTA1 a = new GTA1();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            long res = fact[n];
            for (int i = 0; i < n; i++)
                res = (res * fact[in.readInt()]) % mod;
            res = (res * modinv2) % mod;
            out.println(res);
        }
    }

    public static long pow(long a, long b) {
        a = a % mod;
        if (a <= 1)
            return a;

        if (b == 1)
            return a;

        if (b == 0)
            return 1;

        long r = 1, res = a;
        r = r << 62;

        while ((b & r) == 0)
            r = r >> 1;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
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
