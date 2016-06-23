package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem: Crazy Painter
 * Link: Software AG Hiring Challenge | Crazy Painter
 */

public class SoftwareAGB {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SoftwareAGB a = new SoftwareAGB();
        a.solve();
        out.close();
    }

    private static long process(long n, int[] ar) {
        long count = (n << 4) + (n << 1) - 6;
        long res = 0;
        for (int i = 0; i < 26; i++)
            res += ar[i];

        long cycle = count / 26;

        //        res = cycle * (res + (cycle - 1) * 26);
        //        res = (res >>> 1);
        res = res * cycle;
        res += (cycle * (cycle - 1) * 13);
        long rem = count % 26;
        res += (rem * cycle);
        for (int i = 0; i < rem; i++)
            res += ar[i];
        return res;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        while (t > 0) {
            t--;
            sb.append(process(in.readInt(), in.readIntArray(26))).append('\n');
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
