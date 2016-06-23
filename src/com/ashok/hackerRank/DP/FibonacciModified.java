package com.ashok.hackerRank.DP;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Fibonacci Modified
 * https://www.hackerrank.com/challenges/fibonacci-modified
 */

public class FibonacciModified {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        FibonacciModified a = new FibonacciModified();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(process(in.readInt(), in.readInt(), in.readInt()));
    }

    private static String process(int a, int b, int n) {
        if (n == 1)
            return String.valueOf(a);

        if (n == 2)
            return String.valueOf(b);

        int i = 3;
        //        long one = a, two = b, res = 0;
        /*
        while (i <= n && one <= Long.MAX_VALUE - two * two) {
            res = one + two * two;
            i++;
            one = two;
            two = res;
        }

        if (i > n)
            return String.valueOf(res);
         */

        BigInteger bone = new BigInteger(String.valueOf(a));
        BigInteger btwo = new BigInteger(String.valueOf(b));
        BigInteger bres = BigInteger.ZERO;

        while (i <= n) {
            bres = bone.add(btwo.multiply(btwo));
            bone = btwo;
            btwo = bres;
            i++;
        }

        return bres.toString();
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
