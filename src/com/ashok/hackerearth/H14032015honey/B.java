package com.ashok.hackerearth.H14032015honey;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 */
public class B {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] ar_0 = new long[91], ar_1 = new long[91];

    public B() {
        creatFibAr();
    }


    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        B a = new B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        creatFibAr();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int m = in.readInt();
            sb.append(ar_1[m]).append(' ').append(ar_0[m]).append('\n');
        }

        out.print(sb);
    }

    /**
     * This function was used to create the arrays (fibonacci) for 0's and 1's.
     */
    private static void creatFibAr() {
        long a = 0, b = 1; // a is no of 0's and b is no of 1's
        ar_1[0] = 1;
        ar_0[0] = 0;

        for (int i = 1; i <= 90; i++) {
            a = a + b;
            b = a - b;
            ar_0[i] = a;
            ar_1[i] = b;
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
