package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113
 * problem Link: D E Shaw Hiring Challenge | Primatic Numbers II
 */

public class DEShawPrimatic {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] ar;
    private static int[] prime = gen_prime(1000000);

    static {
        long[] prime_square = new long[5];
        for (int i = 0; i < 5; i++)
            prime_square[i] = (long) Math.pow(prime[i], prime[i]);

        ar = new long[prime.length + 6];
        ar[0] = 1;
        int i = 1, j = 0, k = 0;
        for (i = 1; i < ar.length - 1; i++) {
            if (prime[j] > prime_square[k]) {
                ar[i] = prime_square[k];
                k++;
            } else {
                ar[i] = prime[j];
                j++;
            }
        }

        ar[ar.length - 1] = prime_square[4];
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
        DEShawPrimatic a = new DEShawPrimatic();
        a.solve();
        out.close();
    }

    private static long solve(long n) {
        if (n == 0)
            return 0;
        if (n <= 5)
            return 1;

        if (n > 1000000 && n != ar[ar.length - 1])
            if (check_primality(n))
                return 1;

        if (n >= ar[ar.length - 1])
            return n / ar[ar.length - 1] + solve(n % ar[ar.length - 1]);

        int prima = find(n);
        long count = n / ar[prima];
        return count + solve(n % ar[prima], prima);
    }

    private static long solve(long n, int i) {
        if (n == 0)
            return 0;

        int prima = find(n, i);
        return n / ar[prima] + solve(n % ar[prima], prima);
    }

    private static int find(long n) {
        return find(n, ar.length - 1);
    }

    private static int find(long n, int prev_index) {
        int i = 0, j = prev_index;
        while (i < j - 1) {
            int mid = (i + j) >> 1;
            if (ar[mid] > n)
                j = mid;
            else
                i = mid;
        }
        return i;
    }

    private static boolean check_primality(long n) {
        int root = (int) Math.sqrt(n);

        for (int i = 0; i < prime.length && prime[i] <= root; i++)
            if (n % prime[i] == 0)
                return false;

        return true;
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
