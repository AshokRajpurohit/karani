package com.ashok.projectEuler.problems;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * @author Ashok Rajpurohit
 */

public class Problem {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        String path =
                "D:\\GitHub\\Competetions\\ProjectEuler\\Client\\src\\Problems\\", input =
                "P054.in", output = "P054.out";
        //        in = new InputReader(path + input);
        //        out = new Output(path + output);


        Problem a = new Problem();
        a.solve();
        out.close();
    }

    public void solve() throws IOException {
        boolean cont = true;
        long sum = 0;
        while (cont) {
            //            cont = false;
            //            long v = in.readLong();
            //            sum += v;
            //            out.println(v + ", " + sum);
            out.println(P001.solve(in.readInt() - 1, in.readInt(), in.readInt()));
            out.flush();
        }
    }
}
