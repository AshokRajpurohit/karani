package com.ashok.codechef.CDMN2015;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        A a = new A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = in.readInt();

            int rb = ar[0];
            for (int i = 1; i < n && rb != 1; i++)
                rb = gcd(rb, ar[i]);
            sb.append(rb).append('\n');
            //            int a = in.readInt();
            //            if (n == 1)
            //                sb.append(a).append('\n');
            //            else {
            //                int[] ar = new int[n];
            //                ar[0] = a;
            //                int rb = a;
            //                for (int i = 1; i < n; i++)
            //                    ar[i] = in.readInt();

            //                for (int i = 1; i < n && rb != 1; i++)
            //                    rb = gcd(rb, ar[i]);
            //                sb.append(rb).append('\n');
            //            }
        }
        out.print(sb);
    }

    private static int gcd(int i, int j) {

        if (i == 0)
            return j;

        if (j == 0)
            return i;

        if (i == 1)
            return 1;

        if (j == 1)
            return 1;

        int res = 0;

        while (((i | j) & 1) == 0) {
            res++;
            i = i >> 1;
            j = j >> 1;
        }

        while ((i & 1) == 0)
            i = i >> 1;

        while ((j & 1) == 0)
            j = j >> 1;

        while ((i ^ j) != 0) {
            if (i > j) {
                i = i - j;
                while ((i & 1) == 0)
                    i = i >> 1;
            }
            if (j > i) {
                j = j - i;
                while ((j & 1) == 0)
                    j = j >> 1;
            }
        }

        return i << res;
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
