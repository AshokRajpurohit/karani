package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem: Ilya and Diplomas
 * http://codeforces.com/contest/557/problem/A
 */

public class C311D2A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C311D2A a = new C311D2A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int min1 = in.readInt();
        int max1 = in.readInt();
        int min2 = in.readInt();
        int max2 = in.readInt();
        int min3 = in.readInt();
        int max3 = in.readInt();

        n = n - min1 - min2 - min3;
        if (n >= max1 - min1) {
            n = n - max1 + min1;
            min1 = max1;
        } else if (n > 0) {
            min1 += n;
            n = 0;
        }

        if (n >= max2 - min2) {
            n = n - max2 + min2;
            min2 = max2;
        } else if (n > 0) {
            min2 += n;
            n = 0;
        }

        if (n >= max3 - min3) {
            n = n - max3 + min3;
            min3 = max3;
        } else if (n > 0) {
            min3 += n;
            n = 0;
        }

        out.println(min1 + " " + min2 + " " + min3);
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
