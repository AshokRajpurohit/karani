package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113
 * problem Link:
 */

public class KulizaA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        KulizaA a = new KulizaA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String dom = "Dom", brian = "Brian", tie = "Tie";
        int n = in.readInt();

        int[] dar = in.readIntArray(n);
        int[] bar = in.readIntArray(n);

        int dmax = 0;
        for (int i = 1; i < n; i++)
            dmax = Math.max(dmax, Math.abs(dar[i] - dar[i - 1]));

        int bmax = 0;
        for (int i = 1; i < n; i++)
            bmax = Math.max(bmax, Math.abs(bar[i] - bar[i - 1]));

        if (bmax > dmax) {
            out.println(brian);
            out.println(bmax);
            return;
        }

        if (dmax > bmax) {
            out.println(dom);
            out.println(dmax);
            return;
        }

        out.println(tie);
        out.println(bmax);

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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
