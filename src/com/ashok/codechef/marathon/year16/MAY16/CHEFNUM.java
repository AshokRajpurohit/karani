package com.ashok.codechef.marathon.year16.MAY16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and Amazingness of Numbers
 * https://www.codechef.com/MAY16/problems/CHEFNUM
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CHEFNUM {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] digits = new int[10];
    private static boolean[] map = new boolean[16];
    private static final int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHEFNUM a = new CHEFNUM();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            //            print(in.readInt(), in.readInt());
            //            printSum(in.readInt(), in.readInt());
            out.println(amazingness(in.readInt(), in.readInt()));
            //            out.flush();
        }
    }

    private static void printSum(int l, int r) {
        StringBuilder sb = new StringBuilder();
        long sum = 0;

        for (int i = 1; i < l; i++)
            sum += amazingness(i);

        for (int i = l; i <= r; i++) {
            sum += amazingness(i);
            sb.append(sum).append(", ");
        }

        out.println(sb);
        out.flush();
    }

    private static void print(int l, int r) {
        StringBuilder sb = new StringBuilder();

        for (int i = l; i <= r; i++)
            sb.append(amazingness(i)).append(", ");

        out.println(sb);
        out.flush();
    }

    private static long amazingness(int l, int r) {
        long res = 0;

        for (int i = l; i <= r; i++)
            res += amazingness(i);

        return res % mod;
    }

    private static int amazingness(int n) {
        fill(n);

        int i = 0;

        while (digits[i] == 0)
            i++;

        for (; i < 10; i++) {
            int value = 0;
            for (int j = i; j < 10; j++) {
                value ^= digits[j];
                map[value] = true;
            }
        }

        return mapValue();
    }

    private static int mapValue() {
        int res = 0;

        for (int i = 1; i < 16; i++)
            if (map[i]) {
                res += i;
                map[i] = false;
            }

        return res;
    }

    private static void fill(int n) {
        for (int i = 0; i < 10; i++)
            digits[i] = 0;

        int i = 9;

        while (i >= 0 && n > 0) {
            digits[i--] = n % 10;
            n /= 10;
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
