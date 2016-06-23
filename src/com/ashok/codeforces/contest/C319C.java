package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * problem: Vasya and Petya's Game
 * http://codeforces.com/contest/577/problem/C
 *
 * @author Ashok Rajpurohit
 */

public class C319C {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime =
    { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
      71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149,
      151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227,
      229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307,
      311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389,
      397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467,
      479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571,
      577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653,
      659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751,
      757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853,
      857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947,
      953, 967, 971, 977, 983, 991, 997 };

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C319C a = new C319C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        process(in.readInt());
    }

    private static void process(int n) {
        int[] count = new int[prime.length];
        for (int i = 0; i < count.length && prime[i] <= n; i++) {
            int t = prime[i];
            while (t <= n) {
                count[i]++;
                t *= prime[i];
            }
        }
        int total = 0;
        for (int i = 0; i < count.length && prime[i] <= n; i++)
            total += count[i];

        StringBuilder sb = new StringBuilder(total << 2);
        sb.append(total).append('\n');

        for (int i = 0; i < count.length && prime[i] <= n; i++) {
            if (count[i] > 0) {
                int t = prime[i];
                while (count[i] > 0) {
                    count[i]--;
                    sb.append(t).append(' ');
                    t *= prime[i];
                }
            }
        }

        out.println(sb);
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
