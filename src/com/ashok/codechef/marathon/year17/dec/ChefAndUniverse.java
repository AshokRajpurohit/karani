/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.dec;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Universe
 * Link: https://www.codechef.com/DEC17/problems/CHEFUNI
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndUniverse {
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
            int x = in.readInt(), y = in.readInt(), z = in.readInt(),
                    a = in.readInt(), b = in.readInt(), c = in.readInt();

            sb.append(process(x, y, z, a, b, c)).append('\n');
        }

        out.print(sb);
    }

    private static long process(int x, int y, int z, int a, int b, int c) {
        int na = 6 * a, nb = 3 * b, nc = 2 * c; // normalized value to make these comparable.
        int min = min(na, nb, nc);

        if (min == na) {
            return 1L * (x + y + z) * a;
        } else if (min == nb) {
            int count = count(x, y, z, 2), rem = x + y + z - count * 2;
            if (rem == 0)
                return 1L * count * b;

            long res = 1L * count * b + 1L * rem * a;
            int max = max(x, y, z), sum2 = x + y + z - max;
            if (a + b <= c || max >= sum2)
                return res;

            int count3 = count(x, y, z, 3);
            int kc = Math.min(Math.min(count, count3), rem);
            rem -= kc;
            int count2 = count(x - kc, y - kc, z - kc, 2);
            return 1L * (count - kc) * b + 1L * kc * c + 1L * rem * a;
        } else {
            int count3 = count(x, y, z, 3), rem = x + y + z - count3 * 3;
            long res = 1L * count3 * c;

            if (na <= nb)
                return res + rem * a;

            int count2 = count(x - count3, y - count3, z - count3, 2);
            rem -= count2 * 2;
            res += count2 * b + rem * a;

            int rem3 = Math.min(count3, rem); // some of 3 pairs, we need to revert so that we can use more 2
            count3 -= rem3; // actual count of 3 pairs or type 3 moves.
            count2 = count(x - count3, y - count3, z - count3, 2); // count of type 2 moves.
            int count1 = x + y + z - count3 * 3 - count2 * 2;

            return Math.min(res, count3 * c + count2 * b + count1 * a);
        }
    }

    private static int bruteForce(int x, int y, int z, int a, int b, int c) {
        int count3 = count(x, y, z, 3);
        int na = 6 * a, nb = 3 * b, nc = 2 * c; // normalized value to make these comparable.
        int min = (x + y + z) * a; // min value is not greater than this.
        int res = 0, dx = x, dy = y, dz = z;
        for (int i = 0; i <= count3; i++) {
            if (nb <= na) {
                int count2 = count(dx, dy, dz, 2);
                min = Math.min(min, res + count2 * b + (dx + dy + dz - count2 * 2) * a);
            } else {
                min = Math.min(min, res + (dx + dy + dz) * a);
            }

            res += c;
            dx--;
            dy--;
            dz--;
        }

        return min;
    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    private static int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    private static int count(int x, int y, int z, int n) {
        if (n == 1)
            return x + y + z;

        if (n == 3)
            return min(x, y, z);

        int max = max(x, y, z), sum2 = x + y + z - max;
        if (sum2 <= max)
            return sum2;

        return (x + y + z) >>> 1;
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