package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem: DigIT
 * https://www.hackerearth.com/june-clash-15/algorithm/digit/
 */

public class JuneClash15B {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] digiSum;

    static {
        digiSum = new int[1000];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    digiSum[i * 100 + j * 10 + k] = i + j + k;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        JuneClash15B a = new JuneClash15B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        System.out.println(solve(in.readLong(), in.readLong(), in.readInt(),
                                 in.readInt(), in.readLong()));
    }

    private static long solve(long a, long b, int x, int y, long k) {
        if (y == 0)
            return 0;
        if (k % 9 == 0 && y < 9)
            return 0;
        if (k % 3 == 0 && y < 3)
            return 0;
        String mb = String.valueOf(b);
        int max_sum = mb.charAt(0) - '0' + 9 * (mb.length() - 1);
        if (y >= max_sum) {
            if (x <= 1)
                return b / k - (a - 1) / k;
            long start = a % k == 0 ? a : k * (1 + a / k);
            long count = 0;
            while (start <= b) {
                if (dsum(start) >= x)
                    count++;
                start += k;
            }
            return count;
        }

        if (x <= 1) {
            long start = a % k == 0 ? a : k * (1 + a / k);
            long count = 0;
            while (start <= b) {
                if (dsum(start) <= y)
                    count++;
                start += k;
            }
            return count;
        }

        long start = a % k == 0 ? a : k * (1 + a / k);
        long count = 0;
        while (start <= b) {
            if (check(start, x, y))
                count++;
            start += k;
        }
        return count;
    }

    private static boolean checkX(long n, int x) {
        if (dsum(n) < x)
            return false;
        return true;
    }

    private static boolean check(long n, int x, int y) {
        int temp = dsum(n);
        if (temp < x || temp > y)
            return false;
        return true;
    }

    private static int dsum(long a) {
        int res = 0;
        while (a > 0) {
            res += digiSum[(int)(a % 1000)];
            a = a / 1000;
        }
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }
    }
}
