package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Counter Code 2015 | Degree of Dirtiness
 * https://www.hackerrank.com/contests/countercode/challenges/degree-of-dirtiness
 */

public class CounterCode15C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CounterCode15C a = new CounterCode15C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            process(in.readInt(), in.readInt());
        }
    }

    private static void process(int n, int m) {
        int dirty = (m - 1) / n, rem = (m - 1) % n;
        if (rem == 0) {
            if ((m & 1) == 1)
                out.println(1 + " " + dirty);
            else
                out.println(n + " " + dirty);
            return;
        }

        if ((n & 1) == 0 || (dirty & 1) == 0) {
            if ((m & 1) == 1)
                out.println((rem / 2 + 1) + " " + dirty);
            else
                out.println((n - rem / 2) + " " + dirty);
            return;
        }

        if ((m & 1) == 1)
            out.println(1 + (rem - 1) / 2 + " " + dirty);
        else
            out.println((n - (rem + 1) / 2) + " " + dirty);
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
