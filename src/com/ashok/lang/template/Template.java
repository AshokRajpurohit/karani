package com.ashok.lang.template;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Template {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
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
            int n = in.readInt();
            int[] ar = in.readIntArray(n), copy = in.readIntArray(n);
            Arrays.sort(ar);
            Arrays.sort(copy);
            out.print(ar);
            out.print(copy);
            out.flush();
        }
    }
}
