package com.ashok.hackerRank.Mathematics;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Sherlock and Divisors
 * https://www.hackerrank.com/challenges/sherlock-and-divisors
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

class SherlockAndDivisors {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[] primes;

    static {
        primes = new boolean[31624];

        for (int i = 2; i < primes.length; i++)
            primes[i] = true;

        for (int i = 2; i < primes.length; i++) {
            if (!primes[i])
                continue;

            for (int j = i << 1; j < primes.length; j += i)
                primes[j] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SherlockAndDivisors a = new SherlockAndDivisors();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(process(in.readInt()));
        }
    }

    private static int process(int n) {
        if (n % 2 != 0)
            return 0;

        return count(n >>> 1);
    }

    private static int count(int n) {
        int count = 1;

        for (int i = 2; i < primes.length && i <= n; i++) {
            if (primes[i]) {
                int temp = 1;
                while (n % i == 0) {
                    temp++;
                    n /= i;
                }

                count *= temp;
            }
        }

        if (n != 1)
            return count << 1;

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
