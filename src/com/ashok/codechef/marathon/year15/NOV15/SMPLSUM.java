package com.ashok.codechef.marathon.year15.NOV15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Simple Sum
 * https://www.codechef.com/NOV15/problems/SMPLSUM
 *
 * We have to calculate Sum(n / gcd(i, n)), where i is from 1 to n.
 * It's obvious that for prime number n value of this function is
 *          n * (n - 1) + 1
 * we can see that for i = 1 to n - 1 gcd (i, n) is 1 and for i = n it's 1.
 * The sum is n * (n - 1) + 1.
 *
 * This function is multiplicative. for composite number n = p * q where
 * p and q are prime the sum is sum_for_p * sum_for_q
 * i. e. (p * (p - 1) + 1) * (q * (q - 1) + 1).
 *
 * for composite number that is power of p let's say n = p * p.
 * sum(n) = 1 + p * (p - 1) + p ^ 3 * (p - 1).
 *
 * for n = p * p * p (i. e. p ^ 3)
 * sum(n) = sum(p * p) + p ^ 5 * (p - 1)
 * and so on.
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class SMPLSUM {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] gcdSum;

    static {
        gcdSum = new long[10000001];
        for (int i = 1; i < 10000001; i++) {
            gcdSum[i] = 1;
        }

        int i = 2;

        for (; i < 3163; i++) {
            while (gcdSum[i] != 1)
                i++;

            long a = i * (i - 1) + 1;
            long b = a;

            for (int j = i; j < 10000001; j += i) {
                gcdSum[j] *= a;
            }

            int temp = i * i, sq = i * i;
            long base = i * (i - 1);
            while (temp < 10000001 && temp > 0) {
                base *= sq;
                b = a + base;
                for (int j = temp; j < 10000001; j += temp)
                    gcdSum[j] = b * (gcdSum[j] / a);

                a = b;
                temp *= i;
            }
        }

        for (; i < 5000001; i++)
            if (gcdSum[i] == 1) {
                long a = 1L * i * (i - 1) + 1;
                for (int j = i; j < 10000001; j += i) {
                    gcdSum[j] *= a;
                }
            }

        for (i = 5000001; i < 10000001; i++)
            if (gcdSum[i] == 1)
                gcdSum[i] *= (1L * i * (i - 1) + 1);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SMPLSUM a = new SMPLSUM();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(gcdSum[in.readInt()]).append('\n');
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
