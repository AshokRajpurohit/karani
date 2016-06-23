package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Problem: Infinite Power
 * Challenge: Mojo Networks Hiring Challenge
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class MojoInfinitePower {

    private static PrintWriter out;
    private static InputStream in;
    private static String unlimitedPower = "Unlimited Power\n";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MojoInfinitePower a = new MojoInfinitePower();
        a.solve();
        out.close();
    }

    /**
     * To test the speed of algorithm
     *
     * @param test
     */
    private static void test(int test) {
        int[] ar = gen_rand(test, 1, 1000000000);
        long t = System.currentTimeMillis();

        for (int i = 0; i < test; i++) {
            next(ar[i]);
            prev(ar[i]);
        }

        System.out.println(System.currentTimeMillis() - t);
    }

    private static boolean isInfinite(int n) {
        while (n > 0) {
            int digit = n % 10;

            if ((digit & 1) != 0)
                return false;

            n /= 10;
        }

        return true;
    }

    private static long lessOrEqual(int n) {
        if (n < 10) {
            if ((n & 1) == 0)
                return n;

            return n - 1;
        }

        int digit = n % 10;
        n /= 10;
        long prev = lessOrEqual(n);

        if (n > prev) {
            return prev * 10 + 8;
        }

        return prev * 10 + lessOrEqual(digit);
    }

    private static long prev(int n) {
        if (n < 10) {
            if ((n & 1) == 0)
                return n - 2;

            return n - 1;
        }

        int digit = n % 10;
        n /= 10;
        long prev = 0;

        if (digit == 0)
            prev = prev(n);
        else
            prev = lessOrEqual(n);

        if (n > prev) {
            return prev * 10 + 8;
        }

        return prev * 10 + prev(digit);
    }

    private static long next(int n) {
        if (n < 10) {
            if ((n & 1) == 0)
                return n + 2;

            return n + 1;
        }

        int digit = n % 10;
        n /= 10;

        long prev = 0;
        if (digit == 8)
            prev = next(n);
        else
            prev = greaterOrEqual(n);

        if (n < prev) {
            return prev * 10;
        }

        return prev * 10 + next(digit);
    }

    private static long greaterOrEqual(int n) {
        if (n < 10) {
            if ((n & 1) == 0)
                return n;

            return n + 1;
        }

        int digit = n % 10;
        n /= 10;
        long prev = greaterOrEqual(n);

        if (n < prev) {
            return prev * 10;
        }

        return prev * 10 + greaterOrEqual(digit);
    }

    private static int[] gen_rand(int size, int start, int end) {
        int mod = end + 1 - start;
        int[] res = gen_rand(size, mod);

        for (int i = 0; i < size; i++)
            res[i] += start;

        return res;
    }

    private static int[] gen_rand(int size, int mod) {
        Random random = new Random();
        int[] ar = new int[size];
        for (int i = 0; i < size; i++)
            ar[i] = random.nextInt(mod);

        return ar;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        while (t > 0) {
            t--;
            test(in.readInt());
            //            int n = in.readInt();
            //            out.println(next(n) + ", " + prev(n) + ", " + isInfinite(n));
            //            out.flush();
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
