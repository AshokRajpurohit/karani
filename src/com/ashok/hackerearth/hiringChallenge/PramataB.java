package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link: Pramata Challenge | Xsquare And Two Arrays
 */

public class PramataB {

    private static PrintWriter out;
    private static InputStream in;
    private static long[] arA, arB;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PramataB a = new PramataB();
        a.solve();
        out.close();
    }

    private static long solve(int qtype, int L, int R) {
        long res = 0;
        if (qtype == 1) {
            if (L == R)
                return arA[L] - (L > 1 ? arA[L - 2] : 0);
            res = arA[L + ((R - L) / 2) * 2] - (L > 1 ? arA[L - 2] : 0);
            res =
                    res + arB[L + 1 + 2 * ((R - L - 1) / 2)] - (L > 0 ? arB[L - 1] : 0);
            return res;
        }

        if (L == R)
            return arB[L] - (L > 1 ? arB[L - 2] : 0);

        res = arA[L + 1 + 2 * ((R - L - 1) / 2)] - (L > 0 ? arA[L - 1] : 0);
        res = res + arB[L + ((R - L) / 2) * 2] - (L > 1 ? arB[L - 2] : 0);
        return res;
    }

    private static void format() {
        for (int i = 2; i < arA.length; i += 2) {
            arA[i] = arA[i - 2] + arA[i];
            arB[i] = arB[i - 2] + arB[i];
        }

        for (int i = 3; i < arA.length; i += 2) {
            arA[i] = arA[i - 2] + arA[i];
            arB[i] = arB[i - 2] + arB[i];
        }
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int q = in.readInt();
        arA = new long[n];
        arB = new long[n];
        StringBuilder sb = new StringBuilder(q << 3);

        for (int i = 0; i < n; i++) {
            arA[i] = in.readInt();
        }

        for (int i = 0; i < n; i++) {
            arB[i] = in.readInt();
        }

        format();

        int qtype, L, R;

        while (q > 0) {
            q--;
            qtype = in.readInt();
            L = in.readInt() - 1;
            R = in.readInt() - 1;
            sb.append(solve(qtype, L, R)).append('\n');
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
