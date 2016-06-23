package com.ashok.codechef.lunch.LunchTime;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Problem: Estimating progress
 * https://www.codechef.com/LTIME29/problems/STDPRGS
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class LTime29D {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] grade, left, right;
    private static HashMap hm = new HashMap<Long, Long>();

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        LTime29D a = new LTime29D();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        grade = in.readIntArray(n);

        int m = in.readInt();
        left = new int[m];
        right = new int[m];

        for (int i = 0; i < m; i++) {
            left[i] = in.readInt() - 1;
            right[i] = in.readInt() - 1;
        }

        StringBuilder sb = new StringBuilder(m << 3);
        for (int i = 0; i < m; i++)
            sb.append(process(left[i], right[i])).append('\n');

        out.print(sb);
    }

    private static long process(int start, int end) {
        if (start == end)
            return 0;

        if (start + 1 == end) {
            long diff = grade[start] - grade[end];
            return diff * diff;
        }

        long key = start;
        key = (key << 20) + end;

        if (hm.containsKey(key))
            return (Long)hm.get(key);

        int[] temp = new int[end - start + 1];
        for (int i = start; i <= end; i++)
            temp[i - start] = grade[i];

        //        Arrays.sort(temp);
        long res = solve(temp);

        //        for (int i = 1; i < temp.length; i++)
        //            res += 1L * (temp[i] - temp[i - 1]) * (temp[i] - temp[i - 1]);

        hm.put(key, res);

        return res;
    }

    private static long solve(int[] ar) {
        int min = ar[0], max = ar[0];
        for (int i = 1; i < ar.length; i++)
            if (max < ar[i])
                max = ar[i];
            else if (min > ar[i])
                min = ar[i];

        if (max - min < ar.length * 2) {
            int[] temp = new int[max - min + 1];
            for (int i = 0; i < ar.length; i++)
                temp[ar[i] - min]++;

            long res = 0;
            int cu = 0;
            for (int i = 1; i < temp.length; i++)
                if (temp[i] != 0) {
                    res += 1L * (i - cu) * (i - cu);
                    cu = i;
                }

            return res;
        }

        Arrays.sort(ar);
        long res = 0;
        for (int i = 1; i < ar.length; i++)
            res += 1L * (ar[i] - ar[i - 1]) * (ar[i] - ar[i - 1]);

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
