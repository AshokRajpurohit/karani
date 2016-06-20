package com.ashok.hackerRank.Sorting;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Insertion Sort - Part 1
 * https://www.hackerrank.com/challenges/insertionsort1
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class InsertionSort1 {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        InsertionSort1 a = new InsertionSort1();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);

        int i = n - 1;
        int v = ar[i];

        while (i != 0) {
            if (v < ar[i - 1]) {
                ar[i] = ar[i - 1];
                i--;
                println(ar);
            } else {
                ar[i] = v;
                println(ar);
                return;
            }
        }

        ar[0] = v;
        println(ar);
    }

    private static void println(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 2);

        for (int e : ar)
            sb.append(e).append(' ');

        out.println(sb);
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
