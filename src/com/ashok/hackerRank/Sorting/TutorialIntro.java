package com.ashok.hackerRank.Sorting;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Intro to Tutorial Challenges
 * https://www.hackerrank.com/challenges/tutorial-intro
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class TutorialIntro {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        TutorialIntro a = new TutorialIntro();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int v = in.readInt(), n = in.readInt();
        int[] ar = in.readIntArray(n);

        out.println(find(ar, v));
    }

    private static int find(int[] ar, int v) {
        int start = 0, end = ar.length - 1;
        int mid = (start + end) >>> 1;

        while (start != mid) {
            if (ar[mid] < v)
                start = mid;
            else if (ar[mid] > v)
                end = mid;
            else
                return mid;

            mid = (start + end) >>> 1;
        }

        if (ar[mid] == v)
            return mid;

        return end;
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
