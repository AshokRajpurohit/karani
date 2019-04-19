/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.vinash;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Vinash {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        Vinash a = new Vinash();
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
            int L = in.readInt();
            long[] ar = in.readLongArray(n);
            out.println(findHowMuchCentsToSpend(n, L, ar));
            out.flush();
        }
    }

    static void findMaximumEvents(String[] eventDetails) {
        if (eventDetails.length == 1) {
            System.out.println(1);
            return;
        }

        EventProcessor processor = new EventProcessor(eventDetails);
        System.out.println(processor.getMaximumEvents());
    }

    private static int max(int[] ar) {
        int max = ar[0];
        for (int e : ar)
            max = Math.max(max, e);

        return max;
    }

    static long findHowMuchCentsToSpend(int n, int L, long[] c) {
        normalize(c);
        long res = 0;
        int index = 0;
        long val = 1, var = 1;
        while (var <= L && index < n) {
            if ((val & L) != 0) {
                res += c[index];
            }

            if (var != L)
                val += c[index];
            else
                val = c[index];

            index++;
            var <<= 1;
        }

        res = Math.min(res, val);

        while (index < n) {
            res = Math.min(res, c[index++]);
        }

        return res;
    }

    private static void normalize(long[] ar) {
        for (int i = 1; i < ar.length; i++) {
            ar[i] = Math.min(ar[i], ar[i - 1] << 1);
        }
    }

    private static long sum(long[] ar, int from, int end) {
        long sum = 0;
        for (int i = from; i <= end; i++) sum += ar[i];
        return sum;
    }

    private static long sum(long[] ar) {
        return sum(ar, 0, ar.length - 1);
    }

    final static class EventProcessor {
        final Event[] events;
        final int[] eventCountMap;
        final LinkedList<Integer>[] startTimeEventMap;
        boolean processed = false;

        EventProcessor(String[] eventDetails) {
            events = toEvents(eventDetails);
            Arrays.sort(events, (a, b) -> a.startTime - b.startTime);
            startTimeEventMap = getTimeEventMap(events);
            eventCountMap = new int[events.length]; // map for how many events can be attended if started from this event.
        }

        private int getMaximumEvents() {
            process();
            return max(eventCountMap);
        }

        private synchronized void process() {
            if (processed)
                return;

            for (int i = 0; i < events.length; i++)
                process(i);

            processed = true;
        }

        private void process(int eventIndex) {
            if (eventCountMap[eventIndex] != 0) // already processed.
                return;

            int endTime = events[eventIndex].endTime;
            int max = 0;
            for (int time = endTime + 1; time <= 24; time++) {
                startTimeEventMap[time].stream().forEach((t) -> process(t));
                max = startTimeEventMap[time].stream().map((t) -> eventCountMap[t]).max(Integer::compare).orElse(0);
                for (int eventId : startTimeEventMap[time]) {
                    process(eventId);
                    max = Math.max(max, eventCountMap[eventId]);
                }
            }

            eventCountMap[eventIndex] = max + 1;
        }
    }

    private static LinkedList<Integer>[] getTimeEventMap(Event[] events) {
        LinkedList<Integer>[] timeEventMap = new LinkedList[25];
        for (int i = 0; i < 25; i++)
            timeEventMap[i] = new LinkedList<>();

        int index = 0;
        for (Event event : events)
            timeEventMap[event.startTime].addLast(index++);

        return timeEventMap;
    }

    private static Event[] toEvents(String[] eventDetails) {
        int len = eventDetails.length;
        Event[] events = new Event[len];
        for (int i = 0; i < len; i++) {
            String[] params = eventDetails[i].split(" ");
            events[i] = new Event(Integer.valueOf(params[0]), Integer.valueOf(params[1]));
        }

        return events;
    }

    final static class Event {
        final int startTime, endTime;

        Event(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    final static class ReformattingDates {
        private static String[] months = {
                "jan", "feb", "mar", "apr", "may", "jun",
                "jul", "aug", "sep", "oct", "nov", "dec"
        };

        private static Map<String, String> monthMap = new HashMap<>();

        static {
            int month = 1;
            for (String m : months) {
                monthMap.put(m, month < 10 ? "0" + month : "" + month);
                month++;
            }
        }

        static String[] reformatDates(String[] dates) {
            return Arrays.stream(dates)
                    .map((t) -> reformatDate(t))
                    .toArray((t) -> new String[t]);
        }

        private static String reformatDate(String s) {
            String[] dateParts = s.split(" ");
            StringBuilder sb = new StringBuilder(s.length());
            sb.append(dateParts[2]).append('-')
                    .append(getMonth(dateParts[1]))
                    .append('-').append(getDay(dateParts[0]));

            return sb.toString();
        }

        private static String getMonth(String month) {
            month = month.toLowerCase();
            if (monthMap.containsKey(month))
                return monthMap.get(month);

            throw new RuntimeException("Invalid month name: " + month);
        }

        private static String getDay(String day) {
            int d = day.charAt(0) - '0';
            if (day.charAt(1) >= '0' && day.charAt(1) <= '9')
                d = d * 10 + day.charAt(1) - '0';

            return d < 10 ? "0" + d : "" + d;
        }
    }

    private static class Parser {
        static boolean isBalanced(String s) {
            char[] chars = s.toCharArray();
            int len = chars.length;

            if (closing(chars[0]) || opening(chars[len - 1]))
                return false;

            Stack<Character> stack = new Stack<>();
            for (char bracket : chars) {
                if (opening(bracket))
                    stack.push(bracket);
                else {
                    if (stack.empty())
                        return false;

                    char top = stack.pop();
                    if (!opening(top) || !complements(top, bracket))
                        return false;
                }
            }

            return stack.empty();
        }

        private static boolean complements(int opening, int closing) {
            return opening == '(' ? closing == ')' : closing == '}';
        }

        private static boolean valid(char bracket) {
            return opening(bracket) || closing(bracket);
        }

        private static boolean opening(char bracket) {
            return bracket == '(' || bracket == '{';
        }

        private static boolean closing(char bracket) {
            return bracket == ')' || bracket == '}';
        }
    }
}
