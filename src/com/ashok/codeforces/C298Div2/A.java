package com.ashok.codeforces.C298Div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;


/**
 * @author Ashok Rajpurohit
 * problem Link: http://codeforces.com/contest/534/problem/A
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
        int n = in.readInt();
        if (n == 3) {
            out.println("2\n1 3");
            return;
        }
        if (n == 2) {
            out.println("1\n1");
            return;
        }
        if (n == 1) {
            out.println("1\n1");
            return;
        }
        if (n == 4) {
            out.println("4\n2 4 1 3");
            return;
        }
        StringBuilder sb = new StringBuilder(n << 3);
        sb.append(n).append('\n');
        for (int i = 1; i <= n; i = i + 2) {
            sb.append(i).append(' ');
        }
        for (int i = 2; i <= n; i = i + 2)
            sb.append(i).append(' ');
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
    }
}
