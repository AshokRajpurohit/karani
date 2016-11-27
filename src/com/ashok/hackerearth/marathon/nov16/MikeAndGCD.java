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
    private static int[] largestFactor;
    private static int limit = 200001;
    private static LinkedList<Integer>[] primeFactors;

    private static void process(int n) {
        limit = n;
        largestFactor = new int[limit];
        primeFactors = new LinkedList[limit];

        for (int i = 2; i < limit; i++) {
            if (largestFactor[i] == 0) { // i. e. prime number
                for (int j = i; j < limit; j += i) {
                    largestFactor[j] = i;

                    ensure(primeFactors, j);
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
        process(max(ar) + 1);

        LinkedList<Integer>[] multipliers = new LinkedList[limit];
        for (int i = 0; i < n; i++)
            populate(multipliers, ar[i], i);

        int[][] map = process(multipliers);

        for (int i = 0; i < n; i++) {
            sb.append(findJ(map, ar[i], i)).append(' ');
        }

//        out.println(sb);
    }

    private static int max(int[] ar) {
        int max = 1;

        for (int e : ar)
            max = Math.max(max, e);

        return max;
    }

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

            if (temp == -1 || Math.abs(index - j) < Math.abs(index - temp))
                continue;

            if (Math.abs(index - j) > Math.abs(index - temp))
                j = temp;
            else
                j = Math.min(j, temp);
        }

        return j == -1 ? j : j + 1;
    }

    private static int getJ(int[] indexArray, int index) {
        if (indexArray == null || indexArray.length < 2)
            return -1;

        if (indexArray.length < 100)
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

        mid = indexArray[mid] == index ? mid : end;

        if (mid == 0)
            return indexArray[mid + 1];

        if (mid == indexArray.length - 1)
            return indexArray[mid - 1];

        int prev = indexArray[mid - 1], next = indexArray[mid + 1];
        int prevDelta = index - prev, nextDelta = next - index;

        if (prevDelta == nextDelta)
            return prev;
        else if (prevDelta > nextDelta)
            return next;
        else
            return prev;
    }

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

    private static int[][] process(LinkedList<Integer>[] listArray) {
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

    private static void populate(LinkedList<Integer>[] multipliers, int n, int index) {
        if (n == 1)
            return;

        for (int e : getPrimeFactors(n)) {
            ensure(multipliers, e);
            multipliers[e].addLast(index);
        }
    }

    private static LinkedList<Integer> getPrimeFactors(int n) {
        return primeFactors[n];
    }

    private static void ensure(LinkedList<Integer>[] list, int index) {
        if (list[index] != null)
            return;

        list[index] = new LinkedList<>();
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
