package com.ashok.topcoder;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.topcoder.contest.SRM698;

import java.io.IOException;

/**
 * Problem Name: call the method for contest problem
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CodeRunner {
    private static InputReader in = new InputReader();
    private static Output out = new Output();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        RandomStrings randomStrings = new RandomStrings();
        while (true) {
            String input = randomStrings.nextStringabc(in.readInt());
            long time = System.currentTimeMillis();
            out.println(SRM698.RepeatStringEasy.maximalLength(input));
            out.println(System.currentTimeMillis() - time);
            out.flush();
        }
    }
}
