package com.ashok.hackerearth.CodeHunt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * @author Harshvardhan Rajpurohit
 * problem: Clock Mania
 * https://www.hackerearth.com/code_hunt_90-2/algorithm/clock-mania/
 */

public class CH9_A {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CH9_A a = new CH9_A();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);

        while (t > 0) {
            t--;
            sb.append(getSteps(in.readInt())).append('\n');
        }
        out.print(sb);
    }

    private int getAngle(int h, int m) {
        if (h == 12)
            h = 0;

        h = h * 30 + (m >>> 1);
        m = m * 6;
        int res = h > m ? h - m : m - h;
        return Math.min(res, 360 - res);
    }

    private String getSteps(int n) {
        if (n <= 1)
            return "1";

        long a = 1, b = 1;
        int lim = 79 > n ? n : 79;
        for (int i = 2; i <= lim; i++) {
            a = a + b;
            long c = a;
            a = b;
            b = c;
        }

        BigInteger bia = new BigInteger(Long.toString(a));
        BigInteger bib = new BigInteger(Long.toString(b));
        for (int i = lim + 1; i <= n; i++) {
            BigInteger bic = bia.add(bib);
            bia = bib;
            bib = bic;
        }
        return bib.toString();
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
