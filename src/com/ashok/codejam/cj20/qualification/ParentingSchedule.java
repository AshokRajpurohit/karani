/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codejam.cj20.qualification;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: Parenting Partnering Returns
 * Link: Code Jam 2020 Qualifying Round
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ParentingSchedule {
    private static final PrintWriter out = new PrintWriter(System.out);
    private static final InputReader in = new InputReader();
    private static final String CASE = "Case #";
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static final Schedule INVALID_SCHEDULE = new Schedule(-1, -1, -1);
    private static final Comparator<Schedule> SCHEDULE_COMPARATOR =
            (a, b) -> a.start == b.start ? b.end - a.end : a.start - b.start;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        for (int i = 1; i <= t; i++) {
            print(i, process());
        }
    }

    private static String process() throws IOException {
        int n = in.readInt();
        Schedule[] schedules = new Schedule[n];
        for (int i = 0; i < n; i++) {
            schedules[i] = new Schedule(i, in.readInt(), in.readInt());
        }

        return process(schedules);
    }

    private static String process(Schedule[] schedules) {
        int n = schedules.length;
        if (n == 1) return "J";
        if (n == 2) return "JC";

        Arrays.sort(schedules, SCHEDULE_COMPARATOR);
        Schedule js = schedules[0];
        Schedule cs = schedules[1];
        js.assignee = Assignee.J;
        cs.assignee = Assignee.C;

        for (int i = 2; i < n; i++) {
            Schedule schedule = schedules[i];
            Schedule prev = schedules[i - 1];
            if (schedule.overlaps(prev)) {
                Schedule matching = prev.assignee == Assignee.C ? js : cs;
                if (matching.overlaps(schedule)) return IMPOSSIBLE;
                schedule.assignee = matching.assignee;
            } else {
                schedule.assignee = prev.assignee;
            }

            if (schedule.assignee == Assignee.C) cs = schedule;
            else js = schedule;
        }

        Arrays.sort(schedules, Comparator.comparingInt(value -> value.id));
        StringBuilder sb = new StringBuilder();
        for (Schedule schedule : schedules) sb.append(schedule.assignee);
        return sb.toString();
    }

    private static void print(int testNo, String result) {
        out.println(String.join(" ", CASE + testNo + ":", result));
    }

    private enum Assignee {
        U {
            @Override
            Assignee anti() {
                return J;
            }
        }, C {
            @Override
            Assignee anti() {
                return J;
            }
        }, J {
            @Override
            Assignee anti() {
                return C;
            }
        };

        abstract Assignee anti();
    }

    private static class ScheduleGroup {
        final Schedule head;
        final Collection<Schedule> schedules = new HashSet<>();

        private ScheduleGroup(final Schedule head) {
            this.head = head;
            schedules.add(head);
        }

        private boolean overlaps(Schedule schedule) {
            for (Schedule sch : schedules) {
                if (sch.overlaps(schedule)) return true;
            }

            return false;
        }

        private void add(Schedule schedule) {
            schedules.add(schedule);
        }
    }

    private static class Schedule {
        final int id;
        final int start, end;
        final List<Schedule> conflicts = new LinkedList<>();
        private Assignee assignee = Assignee.U;

        private Schedule(final int id, final int start, final int end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }

        private boolean overlaps(Schedule schedule) {
            return overlaps(schedule.start)
                    || overlaps(schedule.end)
                    || schedule.overlaps(start)
                    || schedule.overlaps(end)
                    || (start == schedule.start && end == schedule.end);
        }

        private boolean overlaps(int time) {
            return start < time && time < end;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    private static class InputReader {
        protected byte[] buffer = new byte[8192];
        protected int offset = 0;
        protected int bufferSize = 0;
        InputStream in;

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
