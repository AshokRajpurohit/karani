package com.ashok.hackerRank.DP;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: The Maximum Subarray
 * https://www.hackerrank.com/challenges/maxsubarray
 */

public class MaxSubArray {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MaxSubArray a = new MaxSubArray();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            process(in.readIntArray(n));
        }
    }

    private static void process(int[] ar) {
        out.print(subArray(ar));
        out.println(" " + maxSequence(ar));
    }
    
    /**
     * Kadane's Algorithm for maximum subarray.
     * @param ar
     * @return
     */

    private static int subArray(int[] ar) {
        int max_sofar = ar[0], max_end = ar[0];

        for (int i = 1; i < ar.length; i++) {
            max_end = Math.max(ar[i], ar[i] + max_end);
            max_sofar = Math.max(max_sofar, max_end);
        }

        return max_sofar;
    }

    private static int maxSequence(int[] ar) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < ar.length; i++)
            max = Math.max(max, ar[i]);

        if (max <= 0)
            return max;

        max = 0;
        for (int i = 0; i < ar.length; i++)
            if (ar[i] > 0)
                max += ar[i];

        return max;
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
