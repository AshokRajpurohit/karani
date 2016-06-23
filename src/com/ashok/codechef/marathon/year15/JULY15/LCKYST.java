package com.ashok.codechef.marathon.year15.JULY15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Na2a and lucky stone
 * http://www.codechef.com/JULY15/problems/LCKYST
 */

public class LCKYST {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] powFive;

    static {
        int t = 0;
        int r = Integer.MAX_VALUE;
        while (r > 4) {
            t++;
            r = r / 5;
        }

        powFive = new int[t + 1];
        powFive[0] = 1;

        for (int i = 1; i <= t; i++)
            powFive[i] = (powFive[i - 1] << 2) + powFive[i - 1];
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LCKYST a = new LCKYST();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            sb.append(process(in.readInt())).append('\n');
        }
        out.print(sb);
    }

    private static long process(int n) {
        long res = n;
        int shift = 0;
        while ((res & 1) == 0) {
            res = res >>> 1;
            shift++;
        }

        int index = 0;
        while (res % powFive[index] == 0)
            index++;

        index--;
        int need = index - shift;
        if (need < 0)
            need = 0;

        if ((need & 1) == 1)
            need++;

        res = n;
        return res << need;
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
