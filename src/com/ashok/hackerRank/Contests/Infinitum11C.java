package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem | Divisor Ranges
 * https://www.hackerrank.com/contests/infinitum11/challenges/divisor-ranges
 */

public class Infinitum11C {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar, sar;
    private static long[] solu;
    private static int[][] lar;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Infinitum11C a = new Infinitum11C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        ar = new int[n];
        solu = new long[n + 1];
        lar = new int[n + 1][];

        for (int i = 0; i < n; i++) {
            ar[i] = in.readInt();
        }

        format();

        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 3);

        while (q > 0) {
            q--;
            int k = in.readInt();
            sb.append(solve(k)).append('\n');
        }
        out.print(sb);
    }

    /**
     * This function stores the index value at number as index in sar array.
     * sar[NUMBER] = index_of_NUMBER_in_original_array
     */

    private static void format() {
        sar = new int[ar.length + 1];
        for (int i = 0; i < ar.length; i++) {
            sar[ar[i]] = i;
        }
    }

    /**
     * The function stores all the indexes in lar[k] array where the integer
     * is divisible by k and later sort lar[k] using merge_sort;
     * @param k
     */

    private static void format(int k) {
        lar[k] = new int[ar.length / k];
        for (int j = k, i = 0; j <= ar.length; j += k, i++) {
            lar[k][i] = sar[j];
        }
        sort(lar[k]);
    }

    private static long solve(int k) {
        if (solu[k] != 0)
            return solu[k];

        if (k == 1) {
            long res = ar.length;
            res = (res * (res + 1)) >>> 1;
            solu[k] = res;
            return res;
        }

        int count = 1;
        long res = 1;
        format(k);
        for (int i = 1; i < lar[k].length; i++) {
            if (lar[k][i] == lar[k][i - 1] + 1) {
                count++;
            } else {
                count = 1;
            }
            res += count;
        }
        solu[k] = res;

        return res;
    }

    public static void sort(int[] a) {
        int[] b = new int[a.length];
        sort(a, b, 0, a.length - 1);
    }

    private static void sort(int[] a, int[] b, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, begin, mid);
        sort(a, b, mid + 1, end);
        merge(a, b, begin, end);
    }

    private static void merge(int[] a, int[] b, int begin, int end) {
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
    }
}
