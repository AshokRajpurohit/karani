package com.ashok.hackerearth.marathon.nov16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Problem Name: Mike and GCD Issues
 * Link: https://www.hackerearth.com/november-circuits/algorithm/mike-and-gcd-issues/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MikeAndGCD {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static int[] largestFactor; // at index n, contains largest prime factor to n. if n is prime than it is n.
    private static int limit = 200001; // size for precomputation.
    private static LinkedList<Integer>[] primeFactors; // at index n, contains list of prime factors of n.

    /**
     * Populates {@link #largestFactor} and {@link #primeFactors} for quick reference.
     *
     * @param n limit for precomputation.
     */
    private static void preprocess(int n) {
        limit = n;
        largestFactor = new int[limit];
        primeFactors = new LinkedList[limit];

        for (int i = 2; i < limit; i++) {
            if (largestFactor[i] == 0) { // i. e. prime number
                for (int j = i; j < limit; j += i) {
                    largestFactor[j] = i;

                    ensureInitialized(primeFactors, j);
                    primeFactors[j].addLast(i);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n);
        StringBuilder sb = new StringBuilder(n << 2);
        preprocess(max(ar) + 1);

        /**
         * at any index n, {@code multipliers} has list of number-index, n divides.
         * example: 3 divides 18(index 3), 216(index 7) and 33(index 8), then multiplier at index 3
         * will be list containing 3, 7, 8. It contains indices, not the numbers.
         */
        LinkedList<Integer>[] multipliers = new LinkedList[limit];
        for (int i = 0; i < n; i++)
            populate(multipliers, ar[i], i);

        int[][] map = toArray(multipliers);

        for (int i = 0; i < n; i++) {
            sb.append(findJ(map, ar[i], i)).append(' ');
        }

        out.println(sb);
    }

    private static int max(int[] ar) {
        int max = 1;

        for (int e : ar)
            max = Math.max(max, e);

        return max;
    }

    /**
     * Finds the index j, for which gcd(ar[i], ar[j]) > 1 and |i - j| is minimum.
     *
     * @param map   array of, prime factors array
     * @param n     reference element for gcd computation.
     * @param index reference index i
     * @return nearest non-coprime element index.
     */
    private static int findJ(int[][] map, int n, int index) {
        if (n == 1)
            return -1;

        int j = -1;
        for (int e : getPrimeFactors(n)) {
            int temp = getJ(map[e], index);

            if (j == -1) {
                j = temp;
                continue;
            }

            if (temp == -1 || Math.abs(index - j) < Math.abs(index - temp)) // we don't need to update, so continue.
                continue;

            if (Math.abs(index - j) > Math.abs(index - temp)) // we have clear answer in this case.
                j = temp;
            else
                j = Math.min(j, temp); // when two values of j are at equal distance, chose smaller one.
        }

        return j == -1 ? j : j + 1; // +1 is to offset 1 based index. -1 is invalid answer.
    }

    /**
     * Finds the closest element to {@code index}.
     *
     * @param indexArray array to be searched for nearest index.
     * @param index      reference index.
     * @return nearest index.
     */
    private static int getJ(int[] indexArray, int index) {
        if (indexArray == null || indexArray.length < 2) // for single element, no j available.
            return -1;

        if (indexArray.length < 35) // let's not chose binary search for very small array.
            return iterateAndFindJ(indexArray, index);

        int start = 0, end = indexArray.length - 1, mid = (start + end) >>> 1;

        while (start != mid) {
            if (indexArray[mid] == index)
                break;

            if (indexArray[mid] > index)
                end = mid;
            else
                start = mid;

            mid = (start + end) >>> 1;
        }

        mid = indexArray[mid] == index ? mid : end; // we found the matching index in mid.

        if (mid == 0) // if it is the first element, then second element is the only neighbouring element.
            return indexArray[mid + 1];

        if (mid == indexArray.length - 1) // last element has only one neighbour.
            return indexArray[mid - 1];

        int prev = indexArray[mid - 1], next = indexArray[mid + 1]; // previous and next neighbours
        int prevDelta = index - prev, nextDelta = next - index; // delta for neighbours

        if (prevDelta == nextDelta)
            return prev;
        else if (prevDelta > nextDelta)
            return next;
        else
            return prev;
    }

    /**
     * Iterate the array and return closest element (and first).
     *
     * @param ar
     * @param index
     * @return
     */
    private static int iterateAndFindJ(int[] ar, int index) {
        if (ar[0] == index)
            return ar[1];

        if (ar[ar.length - 1] == index)
            return ar[ar.length - 2];

        int i = 0;
        while (ar[i] != index)
            i++;

        int j = i + 1;
        i--;

        int prevDelta = index - ar[i], nextDelta = ar[j] - index;

        if (prevDelta <= nextDelta)
            return ar[i];

        return ar[j];
    }

    /**
     * Convert list array into 2D array for fast searching.
     *
     * @param listArray
     * @return
     */
    private static int[][] toArray(LinkedList<Integer>[] listArray) {
        int[][] ar = new int[listArray.length][];

        for (int i = 0; i < listArray.length; i++)
            ar[i] = toArray(listArray[i]);

        return ar;
    }

    private static int[] toArray(LinkedList<Integer> list) {
        if (list == null)
            return null;

        int[] ar = new int[list.size()];
        int index = 0;

        for (int e : list)
            ar[index++] = e;

        return ar;
    }

    /**
     * Updates multipliers for all the prime factors of integer {@code n}.
     * For each prime factor, it adds {@code index} as it divides n.
     *
     * @param multipliers list array to be populated
     * @param n           reference element.
     * @param index       reference element index.
     */
    private static void populate(LinkedList<Integer>[] multipliers, int n, int index) {
        if (n == 1)
            return;

        for (int e : getPrimeFactors(n)) {
            ensureInitialized(multipliers, e);
            multipliers[e].addLast(index); // now e knows that it divides element n at position index.
        }
    }

    /**
     * Returns prime factors list for {@code n}.
     *
     * @param n
     * @return
     */
    private static LinkedList<Integer> getPrimeFactors(int n) {
        return primeFactors[n];
    }

    /**
     * Initializes the list, if not initialized.
     *
     * @param list  list array to be initialized.
     * @param index position for the list to be initialized.
     */
    private static void ensureInitialized(LinkedList<Integer>[] list, int index) {
        if (list[index] != null)
            return;

        list[index] = new LinkedList<>();
    }

    /**
     * Fast Input Reader.
     */
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
