package com.ashok.hackerRank.NumberTheory;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Problem Name: Little Panda Power
 * Link: https://www.hackerrank.com/challenges/littlepandapower
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class LittlePandaPower {
    private static final String path =
            "D:\\Java Projects\\karani\\src\\com\\ashok\\hackerRank\\NumberTheory\\";
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static boolean[] primes;
    private static LinkedList<Integer>[] primeFactors;

    static {
        int max = 1000000;
        primes = new boolean[max + 1];
        primeFactors = new LinkedList[max + 1];

        Arrays.fill(primes, true);

        for (int i = 2; i <= max; i++) {
            if (primes[i]) {
                for (int j = i << 1; j <= max; j += i) {
                    primes[j] = false;
                    ensure(primeFactors, j);
                    primeFactors[j].add(i);
                }
            }
        }
    }

    private static void ensure(LinkedList<Integer>[] list, int index) {
        if (list[index] == null)
            list[index] = new LinkedList<>();
    }

    public static void main(String[] args) throws IOException {
        LittlePandaPower a = new LittlePandaPower();
        a.solve();
        in.close();
        out.close();
    }

    private void solve() throws IOException {
        in = new InputReader(path +
                "LittlePandaPower.in");

        out = new Output(path + "LittlePandaPower.out");
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);

        while (t > 0) {
            t--;

            sb.append(process(in.readInt(), in.readInt(), in.readInt()))
                    .append('\n');
        }

        out.print(sb);
    }

    private static long process(int a, int b, int c) {
        if (c == 1)
            return 0;

        if (a == 0 || a == 1)
            return a;

        if (b == 0)
            return 1;

        if (b < 0) {
            return pow(pow(a, phi(c) - 1, c), -b, c);
        }

        return pow(a, b, c);
    }

    private static long pow(long a, long b, long mod) {
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

    private static int phi(int n) {
        if (primes[n])
            return n - 1;

        int phi = n;

        for (int e : primeFactors[n])
            phi = (e - 1) * (phi / e);

        return phi;
    }

    final static class InputReader1 {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader1() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

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
