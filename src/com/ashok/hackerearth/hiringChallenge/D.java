package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link: https://www.hackerearth.com/druva-hiring-challenge-2015/problems/36eb61fb04e2ab06a29c1e9662c200d9/
 * Submission Link:
 */

public class D {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        D a = new D();
        a.solve();
        out.close();
    }

    private static void solve(long n) {
        if (n == 1) {
            out.print("0 1");
            return;
        }

        if (n == 2) {
            out.println("1 2");
            return;
        }

        if (n == 3) {
            out.println("3 2");
            return;
        }

        long t = n - 1;
        long unit = 1;
        int count_bit = 0;
        while (t != 0) {
            t = t >> 1;
            count_bit++;
        }

        long max_val = unit << count_bit;
        max_val--;

        int top_bit = 0;
        long rev_t = 0;
        t = n - 1;

        while (t != 0) {
            rev_t = rev_t << 1;
            rev_t = rev_t | (1 & t);
            t = t >> 1;
        }

        if ((rev_t & (rev_t + 1)) == 0) {
            top_bit = count_bit;
        } else {
            while ((rev_t & 1) == 1) {
                top_bit++;
                rev_t = rev_t >> 1;
            }

            while ((rev_t & 1) == 0 && rev_t != 0) {
                top_bit++;
                rev_t = rev_t >> 1;
            }
        }

        long max_top = unit << top_bit;
        max_top--;

        t = n - 1;
        t = t >> (count_bit - top_bit);

        long max_top_val = unit << top_bit;
        max_top_val--;
        long diff = max_top_val - t;
        diff = diff << 1;
        long count = (unit << top_bit) - diff;
        count = count << (count_bit - top_bit);
        //
        out.println(max_val + " " + count);
        //        out.println(count_bit + "," + top_bit);
        return;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            long n = in.readLong();
            solve(n);
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
