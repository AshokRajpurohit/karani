package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:
 */

public class April15EasyD {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[] ar = new boolean[100];
    private static int[] temp = new int[100], dig_sum = new int[100];
    static {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                dig_sum[10 * i + j] = i + j;
            }
        }
        for (int i = 1; i < 100; i++) {
            ar[i] = format(i);
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        April15EasyD a = new April15EasyD();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        String yes = "YES\n", no = "NO\n";

        while (t > 0) {
            t--;
            int n = in.readInt();
            if (solve(n))
                sb.append(yes);
            else
                sb.append(no);
        }

        out.print(sb);
    }

    private static boolean solve(long n) {
        long l = n;

        while (true) {
            if (l < 100) {
                return ar[(int)l];
            }

            l = l * l;
            n = 0;
            while (l > 0) {
                n = n + dig_sum[(int)(l % 100)];
                l = l / 100;
            }
            l = n;
        }
    }

    private static boolean format(long n) {
        for (int i = 0; i < 100; i++) {
            temp[i] = 0;
        }
        long l = n;

        while (true) {
            if (l == 1 || l == 4)
                return true;
            if (l < 100) {
                if (temp[(int)l] == 1)
                    return false;
                temp[(int)l] = 1;
            }

            l = l * l;
            n = 0;
            while (l > 0) {
                n = n + dig_sum[(int)(l % 100)];
                l = l / 100;
            }
            l = n;
        }
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
