/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.nov;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Good Median
 * Link: https://www.codechef.com/NOV18A/problems/GMEDIAN
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class GoodMedian {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int MOD = 1000000007, LIMIT = 1000;
    private static final long[] FACTORIALS = new long[LIMIT + 1], INVERSE_FACTORIALS = new long[LIMIT + 1];
    private static final long[] TWO_POWERS = new long[LIMIT + 1];

    static {
        FACTORIALS[0] = FACTORIALS[1] = 1;
        for (int i = 2; i <= LIMIT; i++) FACTORIALS[i] = FACTORIALS[i - 1] * i % MOD;

        INVERSE_FACTORIALS[1] = 1;
        for (int i = 2; i <= LIMIT; i++) INVERSE_FACTORIALS[i] = inverse(FACTORIALS[i]);

        TWO_POWERS[0] = 1;
        for (int i = 1; i <= LIMIT; i++) {
            TWO_POWERS[i] = (TWO_POWERS[i - 1] << 1);
            if (TWO_POWERS[i] >= MOD) TWO_POWERS[i] -= MOD;
        }
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            out.println(process(in.readIntArray(n)));
        }
    }

    private static long process(int[] ar) {
        Arrays.sort(ar);
        if (countUnique(ar) == ar.length)
            return (MOD + TWO_POWERS[ar.length - 1]) % MOD;

        Element[] elements = toElements(ar);
        long res = TWO_POWERS[ar.length - 1]; // all odd length sequence are good.
        for (Element e : elements) {
            if (e.count == 1) continue;
            int n1 = e.smallerCount, n2 = e.largerCount, k = e.count, sn = n1 + n2;
            // with this element as middle two elements and equal number of different elements before and after it.
            res += ncr(sn, n1) * (TWO_POWERS[k - 1] + MOD - 1) % MOD;
            long[] ncrSumBackward = getNcrSumBackward(k, 2);
            int d = 1, r = n1 + 1, r1 = 3;
            while (r <= sn && r1 <= k) {
                res += (ncr(sn, r) * ncrSumBackward[r1] % MOD);
                r1++;
                r++;
            }

            r = n2 + 1;
            r1 = 3;
            while (r <= sn && r1 <= k) {
                res += (ncr(sn, r) * ncrSumBackward[r1] % MOD);
                r1++;
                r++;
            }

            res %= MOD;
        }

        return res < 0 ? res + MOD : res;
    }

    private static long[] getNcrSumBackward(int n, int diff) {
        long[] ar = new long[n + 1];
        for (int i = 0; i <= n; i++) ar[i] = ncr(n, i);
        for (int i = n - diff; i >= 0; i--) {
            ar[i] += ar[i + diff];
            if (ar[i] >= MOD) ar[i] -= MOD;
        }

        return ar;
    }

    private static int countUnique(int[] ar) {
        int count = 1;
        for (int i = 1; i < ar.length; i++) if (ar[i] != ar[i - 1]) count++;
        return count;
    }

    private static long ncr(int n, int r) {
        if (r == 0 || r == n) return 1;
        if (r == 1 || r == n - 1) return n;
        return FACTORIALS[n] * (INVERSE_FACTORIALS[r] * INVERSE_FACTORIALS[n - r] % MOD) % MOD;
    }

    /**
     * calculates a raised to power b and remainder to mod
     *
     * @param a
     * @param b
     * @return
     */
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

    private static long inverse(long a) {
        return pow(a, MOD - 2);
    }

    private static Element[] toElements(int[] ar) {
        LinkedList<Element> list = new LinkedList<>();
        int n = ar.length;
        list.add(new Element(ar[0]));
        for (int i = 1; i < n; i++) {
            if (ar[i] == ar[i - 1])
                list.getLast().count++;
            else
                list.add(new Element(ar[i]));
        }

        Element[] elements = list.toArray(new Element[list.size()]);
        setSmallerElementCount(elements);
        setLargerElementCount(elements);

        return elements;
    }

    private static void setSmallerElementCount(Element[] elements) {
        int n = elements.length;
        for (int i = 1; i < n; i++) elements[i].smallerCount = elements[i - 1].count + elements[i - 1].smallerCount;
    }

    private static void setLargerElementCount(Element[] elements) {
        int n = elements.length;
        for (int i = n - 2; i >= 0; i--) elements[i].largerCount = elements[i + 1].count + elements[i + 1].largerCount;
    }

    final static class Element {
        final int value;
        int count = 1, smallerCount, largerCount;

        Element(int v) {
            value = v;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}