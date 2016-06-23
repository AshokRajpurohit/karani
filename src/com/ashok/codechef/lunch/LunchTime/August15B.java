package com.ashok.codechef.lunch.LunchTime;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Make Palindrome
 */

public class August15B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        August15B a = new August15B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(process(in.read()));
        }
    }

    private static int process(String s) {
        boolean[] check = format(s);
        boolean[] left = formatLeft(s);
        int count = 0, half = (s.length() - 2) >>> 1;
        if (left[0])
            count++;
        for (int i = 1; i <= half; i++) {
            if (left[i + 1] && check[i - 1])
                count++;
        }

        boolean[] right = formatRight(s);
        half = (s.length() - 1);
        for (int i = half + 1; i < s.length() - 1; )
            if (right[i - 1] && check[i + 1])
                count++;

        if (right[s.length() - 1])
            count++;

        if (check[(s.length() - 1) >>> 1])
            count++;

        if (check[(s.length() - 2) >>> 1])
            count++;

        //        System.out.println(s);
        //        for (boolean e : left)
        //            System.out.print(e + "\t");
        //        System.out.println();
        //
        //        for (boolean e : right)
        //            System.out.print(e + "\t");
        //        System.out.println();
        //
        //        for (boolean e : check)
        //            System.out.print(e + "\t");
        //        System.out.println();

        return count;
    }

    private static boolean[] format(String s) {
        boolean[] res = new boolean[s.length()];
        for (int i = 0, j = s.length() - 1; i <= j; i++, j--) {
            if (s.charAt(i) == s.charAt(j)) {
                res[i] = true;
                res[j] = true;
            } else
                return res;
        }
        return res;
    }

    private static boolean[] formatLeft(String s) {
        boolean[] res = new boolean[s.length()];
        int j = (s.length() - 1) >>> 1;
        int i = (s.length() - 2) >>> 1;
        while (i >= 0 && j < s.length()) {
            if (s.charAt(i) == s.charAt(j)) {
                res[i] = true;
                res[j] = true;
            } else
                break;

            i--;
            j++;
        }
        while (i >= 0) {
            res[i] = res[i + 1];
            i--;
        }

        while (j < s.length()) {
            res[j] = res[j - 1];
            j++;
        }
        return res;
    }

    private static boolean[] formatRight(String s) {
        boolean[] res = new boolean[s.length()];
        int i = s.length() >>> 1;
        int j = (s.length() + 1) >>> 1;
        while (i >= 0 && j < s.length()) {
            if (s.charAt(i) == s.charAt(j)) {
                res[i] = true;
                res[j] = true;
            } else
                break;

            i--;
            j++;
        }

        while (i >= 0) {
            res[i] = res[i + 1];
            i--;
        }

        while (j < s.length()) {
            res[j] = res[j - 1];
            j++;
        }

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
