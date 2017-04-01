/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankitSoni;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Problem Name: Ankit Soni
 * Link: Private Link
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ReportGarden {
    private static PrintWriter out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        WordCount.solve();
        out.close();
    }

    final static class DecimalZip {
        private static void solve() throws IOException {
            int a = in.readInt(), b = in.readInt();

            char[] first = Integer.toString(a).toCharArray(), second = Integer.toString(b).toCharArray();

            char[] res = new char[first.length + second.length];
            int index = 0, index1 = 0, index2 = 0;

            while (index1 < first.length && index2 < second.length) {
                res[index++] = first[index1++];
                res[index++] = second[index2++];
            }

            while (index1 < first.length)
                res[index++] = first[index1++];

            while (index2 < second.length)
                res[index++] = second[index2++];

            String value = String.valueOf(res);
            int result = -1;

            if (value.length() < 10 && Integer.valueOf(value) < 100000000)
                result = Integer.valueOf(value);

            out.println(result);
        }
    }

    final static class WordCount {
        private final static String sentanceSplitters = "[.?!]";

        private static void solve() throws IOException {
            while (true) {
                String s = in.readLine();
                String[] sentances = s.split(sentanceSplitters.toString());

                int max = 0;
                for (String sentance : sentances) {
                    sentance = sentance.trim();
                    sentance = sentance.replace("  ", " ");
                    String[] words = sentance.split(" ");
                    max = Math.max(max, count(words));
                }

                out.println(max);
                out.flush();
            }
        }

        private static int count(String[] words) {
            int count = 0;
            for (String word : words) {
                if (word.trim().length() > 0)
                    count++;
            }

            return count;
        }
    }
}
