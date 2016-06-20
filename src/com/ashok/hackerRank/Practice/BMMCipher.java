package com.ashok.hackerRank.Practice;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problem Link: HackerRank | Algorithms | Bit Manipulation | Cipher
 */

public class BMMCipher {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar = new int[256];
    private static char[] dig = new char[2];
    static {
        ar['0'] = 0;
        ar['1'] = 1;
        dig[0] = '0';
        dig[1] = '1';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        BMMCipher a = new BMMCipher();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int k = in.readInt();
        String s = in.read(n);
        char[] sb = new char[n];
        sb[0] = s.charAt(0);
        int xor_sum = 0;
        for (int i = 1; i < k && i < n; i++) {
            sb[i] = dig[ar[s.charAt(i)] ^ ar[s.charAt(i - 1)]];
        }

        for (int i = 0; i < k && i < n; i++)
            xor_sum ^= ar[sb[i]];

        for (int i = k, j = 0; i < n; i++, j++) {
            sb[i] = dig[xor_sum ^ ar[s.charAt(i)] ^ ar[sb[j]]];
            xor_sum ^= ar[sb[i]] ^ ar[sb[j]];
        }
        out.println(sb);
        out.flush();
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
