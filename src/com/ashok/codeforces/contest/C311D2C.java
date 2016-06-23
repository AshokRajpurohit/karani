package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem: Arthur and Table
 * http://codeforces.com/contest/557/problem/C
 * Incomplete and may be incorrect
 */

public class C311D2C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C311D2C a = new C311D2C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        out.println(process(in.readIntArray(n), in.readIntArray(n)));
    }

    private static int process(int[] len, int[] en) {
        if (len.length == 1)
            return 0;

        int minLen = Integer.MAX_VALUE, maxLen = 0;
        for (int i = 0; i < len.length; i++) {
            minLen = Math.min(minLen, len[i]);
            maxLen = Math.max(maxLen, len[i]);
        }

        if (minLen == maxLen)
            return 0;

        int count = 0;
        for (int i = 0; i < len.length; i++)
            if (len[i] == maxLen)
                count++;

        if (count >= len.length >>> 1)
            return 0;

        int[] index = sortIndex(len);
        int[] prev = new int[len.length];
        int[] next = new int[len.length];

        int max = 0, temp = en[index[0]];
        for (int i = 1; i < len.length; i++) {
            if (len[index[i]] == len[index[i - 1]])
                temp += en[index[i]];
            else {
                max = Math.max(max, temp);
                temp = en[index[i]];
            }
        }

        int total = 0;
        for (int i = 0; i < len.length; i++)
            total += en[i];

        return total;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
