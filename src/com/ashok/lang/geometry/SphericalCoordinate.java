package com.ashok.lang.geometry;

import com.ashok.hiring.swiggy.Coordinates;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class SphericalCoordinate extends Coordinates {
    final double longitude, latitude;

    public SphericalCoordinate(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SphericalCoordinate))
            return false;

        SphericalCoordinate coordinate = (SphericalCoordinate) o;
        return latitude == coordinate.latitude && longitude == coordinate.longitude;
    }

    /**
     * Returns distance of this point with {@code point}, asuming the radius is of 1 unit.
     *
     * @param point
     * @return distance between these points on 1 unit radius sphere.
     */
    public double distance(SphericalCoordinate point) {
        double value = Trigonometry.haversine(point.latitude - latitude) +
                Math.cos(latitude) * Math.cos(point.latitude) * Trigonometry.haversine(point.longitude - longitude);

        return Trigonometry.inverseHaversine(value);
    }

    @Override
    public double distance(Coordinates coordinates) {
        return distance((SphericalCoordinate) coordinates);
    }
}
