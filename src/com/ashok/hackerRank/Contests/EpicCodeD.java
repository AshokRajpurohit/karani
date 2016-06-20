package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem: White Falcon and Sequence
 * https://www.hackerrank.com/contests/epiccode/challenges/white-falcon-and-sequence
 */

public class EpicCodeD {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        EpicCodeD a = new EpicCodeD();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        long[] ar = in.readLongArray(n);
        out.println(process(ar));
    }

    private static long process(long[] ar) {
        long max = Long.MIN_VALUE;
        int n = ar.length;

        for (int i = 1; i <= n >>> 1; i++) {
            long temp = 0;
            for (int j = 0; j <= n - 2 * i; j++) {
                for (int k = j + 2 * i - 1; k >= j + i; k--) {
                    // do nothing
                }
            }
            max = Math.max(max, temp);
        }
        return max;
    }

    private static long solve(long[] ar) {
        int n = ar.length;
        long[] rev = new long[n];
        for (int i = 0, j = n - 1; i < n; i++, j--) {
            rev[i] = ar[j];
        }
        long max = 0; //Long.MIN_VALUE;
        int last = n - 2;
        for (int i = 0; i < n - 1; i++, last--) {
            long temp = 0;
            for (int k = 0; k <= (last >>> 1) && last >= 0; k++) {
                temp += rev[k] * ar[i + k];
                temp = Math.max(temp, rev[k] * ar[i + k]);
                max = Math.max(max, temp);
                max = Math.max(max, rev[k] * ar[i + k]);
            }
        }

        last = n - 2;
        for (int i = 0; i < n - 1; i++, last--) {
            long temp = 0;
            for (int k = 0; k <= (last >>> 1) && last >= 0; k++) {
                temp += ar[k] * rev[i + k];
                max = Math.max(max, temp);
                max = Math.max(max, ar[k] * rev[i + k]);
            }
        }
        return max;
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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }
}
