package com.ashok.hackerearth.alkhwarizm;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:
 */

public class E {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar, bar;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        E a = new E();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int l = in.readInt();
        ar = new int[l];
        bar = new int[l];

        for (int i = 0; i < l; i++) {
            ar[i] = in.readInt();
        }

        out.println(solve(0));

    }

    private static int solve(int i) {
        if (i == ar.length)
            return 0;

        if (i == ar.length - 1)
            return 1;

        if (bar[i] != 0)
            return bar[i];

        int res = 0;
        while (ar[i] == 0 && i < ar.length) {
            res++;
            i++;
        }

        bar[i] = res + 1 + solve(i + 1);
        if (ar[i] - 1 > i) {
            bar[i] = Math.min(bar[i], res + solve(ar[i] - 1));
        }
        return bar[i];

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
