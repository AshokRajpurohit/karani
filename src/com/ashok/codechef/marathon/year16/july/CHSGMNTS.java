package com.ashok.codechef.marathon.year16.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Chef and Segments
 * Link: https://www.codechef.com/JULY16/problems/CHSGMNTS
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CHSGMNTS {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        CHSGMNTS a = new CHSGMNTS();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            out.println(solve(n, in.readIntArray(n)));
        }
    }

    public static long solve(int n, int[] ar) {
        if (n == 1 || allEquals(ar))
            return 0;

        if (n == 2)
            return 1;

        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++)
            pairs[i] = new Pair(ar[i], i);

        Arrays.sort(pairs);
        if (allUnique(pairs))
            return 1L * n * (n + 1) * (n - 1) * (n + 2) / 24;

        int[] next = nextEquals(pairs), prev = previousEquals(pairs);
        long total = 0, current = 0;

        for (int a = 0; a < n - 1; a++) {
            CustomArray array = new CustomArray(n + 1 - a);
            array.ar[0] = n;

            for (int b = a; b < n - 1; b++) {
                if (prev[b] >= a) {
                    total += current;
                    continue;
                }

                if (next[b] != n)
                    array.insert(indexArray(next, b));

                current = array.calculate(b);
                total += current;
            }
        }

        return total;
    }

    /**
     * Returns the array of indices where duplicate to element at start exists.
     *
     * @param next
     * @param start
     * @return
     */
    private static int[] indexArray(int[] next, final int start) {
        int size = 0, i = start;
        while (next[i] != next.length) {
            i = next[i];
            size++;
        }

        int[] res = new int[size];
        for (int j = 0, ni = next[start]; j < size; j++, ni = next[ni]) {
            res[j] = ni;
        }

        return res;
    }

    public static int[] previousEquals(Pair[] pairs) {
        int n = pairs.length;
        int[] prev = new int[n];
        prev[pairs[0].index] = -1;

        for (int i = 1; i < n; i++) {
            if (pairs[i].value == pairs[i - 1].value)
                prev[pairs[i].index] = pairs[i - 1].index;
            else
                prev[pairs[i].index] = -1;
        }

        return prev;
    }

    /**
     * Returns the array containing the index of next equal element.
     * At position i the value in the returned array is the position of
     * next elemet which is equal to the element at index i in the original
     * array.
     * This method is customized version of
     * {@link com.ashok.lang.utils.Utils#nextEqual(int[])}, since we have the
     * sorted array, we can utilize the same.
     *
     * @param pairs
     * @return
     */
    public static int[] nextEquals(Pair[] pairs) {
        int n = pairs.length;
        int[] next = new int[n];
        next[pairs[n - 1].index] = n;

        for (int i = 0; i < n - 1; i++) {
            if (pairs[i].value == pairs[i + 1].value)
                next[pairs[i].index] = pairs[i + 1].index;
            else
                next[pairs[i].index] = n;
        }

        return next;
    }

    private static boolean allEquals(int[] ar) {
        int v = ar[0];

        for (int e : ar)
            if (v != e)
                return false;

        return true;
    }

    /**
     * Returns True if all the elements in the specified sorted array are
     * unique.
     *
     * @param pairs
     * @return true when all elements are distinct.
     */
    private static boolean allUnique(Pair[] pairs) {
        for (int i = 1; i < pairs.length; i++)
            if (pairs[i].value == pairs[i - 1].value)
                return false;

        return true;
    }

    /**
     * The {@code Pair} class is to encapsulate the array element and it's
     * index for sorting and keeping the original index together.
     */
    final static class Pair implements Comparable<Pair> {
        int value, index;

        Pair(int v, int i) {
            value = v;
            index = i;
        }

        @Override
        public int compareTo(Pair pair) {
            if (this.value == pair.value)
                return this.index - pair.index;

            return this.value - pair.value;
        }
    }

    /**
     * The {@code CustomArray} class is to merge array into the original
     * multiple times with minimum time cost.
     */
    final static class CustomArray {
        int[] ar;
        int start = 0, end = 0;

        CustomArray(int size) {
            ar = new int[size];
        }

        private void ensureCapacity(int newLen) {
            if (ar.length >= newLen)
                return;

            int[] aux = new int[Math.max(ar.length << 1, newLen)];
            System.arraycopy(ar, 0, aux, 0, ar.length);
        }

        public void insert(int[] a) {
            ensureCapacity(a.length + end + 1);

            for (int i = end, j = a.length - 1, k = end + a.length; j >= 0 &&
                    k >= 0; ) {
                if (i < 0 || a[j] > ar[i])
                    ar[k--] = a[j--];
                else ar[k--] = ar[i--];
            }

            end += a.length;
        }

        public long calculate(int startPos) {
            resetStart(startPos);
            int i = ar[start] > startPos ? start : start + 1;

            int len = ar[i] - startPos - 1;
            long res = (len * (len + 1)) >>> 1;
            i++;

            while (i <= end) {
                len = ar[i] - ar[i - 1];
                i++;

                if (len == 1)
                    continue;

                res += (len * (len - 1)) >>> 1;
            }

            return res;
        }

        private void resetStart(int pos) {
            if (ar[start] >= pos)
                return;

            while (ar[start] < pos)
                start++;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
