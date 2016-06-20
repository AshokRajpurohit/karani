package com.ashok.codechef.marathon.year15.MARCH15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.HashMap;

/**
 * @author  Ashok Rajpurohit
 * prblme Link: http://www.codechef.com/MARCH15/problems/QCHEF
 */
public class C_V4 {

    private static PrintWriter out;
    private static InputStream in;
    //    private static int[][] lr; // to save the calculated values
    //    private static boolean[][] chk; // to save the status of visited l,r
    // t_ar total sub_strings and i_ar sub_strings including that elements
    private static int[] ar, t_ar, i_ar;
    private static StringBuilder sb;
    private static boolean good;
    private static int k;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        C_V4 a = new C_V4();
        a.solve();
        out.print(sb);
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        sb = new StringBuilder();

        int n = in.readInt();
        int m = in.readInt();
        k = in.readInt();
        ar = new int[n];

        for (int i = 0; i < n; i++) {
            ar[i] = in.readInt();
        }

        if (n <= 1) {
            for (int i = 0; i < k; i++) {
                int l = in.readInt();
                int r = in.readInt();
                sb.append("0\n");
            }
        } else {

        }
    }

    private static void format_tar() {
        int i = 1;
        t_ar[0] = 1;
        int count_0 = 0, count_1 = 0, mcount = 1, l_index = 0;
        if (ar[0] == 0)
            count_0 = 1;
        else
            count_1 = 1;
        while (i < ar.length) {
            if (ar[i] == 0) {
                count_0++;
                mcount = mcount < count_0 ? count_0 : mcount;
            }
            if (mcount > k) {
                while (ar[l_index] != ar[i]) {
                    if (ar[l_index] == 0)
                        count_0--;
                    else
                        count_1--;
                    l_index++;
                }
                if (ar[l_index] == 0)
                    count_0--;
                else
                    count_1--;
                mcount--;
            }
            t_ar[i] = t_ar[i - 1] + i - l_index + 1;
            i++;
        }
    }

    private static void format_iar() {

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
