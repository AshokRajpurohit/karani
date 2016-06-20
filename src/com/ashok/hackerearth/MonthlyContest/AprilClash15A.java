package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link: https://www.hackerearth.com/april-clash-15/algorithm/aabbaac/
 */

 /**
  * Problem Statement:
  * You are given an arrayﾠSﾠofﾠNﾠstrings numbered from 0 toﾠN-1.
  * You build string sequenceﾠTiﾠby the following rules:
  * T0ﾠ=ﾠS0
  * Tiﾠ=ﾠTi-1ﾠ+ reverse(Ti-1) +ﾠSi
  * Now please answerﾠMﾠqueries: by non-negative integerﾠxﾠoutputﾠx-thﾠcharacter
  * of theﾠTN-1ﾠinﾠ0-basedﾠindexation. It's guaranteed thatﾠx-thﾠcharacter of
  * theﾠTN-1ﾠexists.
  * Input
  * The first line containsﾠTﾠ- the number of test cases. ThenﾠTﾠtest cases follow.
  * Each test case starts with line containing 2 integers:ﾠNﾠandﾠM.
  * NextﾠNﾠlines describe arrayﾠSﾠ- one string per line. ThenﾠMlines follow
  * describing queries - one non-negative integer per line.
  * Output
  * OutputﾠTﾠlines. Each line should contain one string containing answers for
  * the corresponding test case. Don't separate answers which belong to one test
  * case by whitespaces or anything else.
  */

public class AprilClash15A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        AprilClash15A a = new AprilClash15A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int m = in.readInt();
            StringBuilder sb = new StringBuilder(m + 1);

            String[] sar = new String[n];
            for (int i = 0; i < n; i++) {
                sar[i] = in.read();
            }

            long[] ar = new long[n];
            ar[0] = sar[0].length();

            for (int i = 1; i < n; i++) {
                ar[i] = 2 * ar[i - 1] + sar[i].length();
            }

            for (int i = 0; i < m; i++) {
                long x = in.readLong();
                sb.append(solve(sar, ar, x));
            }
            out.println(sb);
            out.flush();
        }
    }

    private static char solve(String[] sar, long[] ar, long x) {
        if (x < sar[0].length())
            return sar[0].charAt((int)x);
        x++;
        // let's find location for x in array

        int i = 0;
        while (true) {
            if (x <= sar[0].length())
                return sar[0].charAt((int)x - 1);

            for (i = 0; i < ar.length && ar[i] < x; i++)
                ;
            if (x > 2 * ar[i - 1])
                return sar[i].charAt((int)(x - 2 * ar[i - 1] - 1));
            x = 2 * ar[i - 1] + 1 - x;
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
