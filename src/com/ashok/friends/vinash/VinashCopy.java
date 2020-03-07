/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.vinash;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class VinashCopy {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        VinashCopy a = new VinashCopy();
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
            String[] ar = in.readLineArray(n);
            findMaximumEvents(ar);
            out.println(in.read());
            out.flush();
        }
    }

    /*
     * Complete the findMaximumEvents function below.
     */
    static class Event implements Comparable<Event> {
        Integer startTime;
        Integer endTime;

        Event(int st, int et) {
            startTime = st;
            endTime = et;
        }

        @Override
        public int compareTo(Event obj) {
            return startTime.compareTo(((Event) obj).startTime);
        }
    }

    ;

    static void findMaximumEvents(String[] eventDetails) {
        /*
         * Write your code here.
         */
        List<Event> eventList = new ArrayList();

        for (String event : eventDetails) {
            Event e = new Event(Integer.parseInt(event.split(" ")[0]), Integer.parseInt(event.split(" ")[1]));
            //e.startTime = ;
            //e.endTime = ;

            eventList.add(e);
        }

        int maxCount = 0;

        for (int i = 0; i < eventList.size(); i++) {
            List sEventList = new ArrayList();
            sEventList.add(eventList.get(i));
            for (int j = i; j < eventList.size(); j++) {
                if (canBeAdded(sEventList, eventList.get(j))) {
                    sEventList.add(eventList.get(j));
                }
            }

            if (maxCount < sEventList.size())
                maxCount = sEventList.size();
        }

        System.out.println(maxCount);
    }

    static boolean canBeAdded(List<Event> selectedEvent, Event event) {
        for (Event sEvent : selectedEvent) {
            if (sEvent.startTime <= event.startTime || sEvent.endTime >= event.endTime) {
//                System.out.println(sEvent.startTime + " " + event.startTime);
                //System.out.println (event.startTime );
//                System.out.println(sEvent.endTime + " " + event.endTime);
                //System.out.println (event.endTime );

                return false;
            }
        }

        return true;
    }
}
