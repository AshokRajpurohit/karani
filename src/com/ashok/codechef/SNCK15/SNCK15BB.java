package com.ashok.codechef.SNCK15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem:
 */

public class SNCK15BB {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SNCK15BB a = new SNCK15BB();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            solve(in.readIntArray(n));
        }
    }

    private static void solve(int[] ar) {
        //        int max = 0, min = 0;
        //        if (ar.length % 3 != 0)
        //            min = 1;
        //        if (ar.length % 2 != 0)
        //            max = 1;
        //
        //        max += ar.length / 2;
        //        min += ar.length / 3;
        //
        //        out.println(min + " " + max);

        sort(ar);
        int[] min_ar = new int[ar.length];
        for (int i = 0; i < ar.length; i++)
            min_ar[i] = ar[i];

        int min_count = 0;
        for (int i = 1; i < ar.length - 1; ) {
            if (ar[i - 1] == ar[i] - 1 && ar[i + 1] == ar[i] + 1) {
                min_count++;
                ar[i] = 0;
                ar[i - 1] = 0;
                ar[i + 1] = 0;
                i += 3;
            } else
                i++;
        }

        for (int i = 0; i < ar.length - 1; ) {
            if (ar[i] != 0 && ar[i + 1] == ar[i] + 1) {
                min_count++;
                ar[i] = 0;
                ar[i + 1] = 0;
                i += 2;
            } else
                i++;
        }

        for (int i = 0; i < ar.length; i++)
            if (ar[i] != 0)
                min_count++;

        int max_count = 0;
        for (int i = 0; i < ar.length - 1; ) {
            if (min_ar[i + 1] == min_ar[i] + 1) {
                min_ar[i] = 0;
                min_ar[i + 1] = 0;
                max_count++;
                i += 2;
            } else
                i++;
        }

        for (int i = 0; i < ar.length; i++)
            if (min_ar[i] != 0) {
                max_count++;
            }

        out.println(min_count + " " + max_count);
    }

    private static void sort(int[] a) {
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
