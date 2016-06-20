package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem: Counting Good Partitions of a String
 * Link: https://www.hackerrank.com/contests/101hack25/challenges/counting-good-partitions-of-a-string
 *
 * let's say string's character set is charSet {a, b, c, d, ...}.
 * and their occurance count is {p, q, r, s, ...}
 * for good partitions every partition must have distinct first char.
 * we know that the first partition has to start from the first char of the string.
 * if we fix the first character for all the partition the length is fixed (as the
 * first character of next partition is fixed).
 * we can choose a partition with first character as b (second from the set, not
 * to be confused with the second alphabet 'b') in q ways (count of b).
 * similarly a partition with first character as c (again not to be confused with
 * the third alphabet in roman script 'c') in r ways.
 * so total number of good partitions q * r * s * ...
 * Note that you can't say that the character z is not present in the string so
 * the count for z is zero. If z is not present in string then z is not part of
 * charSet of string.
 * I hope it was helpful.
 */

public class Hack25A {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] car = new int[256];

    static {
        for (int i = 'a'; i <= 'z'; i++)
            car[i] = i - 'a';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Hack25A a = new Hack25A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(solve(in.read()));
    }

    private static int solve(String s) {
        int[] ar = new int[26];
        for (int i = 0; i < s.length(); i++)
            ar[car[s.charAt(i)]]++;

        for (int i = 0; i < 26; i++)
            if (ar[i] == 0)
                ar[i] = 1;

        ar[car[s.charAt(0)]] = 1;
        int res = 1;
        for (int i = 0; i < 26; i++)
            res = (res * ar[i]) % 1000;

        return res;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
