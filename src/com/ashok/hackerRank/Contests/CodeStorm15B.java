package com.ashok.hackerRank.Contests;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Save Quantumland
 * https://www.hackerrank.com/contests/codestorm/challenges/save-quantumland
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class CodeStorm15B {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        CodeStorm15B a = new CodeStorm15B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 3);

        while (t > 0) {
            t--;
            int n = in.readInt();
            sb.append(process(in.readIntArray(n))).append('\n');
            //            int[] br = in.readIntArray(n);
            //            boolean[] safe = new boolean[n];
            //            for (int i = 1; i < n - 1; i++) {
            //                safe[i] = br[i] == 1;
            //                safe[i - 1] = safe[i - 1] || safe[i];
            //                safe[i + 1] = safe[i + 1] || safe[i];
            //            }
            //
            //            safe[0] = br[0] == 1;
            //            safe[1] = safe[0] || safe[1];
        }
        out.print(sb);
    }

    private static int process(int[] br) {
        if (br.length == 0)
            return 0;

        if (br.length == 1)
            return 1 - br[0];

        if (br.length == 2)
            if (br[0] == 1 || br[1] == 1)
                return 0;
            else
                return 1;

        if (br.length == 3)
            if (br[1] == 1 || (br[0] == 1 && br[1] == 1))
                return 0;
            else
                return 1;

        int n = br.length;
        boolean[] safe = new boolean[n];
        for (int i = 1; i < n - 1; i++) {
            safe[i] = br[i] == 1;
            safe[i - 1] = safe[i - 1] || safe[i];
            safe[i + 1] = safe[i + 1] || safe[i];
        }

        safe[0] = br[0] == 1 || safe[0];
        safe[1] = safe[0] || safe[1];

        safe[n - 1] = safe[n - 1] || br[n - 1] == 1;
        safe[n - 2] = safe[n - 2] || safe[n - 1];

        int count = 0;
        for (int i = 0; i < n; )
            if (!safe[i]) {
                count++;
                i += 3;
                //                if (!safe[i + 1] && !safe[i + 2])
                //                    i += 3;
                //                else if (!safe[i + 1])
                //                    i += 2;
                //                else
                //                    i++
            } else
                i++;

        //        if (!safe[n - 1])
        //            count++;

        return count;
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
