/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.friends.ankit;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem Name: Ankit Kumar Agrawal
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class CodeDangal {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        CodeDangal a = new CodeDangal();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        CommonPrimeFactors.solve();
    }

    final static class CommonPrimeFactors {
        public static void solve() throws IOException {
            InputReader fip = new InputReader("C:\\Projects\\neustar\\src\\neustar\\arajpurohit\\contest\\problemSets\\hackthon\\hackthon\\inputs\\CommonPrimeFactors\\C1.in");
            long time = System.currentTimeMillis();
            int t = fip.readInt();
            StringBuilder sb = new StringBuilder(t << 3);

            while (t > 0) {
                t--;
                calculate(fip.readInt(), fip.readInt());
            }

            out.println(System.currentTimeMillis() - time);
            out.flush();
        }

        private static long calculate(int a, int b) {
            Map<Long, Integer> map = new HashMap<>();
            int g = gcd(a, b);
            getPrimeFactors(g, map);
            resetMap(map);
            populateMapValues(map, a);
            populateMapValues(map, b);

            long res = 1L;
            for (Map.Entry<Long, Integer> entry: map.entrySet())
                res *= calculatePower(entry.getKey(), entry.getValue());

            return res;
        }

        private static void populateMapValues(Map<Long, Integer> map, long value) {
            for (Map.Entry<Long, Integer> entry: map.entrySet()) {
                long key = entry.getKey();
                int powerCount = entry.getValue();

                while (value % key == 0) {
                    powerCount++;
                    value /= key;
                }

                entry.setValue(powerCount);
            }
        }

        private static long calculatePower(long a, long b) {
            long res = 1;
            while (b != 0) {
                if ((b & 1) == 1)
                    res = res * a;
                a = a * a;
                b = b >> 1;
            }
            return res;
        }

        private static void resetMap(Map<Long, Integer> map) {
            for (Map.Entry entry : map.entrySet())
                entry.setValue(0);
        }

        private static int gcd(int a, int b) {
            if (b == 0)
                return a;

            return gcd(b, a % b);
        }

        static void getPrimeFactors(long num, Map<Long, Integer> numbers) {
            int j = 0;
            int count = 0;
            long i = 2;
            for (; i * i <= num; i++) {
                count = 0;
                while (num % i == 0) {
                    count++;
                    num = num / i;
                }
                if (count != 0) {
                    numbers.put(i, count);
                    j++;
                }
            }
            if (num > 1) {
                numbers.put(num, 1);
            }
        }
    }
}
