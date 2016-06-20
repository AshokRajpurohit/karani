package com.ashok.codechef.marathon.year15.APRIL15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit
 * problem Link:  http://www.codechef.com/APRIL15/problems/FRMQ
 */

public class G_3 {

    private static PrintWriter out;
    private static InputStream in;
    private static int[][] ar;
    private static int x, y;


    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        G_3 a = new G_3();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        ar = new int[n + 1][];
        ar[1] = new int[n];

        for (int i = 0; i < n; i++) {
            ar[1][i] = in.readInt();
        }

        format();

        int m = in.readInt();
        x = in.readInt();
        y = in.readInt();
        long sum = find(x, y);

        for (int i = 1; i < m; i++) {
            sexy();
            if (x > y) {
                sum = sum + find(y, x);
            } else {
                sum = sum + find(x, y);
            }
        }

        out.println(sum);
    }

    private static int count(int n) {
        int res = 1, count = 0;

        while (res <= n) {
            res = res << 1;
            count++;
        }

        return count;
    }

    private static void format() {
        int i = 2, dis = 1;

        while (i <= ar[1].length) {
            dis = i >>> 1;
            ar[i] = new int[ar[1].length + 1 - i];
            for (int j = 0; j < ar[i].length; j++) {
                ar[i][j] = Math.max(ar[dis][j], ar[dis][j + dis]);
            }
            i = i << 1;
        }
    }

    private static int find(int x, int y) {
        if (x == y)
            return ar[1][x];

        int diff = y + 1 - x;
        int index = Integer.highestOneBit(diff);
        return Math.max(ar[index][x], ar[index][y + 1 - index]);
    }

    private static int count1(int diff) {
        int count = 0;
        while (diff > 1) {
            count++;
            diff = diff >> 1;
        }
        return count;
    }

    /**
     * The function sets x and y.
     */

    private static void sexy() {
        x = (x + 7) % (ar[1].length - 1);
        y = (y + 11) % ar[1].length;
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
            for (; buffer[offset] < 0x30; ++offset) {
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
            //            return number;
            return number * s;
        }
    }
}
