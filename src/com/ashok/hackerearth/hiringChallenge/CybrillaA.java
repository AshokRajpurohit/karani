package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author Ashok Rajpurohit ashok1113@gmail.com
 * problem: A new Maximizing problem
 * Link: Cybrilla Hiring Challenge
 */

public class CybrillaA {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        CybrillaA a = new CybrillaA();
        a.solve();
        out.close();
    }

    /**
     * Let's analyze the problem here. We have a set of integers.
     * to get the maximum C we need maximum A and maximum B. So How to maximize
     * A. For A let's divide the set into two parts, at even index and at odd
     * index. Let's say the sum of elements at even index is Ae and at odd
     * index is Ao, so A = Ae - Ao.
     * for maximum value of A, Ae should be maximum and Ao should be minimum
     * so we can put minimum half elements in Ao and max half elements in Ae.
     * if array size is odd then we know there are more elements at odd index
     * than at even index.
     * <p>
     * Now let's focus on B. as the question says we can take any sign for
     * an element. so let's make all the element positive and then add those
     * to make B.
     * B = Sum of all element's absolute value.
     * now let's think again for a moment. what is A is negative?
     * yes, A can be negative, let's take an example, the array is
     * {5, 5, 5, 5, 5}
     * A for this array is -5 so in that case we will make B negative.
     * then we can perform multiplication or we can make A negative just
     * to not to think about B.
     *
     * @param ar
     * @return long result
     */

    private static long process(int[] ar) {
        Arrays.sort(ar);
        int half = (ar.length + 1) >>> 1;
        long a = 0;
        for (int i = half; i < ar.length; i++)
            a += ar[i];

        for (int i = 0; i < half; i++)
            a -= ar[i];

        long b = 0;
        for (int i = 0; i < ar.length; i++)
            b += Math.abs(ar[i]);

        if (a < 0)
            a = -a;

        a = a % mod;
        b = b % mod;
        return (a * b) % mod;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 10);
        while (t > 0) {
            t--;
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            sb.append(process(ar)).append('\n');
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
