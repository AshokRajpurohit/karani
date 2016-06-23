package com.ashok.hackerRank.Amazon;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Permutations divisible by 8 (Coding)
 *
 * You are given an integer N. Is there a permutation of that integer's digits
 * that yields an integer divisible by 8? For example, if the number N = 123,
 * then {123, 132, 213, 231, 312, 321} are the possible permutations. 312 is
 * divisible by 8.
 *
 * Credit: Ankit Agrawal aka Mr. P
 */

public class AmazonA {

    private static PrintWriter out;
    private static InputStream in;
    static Set<String> set = new HashSet<String>();

    static {
        int num = 0;
        set.add("0");
        set.add("8");
        for (int i = 10; i < 99; i++) {
            if (i % 8 == 0) {
                int a = i / 10, b = i % 10;
                set.add(a + "" + b);
                set.add(b + "" + a);
            }
        }
        for (int i = 104; i < 999; i++) {
            if (i % 8 == 0) {
                List<String> nums = generatePermutations(i);
                for (String string : nums) {
                    set.add(string);
                }
            }
        }

    }

    private static List<String> generatePermutations(int i) {
        List<String> list = new LinkedList<String>();
        int a = i / 100;
        int b = (i % 100) / 10;
        int c = i % 10;
        StringBuilder sb = new StringBuilder();
        list.add(sb.append(a).append(b).append(c).toString());
        sb = new StringBuilder();
        list.add(sb.append(a).append(c).append(b).toString());
        sb = new StringBuilder();
        list.add(sb.append(b).append(a).append(c).toString());
        sb = new StringBuilder();
        list.add(sb.append(b).append(c).append(a).toString());
        sb = new StringBuilder();
        list.add(sb.append(c).append(a).append(b).toString());
        sb = new StringBuilder();
        list.add(sb.append(c).append(b).append(a).toString());
        return list;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        AmazonA a = new AmazonA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String yes = "YES\n", no = "NO\n";
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            if (process(in.read()))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
    }

    private static boolean process(String line) {
        if (line.length() <= 3)
            return set.contains(line);

        // let's store the count of digits
        int[] count = new int[10];
        for (int i = 0; i < line.length(); i++)
            count[line.charAt(i) - '0']++;

        // when there is no even digits, no permutation can be even and so
        // no permutation is divisible by 8.
        if (count[0] + count[2] + count[4] + count[6] + count[8] == 0)
            return false;

        char[] ar = new char[3];

        // we don't need to go further if there are three 8 or 0s or
        // if count of 8 and 0 is equal to 3 as 800, 888, 880, 000 all are
        // divisible format.

        if (count[0] + count[8] >= 3)
            return true;

        /**
         * let's take two same digits and one different. the format can be
         * AAB or ABB
         */

        for (int i = 0; i < 10; i++) {
            if (count[i] > 1) {
                for (int j = 0; j < 10; j++) {
                    if (count[j] != 0 && j != i) {
                        ar[0] = (char)('0' + i);
                        ar[1] = (char)('0' + i);
                        ar[2] = (char)('0' + j);
                        if (set.contains(String.valueOf(ar)))
                            return true;
                    }
                }
            }
        }

        /**
         * All the three digits are different. The format considered is
         * ABC
         */

        for (int i = 0; i < 8; i++) {
            if (count[i] != 0) {
                for (int j = i + 1; j < 9; j++)
                    if (count[j] != 0) {
                        for (int k = j + 1; k < 10; k++) {
                            if (k != 0) {
                                ar[0] = (char)('0' + i);
                                ar[1] = (char)('0' + j);
                                ar[2] = (char)('0' + k);
                                if (set.contains(String.valueOf(ar)))
                                    return true;
                            }
                        }
                    }
            }
        }

        /*
         * Original Function submitted on HackerRank.
         *
        for (int l = 0; l < line.length() - 2; l++) {
            char a = line.charAt(l);
            for (int m = l + 1; m < line.length() - 1; m++) {
                char b = line.charAt(m);
                for (int n = m + 1; n < line.length(); n++) {
                    char c = line.charAt(n);
                    StringBuilder sb = new StringBuilder();
                    String str = sb.append(a).append(b).append(c).toString();
                    if (set.contains(str)) {
                        return true;
                    }
                }
            }
        }
        */
        return false;
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
