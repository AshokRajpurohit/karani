package com.ashok.codechef.marathon.year15.AUG15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.HashMap;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Chef and insomnia
 * https://www.codechef.com/AUG15/problems/CHINSM
 */

public class CHINSM {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CHINSM a = new CHINSM();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int k = in.readInt();
        out.println(process(in.readIntArray(n), k));
    }

    private static long process(int[] ar, int k) {
        long res = 1;
        int endHere = 1;
        int max = 0;
        for (int i = 0; i < ar.length; i++)
            max = Math.max(max, ar[i]);

        if (k > max)
            return (1L * ar.length * (ar.length + 1)) >>> 1;

        int minSofar = ar[0], maxSofar = ar[0];

        HashMap<Integer, Integer> hm =
            new HashMap<Integer, Integer>((int)Math.sqrt(ar.length));
        hm.put(ar[0], 0);

        for (int i = 1; i < ar.length; i++) {
            minSofar = Math.min(minSofar, ar[i]);
            maxSofar = Math.max(maxSofar, ar[i]);

            if (ar[i] <= k) {
                endHere++;
            } else {
                int count = (maxSofar - minSofar + ar[i] - 1) / ar[i];
                if (count < endHere) {
                    int num =
                        k < minSofar ? ar[i] + ar[i] * ((minSofar - 1) / ar[i]) +
                        k : k, badIndex = i - 1 - endHere;

                    while (num <= maxSofar) {
                        if (hm.containsKey(num))
                            badIndex = Math.max(badIndex, hm.get(num));
                        num += ar[i];
                    }
                    endHere = i - badIndex;
                } else {
                    int temp = 1;
                    int boundry = i - endHere;
                    for (int j = i - 1; j >= boundry && ar[j] % ar[i] != k;
                         j--)
                        temp++;

                    endHere = temp;
                }
            }
            res += endHere;
            hm.put(ar[i], i);
        }

        return res;
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
