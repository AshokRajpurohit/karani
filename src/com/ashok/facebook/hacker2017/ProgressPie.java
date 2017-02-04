/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.facebook.hacker2017;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;

/**
 * Problem Name: Progress Pie
 * Link: https://www.facebook.com/hackercup/problem/1254819954559001/
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ProgressPie {
    private static Output out;// = new PrintWriter(System.out);
    private static InputReader in;// = new InputReader();
    private static final String CASE = "Case #", WHITE = "white", BLACK = "black";
    private static final int RADIUS = 50;
    private static final double EPSILON = 0.000001;

    public static void main(String[] args) throws IOException {
        String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\facebook\\hacker2017\\";
        in = new InputReader(path + "ProgressPie.in");
        out = new Output(path + "ProgressPie.out");
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 4);

        for (int i = 1; i <= t; i++) {
            boolean res = pointInsideProgress(in.readInt(), in.readInt(), in.readInt());
            append(sb, i, res);
        }

        out.print(sb);
    }

    private static boolean pointInsideProgress(int progress, int x, int y) {
        if (distanceFromCentre(x, y) > EPSILON + RADIUS) // when point is out of circle, it will always be white.
            return false;

        double progressAngle = 3.6 * progress, angleForPoint = radianToDegree(getProgressForPoint(x, y));

        return progressAngle >= angleForPoint;
    }

    /**
     * Returns progress for the point in terms of angle covered.
     *
     * @param x
     * @param y
     * @return
     */
    private static double getProgressForPoint(int x, int y) {
        double distance = distanceFromCentre(x, y);

        if (x == 50) {
            if (y >= 50)
                return 0;

            return 180 - radianToDegree(EPSILON / distance);
        }

        if (y == 50) {
            if (x >= 50)
                return 90 - radianToDegree(EPSILON / distance);

            return 270 - radianToDegree(EPSILON / distance);
        }

        double dx = y - 50, dy = x - 50;
        double angle = Math.atan(dy / dx);

        if (angle < 0)
            angle += Math.PI * 2;

        return angle - radianToDegree(EPSILON / distance);
    }

    private static double radianToDegree(double angleInRadians) {
        return angleInRadians * 180 / Math.PI;
    }

    private static double distanceFromCentre(int x, int y) {
        return distance(x, y, 50, 50);
    }

    private static double distance(int x, int y, int a, int b) {
        return Math.sqrt(square(x - a) + square(y - b));
    }

    private static int square(int a) {
        return a * a;
    }

    private static void append(StringBuilder sb, int testCase, boolean res) {
        sb.append(CASE).append(testCase).append(": ").append(res ? BLACK : WHITE).append('\n');
    }
}
