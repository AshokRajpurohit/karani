package com.ashok.hackerearth.CodeMonk;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Monk in the Magical Land
 * https://www.hackerearth.com/code-monk-bit-manipulation/algorithm/monk-in-the-magical-land/
 */

public class BitManAugC {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        BitManAugC a = new BitManAugC();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            int n = in.readInt();
            int m = in.readInt();
            int k = in.readInt();
            int[] x = in.readIntArray(n);
            int[] c = in.readIntArray(m);
            int[] z = in.readIntArray(m);
            sb.append(process(x, c, z, k)).append('\n');
        }
        out.print(sb);
    }

    private static int process(int[] x, int[] c, int[] z, int k) {
        int res = 0;
        boolean[] check = new boolean[c.length];
        int[] key = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < c.length; j++) {
                if (!check[j] && notCoprime(x[i], c[j])) {
                    key[i] += z[j];
                    check[j] = true;
                }
            }
        }

        int[] sort = sortIndex(key);
        check = new boolean[c.length];
        key = new int[x.length];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < c.length; j++) {
                if (!check[j] && notCoprime(x[sort[i]], c[j])) {
                    key[i] += z[j];
                    check[j] = true;
                }
            }
        }

        //        Arrays.sort(key);
        for (int i = 0; i < k; i++) {
            res += key[i];
        }

        return res;

        /*
        int[] sort = sortIndex(z);
        int res = 0;
        int count = 0;
        for (int i = 0; i < c.length && count < k; i++) {
            if(!check[i])
            for (int j = 0; j < x.length; j++)
                if (!key[j])
                if (!key[j] && notCoprime(x[j], c[sort[i]])) {
                    count++;
                    res += z[sort[i]];
                    check[i] = true;
                }
        }
        return res;
        */
    }

    private static boolean notCoprime(int a, int b) {
        if (a == 0 || b == 0)
            return true;

        return gcd(a, b) != 1;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    private static int[] sortIndex(int[] a) {
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

    private static void merge(int[] a, int[] b, int[] c, int begin, int end) {
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
