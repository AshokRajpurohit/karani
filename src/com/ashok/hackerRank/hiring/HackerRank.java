/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.utils.Generators;

import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Problem Name: HackerRank SDE Hiring Challenge - Java
 * Link: Email Invite
 * Candidate: Ashok Rajpurohit
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class HackerRank {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        HackerRank a = new HackerRank();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            int[] ar = in.readIntArray(n);
            int p = in.readInt();
            out.println(BuyingShowTickets.waitingTime(ar, p));
            out.flush();
        }
    }

    private static void test() throws IOException {
        Random random = new Random();
        int n = in.readInt(), a = in.readInt(), b = in.readInt();
        while (true) {
            int[] ar = Generators.generateRandomIntegerArray(n, a, b);
            int p = random.nextInt(n);
        }
    }

    private static long waitingTimeQueue(int[] tickets, int p) {
        SortedQueue[] sortedQueues = toSortedQueue(tickets);
        final int jesseValue = tickets[p], n = tickets.length;
        PriorityQueue<SortedQueue> queue = new PriorityQueue<SortedQueue>((a, b) ->
                a.ticketCount == b.ticketCount ? a.index - b.index : a.ticketCount - b.ticketCount);

        int loops = 0, removed = 0;
        long time = 0;
        while (!queue.isEmpty()) {
            SortedQueue top = queue.peek();
            if (top.ticketCount != jesseValue) {
                time += 1L * (top.ticketCount - loops) * queue.size();
                loops = top.ticketCount;
            } else {
                int ahead = p - removed + 1, behind = queue.size() - ahead;
                time += 1L * ahead * (top.ticketCount - loops) + 1L * behind * (top.ticketCount - loops - 1);
                break;
            }

            if (top.index < p)
                removed++;

            queue.remove();
        }

        return time;
    }

    private static SortedQueue[] toSortedQueue(int[] ar) {
        int n = ar.length;
        SortedQueue[] sortedQueues = new SortedQueue[n];
        for (int i = 0; i < n; i++)
            sortedQueues[i] = new SortedQueue(i, ar[i]);

        return sortedQueues;
    }

    private static void waitingTime(int[] tickets, int p) {
        int minIndex = 0;
        int toSubtract = 0;
        int n = tickets.length;
        int t = 0;
        int jesseValue = tickets[p];

        SortedQueue[] sortedTktArr = new SortedQueue[n];

        for (int i = 0; i < tickets.length; i++) {
            SortedQueue obj = new SortedQueue(i, tickets[i]);
            sortedTktArr[i] = obj;

        }
        int pos = p;
        Arrays.sort(sortedTktArr);

        while (true) {

            SortedQueue obj = sortedTktArr[minIndex];
            int currentTktCount = obj.ticketCount - toSubtract;

            if (obj.index == p || obj.ticketCount == jesseValue) {
                t = t + (currentTktCount - 1) * n + (pos + 1);
                break;
            } else {
                if (obj.index < p)
                    pos--;
                t = t + currentTktCount * n;
            }
            toSubtract = toSubtract + currentTktCount;
            minIndex++;
            n--;
        }
    }

    final static class SortedQueue implements Comparable<SortedQueue> {
        final int index;
        int ticketCount;

        SortedQueue(int i, int v) {
            index = i;
            ticketCount = v;
        }

        @Override
        public int compareTo(SortedQueue o) {
            return ticketCount - o.ticketCount;
        }
    }

    final static class ArrangeWords {
        static String arrange(String sentence) {
            String[] words = sentence.split("[\\s\\.]");
            words[0] = words[0].toLowerCase();
            String[] formattedWords = Arrays.stream(words)
                    .filter(s -> s.length() > 0) // remove empty strings.
                    .sorted((a, b) -> a.length() - b.length())
                    .toArray(t -> new String[t]);

            char[] firstWordAr = formattedWords[0].toCharArray();
            firstWordAr[0] = Character.toUpperCase(firstWordAr[0]);
            formattedWords[0] = new String(firstWordAr);
            return String.join(" ", formattedWords) + ".";
        }
    }

    final static class BuyingShowTickets {
        static long waitingTime(int[] tickets, int p) {
            int jessiTickets = tickets[p];
            long time = jessiTickets;
            for (int i = 0; i < p; i++)
                time += Math.min(jessiTickets, tickets[i]);

            if (jessiTickets == 1)
                return time;

            for (int i = p + 1; i < tickets.length; i++)
                time += Math.min(jessiTickets - 1, tickets[i]);

            return time;
        }
    }
}
