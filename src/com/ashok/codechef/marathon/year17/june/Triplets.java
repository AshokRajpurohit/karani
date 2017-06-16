/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.june;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Triplets
 * Link: https://www.codechef.com/JUNE17/problems/SUMQ
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class Triplets {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private final static int MODULO = 1000000007;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int p = in.readInt(), q = in.readInt(), r = in.readInt();
            out.println(process(in.readIntArray(p), in.readIntArray(q), in.readIntArray(r)));
        }
    }

    private static long process(int[] a, int[] b, int[] c) {
        long res = 0;
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);

        long[] sumZ = getSumArray(c);
        long[] sigmaYZ = new long[b.length], // for each y, stores sum of yz, that is y * Sigma(z).
                sigmaY2 = new long[b.length], // for each y, stores sum of y^2 in y(y+z).
                sigmaYplusZ = new long[b.length]; // for each y, stores sum of y + z, that is k * y + Sigma(z).

        int xi = 0, yi = 0, zi = 0;
        while (yi < b.length && b[yi] < c[zi]) {
            yi++;
        }

        while (yi < b.length) {
            long y = b[yi];

            while (zi < c.length && c[zi] <= y) zi++;

            zi--;
            sigmaY2[yi] = (zi + 1) * (y * y % MODULO); // we can afford it to go beyond as of now.
            sigmaYZ[yi] = y * sumZ[zi] % MODULO;
            sigmaYplusZ[yi] = ((zi + 1) * y + sumZ[zi]) % MODULO;

            yi++;
        }

        long[] sumY2 = getSumArrayReverse(sigmaY2),
                sumYplusZ = getSumArrayReverse(sigmaYplusZ),
                sumYZ = getSumArrayReverse(sigmaYZ);

        long[] sumY2plusYZ = plus(sumY2, sumYZ);

        yi = 0;
        while (yi < b.length && b[yi] < a[xi]) {
            yi++;
        }

        while (xi < a.length) {
            int x = a[xi];

            while (yi < b.length && b[yi] < x) yi++;

            if (yi == b.length)
                break;

            res += (x * sumYplusZ[yi] % MODULO + sumY2plusYZ[yi]);
            xi++;
        }

        return res % MODULO;
    }

    private static long[] getSumArray(int[] ar) {
        long[] res = new long[ar.length];
        res[0] = ar[0];

        for (int i = 1; i < ar.length; i++)
            res[i] = (res[i - 1] + ar[i]) % MODULO;

        return res;
    }

    private static long[] getSumArrayReverse(long[] ar) {
        long[] res = new long[ar.length];
        int index = ar.length - 1;

        res[index] = ar[index];
        index--;

        while (index >= 0) {
            res[index] = (res[index + 1] + ar[index]) % MODULO;
            index--;
        }

        return res;
    }

    private static long[] plus(long[] a, long[] b) {
        long[] res = new long[a.length];

        for (int i = 0; i < a.length; i++)
            res[i] = a[i] + b[i];

        return res;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}