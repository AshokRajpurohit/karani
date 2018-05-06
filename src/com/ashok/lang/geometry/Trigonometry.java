package com.ashok.lang.geometry;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Trigonometry {

    public static double haversine(double angle) {
        return (1 - Math.cos(angle)) / 2;
    }

    public static double inverseHaversine(double value) {
        return Math.acos(1 - 2 * value);
    }
}
