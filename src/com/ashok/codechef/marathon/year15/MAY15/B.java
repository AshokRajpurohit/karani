package com.ashok.codechef.marathon.year15.MAY15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113
 *  problem Link: http://www.codechef.com/MAY15/problems/SETDIFF
 */

public class B {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;

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
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            sort(ar);
            out.println(solve(ar));
        }
    }

    private static long solve(int[] ar) {
        long res = 0;
        long[] count = new long[ar.length];

        // let's save the number of subsets till ith element.

        for (int i = 1; i < ar.length; i++) {
            count[i] = ((count[i - 1] << 1) + 1) % mod;
        }

        long temp = 0;

        /**
         * let's use the previously calculated value ((i-1)th item).
         * the solution for ith element is related to the solution for first
         * i-1 elements.
         * S(n) = (S(n-1) + count(n-1) * diff(n, n-1)) * 2 + diff(n, n-1)
         * here S(n) is solution for first n elements.
         * count(n) is number of subsets can be formed using first n elements.
         * diff(n, n-1) is the difference b/w nth and (n-1)th element,
         * i. e. ar[i] - ar[i-1].
         * Theory: S(n-1) is the solution for n-1 elements, if we use the same
         * subsets the solution would be S(n-1) + count(n-1) * diff
         * as nth element is larger than n-1 th element by diff and so in every
         * calculation the difference will be diff.
         * now S(n-1) is when we use n-1 th element in subset (for S(n)
         * and S(n-1) when we don't.
         * So (S(n-1) + count * diff) comes in S(n) two times.
         * now we have additional subset of exactly two elements n-1th and nth.
         * so in this case the max - min will again be diff.
         *
         */

        for (int i = 1; i < ar.length; i++) {
            temp += (count[i - 1] * (ar[i] - ar[i - 1])) % mod;
            temp = ((temp << 1) + ar[i] - ar[i - 1]) % mod;
            res = (res + temp) % mod;
        }

        return res;
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
