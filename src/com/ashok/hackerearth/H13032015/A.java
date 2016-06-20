package com.ashok.hackerearth.H13032015;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class A {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar;
    private static long[][] k_ar;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        int n = in.readInt();
        int q = in.readInt();
        ar = new int[n];
        k_ar = new long[10][n];

        for (int i = 0; i < n; i++) {
            ar[i] = in.readInt();
        }

        format_kar();

        for (int i = 0; i < q; i++) {
            int l = in.readInt() - 1;
            int r = in.readInt() - 1;
            int k = in.readInt();
            sb.append(solve(l, r, k)).append('\n');
        }

        out.print(sb);
    }

    private static long solve(int l, int r, int k) {
        if (l == r)
            return ar[l];
        if (k == 0) {
            return k_ar[0][r] + ar[l] - k_ar[0][l];
        }

        int mod = (r - l) % k;
        r = r - mod;
        return k_ar[k - 1][r] + ar[l] - k_ar[k - 1][l];
    }

    private static void format_kar() {

        for (int k = 0; k < 10; k++) {
            for (int i = 0; i <= k && i < ar.length; i++) {
                k_ar[k][i] = ar[i];
            }
            for (int i = k + 1; i < ar.length; i++) {
                k_ar[k][i] = k_ar[k][i - k - 1] + ar[i];
            }
        }
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
