package com.ashok.hackerearth.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:
 *    https://www.hackerearth.com/tracks/pledge-2015-medium/substring-queries/
 */

public class M1 {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        M1 a = new M1();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            String s = in.read();
            int q = in.readInt();
            for (int i = 0; i < q; i++) {
                String qstr = in.read();
                out.println(solve(s, qstr));
            }
        }
    }

    private static long solve(String s, String qstr) {
        long res = 0;
        int[] ar = new int[256];
        int[] bar = new int[256];

        for (int i = 0; i < qstr.length(); i++) {
            ar[qstr.charAt(i)]++;
        }

        int li = 0, ui = 0;
        while (true) {
            while (ui < s.length()) {
                bar[s.charAt(ui)]++;
                if (bar[s.charAt(ui)] == ar[s.charAt(ui)] && check(ar, bar)) {
                    ui++;
                    break;
                }
                ui++;
            }
            if (check(ar, bar)) {
                res = res + s.length() + 1 - ui;
                while (li < ui) {
                    bar[s.charAt(li)]--;
                    if (bar[s.charAt(li)] < ar[s.charAt(li)]) {
                        li++;
                        break;
                    }
                    li++;
                    res = res + s.length() + 1 - ui;
                }
            } else
                return res;
        }

        //        return res;
    }

    private static boolean check(int[] ar, int[] bar) {
        for (int i = 0; i < 256; i++) {
            if (ar[i] > bar[i])
                return false;
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
