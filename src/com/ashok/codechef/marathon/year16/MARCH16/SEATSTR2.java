package com.ashok.codechef.marathon.year16.MARCH16;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Sereja and Two Strings 2
 * https://www.codechef.com/MARCH16/problems/SEATSTR2
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class SEATSTR2 {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;
    private static long[] fact = new long[100001], inverse = new long[100001];

    static {
        fact[0] = 1;

        for (int i = 1; i < fact.length; i++)
            fact[i] = fact[i - 1] * i % mod;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SEATSTR2 a = new SEATSTR2();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int t = in.readInt();

        while (t > 0) {
            t--;
            out.println(process(in.read()));
        }
    }

    /**
     * Parameters and there uses
     *  n2 is for number of permutation when only one swap is needed (it's
     *  obvious between two elements only). So the value of n2 is number of
     *  choosing two different elements. for all the elements different it's
     *  C(n, 2) but here we have many indifferent items.
     *
     *  n3 is for number of permutation when two swaps are needed using three
     *  elements only. let's say for abc permutations which need two swaps are
     *  bca and cab only. It's obvious that acb, bac and cba needs exactly
     *  one swap and this we have already covered during calculation of n2.
     *
     * @param s
     * @return
     */
    private static long process(String s) {
        if (s.length() < 4)
            return 0;

        int[] map = new int[256];

        for (int i = 0; i < s.length(); i++)
            map[s.charAt(i)]++;

        int[] hash = new int[26];
        for (int i = 'a'; i <= 'z'; i++)
            hash[i - 'a'] = map[i];

        long res = fact[s.length()];
        for (int i = 0; i < 26; i++) {
            res = res * inverse(fact[hash[i]]) % mod;
        }

        long[] nc2 = new long[26];
        int temp = hash[0];

        for (int i = 1; i < 26; i++) {
            nc2[i] = temp * hash[i] % mod;
            temp += hash[i];
        }

        long n2 = 0, n4 = 0, n3 = 0;
        for (int i = 1; i < 26; i++)
            n2 += nc2[i];


        temp -= hash[0];
        for (int i = 1; i < 25; i++) {
            temp -= hash[i];
            n3 += nc2[i] * temp % mod;
        }

        n3 = (n3 << 1) % mod;
        n2 %= mod;
        n4 = n4(hash);

        long similar = res * (n2 + n3 + n4 + 1) % mod;

        res = res * res % mod - similar;
        res %= mod;

        if (res < 0)
            res += mod;

        return res;
    }

    /**
     * Parameters and their uses:
     *  same[] array is for number of ways selecting two indifferent elements.
     *  two2 is for aabb type selection.
     *  one2one is for abbc type selection.
     *  diff2 is for abcc type selection.
     *  twodiff is for aabc type selection.
     *  diff is for abcd type selection.
     *
     * Now we know for each selection how many similar selection are
     * possible.
     *
     * for abcd type there can be 3 permutations for which two swaps are needed
     * that includes all the four elements, and these are badc, cdab, dcba.
     *
     * for aabc, abbc and abcc type there are two different permutation for
     * which two swaps are needed, and these are "bcaa", "cbaa" (for aabc)
     *
     * for aabb there can be one permutation only for which two swaps are needed
     * and these are "bbaa"
     *
     * @param hash
     * @return
     */
    private static long n4(int[] hash) {
        long[] same = new long[26];
        for (int i = 0; i < 26; i++) {
            if (hash[i] > 1) {
                long v = hash[i];
                same[i] = ((v * (v - 1)) >>> 1) % mod;
            }
        }

        long[] left = new long[26], right = new long[26];
        long total = same[0], two2 = 0;

        for (int i = 1; i < 26; i++) {
            two2 += total * same[i] % mod;
            total += same[i];
        }

        long res = 0, temp = hash[0];

        for (int i = 1; i < 26; i++) {
            left[i] = temp * hash[i] % mod;
            temp += hash[i];
        }

        temp = hash[25];
        for (int i = 24; i >= 0; i--) {
            right[i] = temp * hash[i] % mod;
            temp += hash[i];
        }

        long[] rsum = new long[26];
        rsum[25] = right[25];

        for (int i = 24; i >= 0; i--) {
            rsum[i] = rsum[i + 1] + right[i];
        }

        long sum = 0, diff2 = 0, twodiff = 0, diff = 0;
        for (int i = 0; i < 26; i++) {
            sum += left[i];
        }

        temp = total - same[0];
        for (int i = 1; i < 25; i++) {
            temp -= same[i];
            diff2 += left[i] * temp % mod;
        }

        temp = total - same[25];
        for (int i = 24; i >= 0; i--) {
            temp -= same[i];
            twodiff += temp * right[i] % mod;
        }

        for (int i = 1; i < 25; i++) {
            diff += left[i] * rsum[i + 1] % mod;
        }

        long one2one = 0;
        long[] sameSum = new long[26];
        sameSum[0] = same[0];

        for (int i = 1; i < 26; i++) {
            sameSum[i] = sameSum[i - 1] + same[i];
        }

        for (int i = 2; i < 26; i++) {
            long tsum = sameSum[i - 1];
            for (int j = 0; j < i - 1; j++) {
                tsum -= same[j];
                one2one += (tsum * hash[i] % mod) * hash[j] % mod;
            }
        }

        res = two2 + ((diff2 + twodiff + one2one) << 1) + 3 * diff;
        res %= mod;

        if (res < 0)
            return res + mod;

        return res;
    }

    private static long inverse(long a) {
        if (a == 1 || a == 0)
            return a;

        long b = mod - 2;
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

        public String read() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                 '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == ' ' || buffer[offset] == '\t' ||
                    buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
