package com.ashok.hackerRank.NumberTheory;

import com.ashok.lang.dsa.RandomStrings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Manasa loves Maths
 * Link: https://www.hackerrank.com/challenges/manasa-loves-maths
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ManasaMaths {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static boolean[][][] map3 = new boolean[10][10][10];
    private static boolean[][] map2 = new boolean[10][10];
    private static int[] charToDigits = new int[256];

    static {
        Arrays.fill(charToDigits, -1);

        for (int i = '0'; i <= '9'; i++)
            charToDigits[i] = i - '0';

        map3[0][0][0] = true;
        map2[0][0] = true;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    int num = (10 * i + j) * 10 + k;
                    boolean res = (num & 7) == 0;

                    if (!res)
                        continue;

                    map3[i][j][k] = res;
                    map3[i][k][j] = res;
                    map3[j][i][k] = res;
                    map3[j][k][i] = res;
                    map3[k][i][j] = res;
                    map3[k][j][i] = res;
                }

                int num = 10 * i + j;
                boolean res = (num & 7) == 0;

                if (!res)
                    continue;

                map2[i][j] = res;
                map2[j][i] = res;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        test(in.readInt(), in.readInt());
        ManasaMaths a = new ManasaMaths();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        String yes = "YES\n", no = "NO\n";

        while (t > 0) {
            t--;

            if (process(in.read()))
                sb.append(yes);
            else
                sb.append(no);
        }

        out.print(sb);
    }

    private static void test(int testCount, int size) throws IOException {
        RandomStrings randomStrings = new RandomStrings();

        while (testCount > 0) {
//            testCount--;

            String s = randomStrings.nextString123(in.readInt());
            System.out.println(s.length());

            long time = System.currentTimeMillis();
            process(s);
            System.out.println(System.currentTimeMillis() - time);
        }
    }

    private static boolean process(String s) {
        char[] digits = formArray(s);

        if (allOdds(digits))
            return false;

        if (digits.length == 1)
            return (charToDigits[digits[0]] & 7) == 0;

        if (digits.length == 2)
            return map2[charToDigits[digits[0]]][charToDigits[digits[1]]];

        for (int i = 0; i < digits.length; i++)
            for (int j = i + 1; j < digits.length; j++)
                for (int k = j + 1; k < digits.length; k++)
                    if (check(digits[i], digits[j], digits[k]))
                        return true;

        return false;
    }

    private static boolean allOdds(char[] ar) {
        for (char e : ar) {
            if ((charToDigits[e] & 1) == 0)
                return false;
        }

        return true;
    }

    private static char[] formArray(String s) {
        char[] ar = s.toCharArray();
        int[] map = new int[256];

        for (char e : ar)
            map[e]++;

        int count = 0;
        for (int i = '0'; i <= '9'; i++)
            if (map[i] > 3) {
                count += 3;
                map[i] = 3;
            } else
                count += map[i];

        char[] res = new char[count];
        for (int i = 0, j = '0'; i < count && j <= '9'; j++) {
            while (map[j] != 0) {
                res[i++] = (char) j;
                map[j]--;
            }
        }

        return res;
    }

    private static boolean check(char a, char b, char c) {
        return map3[charToDigits[a]][charToDigits[b]][charToDigits[c]];
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
