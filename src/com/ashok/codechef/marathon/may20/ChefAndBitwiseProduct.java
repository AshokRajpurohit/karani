/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.may20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Bitwise Product
 * Link: https://www.codechef.com/MAY20A/problems/CHANDF
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndBitwiseProduct {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

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
            long X = in.readLong(), Y = in.readLong(), L = in.readLong(), R = in.readLong();
            sb.append(process(X, Y, L, R)).append('\n');
        }

        out.print(sb);
    }

    private static long process(long x, long y, long a, long b) {
        if (x > y) return process(y, x, a, b);
        if (a == b || x == 0 || y == 0) return a;
        long z = x | y; // z as of now is the number which gives maximum result possible.
        if (z >= a && z <= b) return z;
        z = triFunc(x, y, a, b);
        return Math.max(z, a);
    }

    private static long triFunc(long x, long y, long a, long b) {
        long z = x | y;
        long r = Long.highestOneBit(b);
        if (Long.lowestOneBit(z) > r) return a;
        long ref = z;

        r <<= 1;
        z = 0;
        while (r > 0) {
            r >>= 1;
            boolean ha = (a & r) != 0, hb = (b & r) != 0, hz = (ref & r) != 0;
            if (!hb) continue;
            if (ha) z |= r;
            else {
                long setZ = z | r, unsetZ = z;
                r >>= 1;
                long za = aFunc(x, y, unsetZ, a, r, ref);
                long zb = bFunc(x, y, setZ, b, r, ref);
                long vza = chefFunction(za, x, y), vzb = chefFunction(zb, x, y);
                if (vza > vzb) return za;
                if (vza < vzb) return zb;
                return Math.min(za, zb);
            }
        }

        return z;
    }

    private static long aFunc(long x, long y, long z, long a, long r, long ref) {
        while (r > 0) {
            boolean hz = (ref & r) != 0, ha = (a & r) != 0;
            if (ha) {
                z |= r;
            } else {
                long dz = z | r | (ref & (r - 1)); // set bit solution, now dz is one of possible solution.
                long rz = aFunc(x, y, z, a, r >> 1, ref); // let's find another solution with set/unset bit.
                long vdz = chefFunction(dz, x, y), vrz = chefFunction(rz, x, y);
                // let's compare chef_function results and decide which z to return.
                if (vdz > vrz) return dz;
                if (vdz < vrz) return rz;
                return Math.min(rz, dz);
            }

            r >>= 1;
        }

        return z;
    }

    private static long bFunc(long x, long y, long z, long b, long r, long ref) {
        r <<= 1;
        while (r > 0) {
            r >>= 1;
            boolean hz = (ref & r) != 0, hb = (b & r) != 0;
            if (!hb) {
                continue;
            }

            if (hb && !hz) {
                return z | (ref & (r - 1));
            }
            long dz = z | (ref & (r - 1)); // unset bit solution, now dz is one of possible solution
            long rz = bFunc(x, y, z | r, b, r >> 1, ref); // let's find another solution with set/unset bit.
            long vdz = chefFunction(dz, x, y), vrz = chefFunction(rz, x, y);
            // let's compare chef_function results and decide which z to return.
            if (vdz > vrz) return dz;
            if (vdz < vrz) return rz;
            return Math.min(rz, dz);
        }

        return z;
    }

    private static long chefFunction(long z, long x, long y) {
        return (z & x) * (z & y);
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