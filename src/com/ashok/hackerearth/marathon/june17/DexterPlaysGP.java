/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.marathon.june17;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem Name: Dexter plays with GP
 * Link: https://www.hackerearth.com/challenge/competitive/june-circuits-17/algorithm/dexter-plays-with-gp-1/
 * <p>
 * For complete implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * Not a complete solution.
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class DexterPlaysGP {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static BinaryNumberAppender binaryNumberAppender = new BinaryNumberAppender();
    private static Set<Long> set = new HashSet<>();
    private static final int LIMIT = 1000000;

    public static void main(String[] args) throws IOException {
//        test();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
//            t--;

            out.println(process(in.readInt(), in.readInt(), in.readInt()));
            out.flush();
        }
    }

    private static void test() throws IOException {
        while (true) {
            out.println(shankMethod(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    private static long process(int r, int s, int p) {
        if (p == 2)
            return 1;

        long rPowN = (1L * (r - 1) * s + 1) % p;
        if (rPowN == 0) // not possible.
            return -1;

        return calculate(r, rPowN, p);
    }

    private static long calculate(int r, long rpn, int p) {
        set = new HashSet<>(); // let GC handle the old object. not going to clear it.
        binaryNumberAppender.clear();
        long res = bruteForce(r, rpn, p);
        set = new HashSet<>();

        return res != 0 ? res : calculatePower(r, rpn, p);
    }

    private static long calculatePowerIteratively(int r, long rpn, int p) {
        long inverse = inverseModulo(r, p);
        while (!set.contains(rpn)) {
            set.add(rpn);
            if (rpn == r) {
                binaryNumberAppender.add(1);
                binaryNumberAppender.increment();
                return binaryNumberAppender.number;
            }

            if (binaryNumberAppender.number >= p || binaryNumberAppender.pow >= p)
                return -1;

            if (!square(r, rpn, p)) {
                binaryNumberAppender.add(1);
                binaryNumberAppender.increment();
                rpn = rpn * inverse % p;
                continue;
            }

            binaryNumberAppender.increment();
            rpn = getSquareRoot(rpn, p);
        }

        return -1;
    }

    private static long calculatePower(int r, long rpn, int p) {
        if (set.contains(rpn) || impossible(p))
            return -1;

        if (rpn == r) {
            binaryNumberAppender.add(1);
            binaryNumberAppender.increment();
            return binaryNumberAppender.number;
        }

        set.add(rpn);
        if (!square(r, rpn, p)) {
            binaryNumberAppender.add(1);
            binaryNumberAppender.increment();
            rpn = rpn * inverseModulo(r, p) % p;
        } else {
            binaryNumberAppender.increment();
            rpn = getSquareRoot(rpn, p);
        }

        set.remove(rpn);
        return calculatePower(r, rpn, p);
    }

    private static boolean impossible(long n) {
        return false;
    }

    private static long getSquareRoot(long n, int p) {
        int mod8 = p & 7;
        if (mod8 == 3 || mod8 == 7)
            return pow(n, (p + 1) >>> 2, p);

        if (mod8 == 5) {
            long v = pow(n << 1, (p - 5) >>> 3, p);
            long i = 2 * n * (v * v % p) % p;
            return (i - 1) * (n * v % p) % p;
        }

        return shankMethod(n, p);
    }

    private static long shankMethod(long n, int p) {
        int q = getQ(p), e = Integer.numberOfTrailingZeros((p - 1));
        long x = 2;
        long z;
        while (true) {
            z = pow(x, q, p);
            if (pow(z, 1 << (e - 1), p) != 1)
                break;

            x++;
        }

        long y = z, r = e;
        x = pow(n, (q - 1) >>> 1, p); // (q - 1) / 2 is equivalent to q / 2 as q is odd.
        long v = n * x % p, w = v * x % p;

        while (w != 1) {
            int k = findK(w, p);
            long d = pow(y, pow(2, r - k - 1, p - 1), p);
            y = d * d % p;
            r = k;
            v = d * v % p;
            w = w * y % p;
        }

        return v;
    }

    private static int findK(long w, long p) {
        int k = 1;
        long res = w * w % p;

        while (res != 1) {
            res = res * res % p;
            k++;
        }

        return k;
    }

    private static int getQ(int p) {
        p--;
        while ((p & 1) == 0)
            p >>>= 1;

        return p;
    }

    private static boolean square(int r, long rpn, int p) {
        return pow(rpn, (p - 1) >>> 1, p) == 1;
    }

    private static long bruteForce(int r, long rpn, int p) {
        if (pow(rpn, (p - 1) >>> 1, p) != 1)
            return -1;

        int n = 0;
        long res = 1;

        while (n <= LIMIT) {
            n++;
            res = res * r % p;
            if (res == rpn)
                return n;

            if (set.contains(res))
                return -1;

            set.add(res);
        }

        return 0; // 0 is never the answer.
    }

    private static boolean isSquare(long n) {
        long sqrt = (long) Math.sqrt(n);
        return sqrt * sqrt == n;
    }

    private static long inverseModulo(long n, long p) {
        return pow(n, p - 2, p);
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @param mod
     * @return
     */
    private static long pow(long a, long b, long mod) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
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

    final static class BinaryNumberAppender {
        long number = 0, pow = 1;
        int[] stack = new int[60];
        int index = 0;

        void add(int n) {
            stack[index] = n == 0 ? 0 : 1;
            number = n == 0 ? number : (number | pow);
        }

        void increment() {
            index++;
            pow <<= 1;
        }

        void remove() {
            index--;
            pow >>>= 1;
            number = stack[index] == 0 ? number : number ^ pow;
        }

        void clear() {
            number = 0;
            pow = 1;
            index = 0;
        }

        public String toString() {
            return "number: " + number + ", bitCount: " + index;
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