package com.ashok.lang.geometry;

public class Point3D {
    public final static Point3D ORIGIN = new Point3D(0, 0, 0);
    public final int x, y, z;

    public Point3D(int a, int b, int c) {
        x = a;
        y = b;
        z = c;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Point3D))
            return false;

        Point3D point = (Point3D)o;
        return x == point.x && y == point.y && z == point.z;
    }

    public static double distance(Point3D p1, Point3D p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) +
                         (p1.y - p2.y) * (p1.y - p2.y) +
                         (p1.z - p2.z) * (p1.z - p2.z));
    }
}
