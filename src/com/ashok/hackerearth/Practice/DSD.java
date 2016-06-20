package com.ashok.hackerearth.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  https://www.hackerearth.com/problem/algorithm/dsd-numbers/
 */

public class DSD {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] dig_sum = new int[100];
    private static int[] ar = new int[100000];
    private static int count;
    static {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                dig_sum[10 * i + j] = i + j;
            }
        }
        for (int i = 0; i < 10; i++) {
            ar[i] = i + 1;
        }
        count = 10;
        long t1 = System.currentTimeMillis();
        for (int i = 11; i < 1000000000 && count < 100000; i++) {
            if (check(i)) {
                ar[count] = i;
                count++;
            }
        }
        long t2 = System.currentTimeMillis();
    }

    private static boolean check(int i) {
        if (i < 100) {
            return i % dig_sum[i] == 0;
        }
        int res = 0, t = i, temp = i;
        while (t > 0) {
            temp = t / 100;
            res = res + dig_sum[t - ((temp << 6) + (temp << 5) + (temp << 2))];
            t = temp;
        }
        return i % res == 0;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        DSD a = new DSD();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        while (t > 0) {
            t--;
            int l = in.readInt();
            int r = in.readInt();
            sb.append(solve(l, r)).append('\n');
        }

        out.print(sb);
    }

    private static int solve(int l, int r) {
        if (l > ar[count - 1]) {
            return harshad_count(l, r);
        }
        if (r > ar[count - 1]) {
            return harshad_count(ar[count - 1] + 1, r) + count - find(l);
        }
        return find(r) + 1 - find(l);
    }

    private static int harshad_count(int l, int r) {
        int res = 0;
        while (l <= r) {
            if (check(l))
                res++;
            l++;
        }
        return res;
    }

    private static int find(int l) {
        if (l >= ar[count - 1]) {
            return count - 1;
        }
        if (l <= 10)
            return l - 1;

        int i = 0, j = count - 1, mid = j;
        while (i < j) {
            mid = (i + j) >> 1;
            if (l == ar[mid])
                return mid;
            if (mid == i) {
                if (l == ar[j])
                    return j;
                return mid;
            }
            if (l > ar[mid])
                i = mid;
            else if (l < ar[mid])
                j = mid;
        }
        return i;
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
