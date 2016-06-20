package com.ashok.hackerearth.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Akash and the Assignment 1
 * https://www.hackerearth.com/problem/algorithm/akash-and-the-assignment-1-12/
 */

public class Akash_assignment {

    private static PrintWriter out;
    private static InputStream in;
    private int[][] count;
    private static int[] charint;
    private static char[] intchar;

    static {
        charint = new int[256];
        for (int i = 'a'; i <= 'z'; i++)
            charint[i] = i - 'a';

        intchar = new char[26];
        for (int i = 0; i < 26; i++)
            intchar[i] = (char)(i + 'a');
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        Akash_assignment a = new Akash_assignment();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int q = in.readInt();
        StringBuilder sb = new StringBuilder(q << 2);
        String error = "Out of range\n";
        String s = in.read(n);
        process(s);
        while (q > 0) {
            q--;
            int L = in.readInt();
            int R = in.readInt();
            int K = in.readInt();
            if (L + K > R + 1)
                sb.append(error);
            else
                sb.append(find(L - 1, R - 1, K)).append('\n');
        }
        out.print(sb);
    }


    private char find(int L, int R, int K) {

        int i = 0, sum = 0;
        if (L == 0) {
            while (sum < K) {
                sum += count[i][R];
                i++;
            }
            return intchar[i - 1];
        }

        while (sum < K) {
            sum += count[i][R] - count[i][L - 1];
            i++;
        }
        return intchar[i - 1];
    }

    private void process(String s) {
        count = new int[26][s.length()];
        count[charint[s.charAt(0)]][0]++;

        for (int i = 0; i < 26; i++) {
            for (int j = 1; j < s.length(); j++) {
                if (s.charAt(j) == intchar[i])
                    count[i][j] = count[i][j - 1] + 1;
                else
                    count[i][j] = count[i][j - 1];
            }
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

        public String read(int n) throws IOException {
            StringBuilder sb = new StringBuilder(n);
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
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
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
