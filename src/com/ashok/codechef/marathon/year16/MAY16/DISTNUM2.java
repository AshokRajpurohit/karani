package com.ashok.codechef.marathon.year16.MAY16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.HashMap;

/**
 * Problem: Easy Queries
 * https://www.codechef.com/MAY16/problems/DISTNUM2
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class DISTNUM2 {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar;
    private static int l, r, ans = 0, N, MAX = 1000000001, MIN = 0;
    private static RangeQueryRead rq;
    private static int[] nextLarge, nextSmall, prevSmall;
    private static HashMap<Integer, Boolean> mark =
        new HashMap<Integer, Boolean>();
    private static Heap heap = new Heap();

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        DISTNUM2 a = new DISTNUM2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        N = in.readInt();
        int q = in.readInt();
        ar = in.readIntArray(N);
        populate();
        StringBuilder sb = new StringBuilder(q << 2);

        while (q > 0) {
            q--;
            next(in.readInt(), in.readInt(), in.readInt(), in.readInt());
            solve(in.readInt());
            sb.append(ans).append('\n');
        }

        out.print(sb);
    }

    private static void solve(int k) {
        // update ans
        // let's traverse the array.
        if (k == 1) {
            if (l == r)
                ans = ar[l];
            else
                ans = rq.query(l, r);

            return;
        }

        if (k > r - l + 1) {
            ans = -1;
            return;
        }

        ans = heap.kthMin(ar, k, l, r);
        if (ans == 0)
            ans = -1;

        /*
        int rank = 0;

        for (int i = l; i <= r; ) {
            rank = rank(i);

            if (rank == k) {
                ans = ar[i];
                return;
            }

            if (rank < k)
                i = nextLarge[i];
            else {
                while (rank > k) {
                    rank--;
                    i = nextSmall[i];
                }

                ans = ar[i];
                return;
            }
        } */
    }

    private static int rank(int index) {
        int count = 1;
        int j = prevSmall[index];

        while (j >= l) {
            count++;
            mark.put(ar[j], true);
            j = prevSmall[j];
        }

        j = nextSmall[index];
        while (j <= r) {
            count++;
            mark.put(ar[j], true);
            j = nextSmall[j];
        }

        return count;
    }

    private static void next(int a, int b, int c, int d) {
        if (ans < 0)
            ans = 0;

        l = (int)((1L * a * ans + b) % N);
        r = (int)((1L * c * ans + d) % N);

        if (l > r) {
            int temp = l;
            l = r;
            r = temp;
        }
    }

    private static void populate() {
        rq = new RangeQueryRead(ar);

        nextLarge = new int[N];
        nextSmall = new int[N];
        prevSmall = new int[N];

        nextLarge[N - 1] = N;
        prevSmall[0] = -1;
        nextSmall[N - 1] = N;

        for (int i = N - 2; i >= 0; i--) {
            int j = i + 1;
            while (j != N && ar[i] >= ar[j])
                j = nextLarge[j];

            nextLarge[i] = j;
        }

        for (int i = N - 2; i >= 0; i--) {
            int j = i + 1;
            while (j != N && ar[i] <= ar[j])
                j = nextSmall[j];

            nextSmall[i] = j;
        }

        for (int i = 1; i < N; i++) {
            int j = i - 1;

            while (j != -1 && ar[i] <= ar[j])
                j = prevSmall[j];

            prevSmall[i] = j;
        }
    }

    final static class Pair implements Comparable<Pair> {
        int num, count;

        Pair(int n, int c) {
            num = n;
            count = c;
        }

        public int compareTo(Pair pair) {
            return this.num - pair.num;
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

    final static class RangeQueryRead {
        private int[][] mar;

        public RangeQueryRead(int[] ar) {
            format(ar);
        }

        private int query(int L, int R) {
            int half = Integer.highestOneBit(R + 1 - L);
            return Math.min(mar[half][L], mar[half][R + 1 - half]);
        }

        private void format(int[] ar) {
            mar = new int[ar.length + 1][];
            mar[1] = ar;
            int bit = 2;
            while (bit < mar.length) {
                int half = bit >>> 1;
                mar[bit] = new int[ar.length - bit + 1];
                for (int i = 0; i <= ar.length - bit; i++) {
                    mar[bit][i] = Math.min(mar[half][i], mar[half][i + half]);
                }
                bit <<= 1;
            }
        }
    }

    final static class Heap {
        private Heap() {
            super();
        }

        private int[] heap;
        private boolean min;
        int k = 0;

        public static int kthMax(int[] ar, int k, int from, int to) {
            //            if (k > ar.length >> 1)
            //                return minElements(ar, ar.length - k + 1, from, to)[0];

            return maxElements(ar, k, from, to)[0];
        }

        public static int kthMin(int[] ar, int k, int from, int to) {
            //            if (k > ar.length >> 1)
            //                return maxElements(ar, ar.length - k + 1, from, to)[0];

            return minElements(ar, k, from, to)[0];
        }

        public static int[] maxElements(int[] ar, int k, int from, int to) {
            Heap h = new Heap();
            h.heap = new int[k];
            h.min = true;
            for (int i = 0; i < k; i++)
                h.add(ar[from + i]);

            for (int i = from + k; i <= to; i++) {
                if (ar[i] > h.heap[0]) {
                    h.heap[0] = ar[i];
                    h.reformatDown(0);
                }
            }

            return h.heap;
        }

        public static int[] minElements(int[] ar, int k, int from, int to) {
            Heap h = new Heap();
            h.heap = new int[k];
            h.min = false;
            int i = 0;
            for (i = from; h.k < k && i <= to; i++)
                h.add(ar[i]);

            h.k--;

            for (; i <= to; i++) {
                if (ar[i] < h.heap[0]) {
                    h.heap[0] = ar[i];
                    h.reformatDown(0);
                }
            }

            return h.heap;
        }

        private void sort() {
            while (k > 0) {
                int temp = heap[0];
                k--;
                heap[0] = heap[k];
                heap[k] = temp;
                reformatDown(0);
            }

            // let's reverse the array.
            for (int i = 0, j = heap.length - 1; i < j; i++, j--) {
                int temp = heap[i];
                heap[i] = heap[j];
                heap[j] = temp;
            }
        }

        private void add(int n) {
            heap[k] = n;
            if (reformatUp(k))
                k++;
        }

        private boolean reformatUp(int index) {
            if (index == 0)
                return true;

            int parent = (index - 1) >>> 1;
            if (parent == index)
                return true;

            if (heap[index] == heap[parent]) {
                heap[index] = heap[k];
                reformatDown(index);
                return false;
            }

            if (min) {
                if (heap[index] < heap[parent]) {
                    swap(index, parent);
                    return reformatUp(parent);
                }
                return true;
            }

            if (heap[index] > heap[parent]) {
                swap(index, parent);
                return reformatUp(parent);
            }

            return true;
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        private boolean reformatDown(int index) {
            if ((index << 1) + 1 >= k)
                return true;

            int child1 = (index << 1) + 1;
            int child2 = child1 + 1;
            child2 = child2 == k + 1 ? child1 : child2;

            if (min) {
                int child = heap[child1] > heap[child2] ? child2 : child1;

                if (heap[index] == heap[child]) {
                    heap[child] = heap[k];
                    return reformatDown(child);
                }

                if (heap[index] > heap[child]) {
                    swap(index, child);
                    return reformatDown(child);
                }

                return true;
            }

            int child = heap[child1] > heap[child2] ? child1 : child2;

            if (heap[index] == heap[child]) {
                heap[child] = heap[k];
                reformatDown(child);
                return false;
            }

            if (heap[index] < heap[child]) {
                swap(index, child);
                return reformatDown(child);
            }

            return true;
        }
    }
}
