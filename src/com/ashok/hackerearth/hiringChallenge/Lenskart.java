package com.ashok.hackerearth.hiringChallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Lenskart Java Hiring Challenge
 * Link: https://www.hackerearth.com/lenskart-java-hiring-challenge/problems
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Lenskart {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        VasyaVsRhezo.solve();
//        AwesomeSequence.solve();
        in.close();
        out.close();
    }

    public final static class VasyaVsRhezo {
        int[] ar, br, next;
        SparseTable table;

        public VasyaVsRhezo(int[] a, int[] b) {
            ar = a.clone();
            br = b.clone();
            table = new SparseTable(a);
            next = nextEqual(a);
        }

        static void solve() throws IOException {
            int n = in.readInt();
            int[] ara = in.readIntArray(n), arb = in.readIntArray(n);
            int[] anext = nextEqual(ara);
            SparseTable tableA = new SparseTable(ara);

            int q = in.readInt();
            StringBuilder sb = new StringBuilder(q << 2);

            while (q > 0) {
                q--;

                int left = in.readInt() - 1, right = in.readInt() - 1;
                int indexA = tableA.query(left, right);
                int next = anext[indexA];
                if (next > right) {
                    sb.append(indexA + 1).append('\n');
                    continue;
                }

                if (arb[indexA] <= arb[next])
                    sb.append(indexA + 1).append('\n');
                else
                    sb.append(next + 1).append('\n');
            }

            out.print(sb);
        }

        public int process(int L, int R) {
            int a = table.query(L, R);

            if (next[a] > R)
                return a;

            int b = next[a];
            return br[a] <= br[b] ? a : b;
        }
    }

    public final static class AwesomeSequence {
        static int[] ar;
        static int mod = 1000000007;
        static long[] bits = new long[60];

        static {
            bits[0] = 1;

            for (int i = 1; i < 60; i++)
                bits[i] = bits[i - 1] << 1;
        }

        public AwesomeSequence(int[] ar) {
            this.ar = ar;
        }

        static void solve() throws IOException {
            int n = in.readInt();
            ar = in.readIntArray(n);

            int q = in.readInt();
            StringBuilder sb = new StringBuilder(q << 2);

            while (q > 0) {
                q--;
                sb.append(process(in.readLong())).append('\n');
            }

            out.print(sb);
        }

        private static int bitIndex(long n) {
            if (n == 1)
                return 0;

            int i = 59;

            while (bits[i] > n)
                i--;

            return i;
        }

        public static long process(long n) {
            if (n == 0)
                return 1;

            return process(n, bitIndex(n));
        }

        private static long process(long n, int bitIndex) {
            if (n == 0)
                return 1;

            if (n < bits[bitIndex])
                return process(n, bitIndex - 1);

            return (process(n - bits[bitIndex], bitIndex) + ar[(int) (n % ar.length)]) % mod;
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }
    }

    /**
     * Returns array of integers having index of next equal element to it.
     * If the element doesn't have any such element then the index for it would
     * be the array size.
     * <p>
     * for array
     * {1, 2, 3, 2, 1}
     * next equal elements indices should be
     * {4, 3, 5, 5, 5}
     * <p>
     * The parameter array doesn't need to be sorted.
     *
     * @param ar
     * @return
     */
    public static int[] nextEqual(int[] ar) {
        int[] res = getIndexArray(ar.length);
        Pair[] pairs = new Pair[ar.length];
        res[ar.length - 1] = ar.length;

        for (int i = 0; i < ar.length; i++)
            pairs[i] = new Pair(i, ar[i]);

        Arrays.sort(pairs);

        for (int i = ar.length - 2; i >= 0; i--) {
            if (pairs[i].value == pairs[i + 1].value)
                res[pairs[i].index] = pairs[i + 1].index;
            else
                res[pairs[i].index] = ar.length;
        }

        return res;
    }

    public static int[] getIndexArray(int size) {
        int[] ar = new int[size];

        for (int i = 1; i < size; i++)
            ar[i] = i;

        return ar;
    }

    final static class Pair implements Comparable<Pair> {
        int index;
        int value;

        Pair(int i, int v) {
            index = i;
            value = v;
        }

        @Override
        public int compareTo(Pair pair) {
            return value == pair.value ? index - pair.index : value - pair.value;
        }
    }

    /**
     * Implementation of Range Query Data Structure using an array of arrays.
     * Preprocessing complexity is order of n * long(n) and quering complexity is
     * order of 1 (constant time). This is useful when the range of the query can
     * be anything from 1 to n. If the min range width is fixed,
     * RangeQueryBlock is the better option for range query.
     * For two dimensional arrays use Quadtree or SparseTable2D
     *
     * @author Ashok Rajpurohit ashok1113@gmail.com
     */
    final static class SparseTable {
        private int[][] mar;
        private int[] ar;

        public SparseTable(int[] ar) {
            format(ar);
        }

        public int query(int L, int R) {
            if (R >= mar[1].length || L < 0)
                throw new IndexOutOfBoundsException(L + ", " + R);

            int half = Integer.highestOneBit(R + 1 - L);
            return operation(mar[half][L], mar[half][R + 1 - half]);
        }

        private void format(int[] paramArray) {
            this.ar = paramArray.clone();
            mar = new int[ar.length + 1][];
            mar[1] = getIndexArray(paramArray.length);
            int bit = 2;
            while (bit < mar.length) {
                int half = bit >>> 1;
                mar[bit] = new int[ar.length - bit + 1];
                for (int i = 0; i <= ar.length - bit; i++) {
                    mar[bit][i] = operation(mar[half][i], mar[half][i + half]);
                }
                bit <<= 1;
            }
        }

        /**
         * Single operation for query types.
         *
         * @param a index of first element
         * @param b index of second element
         * @return index of higher element
         */
        public int operation(int a, int b) {
            if (a > b)
                return operation(b, a);

            return ar[a] >= ar[b] ? a : b;
        }
    }
}
