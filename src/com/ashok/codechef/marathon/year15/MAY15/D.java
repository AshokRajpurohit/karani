package com.ashok.codechef.marathon.year15.MAY15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit ashok1113
 *  problem Link: http://www.codechef.com/MAY15/problems/DEVSTR
 */

public class D {

    private static PrintWriter out;
    private static InputStream in;
    private static char xor = '0' ^ '1';
    private static StringBuilder sb;
    private static char[] charAr = new char[256];
    static {
        charAr['0'] = '1';
        charAr['1'] = '0';
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        D a = new D();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        sb = new StringBuilder(10000);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int k = in.readInt();
            solve(k, in.readCharArray(n));
        }
        out.print(sb);
    }

    /**
     * it's based on notion that when there are mk + n (1 < n < k, m > 1)
     * consecutive similar elements
     * we always alter the elements at the interval of k+1, but when there are
     * k+1 consecutive, we don't need to alter the last or first, we can alter
     * any middle element as the change in border element may further demand
     * change.
     * @param k
     * @param ar
     */

    private static void solve(int k, char[] ar) {
        if (k == 1) {
            solve(ar);
            return;
        }

        int i = 0, j = 0, count = 0;
        while (j < ar.length) {
            for (; j < ar.length && ar[i] == ar[j]; j++)
                ;
            j--;

            while (i < j - k) {
                count++;
                ar[i + k] = charAr[ar[i + k]];
                i += k + 1;
            }

            if (i + k == j) {
                ar[j - 1] = charAr[ar[j - 1]];
                count++;
            }
            j++;
            i = j;
        }
        sb.append(count).append('\n');
        sb.append(ar).append('\n');
    }

    /**
     * this function process only when k is one i. e. no two consecutive elements
     * are same. as there are only two kind of elements (chars '0' and '1'),
     * it compares the array first with 010101... pattern and counts the
     * discrepancy in array with the pattern.
     * in second step it compares the array with 101010... pattern and counts
     * the discrepancy.
     * it returns the count and pattern for which count is minimum.
     * @param ar
     */

    private static void solve(char[] ar) {
        int count_01 = 0;
        for (int i = 0; i < ar.length; i += 2)
            if (ar[i] == '1')
                count_01++;

        for (int i = 1; i < ar.length; i += 2)
            if (ar[i] == '0')
                count_01++;

        int count_10 = 0;
        for (int i = 1; i < ar.length; i += 2)
            if (ar[i] == '1')
                count_10++;

        for (int i = 0; i < ar.length; i += 2)
            if (ar[i] == '0')
                count_10++;

        if (count_01 > count_10) {
            sb.append(count_10).append('\n');
            sb.append(create_10(ar.length)).append('\n');
            return;
        }

        sb.append(count_01).append('\n');
        sb.append(create_01(ar.length)).append('\n');
        return;
    }

    /**
     * Generates 010101... character sequence of given length n
     * @param n
     * @return 0101... pattern of length n
     */

    private static char[] create_01(int n) {
        char[] ar = new char[n];

        for (int i = 0; i < n; i += 2)
            ar[i] = '0';

        for (int i = 1; i < n; i += 2)
            ar[i] = '1';

        return ar;
    }

    /**
     * Generates the 1010... pattern of length n
     * @param n
     * @return
     */

    private static char[] create_10(int n) {
        char[] ar = new char[n];

        for (int i = 0; i < n; i += 2)
            ar[i] = '1';

        for (int i = 1; i < n; i += 2)
            ar[i] = '0';

        return ar;
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

        public char[] readCharArray(int n) throws IOException {
            char[] ar = new char[n];
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (int i = 0; offset < bufferSize && i < n; ++offset) {
                if (Character.isValidCodePoint(buffer[offset])) {
                    ar[i] = (char)(buffer[offset]);
                    i++;
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return ar;
        }
    }
}
