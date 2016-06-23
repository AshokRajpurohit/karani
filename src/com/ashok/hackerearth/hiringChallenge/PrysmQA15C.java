package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Ashu and Prime Factors
 * https://www.hackerearth.com/prysm-qa-coding-test/problems/e4773b368150476c9de4dd9c7c37bae3/
 *
 * @author Ashok Rajpurohit ashok1113@gmail.com
 */

public class PrysmQA15C {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] count;

    static {
        count = new int[1000000];
        boolean[] ar = new boolean[1000001];
        int[] prime = gen_prime(1000000);
        for (int i = 0; i < prime.length; i++) {
            int temp = 0, v = prime[i];
            for (int j = v; j < 1000001; j += v) {
                if (!ar[j]) {
                    ar[j] = true;
                    temp++;
                }
            }
            count[prime[i]] = temp;
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PrysmQA15C a = new PrysmQA15C();
        a.solve();
        out.close();
    }

    public static int[] gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int) Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i * 2; j <= n; j += i) {
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

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 6);
        while (t > 0) {
            t--;
            sb.append(count[in.readInt()]).append('\n');
        }
        out.print(sb);
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
