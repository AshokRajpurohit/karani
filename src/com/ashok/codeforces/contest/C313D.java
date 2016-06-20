package com.ashok.codeforces.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:
 */

public class C313D {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C313D a = new C313D();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        if (process(in.read(), in.read()))
            out.print("YES\n");
        else
            out.print("NO\n");
    }

    private static boolean process(String a, String b) {
        if (a.length() != b.length())
            return false;

        if (equal(a, b))
            return true;

        if (a.length() % 2 == 1)
            return false;

        int mid = (a.length() - 1) >>> 1;
        //        if (process(a, 0, mid, b, 0, mid) &&
        //            process(a, mid + 1, a.length() - 1, b, mid + 1, a.length() - 1))
        //            return true;
        //
        //        if (process(a, 0, mid, b, mid + 1, a.length() - 1) &&
        //            process(a, mid + 1, a.length() - 1, b, 0, mid))
        //            return true;
        //
        //        return false;

        return (process(a, 0, mid, b, 0, mid) &&
                process(a, mid + 1, a.length() - 1, b, mid + 1,
                        a.length() - 1)) ||
            (process(a, 0, mid, b, mid + 1, a.length() - 1) &&
             process(a, mid + 1, a.length() - 1, b, 0, mid));
    }

    private static boolean process(String a, int astart, int aend, String b,
                                   int bstart, int bend) {

        if (equal(a, astart, aend, b, bstart, bend))
            return true;

        int len = aend + 1 - astart;
        if ((len & 1) == 1)
            return false;
        /*
        for (int i = astart, j = bstart; i <= aend; i++, j++)
            if (a.charAt(i) != b.charAt(j))
                return false;
*/
        int amid = (astart + aend) >>> 1;
        int bmid = (bstart + bend) >>> 1;

        //        if (process(a, astart, amid, b, bstart, bmid) &&
        //            process(a, amid + 1, aend, b, bmid + 1, bend))
        //            return true;
        //
        //        if (process(a, amid + 1, aend, b, bstart, bmid) &&
        //            process(a, astart, amid, b, bmid + 1, bend))
        //            return true;
        //
        //        return false;

        return (process(a, astart, amid, b, bstart, bmid) &&
                process(a, amid + 1, aend, b, bmid + 1, bend)) ||
            (process(a, amid + 1, aend, b, bstart, bmid) &&
             process(a, astart, amid, b, bmid + 1, bend));
    }

    private static boolean equal(String a, int astart, int aend, String b,
                                 int bstart, int bend) {
        for (int i = astart, j = bstart; i <= aend; i++, j++)
            if (a.charAt(i) != b.charAt(j))
                return false;

        return true;
    }

    private static boolean equal(String a, String b) {
        for (int i = 0; i < a.length(); i++)
            if (a.charAt(i) != b.charAt(i))
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
