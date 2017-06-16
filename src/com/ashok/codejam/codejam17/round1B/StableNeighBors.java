/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.codejam17.round1B;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Problem B. Stable Neigh-bors
 * Link: https://code.google.com/codejam/contest/8294486/dashboard#s=p1
 * <p>
 * Half-baked solution.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class StableNeighBors {
    private static Output out;// = new Output();
    private static InputReader in;// = new InputReader();
    private static final String CASE = "Case #", IMPOSSIBLE = "IMPOSSIBLE";
    private static final String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\codejam\\codejam17\\round1B\\";

    public static void main(String[] args) throws IOException {
//        in = new InputReader(path + "StableNeighBors.in");
//        out = new Output(path + "StableNeighBors.out");
        in = new InputReader();
        out = new Output();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        for (int i = 1; i <= t; i++) {
            sb.append(CASE).append(i).append(": ");

            int n = in.readInt();
            int[] colorCounts = new int[6];
            for (int j = 0; j < 6; j++)
                colorCounts[j] = in.readInt();

            sb.append(process(colorCounts, n)).append('\n');
        }

        out.print(sb);
    }

    private static String process(int[] colorCounts, int total) {
        int half = total >>> 1;

        for (int e : colorCounts)
            if (e > half)
                return IMPOSSIBLE;

        Color[] colors = Color.values();
        for (int i = 0; i < colors.length; i++) {
            for (int j = i + 1; j < colors.length; j++) {
                if (colors[i].shareColor(colors[j])) {
                    if (colorCounts[i] + colorCounts[j] > half)
                        return IMPOSSIBLE;

                    for (int k = j + 1; k < colors.length; k++)
                        if (colors[j].shareColor(colors[k]) && (colorCounts[i] + colorCounts[j] + colorCounts[j] > half))
                            return IMPOSSIBLE;
                }
            }
        }

        String[] permutation = new String[total];
        int emptyIndex = 0, lastFilled = -1;

        for (int i = 0; i < 6; i++) {
            int index = getMaxIndex(colorCounts);
            if (colorCounts[index] == 0)
                break;

            String color = colors[index].toString();
            int count = colorCounts[index];
            colorCounts[index] = 0;

            while (count > 0) {
                count--;
                if (emptyIndex >= total || permutation[emptyIndex] != null)
                    return IMPOSSIBLE;

                permutation[emptyIndex] = color;
                emptyIndex += 2;
            }

            if (emptyIndex > lastFilled) {
                int temp = lastFilled;
                lastFilled = Math.max(lastFilled, emptyIndex - 2);
                emptyIndex = temp + 2;
            }
        }

        if (!validate(permutation))
            return IMPOSSIBLE;

        return toString(permutation);
    }

    private static boolean validate(String[] colors) {
        for (int i = 1; i < colors.length; i++) {
            if (Color.valueOf(colors[i]).shareColor(Color.valueOf(colors[i - 1])))
                return false;
        }

        return Color.valueOf(colors[0]).shareColor(Color.valueOf(colors[colors.length - 1]));
    }

    private static String toString(String[] ar) {
        StringBuilder sb = new StringBuilder();

        for (String str : ar)
            sb.append(str);

        return sb.toString();
    }

    private static int getMaxIndex(int[] ar) {
        int index = 0, maxValue = 0;

        for (int i = 0; i < ar.length; i++) {
            if (maxValue < ar[i]) {
                index = i;
                maxValue = ar[i];
            }
        }

        return index;
    }

    enum Color {
        R, O, Y, G, B, V;

        boolean multipleColor() {
            return this == O || this == G || this == V;
        }

        boolean shareColor(Color color) {
            if (this == color)
                return true;

            if (multipleColor() && color.multipleColor())
                return true;

            if (multipleColor())
                return color.shareColor(this);

            if (!color.multipleColor())
                return false;

            switch (this) {
                case B:
                    return color == G || color == V;
                case Y:
                    return color == O || color == G;
                case R:
                    return color == O || color == V;
                default:
                    throw new RuntimeException("Invalid colors: " + this + " and " + color);
            }
        }
    }
}
