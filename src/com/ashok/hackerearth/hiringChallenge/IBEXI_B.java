package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: Gayle and his legacy
 * Challenge: IBEXI (A Sapiens Company) Java Hiring Challenge
 * Date: 12|03|2016
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class IBEXI_B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        IBEXI_B a = new IBEXI_B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String yes = "YES\n", no = "NO\n";
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            long m = in.readInt();

            Pair[] pairs = new Pair[n];
            for (int i = 0; i < n; i++) {
                pairs[i] = new Pair(in.readInt(), in.readInt());
            }

            Arrays.sort(pairs);
            boolean res = true;

            for (int i = 0; i < n && res; i++) {
                if (pairs[i].power > m) {
                    res = false;
                    break;
                }

                m += pairs[i].gain;
            }

            if (res)
                out.print(yes);
            else
                out.print(no);
        }
    }

    final static class Pair implements Comparable<Pair> {
        int gain, power;

        Pair(int x, int y) {
            gain = x;
            power = y;
        }


        public int compareTo(Pair o) {
            return this.power - o.power;
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
