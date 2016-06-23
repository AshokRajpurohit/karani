package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem: Splendid Matrices
 * Link: Software AG Hiring Challenge | Splendid Matrices
 */

public class SoftwareAGA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SoftwareAGA a = new SoftwareAGA();
        a.solve();
        out.close();
    }

    private static void process(int n) {
        int dim = (1 << n);
        int half = (dim >>> 1);
        int add = half * half;
        int[][] ar = genMatrix(dim >>> 1);
        StringBuilder sb = new StringBuilder(dim << 3);

        for (int i = 0; i < half; i++) {
            for (int j = 0; j < half; j++)
                sb.append(ar[i][j]).append(' ');
            for (int j = 0; j < half; j++)
                sb.append(ar[i][j] + add).append(' ');
            sb.append('\n');
        }
        int temp = add;
        add = (add << 1);
        for (int i = 0; i < half; i++) {
            for (int j = 0; j < half; j++)
                sb.append(ar[i][j] + add).append(' ');
            for (int j = 0; j < half; j++)
                sb.append(ar[i][j] + add + temp).append(' ');
            sb.append('\n');
        }
        out.print(sb);
    }

    private static int[][] genMatrix(int n) {
        int[][] ar = new int[n][n];
        ar[0][0] = 1;
        int i = 2, add = 1;
        while (i <= n) {
            int temp = add;
            int j = (i >>> 1);
            for (int k = 0; k < j; k++) {
                for (int l = j; l < i; l++) {
                    ar[k][l] = temp + ar[k][l - j];
                }
            }

            temp = (add << 1);
            for (int k = j; k < i; k++) {
                for (int l = 0; l < j; l++) {
                    ar[k][l] = temp + ar[k - j][l];
                }
            }

            temp += add;
            for (int k = j; k < i; k++) {
                for (int l = j; l < i; l++) {
                    ar[k][l] = temp + ar[k - j][l - j];
                }
            }

            add = (add << 2);
            i = (i << 1);
        }

        return ar;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        process(in.readInt());
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
