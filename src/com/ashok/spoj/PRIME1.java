package com.ashok.spoj;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  http://www.spoj.com/problems/PRIME1
 */

public class PRIME1 {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime;
    static {
        int[] ar = new int[100000];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = i + 2;
        }

        for (int i = 0; ar[i] < 317; i++) {
            if (ar[i] != 0) {
                for (int j = i + 1; j < ar.length; j++) {
                    if (ar[j] % ar[i] == 0) {
                        ar[j] = 0;
                    }
                }
            }
        }

        int count = 0;
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] != 0)
                count++;
        }

        prime = new int[count];
        for (int i = 0, j = 0; i < ar.length; i++) {
            if (ar[i] != 0) {
                prime[j] = ar[i];
                j++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PRIME1 a = new PRIME1();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int m = in.readInt();
            int n = in.readInt();
            solve(m, n);
            out.println();
        }
    }

    private static void solve(int m, int n) {
        m = m == 1 ? 2 : m;
        int[] ar = new int[n + 1 - m];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = m + i;
        }
        int j = 0;
        int lim = (int)Math.sqrt(n);
        for (; prime[j] <= lim; j++) {
            for (int i = 0; i < ar.length; i++) {
                if (ar[i] != 0 && ar[i] != prime[j] && ar[i] % prime[j] == 0) {
                    ar[i] = 0;
                }
            }
        }
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] != 0) {
                out.println(ar[i]);
            }
        }
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
