package com.ashok.codeforces.contest;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: A Trivial Problem
 * problem Link: http://codeforces.com/contest/633/problem/B
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class C633B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C633B a = new C633B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int m = process(in.readInt());

        if (m > 0) {
            out.println(5);
            for (int i = 0; i < 5; i++, m++)
                out.print(m + " ");
        } else
            out.println(0);
    }

    private static int process(int n) {
        int m = n << 2;
        int zero = zeroes(m);
        while (zero < n) {
            m++;
            zero = zeroes(m);
        }

        if (zero != n)
            return 0;

        return m;
    }

    private static int zeroes(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }

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
    }
}
