package com.ashok.hackerearth.hiringChallenge;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Daisy and the Password
 * Challenge: Lendingkart Java Hiring Challenge
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class LendingKartB {

    private static final String possible = "Possible\n", impossible =
            "Impossible\n";
    private static PrintWriter out;
    private static InputStream in;
    private static int[] hashS = new int[256], hashP = new int[256], map =
            new int[256];

    static {
        for (int i = 'a'; i <= 'z'; i++)
            map[i] = i - 'a';

        for (int i = 'A'; i <= 'Z'; i++)
            map[i] = i - 'A';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        LendingKartB a = new LendingKartB();
        a.solve();
        out.close();
    }

    private static boolean process(String s, String p) {
        if (p.length() != (s.length() << 1))
            return false;

        if (!preprocess(s, p))
            return false;

        int start = 0, end = 0;
        while (start < s.length() && s.charAt(start) == p.charAt(start))
            start++;

        for (int i = start, j = 0; j < s.length(); i++, j++)
            if (s.charAt(j) != p.charAt(i))
                return false;

        for (int i = start + s.length(); i < p.length() && start < s.length();
             i++, start++)
            if (s.charAt(start) != p.charAt(i))
                return false;

        //        int i = p.indexOf(s);
        //
        //        if (i == -1)
        //            return false;
        //
        //        for (int j = 0, k = 0; j < s.length(); j++, k++) {
        //            if (k == i)
        //                k += s.length();
        //
        //            if (s.charAt(j) != p.charAt(k))
        //                return false;
        //        }

        return true;
    }

    private static boolean preprocess(String s, String p) {
        fill(s, p);
        boolean res = true;

        for (int i = 0; i < p.length() && res; i++)
            if (hashP[p.charAt(i)] != (hashS[p.charAt(i)] << 1))
                res = false;

        clear(s, p);
        return res;
    }

    private static void fill(String s, String p) {
        for (int i = 0; i < s.length(); i++)
            hashS[s.charAt(i)]++;

        for (int i = 0; i < p.length(); i++)
            hashP[p.charAt(i)]++;
    }

    private static void clear(String s, String p) {
        for (int i = 0; i < s.length(); i++)
            hashS[s.charAt(i)] = 0;

        for (int i = 0; i < p.length(); i++)
            hashP[p.charAt(i)] = 0;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 11);

        while (t > 0) {
            t--;

            if (process(in.read(), in.read()))
                sb.append(possible);
            else
                sb.append(impossible);
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
