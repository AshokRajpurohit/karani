package com.ashok.codechef.marathon.year15.MAY15;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Ashok Rajpurohit ashok1113
 *  problem Link: http://www.codechef.com/MAY15/problems/CHEFCK
 */

public class E {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007, N, K, Q, L, R;
    private static int[] ar, Lar, Rar;
    private static int[][] mar;
    private static int a, b, c, d, e, f, r, s, t, m;
    private static int L1, La, Lc, Lm, D1, Da, Dc, Dm, last;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
        E a = new E();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();

        N = in.readInt();
        K = in.readInt();
        Q = in.readInt();

        a = in.readInt();
        b = in.readInt();
        c = in.readInt();
        d = in.readInt();
        e = in.readInt();
        f = in.readInt();
        r = in.readInt();
        s = in.readInt();
        t = in.readInt();
        m = in.readInt();

        ar = new int[N];
        ar[0] = in.readInt();

        L1 = in.readInt();
        La = in.readInt();
        Lc = in.readInt();
        Lm = in.readInt();
        D1 = in.readInt();
        Da = in.readInt();
        Dc = in.readInt();
        Dm = in.readInt();

        formatLRar();
        fillArray();
        process();
    }

    /**
     * fills the Lar array and Rar array. Lar array is for L (left index) and
     * Rar array is for R (right index).
     */

    private static void formatLRar() {
        Lar = new int[Q];
        Rar = new int[Q];

        long l2 = L1, d2 = D1;
        for (int i = 0; i < Q; i++) {
            l2 = (l2 * La + Lc) % Lm;
            d2 = (d2 * Da + Dc) % Dm;
            Lar[i] = (int)(l2 + 1);
            Rar[i] = (int)Math.min(d2 + l2 + K, N);
        }
    }

    private static void process() {
        long prod = 1, sum = 0;
        int quality, half = last >>> 1;

        for (int i = 0; i < Q; i++) {
            if (Rar[i] - Lar[i] + 1 >= last)
                quality =
                        Math.min(mar[last][Lar[i] - 1], mar[last][Rar[i] - last]);
            else
                quality =
                        Math.min(mar[half][Lar[i] - 1], mar[half][Rar[i] - half]);

            sum += quality;
            prod = (prod * quality) % mod;
        }

        out.println(sum + " " + prod);
    }

    /**
     * this function formats mar array (mar is Min ARray).
     */

    private static void fillArray() {
        long temp = t, x = ar[0];

        for (int i = 1; i < ar.length; i++) {
            temp = (temp * t) % s;
            if (temp <= r) {
                x = (((a * x + b) % m) * x + c) % m;
            } else {
                x = (((d * x + e) % m) * x + f) % m;
            }
            ar[i] = (int)x;
        }

        mar = new int[(2 * K < ar.length ? 2 * K : ar.length) + 1][];
        mar[1] = ar;

        int bit = 2;
        while (bit < mar.length) {
            int half = bit >>> 1;
            mar[bit] = new int[ar.length - bit + 1];
            for (int i = 0; i <= ar.length - bit; i++) {
                mar[bit][i] = Math.min(mar[half][i], mar[half][i + half]);
            }
            bit <<= 1;
        }
        last = bit >>> 1;
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
