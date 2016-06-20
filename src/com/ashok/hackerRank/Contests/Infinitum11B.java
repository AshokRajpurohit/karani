package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problme | Prime factorization 2
 * https://www.hackerrank.com/contests/infinitum11/challenges/prime-factorization-2
 */

public class Infinitum11B {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime;
    static {
        boolean[] ar = new boolean[1410];
        for (int i = 2; i < 1410; i++)
            ar[i] = true;
        int r = 2;
        while (r < 1410) {
            while (!ar[r])
                r++;

            for (int i = r + 1; i < 1410; i++) {
                if (i % r == 0)
                    ar[i] = false;
            }
            r++;
        }
        int count = 0;
        for (int i = 2; i < 1410; i++) {
            if (ar[i])
                count++;
        }
        prime = new int[count];
        int i = 0, j = 2;
        for (; j < 1410; j++) {
            if (ar[j]) {
                prime[i] = j;
                i++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Infinitum11B a = new Infinitum11B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        long res = 0;

        while (t > 0) {
            t--;
            int n = in.readInt();
            res = res + solve(n);
        }
        out.println(res);
    }

    private static int solve(int n) {
        int res = 0;
        int m = n;
        for (int i = 0; i < prime.length && prime[i] <= n; i++) {
            while (m % prime[i] == 0) {
                res = res + prime[i];
                m = m / prime[i];
            }
        }
        if (m != 1)
            res = res + m;
        return res;
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
