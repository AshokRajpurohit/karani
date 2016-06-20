package com.ashok.hackerearth.hiringChallenge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Problem: Navi and maths
 * https://www.hackerearth.com/prysm-hiring-challenge/problems/8fe5474e370c4f25a8c7be0907b86919/
 *
 * @author: Ashok Rajpurohit ashok1113@gmail.com
 */

public class PrysmQA15A {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static long max = 0;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        PrysmQA15A a = new PrysmQA15A();
        a.solve();
        out.close();
    }

    private static long process(int[] ar) {
        if (ar.length == 2)
            return (1L * ar[0] * ar[1] / (ar[0] + ar[1])) % mod;

        Arrays.sort(ar);
        int count = 1;
        for (int i = 1; i < ar.length; i++)
            if (ar[i] != ar[i - 1])
                count++;

        int[] var = new int[count];
        int[] car = new int[count];

        var[0] = ar[0];
        car[0] = 1;

        for (int i = 1, j = 0; i < ar.length; i++) {
            if (ar[i] == ar[i - 1])
                car[j]++;
            else {
                j++;
                var[j] = ar[i];
                car[j] = 1;
            }
        }

        print(var);
        print(car);

        process(var, car, 1, 0, 0, 0);

        return max;
    }

    private static void print(int[] ar) {
        StringBuilder sb = new StringBuilder(ar.length << 1);
        for (int i = 0; i < ar.length; i++)
            sb.append(ar[i]).append(' ');

        System.out.println(sb);
    }

    private static void process(int[] var, int[] car, long num, long deno,
                                int index, int count) {
        System.out.println(num + "\t" + deno + "\t" + index + ":\t" + max);
        if (index == var.length) {
            if (count > 1)
                max = Math.max(max, num * pow(deno, mod - 2) % mod);

            return;
        }

        int temp = var[index];
        num = num * pow(temp, mod - 2) % mod;
        deno -= temp;
        count--;
        for (int i = 0; i <= car[index]; i++) {
            deno += temp;
            count++;
            num = num * temp % mod;
            process(var, car, num, deno, index + 1, count);
        }
    }

    private static long pow(long a, long b) {
        if (b == 0)
            return 1;

        a = a % mod;
        if (a < 0)
            a += mod;

        if (a == 1 || a == 0 || b == 1)
            return a;

        long r = Long.highestOneBit(b), res = a;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return res;
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();
        for (int i = 1; i <= t; i++) {
            int n = in.readInt();
            out.println("Case #" + i + ": " + process(in.readIntArray(n)));
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
