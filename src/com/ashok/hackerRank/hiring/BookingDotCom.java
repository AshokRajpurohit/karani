/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hackerRank.hiring;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Problem Name: Booking.com
 * Link: HackerRank Jobs
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class BookingDotCom {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        BookingDotCom a = new BookingDotCom();
        try {
            a.solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private void solve() throws IOException {
        while (true) {
            int n = in.readInt();
            int[][] ar = in.readIntTable(in.readInt(), in.readInt());
            out.println(CustomerServiceCapacity.howManyAgentsToAdd(n, ar));
            out.flush();
        }
    }

    final static class Triangle {
        static int triangle(int a, int b, int c) {
            if (a <= 0 || b <= 0 || c <= 0)
                return 0;

            if (a == b && b == c)
                return 1;

            int max = Math.max(a, Math.max(b, c));
            if (max >= (a + b + c) >>> 1)
                return 0;

            return 2;
        }
    }

    final static class DeltaEncoding {
        final static int ESCAPE_TOKEN = -128;

        static int[] delta_encode(int[] array) {
            int len = array.length;
            List<Integer> list = new LinkedList<>();
            list.add(array[0]);

            for (int i = 1; i < len; i++) {
                int diff = array[i] - array[i - 1];
                if (!validate(diff)) list.add(ESCAPE_TOKEN);
                list.add(diff);
            }

            int[] result = new int[list.size()];
            int index = 0;
            for (int e : list) result[index++] = e;

            return result;
        }

        private static boolean validate(int value) {
            return value >= -127 && value <= 127;
        }
    }

    final static class CustomerServiceCapacity {
        static int howManyAgentsToAdd(int noOfCurrentAgents, int[][] callsTimes) {
            Call[] startCalls = toCalls(callsTimes);
            Call[] endCalls = startCalls.clone();
            Arrays.sort(startCalls, (a, b) -> a.start - b.start);
            Arrays.sort(endCalls, (a, b) -> a.end - b.end);
            int len = startCalls.length, currentTime = startCalls[0].start, count = 0, maxTime = startCalls[len - 1].end, si = 0, ei = 0, max = 0;
            while (si < len && startCalls[si].start <= currentTime) {
                si++;
                count++;
            }

            while (ei < len && endCalls[ei].end < currentTime) {
                ei++;
                count--;
            }

            max = Math.max(max, count);

            for (int i = 0; i < len; i++) {
                if (si < len) {
                    currentTime = startCalls[si].start;
                    si++;
                    count++;
                }

                while (ei < len && endCalls[ei].end < currentTime) {
                    ei++;
                    count--;
                }

                max = Math.max(max, count);
            }

            return Math.max(0, max - noOfCurrentAgents);
        }

        private static Call[] toCalls(int[][] callsTimes) {
            int size = callsTimes.length, index = 0;
            Call[] calls = new Call[size];

            for (int[] data : callsTimes)
                calls[index++] = new Call(data[0], data[1]);

            return calls;
        }

        final static class Call {
            final int start, end;

            Call(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }

    final static class SortHotelList {
        static int[] sort_hotels(String keywords, int[] hotel_ids, String[] reviews) {
            String[] keyWords = keywords.split(" ");
            int len = hotel_ids.length;
            Hotel[] hotels = new Hotel[len];
            for (int i = 0; i < len; i++) {
                hotels[i] = new Hotel(hotel_ids[i], reviews[i]);
            }

            for (String keyword : keyWords) {
                int[] failTable = failFunctionKMP(keyword);
                for (Hotel hotel : hotels) {
                    hotel.count += stringCountIntersect(hotel.review, keyword, failTable);
                }
            }

            Arrays.sort(hotels, (a, b) -> b.count - a.count);
            int[] ids = new int[hotels.length];
            int index = 0;
            for (Hotel hotel : hotels)
                ids[index++] = hotel.id;

            return hotel_ids;
        }

        /**
         * This method returns the number of times search appears in s while
         * occurances can be intersecting.
         * This method is based on KMP algorithm for string matching.
         * if the search string and s string both are of same length then it checks
         * for equality and returns 1 or 0 accordingly.
         *
         * @param s
         * @param search
         * @return number of times search appears in String s
         */
        private static int stringCountIntersect(String s, String search, int[] failTable) {
            if (s.length() < search.length())
                return 0;

            if (s.length() == search.length()) {
                for (int i = 0; i < s.length(); i++)
                    if (s.charAt(i) != search.charAt(i))
                        return 0;

                return 1;
            }

            int count = 0;
            int i = 0, j = 0;

            while (i < s.length()) {
                if (s.charAt(i) == search.charAt(j)) {
                    if (j == search.length() - 1) {
                        count++;
                        i++;
                        j = failTable[j];
                    } else {
                        i++;
                        j++;
                    }
                } else {
                    if (j > 0) {
                        j = failTable[j - 1];
                    } else
                        i++;
                }
            }

            return count;
        }

        /**
         * returns fail function (Partial Matching Table) used in KMP string search
         * algorithm.
         *
         * @param s String for which the fail function to be generated.
         * @return
         */
        private static int[] failFunctionKMP(String s) {
            int[] ar = new int[s.length()];
            int i = 1, j = 0;

            while (i < s.length()) {
                if (s.charAt(i) == s.charAt(j)) {
                    ar[i] = j + 1;
                    i++;
                    j++;
                } else if (j > 0)
                    j = ar[j - 1];
                else {
                    ar[i] = 0;
                    i++;
                }
            }
            return ar;
        }

        final static class Hotel {
            final int id;
            String review;
            int count = 0;

            Hotel(int id, String review) {
                this.id = id;
                this.review = review;
            }
        }
    }
}
