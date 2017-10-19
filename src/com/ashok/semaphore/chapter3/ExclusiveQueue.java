/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.semaphore.chapter3;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ExclusiveQueue {
    private static Output out = new Output();
    private static InputReader in = new InputReader();
    private static volatile Integer
            leaderSequence = 1,
            followerSequence = 1;

    private final Semaphore
            mutex = new Semaphore(1),
            leaderQueue = new Semaphore(0),
            followerQueue = new Semaphore(0),
            rendezvous = new Semaphore(0);

    private volatile int leaders = 0, followers = 0;

    public static void main(String[] args) throws IOException {
        ExclusiveQueue a = new ExclusiveQueue();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            out.println("Enter number of leaders and followers");
            out.flush();
            int lead = in.readInt(), fol = in.readInt();
            run(getLeaders(lead));
            run(getFollowers(fol));
            out.flush();
        }
    }

    private static void run(List<Thread> threads) {
        for (Thread thread : threads)
            thread.start();
    }

    private List<Thread> getLeaders(int count) {
        List<Thread> leaders = new LinkedList<>();
        for (int i = 0; i < count; i++)
            leaders.add(new Thread(new Leader()));

        return leaders;
    }

    private List<Thread> getFollowers(int count) {
        List<Thread> followers = new LinkedList<>();
        for (int i = 0; i < count; i++)
            followers.add(new Thread(new Follower()));

        return followers;
    }

    private void dance(Object o) {
        out.println(o + " is dancing. please appreciate him.");
    }

    final class Leader implements Runnable {
        final int id;

        public Leader() {
            synchronized (leaderSequence) {
                id = leaderSequence++;
            }
        }

        @Override
        public void run() {
            try {
                mutex.acquire();
                if (followers > 0) {
                    followers--;
                    followerQueue.release();
                } else {
                    leaders++;
                    mutex.release();
                    leaderQueue.acquire();
                }

                dance(this);
                rendezvous.acquire();
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            return "Leader " + id;
        }
    }

    final class Follower implements Runnable {
        final int id;

        Follower() {
            synchronized (followerSequence) {
                id = followerSequence++;
            }
        }

        @Override
        public void run() {
            try {
                mutex.acquire();
                if (leaders > 0) {
                    leaders--;
                    leaderQueue.release();
                } else {
                    followers++;
                    mutex.release();
                    followerQueue.acquire();
                }

                dance(this);
                rendezvous.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            return "Follower " + id;
        }
    }

}
