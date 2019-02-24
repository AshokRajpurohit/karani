/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.feb19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Guess It Right
 * Link: https://www.codechef.com/FEB19A/problems/GUESSRT
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class GuessItRight {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            sb.append(calculate(in.readInt(), in.readInt(), in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static long calculate(int n, int k, int m) {
        if (m == 1) return inverse(n);
        long failQuanta = inverse(n) * (n - 1) % MOD;
        long res = pow(failQuanta, m >>> 1);
        return (MOD + 1 - res * ((m & 1) == 1 ? failQuanta : inverse(n + k) * (n + k - 1) % MOD) % MOD) % MOD;
    }

    private static long getFailQuanta(int n, int k) {
        return inverse((n - 1) * (n + k - 1)) * inverse(n * (n + k)) % MOD;
    }

    private static long inverse(long n) {
        return pow(n, MOD - 2);
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @return
     */
    private static long pow(long a, long b) {
        if (b == 0) return 1;
        a = a % MOD;
        if (a == 1 || b == 1) return a;

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

    final static class OperationSeries {
        final int n, k, size;
        final long quanta, extra;
        final long[] values;

        OperationSeries(int n, int k, int size) {
            this.n = n;
            this.k = k;
            this.size = size;
            values = new long[size - 1];
            initialize();
            quanta = values[size - 2];
            int buff = k * (size - 1);
            extra = (quanta * (n + buff) % MOD) * inverse(n + buff - 1) % MOD;
        }

        private void initialize() {
            long numerator = n - 1, denominator = n;
            values[0] = numerator * inverse(denominator) % MOD;
            for (int i = 1; i < size - 1; i++) {
                numerator += k;
                denominator += k;
                values[i] = (values[i - 1] * numerator % MOD) * inverse(denominator) % MOD;
            }
        }

        private long calculateValue(int m) {
            int count = m / size;
            long res = pow(quanta, count);
            int rem = m - size * count;
            if (rem == 0) return res * extra % MOD;
            return res * values[rem - 1] % MOD;
        }
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
    }
}