package com.ashok.edrepublic;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 *
 */

public class SumtoN {

    private static PrintWriter out;
    private static InputStream in;
    private static int[] primes = gen_prime(1000000);

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        SumtoN a = new SumtoN();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        goEasy(in.readLong());
    }

    private static void useFactors(long n) {
        // le
    }

    /**
     * NOTE:- This function is now redundant.
     * My first function for this problem. Two indexes i as start and j as
     * end are maintained for the range. i is initialized to 1 and j to the min
     * possible (sqrt(2 * N)). j is incremented each time in the loop till
     * the largest possible value for j (n / 2). i is jumped to the next
     * possible value. the loop runs n / 2 - sqrt(n * 2) times.
     *
     * @param n
     */

    private static void process(long n) {
        StringBuilder sb = new StringBuilder();
        long i = 1, j = (long)Math.sqrt(n << 1), half = n >>> 1;
        long sum = (j * (j + 1)) >>> 1;
        while (i <= half && i < j) {
            if (sum == n) {
                sb.append('[').append(i).append(',').append(j).append("[,");
                j++;
                sum += j - i;
                i++;
            } else if (sum < n) {
                j++;
                sum += j;
            } else if (sum > n) {
                long diff = sum - n;
                if (diff > 100 * i) {
                    double k =
                        (Math.sqrt((2 * i - 1) * (2 * i - 1) + diff << 3) -
                         2 * i + 1) / 2;
                    long count = (long)k;
                    sum = sum - ((count * (2 * i - 1 + count)) >>> 1);
                    i = i + count;
                }

                while (sum > n) {
                    sum -= i;
                    i++;
                }
            }
        }

        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1).append('\n');
        System.out.print(sb);
    }

    /**
     * This is the most efficient function I could write for the problem.
     * The count variable is the length of possible range.
     * if count is odd then there exists one middle element and the sum
     * of range in this case will be mid_elem * count. So the number must be
     * divisible by count.
     *
     * If the count is even then there exists two middle elements and as we
     * know sum of any two consecutive natural numbers is odd.
     * sum of the range in this case is mid_elem_sum * pair_count.
     * pair count is equal to count / 2.
     * so the number must be divisible by pair_count and
     * mid_elem_sum = n / pair_count must be odd.
     *
     * @param n
     */

    private static void goEasy(long n) {
        System.out.println(n);
        long count = (long)Math.sqrt(n << 1);
        StringBuilder sb = new StringBuilder();
        if (count * (count + 1) == 2 * n)
            sb.append("[1,").append(count).append("],");
        count--;

        long town = n << 1;
        while (count > 1) {
            if ((count & 1) == 0 && ((n / (count >>> 1)) & 1) != 0 &&
                n % (count >>> 1) == 0 && !((count & 3) == 0 ^ (n & 1) == 0)) {
                long mid = n / count; // middle left element
                append(sb, mid - (count >>> 1) + 1, mid + (count >>> 1));
            } else if ((count & 1) != 0 && n % count == 0) {
                long mid = n / count; // middle element
                append(sb, mid - (count >>> 1), mid + (count >>> 1));
            }
            count--;
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1).append('\n');
        System.out.print(sb);
    }

    /**
     * I didn't want to write this append again and again (two times in the
     * function) so created this append function.
     *
     * @param sb
     * @param start
     * @param end
     */

    private static void append(StringBuilder sb, long start, long end) {
        if (start < 1)
            return;

        sb.append('[').append(start).append(',').append(end).append("],");
    }

    /**
     * This function generates prime numbers upto given integer n and
     * returns the array of primes upto n (inclusive).
     * @param n prime numbers upto integer n
     * @return
     */
    public static int[] gen_prime(int n) {
        boolean[] ar = new boolean[n + 1];
        int root = (int)Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            while (ar[i])
                i++;
            for (int j = i + 1; j <= n; j++) {
                if (!ar[j]) {
                    ar[j] = j % i == 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++)
            if (!ar[i])
                count++;

        int[] ret = new int[count];

        for (int i = 2, j = 0; i <= n; i++) {
            if (!ar[i]) {
                ret[j] = i;
                j++;
            }
        }
        return ret;
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
    }
}
