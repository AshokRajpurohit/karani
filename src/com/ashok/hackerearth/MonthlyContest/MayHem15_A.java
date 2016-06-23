package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem: Perfect Baseline
 * https://www.hackerearth.com/may-hem-15/algorithm/perfect-baseline/
 */

public class MayHem15_A {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] charint = new int[256];

    static {
        for (int i = 'a'; i <= 'z'; i++)
            charint[i] = i - 'a';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        MayHem15_A a = new MayHem15_A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int k = in.readInt();
            sb.append(solve(in.readArray(n, k))).append('\n');
        }
        out.print(sb);
    }

    private static String solve(String[] ar) {
        StringBuilder sb = new StringBuilder(ar[0].length());

        for (int i = 0; i < ar[0].length(); i++) {
            int[] temp = new int[26];
            for (int j = 0; j < ar.length; j++)
                temp[charint[ar[j].charAt(i)]]++;

            int count = (ar.length + 1) >>> 1, sum = 0;
            for (int j = 0; j < 26 && sum < count; j++) {
                sum += temp[j];
                if (sum >= count) {
                    sb.append((char)(j + 'a'));
                    break;
                }
            }
        }

        return sb.toString();
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

        public String[] readArray(int n, int k) throws IOException {
            String[] ar = new String[n];
            for (int i = 0; i < n; i++)
                ar[i] = read(k);
            return ar;
        }
    }
}
