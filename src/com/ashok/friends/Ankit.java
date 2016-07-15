package com.ashok.friends;


import java.math.BigInteger;

/**
 * This class contains the functions implemented for Syncron
 * Online Coding Test.
 *
 * @ashok Ashok Rajpurohit ashok1113@gmail.com
 */
public class Ankit {
    public Ankit() {
        super();
    }

    public static void main(String[] args) {
        Ankit a = new Ankit();
        MyRegex mi = new MyRegex("266.23.45.34");
        System.out.println(mi.isMatches());
    }

    final static class Color {
        char color;
        static int[][] sdim, edim;
    }

    private static int powOfFive(String s) {
        int res = powOfFive(s, 0);
        if (res == 50)
            return -1;

        return res;
    }

    private static int powOfFive(String s, int start) {
        if (start == s.length())
            return 0;
        long num = 0;
        int count = 50;
        for (int i = start; i < s.length(); i++) {
            num = (num << 1) + s.charAt(i) - '0';
            if (isPowOfFive(num))
                count = Math.min(count, 1 + powOfFive(s, i + 1));
        }

        return count;
    }

    private static boolean isPowOfFive(long num) {
        if (num == 1)
            return true;

        if (num < 5)
            return false;

        while (num >= 0) {
            if (num % 5 != 0)
                return false;

            num /= 5;
        }
        return true;
    }

    /**
     * This function returns the count of digit 1 in the expansion of
     * 11^N.
     * e. g. for N = 1, it will return 2, for N = 2, it will return 2
     * as 11^2 is 121 and there are two 1's in the decimal representaion
     * of the number.
     *
     * @param N
     * @return
     */
    private int solution(int N) {
        BigInteger ELEVEN = new BigInteger("11");
        String res = ELEVEN.pow(N).toString();
        //        System.out.println(res);
        int result = 0;
        for (int i = 0; i < res.length(); i++) {
            if (res.charAt(i) == '1')
                result++;
        }
        return result;
    }

    /**
     * Let me explain the problem statement first.
     * You are given an integer array. let's take two indices p and q
     * where 0 <= p <= q < A.length (you know what I mean).
     * now let's say we need to calculate value = A[p] + A[q] + (q - p).
     * Let me remind you again that p <= q.
     * So calculate the maximum value possible for any value of p and q
     * in the array.
     * <p>
     * Now let me explain the solution.
     * let's break the expression in this form:
     * (A[q] + q) + (A[p] - p)
     * We have two components, let's name these as curr for A[q] + q
     * and past for A[p] - p.
     * let's assume we are traversing the array and the current index is q.
     * let's say that till index q the max value of A[p] - p is past.
     * as p can be equal to q so let's update past before performing the
     * addition with new value max(past, A[q] - q).
     * So if we want to calculate the max value till index q it
     * is curr + past as past is the max value possible for A[p] - p.
     * We can keep updating the result = curr + past for the max value
     * and after the loop return it.
     *
     * @param A
     * @return
     */
    long solution(int[] A) {
        long curr = A[0];
        long past = A[0];
        long res = curr + past;
        for (int i = 1; i < A.length; i++) {
            past = Math.max(past, A[i] - i);
            res = Math.max(res, A[i] + i + past);
        }
        return res;
    }

