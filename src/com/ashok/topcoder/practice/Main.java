package com.ashok.topcoder.practice;


import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Problem Name:
 * Contest ID:
 * Link:
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Main {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new Output(System.out);

        //        String input = "input_file.in", output = "output_file.out";
        //        FileInputStream fip = new FileInputStream(input);
        //        FileOutputStream fop = new FileOutputStream(output);
        //        in = new InputReader(fip);
        //        out = new Output(fop);

        Main a = new Main();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
        }
    }

    private static long square(int n) {
        return 1L * n * n;
    }
}
