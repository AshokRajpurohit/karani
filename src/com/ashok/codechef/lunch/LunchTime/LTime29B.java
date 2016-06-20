package com.ashok.codechef.lunch.LunchTime;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Fast squarer
 * https://www.codechef.com/LTIME29/problems/FSTSQ
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class LTime29B {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LTime29B a = new LTime29B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(process(in.readInt(), in.readInt())).append('\n');
        }
        out.print(sb);
    }

    private static long process(int n, int d) {
        StringBuilder sb = new StringBuilder(n << 1);
        int sq = d * d, carry = 0, temp = 0;
        for (int i = 1; i <= n; i++) {
            temp += sq;
            int res = temp + carry;
            carry = res / 10;
            sb.append(res % 10);
        }

        for (int i = 1; i < n; i++) {
            temp -= sq;
            int res = temp + carry;
            carry = res / 10;
            sb.append(res % 10);
        }

        while (carry > 0) {
            sb.append(carry % 10);
            carry /= 10;
        }

        String s = sb.reverse().toString();
        long res = 0, pow = 1;
        for (int i = 0; i < s.length(); i++) {
            res = (res + (s.charAt(i) - '0') * pow) % mod;
            pow = pow * 23 % mod;
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
    }
}
