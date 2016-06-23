package com.ashok.projecteuler.problems;

import com.ashok.lang.math.ModularArithmatic;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P069 {

    public static void solve() throws IOException {
        InputReader in = new InputReader();
        Output out = new Output();
        out.println("Enter the size: ");
        out.flush();

        int size = in.readInt();
        long time = System.currentTimeMillis();
        int[] phi = ModularArithmatic.totientList(size);

        double max = 0.0;
        int maxn = 0;
        for (int i = 1; i <= size; i++) {
            double value = 1.0 * i / phi[i];

            if (value > max) {
                maxn = i;
                max = value;
            }
        }

        out.println("Max ratio: " + max + " for n: " + maxn);
        out.println("time taken: " + (System.currentTimeMillis() - time));
        out.close();
    }
}
