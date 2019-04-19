/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.codejam17.round1C;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Problem Name: Problem A. Ample Syrup
 * Link: https://code.google.com/codejam/contest/3274486/dashboard#s=p0
 *
 * Note: Not giving correct answer.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AmpleSyrup {
    private static Output out;// = new Output();
    private static InputReader in;// = new InputReader();
    private static final String CASE = "Case #";
    private static final String path = "C:\\Projects\\others\\karani\\src\\com\\ashok\\codejam\\codejam17\\round1C\\AmpleSyrup";

    public static void main(String[] args) throws IOException {
        in = new InputReader(path + ".in");
        out = new Output(path + ".out");
//        in = new InputReader();
//        out = new Output();
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= t; i++) {
            sb.append(CASE).append(i).append(": ");
            int n = in.readInt(), k = in.readInt();
            Pancake[] pancakes = new Pancake[n];
            for (int j = 0; j < n; j++) {
                pancakes[j] = new Pancake(in.readInt(), in.readInt());
            }

            sb.append(process(pancakes, k)).append('\n');
        }

        out.print(sb);
    }

    private static double process(Pancake[] pancakes, int count) {
        Arrays.sort(pancakes, PANCAKE_RADIUS_COMPARATOR);
        if (count == 1)
            return Math.PI * pancakes[pancakes.length - 1].getArea();

        if (count == pancakes.length) {
            long area = 0;
            for (Pancake pancake : pancakes)
                area += pancake.getSideArea();

            area += pancakes[pancakes.length - 1].getSurfaceArea();
            return Math.PI * area;
        }

        PancakeHeap heap = new PancakeHeap(count - 1);
        for (int j = 0; j < count - 1; j++)
            heap.put(pancakes[j]);

        long maxArea = heap.area + pancakes[count - 1].getSurfaceArea();
        for (int j = count - 1; j < pancakes.length; j++) {
            maxArea = Math.max(heap.area + pancakes[j].getArea(), maxArea);
            heap.put(pancakes[j]);
        }

        return Math.PI * maxArea;
    }

    final static class Pancake {
        final int radius, height;

        Pancake(int r, int h) {
            radius = r;
            height = h;
        }

        long getSurfaceArea() {
            return 1L * radius * radius;
        }

        long getSideArea() {
            return 2L * radius * height;
        }

        long getArea() {
            return getSideArea() + getSurfaceArea();
        }

        public String toString() {
            return "[" + radius + ", " + height + "]";
        }
    }

    final static class PancakeHeap {
        long area = 0;
        final int size;
        final PriorityQueue<Pancake> queue;

        PancakeHeap(int size) {
            this.size = size;
            queue = new PriorityQueue<>(size, PANCAKE_HEIGHT_COMPARATOR);
        }

        void put(Pancake pancake) {
            if (size == 0)
                return;

            if (queue.size() == size)
                area -= queue.poll().getSideArea();

            queue.add(pancake);
            area += pancake.getSideArea();
        }
    }

    public static final Comparator<Pancake> PANCAKE_HEIGHT_COMPARATOR = new PancakeHeightRadiusComparator();

    final static class PancakeHeightRadiusComparator implements Comparator<Pancake> {
        @Override
        public int compare(Pancake o1, Pancake o2) {
            return Long.compare(o1.getSideArea(), o2.getSideArea());
        }
    }

    public static final Comparator<Pancake> PANCAKE_RADIUS_COMPARATOR = new PancakeRadiusComparator();

    final static class PancakeRadiusComparator implements Comparator<Pancake> {
        @Override
        public int compare(Pancake o1, Pancake o2) {
            return o1.radius - o2.radius;
        }
    }
}
