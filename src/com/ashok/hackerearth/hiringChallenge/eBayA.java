package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem: Solar Power
 * Link: eBay Hiring Challenge
 */

public class eBayA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        eBayA a = new eBayA();
        a.solve();
        out.close();
    }

    private static long process(int[] ar, boolean[] leaf, long k) {
        long res = 0;
        for (int i = 1; i < leaf.length; i++) {
            if (leaf[i]) {
                int len = 0;
                int j = i;
                while (j != 0) {
                    j = ar[j];
                    len++;
                }
                int[] list = new int[len];
                int m = 0;
                j = i;
                list[0] = j;
                while (j != 0) {
                    list[m] = j;
                    m++;
                    j = ar[j];
                }

                res += getCount(list, k);
            }
        }

        return res;
    }

    private static long getCount(int[] list, long k) {
        if (list.length < 2)
            return 0;
        long count = 0;
        Arrays.sort(list);

        int i = 0, j = list.length - 1;
        while (i <= j) {
            while (i <= j && list[i] * list[j] <= k)
                i++;

            count += i;
            j--;
        }

        return count + j * (j + 1) / 2;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        long k = in.readLong();
        boolean[] leaf = new boolean[n + 1];
        for (int i = 1; i <= n; i++)
            leaf[i] = true;

        int[] ar = new int[n + 1];
        for (int i = 1; i < n; i++) {
            int par = in.readInt();
            ar[in.readInt()] = par;
            leaf[par] = false;
        }

        out.println(process(ar, leaf, k));
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
