package com.ashok.hackerearth.alkhwarizm;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:
 */

public class B {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] arA, arB, arSa, arSb, arS;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        B a = new B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 4);
        arA = new int[n];
        arB = new int[n];
        arSa = new int[n];
        arSb = new int[n];
        arS = new int[n];

        for (int i = 0; i < n; i++) {
            arA[i] = in.readInt();
        }

        for (int i = 0; i < n; i++) {
            arB[i] = in.readInt();
        }

        for (int i = 0; i < q; i++) {
            int x = in.readInt() - 1;
            int y = in.readInt() - 1;
            sb.append(solve(x, y)).append('\n');
        }
        out.print(sb);
    }

    private static long solve(int start, int end) {
        for (int i = start; i <= end; i++) {
            arSa[i] = arA[i];
            arSb[i] = arB[i];
        }
        sort(arSa, arS, start, end);
        sort(arSb, arS, start, end);

        long res = 0;
        for (int i = start; i <= end; i++) {
            res = res + (long)arSa[i] * (long)arSb[i];
        }

        return res;
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
