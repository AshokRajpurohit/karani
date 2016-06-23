package com.ashok.hackerearth.CodeMonk;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: The Confused Monk
 * https://www.hackerearth.com/code-monk-number-theory-i/algorithm/the-confused-monk/
 */

public class NT1C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        NT1C a = new NT1C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int mod = 1000000007;
        int n = in.readInt();
        long fx = 1;
        int gx = 0;
        for (int i = 0; i < n; i++) {
            int temp = in.readInt();
            gx = gcd(gx, temp);
            fx = fx * temp % mod;
        }
        out.println(pow(fx, gx, mod));
        //        int[] ar = in.readIntArray(n);
        //        out.println(pow(fx(ar), gx(ar), mod));
    }

    private static int fx(int[] ar) {
        int res = 1;
        int mod = 1000000007;
        for (int i = 0; i < ar.length; i++)
            res = (res * ar[i]) % mod;
        return res;
    }

    private static int gx(int[] ar) {
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

    public static long pow(long a, long b, long mod) {
        if (b == 0)
            return 1;

        a = a % mod;

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
