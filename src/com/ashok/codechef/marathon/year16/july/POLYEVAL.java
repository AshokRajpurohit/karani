package com.ashok.codechef.marathon.year16.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Evaluate the polynomial
 * Link: https://www.codechef.com/JULY16/problems/POLYEVAL
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class POLYEVAL {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int mod = 786433, bruteForce = 10000000;
    private static long[] cache = new long[mod];
    private static boolean[] check = new boolean[mod];

    public static void main(String[] args) throws IOException {
        POLYEVAL a = new POLYEVAL();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int n = in.readInt();
        int[] poly = in.readIntArray(n + 1);

        for (int i = 0; i <= n; i++)
            poly[i] %= mod;

        int q = in.readInt();
        long limit = 1L * n * q;
        StringBuilder sb = new StringBuilder(q << 3);
        check[0] = true;
        cache[0] = poly[0];

        long ref = bruteForce(poly, mod - 1);
        while (q > 0) {
            q--;
            int diff = mod - in.readInt();

            if (diff == 0)
                sb.append(ref).append('\n');
            else
                sb.append((ref + mod) % diff).append('\n');
        }

        while (q > 0) {
            q--;

            if (limit <= bruteForce)
                sb.append(bruteForce(poly, in.readInt() % mod)).append('\n');
            else
                sb.append(process(poly, in.readInt() % mod)).append('\n');
        }

        out.print(sb);
    }

    private static long bruteForce(int[] poly, final int x) {
        if (check[x])
            return cache[x];

        long res = poly[0];
        long variable = x;

        for (int i = 1; i < poly.length; i++) {
            res += poly[i] * variable;
            variable = variable * x % mod;
        }

        cache[x] = res % mod;
        check[x] = true;
        return cache[x];
    }

    private static long process(int[] poly, int x) {
        return bruteForce(poly, x);
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
