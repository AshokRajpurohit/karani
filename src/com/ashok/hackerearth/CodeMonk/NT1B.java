package com.ashok.hackerearth.CodeMonk;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Chandu and his Interns
 * https://www.hackerearth.com/code-monk-number-theory-i/algorithm/chandu-and-his-interns/
 */

public class NT1B {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime = gen_prime(3163);

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        NT1B a = new NT1B();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        String yes = "YES\n", no = "NO\n";
        int n = in.readInt();
        StringBuilder sb = new StringBuilder(n << 2);
        for (int i = 0; i < n; i++) {
            if (check(in.readInt()))
                sb.append(yes);
            else
                sb.append(no);
        }
        out.print(sb);
    }

    private static boolean check(int n) {
        if (n < 6)
            return false;
        if (n % 2 == 0)
            return true;

        int root = (int)Math.sqrt(n);
        if (n == root * root)
            return !find(root);

        if (n < 3164)
            return !find(n);

        for (int i = 0; i < prime.length && prime[i] <= root; i++) {
            if (n % prime[i] == 0)
                return true;
        }
        return false;
    }

    private static boolean find(int n) {
        int i = 0, j = prime.length - 1, mid = prime.length >>> 1;

        while (i + 1 < j) {
            if (n > prime[mid]) {
                i = mid;
            } else if (n == prime[mid])
                return true;
            else
                j = mid;

            mid = (i + j) >>> 1;
        }
        return n == prime[j];
    }

    private static int[] gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int)Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i + 1; j <= n; j++) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }
        return ret;
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
