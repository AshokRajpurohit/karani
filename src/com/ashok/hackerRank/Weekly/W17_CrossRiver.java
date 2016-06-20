package com.ashok.hackerRank.Weekly;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Cross the River
 */

public class W17_CrossRiver {

    private static PrintWriter out;
    private static InputStream in;
    private static int n, h, dh, dw;
    private static Pair[] ar;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        W17_CrossRiver a = new W17_CrossRiver();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        n = in.readInt();
        h = in.readInt();
        dh = in.readInt();
        dw = in.readInt();
        ar = new Pair[n];

        for (int i = 0; i < n; i++)
            ar[i] = new Pair(in.readInt(), in.readInt(), in.readInt());

        out.println(process());
    }

    private static int process() {
        Arrays.sort(ar, Pair_Compare);

        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n && ar[i].y <= dh; i++)
            res = Math.max(res, process(i));

        return res;
    }

    /**
     * This function calculates the points you will get if you start from this
     * rock to the other shore.
     *
     * @param index rock ar[index] as start point
     * @return
     */
    private static int process(int index) {
        if (index >= n || ar[index].y >= h)
            return 0;

        if (ar[index].reachable != 0)
            return ar[index].z;

        int res = Integer.MIN_VALUE, j = index;
        while (j < n && ar[j].y == ar[index].y)
            j++;

        for (; j < n && ar[j].y <= ar[index].y + dh; j++) {
            if (ar[j].x <= ar[index].x + dw && ar[j].x >= ar[index].x - dw) {
                if (ar[j].reachable == 0)
                    process(j);

                if (ar[j].reachable == 1)
                    res = Math.max(res, ar[j].z);
            }
        }

        ar[index].reachable = -1;

        if (res != Integer.MIN_VALUE)
            ar[index].reachable = 1;

        if (ar[index].y + dh >= h) {
            ar[index].reachable = 1;
            res = Math.max(res, 0);
        }

        ar[index].z += res;
        return ar[index].z;
    }

    private static POINT_ORDER Pair_Compare = new POINT_ORDER();

    final static class POINT_ORDER implements Comparator<Pair> {
        public int compare(Pair a, Pair b) {
            if (a.y != b.y)
                return a.y - b.y;

            return a.x - b.x;
        }
    }

    final static class Pair {
        int x, y, z;
        int reachable = 0;

        Pair(int b, int a, int c) {
            x = a;
            y = b;
            z = c;
        }

        public String toString() {
            return x + " " + y;
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
