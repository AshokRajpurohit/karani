package com.ashok.codechef.marathon.year16.JAN16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Churu and Balls
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CBALLS {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime = gen_prime(5000);
    private static int[] hash = new int[5000];

    private static int[] gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int)Math.sqrt(n);

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
        return ret;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CBALLS a = new CBALLS();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            out.println(process(in.readIntArray(n), n));
        }
    }

    private static int process(int[] list, int n) {
        if (n == 1) {
            if (list[0] == 1)
                return 1;

            return 0;
        }

        int max = 0;
        for (int e : list)
            max = Math.max(max, e);

        if (max == 1)
            return n;

        if (max == list[0]) {
            int res = 0;
            for (int e : list)
                res += max - e;

            return res;
        }

        for (int e : prime) {
            int bar = e;
            for (int f : list) {
                if (f <= bar)
                    hash[e] += bar - f;
                else {
                    bar = getBar(e, f);
                    hash[e] += bar - f;
                }
            }
        }

        int min = hash[2];
        for (int e : prime) {
            min = Math.min(min, hash[e]);
            hash[e] = 0;
        }

        int res = 0;
        for (int e : list)
            res += max - e;

        return Math.min(min, res);
    }

    private static boolean check(int[] ar) {
        for (int i = 1; i < ar.length; i++)
            if (ar[i] < ar[i - 1])
                return false;

        return gcd(ar) != 1;
    }

    private static int gcd(int[] ar) {
        int res = ar[0];

        for (int i = 1; i < ar.length; i++)
            res = gcd(res, ar[i]);

        return res;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
    }

    private static int getBar(int a, int b) {
        if (b % a == 0)
            return b;

        return b + a - b % a;
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
