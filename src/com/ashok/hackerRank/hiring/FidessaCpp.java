/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring;

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
public class FidessaCpp {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        int n = in.readInt();
        int[] ar = in.readIntArray(n), br = in.readIntArray(n);
        out.println(FidessaAirport.findMinGates(ar, br, n));
        in.close();
        out.close();
    }

    final static class FidessaAirport {
        static int findMinGates(int[] arrivals, int[] departures, int flights) {
            Arrays.sort(departures);
            int time = arrivals[0], ai = 0, di = 0;
            int maxCount = 0, count = 0;
            int maxTime = departures[flights - 1];
            while (time <= maxTime) {
                while (ai < flights && arrivals[ai] == time) {
                    ai++;
                    count++;
                }

                while (di < flights && departures[di] < time) {
                    di++;
                    count--;
                }

                maxCount = Math.max(maxCount, count);
                if (ai < flights && di < flights)
                    time = Math.max(time + 1, Math.min(arrivals[ai], departures[di]));
                else if (ai == flights)
                    time = Math.max(time + 1, departures[di]);
                else
                    break;
            }

            return maxCount;
        }
    }
}
