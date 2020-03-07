/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.dec19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Binary XOR
 * Link: https://www.codechef.com/DEC19A/problems/BINXOR
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class BinaryXOR {
    private static final int MOD = 1000000000 + 7, LIMIT = 1000000;
    private static final long[] factorials = new long[LIMIT];
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    static {
        factorials[1] = factorials[0] = 1;
        for (int i = 2; i < LIMIT; i++) {
            factorials[i] = factorials[i - 1] * i % MOD;
        }
    }

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
            int n = in.readInt();
            long result = process(in.read(n), in.read(n));
            sb.append(result).append('\n');
        }

        out.print(sb);
    }

    private static long process(String a, String b) {
        int n = a.length();
        if (n == 1) return 1;
        char[] binA = a.toCharArray(), binB = b.toCharArray();
        int countA = count(binA, '1'), countB = count(binB, '1');
        int min = Math.abs(countA - countB), max = countA + countB;
        if (max > n) max = (n << 1) - max;
        long result = 0;
        for (int i = min; i <= max; i += 2) {
            result += ncr(n, i);
        }
        return result % MOD;
    }

    private static int count(char[] chars, char ch) {
        int count = 0;
        for (char c : chars) if (c == ch) count++;
        return count;
    }

    private static long ncr(int n, int r) {
        if (r == 0 || r == n) return 1;
        return factorials[n] * (inverse(factorials[r] * factorials[n - r] % MOD)) % MOD;
    }

    private static long inverse(long n) {
        return simplePower(n, MOD - 2);
    }

    public static long pow(long a, long b) {
        if (b == 0) return 1;

        a = a % MOD;
        if (a < 0) a += MOD;

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

    private static long simplePower(long a, long b) {
        if (b == 1) return a;
        if (b == 0) return 1;
        if (a == 0 || a == 1) return a;
        return simplePower0(a, b);
    }

    private static long simplePower0(long a, long b) {
        if (b == 1) return a;
        long result = simplePower0(a, b >>> 1);
        result = result * result % MOD;
        return isEven(b) ? result : result * a % MOD;
    }

    private static boolean isEven(long n) {
        return (n & 1) == 0;
    }

    final static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                        buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}