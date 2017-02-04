/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.lang.template;

import com.ashok.lang.dsa.GroupOperator;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.Prime;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem Name: Link:
 * <p>
 * For full implementation please see {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Test {
    private static final String path = "C:/Projects/karani/src/com/ashok/lang/template/";
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            play();
        } finally {
            out.close();
        }
    }

    private static void play() throws IOException, InterruptedException {
        out.println("Ashok");
        out.flush();
        while (true) {
            int a = 10;

            loop:
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (a == 10)
                        break loop;

                    out.println(a);
                    out.flush();
                }
        }
    }

    private static int[][] generateInput(int n, int m) {
        int[][] res = new int[n][];

        for (int i = 0; i < n; i++)
            res[i] = Generators.generateRandomIntegerArray(m, -2, 10);

        return res;
    }

    private static String convert(int[][] ar) {
        StringBuilder sb = new StringBuilder();
        for (int[] a : ar) {
            for (int e : a)
                sb.append(e).append('@');

            sb.deleteCharAt(sb.length() - 1);
            sb.append('#');
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static void solve() throws IOException {
        while (true) {
            out.print(Prime.primesInRange(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    private static void updateInt(Integer n) {
        n++;
    }

    static class Custom {
        int value = 0;
    }

    static class CustomExtend extends Custom {
        int value = 10;
    }

    public static void puzzle(int totalNumbersCount) {

        List<Integer> numberList = new ArrayList<Integer>();
        for (int i = 0; i < totalNumbersCount; i++) {
            numberList.add(i + 1);
        }


        while (true) {
            numberList = copyNumbers(numberList);
            numberList.notify();
            if (numberList.size() == 1) {
                System.out.println("Number Remaining is " + numberList.get(0));
                break;
            }
        }
    }

    private static List<Integer> copyNumbers(List<Integer> numberList) {

        List<Integer> copiedNumberList = new ArrayList<Integer>();
        for (int i = 1; i < numberList.size(); i = i + 2) {
            copiedNumberList.add(numberList.get(i));
        }
        return copiedNumberList;
    }


    final static class Operator implements GroupOperator<Long> {

        @Override
        public Long operation(Long first, Long second) {
            return first + second;
        }

        @Override
        public Long inverseOperation(Long first, Long second) {
            return first - second;
        }

        @Override
        public Long newInstance() {
            return 0L;
        }
    }

    private static void normalizeIndex(int[] start, int[] end) {
        for (int i = 0; i < start.length; i++)
            if (start[i] > end[i]) {
                int t = start[i];
                start[i] = end[i];
                end[i] = t;
            }
    }
}
