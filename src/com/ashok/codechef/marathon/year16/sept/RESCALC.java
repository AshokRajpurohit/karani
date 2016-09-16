package com.ashok.codechef.marathon.year16.sept;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem Name: Chef and calculation
 * Link: https://www.codechef.com/SEPT16/problems/RESCALC
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class RESCALC {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] types = new int[7];
    private static int[] countToPoints = new int[7];

    static {
        countToPoints[6] = 4;
        countToPoints[5] = 2;
        countToPoints[4] = 1;
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();

        while (t > 0) {
            t--;

            int n = in.readInt();
            int[] ar = new int[n];

            for (int i = 0; i < n; i++) {
                ar[i] = in.readInt();
                ar[i] += additionals(in.readIntArray(ar[i]));
            }

            int winnner = process(ar);
            if (winnner == 0)
                out.print("chef\n");
            else if (winnner == -1)
                out.print("tie\n");
            else
                out.println(winnner + 1);
        }
    }

    private static int process(int[] ar) {
        int max = 0;

        for (int e : ar)
            max = Math.max(max, e);

        int index = -1;
        for (int i = 0; i < ar.length; i++)
            if (ar[i] == max)
                if (index == -1)
                    index = i;
                else
                    return -1;

        return index;
    }

    private static int additionals(int[] ar) {
        int[] res = countArray(ar);
        Arrays.sort(res);
        int additionals = 0, count = res.length, index = 0;
        additionals = res[index++] * countToPoints[count--];

        while (count > 3) {
            additionals += (res[index] - res[index - 1]) * countToPoints[count--];
            index++;
        }

        return additionals;
    }

    private static int[] countArray(int[] ar) {
        for (int e : ar)
            types[e]++;

        int count = 0;
        for (int i = 1; i < types.length; i++) {
            if (types[i] != 0)
                count++;
        }

        int[] res = new int[count];
        int index = 0;
        for (int i = 1; i < types.length; i++) {
            if (types[i] != 0)
                res[index++] = types[i];

            types[i] = 0;
        }

        return res;
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
