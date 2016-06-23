package com.ashok.hackerearth.alkhwarizm;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 */

public class C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C a = new C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 3);
        int[] p, d;
        p = new int[n];
        d = new int[n];

        for (int i = 0; i < n; i++) {
            p[i] = in.readInt();
        }

        for (int i = 0; i < n; i++) {
            d[i] = in.readInt();
        }

        int[] b = new int[n];
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = i;
        }
        sort(d, b, c, 0, n - 1);

        long[] sum = new long[n];

        sum[0] = p[c[n - 1]];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + p[c[n - 1 - i]];
        }

        for (int i = 0; i < q; i++) {
            sb.append(sum[in.readInt() - 1]).append('\n');
        }

        out.print(sb);
    }

    private static void sort(int[] a, int[] b, int[] c, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, c, begin, mid);
        sort(a, b, c, mid + 1, end);
        merge(a, b, c, begin, end);
    }

    public static void merge(int[] a, int[] b, int[] c, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[c[i]] > a[c[j]]) {
                b[k] = c[j];
                j++;
            } else {
                b[k] = c[i];
                i++;
            }
            k++;
        }
        if (j <= end) {
            while (j <= end) {
                b[k] = c[j];
                k++;
                j++;
            }
        }
        if (i <= mid) {
            while (i <= mid) {
                b[k] = c[i];
                i++;
                k++;
            }
        }

        i = begin;
        while (i <= end) {
            c[i] = b[i];
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
