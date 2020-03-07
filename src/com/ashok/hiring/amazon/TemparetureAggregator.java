/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.amazon;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;
import sun.jvm.hotspot.utilities.Assert;

import java.io.IOException;
import java.util.Collection;
import java.util.TreeMap;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TemparetureAggregator {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        TemperatureAggregatorService aggregatorService = new TemperatureAggregatorService();
        while (true) {
            int type = in.readInt();
            if (type == 0) {
                long time = in.readInt();
                double temp = in.readDouble();
                aggregatorService.recordTemperature(time, temp);
            } else {
                Collection<TemperatureStatistic> temperatureStatistics = aggregatorService.getTemperatureStatistics(in.readLong(), in.readLong());
                out.println(temperatureStatistics);
                out.flush();
            }
        }
    }

    final static class TemperatureAggregatorService {
        private final TreeMap<Long, TemperatureStatistic> temparetureInfoMap = new TreeMap<>();

        public void recordTemperature(long time, double temperature) {
            Assert.that(time >= 0, "time can not be negative");
            if (!temparetureInfoMap.containsKey(time)) {
                temparetureInfoMap.put(time, new TemperatureStatistic(time));
            }

            TemperatureStatistic tempInfo = temparetureInfoMap.get(time);
            tempInfo.add(temperature);
        }

        public Collection<TemperatureStatistic> getTemperatureStatistics(long startTime, long endTime) {
            Assert.that(endTime >= startTime, "end time can not be negative");
            Assert.that(startTime >= 0, "time can not be negative");
            Collection<TemperatureStatistic> temperatureStatistics = temparetureInfoMap.subMap(startTime, endTime + 1).values();
            return temperatureStatistics;
        }

        private Collection<TemperatureStatistic> aggregateStatistics(Collection<TemperatureStatistic> temperatureStatistics, int aggregationLevel) {
            return null;
        }
    }

    final static class TemperatureStatistic {
        final long time;
        private double min = Double.MAX_VALUE, max = Double.MIN_VALUE, sum = 0;
        private double average;
        private int count = 0;

        TemperatureStatistic(long time) {
            this.time = time;
        }

        private void add(double temperature) {
            min = Math.min(min, temperature);
            max = Math.max(max, temperature);
            sum += temperature;
            count++;
        }

        public double getAverage() {
            return sum / count;
        }

        public String toString() {
            return "time: " + time + ", min: " + min + ", max: " + max + ", avg: " + getAverage();
        }
    }

    private void testTemperatureAggregator() {
        TemperatureAggregatorService aggregatorService = new TemperatureAggregatorService();

        // add one temperature and query for different range, including and excluding the temperature time.
        // aggregator is empty and we query for different time ranges.
        // aggregator has more than one entry and ranges include one, none and all.
        // when the time range is of 1 unit, start and end time are equal: aggregator has entry, has multiple entries, does not have entry.
        aggregatorService.recordTemperature(10, 20);
    }
}
