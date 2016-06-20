package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.HashMap;

/**
 * @author: Ashok Rajpurohit
 * problem: Geometric Progression
 * Link: http://codeforces.com/contest/567/problem/C
 */

public class CPiC {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CPiC a = new CPiC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        out.println(process(in.readInt(), in.readLongArray(n)));
    }

    private static long countGP(int k, long[] ar) {
        if (ar.length < 3)
            return 0;

        long res = 0;
        HashMap<Long, Integer> left = new HashMap<Long, Integer>(ar.length);
        HashMap<Long, Integer> right = new HashMap<Long, Integer>(ar.length);

        for (int i = 0; i < ar.length; i++) {
            left.put(ar[i], 0);
            right.put(ar[i], 0);
        }

        // let's populate left.
        left.put(ar[0], 1);

        // let's populate right.
        for (int i = 2; i < ar.length; i++)
            right.put(ar[i], right.get(ar[i]) + 1);

        for (int i = 1; i < ar.length - 1; i++) {
            if (ar[i] % k == 0) {
                if (left.containsKey(ar[i] / k) &&
                    right.containsKey(ar[i] * k))
                    res += 1L * left.get(ar[i] / k) * right.get(ar[i] * k);
            }

            int value = left.get(ar[i]);
            left.put(ar[i], value + 1);
            value = right.get(ar[i + 1]);
            right.put(ar[i + 1], value - 1);

        }

        return res;
    }

    private static long process(int k, long[] ar) {
        if (ar.length < 3)
            return 0;

        if (k == 1)
            return getThree(ar);

        int pos_count = 0; // count of positive numbers
        int neg_count = 0; // count of negative numbers

        for (int i = 0; i < ar.length; i++)
            if (ar[i] > 0)
                pos_count++;

        for (int i = 0; i < ar.length; i++)
            if (ar[i] < 0)
                neg_count++;

        long[] pos_ar = new long[pos_count];
        for (int i = 0, j = 0; i < ar.length && j < pos_count; i++)
            if (ar[i] > 0) {
                pos_ar[j] = ar[i];
                j++;
            }

        long[] neg_ar = new long[neg_count];
        for (int i = 0, j = 0; i < ar.length && j < neg_count; i++)
            if (ar[i] < 0) {
                neg_ar[j] = -ar[i];
                j++;
            }

        long res = 0, zero_count = ar.length - pos_count - neg_count;
        if (zero_count > 2)
            res = zero_count * (zero_count - 1) * (zero_count - 2) / 6;

        res += countGP(k, pos_ar) + countGP(k, neg_ar);

        return res;
    }

    private static long getThree(long[] ar) {
        sort(ar);
        int i = 0;
        long res = 0;
        while (i < ar.length) {
            int count = 0;
            int j = i;
            while (j < ar.length && ar[j] == ar[i]) {
                j++;
                count++;
            }

            if (count > 2)
                res += (1L * count * (count - 1) * (count - 2) / 6);

            i = j;
        }
        return res;
    }

    private static void sort(long[] a) {
        long[] b = new long[a.length];
        sort(a, b, 0, a.length - 1);
    }

    private static void sort(long[] a, long[] b, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, begin, mid);
        sort(a, b, mid + 1, end);
        merge(a, b, begin, end);
    }

    private static void merge(long[] a, long[] b, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[i] > a[j]) {
                b[k] = a[j];
                j++;
            } else {
                b[k] = a[i];
                i++;
            }
            k++;
        }
        if (j <= end) {
            while (j <= end) {
                b[k] = a[j];
                k++;
                j++;
            }
        }
        if (i <= mid) {
            while (i <= mid) {
                b[k] = a[i];
                i++;
                k++;
            }
        }

        i = begin;
        while (i <= end) {
            a[i] = b[i];
            i++;
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }
}
