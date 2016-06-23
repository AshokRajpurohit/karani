package com.ashok.codechef.marathon.year15.NOV15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Eugene and function
 * https://www.codechef.com/NOV15/problems/KFUNC
 *
 * let's say the Arithmatic Progression is:
 *      a, a + d, a + 2d, a + 3d, .., a + 8d, ..., a + (n - 1)d
 * where a is the first term and d is common difference.
 *
 * Digit Sum of any number can be any number from 1 to 9.
 * Digit Sum is additive. let's take c = a + b, then
 * Digit Sum of c = Digit Sum of (digitSum(a) + digitSum(b)).
 * Let's make it clear with one example.
 *
 * take a = 15, b = 17 and c = 32.
 * digitSum(32) = 5
 * digitSum(15) = 6
 * digitSum(17) = 8
 * digitSum(6 + 8) = digitSum(14) = 5
 *
 * so if d and 9 are coprime (d is not 3 or 9) then traversing
 * a, a + d, a + 2d, ..., a + 8d will cover all numbers 1 to 9 once.
 * Oh, I forgot to tell you one property.
 * digitSum(a + 9) = digitSum(a). So it is cycle of 9 elements.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class KFUNC {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        KFUNC a = new KFUNC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            sb.append(getSum(in.readInt(), in.readInt(), in.readLong(),
                             in.readLong())).append('\n');
        }
        out.print(sb);
    }

    private static long getSum(int a, int d, long l, long r) {
        a = a % 9 + (int)((l - 1) % 9) * (d % 9);

        return cycle(a % 9, d, r + 1 - l);
    }

    private static int digiSum(int a) {
        a = a % 9;
        if (a == 0)
            return 9;

        return a;
    }

    private static int digiSum(long a) {
        a = a % 9;
        if (a == 0)
            return 9;

        return (int)a;
    }

    private static int cycle(int a, int d) {
        if (d % 3 != 0)
            return 45;

        if (d == 9)
            return a * 9;

        int res = a;
        for (int i = 1; i < 9; i++) {
            a += d;
            res += digiSum(a);
        }

        return res;
    }

    private static long cycle(int a, int d, long n) {
        a = digiSum(a);
        d = digiSum(d);

        long res = 0;
        if (n > 8)
            res = (n / 9) * cycle(a, d);

        int m = (int)(n % 9);
        for (int i = 0; i < m; i++) {
            res += digiSum(a);
            a += d;
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
