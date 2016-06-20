package com.ashok.codechef.marathon.year15.APRIL15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  http://www.codechef.com/APRIL15/problems/BROKPHON
 */

public class A {

    private static PrintWriter out;
    private static InputStream in;

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
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = new int[n];

            for (int i = 0; i < n; i++)
                ar[i] = in.readInt();

            boolean bar[] = new boolean[n];
            for (int i = 1; i < n; i++) {
                if (ar[i] != ar[i - 1])
                    bar[i] = true;
            }
            for (int i = 0; i < n - 1; i++) {
                if (ar[i] != ar[i + 1])
                    bar[i] = true;
            }
            int count = 0;
            for (int i = 0; i < n; i++)
                if (bar[i])
                    count++;

            sb.append(count).append('\n');

        }

        out.print(sb);
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
