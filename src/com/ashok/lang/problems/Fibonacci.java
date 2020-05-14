package com.ashok.lang.problems;

import com.ashok.lang.math.Matrix;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Fibonacci Series
 * Link: Standard Problem
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Fibonacci {
    private static Output out;
    private static InputReader in;
    private static long[][] matrix = new long[2][];
    private static Matrix base;
    private final static int iterLimit = 67, zero = 1;
    private final static String line =
        "---------------------------------------------------------";

    static {
        matrix[0] = new long[] { 1, 1 };
        matrix[1] = new long[] { 1, 0 };
        base = new Matrix(matrix);
    }

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        solve();
        out.close();
    }

    private static void solve() throws IOException {
        int mod = 1000000007;
        while (true) {
            out.println(termMatrix(in.readLong(), mod));
            out.flush();
        }
    }

    private static boolean check(long[] a, long[] b) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != b[i])
                return false;

        return true;
    }

    public static long term(long n) {
        if (n == 0)
            return zero;

        if (n < iterLimit)
            return termIter(n);

        return termMatrix(n);
    }

    public static long term(long n, long mod) {
        if (n == 0)
            return zero;

        if (n < iterLimit)
            return termIter(n, mod);

        return termMatrix(n, mod);
    }

    private static long termMatrix(long n) {
        Matrix res = base.pow(n - 1);
        return res.get(0, 0);
    }

    private static long termMatrix(long n, long mod) {
        Matrix res = base.pow(n - 1, mod);
        return res.get(0, 0);
    }

    private static long termIter(long n) {
        long a = 1, b = 1;

        while (n > 2) {
            n--;
            long c = a;
            a = a + b;
            b = c;
        }

        return a;
    }

    private static long termIter(long n, long mod) {
        if (n == 0)
            return zero;

        long a = 1, b = 1;
        while (n > 2) {
            n--;
            long c = a;
            a = (a + b) % mod;
            b = c;
        }

        return a % mod;
    }
}
