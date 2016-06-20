package com.ashok.hackerearth.MonthlyContest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ( ashok1113@hotmail.com )
 * problem Link: https://www.hackerrank.com/contests/101hack23/challenges/devu-magical-girl-and-spirits
 */

public class Hack23A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Hack23A a = new Hack23A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        String yes = "She did it!";

        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = new int[n];
            boolean rev = false;
            for (int i = 0; i < n; i++) {
                ar[i] = in.readInt();
            }
            int i = 0, shakti = 0;
            for (i = 0; i < n; i++) {
                shakti = shakti + ar[i];
                if (shakti < 0)
                    break;
            }
            if (shakti < 0) {
                int min = 0;
                for (int j = 0; j <= i; j++) {
                    min = min < ar[j] ? min : ar[j];
                }
                shakti = -min - min;

                for (i = 0; i < n; i++) {
                    shakti = shakti + ar[i];
                    if (shakti < 0) {
                        break;
                    }
                }

                if (shakti >= 0) {
                    sb.append(yes).append('\n');
                } else {
                    sb.append(i + 1).append('\n');
                }
            } else {

                sb.append(yes).append('\n');
            }
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
    }
}
