/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.codechef.marathon.year18.april;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Highway Crossing
 * Link: https://www.codechef.com/APRIL18A/problems/HIGHWAYC
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class HighwayCrossing {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();
    private static final double MIN_DISTANCE = 0.000001;

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int t = in.readInt();
        StringBuilder sb = new StringBuilder(t << 3);
        while (t > 0) {
            t--;
            int n = in.readInt(), chefSpeed = in.readInt(), laneWidth = in.readInt();
            Car[] cars = getCars(in.readIntArray(n), in.readIntArray(n), in.readIntArray(n), in.readIntArray(n));
            sb.append(process(cars, chefSpeed, laneWidth)).append('\n');
        }

        out.print(sb);
    }

    private static double process(Car[] cars, double chefSpeed, double laneWidth) {
        if (cars.length == 0)
            throw new RuntimeException("No cars");

        double time = 0, laneCrossingTime = laneWidth / chefSpeed;
        for (Car car : cars) {
            car.move(time);
            if (car.isTowards(0)) { // let's check if we can cross before car or not.
                double carReachTime = car.timeToReach(0), carCrossTime = car.timeToCross(0);
                double carPositionAtLaneCrossing = car.positionAfterTime(laneCrossingTime);
                if (carReachTime <= laneCrossingTime || Math.abs(carPositionAtLaneCrossing) <= MIN_DISTANCE) {
                    time += carCrossTime; // it's safe to let the car cross.
                }
            } else {
                if (Math.abs(car.position) <= car.length) // car is already in-front of chef.
                    time += car.timeToCross(0);
            }

            // now cross the road.
            time += laneCrossingTime;
        }

        return time;
    }

    private static Car[] getCars(int[] speeds, int[] directions, int[] positions, int[] lengths) {
        int n = positions.length;
        Car[] cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(positions[i], speeds[i], directions[i], lengths[i]);
        }

        return cars;
    }

    final static class Car {
        final double speed, direction, length, velocity;
        double position;

        Car(int position, int s, int d, int len) {
            this.position = position;
            speed = s;
            direction = d == 0 ? -1 : d;
            length = len;
            velocity = d == 0 ? -speed : speed;
        }

        void move(double time) {
            position += velocity * time;
        }

        double positionAfterTime(double time) {
            return position + velocity * time;
        }

        double timeToReach(double target) {
            return (target - position) / velocity;
        }

        boolean isTowards(double target) {
            return (target - position) * direction >= 0;
        }

        double timeToCross(double target) {
            return timeToReach(target) + length / speed;
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