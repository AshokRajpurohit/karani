package com.ashok.hackerearth.Contest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: Kevin doesn't like his array
 * Link: https://www.hackerearth.com/october-clash-15/algorithm/kevin-doesnt-like-his-array/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class OCT15A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        OCT15A a = new OCT15A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = in.readIntArray(n);

        if (notPossible(ar)) {
            out.print("-1\n");
            return;
        }

        int count = 0;
        for (int i = 1; i < n; i++)
            if (ar[i] == ar[i - 1])
                count++;

        count = (count + 1) >>> 1;
        out.println(count);
    }

    private static boolean notPossible(int[] ar) {
        int[] copy = new int[ar.length];
        for (int i = 0; i < ar.length; i++)
            copy[i] = ar[i];

        Arrays.sort(copy);
        for (int i = 1; i < ar.length; i++)
            if (ar[i] == ar[i - 1]) {
                int t = 1;
                while (i < ar.length && ar[i] == ar[i - 1]) {
                    t++;
                    i++;
                }

                if (t > (ar.length + 1) >>> 1)
                    return true;
            }
        return false;
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
