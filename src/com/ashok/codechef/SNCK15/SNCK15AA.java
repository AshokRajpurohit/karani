package com.ashok.codechef.SNCK15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem: Chefs and Voting for best friend
 * http://www.codechef.com/SNCK151A/problems/CHEFVOTE
 */

public class SNCK15AA {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        SNCK15AA a = new SNCK15AA();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            solve(in.readIntArray(n));
        }
    }

    private static void solve(int[] ar) {
        if (ar.length < 2) {
            out.print("-1\n");
            return;
        }
        int max = 0, count = 0;
        for (int i = 0; i < ar.length; i++)
            count += ar[i];

        if (count != ar.length) {
            out.print("-1\n");
            return;
        }

        for (int i = 0; i < ar.length; i++)
            max = Math.max(max, ar[i]);

        if (max == ar.length) {
            out.print("-1\n");
            return;
        }

        StringBuilder sb = new StringBuilder(3 * ar.length);

        for (int i = 0, j = 1; i < ar.length; i++) {
            j = i + 1;
            while (j < ar.length && ar[j] == 0)
                j++;
            if (j == ar.length) {
                j = 0;
                while (j < i && ar[j] == 0)
                    j++;
            }

            sb.append(j + 1).append(' ');
            ar[j]--;

        }
        out.println(sb);

        //        //        int[] vote = new int[ar.length];
        //
        //        for (int i = 0, j = 1; i < ar.length; i++) {
        //            if (j == ar.length)
        //                j = 0;
        //
        //            while (ar[j] == 0) {
        //                while (j < ar.length && ar[j] == 0)
        //                    j++;
        //                if (j == i)
        //                    j++;
        //
        //                if (j == ar.length)
        //                    j = 0;
        //            }
        //            //            while (i != j && i < ar.length && ar[j] > 0) {
        //            //            vote[i] = j + 1;
        //            //                i++;
        //            ar[j]--;
        //            //            }
        //            //            out.print((j + 1) + " ");
        //            sb.append((j + 1)).append(' ');
        //            //            ar[j]--;
        //        }
        //
        //        //        for (int i = 0; i < ar.length; i++)
        //        //            sb.append(vote[i]).append(' ');

        sb.append('\n');
        out.print(sb);
        //        out.println();
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
