package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Card Game
 * Challenge: Accolite Java Hiring Challenge
 * Date: 20160227 (YYYYMMDD)
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class AccoliteCardGame {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        AccoliteCardGame a = new AccoliteCardGame();
        a.solve();
        out.close();
    }

    private static int diff(int[] a, int[] b) {
        int count = 0;
        for (int i = 0; i < a.length; i++)
            if (a[i] != b[i])
                count++;

        return count;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 6);
        int devu = 26069, churu = 253629;
        String Devu = "Devu\n", Churu = "Churu\n";

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] s = in.readIntArray(n), p = in.readIntArray(n);
            int start = in.readInt();

            if (n < 3) {
                sb.append(Devu);
                continue;
            }

            if (start == churu) {
                sb.append(Churu);
                continue;
            }

            int diff = diff(s, p);

            if (diff == 2) {
                sb.append(Devu);
                continue;
            }

            sb.append(Churu);
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
