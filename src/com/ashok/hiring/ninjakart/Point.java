package com.ashok.hiring.ninjakart;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public final class Point {
    final int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return x + "-" + y;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    public int hashCode() {
        return x * y;
    }
}
