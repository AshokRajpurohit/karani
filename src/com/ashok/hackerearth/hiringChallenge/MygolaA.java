package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: TShirtSelection
 * Challenge: Mygola(A MakeMyTrip Entity) Hiring Challenge
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class MygolaA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MygolaA a = new MygolaA();
        a.solve();
        out.close();
    }

    private static void process(int[] ar, int n) {
        if (n == 1 || (n == 2 && ar[0] == ar[1])) {
            out.println("1 1");
            return;
        }

        if (n == 2) {
            out.println("1 2");
            return;
        }

        int[] sar = sortIndex(ar);
        int[] sameColor = new int[n];
        for (int i = 0; i < n; i++)
            sameColor[i] = -1;

        for (int i = 1; i < n; i++) {
            if (ar[sar[i]] == ar[sar[i - 1]])
                sameColor[sar[i]] = sar[i - 1];
        }

        int a = 0, b = 0;
        for (int i = 0, j = 1; i < n && j < n; ) {
            if (sameColor[j] >= i) {
                i = sameColor[j] + 1;
            } else
                j++;

            if (j - i > b - a) {
                a = i;
                b = j;
            }
        }

        out.println((a + 1) + " " + b);
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

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            process(in.readIntArray(n), n);
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
