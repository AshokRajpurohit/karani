package com.ashok.projecteuler.problems;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.HashMap;

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
        HashMap<String, Integer> map = new HashMap<>();
        map.containsKey("le");
        while (true) {
            int n = in.readInt();
            long time = System.currentTimeMillis();
            int[] ar = Generators.generateRandomIntegerArray(in.readInt(), 10);
            out.print(ar);
            out.println(P074.maximumAmount(ar, in.readInt()));
            out.println(System.currentTimeMillis() - time);
            out.flush();
        }
    }
}
