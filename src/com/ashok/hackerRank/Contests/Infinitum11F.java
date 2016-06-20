package com.ashok.hackerRank.Contests;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author  Ashok Rajpurohit
 * problme | Totient Until The End
 * https://www.hackerrank.com/contests/infinitum11/challenges/totient-until-the-end
 */

public class Infinitum11F {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] prime, fact, eprime;
    private static int powsum = 0;
    static {
        boolean[] ar = new boolean[1412];
        for (int i = 0; i < ar.length; i++)
            ar[i] = true;

        for (int i = 2; i < ar.length; i++) {
            if (ar[i]) {
                for (int j = i + 1; j < ar.length; j++) {
                    if (j % i == 0)
                        ar[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < ar.length; i++) {
            if (ar[i])
                count++;
        }

        prime = new int[count];
        for (int i = 2, j = 0; i < ar.length && j < count; i++) {
            if (ar[i]) {
                prime[j] = i;
                j++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        Infinitum11F a = new Infinitum11F();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;
            fact = new int[prime.length + 4];
            eprime = new int[4];
            powsum = 0;

            for (int i = 0; i < 4; i++)
                factor(in.readInt(), in.readInt());

            sb.append(count()).append('\n');
        }
        out.print(sb);
    }

    private static int count() {
        int count = 0;
        while (powsum > 0) {
            count++;

            for (int i = 0; i < prime.length; i++) {
                if (fact[i] > 0) {
                    fact[i]--;
                    factor(prime[i] - 1, 1);
                    powsum--;
                }
            }

            for (int i = 0; i < 4; i++) {
                if (eprime[i] != 0) {
                    powsum--;
                    fact[i + prime.length]--;
                    factor(eprime[i] - 1, 1);
                    if (fact[i + prime.length] == 0)
                        eprime[i] = 0;
                }
            }
        }
        return count;
    }

    private static void factor(int a, int b) {
        if (a == 1)
            return;

        int count = 0;
        for (int i = 0; i < prime.length; i++) {
            count = 0;
            while (a % prime[i] == 0) {
                count++;
                a = a / prime[i];
            }
            fact[i] += b * count;
            powsum += b * count;
        }
        if (a != 1) {
            for (int i = 0; i < 4; i++) {
                if (eprime[i] == 0 || eprime[i] == a) {
                    eprime[i] = a;
                    fact[i + prime.length] += b;
                    powsum += b;
                    return;
                }
            }
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
