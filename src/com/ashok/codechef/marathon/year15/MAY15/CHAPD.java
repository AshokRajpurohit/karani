package com.ashok.codechef.marathon.year15.MAY15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *  problem Link: http://www.codechef.com/MAY15/problems/CHAPD
 *
 *  for two number a and b all of their common prime factors with max power (in
 *  common) are in c = gcd(a, b).
 *  gcd of a and b contains all the common prime factors a and b.
 *  if we divide b by gcd of a and b it will still have uncommon prime factors.
 *  gcd of b and c will always have common factors only while b will always
 *  contain uncommon prime factor between a and b.
 *  we will keep dividing b by c and c as gcd of b and c.
 *  in the end if b will be product of uncommon factors only and c will be 1
 *  as now there is no common factors in b and c.
 *  if b is 1 it means there was no uncommon prime factor.
 *
 *  @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class CHAPD {

    private static PrintWriter out;
    private static InputStream in;
    private static String yes = "Yes\n", no = "No\n";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CHAPD a = new CHAPD();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            if (solve(in.readLong(), in.readLong()))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
    }

    private static boolean solve(long a, long b) {
        long temp = gcd(a, b);
        while (temp != 1 && b != 1) {
            b = b / temp;
            temp = gcd(b, temp);
        }
        if (b == 1)
            return true;
        return false;
    }

    private static long gcd(long a, long b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
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
