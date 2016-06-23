package com.ashok.projecteuler.problems;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class P074 {
    private static LinkedList<Integer> temp = new LinkedList<Integer>();
    private static LinkedList<Integer>[] listArray =
            (LinkedList<Integer>[]) Array.newInstance(temp.getClass(), 2177281); // = 6 * 9!
    private static boolean[] processing = new boolean[2177281];

    public static void solve(int n) {
    }
}
