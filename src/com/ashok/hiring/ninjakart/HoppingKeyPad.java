package com.ashok.hiring.ninjakart;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class HoppingKeyPad {
    private static final char[][] keypad = new char[4][3];
    private static final int[] charIndexMapping = new int[256];
    private static final int[] keyCharMappng = new int[12];

    static {
        char ch = '1';
        for (int i = 0; i < 9; i++) {
            keypad[i / 3][i % 3] = ch;
            ch++;
        }

        keypad[3][1] = '0';
        keypad[3][0] = '*';
        keypad[3][2] = '#';

    }

    public static List<Character> path(char[] keys) {
        if (keys.length <= 1) return new LinkedList<>();


        Character[] pathKeys = IntStream.range(1, keys.length)
                .mapToObj(i -> path(keys[i - 1], keys[i]))
                .flatMap(p -> p.stream())
                .filter(p -> p != INVALID_POINT)
                .map(p -> keypad[p.x][p.y])
                .toArray(t -> new Character[t]);

        List<Character> path = new LinkedList<>();
        path.add(pathKeys[0]);
        IntStream.range(1, pathKeys.length).filter(i -> pathKeys[i] != pathKeys[i - 1]).mapToObj(i -> pathKeys[i]).forEach(i -> path.add(i));
        return path;
    }

    private static List<Point> path(char from, char to) {
        if (from == to)
            return new LinkedList<>();

        Point source = getPoint(from), target = getPoint(to);
        if (source == INVALID_POINT || target == INVALID_POINT) new LinkedList<>();
        Deque<Point> deque = new LinkedList<>();
        deque.offer(source);
        Map<Point, Point> previousPoint = new HashMap<>();
        previousPoint.put(source, INVALID_POINT);
        int distance = 0;
        deque.offer(INVALID_POINT); // marker

        while (deque.size() > 1) {
            Point point = deque.poll();
            if (point == INVALID_POINT) {
                distance++;
                deque.offer(INVALID_POINT);
                continue;
            }

            Point next;
            if ((next = upLeft(point)) != INVALID_POINT && !previousPoint.containsKey(next)) {
                deque.offer(next);
                previousPoint.put(next, point);
            }
            if ((next = upRight(point)) != INVALID_POINT && !previousPoint.containsKey(next)) {
                deque.offer(next);
                previousPoint.put(next, point);
            }
            if ((next = downLeft(point)) != INVALID_POINT && !previousPoint.containsKey(next)) {
                deque.offer(next);
                previousPoint.put(next, point);
            }
            if ((next = downRight(point)) != INVALID_POINT && !previousPoint.containsKey(next)) {
                deque.offer(next);
                previousPoint.put(next, point);
            }
            if ((next = leftUp(point)) != INVALID_POINT && !previousPoint.containsKey(next)) {
                deque.offer(next);
                previousPoint.put(next, point);
            }
            if ((next = leftDown(point)) != INVALID_POINT && !previousPoint.containsKey(next)) {
                deque.offer(next);
                previousPoint.put(next, point);
            }
            if ((next = rightUp(point)) != INVALID_POINT && !previousPoint.containsKey(next)) {
                deque.offer(next);
                previousPoint.put(next, point);
            }
            if ((next = rightDown(point)) != INVALID_POINT && !previousPoint.containsKey(next)) {
                deque.offer(next);
                previousPoint.put(next, point);
            }

            if (next.equals(target)) break;
        }

        Deque<Point> path = new LinkedList<>();
        Point point = target;
        while (point != INVALID_POINT) {
            path.push(point);
            point = previousPoint.get(point);
        }

        return new LinkedList<>(path);
    }

    private static Point getPoint(char ch) {
        if (ch == '*') return new Point(3, 0);
        if (ch == '#') return new Point(3, 2);
        if (ch == '0') return new Point(3, 1);
        return ch >= '1' && ch <= '9' ? new Point((ch - 1 - '0') / 3, (ch - 1 - '0') % 3) : INVALID_POINT;
    }

    private static Point upLeft(Point point) {
        if (point == INVALID_POINT) return INVALID_POINT;
        int x = point.x - 2, y = point.y - 1;
        return getPoint(x, y);
    }

    private static Point upRight(Point point) {
        if (point == INVALID_POINT) return INVALID_POINT;
        int x = point.x - 2, y = point.y + 1;
        return getPoint(x, y);
    }

    private static Point downLeft(Point point) {
        if (point == INVALID_POINT) return INVALID_POINT;
        int x = point.x + 2, y = point.y - 1;
        return getPoint(x, y);
    }

    private static Point downRight(Point point) {
        if (point == INVALID_POINT) return INVALID_POINT;
        int x = point.x + 2, y = point.y + 1;
        return getPoint(x, y);
    }

    private static Point leftUp(Point point) {
        if (point == INVALID_POINT) return INVALID_POINT;
        int x = point.x - 1, y = point.y - 2;
        return getPoint(x, y);
    }

    private static Point leftDown(Point point) {
        if (point == INVALID_POINT) return INVALID_POINT;
        int x = point.x + 1, y = point.y - 2;
        return getPoint(x, y);
    }

    private static Point rightUp(Point point) {
        if (point == INVALID_POINT) return INVALID_POINT;
        int x = point.x - 1, y = point.y + 2;
        return getPoint(x, y);
    }

    private static Point rightDown(Point point) {
        if (point == INVALID_POINT) return INVALID_POINT;
        int x = point.x + 1, y = point.y + 2;
        return getPoint(x, y);
    }

    private static Point getPoint(int x, int y) {
        return validate(x, y) ? new Point(x, y) : INVALID_POINT;
    }

    private static boolean validate(int x, int y) {
        return x >= 0 && x < 4 && y >= 0 && y < 3;
    }

    public final static Point INVALID_POINT = new Point(-1, -1);

}
