package com.ashok.codechef.marathon.year15.APRIL15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  http://www.codechef.com/APRIL15/problems/DIVLAND
 */

public class F_2 {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        F_2 a = new F_2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int m = in.readInt();
        long[] cost = new long[n];
        StringBuilder sb = new StringBuilder(n << 2);

        for (int i = 0; i < m; i++) {
            int a = in.readInt();
            int b = in.readInt();
            int c = in.readInt();
            cost[a] = cost[a] + c;
            cost[b] = cost[b] + c;
        }
        //        int[] format = MergeSort.sortIndex(cost);

        for (int i = 0; i < cost.length; i = i + 2) {
            sb.append(i).append(' ');
            //            sb.append(format[i]).append(' ');
        }
        sb.append('\n');
        out.print(sb);
    }

    final static class MergeSort {

        public static int[] sortIndex(long[] a) {
            int[] b = new int[a.length];
            int[] c = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                c[i] = i;
            }
            sort(a, b, c, 0, a.length - 1);
            return c;
        }

        private static void sort(long[] a, int[] b, int[] c, int begin,
                                 int end) {
            if (begin == end) {
                return;
            }

            int mid = (begin + end) / 2;
            sort(a, b, c, begin, mid);
            sort(a, b, c, mid + 1, end);
            merge(a, b, c, begin, end);
        }

        private static void merge(long[] a, int[] b, int[] c, int begin,
                                  int end) {
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
