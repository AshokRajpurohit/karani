package com.ashok.projecteuler.problems;

import com.ashok.Params;
import com.ashok.lang.inputs.Output;

import java.io.FileNotFoundException;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P066 {
    public static void solve() throws FileNotFoundException {
        long max = 0, maxD = 0;
        Output output = new Output(Params.Path.property + "projecteuler\\problems\\P066.out");

        for (int d = 2; d <= 1000; d++) {
            if (isSquare(d))
                continue;

            for (int y = 1; y <= 100; y++) {
                long x = 1L * d * y * y + 1;

                if (!isSquare(x))
                    continue;

                x = (long) Math.sqrt(x);
                output.println("D: " + d + ", x: " + x + ", y: " + y);

                if (x > max) {
                    max = x;
                    maxD = d;
//                    output.println("D: " + d + ", x: " + x + ", y: " + y);
                }
                break;
            }
        }

        output.close();
        System.out.println(max + "\t" + maxD);
    }

    private static boolean isSquare(long n) {
        long digit = n % 10;

        if (digit != 1 && digit != 4 && digit != 5 && digit != 6 && digit != 9 && (digit == 0 && n % 100 != 0))
            return false;

        long r = (long) Math.sqrt(n);

        return r * r == n;
    }
}
