package com.ashok.spoj;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem: PALIN - The Next Palindrome
 * problem Link:  http://www.spoj.com/problems/PALIN/
 *
 */

public class PALIN {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PALIN a = new PALIN();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            solve(in.read());
        }
    }

    private static void solve(String s) {
        StringBuilder sb = new StringBuilder(s);
        int i, j, mid1, mid2;


        if (isPal(s)) {
            if (isAllNine(s)) {
                char[] ar = new char[s.length() + 1];
                ar[0] = '1';
                for (i = 1; i < s.length(); i++)
                    ar[i] = '0';
                ar[s.length()] = '1';
                out.println(ar);
                return;
            }

            if (s.length() % 2 == 1) {
                j = (s.length() - 1) >>> 1;
                i = j;
            } else {
                j = (s.length() - 1) >>> 1;
                i = j + 1;
            }

            while (s.charAt(j) == '9') {
                i++;
                j--;
            }
            sb.setCharAt(i, (char)(s.charAt(j) + 1));
            sb.setCharAt(j, (char)(s.charAt(j) + 1));

            for (int k = j + 1; k < i; k++)
                sb.setCharAt(k, '0');

            out.println(sb);
            return;
        }

        if (s.length() % 2 == 1) {
            j = (s.length() - 1) >>> 1;
            i = j;
        } else {
            j = (s.length() - 1) >>> 1;
            i = j + 1;
        }

        mid1 = j;
        mid2 = i;

        while ((s.charAt(j) == s.charAt(i))) {
            j--;
            i++;
        }

        if (s.charAt(j) > s.charAt(i)) {
            while (j >= 0) {
                sb.setCharAt(i, sb.charAt(j));
                i++;
                j--;
            }

            out.println(sb);
            return;
        }

        j = mid1;
        i = mid2;

        while (s.charAt(j) == '9') {
            j--;
            i++;
        }


        sb.setCharAt(j, (char)(s.charAt(j) + 1));
        sb.setCharAt(i, (char)(s.charAt(j) + 1));

        for (int k = j + 1; k < i; k++)
            sb.setCharAt(k, '0');

        while (j >= 0) {
            sb.setCharAt(i, sb.charAt(j));
            i++;
            j--;
        }

        out.println(sb);
        return;
    }

    private static boolean isAllNine(String s) {
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) != '9')
                return false;

        return true;
    }

    private static boolean isPal(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
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
