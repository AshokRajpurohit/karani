package com.ashok.hackerRank.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link: https://www.hackerrank.com/challenges/the-quickest-way-up
 * GT stands for Graph Theory
 */

public class GTE1 {

    /**
     * mokshapad is the original name of Snakes and Ladder while
     * baari is hindi word for turn (It's my turn/ It's my baari).
     */

    private static PrintWriter out;
    private static InputStream in;
    private static int[] mokshapad, baari;
    private static boolean[] vis;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        GTE1 a = new GTE1();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            mokshapad = new int[101]; // let's save time from substraction
            baari = new int[101];
            vis = new boolean[101];
            int n = in.readInt();

            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                mokshapad[x] = in.readInt();
            }

            int m = in.readInt();

            for (int i = 0; i < m; i++) {
                int x = in.readInt();
                mokshapad[x] = in.readInt();
            }

            int res = solve(1);
            if (res >= 10000)
                out.println("-1");
            else
                out.println(res);
        }
    }

    private static int solve(int k) {
        if (k == 100)
            return 0;

        if (baari[k] != 0)
            return baari[k];

        if (vis[k])
            return 15000;

        if (mokshapad[k] != 0)
            return solve(mokshapad[k]);

        if (k >= 94)
            return 1;

        int res = 15000;

        vis[k] = true;
        for (int i = 1; i <= 6 && k + 6 <= 100; i++) {
            res = Math.min(res, solve(k + i));
        }
        res++;

        baari[k] = res;
        vis[k] = false;
        //        System.out.println("le bhai " + k + "," + res);
        return res;
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
