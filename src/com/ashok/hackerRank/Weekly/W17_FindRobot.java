package com.ashok.hackerRank.Weekly;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Find the Robot
 * https://www.hackerrank.com/contests/w17/challenges/find-the-robot
 */

public class W17_FindRobot {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        W17_FindRobot a = new W17_FindRobot();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            process(sb, in.readInt());
        }
        out.print(sb);
    }

    private static void process(StringBuilder sb, int n) {
        int loops = (n >>> 2);
        int x = -(loops << 1);
        int y = x;
        int pos = (loops << 2);
        while (pos < n) {
            pos++;
            if (pos > n)
                break;
            x += pos;

            pos++;
            if (pos > n)
                break;
            y += pos;

            pos++;
            if (pos > n)
                break;
            x -= pos;

            pos++;
            if (pos > n)
                break;
            y -= pos;
        }
        sb.append(x).append(' ').append(y).append('\n');
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
