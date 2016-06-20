package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem:Product divisors
 * Challenge: Quintype Backend Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class QuintypeProductDivisors {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] primes;
    private static int mod = 1000000007;
    private static boolean[] prime_check;

    private static void gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }

        prime_check = ar;
        primes = ret;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        QuintypeProductDivisors a = new QuintypeProductDivisors();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        int max = 0;

        for (int e : ar)
            if (max < e)
                max = e;

        gen_prime(max);

        int[] factors = new int[max + 1];
        for (int i = 1; i <= max; i++)
            factors[i] = 1;

        for (int i = 0; i < ar.length; i++) {
            if (!prime_check[ar[i]]) {
                factors[ar[i]]++;
                continue;
            }

            int sqrt = (int) Math.sqrt(ar[i]);
            int e = ar[i];

            for (int j = 0; primes[j] <= sqrt; ) {
                if (!prime_check[e]) {
                    factors[e]++;
                    break;
                }
                while (e % primes[j] == 0) {
                    e /= primes[j];
                    factors[primes[j]]++;
                }
                j++;

                //                sqrt = (int)Math.sqrt(e);
            }
        }

        long res = 1;
        for (int i = 2; i <= max; i++) {
            res *= factors[i];

            if (res >= mod)
                res %= mod;
        }

        out.println(res);
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
