package com.ashok.physics;

import com.ashok.lang.inputs.InputReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * You are given set of objects (mass objects) with mass and their position.
 * All the objects lie on straight line. It is assumed that no two objects share
 * the position. We have to find zero gravity points.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class ZeroGravityPoints {
    private final static double epsilon = 0.0000000001;

    public static void solve() throws IOException {
        InputReader input = new InputReader();

        int count = input.readInt();
        MassObject[] objects = new MassObject[count];

        for (int i = 0; i < count; i++)
            objects[i] = new MassObject(input.readInt(), input.readDouble());

        Arrays.sort(objects);

        LinkedList<Double> zeroGravityPointsList = new LinkedList<Double>();
        for (int i = 1; i < count; i++)
            zeroGravityPointsList.add(zeroGravityPoint(objects[i - 1].x + epsilon, objects[i].x - epsilon, objects));

        System.out.println(zeroGravityPointsList);
    }

    private static double zeroGravityPoint(double xfrom, double xto, MassObject[] massObjects) {
        double xmid = (xfrom + xto) / 2;

        while (xmid > xfrom + epsilon) {
            double gravitationalField = calculateGravitationalField(xmid, massObjects);

            if (gravitationalField == 0.0)
                return xmid;

            if (gravitationalField > 0.0)
                xfrom = xmid;
            else
                xto = xmid;

            xmid = (xfrom + xto) / 2;
        }

        return xmid;
    }

    private static double calculateGravitationalField(double pos, MassObject[] massObjects) {
        double totalField = 0.0;

        for (MassObject massObject : massObjects) {
            double distance = pos - massObject.x;

            double field = massObject.mass / (distance * distance);

            if (distance >= 0)
                totalField += field;
            else
                totalField -= field;
        }

        return totalField;

    }

    final static class MassObject implements Comparable<MassObject> {
        private final int mass;
        private final double x;

        MassObject(int m, double x) {
            mass = m;
            this.x = x;
        }

        @Override
        public int compareTo(MassObject object) {
            if (this.x > object.x)
                return 1;

            if (this.x == object.x)
                return 0;

            return -1;
        }
    }
}
