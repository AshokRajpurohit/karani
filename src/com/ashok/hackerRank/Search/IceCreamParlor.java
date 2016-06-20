package com.ashok.hackerRank.Search;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: Ice Cream Parlor
 * https://www.hackerrank.com/challenges/icecream-parlor
 *
 * @author  Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class IceCreamParlor {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        IceCreamParlor a = new IceCreamParlor();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 6);

        while (t > 0) {
            t--;

            int m = in.readInt(), n = in.readInt();
            Pair[] pairs = new Pair[n];

            for (int i = 0; i < n; i++) {
                pairs[i] = new Pair(in.readInt(), i + 1);
            }

            Pair ans = process(pairs, m);
            sb.append(ans.price).append(' ').append(ans.index).append('\n');
        }

        out.print(sb);
    }

    private static Pair process(Pair[] pairs, int sum) {
        Arrays.sort(pairs);
        int start = 0, end = pairs.length - 1;
        int cur = pairs[start].price + pairs[end].price;

        while (cur != sum) {
            if (cur > sum) {
                end--;
            } else {
                start++;
            }

            cur = pairs[start].price + pairs[end].price;
        }

        int a = pairs[start].index, b = pairs[end].index;
        if (a < b) {
            return new Pair(a, b);
        }

        return new Pair(b, a);
    }

    final static class Pair implements Comparable<Pair> {
        int price, index;

        Pair(int price, int index) {
            this.price = price;
            this.index = index;
        }

        public int compareTo(Pair pair) {
            if (this.price != pair.price)
                return this.price - pair.price;

            return this.index - pair.index;
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
