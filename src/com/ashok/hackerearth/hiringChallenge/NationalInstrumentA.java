package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem: Crazy Numbers
 */

public class NationalInstrumentA {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        NationalInstrumentA a = new NationalInstrumentA();
        a.solve();
        out.close();
    }

    private static long process(int n) {
        if (n == 1)
            return 10;

        if (n == 2)
            return 17;

        long[] ar = new long[10];
        ar[0] = 1;
        ar[1] = 1;
        ar[9] = 1;
        for (int i = 2; i < 9; i++)
            ar[i] = 2;

        long[] temp = new long[10];
        boolean but = true;
        for (int i = 2; i < n; i++) {
            temp[0] = ar[1];
            temp[9] = ar[8];
            for (int j = 1; j < 9; j++)
                temp[j] = ar[j - 1] + ar[j + 1];

            for (int j = 0; j < 10; j++)
                ar[j] = temp[j] % mod;
        }
        long res = 0;
        for (int i = 0; i < 10; i++)
            res += (res + ar[i]) % mod;

        return res;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        while (t > 0) {
            t--;
            out.println(process(in.readInt()));
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
