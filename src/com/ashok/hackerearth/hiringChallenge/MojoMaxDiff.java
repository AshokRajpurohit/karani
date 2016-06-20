package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: Maximum Difference
 * Challenge: Mojo Networks Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class MojoMaxDiff {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MojoMaxDiff a = new MojoMaxDiff();
        a.solve();
        out.close();
    }

    private static int process(int[] ar) {
        if (ar.length == 1)
            return 0;

        if (ar.length == 2)
            return Math.abs(ar[0] - ar[1]);

        Arrays.sort(ar);

        int max = 0, i = 0, j = ar.length - 1;
        boolean toggle = true;

        while (i < j) {
            max += ar[j] - ar[i];

            if (toggle)
                i++;
            else
                j--;

            toggle = !toggle;
        }

        int temp = 0;
        i = 0;
        j = ar.length - 1;
        toggle = true;

        while (i < j) {
            temp += ar[j] - ar[i];

            if (toggle)
                j--;
            else
                i++;

            toggle = !toggle;
        }

        return max > temp ? max : temp;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();

        out.println(process(in.readIntArray(n)));
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
