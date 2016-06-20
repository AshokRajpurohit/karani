package com.ashok.hackerRank.Search;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Sherlock and Array
 * https://www.hackerrank.com/challenges/sherlock-and-array
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class SherlockArray {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SherlockArray a = new SherlockArray();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String yes = "YES\n", no = "NO\n";

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            if (check(ar))
                out.print(yes);
            else
                out.print(no);
        }
    }

    private static boolean check(int[] ar) {
        if (ar.length == 1)
            return true;

        if (ar.length == 2)
            return false;

        int total = 0;
        for (int e : ar)
            total += e;

        int cur = ar[0];
        total -= ar[0];

        for (int i = 1; i < ar.length; i++) {
            total -= ar[i];

            if (cur == total)
                return true;

            cur += ar[i];
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
