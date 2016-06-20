package com.ashok.codechef.Codebyte15;


import java.io.IOException;

import static java.lang.System.in;

import java.io.OutputStream;
import java.io.PrintWriter;

public class C {

    //    private static InputReader in;
    private static PrintWriter out;

    public C() {
        super();
    }

    public static void main(String[] args) throws IOException {
        //        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        //        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream);
        C a = new C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        NewInputReader in = new NewInputReader();
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = new int[n];
            long[][] mar = new long[n][n];
            long r = 0;

            for (int i = 0; i < n; i++) {
                ar[i] = in.readInt();
            }

            int[] sar = sortIndex(ar);
            r = solve(ar, sar, 0, n - 1);
            sb.append(r);
        }

        out.print(sb);
    }

    private long solve(int[] ar, int[] sar, int i, int j) {
        long r = ar[sar[i]];
        return r;
    }

    public static int[] sortIndex(int[] a) {
        int[] b = new int[a.length];
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = i;
        }
        sort(a, b, c, 0, a.length - 1);
        return c;
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


    final static class NewInputReader {
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
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = number * 0x0a + buffer[offset] - 0x30;
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
