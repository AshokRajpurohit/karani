package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Problem: Lucky Numbers
 * <p>
 * Golu wants to find out the sum of Lucky numbers.
 * Lucky numbers are those numbers which contain exactly two set bits.
 * This task is very diffcult for him.So Help Golu to find sum of those numbers
 * which exactly contain two set bits upto a given number N.
 * 3 5 10 are lucky numbers where 7 14 are not.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CraftsvillaA {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] bits, sum, bsum;
    private static int mod = 1000000007;

    static {
        bits = new long[63];
        sum = new long[63];
        bsum = new long[63];

        bits[0] = 1;
        long v = 1;
        for (int i = 1; i < 63; i++)
            bits[i] = (v << i) % mod;

        sum[0] = bits[0];
        for (int i = 1; i < 63; i++)
            sum[i] = (sum[i - 1] + bits[i]) % mod;

        bsum[0] = 0;
        for (int i = 1; i < 63; i++)
            bsum[i] = (bsum[i - 1] + bits[i] * i + sum[i - 1]) % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CraftsvillaA a = new CraftsvillaA();
        a.solve();
        out.close();
    }

    private static long process(long n) {
        if (n < 3)
            return 0;

        int index = getIndex(n);
        if (index == 62) {
            return bsum[62];
        }
        long res = 0;
        if (n < bits[index])
            throw new RuntimeException("le le ab");

        int j = getIndex(n - bits[index]);
        res = (bits[index] * (j + 1) + sum[j]) % mod;
        if (index > 0)
            res = (res + bsum[index - 1]) % mod;

        //        for (int i = index - 1; i > 0; i--) {
        //            res = (res + bits[i] * i + sum[i - 1]) % mod;
        //        }

        return res;
    }

    private static int getIndex(long n) {
        if (n == 1)
            return 0;

        int i = 0;
        while (n > 1) {
            i++;
            n = (n >>> 1);
        }

        return i;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        Random random = new Random();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            //            process(Math.abs(random.nextLong()));
            sb.append(process(Math.abs(random.nextLong()))).append('\n');
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
