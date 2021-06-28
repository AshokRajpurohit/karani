/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.atlassian;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CodeDesign {
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
        testRateLimiter();
//        while (true) {
//            out.println(in.read());
//            out.flush();
//        }
    }

    private static void testRateLimiter() {
        out.println("starting rate limiter tests");
        RateLimiter rateLimiter = new RateLimiter(60000, 5);
        int clientId = 1;
        long timestamp = System.currentTimeMillis();
        if (!rateLimiter.checkLimit(clientId)) throw new RuntimeException("test failed, 2st request");
        if (!rateLimiter.checkLimit(clientId)) throw new RuntimeException("test failed, 3st request");
        if (!rateLimiter.checkLimit(clientId)) throw new RuntimeException("test failed, 4st request");
        if (!rateLimiter.checkLimit(clientId)) throw new RuntimeException("test failed, 1st request");
        if (!rateLimiter.checkLimit(clientId)) throw new RuntimeException("test failed, 5st request");
        long t2 = System.currentTimeMillis();
        if (rateLimiter.checkLimit(clientId, timestamp)) throw new RuntimeException("test failed, 6st request should not pass");
        if (rateLimiter.checkLimit(clientId, timestamp)) throw new RuntimeException("test failed, 7st request should not pass");


        clientId = 2;
        if (!rateLimiter.checkLimit(clientId, timestamp + 1)) throw new RuntimeException("should pass for second client, since it's first request");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60000)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60001)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60002)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60003)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60004)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60005)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60006)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60007)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60008)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60009)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60009)) new RuntimeException("test failed, 7st request should not pass");
        if (!rateLimiter.checkLimit(clientId, timestamp + 60009)) new RuntimeException("test failed, 7st request should not pass");
        out.println("Test passed");
    }

    static class RateLimiter {
        private final Map<Integer, Integer> userCredits = new HashMap<>();
        private final long windowLen; // window size in milli seconds
        private final int allowedRequests; // number of requests in given time window
        private final Map<Integer, Queue<Long>> clientTimestampsMap = new HashMap<>();

        RateLimiter(int windowLen, int allowedRequests) {
            this.windowLen = windowLen;
            this.allowedRequests = allowedRequests;
        }

        public boolean checkLimit(int clientId) {
            boolean allowed = checkLimit(clientId, System.currentTimeMillis());
            if (allowed) return true;

            int credits = userCredits.getOrDefault(clientId, 0) - 1;
            if (credits < 0) return false;

            userCredits.put(clientId, credits);
            return true;
        }

        public void addCredits(int client, int credits) {
            credits += userCredits.getOrDefault(client, 0);
            userCredits.put(client, credits);
        }

        private boolean checkLimit(int clinetId, long timestamp) {
            Queue<Long> clientQueue = clientTimestampsMap.computeIfAbsent(clinetId, key -> new ArrayDeque<>());
            long first = timestamp - windowLen;

            long last = clientQueue.isEmpty() ? Integer.MAX_VALUE : clientQueue.peek();
            while(!clientQueue.isEmpty() && clientQueue.peek() <= first) last = clientQueue.remove();

            int windows = (int) ((timestamp - last) / windowLen);
            if (windows < 1) windows = 1;
            int newCredits = (windows - 1) * allowedRequests;
            boolean allowed = clientQueue.size() < allowedRequests;
            if (allowed) clientQueue.add(timestamp);

            addCredits(clinetId, newCredits);
            return allowed;
        }
    }
}
