package com.ashok.projectEuler.problems;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * I implemented many un-necessary methods also those I will incorporate into
 * my Date/Calender Class in future.
 * I will explain the concept later.
 *
 * @author: Ashok Rajpurohit (ashok1113@gmail.com)
 * problem: Count Fridays the 13-th
 * https://www.hackerrank.com/contests/w17/challenges/count-fridays-the-13-th
 */

class P019 {

    private static PrintWriter out;
    private static InputStream in;
    private static int loop = 2800, loopCount = 0;
    private static int[] dayMonth = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
    private static int[] MonthDays =
            {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int[] dayCount = new int[7];
    private static int[] dayYear = new int[loop];
    private static String[] weekDays =
            {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
                    "Saturday"};

    static {
        for (int i = 0; i < 12; i++)
            dayCount[dayMonth[i]]++;

        // I am taking 1900 as reference year.
        int d = 0; // 01 01 1900 is monday

        for (int i = 0; i < loop; i++) {
            if (d > 6)
                d -= 7;
            dayYear[(i + 1900) % loop] = d;
            d++;
            if (isLeap(i + 1900))
                d++;
        }

        for (int i = 0; i < loop; i++) {
            loopCount += getCount(i + 1900);
        }
    }

    public static int process(int d1, int m1, int y1, int d2, int m2, int y2) {
        int count = 0;

        if (y2 > y1 + loop) {
            int loops = (y2 - y1) / loop;
            count += loops * loopCount;
            y1 += loops * loop;
        }

        if (y2 == y1 + loop && (m2 > m1 || (m2 == m1 && d2 >= d1))) {
            count += loopCount;
            y1 += loop;
        }

        if (y1 < y2) {
            for (int y = y1 + 1; y < y2; y++) {
                count += getCount(y);
            }

            while (m1 <= 12) {
                if (isSunday(y1, m1, 1))
                    count++;
                m1++;
            }

            while (m2 > 0) {
                if (isSunday(y2, m2, 1))
                    count++;
                m2--;
            }

            return count;
        }

        while (m1 <= m2) {
            if (isSunday(y1, m1, 1))
                count++;
            m1++;
        }

        return count;

    }

    private static int getCount(int y) {
        int count = 0;
        for (int i = 1; i < 13; i++)
            if (day(y, i, 1) == 0)
                count++;

        return count;
    }

    private static boolean isSunday(int year, int month, int date) {
        return day(year, month, date) == 0;
    }

    private static boolean isLeap(int y) {
        if ((y & 3) != 0)
            return false;

        if (y % 400 == 0)
            return true;

        return y % 100 != 0;
    }

    private static int dayYear(int y) {
        //        y = (y + 2800 - 1900) % 2800;
        return dayYear[y % loop];
    }

    private static int dayMonth(int y, int m) {
        int d = dayYear(y);
        if (isLeap(y) && m > 2)
            return (d + dayMonth[m - 1] + 1) % 7;

        return (d + dayMonth[m - 1]) % 7;
    }

    private static int day(int year, int month, int date) {
        return (dayMonth(year, month) + date) % 7;
    }

    private static String getDay(int d) {
        return weekDays[d];
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

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
    }
}
