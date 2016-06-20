package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Modulo Sum
 * http://codeforces.com/contest/577/problem/B
 *
 * @author: Ashok Rajpurohit
 */

public class C319B {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C319B a = new C319B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int m = in.readInt();
        int[] ar = new int[n];
        for (int i = 0; i < n; i++)
            ar[i] = in.readInt() % m;

        if (process(ar, m))
            out.print("YES\n");
        else
            out.print("NO\n");
    }

    private static boolean process(int[] ar, int m) {
        if (ar.length >= m)
            return true;

        mod = m;
        int t = 0;
        for (int i = 0; i < ar.length; i++)
            t += ar[i];

        return exists(ar, t % mod, 0);
    }

    private static boolean exists(int[] ar, int m, int index) {
        if (index == ar.length)
            return false;

        if (m == 0)
            return true;

        if (m < 0)
            m += mod;

        return exists(ar, m - ar[index], index + 1) ||
            exists(ar, m, index + 1);
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
