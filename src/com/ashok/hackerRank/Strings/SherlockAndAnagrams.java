package com.ashok.hackerRank.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Sherlock and Anagrams
 * Link: https://www.hackerrank.com/challenges/sherlock-and-anagrams
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class SherlockAndAnagrams {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] left = new int[256], right = new int[256],
            charIndex = new int[256];

    static {
        for (int i = 'b'; i <= 'z'; i++)
            charIndex[i] = i - 'a';
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        long time = System.currentTimeMillis();

        while (t > 0) {
            t--;

            out.println(process(in.read().toCharArray()));
        }

        time = System.currentTimeMillis() - time;
        out.println("time: " + time + " ms");
    }

    private static int process(char[] ar) {
        if (ar.length <= 26 && checkForUniqueChars(ar))
            return 0;

        int count = 0;
        int[][] map = map(ar);

        for (int i = 0; i < ar.length; i++) {
            clear(left);
            for (int j = i; j < ar.length; j++) {
                int len = j + 1 - i;
                if (i + len >= ar.length)
                    break;

                left[ar[j]]++;
                int k = i + 1;
                populateRight(k, k + len - 1, map);
                if (equals())
                    count++;

                k++;
                for (; k + len <= ar.length; k++) {
                    right[ar[k - 1]]--;
                    right[ar[k + len - 1]]++;

                    if (equals())
                        count++;
                }
            }
        }

        return count;
    }

    private static void populateRight(int start, int end, int[][] map) {
        clear(right);
        for (int i = 'a'; i <= 'z'; i++)
            right[i] = getSum(map[i - 'a'], start, end);
    }

    private static boolean equals() {
        for (int i = 'a'; i <= 'z'; i++)
            if (left[i] != right[i])
                return false;

        return true;
    }

    private static int getSum(int[] sum, int start, int end) {
        return getSum(sum, end) - getSum(sum, start - 1);
    }

    private static int getSum(int[] sum, int index) {
        if (index < 0)
            return 0;

        return sum[index];
    }

    private static int[][] map(char[] ar) {
        int[][] map = new int[26][ar.length];
        map[charIndex[ar[0]]][0] = 1;

        for (int i = 1; i < ar.length; i++) {
            for (int j = 0; j < 26; j++)
                map[j][i] = map[j][i - 1];

            map[charIndex[ar[i]]][i]++;
        }

        return map;
    }

    private static void clear(int[] ar) {
        for (int i = 'a'; i <= 'z'; i++)
            ar[i] = 0;
    }

    private static boolean checkForUniqueChars(char[] ar) {
        clear(left);
        for (char c : ar)
            left[c]++;

        for (char c : ar)
            if (left[c] > 1)
                return false;

        return true;
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
