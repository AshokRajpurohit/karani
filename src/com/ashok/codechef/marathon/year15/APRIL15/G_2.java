package com.ashok.codechef.marathon.year15.APRIL15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit
 * problem Link:  http://www.codechef.com/APRIL15/problems/FRMQ
 */

public class G_2 {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] ar, sar, left, right;
    private static int x, y;


    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        G_2 a = new G_2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        ar = new int[n];
        for (int i = 0; i < n; i++) {
            ar[i] = in.readInt();
        }
        sar = sortIndex(ar);
        format();
        int m = in.readInt();
        x = in.readInt();
        y = in.readInt();
        long sum = ar[find(x, y)];

        for (int i = 1; i < m; i++) {
            sexy();
            if (x > y) {
                sum = sum + ar[find(y, x)];
            } else {
                sum = sum + ar[find(x, y)];
            }
        }

        out.println(sum);
    }

    private static int find(int x, int y) {
        int root = sar[0];
        while (true) {
            if (x > root) {
                root = right[root];
            } else if (y < root) {
                root = left[root];
            } else {
                return root;
            }
        }
    }

    private static void format() {
        left = new int[ar.length];
        right = new int[ar.length];
        int root = sar[0];

        for (int i = 0; i < ar.length; i++) {
            left[i] = -1;
            right[i] = -1;
        }

        for (int i = 1; i < ar.length; i++) {
            format(sar[i]);
        }
    }

    private static void format(int i) {
        int temp = sar[0];
        while (true) {
            if (i < temp) {
                if (left[temp] == -1) {
                    left[temp] = i;
                    return;
                }
                temp = left[temp];
            } else {
                if (right[temp] == -1) {
                    right[temp] = i;
                    return;
                }
                temp = right[temp];
            }
        }
    }

    /**
     * The function sets x and y.
     */

    private static void sexy() {
        x = (x + 7) % (ar.length - 1);
        y = (y + 11) % ar.length;
    }

    public static int[] sortIndex(int[] a) {
        int[] b = new int[a.length];
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = i;
        }
        sort(a, b, c, 0, a.length - 1);
        return c;
    }

    private static void sort(int[] a, int[] b, int[] c, int begin, int end) {
        if (begin == end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(a, b, c, begin, mid);
        sort(a, b, c, mid + 1, end);
        merge(a, b, c, begin, end);
    }

    public static void merge(int[] a, int[] b, int[] c, int begin, int end) {
        int mid = (begin + end) / 2;
        int i = begin;
        int j = mid + 1;
        int k = begin;
        while (i <= mid && j <= end) {
            if (a[c[i]] < a[c[j]]) {
                b[k] = c[j];
                j++;
            } else {
                b[k] = c[i];
                i++;
            }
            k++;
        }
        if (j <= end) {
            while (j <= end) {
                b[k] = c[j];
                k++;
                j++;
            }
        }
        if (i <= mid) {
            while (i <= mid) {
                b[k] = c[i];
                i++;
                k++;
            }
        }

        i = begin;
        while (i <= end) {
            c[i] = b[i];
            i++;
        }
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
