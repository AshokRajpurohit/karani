package com.ashok.hackerRank.NumberTheory;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem: Fibonacci GCD Again
 * https://www.hackerrank.com/contests/infinitum11/challenges/fibonacci-gcd-again
 */

public class FibGCD {

    private static PrintWriter out;
    private static InputStream in;
    private static long[][] transpose = { { 1, 1 }, { 1, 0 } };
    private static long[] basic = { 1, 0 };
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        in = System.in;
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        FibGCD a = new FibGCD();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            long n = in.readLong();
            int a0 = in.readInt();
            int a1 = in.readInt();
            int a2 = in.readInt();
            int b0 = in.readInt();
            int b1 = in.readInt();
            int b2 = in.readInt();
            mod = in.readInt();
            long fn = getNthFibNumber(n - 1);
            long fn1 = getNthFibNumber(n);
            long fn2 = fn + fn1;
            long num1 = a0 * fn + a1 * fn1 + a2 * fn2;
            long num2 = b0 * fn + b1 * fn1 + b2 * fn2;
            sb.append(gcd(num1 % mod, num2 % mod)).append('\n');
        }
        out.print(sb);
    }

    private static long gcd(long a, long b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    private static long getNthFibNumber(long n) {
        if (n == 0)
            return basic[0];

        if (n == -1)
            return basic[1];

        long[][] result = new long[2][2];
        long[][] temp = new long[2][2];
        copy(transpose, result);
        long r = Long.highestOneBit(n);
        while (r > 1) {
            r = r >>> 1;
            multiply(result, result, temp);
            if ((r & n) != 0)
                multiply(result, transpose, temp);
        }

        return (result[0][0] * basic[0] + result[0][1] * basic[1]) % mod;
    }

    private static void multiply(long[][] a, long[][] b, long[][] temp) {
        reset(temp);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                for (int k = 0; k < 2; k++)
                    temp[i][j] += a[i][k] * b[k][j];
        copy(temp, a);
    }

    private static void copy(long[][] source, long[][] target) {
        target[0][0] = source[0][0] % mod;
        target[0][1] = source[0][1] % mod;
        target[1][0] = source[1][0] % mod;
        target[1][1] = source[1][1] % mod;
    }

    private static void reset(long[][] ar) {
        ar[0][0] = 0;
        ar[0][1] = 0;
        ar[1][0] = 0;
        ar[1][1] = 0;
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }
    }
}
