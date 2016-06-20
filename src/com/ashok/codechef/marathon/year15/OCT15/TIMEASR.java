package com.ashok.codechef.marathon.year15.OCT15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * Problem: Time measure
 * https://www.codechef.com/OCT15/problems/TIMEASR
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class TIMEASR {

    private static PrintWriter out;
    private static InputStream in;
    private static double diff = 1.0 / 120.0;
    private static double minSpeed = 5.5;
    private static double cycle = 720.0 / 11.0;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        TIMEASR a = new TIMEASR();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 6);

        while (t > 0) {
            t--;
            //            StringBuilder sb = new StringBuilder();
            solve(sb, Double.valueOf(in.read()));
            //            System.out.print(sb);
        }
        out.print(sb);
    }

    private static void solve(StringBuilder sb, double angle) {
        if (angle == 0) {
            sb.append("00:00\n");
            return;
        }

        if (angle == 180) {
            sb.append("06:00\n");
            return;
        }

        if (angle == 90) {
            sb.append("03:00\n09:00\n");
            return;
        }

        int[] ar = new int[24];
        double bangle = angle;
        for (int i = 0; i < 12 && angle < 4320; i++, bangle += 360) {
            int minute = (int)(0.5 + (bangle / 5.5));
            if (diff >= Math.abs(angle - getAngle(minute))) {
                int hour = minute / 60;
                ar[i] = (hour << 8) + minute % 60;
            }
        }

        bangle = 360 - angle;
        angle = bangle;

        for (int i = 12; i < 24 && bangle < 4320; i++, bangle += 360) {
            int minute = (int)(0.5 + (bangle / 5.5));
            if (diff >= Math.abs(angle - getAngle(minute))) {
                int hour = minute / 60;
                ar[i] = (hour << 8) + minute % 60;
            }
        }

        Arrays.sort(ar);
        int i = 0;
        while (i < 24 && ar[i] == 0)
            i++;

        if (ar[23] == 3072)
            if (360 - angle <= diff)
                sb.append("00:00\n");
            else
                return;

        if (ar[23] == 0)
            if (angle <= diff)
                sb.append("00:00\n");
            else
                return;

        while (i < 23) {
            if (ar[i] != ar[i + 1]) {
                int h = ar[i] >>> 8;
                if (h < 10)
                    sb.append(0).append(h).append(':');
                else
                    sb.append(h).append(':');

                int m = ar[i] & 63;
                if (m < 10)
                    sb.append('0');

                sb.append(m).append('\n');
            }
            i++;
        }

        i = 23;
        int h = ar[i] >>> 8;
        if (h < 12) {
            if (h < 10)
                sb.append(0).append(h).append(':');
            else
                sb.append(h).append(':');

            int m = ar[i] & 63;
            if (m < 10)
                sb.append('0');

            sb.append(m).append('\n');
        }
    }

    private static double getAngle(int minute) {
        return (5.5 * minute) % 360.0;
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
