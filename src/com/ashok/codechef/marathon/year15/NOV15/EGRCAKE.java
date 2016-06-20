package com.ashok.codechef.marathon.year15.NOV15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Chef and cakes
 * https://www.codechef.com/NOV15/problems/EGRCAKE
 *
 * for the robot to visite all the cakes m and n - m should be coprime.
 * let's examine why?
 * Initial order of cakes
 * 1, 2, 3, 4, ..., m, m + 1, ..., n
 *
 * after shifting first m cakes to the end:
 * m + 1, m + 2, ..., n, 1, 2, ..., m
 *
 * The robot will visit cakes at distance of m. 1st it will mark m + 1 then
 * it will go to at index m + 1 and there the cake number is m + m + 1 (assuming
 * it's still less than n - m + 1), will mark it and then 3 * m + 1 and so on
 * untill it visit one of last m cakes.
 * if by chance it visits 1 then it will go back to 1st index and it is now
 * in infinite loop.
 *
 * let's say it visits at cake number x then it will go to first half (before
 * cake number 1) at index x.
 *
 * Conclusion: I need to work more to explain it :)
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class EGRCAKE {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        EGRCAKE a = new EGRCAKE();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String yes = "Yes\n", no = "No ";
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            int n = in.readInt(), m = in.readInt();
            int g = gcd(n, m);
            if (g == 1)
                sb.append(yes);
            else
                sb.append(no).append(n / g).append('\n');
        }
        out.print(sb);
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;

        return gcd(b, a % b);
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
