package com.ashok.codechef.marathon.year16.JAN16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Cyclist Race
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

class CYCLRACE {

    private static PrintWriter out;
    private static InputStream in;
    private Cyclist[] ar;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CYCLRACE a = new CYCLRACE();
        a.solve();
        out.close();
    }

    private void format(int n) {
        ar = array(n);
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt(), q = in.readInt();
        format(n);

        StringBuilder sb = new StringBuilder(q);

        while (q > 0) {
            q--;
            if (in.readInt() == 1) {
                int t = in.readInt(), c = in.readInt() - 1, s = in.readInt();
                ar[c].update(t);
                ar[c].speed = s;
            } else {
                int t = in.readInt();
                long max = 0;
                for (int i = 0; i < n; i++) {
                    ar[i].update(t);
                    max = Math.max(max, ar[i].pos);
                }
                sb.append(max).append('\n');
            }
        }
        out.print(sb);
    }

    private static Cyclist[] array(int size) {
        Cyclist[] ar = new Cyclist[size];
        for (int i = 0; i < size; i++)
            ar[i] = new Cyclist();

        for (int i = 0; i < size; i++) {
            ar[i].pin = i;
            ar[i].sin = i;
        }

        return ar;
    }

    final static class Cyclist {
        long pos = 0L;
        int speed = 0, time = 0;
        int sin, pin; // heapS heap index and heapP heap index

        void update(int t) {
            if (t == time)
                return;

            pos += 1L * speed * (t - time);
            time = t;
        }
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public int readInt() throws IOException {
            int number = 0;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
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
            return number;
        }
    }
}
