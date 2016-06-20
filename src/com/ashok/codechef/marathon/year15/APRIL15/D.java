package com.ashok.codechef.marathon.year15.APRIL15;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit  ashok1113@gmail.com
 * problem Link:  http://www.codechef.com/problems/CSEQ
 * problem Link:  http://www.codechef.com/APRIL15/problems/CSEQ
 */

public class D {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000003;
    private static long[] fact = new long[mod];

    /**
     * let's calculate the factorials of numbers upto mod - 1 and save them
     * for quick reference.
     */
    static {
        fact[0] = 1;
        for (int i = 1; i < fact.length; i++) {
            fact[i] = (fact[i - 1] * i) % mod;
        }
    }

    /**
     * @chacha: Do you remember you asked me once how can we can calculate
     * power with minimum multiplications possible?
     * This is the implementation of the same.
     *
     * This is power calculating function which calculates number a raised to
     * power b with modulo mod.
     * This function uses minimum number of multiplications for power
     * calculation.
     * For Long number maximum 124 multiplications are possible and for
     * integer number (32 bit) max 62 multiplicaions are needed to
     * calculate a raised to power b.
     * @param a
     * @param b
     * @return
     */

    private static long pow(long a, long b) {
        if (a <= 1)
            return a;

        if (b == 1)
            return a % mod;

        if (b == 0)
            return 1;

        a = a % mod;
        long r = 1, res = a;
        r = r << 62;

        while ((b & r) == 0)
            r = r >> 1;

        while (r > 1) {
            r = r >> 1;
            res = (res * res) % mod;
            if ((b & r) != 0) {
                res = (res * a) % mod;
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);
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
            sb.append(solve(n, l, r)).append('\n');
        }

        out.print(sb);
    }

    /**
     * returns the factorial of given number n.
     * natural numbers are in cycle of mod after taking modulus by mod.
     * so for number equal to 2*mod factorial is square of factorial of mod-1
     * multiplied by mod and 2 * mod.
     * For modular calculations we are cancling out mod factor from denominator
     * to make denominator and mod coprime.
     * so the factorial of 2 * mod will be square of (factorial of mod - 1)
     * multiplied by 1 * 2.
     * similarly if number is n*mod + m then the factorial will be
     * (factorial of mod-1) ^ n * (factorial of n) * (factorial of m).
     * @param n
     * @return returns n! modulo mod
     */

    private static long getFact(int n) {
        if (n < mod) {
            return fact[n];
        }
        int factor = n / mod;
        long res = (pow(fact[mod - 1], factor) * fact[n % mod]) % mod;
        return (res * fact[factor]) % mod;
    }

    /**
     * calculates number of sequences of r+1-l elements taken upto n elements
     * with repetetion.
     * The formula is C(n+k,k) where n is number of elements and k is max
     * length of sequence.
     * C(n+k,k) is sum of C(n+r-1,n) where r = 1 to k.
     * C(n+r-1,n) is the formula for the number of combination of n things
     * taken r at a time when repetetion is allowed.
     * This is same as the number of terms in expansion of
     * (a1 + a2 + ... + an) ^ r.
     * (refer Higher Algebra by S. Bernard and J. M. Child, Chapter XXXI
     * Permutations, Combinations and Distributions).
     * For inverse modulo we are calculating a raised to power mod - 2
     * as mod is prime number
     * (see wiki for inverse modulo, Euler's totient function)
     * @param n
     * @param l
     * @param r
     * @return returns C(n+r+1-l, n)
     */

    private static long solve(int n, int l, int r) {
        int k = l > r ? l + 1 - r : r + 1 - l;
        if (n == 1)
            return k % mod;

        if (k == 1)
            return n % mod;

        if ((n + k) / mod > (n / mod) + (k / mod))
            return mod - 1;

        long res = getFact(n + k);
        res = res * pow(getFact(n) * getFact(k), mod - 2);
        res = res % mod;

        if (res == 0)
            return mod - 1;

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
