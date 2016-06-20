package com.ashok.codechef.lunch.LunchTime;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Sum of palindromic numbers
 * https://www.codechef.com/LTIME28/problems/SPALNUM
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class SEPA {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] sum;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SEPA a = new SEPA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        int[] start = new int[t], end = new int[t];

        for (int i = 0; i < t; i++) {
            start[i] = in.readInt();
            end[i] = in.readInt();
        }
        int max = 0;
        for (int i = 0; i < t; i++)
            max = Math.max(max, end[i]);

        format(max);

        for (int i = 0; i < t; i++)
            sb.append(process(start[i], end[i])).append('\n');

        out.print(sb);
    }

    private static void format(int n) {
        sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (isPalin(i))
                sum[i] = sum[i - 1] + i;
            else
                sum[i] = sum[i - 1];
        }
    }

    private static int process(int start, int end) {
        return sum[end] - sum[start - 1];
    }

    private static boolean isPalin(int n) {
        if (n < 10)
            return true;

        if (n < 100)
            return n % 10 == n / 10;

        if (n < 1000)
            return n % 10 == n / 100;
        //
        //        if (n < 10000)
        //            return (n % 10 == n / 1000) && ((n / 10) % 10 == (n / 100) % 10);

        return isPalin(String.valueOf(n));
    }

    private static boolean isPalin(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--)
            if (s.charAt(i) != s.charAt(j))
                return false;
        return true;
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
