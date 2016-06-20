package com.ashok.codechef.marathon.year15.APRIL15;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Main {
    private Main() {
        super();
    }

    public static void main(String[] args) {
        long t1 = System.nanoTime();
        for (int i = 0; i < 200; i++) {
            try {
                D.main();
            } catch (IOException e) {
                System.out.println("Do Nothing");
            }
        }
        long t2 = System.nanoTime();
        System.out.println(t2 - t1);
    }

    final static class D {

        private static PrintWriter out;
        private static InputStream in;
        private static int mod = 1000003;
        // let's not care about task 2 and 3
        private static int[] fact = new int[1000003];
        private static int x, y;
        static {
            fact[0] = 1;
            for (int i = 1; i < fact.length; i++) {
                fact[i] = (i * fact[i - 1]) % mod;
            }
        }

        private static long pow(int a, int b, int mod) {
            if (a <= 1)
                return a;

            if (b == 1)
                return a % mod;

            if (b == 0)
                return 1;

            long r = Long.highestOneBit(a), res = a;

            while (r > 1) {
                r = r >> 1;
                res = (res * res) % mod;
                if ((b & r) != 0) {
                    res = (res * a) % mod;
                }
            }
            return res;
        }

        public static void main() throws IOException {
            //        OutputStream outputStream = System.out;
            //        in = System.in;
            //        out = new PrintWriter(outputStream);

            String input = "D.in", output = "D.out";
            FileInputStream fip = new FileInputStream(input);
            FileOutputStream fop = new FileOutputStream(output);
            in = fip;
            out = new PrintWriter(fop);
            D a = new D();
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
                int l = in.readInt();
                int r = in.readInt();
                out.println(solve(n, l, r));
                sb.append(solve(n, l, r)).append('\n');
            }

            out.print(sb);
        }

        private static int getFact(int n) {
            int factor = n / mod;
            if (factor == 0) {
                return fact[n];
            }
            long res = (pow(fact[mod - 1], factor, mod) * fact[n % mod]) % mod;
            return (int)(res * fact[factor]) % mod;
        }

        private static long solve(int n, int l, int r) {
            int k = l > r ? l + 1 - r : r + 1 - l;
            if (n == 1)
                return k % mod;

            if (k == 1)
                return n % mod;

            if ((n + k) / mod > (n / mod) + (k / mod))
                return mod - 1;

            long res = getFact(n + k);
            res = (res * pow(getFact(n), mod - 2, mod)) % mod;
            res = (res * pow(getFact(k), mod - 2, mod)) % mod;
            if (res <= 0)
                res = res - (res / mod - 1) * mod;
            return res - 1;
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
                for (; buffer[offset] < 0x30 || buffer[offset] == '-';
                     ++offset) {
                    if (buffer[offset] == '-')
                        s = -1;
                    if (offset == bufferSize - 1) {
                        offset = -1;
                        bufferSize = in.read(buffer);
                    }
                }
                for (; offset < bufferSize && buffer[offset] > 0x2f;
                     ++offset) {
                    number =
                            (number << 3) + (number << 1) + buffer[offset] - 0x30;
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
}
