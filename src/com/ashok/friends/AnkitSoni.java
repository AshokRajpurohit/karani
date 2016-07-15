package com.ashok.friends;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Arrays;


/**
 * Problem Name: Ankit Soni
 * Link: Office E-MAIL ID
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AnkitSoni {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        AnkitSoni a = new AnkitSoni();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        RandomStrings random = new RandomStrings();
        while (true) {
            int n = in.readInt(), k = in.readInt();
            int[] ar = Generators.generateRandomIntegerArray(n, in.readInt(),
                    in.readInt());

            out.print(ar);
            out.flush();
            long t = System.currentTimeMillis();
            out.println(MinimizeSum.minSum(ar, n, k));
            out.println("total time: " + (System.currentTimeMillis() - t));
            out.flush();
        }
    }

    private static int reach(int[][] matrix, int sr, int sc, int er, int ec) {
        if (sr == er && sc == ec)
            return 0;

        return 4;
    }

    class Pair implements Comparable<Pair> {
        char key;
        int value;

        Pair(char k, int v) {
            key = k;
            value = v;
        }

        public int compareTo(Pair o) {
            return this.key - o.key;
        }
    }

    /**
     * HackerEarth Hiring Challenge Problem.
     */
    final static class MinimizeSum {
        private static int[][][] excludeArray, includeArray;
        private static int[] sums;

        private static void populate(int[] ar) {
            int n = ar.length;
            excludeArray = new int[n][n][];
            includeArray = new int[n][n][];

            for (int i = 0; i < n; i++)
                for (int j = i; j < n; j++) {
                    int width = j - i + 1;
                    excludeArray[i][j] = new int[n - width];
                    includeArray[i][j] = new int[width];

                    for (int k = 0; k < i; k++)
                        excludeArray[i][j][k] = ar[k];

                    for (int k = j + 1; k < n; k++)
                        excludeArray[i][j][k - width] = ar[k];

                    for (int k = i; k <= j; k++)
                        includeArray[i][j][k - i] = ar[k];

                    Arrays.sort(excludeArray[i][j]);
                    Arrays.sort(includeArray[i][j]);
                }

            sums = new int[ar.length];
            sums[0] = ar[0];

            for (int i = 1; i < ar.length; i++)
                sums[i] = ar[i] + sums[i - 1];
        }

        public static int minSum(int[] ar, int n, int k) {
            if (allNegative(ar))
                return sum(ar);

            int min = min(ar);
            if (min >= 0)
                return min;

            int pos = 0, neg = 0;
            for (int e : ar) {
                if (e == 0)
                    continue;

                if (e > 0)
                    pos++;
                else neg++;
            }

            if (k >= pos || k >= neg)
                return negativeSum(ar);

            return process(ar, k, negativeSum(ar));
        }

        private static int process(int[] ar, int k, int minLimit) {
            populate(ar);
            int minSum = ar[0];

            for (int i = 0; i < ar.length && minSum > minLimit; i++)
                for (int j = 0; j < ar.length; j++) {
                    int[] include = includeArray[i][j], exclude =
                            excludeArray[i][j];

                    if (include == null || exclude == null)
                        continue;

                    int sum = sums[j] - (i == 0 ? 0 : sums[i - 1]);

                    for (int x = include.length - 1, y = 0; x >= 0 && y <
                            exclude.length && y < k; x--, y++) {
                        if (include[x] <= exclude[y])
                            break;

                        sum += exclude[y] - include[x];
                    }

                    minSum = Math.min(minSum, sum);
                    if (minSum == minLimit)
                        return minLimit;
                }

            return minSum;
        }

        private static int negativeSum(int[] ar) {
            int sum = 0;

            for (int e : ar)
                if (e < 0)
                    sum += e;

            return sum;
        }

        private static int[] countNegatives(int[] ar) {
            int[] res = new int[ar.length];
            if (ar[0] <= 0)
                res[0] = 1;

            for (int i = 1; i < ar.length; i++)
                res[i] = ar[i] <= 0 ? res[i - 1] + 1 : res[i - 1];

            return res;
        }

        private static int min(int[] ar) {
            int min = Integer.MAX_VALUE;

            for (int e : ar)
                if (min > e) min = e;

            return min;
        }

        private static boolean allNegative(int[] ar) {
            for (int e : ar)
                if (e > 0)
                    return false;

            return true;
        }

        private static int sum(int[] ar) {
            int sum = 0;
            for (int e : ar)
                sum += e;

            return sum;
        }
    }
}
