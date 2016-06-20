package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 * problem Link: Kaybus Hiring Challenge | Cutting the birthday cake
 */

class KaybusB {

    private static PrintWriter out;
    private static InputStream in;
    private static String alice = "ALICE\n", equal = "EQUAL\n", bob = "BOB\n";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        KaybusB a = new KaybusB();
        a.solve();
        out.close();
    }

    private static void process(StringBuilder sb, int r, int a, int b) {
        if (r < a || r < b) {
            sb.append(equal);
            return;
        }

        if (r == a || r == b) {
            sb.append(alice);
            return;
        }

        int x = 0, diam = r << 1;
        int len = a > b ? a : b;
        int wid = a + b - len;
        int count = 0;

        if (len + wid + len > r) {
            sb.append(alice);
            return;
        }

        count = (r * r) / (len * len);
        /*
        while (x < diam) {
            int y = Math.min(x, diam - r - wid);
            int size = (int)(Math.sqrt(y * (diam - y)) * 2);
            count += size / len;
            x += wid;
        }
        */
        if ((count & 1) == 1) {
            sb.append(alice);
            return;
        }

        sb.append(equal);
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(6 * t);
        while (t > 0) {
            t--;
            process(sb, in.readInt(), in.readInt(), in.readInt());
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
