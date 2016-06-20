package com.ashok.hackerearth.H14032015honey;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
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
        StringBuilder sb = new StringBuilder();
        int n = in.readInt();
        long[] ar = new long[n];

        for (int i = 0; i < n; i++) {
            ar[i] = in.readInt();
        }

        long d = 1;
        int q = in.readInt();
        for (int i = 0; i < q; i++) {
            d = d * in.readInt();
        }

        /**
        * would some tell me why I am checking division by zero?
        * becoz without this two test cases failed.
        * who expects this when in question it was explicitly mentioned
        * that value of D is greater than 0.
        */
        if (d == 0) {
            for (int i = 0; i < n; i++) {
                sb.append("0 ");
            }
        } else {
            for (int i = 0; i < n; i++) {
                ar[i] = ar[i] / d;
                sb.append(ar[i]).append(' ');
            }
        }

        sb.append('\n');
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
