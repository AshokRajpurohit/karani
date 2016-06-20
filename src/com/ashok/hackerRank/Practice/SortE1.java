package com.ashok.hackerRank.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link: https://www.hackerrank.com/challenges/closest-numbers
 */

public class SortE1 {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SortE1 a = new SortE1();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = new int[n];
        StringBuilder sb = new StringBuilder(n << 4);

        for (int i = 0; i < n; i++) {
            ar[i] = in.readInt();
        }

        MergeSort.sort(ar);
        int min_dif = Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            min_dif =
                    ar[i] - ar[i - 1] >= min_dif ? min_dif : ar[i] - ar[i - 1];
        }

        for (int i = 1; i < n; i++) {
            if (ar[i] == ar[i - 1] + min_dif)
                sb.append(ar[i - 1]).append(' ').append(ar[i]).append(' ');
        }
        sb.append('\n');
        out.print(sb);
    }

    final static class MergeSort {
        private MergeSort() {
            super();
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
