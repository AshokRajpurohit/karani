/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year17.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Problem Name: IPC Trainers
 * Link: https://www.codechef.com/JULY17/problems/IPCTRAIN
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class IpcTrainers {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final Comparator TRAINER_DAY_COMPARATOR = new TrainerDayComparator(),
            TRAINER_SADNESS_COMPARATOR = new TrainerSadnessComparator(),
            TRAINER_COMPARATOR = new TrainerComparator();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;

            int n = in.readInt(), d = in.readInt();
            Trainer[] trainers = new Trainer[n];
            for (int i = 0; i < n; i++)
                trainers[i] = new Trainer(in.readInt() - 1, in.readInt(), in.readInt());

            out.println(process(trainers, d));
        }
    }

    private static long process(Trainer[] trainers, int days) {
        int len = trainers.length;
        Arrays.sort(trainers, TRAINER_DAY_COMPARATOR);
//        sortByDays(trainers, days);
        PriorityQueue<Trainer> queue = new PriorityQueue<>(len, TRAINER_SADNESS_COMPARATOR);

        int ref = 0;
        for (Trainer trainer : trainers) {
            scheduleLectures(queue, trainer.arrivalDay - ref);
            queue.add(trainer);
            ref = trainer.arrivalDay;
        }

        scheduleLectures(queue, days - ref);
        return computeSadness(trainers);
    }

    private static void scheduleLectures(PriorityQueue<Trainer> queue, int days) {
        while (days != 0 && queue.size() != 0) { // keep checking while we have trainers to give session on left days.
            Trainer trainer = queue.peek(); // trainer who has been giving lectures before this <i>day</i>
            int lectures = Math.min(trainer.lectures, days);
            trainer.lectures -= lectures; // how many more lectures he wants to give.
            days -= lectures;
            trainer.arrivalDay += days; // the effective arrival day for this trainer after sessions.

            if (trainer.lectures == 0) // This trainer had already enough lectures, let's remove him from queue.
                queue.poll();
        }
    }

    private static long computeSadness(Trainer[] trainers) {
        long res = 0;
        for (Trainer trainer : trainers)
            res += trainer.getSadnessLevel();

        return res;
    }

    /**
     * Count sort is used to arrange trainers in their arrival time.
     *
     * @param trainers
     * @param days
     */
    private static void sortByDays(Trainer[] trainers, int days) {
        LinkedList<Trainer>[] trainerListMap = new LinkedList[days];
        for (Trainer trainer : trainers) {
            int day = trainer.arrivalDay;
            if (trainerListMap[day] == null)
                trainerListMap[day] = new LinkedList<>();

            trainerListMap[day].addLast(trainer);
        }

        int index = 0;
        for (LinkedList<Trainer> trainerList : trainerListMap) {
            if (trainerList == null)
                continue;

            for (Trainer trainer : trainerList)
                trainers[index++] = trainer;
        }
    }

    private static Trainer getMax(Trainer[] trainers, Comparator<Trainer> comparator) {
        Trainer ref = trainers[0];
        for (Trainer trainer : trainers)
            ref = comparator.compare(ref, trainer) > 0 ? trainer : ref;

        return ref;
    }

    final static class Trainer {
        int arrivalDay, lectures, sadness;

        Trainer(int d, int l, int s) {
            arrivalDay = d;
            lectures = l;
            sadness = s;
        }

        public long getSadnessLevel() {
            return 1L * sadness * lectures;
        }

        @Override
        public String toString() {
            return "day: " + arrivalDay + ", lectures: " + lectures + ", sadness: " + sadness;
        }
    }

    final static class TrainerComparator implements Comparator<Trainer> {
        @Override
        public int compare(Trainer a, Trainer b) {
            if (a.arrivalDay == b.arrivalDay)
                return b.sadness - a.sadness;

            return a.arrivalDay - b.arrivalDay;
        }
    }

    final static class TrainerDayComparator implements Comparator<Trainer> {
        @Override
        public int compare(Trainer a, Trainer b) {
            return a.arrivalDay - b.arrivalDay;
        }
    }

    final static class TrainerSadnessComparator implements Comparator<Trainer> {
        @Override
        public int compare(Trainer a, Trainer b) {
            if (b.sadness == a.sadness)
                return a.lectures - b.lectures;

            return b.sadness - a.sadness;
        }
    }

    final static class InputReader {
        InputStream in;
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;

        public InputReader() {
            in = System.in;
        }

        public void close() throws IOException {
            in.close();
        }

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }
    }
}