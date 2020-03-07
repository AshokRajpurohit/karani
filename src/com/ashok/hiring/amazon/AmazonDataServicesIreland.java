/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.amazon;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: Amazon Data Services Ireland
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AmazonDataServicesIreland {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        out.println(Integer.MAX_VALUE);
        out.println(Integer.MIN_VALUE);
        out.flush();
        while (true) {
            out.println(calculateMinimumDistance(in.readIntArray(in.readInt())));
            out.flush();
        }
    }

    private static int maxTurbulanceDuration(int[] heightMeasurements) {
        if (heightMeasurements.length == 1)
            return 1;

        if (heightMeasurements.length == 2)
            return heightMeasurements[0] == heightMeasurements[1] ? 1 : 2;

        int mtd = 1, ctd = 0; // maxTurbulanceDuration and currentTurbulanceDuration
        int diff = 0;
        for (int i = 1; i < heightMeasurements.length; i++) {
            if (ctd == 0) {
                diff = heightMeasurements[i] - heightMeasurements[i - 1];
                if (diff != 0) {
                    ctd = 2;
                }
            } else {
                int cdiff = heightMeasurements[i] - heightMeasurements[i - 1];
                if (cdiff == 0) {
                    ctd = 0;
                } else {
                    if ((cdiff ^ diff) < 0) {
                        ctd++;
                    } else {
                        ctd = 2;
                    }
                }
                diff = cdiff;
            }

            mtd = Math.max(mtd, ctd);
        }

        return mtd;
    }

    private static int calculateMinimumDistance(int[] ar) {
        if (ar.length == 1) return noAdjacentElementDistance();
        Arrays.sort(ar);
        long minimumDistance = Long.MAX_VALUE;
        for (int i = 1; i < ar.length; i++) {
            long distance = Math.abs(0L + ar[i] - ar[i - 1]);
            minimumDistance = Math.min(minimumDistance, distance);
        }

        if (minimumDistance <= distanceUpperLimit())
            return (int) minimumDistance;

        return distanceMoreThanLimit();
    }

    private static int distanceUpperLimit() {
        return 100000000;
    }

    private static int distanceMoreThanLimit() {
        return -1;
    }

    private static int noAdjacentElementDistance() {
        return -2;
    }
}
