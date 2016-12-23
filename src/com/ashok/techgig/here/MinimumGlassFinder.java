/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ashok.techgig.here;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

/**
 * Problem Name: Minimum Glass Finder
 * Link:
 * Problem Statement:
 * Ruth is hosting a party at his home and he wants to serve a glass of water to all the guests when they arrive.
 * Given arrival and exit time of all the guests that visit his home, find the minimum number of glasses required
 * so that no one has to wait.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class MinimumGlassFinder {
    private static PrintWriter out = new PrintWriter(System.out);
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        solve();
        in.close();
        out.close();
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        DateWrapper[] dateWrappers = new DateWrapper[n * 2];

        for (int i = 0, j = 0; i < n; i++) {
            String[] data = in.readLine().split("#");
            Date from = getDate(data[0]), to = getDate(data[1]);

            if (from.compareTo(to) > 0) {
                out.println(-1);
                return;
            }

            dateWrappers[j++] = new DateWrapper(from, 1); // entering
            dateWrappers[j++] = new DateWrapper(to, -1); // leaving
        }

        Arrays.sort(dateWrappers);
        int maxGlasses = getMaxGlasses(dateWrappers);

        out.println(maxGlasses);
    }

    private static int getMaxGlasses(DateWrapper[] dateWrappers) {
        if (dateWrappers.length < 3)
            return 1;

        int maxValue = 1, current = 0;

        for (DateWrapper dateWrapper : dateWrappers) {
            current += dateWrapper.value;
            maxValue = Math.max(maxValue, current);
        }

        return maxValue;
    }

    private static Date getDate(String date) {
        String[] time = date.split(" ");
        String[] ddMMYYYY = time[0].split("/");
        String[] hourMinutes = time[1].split(":");

        return new Date(Integer.valueOf(ddMMYYYY[2]), Integer.valueOf(ddMMYYYY[0]), Integer.valueOf(ddMMYYYY[0]),
                Integer.valueOf(hourMinutes[0]), Integer.valueOf(hourMinutes[1]));
    }

    final static class DateWrapper implements Comparable<DateWrapper> {
        final Date date;
        final int value; // +1 for incoming, -1 for outgoing

        DateWrapper(Date date, int value) {
            this.date = date;
            this.value = value;
        }

        @Override
        public int compareTo(DateWrapper o) {
            int cmp = date.compareTo(o.date);

            if (cmp == 0)
                return value - o.value;

            return cmp;
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

        public String readLine() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }

            if (bufferSize == -1 || bufferSize == 0)
                throw new IOException("No new bytes");

            for (;
                 buffer[offset] == ' ' || buffer[offset] == '\t' || buffer[offset] ==
                         '\n' || buffer[offset] == '\r'; ++offset) {
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize; ++offset) {
                if (buffer[offset] == '\n' || buffer[offset] == '\r')
                    break;
                if (Character.isValidCodePoint(buffer[offset])) {
                    sb.appendCodePoint(buffer[offset]);
                }
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            return sb.toString();
        }
    }
}
