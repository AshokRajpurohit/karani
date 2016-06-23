package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link: Pramata Challenge: Xsquare And Two Strings
 */

public class PramataA {

    private static PrintWriter out;
    private static InputStream in;
    private static boolean[] ar = new boolean[26];
    private static int[] charin = new int[256];

    static {
        for (int i = 'a'; i <= 'z'; i++)
            charin[i] = i - 'a';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PramataA a = new PramataA();
        a.solve();
        out.close();
    }

    private static boolean solve(String s1, String s2) {
        for (int i = 0; i < 26; i++)
            ar[i] = false;

        for (int i = 0; i < s1.length(); i++)
            ar[charin[s1.charAt(i)]] = true;

        for (int i = 0; i < s2.length(); i++) {
            if (ar[charin[s2.charAt(i)]])
                return true;
        }
        return false;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        String yes = "Yes\n", no = "No\n";
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            String s1 = in.read();
            String s2 = in.read();
            if (solve(s1, s2))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
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
