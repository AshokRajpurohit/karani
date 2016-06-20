package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Navi and his friend
 * https://www.hackerearth.com/magnasoft-java-hiring-challenge/problems/07dad81f71c0467386e215b2d1c547c6/
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class MagnaSoftNaviFriends {

    private static PrintWriter out;
    private static InputStream in;
    private static String format = "For Day #";
    private int[] price, weight, min;
    private int n;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MagnaSoftNaviFriends a = new MagnaSoftNaviFriends();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int d = in.readInt();
        for (int j = 1; j <= d; j++) {
            int n = in.readInt();
            this.n = n;
            int[] p = new int[n], w = new int[n];

            for (int i = 0; i < n; i++) {
                p[i] = in.readInt();
                w[i] = in.readInt();
            }

            price = p;
            weight = w;
            int wmax = in.readInt(), count = in.readInt();
            out.println(format + j + ":\n" +
                    process(wmax, count));
        }
    }

    private long process(int w, int c) {
        if (c == 0)
            return -1;

        int[] rmin = new int[n];
        rmin[n - 1] = weight[n - 1];
        for (int j = n - 2; j >= 0; j--)
            rmin[j] = Math.min(weight[j], rmin[j + 1]);

        if (w < rmin[0])
            return -1;

        min = rmin;

        return process(w, c, 0, 0);
    }

    private long process(int w, int c, long p, int index) {
        if (w < 0)
            return -197;
        if (index == n || c == 0 || w < min[index])
            return p;

        return Math.max(process(w - weight[index], c - 1, p + price[index],
                index + 1), process(w, c, p, index + 1));
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
