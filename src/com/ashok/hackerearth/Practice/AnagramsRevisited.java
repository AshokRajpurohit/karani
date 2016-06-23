package com.ashok.hackerearth.Practice;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem: Anagrams Revisited
 * https://www.hackerearth.com/problem/algorithm/anagrams-revisited-26/description/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class AnagramsRevisited {

    private static PrintWriter out;
    private static InputStream in;
    private int[] ar = new int[256];
    private static int mod = 1000000007;
    private static long[] hashCode = new long[256];

    static {
        long base = 1;
        for (int i = 'a'; i <= 'z'; i++) {
            hashCode[i] = base;
            base = ((base << 5) - base) % mod;
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        AnagramsRevisited a = new AnagramsRevisited();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        int max = 1;

        while (n > 0) {
            n--;
            long code = hash(in.read());
            if (map.containsKey(code))
                map.put(code, map.get(code) + 1);
            else
                map.put(code, 1);
        }

        for (Map.Entry<Long, Integer> e : map.entrySet())
            max = Math.max(max, e.getValue());

        out.println(max);
    }

    private long hash(String s) {
        long res = 0;
        for (int i = 0; i < s.length(); i++)
            res += hashCode[s.charAt(i)];

        return res;
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
