package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Roy and Wobbly Numbers
 * Roy is looking for Wobbly Numbers.
 * <p>
 * An N-length wobbly number is of the form "ababababab..." and so on of length N,
 * where a != b.
 * <p>
 * A 3-length wobbly number would be of form "aba".
 * Eg: 101, 121, 131, 252, 646 etc
 * But 111, 222, 999 etc are not 3-length wobbly number, because here a != b
 * condition is not satisfied.
 * Also 010 is not a 3-length wobbly number because it has preceding 0. So 010
 * equals 10 and 10 is not a 3-length wobbly number.
 * <p>
 * A 4-length wobbly number would be of form "abab".
 * Eg: 2323, 3232, 9090, 1414 etc
 * <p>
 * Similarly we can form a list of N-length wobbly numbers.
 * <p>
 * Now your task is to find Kth wobbly number from a lexicographically sorted list
 * of N-length wobbly numbers. If the number does not exist print -1 else print
 * the Kth wobbly number. See the sample test case and explanation for more clarity.
 * <p>
 * Input:
 * First line contains T - number of test cases
 * Each of the next T lines contains two space separated integers - N and K.
 * <p>
 * Output:
 * For each test case print the required output in a new line.
 * <p>
 * Constraints:
 * 1 ? T ? 100
 * 3 ? N ? 1000
 * 1 ? K ? 100
 */

/**
 * @author: Ashok Rajpurohit
 * problem Link: https://www.hackerearth.com/zopper-hiring-challenge/problems/8ef87146cd0f997fe14a12ff8fad495c/
 */

public class ZopperA {

    private static PrintWriter out;
    private static InputStream in;
    private static char[] ar =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        ZopperA a = new ZopperA();
        a.solve();
        out.close();
    }

    private static void solve(int n, int k) {
        if (k == 81) {
            formString(n, 9, 8);
            return;
        }
        if (k % 9 == 0) {
            formString(n, k / 9, 9);
            return;
        }
        int a = 1 + k / 9;
        int b = k % 9;
        if (b <= a)
            b--;
        formString(n, a, b);
    }

    private static void formString(int n, int a, int b) {
        StringBuilder sb = new StringBuilder(n + 1);
        boolean extra = n % 2 == 1;
        n = n >> 1;
        while (n > 0) {
            sb.append(ar[a]).append(ar[b]);
            n--;
        }
        if (extra)
            sb.append(ar[a]);
        out.println(sb);
        out.flush();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            int n = in.readInt();
            int k = in.readInt();
            if ((n >= 2 && k > 81) || (n == 1 && k > 9))
                out.println("-1");
            else if (n == 1 && k <= 9)
                out.println(k);
            else
                solve(n, k);
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
    }
}
