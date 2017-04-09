/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.codejam17.qualification;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.ArrayUtils;

import java.io.IOException;

/**
 * Problem Name: Problem A. Oversized Pancake Flipper
 * Link: https://code.google.com/codejam/contest/3264486/dashboard#s=p0
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class OversizedPancakeFlipper {
    private static Output out;// = new Output();
    private static InputReader in;// = new InputReader();
    private static final String CASE = "Case #";
    private static final String path
            = "C:\\Projects\\others\\karani\\src\\com\\ashok\\codejam\\codejam17\\qualification\\OversizedPancakeFlipperLarge";
    private static final int HAPPY = '+', UNHAPPY = '-', XOR = HAPPY ^ UNHAPPY;
    private static final String IMPOSSIBLE = "IMPOSSIBLE";

    public static void main(String[] args) throws IOException {
//        in = new InputReader();
//        out = new Output();
        in = new InputReader(path + ".in");
        out = new Output(path + ".out");
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        for (int i = 1; i <= t; i++) {
            sb.append(CASE).append(i).append(": ");
            sb.append(process(in.read().toCharArray(), in.readInt())).append('\n');
        }

        out.print(sb);
    }

    private static Object process(char[] pancakes, int flipperSize) {
        if (allHappy(pancakes))
            return 0;

        char[] copy = pancakes.clone();
        int res = 0, loops = 0;
        while (loops < 8) {
            loops++;
            res += flipLeftToRight(copy, flipperSize);

            if (allHappy(copy))
                return res;

            res += flipRightToLeft(copy, flipperSize);

            if (equals(pancakes, copy))
                return IMPOSSIBLE;

            if (allHappy(copy))
                return res;
        }

        return IMPOSSIBLE;
    }

    private static int flipLeftToRight(char[] pancakes, int flipperSize) {
        int flipCount = 0;
        for (int i = 0; i <= pancakes.length - flipperSize; i++) {
            if (pancakes[i] == UNHAPPY) {
                flipCount++;
                // let's flip from here.
                for (int j = i; j < i + flipperSize; j++)
                    pancakes[j] = (char) (pancakes[j] ^ XOR);
            }
        }

        return flipCount;
    }

    private static int flipRightToLeft(char[] pancakes, int flipperSize) {
        ArrayUtils.reverse(pancakes);
        int count = flipLeftToRight(pancakes, flipperSize);
        ArrayUtils.reverse(pancakes);

        return count;
    }

    private static boolean equals(char[] a, char[] b) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != b[i])
                return false;

        return true;
    }

    private static boolean allHappy(char[] pancakes) {
        for (char pancake : pancakes)
            if (pancake != HAPPY)
                return false;

        return true;
    }
}
