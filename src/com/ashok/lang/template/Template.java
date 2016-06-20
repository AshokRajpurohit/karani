package com.ashok.lang.template;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Template {
    private static Output out;
    private static InputReader in;

    public static void main(String[] args) throws IOException {
        in = new InputReader();
        out = new Output();

        //        String path =
        //            "D:\\GitHub\\Competetions\\ProjectEuler\\Client\\src\\Problems\\", input =
        //            "input_file.in", output = "output_file.out";
        //        in = new InputReader(path + input);
        //        out = new Output(path + output);

        Template a = new Template();
        try {
            a.solve();
        } catch (IOException ioe) {
            // do nothing
        }
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            // write code here, call function, whatever you do
        }
    }
}
