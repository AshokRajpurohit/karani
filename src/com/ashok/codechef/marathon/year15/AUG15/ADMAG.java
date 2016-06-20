package com.ashok.codechef.marathon.year15.AUG15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Aditi and Magic Trick
 * https://www.codechef.com/AUG15/problems/ADMAG
 */

public class ADMAG {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] list = new long[91];

    static {
        list[1] = 1;
        list[2] = 2;

        for (int i = 3; i < 91; i++)
            list[i] = list[i - 1] + list[i - 2] + 1;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        ADMAG a = new ADMAG();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 3);

        while (t > 0) {
            t--;
            sb.append(process(in.readLong())).append('\n');
        }
        out.print(sb);

    }

    private static int process(long n) {
        if (n < list[20]) {
            int res = 1;
            while (list[res] < n)
                res++;
            return res;
        }

        if (n > list[70]) {
            int res = 71;
            while (list[res] < n)
                res++;
            return res;
        }

        int start = 20, end = 70, mid = 45;
        while (start != mid) {
            if (list[mid] == n)
                return mid;
            else if (list[mid] > n)
                end = mid;
            else
                start = mid;

            mid = (start + end) >>> 1;
        }
        if (list[start] == n)
            return start;
        return end;
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }
    }
}
