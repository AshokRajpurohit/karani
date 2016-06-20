package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chandu and Primes
 * Challenge: Spini Java Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class SpiniChanduPrime {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] count;

    static {
        boolean[] ar = new boolean[1000001];
        for (int i = 2; i < ar.length; i++)
            ar[i] = true;

        for (int i = 2; i < ar.length; i++) {
            if (ar[i]) {
                for (int j = i << 1; j < ar.length; j += i)
                    ar[j] = false;
            }
        }

        for (int i = 2, j = 10; i < ar.length; i++) {
            if (i == j)
                j *= 10;

            if (ar[i]) {
                for (int k = j + i; k + i < ar.length; k += j)
                    ar[k] = true;
            }
        }

        count = new int[1000001];
        for (int i = 2; i < ar.length; i++) {
            if (ar[i])
                count[i] = count[i - 1] + 1;
            else
                count[i] = count[i - 1];
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SpiniChanduPrime a = new SpiniChanduPrime();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 30; i++)
            sb.append(i).append(": ").append(count[i]).append('\n');

        out.print(sb);
        out.flush();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 2);

        while (q > 0) {
            q--;
            int l = in.readInt(), r = in.readInt();
            sb.append(count[r] - count[l - 1]).append('\n');
        }

        out.print(sb);
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
