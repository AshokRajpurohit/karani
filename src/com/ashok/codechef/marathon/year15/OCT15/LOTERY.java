package com.ashok.codechef.marathon.year15.OCT15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Random;

/**
 * Problem: LCM equation
 * https://www.codechef.com/OCT15/problems/LOTERY
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class LOTERY {

    private static PrintWriter out;
    private static InputStream in;
    private static long mod = 1000000007;
    private static int N, K, A, B, M;
    private static int[] C, D;
    private static long Answer;
    private static int[] prime;
    private static long[] factPrime;

    private static void format() {
        // write code here.
        prime = gen_prime(M);
        factPrime = new long[M + 1];
        for (int i = 0; i <= M; i++)
            factPrime[i] = 1;

        for (int i = 0; i < prime.length; i++)
            factPrime[prime[i]] = prime[i];

        for (int i = 2; i <= M; i++)
            factPrime[i] = factPrime[i] * factPrime[i - 1] % mod;
    }

    private static int[] getPowArray(int p, int[] ar) {
        for (int i = 0; i <= M; i++)
            ar[i] = 1;

        int temp = p;
        while (temp <= M) {
            for (int i = temp; i <= M; i += temp)
                ar[i] *= p;

            temp *= p;
        }

        return ar;
    }

    public static int[] gen_prime(int n) {
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

    public static long pow(long a, long b) {
        if (a == 1 || a == 0)
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

    private static int[] gen_rand(int n) {
        Random random = new Random();
        int[] ar = new int[n];
        for (int i = 0; i < n; i++)
            ar[i] = random.nextInt(M);

        return ar;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LOTERY a = new LOTERY();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        N = in.readInt();
        K = in.readInt();
        A = in.readInt();
        B = in.readInt();
        M = in.readInt();

        C = in.readIntArray(t - 1);
        D = in.readIntArray(t - 1);
        //        C = gen_rand(t - 1);
        //        D = gen_rand(t - 1);

        format();

        process();
        sb.append(Answer).append('\n');

        for (int i = 0; i < t - 1; i++) {
            reset(i);
            process();
            sb.append(Answer).append('\n');
        }

        out.print(sb);
    }

    private static void process() {
        Answer = getLCM(N - K + 1, N);
    }

    private static long getLCM(int a, int b) {
        if (b < a + 3) {
            long res = a;
            for (int i = a + 1; i <= b; i++)
                res = res * i / gcd(res, i);

            return res % mod;
        }


        int root = (int)Math.sqrt(b);
        long res = 1;
        int i = 0;

        for (i = 0; prime[i] <= root; i++)
            res = res * getPow(prime[i], a, b) % mod;

        while (i < prime.length && prime[i] < a) {
            if (check(prime[i], a - 1, b))
                res = res * prime[i] % mod;
            i++;
        }

        if (i == prime.length)
            i--;

        return (res * factPrime[b] % mod) *
            pow(factPrime[prime[i] - 1], mod - 2) % mod;

        //        return res;
    }

    private static int getSq(int n, int a, int b) {
        int res = n * n;
        if (b >= res && a <= res)
            return res;

        if (check(n, a, b))
            return n;

        return 1;
    }

    private static int getCube(int n, int a, int b) {
        int res = n * n * n;
        if (check(res, a, b))
            return res;

        if (check(res / n, a, b))
            return n * n;

        if (check(n, a, b))
            return n;

        return 1;
    }

    private static boolean check(int n, int a, int b) {
        return b / n - a / n != 0;
    }

    private static int getIndex(int a) {
        if (a == 2)
            return 0;

        int start = 0, end = prime.length - 1;
        int mid = (start + end) >>> 1;

        while (start != mid) {
            if (prime[end] == a)
                return end;
            else if (prime[mid] >= a)
                end = mid;
            else
                start = mid;

            mid = (start + end) >>> 1;
        }

        return end;
    }

    private static int getPow(int n, int first, int last) {
        int res = 1, comp = 1;
        int b = last, a = first - 1;

        while (b > 0) {
            if (b > a)
                res = comp;

            comp *= n;
            b = b / n;
            a = a / n;
        }

        return res;
    }

    private static long LCM(long a, long b) {
        if (b == 1)
            return a;

        return (a * b / gcd(a, b)) % mod;
    }

    private static long LCM(int n, int k) {
        if (n == k + 1)
            return n * k;

        if (n == k)
            return n;

        int mid = (n + k) >>> 1;
        long a = LCM(n, mid);
        long b = LCM(mid, k);
        long g = gcd(a, b);

        a /= g;
        return b * a % mod;
    }

    private static long gcd(long a, long b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
    }

    private static void reset(int index) {
        N = 1 + (int)((Answer * A + C[index]) % M);
        K = 1 + (int)((Answer * B + D[index]) % N);
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
