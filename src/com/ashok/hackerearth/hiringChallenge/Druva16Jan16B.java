package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: K - Arrays
 * Challenge: Druva Developer Hiring Challenge
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class Druva16Jan16B {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static long[] inv = new long[17];

    static {
        inv[0] = 0;
        inv[1] = 1;

        for (int i = 2; i < 17; i++)
            inv[i] = pow(i, mod - 2);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Druva16Jan16B a = new Druva16Jan16B();
        a.solve();
        out.close();
    }

    private static void process(long[] ar, int n, int k) {
        Arrays.sort(ar);
        long maxP = ar[n - 1], minP = ar[0];
        long maxLen = 1, minLen = 1;
        long min = ar[0];

        for (int i = 1; i < n; i++) {
            min = (min * i * inv[i + 1] % mod) * ar[i] % mod;
            if (min <= maxP) {
                minP = min;
                minLen = i + 1;
            }
        }


        for (int i = 0, j = n - 1; i < j; i++, j--) {
            long temp = ar[i];
            ar[i] = ar[j];
            ar[j] = temp;
        }

        long max = ar[0];
        for (int i = 1; i < n; i++) {
            max = (max * i * inv[i + 1] % mod) * ar[i] % mod;
            if (max > maxP) {
                maxP = max;
                maxLen = i + 1;
            }
        }

        out.println((maxP ^ minP) + " " + (maxLen ^ minLen));
    }

    public static long pow(long a, long b) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return res;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();

        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt(), k = in.readInt();
            long[] sum = new long[n];

            for (int i = 0; i < k; i++)
                for (int j = 0; j < n; j++)
                    sum[j] += in.readInt();

            for (int i = 0; i < n; i++)
                sum[i] %= mod;

            process(sum, n, k);
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
