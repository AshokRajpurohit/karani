package com.ashok.codechef.cook;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Cops and the Thief Devu
 * Link: https://www.codechef.com/COOK60/problems/COPS
 */

public class C60A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        C60A a = new C60A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 3);

        while (t > 0) {
            t--;
            int m = in.readInt(), x = in.readInt(), y = in.readInt();
            sb.append(process(x, y, in.readIntArray(m))).append('\n');
        }
        out.print(sb);
    }

    private static int process(int x, int y, int[] m) {
        boolean[] houses = new boolean[100];
        int len = x * y;

        for (int i = 0; i < m.length; i++) {
            for (int j = m[i] - 1; j < 100 && j <= m[i] + len - 1; j++)
                houses[j] = true;

            for (int j = m[i] - 1; j >= 0 && j >= m[i] - len - 1; j--)
                houses[j] = true;
        }

        int count = 0;
        for (int i = 0; i < 100; i++)
            if (!houses[i])
                count++;

        return count;
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
