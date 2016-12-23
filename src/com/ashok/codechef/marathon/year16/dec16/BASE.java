package com.ashok.codechef.marathon.year16.dec16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Our Base is Under Attack
 * Link: https://www.codechef.com/DEC16/problems/BASE
 * <p>
 * Magic Number is a number with 1 as first digit.
 * Algorithm and logic is explained at {@link #getBaseCounts(long)}.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * @see #getBaseCounts(long)
 */
class BASE {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final int maxPower = 26;
    private static final long MAX_NUM = 1000000000000L;
    private static final int limit = 1000, mod = 1000000007;
    private static final String infinite = "INFINITY\n";
    private static long[][] powers = new long[maxPower + 1][];

    /**
     * Populates {@link #powers} array as precomputation, as we are
     * going to calculate exponents frequently and at the same time,
     * we need to search the appropriate power.
     * a ^ b is stored at powers[b][a].
     */
    private static void populate() {
        powers[1] = new long[limit + 1]; // single power i.e. all numbers  1 to limit
        for (int i = 1; i <= limit; i++)
            powers[1][i] = i;

        for (int i = 2; i <= maxPower; i++) {
            powers[i] = getPowerArray(i);
        }
    }

    public static void main(String[] args) throws IOException {
        populate();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t * 10);

        while (t > 0) {
            t--;
            long res = getBaseCounts(in.readLong());
            if (res == Long.MAX_VALUE)
                sb.append(infinite);
            else
                sb.append(res).append('\n');
        }

        out.print(sb);
    }

    /**
     * Checks whether n is magic number in base b. Magic number is the
     * number which has 1 as first digit as in 134, 163 are magic numbers.
     *
     * @param n Number to be checked for Magic Number Condition.
     * @param b Base for which n is to be checked.
     * @return true if n is magic number in base b.
     */
    private static boolean isMagicNumber(long n, long b) {
        while (n >= b)
            n /= b;

        return n == 1;
    }

    /**
     * Calculates base counts for which n is Magic Number.
     * <p>
     * Two digit magic numbers (in any base) are in format 1X where
     * X is in between 0 and b (base).
     * It can be proved that for any base b ( < n) larger than n / 2 (integer division)
     * n is in 1X format.
     * <p>
     * So for all bases from n / 2 to n, n is magic number.
     * Base counts for two digit magic numbers (for n) is n - (n / 2) + 1
     * <p>
     * For 3 digit numbers:
     * 3 digit basic numbers are of the form 1XY where X < b and y < b.
     * <p>
     * So n should be between 100 to 1b'b' (b' = b - 1).
     * For largest base n would be close to 100 and for smallest base n would be
     * close to 1b'b'.
     * <p>
     * so largest base is the smallest number b where b * b <= n, and
     * So smallest base is the largest number b' where n < b' * b' * 2 (200)
     * <p>
     * For k + 1 digits:
     * the largest base is the smallest number b where b ^ k <= n (100...00), and
     * smallest base is the largest number b' where n < 2 * b' ^ k (200...00).
     *
     * @param n Number for which base count to be calculated.
     * @return Number of bases where n is magic number.
     */
    private static long getBaseCounts(long n) {
        if (n == 0)
            return 0;

        if (n == 1)
            return Long.MAX_VALUE;

        if (n < 6)
            return n - 1;

        int digits = 4;
        long res = 1; // for base 2, the number always starts with 1

        res += n - (n >>> 1); // for all bases with two digits i.e. n = 1x
        res += getBaseCountFor3Digits(n);
        res += getBaseCountFor4Digits(n);

        while (digits < maxPower) {
            long count = getMagicNumbersForDigits(powers[digits], n);
            if (count == -1)
                return res;

            res += count;
            digits++;
        }

        return res;
    }

    /**
     * Calculates base counts where n is 3 digit magic number.
     *
     * @param n
     * @return
     */
    private static long getBaseCountFor3Digits(long n) {
        long min = (long) Math.sqrt((n + 1) >>> 1), max = (long) Math.sqrt(n);

        while (2 * min * min <= n)
            min++;

        min = Math.max(3, min);
        if (min > max)
            return 0;

        return max - min + 1;
    }

    private static long getBaseCountFor4Digits(long n) {
        long min = (long) Math.cbrt((n + 1) >>> 1), max = (long) Math.cbrt(n);

        while (2 * min * min * min <= n)
            min++;

        min = Math.max(3, min);
        if (min > max)
            return 0;

        return max - min + 1;
    }

    /**
     * Calculates number of bases, when n is translated into is of 1 + n digits.
     * The first digit is always 1 and the remaing digits can be anything
     * from 00...00 to bb...bb, total of b^n numbers starting from b^n to 2 * b^n - 1.
     *
     * @param n  The number for which the base count is to be calculated for 1 + n digits.
     * @param ar arrays to be used to find number of bases.
     * @return Number of bases where number is of 1 + n digits and the first
     * digit is 1.
     */
    private static long getMagicNumbersForDigits(long[] ar, long n) {
        if (ar.length < 4 || ar[3] > n)
            return -1; // stop calling this method again

        long max = findMax(ar, n), min = findMin(ar, n);
        if (min > max)
            return 0;

        return max - min + 1;
    }

    /**
     * Finds the largest base for which n is greater or equal to
     * 100...00 in that base.
     *
     * @param n
     * @param ar Array to be searched for.
     * @return largest base where 100...00 is not larger than n.
     */
    private static long findMax(long[] ar, long n) {
        if (ar.length < 15)
            return findMaxIteratively(ar, n);

        int start = 3, end = ar.length - 1, mid = (start + end) >>> 1;

        while (start != mid) {
            long num = ar[mid]; // same as pow(mid, pow)
            if (num <= n)
                start = mid;
            else
                end = mid;

            mid = (start + end) >>> 1;
        }

        if (ar[end] <= n)
            return end;

        return mid;
    }

    /**
     * Finds smallest base with 1 + pow digits where 1bb...bb is
     * larger than n.
     *
     * @param ar Array to be searched for.
     * @param n  The number for which the smallest base to be searched.
     * @return smallest base where 1bb..bb is smaller than n.
     */
    private static long findMin(long[] ar, long n) {
        if (ar.length < 15)
            return findMinIteratively(ar, n);

        int start = 3, end = ar.length - 1, mid = (start + end) >>> 1;

        while (start != mid) {
            long num = (ar[mid] << 1); // same as pow(mid, pow) * 2

            if (num <= n)
                start = mid;
            else if (num > n)
                end = mid;

            mid = (start + end) >>> 1;
        }

        if ((ar[mid] << 1) > n)
            return mid;

        return end;
    }

    /**
     * See {@link #findMax(long[], long)} for description.
     * This method does the exactly same thing but in iterative manner.
     *
     * @param ar
     * @param n
     * @return
     */
    private static int findMaxIteratively(long[] ar, long n) {
        int i = 3;
        for (i = 3; i <= ar.length && ar[i] <= n; i++) ;

        return i - 1;
    }

    /**
     * This method searched iteratively instead of binary searching.
     *
     * @param ar Array to be searched.
     * @param n  Element to be searched.
     * @return index.
     * @see #findMinIteratively(long[], long)
     */
    private static int findMinIteratively(long[] ar, long n) {
        int i = 3;
        for (; i < ar.length && 2 * ar[i] <= n; i++) ;

        return i;
    }

    private static long[] getPowerArray(int n) {
        int size = 2;
        long[] prevPowers = powers[n - 1];

        for (int i = 2; i < prevPowers.length && prevPowers[i] * i <= MAX_NUM; i++)
            size++;

        size++;
        size = Math.min(size, prevPowers.length);
        long[] powerArray = new long[size];
        powerArray[1] = 1;

        for (int i = 2; i < powerArray.length; i++)
            powerArray[i] = prevPowers[i] * i;

        return powerArray;
    }

    /**
     * Calculates a raised to power b in least number of multiplication.
     *
     * @param a number to be raised to power b.
     * @param b exponent
     * @return a raised to power b.
     */
    public static long pow(long a, long b) {
        if (b == 2)
            return a * a;

        long r = Long.highestOneBit(b), res = a;
        while (r > 1) {
            r = r >> 1;
            res = res * res;
            if ((b & r) != 0) {
                res = res * a;
            }
        }
        return res;
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
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

        public long readLong() throws IOException {
            long res = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                res = (res << 3) + (res << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            if (s == -1)
                res = -res;
            return res;
        }

        public long[] readLongArray(int n) throws IOException {
            long[] ar = new long[n];

            for (int i = 0; i < n; i++)
                ar[i] = readLong();

            return ar;
        }
    }
}
