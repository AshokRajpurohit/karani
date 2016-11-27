package com.ashok.techgig;

import java.util.PriorityQueue;

/**
 * I do not intend to make the code short. My focus is clean, readable code.
 * Even a novice should be able to understand what this code is doing, without
 * explicit documentation.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Synechron {

    public static int[] getJoinedPipes(int n, int[] pipes) {
        if (n <= 1)
            return new int[]{0};

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int e : pipes)
            priorityQueue.add(e);

        int[] res = new int[n - 1];
        int index = 0;

        while (priorityQueue.size() > 1) {
            int first = priorityQueue.poll(), second = priorityQueue.poll();
            int newPipe = first + second;

            res[index++] = newPipe;
            priorityQueue.add(newPipe);
        }

        return res;
    }
}
