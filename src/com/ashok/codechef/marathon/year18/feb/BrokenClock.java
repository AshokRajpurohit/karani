/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Broken Clock
 * Link: https://www.codechef.com/FEB18/problems/BROCLK
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BrokenClock {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int MOD = 1000000007;
    private static final long TWO_INVERSE = inverse(2), ITER_LIMIT = 67;

    public static void main(String[] args) throws IOException {
        play();
        solve();
        in.close();
        out.close();
    }

    private static void play() throws IOException {
        while (true) {
            final int L = in.readInt(), d = in.readInt(), t = in.readInt();
            for (int i = 2; i <= t; i++) {
                long res = iteratively(L, d, i);
                long mres = matrixWay(L, d, i);
                out.println("Actual: " + mres + ", Expected: " + res);
            }

            out.flush();
        }
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            long res = process(in.readLong(), in.readLong(), in.readLong());
            sb.append(res).append('\n');
        }

        out.print(sb);

    }

    private static long process(long L, long d, long t) {
        if (t < ITER_LIMIT)
            return iteratively(L, d, t);

        if (powerOfTwo(t))
            return recursively(L, d, t);

        return matrixWay(L, d, t);
    }

    private static boolean powerOfTwo(long n) {
        return (n & (n - 1)) == 0;
    }

    private static long iteratively(long L, long d, long n) {
        long cosTheta = inverse(L) * d % MOD;
        if (n == 1)
            return cosTheta * L % MOD;

        long a = cosTheta, b = 1;
        while (n > 1) {
            n--;
            long t = 2 * a * cosTheta - b;
            b = a;
            a = t % MOD;
        }

        return a * L % MOD;
    }

    private static long matrixWay(long L, long d, long n) {
        long cos_theta = inverse(L) * d % MOD;
        long[][] baseMatrix = getBaseMatrix(cos_theta);
        long result[][] = pow(baseMatrix, n - 1);
        long cost_t_theta = cos_theta * result[0][0] % MOD + result[1][0];
        long res = cost_t_theta * L % MOD;
        return res >= 0 ? res : MOD + res;
    }

    private static long[][] getBaseMatrix(long costTheta) {
        long[][] matrix = new long[2][];
        matrix[0] = new long[]{2 * costTheta % MOD, 1};
        matrix[1] = new long[]{MOD - 1, 0};
        return matrix;
    }

    private static long recursively(long L, long d, long n) {
        long cos_theta = inverse(L) * d % MOD;
        return cos(n, cos_theta) * L % MOD;
    }

    private static long cos(long n, long base) {
        if (n == 1)
            return base;

        if (odd(n))
            return oddCos(n, base);

        long res = cos(n >>> 1, base);
        return addCos(res);
    }

    private static long oddCos(long odd, long base) {
        if (odd == 1)
            return base;

        long evenp = odd >>> 1, oddp = odd - evenp;
        long sum = 2 * cos(oddp, base) * cos(evenp, base) - base;
        return sum % MOD;
    }

    private static boolean odd(long n) {
        return (n & 1) == 1;
    }

    private static long addCos(long a) {
        return (2 * a * a - 1) % MOD;
    }

    private static long inverse(long n) {
        return pow(n, MOD - 2);
    }

    private static long pow(long a, long b) {
        if (b == 0)
            return 1;

        a = a % MOD;
        if (a < 0)
            a += MOD;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % MOD;
            if ((b & r) != 0) {
                res = (res * a) % MOD;
            }
        }

        if (res < 0) res += MOD;
        return res;
    }

    private static long[][] pow(long[][] matrix, long p) {
        if (p == 1)
            return matrix;

        long[][] temp = new long[2][2], result = new long[2][], t1;
        result[0] = matrix[0].clone();
        result[1] = matrix[1].clone();
        long r = Long.highestOneBit(p);
        while (r > 1) {
            r = r >>> 1;
            square(result, temp);
            t1 = result;
            result = temp;
            temp = t1;

            if ((r & p) != 0) {
                multiply(result, matrix, temp);
                t1 = result;
                result = temp;
                temp = t1;
            }
        }

        return result;
    }

    private static void multiply(long[][] a, long[][] b, long[][] res) {
        res[0][0] = (a[0][0] * b[0][0] + a[0][1] * b[1][0]) % MOD;
        res[0][1] = (a[0][0] * b[0][1] + a[0][1] * b[1][1]) % MOD;
        res[1][0] = (a[1][0] * b[0][0] + a[1][1] * b[1][0]) % MOD;
        res[1][1] = (a[1][0] * b[0][1] + a[1][1] * b[1][1]) % MOD;
    }

    private static void square(long[][] matrix, long[][] res) {
        multiply(matrix, matrix, res);
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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