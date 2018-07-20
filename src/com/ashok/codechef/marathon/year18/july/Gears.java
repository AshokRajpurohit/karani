/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.july;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Gears
 * Link: https://www.codechef.com/JULY18A/problems/GEARS
 * Concept: Disjoint Sets
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class Gears {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt(), m = in.readInt();
        int[] ar = in.readIntArray(n);
        GearSet gearSet = new GearSet(ar);
        StringBuilder sb = new StringBuilder(m << 2);
        while (m > 0) {
            m--;
            int type = in.readInt(), x = in.readInt(), y = in.readInt();
            if (type == 1)
                gearSet.updateTeeth(x - 1, y);
            else if (type == 2)
                gearSet.connectGears(x - 1, y - 1);
            else {
                Fraction fraction = gearSet.query(x - 1, y - 1, in.readInt());
                if (fraction == ZERO_FRACTION)
                    sb.append(0);
                else
                    sb.append(fraction.a).append('/').append(fraction.b);

                sb.append('\n');
            }
        }

        out.print(sb);
    }

    private static void connect(Gear a, Gear b) {
        if (!b.group.isValid() || a.group.gears.size() >= b.group.gears.size()) {
            a.connect(b);
        } else
            b.connect(a);
    }

    /**
     * Euclid's Greatest Common Divisor algorithm implementation.
     * For more information refer Wikipedia and Alan Baker's Number Theory.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static long gcd(long a, long b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    final static class GearSet {
        final int size;
        private final Gear[] gears;

        GearSet(int[] gearTeeth) {
            size = gearTeeth.length;
            gears = new Gear[size];
            for (int i = 0; i < size; i++)
                gears[i] = new Gear(i, gearTeeth[i]);
        }

        void updateTeeth(int id, int teeth) {
            gears[id].teeth = teeth;
        }

        void connectGears(int x, int y) {
            connect(gears[x], gears[y]);
        }

        Fraction query(int x, int y, long v) {
            Gear a = gears[x], b = gears[y];
            if (a.group == Group.ADI_GROUP || a.group != b.group || a.group.jammed || v == 0)
                return ZERO_FRACTION;

            long numerator = a.teeth * v, denominator = b.teeth;
            long gcd = gcd(numerator, denominator);
            numerator /= gcd;
            denominator /= gcd;
            return a.aligned == b.aligned ? new Fraction(numerator, denominator) : new Fraction(-numerator, denominator);
        }
    }

    final static class Gear {
        final int id;
        Group group = Group.ADI_GROUP;
        int teeth;
        final List<Gear> connectedGears = new LinkedList<>();
        boolean aligned = true; // distance from local group head.

        Gear(int id, int teeth) {
            this.id = id;
            this.teeth = teeth;
        }

        void connect(Gear gear) {
            createGroupIfNotValid();
            connectedGears.add(gear);
            gear.connectedGears.add(this);
            if (gear.group.isValid())
                group.merge(this, gear);
            else {
                gear.aligned = !aligned;
                group.add(gear);
            }
        }

        private void createGroupIfNotValid() {
            if (!group.isValid()) {
                group = new Group();
                group.add(this);
            }
        }
    }

    final static class Group {
        final static Group ADI_GROUP = new Group();
        private static int sequence = 0;
        private final int id;
        private boolean jammed = false; // group is jammed if there is a cycle and both the gears are in same direction
        private final List<Gear> gears = new LinkedList<>();

        Group() {
            id = sequence++;
        }

        boolean isValid() {
            return this != ADI_GROUP;
        }

        void merge(Gear gear, Gear connectingGear) {
            Group group = connectingGear.group;
            if (this == group) {
                jammed = jammed || (gear.aligned == connectingGear.aligned);
                return;
            }

            jammed = jammed || group.jammed;
            boolean alignDiff = gear.aligned == connectingGear.aligned;
            group.gears.forEach((it) -> {
                it.aligned = alignDiff ^ it.aligned;
                add(it);
            });
        }

        void add(Gear gear) {
            gear.group = this;
            gears.add(gear);
        }
    }

    enum GearGroupJamStatus {
        NOT_JAMMED, SPEED_JAMMED, ROTATIONAL_JAMMED;
    }

    public static final Fraction ZERO_FRACTION = new Fraction(0, 1);

    final static class Fraction {
        final long a, b;

        Fraction(long a, long b) {
            this.a = a;
            this.b = b;
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

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}