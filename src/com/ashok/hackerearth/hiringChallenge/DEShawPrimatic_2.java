package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113
 * problem Link: D E Shaw Hiring Challenge | Primatic Numbers II
 */

public class DEShawPrimatic_2 {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime = gen_prime(1000000);
    private static long[] prime_square;

    static {
        prime_square = new long[5];
        prime_square[0] = 4;
        prime_square[1] = 27;
        prime_square[2] = (long) Math.pow(5, 5);
        prime_square[3] = (long) Math.pow(7, 7);
        prime_square[4] = (long) Math.pow(11, 11);
    }

    private static int[] gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i + 1; j <= n; j++) {
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
        DEShawPrimatic_2 a = new DEShawPrimatic_2();
        a.solve();
        out.close();
    }

    private static long solve(long n) {
        if (n <= 5)
            return 1;

        if (check_sq(n))
            return 1;

        if (check_primality(n))
            return 1;

        return 2;
    }

    private static boolean check_sq(long n) {
        for (int i = 0; i < prime_square.length; i++)
            if (n == prime_square[i])
                return true;

        return false;
    }

    private static boolean find(long n) {
        if (n == prime[prime.length - 1])
            return true;

        return find(n, prime.length - 1);
    }

    private static boolean find(long n, int prev_index) {
        int i = 0, j = prev_index;
        while (i < j - 1) {
            int mid = (i + j) >> 1;
            if (prime[mid] > n)
                j = mid;
            else
                i = mid;
        }
        return n == prime[i];
    }

    private static boolean check_primality(long n) {
        if (n <= prime[prime.length - 1])
            return find(n);

        return false;

        //        double droot = Math.sqrt(n);
        //        int root = (int)droot;
        //
        //        if (root == droot)
        //            return false;
        //
        //        if (root <= prime[prime.length - 1]) {
        //            for (int i = 0; prime[i] <= root; i++)
        //                if (n % prime[i] == 0)
        //                    return false;
        //            return true;
        //        }
        //
        //        for (int i = 0; i < prime.length; i++)
        //            if (n % prime[i] == 0)
        //                return false;
        //
        //        for (int i = prime[prime.length - 1] + 2; i <= root; i += 2)
        //            if (n % i == 0)
        //                return false;
        //
        //        return true;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(solve(in.readLong()));
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
