package com.ashok.hackerearth.Practice;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.LinkedList;

/**
 * Problem: Zeroes
 * https://www.hackerearth.com/algorithms-qualifiers-round-3/algorithm/zeroes/
 *
 * First let's think in base 10 (Decimal Number System). When do you think
 * there will be zeroes in a number (trailing zeroes)?
 * It's obvious that this number should be divisible by some power of 10
 * (number of zeroes trailing). When you multiply two numbers when do you think
 * any extra zeroes emerge after multiplication? When one more 5 and one more 2
 * are present (after removing zeroes).
 *
 * Let's take some example.
 * 100 = 10 ^ 2
 * 100 x 100 = 10000
 * 200 x 300 = 60000
 * 200 x 500 = 100000 (can you see one more zero?)
 *
 * so basically 10 is formed by pair of one 2 and one 5.
 * if there are x number of two's and y number of five's in factors of a
 * large number (in this problem very very large), then there will be
 * min(x, y) zeroes trailing in the number.
 *
 * This was case with base 10.
 * Now let's take base b whose factorization is (A ^ a) x (B ^ b) x (C ^ c)...
 *
 * now we can say that there are as many trailing zeroes as many times b is
 * factor of number.
 * Now let's say power of A in number is na, B is nb, C is nc and so on.
 * So the base b's power in number is min(na / a, nb / b, nc / c, ...)
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class Zeroes {

    private static PrintWriter out;
    private static InputStream in;
    private static LinkedList<Integer>[] factors;

    static {
        LinkedList<Integer> temp = new LinkedList<Integer>();
        factors =
                (LinkedList<Integer>[])Array.newInstance(temp.getClass(), 100001);

        for (int i = 0; i < factors.length; i++) {
            factors[i] = new LinkedList<Integer>();
        }

        int[] ar = new int[100001];
        for (int i = 1; i < ar.length; i++)
            ar[i] = i;

        for (int i = 2; i < ar.length; i++) {
            if (ar[i] != 1) {
                int value = ar[i];
                for (int j = i; j < ar.length; j += i) {
                    factors[j].add(value);
                    ar[j] /= value;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        Zeroes a = new Zeroes();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int[] ar = in.readIntArray(in.readInt());
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 2);
        int[] count = new int[100001];

        for (int i = 0; i < ar.length; i++)
            for (int e : factors[ar[i]])
                count[e]++;

        int[] base = new int[100001];

        while (q > 0) {
            q--;
            int b = in.readInt();
            for (int e : factors[b])
                base[e]++;

            int min = Integer.MAX_VALUE;
            for (int e : factors[b]) {
                min = Math.min(count[e] / base[e], min);
            }

            sb.append(min).append('\n');

            // clean up for next query to keep the array fresh
            for (int e : factors[b])
                base[e] = 0;
        }

        out.print(sb);

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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
