package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  https://www.hackerearth.com/march-clash-15/algorithm/equivalent-strings-2-2/
 */

public class Mar151 {

    private static PrintWriter out;
    private static InputStream in;
    private static String yes = "yes\n", no = "no\n";
    private static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Mar151 a = new Mar151();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        sb = new StringBuilder(400000);
        int t = in.readInt();

        while (t > 0) {
            t--;
            String a = in.read();
            String b = in.read();
            int q = in.readInt();

            if (a.length() == 1) {
                while (q != 0) {
                    q--;
                    sb.append(yes);
                }
            } else {
                int[] ara = new int[a.length()];
                int[] arb = new int[b.length()];

                format(ara, a);
                format(arb, b);

                while (q != 0) {
                    q--;
                    int i = in.readInt() - 1;
                    int j = in.readInt() - 1;
                    int k = in.readInt();

                    solve(ara, arb, i, j, k);
                }
            }
        }

        out.print(sb);
    }

    private static void solve(int[] ara, int[] arb, int i, int j, int k) {
        int ai, bi;

        for (int index = 0; index < k; index++) {
            ai = ara[i + index];
            bi = arb[j + index];
            if (!((ai < i && bi < j) || (ai == bi))) {
                sb.append(no);
                return;
            }
        }

        sb.append(yes);
    }

    private static void format(int[] ar, String a) {
        int[] arch = new int[26];
        for (int i = 0; i < 26; i++) {
            arch[i] = -1;
        }

        for (int i = 0; i < a.length(); i++) {
            if (arch[a.charAt(i) - 'a'] == -1) {
                arch[a.charAt(i) - 'a'] = i;
                ar[i] = -1;
            } else {
                ar[i] = arch[a.charAt(i) - 'a'];
                arch[a.charAt(i) - 'a'] = i;
            }
        }
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
