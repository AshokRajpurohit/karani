package com.ashok.hackerRank.Knowlarity;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Minimum String Moves
 */

public class C {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        C a = new C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        out.println(process(in.read(), in.read()));
    }

    private static int process(String a, String b) {
        char[] ar = b.toCharArray();
        int count = 0;
        if (b.charAt(0) != a.charAt(0) &&
            b.charAt(b.length() - 1) == a.charAt(0)) {
            ar[0] = a.charAt(0);
            ar[a.length() - 1] = b.charAt(0);
            count = 1;
        }

        int i = 1;
        while (!equal(a, ar)) {
            while (i < ar.length && ar[i] == a.charAt(i))
                i++;

            if (i == ar.length)
                return Math.max(count, back(a, b));
            char c = a.charAt(i);
            int j = i + 1;
            while (j < ar.length && ar[j] != c)
                j++;
            if (j == ar.length)
                return Math.max(count, back(a, b));
            count += j - i;
            while (j > i) {
                ar[j] = ar[j - 1];
                --j;
            }
            ar[i] = c;

        }
        return Math.max(count, back(a, b));
    }

    private static int back(String a, String b) {
        char[] ar = b.toCharArray();
        int count = 0;
        if (b.charAt(a.length() - 1) != a.charAt(a.length() - 1) &&
            b.charAt(0) == a.charAt(a.length() - 1)) {
            ar[a.length() - 1] = a.charAt(a.length() - 1);
            ar[0] = b.charAt(a.length() - 1);
            count = 1;
        }

        int i = ar.length - 2;
        while (!equal(a, ar)) {
            while (i >= 0 && ar[i] == a.charAt(i))
                i--;

            if (i == -1)
                return count;
            char c = a.charAt(i);
            int j = i - 1;
            while (j != -1 && ar[j] != c)
                j--;
            if (j == -1)
                return count;

            count += i - j;
            while (j < i) {
                ar[j] = ar[j + 1];
                ++j;
            }
            ar[i] = c;
        }
        return count;
    }

    private static boolean equal(String a, char[] ar) {
        for (int i = 0; i < a.length(); i++)
            if (a.charAt(i) != ar[i])
                return false;

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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
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

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
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
