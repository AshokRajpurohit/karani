package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 */

public class C315D2B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C315D2B a = new C315D2B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        StringBuilder sb = new StringBuilder(5 * n);
        int[] ar = in.readIntArray(n);
        boolean[] num = new boolean[n];
        boolean[] rem = new boolean[n + 1];

        for (int i = 0; i < n; i++) {
            if (ar[i] <= n) {
                if (!rem[ar[i]]) {
                    num[i] = true;
                    rem[ar[i]] = true;
                }
            }
        }

        int i = 0, j = 1;
        while (i < n && j <= n) {
            if (num[i])
                i++;
            else if (rem[j])
                j++;
            else {
                ar[i] = j;
                i++;
                j++;
            }
        }

        for (int e : ar)
            sb.append(e).append(' ');
        out.println(sb);
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
