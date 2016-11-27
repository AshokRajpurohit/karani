package com.ashok.hackerearth.marathon.nov16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Fredo is in a Hurry
 * Link: https://www.hackerearth.com/november-circuits/algorithm/fredo-is-in-a-hurry/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FredoInHurry {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            sb.append(process(in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static int process(int floors) {
        if (floors == 1)
            return 1;

        int byWalk = getByWalk(floors);
        int timeByWalk = calculateTimeByWalk(byWalk);

        return floors - byWalk + Math.max(timeByWalk, floors - byWalk);
    }

    private static int getByWalk(int floors) {
        int start = 1, end = (int) Math.sqrt(floors * 2);
        int mid = (start + end) >>> 1;

        while (start != mid) {
            int time = calculateTimeByWalk(mid);

            if (time < floors - mid)
                start = mid;
            else if (time > floors - mid)
                end = mid;
            else
                return mid;

            mid = (start + end) >>> 1;
        }

        int time = calculateTimeByWalk(mid);
        return floors - mid >= time ? mid : end;
    }

    private static int calculateTimeByWalk(int floors) {
        return floors * (floors + 1) / 2;
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
