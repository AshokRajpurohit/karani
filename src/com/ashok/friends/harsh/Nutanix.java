/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.harsh;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import com.ashok.lang.math.Prime;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Problem Name:
 * Link:
 * <p>
 * For complete implementation please see
 * {@link https://github.com/AshokRajpurohit/karani/tree/master/src/com/ashok/}
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Nutanix {
    private static PrintWriter out = new Output(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
//        SortedArrays.solve();
        Help.solve();
//        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        while (true) {
            out.print(Prime.primesInRange(in.readInt(), in.readInt()));
            out.flush();
        }
    }

    final static class SortedArrays {
        private static void solve() throws IOException {
            int n = in.readInt();
            out.println(process(in.readIntArray(n)));
        }

        private static long process(int[] ar) {
            int len = ar.length;
            long res = 0;
            for (int i = 1; i < len; i++) {
                int moves = Math.max(0, ar[i - 1] + 1 - ar[i]);
                ar[i] += moves;
                res += moves;
            }

            return res;
        }
    }

    final static class Help {
        private static void solve() throws IOException {
            int n = in.readInt();
            PetrolPump[] petrolPumps = new PetrolPump[n];
            for (int i = 0; i < n; i++)
                petrolPumps[i] = new PetrolPump(in.readInt(), in.readInt());

            Car car = new Car(in.readInt(), in.readInt());
            Arrays.sort(petrolPumps, new Comparator<PetrolPump>() {
                @Override
                public int compare(PetrolPump o1, PetrolPump o2) {
                    return o2.distance - o1.distance;
                }
            });

            out.println(process(petrolPumps, car));
        }

        private static int process(PetrolPump[] petrolPumps, Car car) {
            int len = petrolPumps.length, stops = 0, distance = 0;
            PriorityQueue<PetrolPump> queue = new PriorityQueue<>(new Comparator<PetrolPump>() {
                @Override
                public int compare(PetrolPump o1, PetrolPump o2) {
                    return o2.petrol - o1.petrol;
                }
            });

            for (PetrolPump petrolPump : petrolPumps) {
                if (petrolPump.distance > car.distance) // no need to consider this petrol pump, it's not in route.
                    continue;

                distance = car.distance - petrolPump.distance;
                while (distance > car.petrol) { // car does not have enough petrol to reach this place.
                    if (queue.isEmpty()) // no petrol pump to get a petrol. Oh we can't reach town.
                        return -1;

                    PetrolPump petrolPumpStop = queue.remove();
                    car.petrol += petrolPumpStop.petrol; // get petrol.
                    stops++;
                }

                queue.add(petrolPump); // add this petrol pump also to the available pumps.
            }

            while (car.distance > car.petrol) {
                if (queue.isEmpty()) // no petrol pump to get a petrol. Oh we can't reach town.
                    return -1;

                PetrolPump petrolPumpStop = queue.remove();
                car.petrol += petrolPumpStop.petrol; // get petrol.
                stops++;
            }

            return stops;
        }
    }

    private static class PetrolPump {
        final int distance, petrol;

        PetrolPump(int d, int p) {
            distance = d;
            petrol = p;
        }

        public String toString() {
            return "distance: " + distance + ", capacity: " + petrol;
        }
    }

    final static class Car {
        private int distance, petrol;

        Car(int d, int p) {
            distance = d;
            petrol = p;
        }

        public String toString() {
            return "distance: " + distance + ", petrol: " + petrol;
        }
    }
}