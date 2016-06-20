package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Lovely Couple
 * Challenge: TehcMojo Java Hiring Challenge Feb 16
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class TechMojoLovelyCouple {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] factors = new int[1000001];
    private static boolean[] primes;

    static {
        boolean[] check = new boolean[1001];
        for (int i = 2; i < check.length; i++)
            check[i] = true;

        for (int i = 2; i < check.length; i++) {
            if (!check[i])
                continue;

            for (int j = i << 1; j < check.length; j += i)
                check[j] = false;

            for (int j = i; j < factors.length; j += i) {
                factors[j]++;
            }
        }

        primes = check;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        TechMojoLovelyCouple a = new TechMojoLovelyCouple();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        String yes = "Yes\n", no = "No\n";

        while (t > 0) {
            t--;
            if (primes[factors[in.readInt() * in.readInt()]])
                sb.append(yes);
            else
                sb.append(no);
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
    }
}
