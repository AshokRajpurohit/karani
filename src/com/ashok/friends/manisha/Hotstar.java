/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.manisha;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Manisha
 * Link: Hotstar
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Hotstar {
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
        out.println(dropRequests(new int[]{1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8,
                8, 9, 9, 9, 9, 9, 10, 10, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 14, 14,
                14, 14, 14, 16, 16, 16, 16, 16, 16, 17, 17, 17, 18, 18, 18, 18, 18, 18, 18, 18, 19, 19, 19, 19, 19, 19,
                19, 20, 20, 20, 20, 20}));
        out.flush();
        while (true) {
            out.println(dropRequests(in.readIntArray(in.readInt())));
            out.flush();
        }
    }

    private static int dropRequests(int[] requestTimes) {
        Arrays.sort(requestTimes);
        Request[] requests = toRequests(requestTimes);
        Arrays.stream(policies).forEach(policy -> applyPolicy(requests, policy));
        return Arrays.stream(requests).mapToInt(pair -> pair.drops).sum();
    }

    private static Request[] toRequests(int[] requestTimes) {
        List<Request> requestList = new LinkedList<>();
        Request request = new Request(requestTimes[0]);
        requestList.add(request);
        for (int time : requestTimes) {
            if (time != request.time) {
                request = new Request(time);
                requestList.add(request);
            }

            request.count++;
        }

        return requestList.stream().toArray(t -> new Request[t]);
    }

    private static void applyPolicy(Request[] requests, Policy policy) {
        int len = requests.length, buffer = 0;
        for (int from = 0, to = from; to < len; ) {
            while (to < len && requests[to].time < requests[from].time + policy.duration) {
                Request request = requests[to];
                int remaining = Math.max(0, policy.limit - buffer);
                buffer += request.count;
                if (remaining < request.count) {
                    request.drop(request.count - remaining);
                }
                to++;
            }

            if (to == len) break;
            while (from < len && requests[to].time >= requests[from].time + policy.duration)
                buffer -= requests[from++].count;
        }

        return;
    }

    final static class Request {
        final int time;
        int count = 0, drops = 0;

        Request(int time) {
            this.time = time;
        }

        void drop(int count) {
            drops = Math.max(drops, Math.min(count, this.count));
        }

        public String toString() {
            return time + ": " + count;
        }
    }

    private static Policy[] policies = new Policy[]{new Policy(1, 3), new Policy(10, 20), new Policy(60, 60)};

    final static class Policy {
        final int duration, limit;

        Policy(int duration, int limit) {
            this.duration = duration;
            this.limit = limit;
        }

        public String toString() {
            return duration + ", " + limit;
        }
    }
}
