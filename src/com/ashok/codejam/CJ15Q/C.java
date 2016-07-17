package com.ashok.codejam.CJ15Q;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 *
 */

public class C {

    private static PrintWriter out;
    private static InputStream in;
    private static String format = "Case #";
    private static int[][] ar =
    { { 1, 2, 3, 4 }, { 2, -1, 4, -3 }, { 3, -4, -1, 2 }, { 4, 3, -2, -1 } };

    public static void main(String[] args) throws IOException {
        //        OutputStream outputStream = System.out;
        //        in = System.in;
        //        out = new PrintWriter(outputStream);

        String input = "C.in", output = "C.out";
        FileInputStream fip = new FileInputStream(input);
        FileOutputStream fop = new FileOutputStream(output);
        in = fip;
        out = new PrintWriter(fop);
        C a = new C();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        String yes = "YES\n", no = "NO\n";

        for (int i = 1; i <= t; i++) {
            long l = in.readLong();
            long x = in.readLong();
            String str = in.read();
            sb.append(format).append(i).append(": ");
            if (solve(str, x))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
    }

    private static boolean solve(String eq, long x) {
        if (eq.length() == 1)
            return false;

        if (eq.length() * x < 3)
            return false;

        if (eq.length() * x == 3) {
            return eq.equalsIgnoreCase("ijk");
        }
        long i, k;
        int res = 1;
        // let's find first i
        for (i = 0; i < eq.length() * x; i++) {
            int index = eq.charAt((int)(i % eq.length()));
            if (index != '1')
                index = index + 1 - 'i';
            else
                index = 0;
            if (res < 0) {
                res = -ar[-res - 1][index];
            } else
                res = ar[res - 1][index];
            if (res == 2)
                break;
        }
        if (res != 2 || i > eq.length() - 3)
            return false;

        // let's find k now
        res = 1;
        i++;
        for (k = x * eq.length() - 1; k > i; k--) {
            int index = eq.charAt((int)(k % eq.length()));
            if (index >= 'i')
                index = index + 1 - 'i';
            else
                index = 0;
            if (res < 0) {
                res = -ar[index][-res - 1];
            } else
                res = ar[index][res - 1];
            if (res == 4)
                break;
        }
        if (res != 4)
            return false;

        // final step, let's find whether j exist.
        res = 1;
        for (; i <= k && res != 3; i++) {
            int index = eq.charAt((int)(i % eq.length()));
            if (index >= 'i')
                index = index + 1 - 'i';
            else
                index = 0;
            if (res < 0) {
                res = -ar[-res - 1][index];
            } else
                res = ar[res - 1][index];
        }
        if (res != 3 || i == k)
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
