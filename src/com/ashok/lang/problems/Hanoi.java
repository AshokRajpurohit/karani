package com.ashok.lang.problems;

import com.ashok.lang.math.Matrix;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.Power;

import java.io.IOException;

/**
 * Problem Name: Moves in Tower of Hanoi Problem.
 *
 * How many moves you need to perform in order to move n disk from one
 * needle to another using a third needle (intermediate needle).
 *
 * We have three needles A, B and C. Currently all the disks are on
 * needle A. We have to move these disks to needle B using C.
 * Let's say we number of moves needed to move n elements is F(n).
 * Think how can we move n elements effectively from needle A to B.
 *
 * 1. We move all but last (n - 1) disks to needle C.
 * 2. We move the last disk on A to B.
 * 3. We move all the disks on C to B using the same procedure used in step 1.
 *
 * Formulating these steps, moves needed for n disks
 *      F(n) = F(n - 1) + 1 + F(n - 1)
 *      F(n) = 2 * F(n - 1) + 1
 *
 * Now we know the recursive formula for n.
 * There are three ways to implement it.
 *
 * 1. Calling function recursively.
 * 2. Using Matrix Exponentiation.
 * 3. Simplifying the formula using recurring series
 *
 * 1. For small value of n first method (Recursive) is more efficient as there
 * are extra multiplications in Matrix Multiplication.
 *
 * 2. But for sufficiently large value of n let's say 10^9 we can't call the
 * function n times, in that case Matrix Exponentiation will help us.
 * We can use Power by Square Method to reduce the number of multiplication to
 * order of log(n).
 *
 * 3. Using recurring series we can prove that
 * F(n) = 2^n - 1
 *
 * Please see Higher Algebra by H. S. Hall & S. R. Knight or
 * Higher Algebra by Bernard and Child.
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * @see Power
 * @see Matrix
 */
public class Hanoi {
    private static Output out;
    private static InputReader in;
    private static Matrix base;
    private static long[][] matrixArray = new long[2][];

    static {
        matrixArray[0] = new long[] { 2, 0 };
        matrixArray[1] = new long[] { 1, 1 };
        base = new Matrix(matrixArray);
    }

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        Hanoi a = new Hanoi();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        int mod = 1000000007;
        while (true) {
            out.println(steps(in.readInt()));
            out.flush();
        }
    }

    /**
     * Moves disks from Pole A to Pole C
     *
     * @param size
     * @return
     */
    public static String steps(int size) {
        if (size <= 0)
            return "Paagal hai kyha?";

        if (size > 10)
            return "Bhad mein jaa, this is too big";

        if (size == 1) {
            return "1: A -> C\n";
        }

        StringBuilder sb = new StringBuilder();
        int step = 1;

        step = move(sb, 'A', 'B', 'C', 1, size - 1);
        sb.append(step + ": ").append("A -> C\n");

        move(sb, 'B', 'C', 'A', step + 1, size - 1);

        return sb.toString();
    }

    private static int move(StringBuilder sb, char from, char to,
                            char intermediate, int step, int size) {
        if (size == 1) {
            sb.append(step + ": " + from + " -> " + to + "\n");
            return step + 1;
        }

        step = move(sb, from, intermediate, to, step, size - 1);
        sb.append(step + ": " + from + " -> " + to + "\n");

        step = move(sb, intermediate, to, from, step + 1, size - 1);
        return step;
    }

    public static long Moves(long n, long mod) {
        return recurringSeries(n, mod);
    }

    public static long Moves(long n) {
        return recurringSeries(n);
    }

    private static long iterative(long n, long mod) {
        if (n == 0)
            return 0;

        long res = 1;

        for (int i = 1; i < n; i++) {
            res = ((res << 1) + 1) % mod;
        }

        return res;
    }

    private static long iterative(long n) {
        if (n == 0)
            return 0;

        long res = 1;

        for (int i = 1; i < n; i++) {
            res = (res << 1) + 1;
        }

        return res;
    }

    private static long matrixMethod(long n, long mod) {
        if (n == 0)
            return 0;

        if (n == 1)
            return 1;

        if (n == 2)
            return 3;

        Matrix res = Matrix.pow(base, n - 1, mod);
        return (res.get(0, 0) + res.get(1, 0)) % mod;
    }

    private static long matrixMethod(long n) {
        if (n == 1)
            return 1;

        if (n == 2)
            return 3;

        Matrix res = Matrix.pow(base, n - 1);
        return res.get(0, 0) + res.get(1, 0);
    }

    private static long recurringSeries(long n, long mod) {
        if (n < 63)
            return ((1L << n) - 1) % mod;


        return (mod + Power.pow(2, n, mod) - 1) % mod;
    }

    private static long recurringSeries(long n) {
        return (1L << n) - 1;
    }
}