    /**
     * You are given a field in form of matrix. Lands are connected to
     * each other if they share a common edge (assume all are square),
     * i. e. one is at either east, west, north or south to the other.
     * value at index i, j A[i][j] is the color of the land.
     * If two land areas are connected and have same color then they are
     * part of one country.
     * color can be any positive integer (>=1).
     * You have to tell how many contries are there in the Graph.
     * for more clarification, please contact Mr. Ankit Agrawal.
     * I am just the editorialist.
     *
     * @param A
     * @return
     */
    public int solution(int[][] A) {
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] != 0) {
                    res++;
                    markAllReachable(A, i, j, A[i][j]);
                }
            }
        }
        return res;
    }

    /**
     * Checks validity for A[row][col].
     * row and col should be in the boundry and A[row][col] should be
     * equal to value.
     *
     * @param A
     * @param row
     * @param col
     * @param value
     * @return
     */
    private boolean isSafe(int[][] A, int row, int col, int value) {
        if (row < A.length && row >= 0 && col >= 0 && col < A[0].length &&
                A[row][col] == value)
            return true;
        return false;

    }

    /**
     * Marking all the rechable land areas belonging to the same country.
     * 0 is chosed as this value is invalid value and so distinguishable
     * from the unmarked (have non-zero positive value).
     * A[i][j] is the starting land mark and the value is the actual value
     * at A[i][j] as we are already setting A[i][j] to zero before calling
     * this function.
     *
     * @param A
     * @param i
     * @param j
     * @param value
     */
    private void markAllReachable(int[][] A, int i, int j, int value) {
        A[i][j] = 0;
        if (isSafe(A, i + 1, j, value)) {
            markAllReachable(A, i + 1, j, value);
        }
        if (isSafe(A, i - 1, j, value)) {
            markAllReachable(A, i - 1, j, value);
        }
        if (isSafe(A, i, j + 1, value)) {
            markAllReachable(A, i, j + 1, value);
        }
        if (isSafe(A, i, j - 1, value)) {
            markAllReachable(A, i, j - 1, value);
        }
    }

    /**
     * Don't pay attention to this.
     * This is binary searching in the sorted array.
     *
     * @param A
     * @param X
     * @return
     */
    int solution(int[] A, int X) {
        int N = A.length;
        if (N == 0) {
            return -1;
        }
        int l = 0;
        int r = N - 1;
        while (l < r) {
            int m = (l + r) / 2;
            if (A[m] > X) {
                r = m - 1;
            } else if (A[m] < X) {
                l = m + 1;
            } else
                return m;
        }
        if (A[l] == X) {
            return l;
        }
        return -1;
    }

    final static class MyRegex {
        public static String pattern;

        public MyRegex(String IP) {
            pattern = IP;
        }

        public static boolean isMatches() {
            System.out.println(pattern);
            int a = 0;
            int i = 0;
            while (i < pattern.length() && pattern.charAt(i) != '.')
                i++;

            for (int j = 0; j < i; j++)
                a = a * 10 + pattern.charAt(j) - '0';

            if (a > 255)
                return false;

            System.out.println(a);

            a = 0;
            i++;
            while (i < pattern.length() && i != '.')
                i++;

            for (int j = 0; j < i; j++)
                a = a * 10 + pattern.charAt(j) - '0';

            if (a > 255)
                return false;

            a = 0;
            i++;
            while (i < pattern.length() && i != '.')
                i++;

            for (int j = 0; j < i; j++)
                a = a * 10 + pattern.charAt(j) - '0';

            if (a > 255)
                return false;

            a = 0;
            i++;
            while (i < pattern.length() && i != '.')
                i++;

            for (int j = 0; j < i; j++)
                a = a * 10 + pattern.charAt(j) - '0';

            if (a > 255)
                return false;

            return true;
        }
    }

    /**
     * Problem for Hackerearth Hiring Challenge.
     * Problem Name: Minimize the sum
     */
    final static class MinimizeSum {
        public static int solve(int[] ar) {
            int min = min(ar);
            if (min >= 0)
                return min;

            int[] negativeSum = negativeSum(ar);
            int[] negativeCount = negativeCount(ar);

            for (int i = 0; i < ar.length; i++)
                for (int j = i + 1; j < ar.length; j++) {
                }

            return 0;
        }

        private static int[] negativeCount(int[] ar) {
            int[] res = new int[ar.length];
            if (ar[0] < 0)
                res[0] = -1;

            for (int i = 1; i < ar.length; i++)
                res[i] = ar[i] <= 0 ? res[i - 1] + 1 : res[i - 1];

            return res;
        }

        private static int[] negativeSum(int[] ar) {
            int[] res = new int[ar.length];

            if (ar[0] < 0)
                res[0] = ar[0];

            for (int i = 1; i < ar.length; i++)
                res[i] = ar[i] < 0 ? res[i - 1] + ar[i] : res[i - 1];

            return res;
        }

        private static int min(int[] ar) {
            int min = Integer.MAX_VALUE;

            for (int e : ar)
                min = Math.min(min, e);

            return min;
        }
    }
}