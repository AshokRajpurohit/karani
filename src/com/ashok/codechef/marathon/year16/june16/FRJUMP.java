package com.ashok.codechef.marathon.year16.june16;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.lang.reflect.Array;

import java.util.LinkedList;

/**
 * Problem: Chef and cities
 * https://www.codechef.com/JUNE16/problems/FRJUMP
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class FRJUMP {

    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] cities;
    private static long[] products;
    private static double[] logs, citiLogs;
    private static final int mod = 1000000007, update = 1, query = 2, highBit =
        Integer.highestOneBit(mod - 2);
    private static int limit;
    private static long multi = 1L, productOffset = 1L;
    private static double logSum = 0.00000000000000000, productLogOffset = 0.0;
    private static LinkedList<Integer>[] factors;

    public static void main(String[] args) throws IOException {
        FRJUMP a = new FRJUMP();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        int n = in.readInt();
        cities = in.readIntArray(n);

        if (n > 10)
            process(n);

        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 3);
        while (q > 0) {
            q--;

            if (in.readInt() == update) {
                if (n > 10)
                    update(in.readInt() - 1, in.readInt());
                else
                    cities[in.readInt() - 1] = in.readInt();
            } else {
                if (n > 10)
                    query(sb, in.readInt());
                else {
                    bruteQuery(sb, in.readInt());
                }
            }
        }

        out.print(sb);
    }

    private static void bruteQuery(StringBuilder sb, int len) {
        if (len == 0) {
            long multi = 1;

            for (int e : cities)
                multi *= e;

            long last = multi % mod;

            while (multi > 9)
                multi /= 10;

            sb.append(multi).append(' ').append(last).append('\n');
            return;
        }

        long multi = 1;
        for (int i = 0; i < cities.length; i += len)
            multi *= cities[i];

        long last = multi % mod;

        while (multi > 9)
            multi /= 10;

        sb.append(multi).append(' ').append(last).append('\n');
        return;
    }

    private static void update(int index, int value) {
        if (cities[index] == value)
            return;

        double logDelta = Math.log10(value) - citiLogs[index];
        long delta = inverse(cities[index]) * value % mod;
        citiLogs[index] += logDelta;
        cities[index] = value;
        multi = multi * delta % mod;
        logSum += logDelta;

        if (index == 0) {
            productOffset = productOffset * delta % mod;
            productLogOffset += logDelta;
            return;
        }

        if (index == 1) {
            return;
        }

        for (int e : factors[index]) {
            if (e > limit)
                break;

            products[e] = products[e] * delta % mod;
            logs[e] += logDelta;
        }
    }

    private static void query(StringBuilder sb, int r) {
        if (r == 1) {
            int first = (int)Math.pow(10, logSum - (int)logSum);
            sb.append(first).append(' ').append(multi).append('\n');
            return;
        }

        if (r <= limit) {
            int first =
                (int)Math.pow(10, logs[r] + productLogOffset - (int)(logs[r] +
                                                                     productLogOffset));
            sb.append(first).append(' ').append(products[r] * productOffset %
                                                mod).append('\n');
            return;
        }

        long res = 1L;
        double log = 0.0;

        for (int i = 0; i < cities.length; i += r) {
            res = res * cities[i] % mod;
            log += citiLogs[i];
        }

        int first = (int)(Math.pow(10, log - (int)log));
        sb.append(first).append(' ').append(res).append('\n');
    }

    private static void process(int n) {
        logSum = 0.0;
        multi = 1L;
        limit = n > 317 ? 317 : n >>> 1;
        logs = new double[limit + 1];
        products = new long[limit + 1];
        citiLogs = new double[n];

        for (int i = 0; i < n; i++)
            citiLogs[i] = Math.log10(cities[i]);

        for (int i = 0; i <= limit; i++)
            products[i] = 1;

        factors =
                (LinkedList<Integer>[])Array.newInstance(LinkedList.class, n +
                                                         1);

        for (int i = 2; i <= n; i++) {
            factors[i] = new LinkedList<Integer>();
        }

        for (int i = 2; i <= limit; i++) {
            for (int j = i; j <= n; j += i)
                factors[j].add(i);
        }

        for (int e : cities)
            multi = multi * e % mod;

        for (int i = 2; i <= limit; i++) {
            for (int j = 0; j < n; j += i) {
                products[i] = products[i] * cities[j] % mod;
                logs[i] += citiLogs[j];
            }
        }

        for (double e : citiLogs)
            logSum += e;
    }

    public static long inverse(long a) {
        if (a == 1 || a == 0)
            return a;

        int r = highBit, b = mod - 2;
        long res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }

        return res;
    }

    final static class InputReader {
        InputStream in;
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
