/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.feb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Problem Name: Chef and odd queries
 * Link: https://www.codechef.com/FEB18/problems/CHANOQ
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class ChefAndOddQueries {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        while (t > 0) {
            t--;
            int n = in.readInt();
            Segment[] segments = new Segment[n];
            for (int i = 0; i < n; i++)
                segments[i] = new Segment(in.readInt() - 1, in.readInt() - 1);

            QueryProcessor processor = new QueryProcessor(segments);
            int q = in.readInt();
            while (q > 0) {
                q--;
                int m = in.readInt();
                int[] ar = in.readIntArray(m);
                normalize(ar);
                out.println(processor.bruteForce(ar));
            }
        }
    }

    private static void normalize(int[] ar) {
        int len = ar.length;
        for (int i = 0; i < len; i++)
            ar[i]--;
    }

    private static <E> List<E>[] getListArray(int size) {
        List<E>[] lists = new List[size];
        for (int i = 0; i < size; i++)
            lists[i] = new LinkedList<>();

        return lists;
    }

    private static int countCommons(Collection a, Collection b) {
        if (a.size() > b.size())
            return countCommons(b, a);

        int count = 0;
        for (Object o : a)
            if (b.contains(o)) count++;

        return count;
    }

    private static <E> Collection<E> commonElements(Collection<E> a, Collection<E> b) {
        if (a.size() > b.size())
            return commonElements(b, a);

        Collection<E> c = new HashSet<>();
        for (E e : a)
            if (b.contains(e)) c.add(e);

        return c;
    }

    private static int countNotPresent(Collection a, Collection b) {
        int count = 0;
        for (Object o : a)
            if (!b.contains(o)) count++;

        return count;
    }

    private static <K> void addToMap(Map<K, Counter> map, K key) {
        addToMap(map, key, 1);
    }

    private static <K> void addToMap(Map<K, Counter> map, K key, int count) {
        if (map.containsKey(key)) {
            map.get(key).increment(count);
        } else {
            map.put(key, new Counter(count));
        }
    }

    private static int segmentSequence = 1;

    final static class Segment implements Comparable<Segment> {
        final int left, right, id = segmentSequence++;
        private final List<PointGroup> pointGroups = new LinkedList<>();

        Segment(int left, int right) {
            this.left = left;
            this.right = right;
        }

        void addGroup(PointGroup pointGroup) {
            pointGroups.add(pointGroup);
        }

        public int hashCode() {
            return id;
        }

        public String toString() {
            return left + " -> " + right;
        }

        @Override
        public int compareTo(Segment segment) {
            return left == segment.left ? right - segment.right : left - segment.left;
        }
    }

    private static int pointGroupSequence = 1;

    final static class PointGroup {
        final int id = pointGroupSequence++;
        final List<Point> points = new LinkedList<>();
        final Set<Segment> segments = new HashSet<>();

        void addSegment(Segment segment) {
            segments.add(segment);
            segment.addGroup(this);
        }

        void addSegments(Collection<Segment> c) {
            for (Segment segment : c)
                segments.add(segment);
        }

        void addPoint(Point point) {
            points.add(point);
            point.group = this;
        }

        public int hashCode() {
            return id;
        }

        public String toString() {
            return "" + id;
        }
    }

    private static final Point INVALID_POINT = new Point(-1);

    final static class Point {
        final int index;
        PointGroup group;

        Point(int id) {
            index = id;
        }

        public int hashCode() {
            return index;
        }

        public String toString() {
            return this == INVALID_POINT ? "INVALID_POINT" : "" + index;
        }
    }

    final static class QueryProcessor {
        final int size;
        final Point[] points;
        final Segment[] segments;

        QueryProcessor(Segment[] segments) {
            size = segments.length;
            this.segments = segments;
            points = new Point[size];

            for (int i = 0; i < size; i++)
                points[i] = new Point(i);

            Arrays.sort(segments);
//            initialize();
        }

        int query(int[] pointIndices) {
            Arrays.sort(pointIndices);
            int len = pointIndices.length;
            int first = pointIndices[0], last = pointIndices[len - 1];
            int from = Arrays.binarySearch(segments, new Segment(first, first));
            Map<Segment, Counter> segmentCounterMap = new HashMap<>();
            Map<PointGroup, Counter> groupCounterMap = new HashMap<>();
            for (int p : pointIndices)
                addToMap(groupCounterMap, points[p].group);

            groupCounterMap.entrySet().forEach(e ->
                    e.getKey().segments.forEach(segment ->
                            addToMap(segmentCounterMap, segment, e.getValue().count))
            );

            Counter counter = new Counter(0);
            groupCounterMap.values().forEach(c -> counter.count += c.count & 1);
            return counter.count;
        }

        int bruteForce(int[] pointIndices) {
            Arrays.sort(pointIndices);
            int len = pointIndices.length;
            int count = 0;
            for (Segment segment : segments) {
                int from = Arrays.binarySearch(pointIndices, segment.left);
                if (from < 0) from = -from - 1;
                if (from == len) continue;

                int to = Arrays.binarySearch(pointIndices, segment.right);
                if (to == -1) continue;
                if (to < 0) to = -to - 2;
                count += (to + 1 - from) & 1;
            }

            return count;
        }

        int processQuery(int[] pointIndices) {
            Arrays.sort(pointIndices);
            int res = 0, len = pointIndices.length;
            if (len == 1)
                return points[pointIndices[0]].group.segments.size();

            Collection<Segment> segmentCollection = new HashSet<>();
            Point prev = INVALID_POINT;
            for (int i = 0; i < len; i++) {
                Point point = points[pointIndices[i]];
                PointGroup group = point.group;
                segmentCollection.addAll(group.segments);
                if (prev != INVALID_POINT)
                    segmentCollection.removeAll(prev.group.segments);

                Point next = INVALID_POINT;
                if (i < len - 1)
                    next = points[pointIndices[i + 1]];

                if (next == INVALID_POINT)
                    res += segmentCollection.size();
                else if (next.group != group)
                    res += countNotPresent(segmentCollection, next.group.segments);

                for (int j = i + 2; j < len; j += 2) {
                    next = points[pointIndices[j]];
                    if (next.group == group)
                        continue;

                    if (segmentCollection.isEmpty())
                        break;

                    segmentCollection = commonElements(segmentCollection, next.group.segments);
                    if (j < len - 1) {
                        Point nextNextPoint = points[pointIndices[j + 1]];
                        if (nextNextPoint.group == next.group)
                            continue;

                        int remove = countCommons(segmentCollection, nextNextPoint.group.segments);
                        res -= remove;
                    }

                    res += segmentCollection.size();
                }

                prev = point;
            }

            return res;
        }

        private void initialize() {
            List<Segment>[] startSegmentMap = startSegmentMap();
            List<Segment>[] endSegmentMap = endSegmentMap();
            Collection<Segment> sc = new HashSet<>();
            PointGroup pointGroup = new PointGroup();

            for (int i = 0; i < size; i++) {
                if (!startSegmentMap[i].isEmpty()) {
                    pointGroup = new PointGroup();
                    pointGroup.addSegments(sc);
                }

                for (Segment segment : startSegmentMap[i]) {
                    sc.add(segment);
                    pointGroup.addSegment(segment);
                }

                pointGroup.addPoint(points[i]);
                endSegmentMap[i].forEach(segment -> sc.remove(segment));

                if (!endSegmentMap[i].isEmpty()) {
                    pointGroup = new PointGroup();
                    pointGroup.addSegments(sc);
                }
            }
        }

        private List<Segment>[] startSegmentMap() {
            List<Segment>[] lists = getListArray(size);
            for (Segment segment : segments)
                lists[segment.left].add(segment);

            return lists;
        }

        private List<Segment>[] endSegmentMap() {
            List<Segment>[] lists = getListArray(size);
            for (Segment segment : segments)
                lists[segment.right].add(segment);

            return lists;
        }
    }

    final static class Counter implements Comparable<Counter> {
        int count;

        Counter() {
            this(1);
        }

        Counter(int value) {
            count = value;
        }

        void increment() {
            count++;
        }

        void increment(int value) {
            count += value;
        }

        @Override
        public int compareTo(Counter counter) {
            return count - counter.count;
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