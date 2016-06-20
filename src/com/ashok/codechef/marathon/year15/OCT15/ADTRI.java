package com.ashok.codechef.marathon.year15.OCT15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Random;

/**
 * Problem: Rupsa and Equilateral Triangle
 * https://www.codechef.com/OCT15/problems/ADTRI
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class ADTRI {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[] triplet = new boolean[1000001], check;

    static {
        long t = System.currentTimeMillis();
        int n = 5000000;
        boolean[] ar = new boolean[n + 1];
        ar[2] = true;
        int i = 3;
        while (i <= n) {
            ar[i] = true;
            ++i;
            ++i;
        }


        int root = 2236;
        i = 3;
        while (i <= root) {
            while (!ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i)
                ar[j] = false;

            ++i;
            ++i;
        }

        for (i = 5; i <= n; ++i)
            if (ar[i] && ((i & 3) != 1))
                ar[i] = false;

        for (i = 0; i < 5; ++i)
            ar[i] = false;

        for (i = 5; i <= n; ++i)
            if (ar[i]) {
                for (int j = i * 2; j <= n; j += i)
                    ar[j] = true;
            }

        check = ar;
        System.out.println(System.currentTimeMillis() - t);
    }

    private static int[] gen_rand(int n) {
        Random random = new Random();
        int[] ar = new int[n];
        for (int i = 0; i < n; i++)
            ar[i] = random.nextInt(5000000) + 1;

        return ar;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        ADTRI a = new ADTRI();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        long time = System.currentTimeMillis();
        int[] input = gen_rand(1000000);
        int t = 1000000;
        StringBuilder sb = new StringBuilder(t << 2);
        String yes = "YES\n", no = "NO\n";

        while (t > 0) {
            t--;
            if (check[input[t]])
                sb.append(yes);
            else
                sb.append(no);
        }
        out.write(sb.toString(), 0, sb.length());
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
