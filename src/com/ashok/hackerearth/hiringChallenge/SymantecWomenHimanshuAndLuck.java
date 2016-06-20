package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Himanshu and Luck
 * Challenge: Symantec Women Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class SymantecWomenHimanshuAndLuck {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] luckyNumbers = new long[1048575];

    static {
        for (int i = 2; i < luckyNumbers.length; i++)
            luckyNumbers[i] = lucky(i);
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SymantecWomenHimanshuAndLuck a = new SymantecWomenHimanshuAndLuck();
        a.solve();
        out.close();
    }

    private static int process(long a, long b) {
        return find(b) - find(a - 1);
    }

    private static int find(long n) {
        if (n < 4)
            return 1;

        if (n < 77777)
            return sequence(n);

        int start = 2, end = luckyNumbers.length - 1;
        int mid = (start + end) >>> 1;

        while (start != mid) {
            if (luckyNumbers[mid] > n)
                end = mid;
            else if (luckyNumbers[mid] < n)
                start = mid;
            else
                return mid;

            mid = (start + end) >>> 1;
        }

        if (luckyNumbers[mid] <= n)
            return mid;

        return start;
    }

    private static int sequence(long n) {
        int i = 2;

        while (luckyNumbers[i] < n)
            i++;

        if (luckyNumbers[i] == n)
            return i;

        return i - 1;
    }

    private static long lucky(int n) {
        int digit = 7;

        if ((n & 1) == 0)
            digit = 4;

        n = n >>> 1;
        if (n == 1)
            return digit;

        long num = luckyNumbers[n];
        return (num << 3) + (num << 1) + digit;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(process(in.readLong(), in.readLong())).append('\n');
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
