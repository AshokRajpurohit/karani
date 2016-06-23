package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Lauren And Inversions
 * https://www.hackerrank.com/contests/worldcupsemifinals/challenges/lauren-and-inversions
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class WorldCupSemiFinalB {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        WorldCupSemiFinalB a = new WorldCupSemiFinalB();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] ar = new int[n];
        for (int i = 0; i < n; i++)
            ar[in.readInt() - 1] = i;

        //        for (int e : ar)
        //            System.out.print(e + " ");
        //        System.out.println();

        int maxSoFar = 0, cur_min = 0, max_dif = 0, temp = 0;
        int a = 0, b = 0;
        for (int i = 0; i < n; i++) {
            if (ar[i] > maxSoFar)
                maxSoFar = ar[i];
            temp = maxSoFar - ar[i];
            if (temp > max_dif) {
                a = maxSoFar;
                b = ar[i];
                max_dif = temp;
            }
        }
        a++;
        b++;
        if (a > b)
            out.print(b + "\t" + a + "\n");
        else
            out.print(a + "\t" + b + "\n");
        //        System.out.println(a + "\t" + b + "\t" + maxSoFar);
        //        out.println((ar[a] + 1) + " " + (ar[b] + 1));
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
