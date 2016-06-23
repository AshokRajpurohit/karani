package com.ashok.codechef.marathon.year16.FEB16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Strange Operations
 * https://www.codechef.com/FEB16/problems/STROPR
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class STROPR {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static long[] inverse = new long[100000];
    private static long bits = Long.highestOneBit(mod - 2);

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        STROPR a = new STROPR();
        inverse[1] = 1;
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt(), x = in.readInt();
            long m = in.readLong();
            long[] ar = in.readLongArray(n);

            out.println(process(n, x - 1, m % mod, ar));
        }
    }

    private static long process(int n, int x, long m, long[] ar) {
        if (x == 0 || m == 0)
            return ar[x] % mod;

        long res = ar[x], multiply = 1;
        int r = 1;

        for (int i = x - 1; i >= 0; i--, m++, r++) {
            multiply = (multiply * m % mod) * inverse(r) % mod;
            res += multiply * (ar[i] % mod) % mod;
        }

        return res % mod;
    }

    private static long inverse(int a) {
        if (inverse[a] == 0)
            inverse[a] = calculate(a);

        return inverse[a];
    }

    private static long calculate(int a) {
        long b = mod - 2;
        long r = bits, res = a;

        while (r > 1) {
            r = r >> 1;
            res = res * res % mod;
            if ((b & r) != 0) {
                res = res * a % mod;
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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }
}
