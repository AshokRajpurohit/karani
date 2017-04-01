/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerearth.Contest;

import com.ashok.lang.inputs.InputReader;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Problem Name:
 * Link: https://www.hackerearth.com/challenge/competitive/accenture-hack-diva-professional/
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AccentureHackDiva {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        CheckIPAddress.solve();
        LinesAndSpheres.solve();
    }

    private static long getMax(long[] ar) {
        long max = ar[0];

        for (long e : ar)
            max = Math.max(e, max);

        return max;
    }

    private static long getMin(long[] ar) {
        long min = ar[0];

        for (long e : ar)
            min = Math.min(e, min);

        return min;
    }

    /**
     * Problem Name: Lines and spheres
     * Link: https://www.hackerearth.com/challenge/competitive/accenture-hack-diva-professional/algorithm/help-ankit/
     */
    final static class LinesAndSpheres {
        public static void solve() throws IOException {
            int t = in.readInt();

            while (t > 0) {
                t--;

                long L = in.readLong();
                int N = in.readInt();

                if (N == 0) {
                    out.println("0 0");
                    continue;
                }

                long[] ar = in.readLongArray(N);
                long min = 0, max = 0, maxValue = getMax(ar);

                // let's calculate min value first.
                for (long e : ar)
                    min = Math.max(min, Math.min(e, L - e));

                long minValue = getMin(ar);

                // let's calculate the max possible value.
                max = Math.max(L - minValue, maxValue);
                out.println(min + " " + max);
            }
        }
    }

    /**
     * Problem Name: Check the address
     * Link: https://www.hackerearth.com/challenge/competitive/accenture-hack-diva-professional/algorithm/sahils-computer-address-18/
     */
    final static class CheckIPAddress {
        final static String yes = "YES", no = "NO";

        public static String solve() throws IOException {
            String ipAddress = in.read();
            String[] numbers = ipAddress.split("[.]");

            if (numbers.length != 4)
                return no;

            for (String num : numbers) {
                int n = Integer.parseInt(num);

                if (n < 0 || n > 255)
                    return no;
            }

            return yes;
        }
    }
}
