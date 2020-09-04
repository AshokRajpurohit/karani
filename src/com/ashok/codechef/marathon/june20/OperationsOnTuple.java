/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.june20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Problem Name: Operations on a Tuple
 * Link: https://www.codechef.com/JUNE20A/problems/TTUPLE
 * <p>
 * For complete implementation please see
 * <a href="https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/">GIT Repo</a>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class OperationsOnTuple {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final long NaN = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int t = in.readInt();
        while (t > 0) {
            t--;
            Tuple source = new Tuple(in.readInt(), in.readInt(), in.readInt());
            Tuple target = new Tuple(in.readInt(), in.readInt(), in.readInt());
            out.println(process(source, target));
//            out.println(bruteForce(source, target));
        }
    }

    private static void test() throws Exception {
        while (true) {
            out.println("enter limit and count");
            out.flush();
            Random random = new Random();
            int limit = in.readInt(), mod = 2 * limit + 1;
            int count = in.readInt();
            List<Tuple> tuples = new ArrayList<>();
            for (int a = -limit; a <= limit; a++) {
                for (int b = -limit; b <= limit; b++) {
                    for (int c = -limit; c <= limit; c++) {
                        tuples.add(new Tuple(a, b, c));
                    }
                }
            }

            tuples.stream().parallel().forEach(s -> testOneOps(s));
            tuples.stream().parallel().forEach(s -> testTwoOps(s));
            int t = count;
            Callable<Integer> func = () -> random.nextInt(mod) - limit;
            while (t > 0) {
                t--;
                Tuple s = new Tuple(func.call(), func.call(), func.call());
//                testOneOps(s);
            }

            t = count;
            while (t > 0) {
                t--;
                Tuple s = new Tuple(func.call(), func.call(), func.call());
//                testTwoOps(s);
            }
        }
    }

    private static void testOneOps(Tuple source) {
        Tuple s, target;
        Runnable error = () -> {
            throw new RuntimeException("break");

        };

        Predicate<Tuple> singleOp = t -> process(source, t) == 1;

        List<Tuple> failedAddDetect = applyAddOps(source)
                .stream()
                .filter(singleOp.negate())
                .collect(Collectors.toList());

        List<Tuple> failedMultiDetect = applyMultOp(source)
                .stream()
                .filter(singleOp.negate())
                .collect(Collectors.toList());

        if (!failedAddDetect.isEmpty()) {
            out.println(failedAddDetect.size());
            out.flush();
            error.run();
        }

        if (!failedMultiDetect.isEmpty()) {
            out.println(failedMultiDetect.size());
            out.flush();
            error.run();
        }
    }

    private static void testTwoOps(Tuple source) {
        Runnable error = () -> {
            throw new RuntimeException("break");

        };

        Predicate<Tuple> twoOps = t -> process(source, t) <= 2;


        List<Tuple> errorList = applyAddOps(source)
                .stream()
                .flatMap(t -> applyAddOps(t).stream())
                .filter(twoOps.negate())
                .collect(Collectors.toList());

        if (!errorList.isEmpty()) {
            out.println(errorList.size());
            out.flush();
            error.run();
        }

        errorList = applyMultOp(source)
                .stream()
                .flatMap(t -> applyMultOp(t).stream())
                .filter(twoOps.negate())
                .collect(Collectors.toList());

        if (!errorList.isEmpty()) {
            out.println(errorList.size());
            out.flush();
            error.run();
        }

        errorList = applyAddOps(source)
                .stream()
                .flatMap(t -> applyMultOp(t).stream())
                .filter(twoOps.negate())
                .collect(Collectors.toList());

        if (!errorList.isEmpty()) {
            out.println(errorList.size());
            out.flush();
            error.run();
        }

        errorList = applyMultOp(source)
                .stream()
                .flatMap(t -> applyAddOps(t).stream())
                .filter(twoOps.negate())
                .collect(Collectors.toList());

        if (!errorList.isEmpty()) {
            out.println(errorList.size());
            Tuple target = errorList.get(0);
            out.println(source + ": " + target);
            out.flush();
            int res = process(source, target);
            out.println(res);
            error.run();
        }
    }

    private static List<Tuple> applyAddOps(Tuple tuple) {
        Tuple s;
        List<Tuple> tuples = new ArrayList<>();
        final long a = tuple.a, b = tuple.b, c = tuple.c;
        for (int i = -10; i <= 10; i++) {
            if (i == 0) continue;
            s = new Tuple(a + i, b, c);
            tuples.add(s);
            s = new Tuple(a, b + i, c);
            tuples.add(s);
            s = new Tuple(a, b, c + i);
            tuples.add(s);
            s = new Tuple(a + i, b + i, c);
            tuples.add(s);
            s = new Tuple(a, b + i, c + i);
            tuples.add(s);
            s = new Tuple(a + i, b, c + i);
            tuples.add(s);
            s = new Tuple(a + i, b + i, c + i);
            tuples.add(s);
        }

        return tuples;
    }

    private static List<Tuple> applyMultOp(Tuple tuple) {
        Tuple s;
        List<Tuple> tuples = new ArrayList<>();
        final long a = tuple.a, b = tuple.b, c = tuple.c;
        for (int i = -10; i <= 10; i++) {
            if (i == 1) continue;
            s = new Tuple(a * i, b, c);
            tuples.add(s);
            s = new Tuple(a, b * i, c);
            tuples.add(s);
            s = new Tuple(a, b, c * i);
            tuples.add(s);
            s = new Tuple(a * i, b * i, c);
            tuples.add(s);
            s = new Tuple(a, b * i, c * i);
            tuples.add(s);
            s = new Tuple(a * i, b, c * i);
            tuples.add(s);
            s = new Tuple(a * i, b * i, c * i);
            tuples.add(s);
        }

        return tuples.stream().filter(t -> !t.equals(tuple)).collect(Collectors.toList());
    }

    private static int bruteForce(Tuple source, Tuple target) {
        int res = bruteMulti(source, target, 0);
        res = Math.min(res, bruteAdd(source, target, 0));
        return res;
    }

    private static int bruteMulti(Tuple source, Tuple target, int opCount) {
        if (source.equals(target)) return opCount;
        if (opCount == 2) return opCount + 1;
        int res = 3;
        opCount++;
        Tuple s;
        for (int i = -20; i <= 20; i++) {
            long a = source.a, b = source.b, c = source.c;
            s = new Tuple(a * i, b, c);
            res = Math.min(res, bruteAdd(s, target, opCount));
            s = new Tuple(a, b * i, c);
            res = Math.min(res, bruteAdd(s, target, opCount));
            s = new Tuple(a, b, c * i);
            res = Math.min(res, bruteAdd(s, target, opCount));
            s = new Tuple(a * i, b * i, c);
            res = Math.min(res, bruteAdd(s, target, opCount));
            s = new Tuple(a, b * i, c * i);
            res = Math.min(res, bruteAdd(s, target, opCount));
            s = new Tuple(a * i, b, c * i);
            res = Math.min(res, bruteAdd(s, target, opCount));
            s = new Tuple(a * i, b * i, c * i);
            res = Math.min(res, bruteAdd(s, target, opCount));

            if (res == opCount) return res;
        }

        return res;
    }

    private static int bruteAdd(Tuple source, Tuple target, int opCount) {
        if (source.equals(target)) return opCount;
        if (opCount == 2) return 3;
        int res = 3;
        opCount++;
        Tuple s;
        for (int i = -20; i <= 20; i++) {
            long a = source.a, b = source.b, c = source.c;
            s = new Tuple(a + i, b, c);
            res = Math.min(res, bruteMulti(s, target, opCount));
            s = new Tuple(a, b + i, c);
            res = Math.min(res, bruteMulti(s, target, opCount));
            s = new Tuple(a, b, c + i);
            res = Math.min(res, bruteMulti(s, target, opCount));
            s = new Tuple(a + i, b + i, c);
            res = Math.min(res, bruteMulti(s, target, opCount));
            s = new Tuple(a, b + i, c + i);
            res = Math.min(res, bruteMulti(s, target, opCount));
            s = new Tuple(a + i, b, c + i);
            res = Math.min(res, bruteMulti(s, target, opCount));
            s = new Tuple(a + i, b + i, c + i);
            res = Math.min(res, bruteMulti(s, target, opCount));

            if (res == opCount) return res;
        }

        return res;
    }


    private static int process(Tuple source, Tuple target) {
        if (target.equals(source)) return 0;
        if (source.twoEquals(target)) return 1;
        if (source.oneEqual(target)) {
            int id = 0;
            if (source.b == target.b) id = 1;
            else if (source.c == target.c) id = 2;
            Pair sp = source.getPairIndex(id);
            Pair st = target.getPairIndex(id);

            return process(sp, st);
        }

        if (oneOps(source, target)) return 1;
        if (twoOps(source, target)) return 2;
        return 3;
    }

    private static int process(Pair source, Pair target) {
        if (source.equals(target)) return 0;
        if (source.a == target.a || source.b == target.b) return 1;

        long da = target.a - source.a, db = target.b - source.b;
        if (da == db) return 1;
        if (target.a == 0 && target.b == 0) return 1; // multiply source with 0.
        if (source.a == 0 || source.b == 0) return 2;
        if (target.a == 0 || target.b == 0) return 2;

        long ra = target.a % source.a, rb = target.b % source.b;
        if (ra != 0 || rb != 0) return 2;

        long qa = target.a / source.a, qb = target.b / source.b;
        return qa == qb ? 1 : 2;
    }

    private static boolean oneOps(Tuple source, Tuple target) {
        if (source.twoEquals(target)) return true;
        Tuple diff = target.subtract(source);
        if (diff.nonZeroValuesSame()) return true; // single add operation
        if (target.allEquals(0)) return true; // multiply source with 0.
        if (target.a == 0 || target.b == 0 || target.c == 0) return false;
        if (source.a == 0 || source.b == 0 || source.c == 0) return false;
        Tuple mod = target.modulo(source);
        if (!mod.allEquals(0)) return false; // single multiplication operation
        Tuple divide = target.div(source);
        return divide.nonOneValuesSame(); // single multiplication operation
    }

    /**
     * Two operations can be performed, add x and then multiply with y.
     * These are the scenarios:
     * <ul>
     *     <li>np, q+d, r+d: implemented</li>
     *     <li>p+d1, q+d1, r+d2: implemented</li>
     *     <li>p+d1, q+d2, r+d1+d2: implemented</li>
     *     <li>np+nd, nq, r+d: implemented</li>
     *     <li>np+nd, nq+nd, r+d</li>
     *     <li>np, nq, r+d</li>
     *     <li>np, nq, nr+d: this scenario is same as previous one.</li>
     *     <li>np+d, nq, r+d</li>
     *     <li>np+nd, nq+nd, nr+nd: implemented</li>
     * </ul>
     *
     * @param source
     * @param target
     * @return
     */
    private static boolean twoOps(Tuple source, Tuple target) {
        if (source.oneEqual(target)) return true;
        if (twoAdditions(source, target)) return true;
        if (twoMultiplications(source, target)) return true;

        // scenario np+d, nq+d, nr+d or np+nd, nq+nd, nr+nd
        Tuple ds = source.delta(), dt = target.delta();
        Tuple mod = dt.modulo(ds), div = dt.div(ds);
        if (mod.allEquals(0) && div.nonZeroValuesSame()) return true; // n * (source + d) ~ target

        return multiplyAndAdd(source, target) || addAndMultiply(source, target);
    }

    private static boolean addAndMultiply(Tuple source, Tuple target) {
        // scenario np+nd, q+d, nr and np+nd, nq+nd, nr
        for (int id = 0; id < 3; id++) {
            long b = source.getById(id), c = target.getById(id);
            if (b == 0 || c == 0 || c % b != 0) continue;
            long n = c / b;
            Pair sb = source.getPairIndex(id), st = target.getPairIndex(id);
            if (st.a % n != 0 && st.b % n != 0) continue;
            if (st.a % n == 0) {
                if (st.b % n == 0) {
                    Pair str = new Pair(st.a / n, st.b / n); // reduced
                    if (process(sb, str) == 1) return true;
                }

                Pair str = new Pair(st.a / n, st.b);
                if (process(sb, str) == 1) return true;
            }

            if (st.b % n == 0) {
                Pair str = new Pair(st.a, st.b / n);
                if (process(sb, str) == 1) return true;
            }
        }

        // scenario np+nd, nq+nd, r+d
        for (int id = 0; id < 3; id++) {
            long b = source.getById(id), c = target.getById(id);
            long d = c - b;
            Pair tp = target.getPairIndex(id), sp = source.getPairIndex(id);
            if (sp.a == sp.b) continue;
            if ((tp.a - tp.b) % (sp.a - sp.b) != 0) continue;
            long n = (tp.a - tp.b) / (sp.a - sp.b);
            if (mod(tp.a, n) != 0 || mod(tp.b, n) != 0) continue;
            if (tp.a == n * (sp.a + d) && tp.b == n * (sp.b + d)) return true;
        }

        return false;
    }

    private static boolean multiplyAndAdd(Tuple source, Tuple target) {
        Tuple diff = target.subtract(source);
        if (target.a == target.b && target.b == target.c) return true; // multiply by 0 and add something.
        // scenarios np+{0,d}, nq+{0,d}, r+d
        for (int id = 0; id < 3; id++) {
            long d = id == 0 ? diff.a : id == 1 ? diff.b : diff.c;
            Pair base = target.getPairIndex(id);
            Pair sp = source.getPairIndex(id), tp = base;

            int res = process(sp, tp);
            tp = new Pair(base.a - d, base.b);
            res = Math.min(res, process(sp, tp));
            tp = new Pair(base.a, base.b - d);
            res = Math.min(res, process(sp, tp));
            tp = new Pair(base.a - d, base.b - d);
            res = Math.min(res, process(sp, tp));

            if (res == 1) return true;
        }

        // scenarios np+{0,d}, nq+{0,d}, nr
        for (int id = 0; id < 3; id++) {
            long b = id == 0 ? source.a : id == 1 ? source.b : source.c;
            long c = id == 0 ? target.a : id == 1 ? target.b : target.c;
            if (b == 0 || c % b != 0) continue;
            long n = c / b;
            Pair sb = source.getPairIndex(id), st = target.getPairIndex(id);

            // scenario np+{0,d}, nq, nr
            if (sb.a * n == st.a || sb.b * n == st.b) return true;

            // scenario np+d, nq+d, nr
            if ((sb.a - sb.b) * n == (st.a - st.b)) return true;
        }

        return false;
    }

    /**
     * Check if it is possible to apply two addition operation on {@code source} to make it equal to {@code target}
     *
     * @param source
     * @param target
     * @return
     */
    private static boolean twoAdditions(Tuple source, Tuple target) {
        Tuple diff = target.subtract(source);
        long a = diff.a, b = diff.b, c = diff.c;
        if (a == b || b == c || c == a) return true;
        return a == b + c || b == c + a || c == a + b;
    }

    private static boolean twoMultiplications(Tuple source, Tuple target) {
        if (source.a == 0 || source.b == 0 || source.c == 0) return false;
        if (target.a == 0 || target.b == 0 || target.c == 0) return false;

        Tuple mod = target.modulo(source);
        if (!mod.allEquals(0)) return false;

        Tuple div = target.div(source);
        long a = div.a, b = div.b, c = div.c;
        if (a == b || b == c || c == a) return true;
        return a == b * c || b == c * a || c == a * b;
    }

    private static long mod(long a, long b) {
        return b == 0 ? a : a % b;
    }

    private static long divide(long a, long d) {
        return d == 0 ? NaN : a / d;
    }

    final static class Tuple {

        final long a, b, c;

        Tuple(final long a, final long b, final long c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        Tuple modulo(final Tuple tuple) {
            return new Tuple(mod(a, tuple.a), mod(b, tuple.b), mod(c, tuple.c));
        }

        Tuple subtract(final Tuple tuple) {
            return new Tuple(a - tuple.a, b - tuple.b, c - tuple.c);
        }

        Tuple div(final Tuple tuple) {
            return new Tuple(divide(a, tuple.a), divide(b, tuple.b), divide(c, tuple.c));
        }

        Tuple delta() {
            return new Tuple(a - b, b - c, c - a);
        }

        private long getById(int id) {
            return id == 0 ? a : id == 1 ? b : c;
        }

        public long max() {
            return Math.max(a, Math.max(b, c));
        }

        public boolean oneEqual(Tuple tuple) {
            return a == tuple.a || b == tuple.b || c == tuple.c;
        }

        public boolean twoEquals(Tuple tuple) {
            return (a == tuple.a && (b == tuple.b || c == tuple.c))
                    || (b == tuple.b && c == tuple.c);
        }

        public boolean equalValues() {
            return a == b && b == c;
        }

        public boolean nonZeroValuesSame() {
            if (a == b && b == c) return true;
            if (a == 0) return b == c || b == 0 || c == 0;
            if (b == 0) return a == c || c == 0;
            if (c == 0) return a == b;
            return false;
        }

        public boolean nonNaNValuesSame() {
            if (a == b && b == c) return true;
            if (a == NaN) return b == c || b == NaN || c == NaN;
            if (b == NaN) return a == c || c == NaN;
            if (c == NaN) return a == b;
            return false;
        }

        public boolean nonOneValuesSame() {
            if (a == b && b == c) return true;
            if (a == 1) return b == c || b == 1 || c == 1;
            if (b == 1) return a == c || c == 1;
            if (c == 1) return a == b;
            return false;
        }

        @Override
        public boolean equals(Object o) {
            Tuple target = (Tuple) o;
            return a == target.a && b == target.b && c == target.c;
        }

        public boolean allEquals(int v) {
            return a == v && b == v && c == v;
        }

        public Pair getPair(long excludeValue) {
            if (a == excludeValue) return new Pair(b, c);
            if (b == excludeValue) return new Pair(a, c);
            return new Pair(a, b);
        }

        public Pair getPairIndex(long excludeIndex) {
            if (excludeIndex == 0) return new Pair(b, c);
            if (excludeIndex == 1) return new Pair(a, c);
            return new Pair(a, b);
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + ", " + c + ")";
        }
    }

    final static class Pair {
        final long a, b;

        Pair(final long a, final long b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            Pair pair = (Pair) o;
            return a == pair.a && b == pair.b;
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + ")";
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