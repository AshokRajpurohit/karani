package com.ashok.codechef.marathon.year15.SEP15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem:
 */

public class MGCH3D {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] x, y, z, power;
    private static double deno;
    private static int n;

    static {
        power = new int[78];
        for (int i = 1; i < 78; i++)
            power[i] = i * i * i * i;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MGCH3D a = new MGCH3D();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        n = in.readInt();
        int q = in.readInt();
        x = new int[n];
        y = new int[n];
        z = new int[n];
        deno = 1.0 * n * (n - 1);

        for (int i = 0; i < n; i++) {
            x[i] = in.readInt();
            y[i] = in.readInt();
            z[i] = in.readInt();
        }

        while (q > 0) {
            q--;
            query(in.readInt(), in.readInt(), in.readInt(), in.readInt());
        }
    }

    private static void query(int a, int b, int c, int d) {
        double res = 0.0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int num =
                        Math.abs(a * (x[i] - x[j]) + b * (y[i] - y[j]) + c *
                                 (z[i] - z[j]) + d);
                    double dd =
                        Math.sqrt(pow(x[i] - x[j]) + pow(y[i] - y[j]) + pow(z[i] -
                                                                            z[j]));

                    res += num / dd;
                }
            }

        out.println(res / deno);
    }

    private static int pow(int n) {
        if (n > 0)
            return power[n];
        else
            return power[-n];
    }

    private static int hash(int x, int y, int z) {
        return (x << 14) | (y << 7) | z;
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
