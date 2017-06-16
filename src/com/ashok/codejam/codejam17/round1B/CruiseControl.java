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
 * Problem Name: Problem A. Steed 2: Cruise Control
 * Link: https://code.google.com/codejam/contest/8294486/dashboard#s=p0
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CruiseControl {
    private static Output out;// = new Output();
    private static InputReader in;// = new InputReader();
    private static final String CASE = "Case #";
    private static final String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\codejam\\codejam17\\round1B\\";

    public static void main(String[] args) throws IOException {
        in = new InputReader(path + "CruiseControl.in");
        out = new Output(path + "CruiseControl.out");
//        in = new InputReader();
//        out = new Output();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        StringBuilder sb = new StringBuilder();
        int t = in.readInt();

        for (int i = 1; i <= t; i++) {
            int d = in.readInt(), n = in.readInt();
            Horse[] horses = new Horse[n];

            for (int j = 0; j < n; j++)
                horses[j] = new Horse(in.readInt(), in.readInt());

            double speed = process(horses, d);
            sb.append(CASE).append(i).append(": ").append(speed).append('\n');
        }

        out.print(sb);
    }

    private static double process(Horse[] horses, int destination) {
        if (destination == 0)
            return 0;

        double time = 0;
        for (Horse horse : horses) {
            time = Math.max(time, (destination - horse.pos) / horse.speed);
        }

        return destination / time;
    }

    private static double calculateCollisionTime(Horse a, Horse b) {
        double relativeSpeed = b.speed - a.speed;
        double distance = b.pos - a.pos;

        return distance / relativeSpeed;
    }

    final static class Horse implements Comparable<Horse> {
        double pos, speed;

        Horse(int p, int s) {
            pos = p;
            speed = s;
        }

        @Override
        public int compareTo(Horse o) {
            return Double.compare(pos, o.pos);
        }

        void move(double time) {
            pos += speed * time;
        }

        public String toString() {
            return pos + ", " + speed;
        }
    }
}
