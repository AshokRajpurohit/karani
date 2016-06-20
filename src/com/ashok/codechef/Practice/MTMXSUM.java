package com.ashok.codechef.Practice;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Problem: Matrix Maximum Sum
 * https://www.codechef.com/FEB16/problems/MTMXSUM
 *
 * Let's analyse the solution.
 * As explained in the quesion C(i, j) = (Ai + i) * (Bj + j).
 * We can update arrays A and B (adding the index).
 *
 * Now let's zoom into any submatrix of C of size k x k, and let's say
 * the submatrix is between rows row_start, col_start to row_start + k - 1, col_start + k - 1.
 *
 * It's obvious that all the element in the chosen submatrix are multiplications
 * of all elements from two sets A(row_start to row_strat + k -1) and B(col_start to col_start + k - 1).
 *
 * The maximum element in the chosen submatrix is Max(A(start to end)) * Max(B(start to end)).
 * If we fix rows (start and end) and vary column start and end values with
 * fixed width k then we know that for every submatrix the value of it or
 * max element in the submatrix is Max(A(row_start to row_start + k - 1) * Max (B(col, col + k - 1)).
 *
 * Every time the max value from Array A is part of value of submatrix.
 * so for every k x k the sum of all values of submatrix is:
 *
 * (sum of max values of A for each subarray of width k)
 *              * (sum of max values of B for each subarray of width k).
 *
 * Now it's upto you how you implement it.
 * I will explain How I did it.
 * for every element of Array A, A[i] I calculated the next large element's index
 * right[i] and previous large element's index left[i], so in this width A[i]
 * element is maximum element for all subarray from size 1 to width of this array.
 * and we can calculate for each size from 1 to width (right[i] - left[i] - 1),
 * how many subarray are there which includes this element, that many times
 * this element will be in size x size submatrix.
 *
 * Now again it's upto you how you implement it.
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */

class MTMXSUM {

    private static PrintWriter out;
    private static InputStream in;
    private static int mod = 1000000007;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        MTMXSUM a = new MTMXSUM();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] a = in.readIntArray(n);
        int[] b = in.readIntArray(n);
        solve(a, b, n);
    }

    private static void solve(int[] a, int[] b, int n) {
        StringBuilder sb = new StringBuilder(n << 2);
        for (int i = 0; i < n; i++) {
            a[i] += i + 1;
            b[i] += i + 1;
        }

        int[] lefta = leftBoundry(a), leftb = leftBoundry(b);
        int[] righta = rightBoundy(a), rightb = rightBoundy(b);

        long[] suma = process(a, lefta, righta), sumb =
            process(b, leftb, rightb);

        for (int i = 0; i < n; i++) {
            sb.append((suma[i] % mod) * (sumb[i] % mod) % mod).append(' ');
        }

        out.println(sb);
    }

    private static long[] process(int[] a, int[] left, int[] right) {
        long[] res = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            int l = i - left[i], r = right[i] - i;
            if (l <= r) {
                int j = 0;
                r--;
                while (j < l) {
                    res[j] += a[i];
                    j++;
                    r++;
                    if (r < a.length)
                        res[r] -= a[i];
                }
            } else {
                int j = 0;
                l--;
                while (j < r) {
                    res[j] += a[i];
                    j++;
                    l++;
                    if (l < a.length)
                        res[l] -= a[i];
                }
            }
        }

        for (int i = 1; i < a.length; i++)
            res[i] += res[i - 1];

        return res;
    }

    /**
     * Calculates the index of the previous larger element and stores in the
     * array. If there is no larger element previous to it then the value is
     * -1.
     *
     * @param ar
     * @return
     */
    private static int[] leftBoundry(int[] ar) {
        int n = ar.length;
        int[] res = new int[n];
        res[0] = -1;

        for (int i = 1; i < ar.length; i++) {
            int j = i - 1;
            while (j != -1 && ar[j] < ar[i])
                j = res[j];

            res[i] = j;
        }

        return res;
    }

    /**
     * Calculates the index of next large element and store in the array.
     * If there is no larger element following it then the value is the size of
     * array.
     *
     * @param ar
     * @return
     */
    private static int[] rightBoundy(int[] ar) {
        int n = ar.length;
        int[] res = new int[n];

        res[n - 1] = n;
        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;
            while (j != n && ar[j] <= ar[i])
                j = res[j];

            res[i] = j;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
