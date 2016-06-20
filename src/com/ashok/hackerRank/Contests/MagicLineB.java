package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem  Polar Angles - Fill the key-line!
 * https://www.hackerrank.com/contests/magiclines/challenges/polar-angles-fill-the-key-line
 */

public class MagicLineB {

    private static PrintWriter out;
    private static InputStream in;
    private static double pibytwo = Math.PI / 2;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MagicLineB a = new MagicLineB();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        double[] ar = new double[n];
        int[] x = new int[n], y = new int[n], dis = new int[n];

        for (int i = 0; i < n; i++) {
            x[i] = in.readInt();
            y[i] = in.readInt();
        }

        for (int i = 0; i < n; i++) {
            dis[i] = x[i] * x[i] + y[i] * y[i];
            if (x[i] == 0) {
                if (y[i] > 0)
                    ar[i] = pibytwo;
                else
                    ar[i] = 3 * pibytwo;
            } else if (x[i] >= 0 && y[i] >= 0)
                ar[i] = Math.atan((1.0 * y[i]) / x[i]);
            else if (x[i] < 0 && y[i] >= 0)
                ar[i] = Math.PI - Math.atan((1.0 * y[i]) / (-x[i]));
            else if (x[i] < 0 && y[i] < 0)
                ar[i] = Math.PI + Math.atan((1.0 * y[i]) / x[i]);
            else
                ar[i] = 2 * Math.PI + Math.atan((1.0 * y[i]) / x[i]);
        }

        int[] temp = sortIndex(dis);
        int[] tx = new int[n], ty = new int[n];
        double[] tar = new double[n];

        for (int i = 0; i < n; i++) {
            tx[i] = x[temp[i]];
            ty[i] = y[temp[i]];
            tar[i] = ar[temp[i]];
        }

        temp = sortIndex(tar);

        for (int i = 0; i < n; i++) {
            out.println(tx[temp[i]] + " " + ty[temp[i]]);
        }
    }

    private static int[] sortIndex(double[] a) {
        int[] b = new int[a.length];
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = i;
        }
        sort(a, b, c, 0, a.length - 1);
        return c;
    }

    private static void sort(double[] a, int[] b, int[] c, int begin,
                             int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, c, begin, mid);
        sort(a, b, c, mid + 1, end);
        merge(a, b, c, begin, end);
    }

    public static void merge(double[] a, int[] b, int[] c, int begin,
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
