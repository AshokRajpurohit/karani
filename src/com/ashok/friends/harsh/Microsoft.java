/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.harsh;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Microsoft {
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
        char cr = '\r';
        out.println(cr);
        out.println((int)cr);
        out.flush();
        in.readInt();
        while (true) {
            int[] ar = in.readIntArray(in.readInt());
            List<Integer> list = Arrays.stream(ar).mapToObj(n -> n).collect(Collectors.toList());
            int[] br = in.readIntArray(in.readInt());
            List<Integer> list1 = Arrays.stream(br).mapToObj(n -> n).collect(Collectors.toList());
            out.println(UniversityCareerFair.maxEvents(list, list1));
            out.flush();
        }
    }

    /**
     * Throttling Gateway.
     *
     * @param requestTime
     * @return
     */
    static int droppedRequests(List<Integer> requestTime) {
        TimeWindow secondWindow = new TimeWindow(1, 3);
        TimeWindow minuteWindow = new TimeWindow(60, 60);
        TimeWindow tenSecondWindow = new TimeWindow(10, 20);

        List<TimeWindow> timeWindows = Arrays.asList(secondWindow, tenSecondWindow, minuteWindow);
        AtomicInteger counter = new AtomicInteger(0);
        requestTime
                .stream()
                .filter(time -> timeWindows.stream().allMatch(timeWindow -> timeWindow.canTake(time)))
                .forEach(time -> {
                    timeWindows.forEach(timeWindow -> timeWindow.add(time));
                    counter.incrementAndGet();
                });

        return requestTime.size() - counter.get();
    }

    final static class TimeWindow {
        final int duration, limit;
        private final Queue<Integer> queue = new LinkedList<>();

        TimeWindow(final int duration, final int limit) {
            this.duration = duration;
            this.limit = limit;
        }

        public boolean canTake(int time) {
            refresh(time);
            return queue.size() < limit;
        }

        private void refresh(int currentTime) {
            while (!queue.isEmpty() && !validDuration(currentTime + 1 - queue.peek()))
                queue.remove();
        }

        private boolean validDuration(int duration) {
            return duration <= this.duration;
        }

        public void add(int time) {
            queue.add(time);
        }

    }

    final static class UniversityCareerFair {
        /*
         * Complete the 'maxEvents' function below.
         *
         * The function is expected to return an INTEGER.
         * The function accepts following parameters:
         *  1. INTEGER_ARRAY arrival
         *  2. INTEGER_ARRAY duration
         */

        public static int maxEvents(List<Integer> arrival, List<Integer> duration) {
            int count = arrival.size();
            if (count == 1) return 1;
            int max = arrival.stream().max(Integer::compareTo).get();
            int[] freeCount = new int[max + 1], fillCount = new int[max + 1];
            Iterator<Integer> aIter = arrival.iterator(), dIter = duration.iterator();
            while (aIter.hasNext()) {
                // use and update the rest.
                int arrivalTime = aIter.next(), durationTime = dIter.next();
                int endTime = Math.min(arrivalTime + durationTime, max + 1);
                int countSoFar = freeCount[arrivalTime];
                for (int time = arrivalTime; time < endTime; time++) {
                    fillCount[time] = Math.max(fillCount[time], countSoFar + 1);
                }

                for (int time = arrivalTime + durationTime; time <= max; time++) {
                    freeCount[time]++;
                }
            }

            return Math.max(
                    Arrays.stream(freeCount).max().getAsInt(),
                    Arrays.stream(fillCount).max().getAsInt()
            );
        }
    }
}
