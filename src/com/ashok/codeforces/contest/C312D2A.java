package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem: Lala Land and Apple Trees
 * http://codeforces.com/contest/558/problem/A
 */

public class C312D2A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C312D2A a = new C312D2A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] x = new int[n];
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            x[i] = in.readInt();
            a[i] = in.readInt();
        }

        out.println(process(x, a));
    }

    private static long process(int[] x, int[] a) {
        if (x.length == 1)
            return a[0];
        int neg_count = 0, pos_count = 0;

        int[] sortIndex = sortIndex(x);
        int index = 0;
        while (x[neg_count] < 0)
            neg_count++;

        pos_count = x.length - neg_count;
        int equal = pos_count > neg_count ? neg_count : pos_count;
        long result = 0;

        index = neg_count - 1;
        for (int i = 0; i < equal; i++)
            result += a[index - i];

        index = neg_count;
        for (int i = 0; i < equal; i++)
            result += a[index + i];

        if (neg_count + equal >= x.length && neg_count < equal + 1)
            return result;

        if (neg_count + equal >= x.length && neg_count >= equal + 1)
            return result + a[neg_count - equal - 1];

        if (neg_count + equal < x.length && neg_count < equal + 1)
            return result + a[neg_count + equal];

        if (a[neg_count + equal] > a[neg_count - equal - 1])
            result += a[neg_count + equal];
        else
            result += a[neg_count - equal - 1];

        return result;
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
