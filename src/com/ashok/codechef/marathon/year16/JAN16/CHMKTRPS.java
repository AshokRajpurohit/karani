package com.ashok.codechef.marathon.year16.JAN16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Making Triples
 * https://www.codechef.com/JAN16/problems/CHMKTRPS
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHMKTRPS {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar, sar;
    private static boolean[] check;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHMKTRPS a = new CHMKTRPS();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt() * 3;
        ar = in.readIntArray(n);
        StringBuilder sb = new StringBuilder();
        sar = sortIndex(ar);
        check = new boolean[n];
        int count = 0;
        int sum = 0;
        int m = n / 2;
        sum = ar[sar[m - 1]] + ar[sar[m]] + ar[sar[m + 1]];
        sum = Math.min(sum, ar[sar[0]] + ar[sar[1]] + ar[sar[n - 1]]);

        //        if (count == 0) {
        //            out.println("1\n1 2 3 ");
        //            return;
        //        }

        for (int i = 0, j = n - 1; i < j; i++, j--) {
            if (check[i])
                i++;

            if (check[j])
                j--;

            if (i >= j)
                break;

            int index = find(sum - ar[sar[i]] - ar[sar[j]]);
            if (index == -1)
                continue;

            check[i] = true;
            check[j] = true;
            check[index] = true;
            count++;
            sb.append(sar[i] + 1).append(' ').append(sar[j] +
                                                     1).append(' ').append(sar[index] +
                                                                           1).append(' ');
        }

        if (count == 0) {
            out.println("1\n1 2 3");
            return;
        }
        out.println(count);
        out.println(sb);
    }

    private static int find(int value) {
        int start = 0, end = ar.length - 1, mid = ar.length >>> 1;
        while (start < end - 1) {
            mid = (start + end) >>> 1;
            if (ar[sar[mid]] > value)
                end = mid;
            else if (ar[sar[mid]] < value)
                start = mid;
            else if (check[mid])
                return -1;
            else
                return mid;

            if (ar[sar[start]] == value) {
                if (check[start])
                    return -1;
                return start;
            }

            if (ar[sar[end]] == value) {
                if (check[end])
                    return -1;

                return end;
            }
        }

        if (check[mid] || ar[sar[mid]] != value)
            return -1;
        else
            return mid;
    }

    private static int[] sortIndex(int[] a) {
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

    private static void merge(int[] a, int[] b, int[] c, int begin, int end) {
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
