package com.ashok.codechef.IPC15P1A;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Max Subarray GCD
 * https://www.codechef.com/IPC15P1A/problems/GCDMAX1
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class GCDMAX1 {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);


        GCDMAX1 a = new GCDMAX1();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), k = in.readInt();
        out.println(process(in.readIntArray(n), k, n));
    }

    private static int process(int[] ar, int k, int n) {
        if (k == 1)
            return n;

        int res = 0;
        for (int i = 0; i < n - res; i++) {
            int g = ar[i];
            int j = i + 1;
            if (g >= k) {
                for (j = i + 1; j < n && g >= k; j++) {
                    g = gcd(g, ar[j]);
                    if (g < k) {
                        break;
                    }
                }
                res = Math.max(res, j - i);
            }
        }

        return res;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
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
