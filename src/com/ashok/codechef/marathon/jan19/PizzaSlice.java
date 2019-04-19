/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.jan19;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: A Pizza Slice
 * Link: https://www.codechef.com/JAN19A/problems/XYPIZQ
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class PizzaSlice {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static Query[] queries = Query.values();

    public static void main(String[] args) throws IOException {
        out.println(Integer.MAX_VALUE);
        out.flush();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            int n = in.readInt(), ty = in.readInt(), x = in.readInt(), y = in.readInt(), z = in.readInt();
            validate(n, x, y, z);
            sb.append(queries[ty - 1].get(n, x, y, z)).append('\n');
        }
        out.print(sb);
    }

    private static void validate(int n, int x, int y, int z) {
        if (x == y || y == z || n < x || n < y || n < z) throw new RuntimeException("le kutte");
    }

    private static boolean isEven(long n) {
        return (n & 1) == 0;
    }

    final static class Fraction {
        final long numerator, denominator;

        Fraction(long n, long d) {
//            if (n < 0) throw new RuntimeException("invalid fraction");
//            if (d < 0) throw new RuntimeException("invalid fraction");
//            if (n > d) throw new RuntimeException("invalid fraction");
            long gcd = gcd(n, d);
            numerator = n / gcd;
            denominator = d / gcd;
        }

        public String toString() {
            return numerator + " " + denominator;
        }
    }

    private static long gcd(long a, long b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    @FunctionalInterface
    interface QueryProcessor {
        Fraction get(long n, int x, int y, int z);
    }

    enum Query implements QueryProcessor {
        FIRST { // AxAyBz

            @Override
            public Fraction get(long n, int x, int y, int z) {
                long deno = (n << 1) | 1;
                long num = z > y ? z : deno + 1 - y;
                num = deno - num;
                return new Fraction(y > x ? num : deno + num, deno);
            }
        },
        SECOND { // AxByAz

            @Override
            public Fraction get(long n, int x, int y, int z) {
                long deno = (n << 1) | 1;
                long num = deno - (y << 1);
                return new Fraction(z > x ? (deno << 1) - num : num, deno);
            }
        },
        THIRD { //AxByBz

            @Override
            public Fraction get(long n, int x, int y, int z) {
                return FIRST.get(n, z, y, x);
                /*long deno = (n << 1) + 1;
                long num = x > y ? x : deno + 1 - y;
                return new Fraction(y > z ? deno - num : num, deno);*/
            }
        },
        FOURTH { // BxAyBz

            @Override
            public Fraction get(long n, int x, int y, int z) {
                return SECOND.get(n, z, y, x);
            }
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