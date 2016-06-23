package com.ashok.codechef.marathon.year15.OCT15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: KSPHERES
 * https://www.codechef.com/OCT15/problems/KSPHERES
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class KSPHERES {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        KSPHERES a = new KSPHERES();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), m = in.readInt(), c = in.readInt();

        int[] up = sort(in.readIntArray(n), c);
        int[] down = sort(in.readIntArray(m), c);
        long[] multi = multiply(up, down);

        long[] res = new long[c + 1];
        for (int i = 1; i <= c; i++) {
            for (int j = i; j > 0; j--) {
                res[j] = (res[j] + res[j - 1] * multi[i] % mod) % mod;
            }

            res[0] = (res[0] + multi[i]) % mod;
        }

        StringBuilder sb = new StringBuilder(c << 3);
        for (int i = 1; i <= c; i++)
            sb.append(res[i]).append(' ');

        sb.append('\n');
        out.print(sb);
    }

    private static long[] multiply(int[] up, int[] down) {
        long[] res = new long[up.length];
        for (int i = 0; i < up.length; i++)
            res[i] = 1L * up[i] * down[i] % mod;

        return res;
    }

    private static int[] sort(int[] ar, int c) {
        int[] sort = new int[c + 1];
        for (int i = 0; i < ar.length; i++)
            sort[ar[i]]++;

        return sort;
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
