package com.ashok.codechef.marathon.year16.june16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Array and K
 * https://www.codechef.com/JUNE16/problems/CHEFARK
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFARK {

    private static PrintWriter out;
    private static InputStream in;
    private static int size = 100001;
    private static long[] factorial = new long[size];
    private static long[] inverse = new long[size];
    private static final int mod = 1000000007, highBit =
        Integer.highestOneBit(mod - 2);

    static {
        factorial[0] = 1;
        for (int i = 1; i < size; i++) {
            factorial[i] = factorial[i - 1] * i;

            if (factorial[i] >= mod)
                factorial[i] %= mod;
        }

        inverse[0] = 1;
        inverse[1] = 1;

        for (int i = 2; i < size; i++)
            inverse[i] = inverse(factorial[i]);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHEFARK a = new CHEFARK();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt(), k = in.readInt(), count = 0;

            // we don't need array elements, non-zero element count is enough.
            for (int i = 0; i < n; i++)
                if (in.readInt() != 0)
                    count++;

            out.println(process(n, k, count));
        }
    }

    private static long process(int n, int k, int count) {
        long res = 0;
        int lim = n > k ? k : n;

        if (lim > count)
            return exp(count);

        int increment = n == count ? 2 : 1;
        int i = n != count ? 0 : k & 1;

        for (; i <= lim; i += increment)
            res += nCk(n, i);

        return res % mod;
    }

    private static long nCk(int n, int k) {
        if (k == 0 || n == k)
            return 1;

        if (k == 1 || k == n - 1)
            return n;

        return (factorial[n] * inverse[k] % mod) * inverse[n - k] % mod;
    }

    private static long inverse(long a) {
        if (a == 1 || a == 0)
            return a;

        int r = highBit, b = mod - 2;
        long res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }

        return res;
    }

    private static long exp(int b) {
        if (b == 0)
            return 1;

        if (b == 1)
            return 2;

        long r = Long.highestOneBit(b), res = 2;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res << 1) % mod;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
