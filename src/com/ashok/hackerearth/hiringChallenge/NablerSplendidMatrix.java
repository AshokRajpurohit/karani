package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 * problem: Queries on Splendid Matrices
 * problem Link: Nabler Hiring Challenge
 */

public class NablerSplendidMatrix {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        NablerSplendidMatrix a = new NablerSplendidMatrix();
        a.solve();
        out.close();
    }

    private static void process(StringBuilder sb, int n, int k) {
        int comp = (1 << (n << 1));
        int i = 1, j = 1, dim = (1 << n);
        while (comp > 0) {
            while (comp > 0 && comp >= k) {
                comp = comp >>> 2;
                dim = dim >>> 1;
            }

            if (k <= (comp << 1)) {
                j += dim;
                k -= comp;
            } else if (k <= (comp * 3)) {
                i += dim;
                k -= (comp << 1);
            } else {
                i += dim;
                j += dim;
                k -= ((comp << 1) + comp);
            }
        }
        sb.append(i).append(' ').append(j).append('\n');
    }

    private static int getValue(int n, int i, int j) {
        int dim = (1 << n), res = 0;
        while (dim > 1) {
            while (dim >= i && dim >= j)
                dim = (dim >>> 1);
            if (i > dim && j > dim)
                res += (3 * dim * dim);
            else if (i > dim)
                res += ((dim * dim) << 1);
            else
                res += (dim * dim);

            if (i > dim)
                i -= dim;

            if (j > dim)
                j -= dim;
        }
        return res + 1;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 2);
        while (t > 0) {
            t--;
            int q = in.readInt();
            if (q == 1)
                process(sb, in.readInt(), in.readInt());
            else
                sb.append(getValue(in.readInt(), in.readInt(),
                        in.readInt())).append('\n');
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
